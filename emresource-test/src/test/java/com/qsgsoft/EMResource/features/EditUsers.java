package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import org.junit.*;
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
import static org.junit.Assert.*;

/**********************************************************************
' Description :This class includes Edit Users requirement testcases
' Precondition:
' Date		  :24-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/


public class EditUsers  {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditUsers");
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
	Selenium selenium,seleniumPrecondition;
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
				4444,  propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));
		

		selenium.start();
		selenium.windowMaximize();
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444,  propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
						.getProperty("urlEU"));
		
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	
	/********************************************************************
	'Description	:Verify that a user can be edited by RegAdmin.
	'Precondition	:User U1 is created without selecting any role and 
	'				 right from 'Advanced Options' section. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-April-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*********************************************************************/
	
	@Test
	public void testBQS68427() throws Exception {

		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		// CreateUsers
		String strFILE_PATH = pathProps.getProperty("TestData_path");

		try {
			gstrTCID = "BQS-68427 ";
			gstrTO = "Verify that a user can be edited by RegAdmin.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";

			String strRegn = "";
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			

			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users.<-> 'Users
			 * List' page is displayed.
			 */

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strRegn = rdExcel.readData("Login", 3, 4);
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
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3: Click on the 'Edit' link associated with user U2 and edit
			 * the mandatory data.<-> The updated data is displayed on the
			 * 'Users List' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUsrFulName_1 = "Edit" + strUsrFulName_1;
				selenium.type(propElementDetails
						.getProperty("CreateNewUsr.FulUsrName"), strUsrFulName_1);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[3][text()='"
								+ strUsrFulName_1 + "']"));

				log4j
						.info("The updated data is displayed on the 'Users List' screen. ");

			} catch (AssertionError Ae) {
				log4j
						.info("The updated data is NOT displayed on the 'Users List' screen. ");
				strFuncResult = "The updated data is NOT displayed on the 'Users List' screen. ";

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
			gstrTCID = "BQS-68427";
			gstrTO = "Verify that a user can be edited by RegAdmin.";
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
	
	/*************************************************************************
	'Description	:Verify that a user account can be deactivated by RegAdmin.
	'Precondition	:User U1 is created .
	'Arguments		:None
	'Returns		:None
	'Date	 		:25-April-2012
	'Author			:QSG
	'------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**************************************************************************/
	
	@Test
	public void testBQS111895() throws Exception {

		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		// CreateUsers

		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();// Object

		try {
			gstrTCID = "BQS-111895 ";
			gstrTO = "Verify that a user account can be deactivated by RegAdmin.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users.<-> 'Users
			 * List' page is displayed.
			 */
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);


			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*1 	Precondition:

				User U1 and U2 are created. 		No Expected Result 
			*/

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

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*2 	Login as RegAdmin and navigate to Setup>>Users. 		'Users List' page is displayed. */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on the 'Edit' link associated with user U2 and select
			 * 'User Status' check box and save the page. 'Yes, Deactivate this
			 * User' and 'No, Do NOT Deactivate this User' button are available
			 * in 'Confirm User Deactivation' page.
			 */ 
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(seleniumPrecondition, strUsrFulName_2,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.deactivateUser(seleniumPrecondition,
						strUserName_2, strUsrFulName_2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.login(selenium, strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Login as user U2.<-> 'Account Deactivated' screen with
			 * themessage 'This account has been deactivated. Please contact
			 * EMSystem at (414) 721-9700 or (888) 367-9783 for assistance.'
			 * is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals("This account has been deactivated.",
							selenium.getText(propElementDetails
									.getProperty("AccountDeactivated")));
					assertEquals(
							"Please contact EMSystem at 1-888-735-9559 (Prompt 1 then 6) for assistance.",
							selenium
									.getText(propElementDetails
											.getProperty("AccountDeactivated.Contact")));

					log4j
							.info("'This account has been deactivated.Please contact"
									+ " EMSystem at (414) 721-9700 or (888) 367-9783 for "
									+ "assistance.' is  displayed. ");

				} catch (AssertionError Ae) {
					strFuncResult = "'This account has been deactivated.Please contact"
							+ " EMSystem at (414) 721-9700 or (888) 367-9783 for "
							+ "assistance.' is NOT displayed. " + Ae;

					log4j
							.info("'This account has been deactivated.Please contact"
									+ " EMSystem at (414) 721-9700 or (888) 367-9783 for "
									+ "assistance.' is NOT displayed. "
									+ Ae);

					gstrReason = strFuncResult;
				}

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
			gstrTCID = "BQS-111895";
			gstrTO = "Verify that a user account can be deactivated by RegAdmin.";
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
	'Description	:Verify that a user can be provided a role with
	                 right to setup resource.
	'Precondition	:1. A form F1 is configured as OLD form type
	                 (i.e. without selecting the check box 'New Form'), selecting 'User Initiate & Fill out Him/Herself' for 'Form Activation' and 'User to Individual Users' for 'Completed Form Delivery' and also select to receive completed form via email, pager and web.
	                 2. Question Q has been added to the questionnaire of old form F1.
	                 3. User A is created.
	                 4. User B is created by proving email and pager address. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	@Test
	public void testBQS69679() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		Forms objForms = new Forms();
		try {
			gstrTCID = "BQS-69679 ";
			gstrTO = " Verify that a user with only �Associated with� right on a resource"
					+ "receives the completed form sent to the resource.";
			gstrResult = "FAIL";
			gstrReason = "";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strTestData[] = new String[10];
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			
			// Data for searching user
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_" +strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" +strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			
			// USER
			String strUserNameA = "AutoUsrA" + System.currentTimeMillis();
			String strUserNameB = "AutoUsrB" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			
			String strPrimaryEMail=rdExcel.readData("WebMailUser", 2, 1);
			String strEMail=rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue=rdExcel.readData("WebMailUser", 2, 1);
			//form
			String strFormTempTitleOF = "OF" +strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "User To Individual Users";
		    String strQuestion="Q"+strTimeText;
		    String strDescription="Description";
		    String strquesTypeID="Free text field";   

			/*precondition:1. A form F1 is configured as OLD form type (i.e. without selecting the check box 'New Form'), selecting 'User Initiate & Fill out Him/Herself' for 'Form Activation' and 'User to Individual Users' for 
			 * 'Completed Form Delivery' and also select to receive completed form via email, pager and web.
			2. Question Q has been added to the questionnaire of old form F1.
			3. User A is created.
			4. User B is created by proving email and pager address. 
			 */
			 
			   log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
			     + " EXECUTION STATRTS~~~~~");
			
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
					strFuncResult = objStatusTypes.navStatusTypList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue = "Number";
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							selenium, strStatusTypeValue, statTypeName,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
							statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatusTypeValues[0] = objStatusTypes
							.fetchSTValueInStatTypeList(selenium, statTypeName);

					if (strStatusTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.navResourceTypList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
							selenium, strResrcTypName, strStatusTypeValues[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
							strResrcTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objResourceTypes
							.fetchResTypeValueInResTypeList(selenium,
									strResrcTypName);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// RS1
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navResourcesList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navToCreateResourcePage(selenium);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.createResourceWithMandFields(
							selenium, strResource, strAbbrv, strResrcTypName,
							"Hospital", "FN", "LN");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
							strResource);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndVerifyResource(selenium,
							strResource, strHavBed, "", strAbbrv, strResrcTypName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strResVal = objResources.fetchResValueInResList(selenium,
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
					strFuncResult = objCreateUsers.navUserListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				//user1
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
							strUserNameA, strInitPwd, strInitPwd, strUsrFulName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
					strFuncResult = objCreateUsers.advancedOptns(selenium,
							strOptions, true);
				} catch (AssertionError ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				//user2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
							strUserNameB, strInitPwd, strInitPwd, strUsrFulName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(selenium, "", "", "", "",
							"", strPrimaryEMail, strEMail, strPagerValue, "");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navToFormConfig(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);				
					strFuncResult=objForms.navTocreateNewFormTemplate(selenium);			
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(selenium,
							strFormTempTitleOF, strDescription, strFormActiv,
							strComplFormDel, true, true, true, false, false,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objForms.navToCreateNewQuestion(selenium,
							strFormTempTitleOF);					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
				    assertEquals("", strFuncResult);	    
				    strFuncResult = objForms.ToCreateQuestion(selenium,
				      strFormTempTitleOF, strQuestion, strDescription,
				      strquesTypeID, false, false);
				   } catch (AssertionError Ae) {
				    gstrReason = strFuncResult;
				   }

			 log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					     + " EXECUTION ENDS~~~~~");
			 
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");		
			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users. 'Users
			 * List' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3:Click on the 'Edit' link associated with user B provide
			 * �Associated with� right on a resource RS and save the page.
			 * 'Users List' page is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResource, true, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 4:Navigate to Form>>Form security and click on 'Security'
			 * associated with the form. 'Form Security Settings: F1' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 5 :Select user A to activate the form, then save the page.
			 * 'Form Security Settings' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserNameA, strFormTempTitleOF, true, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserNameA+"/"+strInitPwd+","+strUserNameB + "/" + strInitPwd;
				strTestData[3] = strResource;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 6th step" ;
				strTestData[6] = strQuestion;
				strTestData[9] = strResrcTypName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

		} catch (Exception e) {
			gstrTCID = "BQS-69679";
			gstrTO = " Verify that a user with only �Associated with� right on a resource"
					+ "receives the completed form sent to the resource.";
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
	'Description		:Verify that data provided in the mandatory fields for a user can be edited.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/30/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS88831() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		RegionalInfo objRegionalInfo=new RegionalInfo();
	try{	
		gstrTCID = "88831";	//Test Case Id	
		gstrTO = " Verify that data provided in the mandatory fields for a user can be edited.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

	
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		String strLoginUserName = "";
		String strLoginPassword = "";
		String strRegn =rdExcel.readData("Login", 3, 4);
		
		// USER
		String strUserName = "Autousr" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "fn"+strUserName;
		String strUsrFulName1 = "Cfn"+strUserName;
		
		//search user
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		String strByRole = "";
		String strByResourceType = "";
		String strByUserInfo = "";
		String strNameFormat = "";	
		strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
				12, strFILE_PATH);
		strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
				strFILE_PATH);
		strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
				strFILE_PATH);

		
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		
		/*
		* STEP :
		  Action:Login as RegAdmin, navigate to Setup >> Users
		  Expected Result:'Users List' page is displayed
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New User' button, create a user U1 providing only the mandatory data.
		  Expected Result:User U1 is listed in the 'Users List' screen under Setup.
	      Username and Full name provided for user U1 is displayed.
		*/
		//541188
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(selenium, strUserName, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyUserFullName(selenium,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Regional Info >> Users
		  Expected Result:User U1 is listed on the 'Users List' screen.
	      Username and Full name provided for user U1 is displayed.
		*/
		//541189
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.vrfyUsrNameAndFNInRegInfo(
						selenium, strUserName, strUsrFulName);
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
		  Action:Login as user U1
		  Expected Result:User is taken to the 'Change Password' screen.
		*/
		//541190
		/*
		* STEP :
		  Action:Provide data in 'New Password' and 'Verify Password' fields and click on 'Submit'
	     User U1's full name is displayed at the bottom right of the application
		*/
		//541191
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium, strUsrFulName, strUserName);
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
		  Action:Login as RegAdmin, navigate to Setup >> Users
		  Expected Result:'Users List' page is displayed
		*/
		//541192
			
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on edit link associated with user 'U1'  edit the  mandatory data.
		  Expected Result:User U1 is listed in the 'Users List' screen under Setup.
	      Updated Username and Full name provided for user U1 is displayed.
		*/
		//541193
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
				selenium.type(propElementDetails
						.getProperty("CreateNewUsr.FulUsrName"), strUsrFulName1);
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
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(selenium, strUserName, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyUserFullName(selenium,
						strUsrFulName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Regional Info >> Users
		  Expected Result:User U1 is listed in the 'Users List' screen under Setup.
	      Updated Username and Full name provided for user U1 is displayed.
		*/
		//541194
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.vrfyUsrNameAndFNInRegInfo(
						selenium, strUserName, strUsrFulName1);
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
		  Action:Login as user U1
		  Expected Result:'Region Default' screen is displayed. 
	      User U1's updated full name is displayed at the bottom right of the application
		*/
		//541195
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium,
						strUsrFulName1, strUserName);
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
		gstrTCID = "BQS-88831";
		gstrTO = "Verify that data provided in the mandatory fields for a user can be edited.";
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


	

	/*******************************************************************
	'Description	Edit a user and de select role and verify that the
	'			    role is no longer assigned 
	'Precondition	:User U1 is created without selecting any role and 
	'				 right from 'Advanced Options' section. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-July-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*********************************************************************/
	
	@Test
	public void testBQS68478() throws Exception {

		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		// CreateUsers
		Roles objRoles = new Roles();

		try {
			gstrTCID = "BQS-68478 ";
			gstrTO = "Edit a user and deselect role and verify"
					+ " that the role is no longer assigned ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = "";
			String strLoginPassword = "";

			String strRegn =  rdExcel.readData("Login",3, 4);

			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			String strRoleName = "AutoRole" + strTimeText;
			String strRoleValue = "";
			String strOptions = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

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
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
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

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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
				log4j.info("The newly selected right is available to the "
						+ "user (ie 'Setup' tab is NOT available and the "
						+ "user cannot create or edit other user). ");

				gstrResult = "PASS";

			} catch (AssertionError Ae) {

				log4j.info("The newly selected right is available to the "
						+ "user (ie 'Setup' tab is  available and the "
						+ "user cannot create or edit other user). " + Ae);

				gstrReason = "The newly selected right is available to the "
						+ "user (ie 'Setup' tab is  available and the "
						+ "user cannot create or edit other user). "
						+ strFuncResult + Ae;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68478";
			gstrTO = "Edit a user and deselect role and"
					+ " verify that the role is no longer assigned ";
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
	
	/******************************************************************************************************
	'Description	:	Edit the status change notification Preferences of User A while editing the user 
	'				 account of User A and verify that:a. The selected notification methods for the
					 resources are retained when User A opens the status change notification screen.b. User A receives 
					 	 status change notification as selected. 
	'Precondition	:1. Status type ST(number) is created selecting role R1 under both 'Roles with view rights' and 'Roles with update rights'.
					2. Resource type RT is associated with status type ST.
					3. Resource RS is created with address under resource type RT.
					4. User U1 is created with primary email, email and pager address and also by selecting role R1.
					5. User U2 is created with role R1 and 'Update right' for resource RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:8-May-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------------------------
	'Modified Date                            											Modified By
	'15-May-2012                                     									   <Name>
	**********************************************************************************************************/

	@Test
	public void testBQS69680() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Preferences objPreferences = new Preferences();// object of class
														// Preferences
		EventNotification objEventNotification = new EventNotification();// object
																			// of
																			// class
																			// EventNotification
		Resources objResources=new Resources();
		
		General objMail = new General();// object of class General
		ViewMap objViewMap=new ViewMap();
		Roles objRoles=new Roles();
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
	
		try {
			gstrTCID = "BQS-69680 ";
			gstrTO = "Edit the status change notification Preferences of User A"
				+ " while editing the user account of User A and verify that:"
				+ "a. The selected notification methods for the resources are"
				+ " retained when User A opens the status change notification"
				+ " screen.b. User A receives status change notification as "
				+ "selected. ";
			gstrResult = "FAIL";
			gstrReason = "";
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strStatusTypeValue = "Number";
			String statTypeName = "AutoNST_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strResVal = "";
			
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			
			//Admin login 
			String strLoginUserName = "";
			String strLoginPassword = "";
			
			// Region Name
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			//Users U1 mandatory fields
			String strUserName_U1 = "AutoUserU1"+System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName_U1 = strUserName_U1;

			//Users U2 mandatory fields
			String strUserName_U2 = "AutoUserU2"+System.currentTimeMillis();
			String strUsrFulName_U2 = strUserName_U2;
	
		
			String strOptions = "";
			
			//Search criteria
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";
			

			//String  strSTValue="";
			
			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			
			String strSTvalue[] = new String[1];
			strSTvalue[0]="";
			String strRSValue[] = new String[1];
			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Users. 'Users List'
			 * page is displayed.
			 */ 
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			

			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				strStatValue = objST.fetchSTValueInStatTypeList(
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
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";

				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium, strResource);

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

				strFuncResult=objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 4. User U1 is created with primary email, email and pager address
			 * and also by selecting role R1
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_U1, strInitPwd, strConfirmPwd, strUsrFulName_U1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";
				
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
		

			try {
				assertEquals("", strFuncResult);

				// Role
				selenium.click("css=input[name='userRoleID'][value='"
						+ strRoleValue + "']");

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_U1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*5. User U2 is created with role R1 and 'Update right' for resource RS. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_U2, strInitPwd, strConfirmPwd, strUsrFulName_U2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				selenium.click("css=input[name='userRoleID'][value='"+strRoleValue+"']");
				
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_U2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * // 2 Login as RegAdmin and navigate to Setup>>Users, click on
			 * 'Edit' link associated with user U1. 'Edit User' screen is
			 * displayed.
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_U1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navStatusChangeNotiFrmEditUserPge(selenium,
								strUserName_U1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			
			/*
			 * 3 Click on 'Status Change Notification Preferences' from 'User
			 * Preferences' section. 'My Status Change Preferences for user U1'
			 * screen is displayed.
			 */ 
			
			/*
			 * 4 Click on 'Add', search for a resource RS and select RS and
			 * click on 'Notifications' button. 'Edit Status Change Preferences
			 * for user U1' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndSelctNotifSTInFrmEditUsr(selenium,
								strUserName_U1, strResource, "(Any)",
								strRSValue[0], strSTvalue[0]);
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*6 	Save the 'Edit User' page. 		'Users List' page is displayed. */

			/*try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_U1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
			
			/*
			 * 7 Login as user U1 and navigate to Preferences>>Status Change
			 * Prefs. 'My Status Change Preferences' screen is displayed. The
			 * selected preferences are retained for status type ST.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_U1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult=objPreferences.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objPreferences
						.verifyEventNotifInMyStatusChangPref(selenium,
								statTypeName, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Logout and login as user U2 and navigate to View >> Map, select
			 * resource 'RS' under 'Find Resource' drop down and update the
			 * status value of Status type 'ST' on 'resource pop up window'
			 * screen. Status type 'ST' is updated and is displayed on 'resource
			 * pop up window' screen.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_U2,
						strInitPwd);
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

			try {
				assertEquals("", strFuncResult);

				String[] strRoleStatTypeValue = { strSTvalue[0] };
				strFuncResult = objViewMap.updateStatusType(selenium,
						strResource, strRoleStatTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=span.time");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				String str = strDate[0].substring(1, 3);
				log4j.info(str);

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = str;
				strFndStYear = dts.getTimeOfParticularTimeZone("CST", "yyyy");;

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 9 Logout and login user U1. User U1 receives web, email and pager
			 * notification for status change.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserName_U1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				String strCurrDate = dts.converDateFormat(strStartDate,
						"M/d/yyyy", "dd MMM yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				String strSTDateTime = strStartDate;
				strFuncResult = objEventNotification.ackSTWebNotification(
						selenium, strResource, strSTDateTime);

				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				strSubjName = "Change for Rs";

				strMsgBody2 = statTypeName + " from 0 to 2; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_U2
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statTypeName
						+ " status from 0 "
						+ "to 2.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						try {
							assertEquals(strMsg, selenium
									.getText("css=div.fixed.leftAlign"));
						} catch (AssertionError Ae) {
							System.out.println(Ae);
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						try {
							assertEquals(strMsg, selenium
									.getText("css=div.fixed.leftAlign"));
						} catch (AssertionError Ae) {
							System.out.println(Ae);
						}
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						try {
							assertEquals(strMsg, selenium
									.getText("css=div.fixed.leftAlign"));
						} catch (AssertionError Ae) {
							System.out.println(Ae);
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
					selenium.selectFrame("Data");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// verify result
				if (intResCnt == 2)
					gstrResult = "PASS";

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
			gstrTCID = "BQS-69680";
			gstrTO = "Edit the status change notification Preferences of User A"
					+ " while editing the user account of User A and verify that:"
					+ "a. The selected notification methods for the resources are"
					+ " retained when User A opens the status change notification"
					+ " screen.b. User A receives status change notification as "
					+ "selected. ";
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
	'Description	:Reactivate an inactive user and verify that the user
	'				 can successfully login 
	'Precondition	:User U1 is created and is inactive. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-April-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*********************************************************************/
	
	@Test
	public void testBQS68453() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application
		
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "BQS-68453 ";
			gstrTO = "Reactivate an inactive user and verify that"
					+ " the user can successfully login ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword  = rdExcel.readData("Login", 3, 2);
			String strRegn= rdExcel.readData("Login", 3, 4);
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
		
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strUserName_Test = "AutoUsr_Test" + System.currentTimeMillis();
			String strUsrFulName_Test = strUserName_Test;
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*User U1 is created and is inactive. */

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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName_1, strUsrFulName_1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_Test, strInitPwd, strConfirmPwd,
						strUsrFulName_Test);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_Test, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
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

			try {
				assertEquals("", strFuncResult);

				selenium.click("css=#INCLUDE_INACTIVE");

				int intCnt = 0;

				try {

					while (selenium.isVisible(propElementDetails
							.getProperty("Reloading.Element"))
							&& intCnt < 60) {
						intCnt++;
						Thread.sleep(1000);
					}

				} catch (Exception e) {
					log4j.info(e);
				}

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName_1,
								strByRole, strByResourceType,
								strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		
			try {
				assertEquals("", strFuncResult);

				try {
					assertTrue(selenium
							.isElementPresent("//td[@class='inactive'][contains(text(),'"
									+ strUsrFulName_1 + "')]"));

					log4j
							.info("Inactive User Present in the User List \n Full name of inactive user is struck out. ");

				} catch (AssertionError Ae) {
					strFuncResult = "Inactive User NOT Present in the User List\n Full name of inactive user is NOT struck out.  "
							+ Ae;
					log4j
							.info("Inactive User NOT Present in the User List\n Full name of inactive user is NOT struck out.  "
									+ Ae);
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//td[@class='inactive'][contains(text(),'"
									+ strUsrFulName_Test + "')]"));

					log4j.info("active User Present in the User List ");

				} catch (AssertionError Ae) {
					strFuncResult = "active User NOT Present in the User List " + Ae;
					log4j.info("active User NOT Present in the User List " + Ae);
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 3: Select 'include inactive users' check box. <->1.
			 * Active and inactive users are displayed on the 'Users List'
			 * screen. 2. Full name of inactive user is struck out.
			 */
			
			/*
			 * STEP 4: Click on the 'Edit' link associated with user U1 and
			 * select 'User Status' check box and save the page. <->1. 'User
			 * Activation Complete' screen is displayed. 2. 'Return to User
			 * List' button is available.
			 */
			
			/*
			 * STEP 5: Click on 'Return to User List' button.<-> 'Users
			 * List' screen is displayed with user U1.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.activateUser(selenium,
						strUserName_1, strUsrFulName_1, true);
				
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 6: Login as user U1.<-> User U1 is logged into
			 * EMResource application successfully
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				gstrResult = "PASS";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68453";
			gstrTO = "Reactivate an inactive user and verify that"
					+ " the user can successfully login ";
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
	  'Description  :Edit a user and select new role and verify that the 
	                ' user has the right selected for the role. ' 
	  'Precondition :1. Role R1 is created selecting some right 
	                 (eg. User - Setup User Accounts). 
	                 2. User U1 is created without selecting role R1. 
	  'Arguments    :None 
	  'Returns      :None
	  'Date         :02-May-2012 
	  'Author       :QSG
	  '-------------------------------------------------------------------
	  'Modified Date Modified By '<Date> <Name>
	 *********************************************************************/

	@Test
	public void testBQS68485() throws Exception {
		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";// To store Function result

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers

		Roles objRole=new Roles();

		try {
			gstrTCID = "BQS-68485 ";
			gstrTO = "Verify that a user can be edited by RegAdmin.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strRoleValue = "";
			String strRoleName = "AutoRole" + strTimeText;

			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.rolesMandatoryFlds(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole
						.savAndVerifyRoles(selenium, strRoleName);
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

			/* 2. User U1 is created without selecting role R1. */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * Login as RegAdmin and navigate to Setup>>Users. 'Users List' page
			 * is displayed.
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
			
			// Navigating to user list Page

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Searching User U1 in UserList
			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
						strFILE_PATH);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);// Admin LogOut

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3:Click on the 'Edit' link associated with user U1 and
			 * select the role R1 from 'User Type & Roles' section and save the
			 * page.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Verifying Set-Up link and User Link
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("SetUP.SetUpLink")));
				log4j.info("Set-Up link is present");

				selenium.mouseOver(propElementDetails
						.getProperty("SetUP.SetUpLink"));

				assertTrue(selenium.isElementPresent(propElementDetails
						.getProperty("SetUP.UsersLink")));
				log4j.info("Users link is present");

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				log4j.info("Set-Up link is Not  present");
				log4j.info("Users link is Not present");

				gstrReason = "Set-Up link is Not  present" + ""
						+ "Users link is Not present";

			}

			// Navigating to User list
			try {
				assertEquals("", strFuncResult);

				// Verifying Create New UserLink and Edit link
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsrLink")));
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("UserList.Edit")));

					log4j.info("Create New User Link is Present");
					log4j.info("Edit link is Present");
					gstrResult = "PASS";

				} catch (AssertionError Ae) {

					log4j.info("Create New User Link is Not Present");
					log4j.info("Edit link is Not Present");
					gstrReason = "Create New User Link is Not Present"
							+ "Edit link is Not Present";
				}

			} catch (AssertionError Ae) {
				log4j.info("SetUp link is not available");
				log4j.info("Do not Navigated to User List");
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION ENDS~~~~~");

		} catch (Exception e) {
			gstrTCID = "BQS-68485";
			gstrTO = "Edit a user and select new role and verify that the"
					+ "user has the right selected for the role. ";
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
	'Description	:Edit a user and deselect a right and verify that 
	'				 the user no longer has the right.
	'
	'Precondition	:User U1 is created without selecting any role and 
	'				 right from 'Advanced Options' section. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-April-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*********************************************************************/
	
	@Test
	public void testBQS68507() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application
		
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Roles objRole=new Roles();

		try {
			gstrTCID = "BQS-68507 ";
			gstrTO = "Edit a user and deselect a right and verify"
					+ " that the user no longer has the right.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strRoleValue = "";
			String strRoleName = "AutoRole" + strTimeText;

			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.rolesMandatoryFlds(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole
						.savAndVerifyRoles(selenium, strRoleName);
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

			/* 2. User U1 is created without selecting role R1. */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * Login as RegAdmin and navigate to Setup>>Users. 'Users List' page
			 * is displayed.
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
			
			// Navigating to user list Page

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Searching User U1 in UserList
			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
						strFILE_PATH);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);// Admin LogOut

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3:Click on the 'Edit' link associated with user U1 and
			 * select the role R1 from 'User Type & Roles' section and save the
			 * page.
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

				try {
					assertFalse(selenium
							.isElementPresent("SetUP.SetUpLink"));
					log4j.info("Set up link NOT present");
					
				} catch (AssertionError Ae) {
					
					log4j.info("Set up link  present" + Ae);
					strFuncResult = "Set up link  present " + Ae;
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				
				gstrResult = "PASS";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION ENDS~~~~~");
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68507";
			gstrTO = "Edit a user and deselect a right and verify "
					+ "that the user no longer has the right.";
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
	
	/*******************************************************************
	'Description	:Edit a user and select new right and verify that the
	'				 user has the right.
	'Precondition	:User U1 is created without selecting any role and 
	'				 right from 'Advanced Options' section. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-April-2012
	'Author			:QSG
	'-------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*********************************************************************/
	
	@Test
	public void testBQS68558() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application
		
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUser

		try {
			gstrTCID = "BQS-68558 ";
			gstrTO = "Edit a user and select new right and verify"
					+ " that the user has the right.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" +System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * User U1 is created without selecting any role and right from
			 * 'Advanced Options' section.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
				
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {

				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * Login as RegAdmin and navigate to Setup>>Users. 'Users List' page
			 * is displayed.
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
			
			// Navigating to user list Page

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Searching User U1 in UserList
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);// Admin LogOut

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3:Click on the 'Edit' link associated with user U1 and
			 * select the role R1 from 'User Type & Roles' section and save the
			 * page.
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

				selenium.selectWindow("");
				selenium.selectFrame("Data");

				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("SetUP.SetUpLink")));
					log4j.info("Set up link  present");
					
				} catch (AssertionError Ae) {
					
					log4j.info("Set up link NOT  present" + Ae);
					strFuncResult = "Set up link NOT present " + Ae;
					gstrReason = strFuncResult;
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
				
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsrLink")));
					log4j.info("The user can create the user");
					
				} catch (AssertionError Ae) {
					
					log4j.info("The user canNOT create the user" + Ae);
					strFuncResult = "The user canNOT create the user" + Ae;
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
									+ strUserName_1
									+ "']/parent::tr/td[1]/a[text()='Edit']"));
					log4j.info("The user can edit the user");

				} catch (AssertionError Ae) {

					log4j.info("The user canNOT edit the user" + Ae);
					strFuncResult = "The user canNOT edit the user" + Ae;
					gstrReason = strFuncResult;
				}

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
			gstrTCID = "BQS-68558";
			gstrTO = "Verify that users can be searched by providing a " +
					"search criteria in the user ";
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
	 * 'Description : Edit the event notification Preferences of User A while
	                  editing the user account of User A and verify that: 
	                  a. The selected notification methods for event templates 
	                  are retained when User A opens the event notification screen.
	                  b. User A receives event notification as selected. 
	 * 'Precondition: 1. User U1 is created with primary email, email
	                  and pager address. 
	                  2. Event template ET is created. 
	 * 'Arguments   :None
	 * 'Returns     :None 
	 * 'Date :3-May-2012  
	 * 'Author :QSG
	 * '-------------------------------------------------------------------
	 * 'Modified Date                                            Modified By '
	 * <Date>                                                    <Name>
	 *********************************************************************/

	@Test
	public void testBQS68601() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";// To store Function result
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		// CreateUsers
		EventNotification objEventNotification = new EventNotification();
		Preferences objPreferences=new Preferences();
		General objMail = new General();
		EventSetup objEve=new EventSetup();
		EventSetup objEventSetup=new EventSetup();
		
		try {
			gstrTCID = "BQS-68601 ";
			gstrTO = "Edit the event notification Preferences of User A while"
					+ " editing the user account of User A and verify that:"
					+ "a. The selected notification methods for event templates "
					+ "are retained when User A opens the event notification screen."
					+ "b. User A receives event notification as selected.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);// User Name
			String strLoginPassword = rdExcel.readData("Login", 3,2);// Password
			String strRegn = rdExcel.readData("Login", 3, 4);// String Variable to store Region value
			
			String strStartDate = "";
			
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
	
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strLoginPwd = rdExcel.readData("WebMailUser", 2, 2);
			
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
			
			//Event Template
			
			String strEveName = "Eve" + strTimeText;
			String strInfo = strEveName;
			
			/*boolean blnSave;
			String strResource="";*/
			
			
			String strUserName_1 = "AutoUsr_1" +System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			
			
			String strEveTemp="AutoET_"+strTimeText;
			String strETValue="";
			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("Event Temp Data", 2, 5,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("Event Temp Data", 2, 6,
					strFILE_PATH);
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			


			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 1. User U1 is created with primary email, email and pager
			 * address.
			 */	
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objLogin.navUserDefaultRgn(selenium, strRegn);
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
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditEventNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);

			
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEve.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// navigate to Event Template
				strFuncResult = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// fill the required fields in Create Event Template and save
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strStatTypeArr, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strETValue = objEve.fetchETInETList(selenium, strEveTemp);
				

				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch ET value";
				}

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Users, click on 'Edit'
			 * link associated with user U1. 'Edit User' screen is displayed.
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
			
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEventNotificationPreferencePage(selenium,
								strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Select web, email and pager for an event template ET and save
			 * the page. User is returned to the 'Edit User' screen.
			 */ 
			
			
			/*5 	Save the 'Edit User' page. 		'Users List' page is displayed. */
			try {

				assertEquals("", strFuncResult);
				String strTemplateValue[] = { strETValue };
				strFuncResult = objPreferences
						.selectEvenNofMethodsInEditUserPgeETValue(selenium,
								strTemplateValue, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Login as user U1 and navigate to Preferences>>Event
			 * Notification. The selected preferences are retained for template
			 * ET.
			 */
			
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objPreferences.navEventNotificationPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				

				strFuncResult = objPreferences
						.verifyEventNotifInMyEventNotifPref(selenium,
								strETValue, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				

				strFuncResult = objPreferences.navBackToPrefMenuPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 From RegAdmin, create an event under event template ET. User U1
			 * receives web, email and pager notification.
			 */ 
			
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strEveTemp, strEveName, strInfo, false);
				
				// get Start Date
				strFndStMnth = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMnt"));
				strFndStDay = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartDay"));
				strFndStYear = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartYear"));

				// get Start Time
				strFndStHr = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartHour"));
				strFndStMinu = selenium.getSelectedLabel(propElementDetails
						.getProperty("Event.CreateEve.StartMinut"));

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);


				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEveName + "']"));
					log4j.info("Event '" + strEveName
							+ "' is listed on 'Event Management' screen.");
					intResCnt++;
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEveName
							+ "' is NOT listed on 'Event Management' screen.";
					strFuncResult=gstrReason;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * STEP 7: Logout and login as user U1.<-> User U1 receives web,
			 * email and pager notification.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strFuncResult = objEventNotification.ackWebNotification(
						selenium, strEveName, strInfo, strStartDate);

				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				strSubjName = strEveName;
				strMsgBody1 = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strInfo + "\nRegion:  " + strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required.";
				strMsgBody2 = strInfo + "\nRegion:  " + strRegn;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strLoginPwd);
				
				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
							strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info(strMsgBody1);
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}
					selenium.selectWindow("");
					selenium.selectFrame("Data");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// verify result
				if (intResCnt == 2)
					gstrResult = "PASS";

			

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			
		} catch (Exception e) {
			gstrTCID = "BQS-68601";
			gstrTO = "Edit the event notification Preferences of User A while"
					+ " editing the user account of User A and verify that:"
					+ "a. The selected notification methods for event templates "
					+ "are retained when User A opens the event notification screen."
					+ "b. User A receives event notification as selected.";
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
	
	/*************************************************************************************
	'Description	:Verify that a user with the right 'Form - Do not participate in forms
	'				 for resources' does not receive completed form sent to a resource.
	                 right to setup resource.
	'Precondition	:1. A form F1 is configured as OLD form type
	                 (i.e. without selecting the check box 'New Form'), selecting 'User Initiate & Fill out Him/Herself' for 'Form Activation' and 'User to Individual Users' for 'Completed Form Delivery' and also select to receive completed form via email, pager and web.
	                 2. Question Q has been added to the questionnaire of old form F1.
	                 3. User A is created.
	                 4. User B is created by proving email and pager address. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS69664() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers
		Forms objForms = new Forms();
		try {
			gstrTCID = "BQS-69664 ";
			gstrTO = " Verify that a user with the right" +
					" 'Form - Do not participate in forms for resources' " +
					"does not receive completed form sent to a resource..";
			gstrResult = "FAIL";
			gstrReason = "";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTestData[] = new String[10];
			
			
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			
			// Data for searching user
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_" +strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" +strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" +strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValues[] = new String[1];
			
			// USER
			String strUserName1 = "AutoUsr_A" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strPrimaryEMail=rdExcel.readData("WebMailUser", 2, 1);
			String strEMail=rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue=rdExcel.readData("WebMailUser", 2, 1);
			
			
			//Search user data
			
			//form
			String strFormTempTitleOF = "AutoFm_" +strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "User To Individual Users";
		    String strQuestion="QS_"+strTimeText;
		    String strDescription="Automation";
		    String strquesTypeID="Free text field";   

			/*precondition:1. 
			1. A form F1 is configured as OLD form type (i.e. without selecting the check box 'New Form'),
			selecting 'User Initiate & Fill out Him/Herself' for 'Form Activation' and 'User to Individual Users' for 
			'Completed Form Delivery' and also select to receive completed form via email, pager and web.
			2. Question Q has been added to the questionnaire of old form F1.
			3. User A is created.
			4. User B is created by proving email and pager address and also by proving
			 �Associated with� right or �Update status� or �Run Report� right for a resource RS
			 */
			 
			   log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
			     + " EXECUTION STATRTS~~~~~");
			
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
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.navStatusTypList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue = "Number";
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							selenium, strStatusTypeValue, statTypeName,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
							statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatusTypeValues[0] = objStatusTypes
							.fetchSTValueInStatTypeList(selenium, statTypeName);

					if (strStatusTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.navResourceTypList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
							selenium, strResrcTypName, strStatusTypeValues[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
							strResrcTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRsTypeValues[0] = objResourceTypes
							.fetchResTypeValueInResTypeList(selenium,
									strResrcTypName);

					if (strRsTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// RS1
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navResourcesList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navToCreateResourcePage(selenium);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.createResourceWithMandFields(
							selenium, strResource, strAbbrv, strResrcTypName,
							"Hospital", "FN", "LN");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
							strResource);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					// assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndVerifyResource(selenium,
							strResource, strHavBed, "", strAbbrv, strResrcTypName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strResVal = objResources.fetchResValueInResList(selenium,
							strResource);

					if (strResVal.compareTo("") != 0) {
						strFuncResult = "";
						strRSValues[0] = strResVal;
					} else {
						strFuncResult = "Failed to fetch Resource value";
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
			// user1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strInitPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName2, strInitPwd, strInitPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strPrimaryEMail,
						strEMail, strPagerValue, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strResVal, true, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						selenium, strFormTempTitleOF, strDescription,
						strFormActiv, strComplFormDel, true, true, true, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users. 'Users
			 * List' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3:Click on the 'Edit' link associated with user B and select
			 * 'Form - Do not participate in forms for resources' right from
			 * 'Advanced Options' section and save the page. 'Users List' page
			 * is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.DoNotParticipateInFomForResources"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 4:Navigate to Form>>Form security and click on 'Security'
			 * associated with the form. 'Form Security Settings: F1' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP :Select user A to activate the form, then save the page.
			 * 'Form Security Settings' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName1, strFormTempTitleOF, true, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strUserName2 + "/"
						+ strInitPwd;
				strTestData[3] = strResource;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 6th step";
				strTestData[6] = strQuestion;
				strTestData[9] = strResrcTypName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

		} catch (Exception e) {
			gstrTCID = "BQS-69664 ";
			gstrTO = " Verify that a user with the right" +
					" 'Form - Do not participate in forms for resources' " +
					"does not receive completed form sent to a resource..";
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
'Description		:Verify that a user with the right 'Form - Do not participate in forms for resources' does not receive blank form sent to a resource.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:9/5/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS69670() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Forms objForms = new Forms();
		try {
			gstrTCID = "69670"; // Test Case Id
			gstrTO = " Verify that a user with the right 'Form - Do not participate in forms for resources' does not receive blank form sent to a resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			String strUserNameC = "Usr_C" + System.currentTimeMillis();
			String strUsrFulNameC = strUserNameC;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Other To Fill Out";
			String strComplFormDel = "User To Individual Resources";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";
			/*
			 * STEP : Action:Precondition: 1. User A and user C are created. 2.
			 * User B is created selecting 'Form - User may activate forms'
			 * right. Expected Result:No Expected Result
			 */
			// 416244
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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

			// USER A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER C
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameC,
								strInitPwd, strConfirmPwd, strUsrFulNameC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameC, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin and navigate to Form>>>Configure
			 * Forms. Expected Result:'Form Configuration' screen is displayed.
			 */
			// 416245
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a form 'F1' selecting: 1. 'User initiate and
			 * other to fill out' for 'Form Activation' and 'User to individual
			 * Resources' for 'Completed form delivery' 2.Do not select
			 * "New Form" Checkbox (To create an OLD form) 3.Select to receive
			 * completed form via email, pager and web 4.Click on 'Next'
			 * Expected Result:'Users to Fill Out Form' screen is displayed.
			 */
			// 416246
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strDescription, strFormActiv,
						strComplFormDel, true, true, true, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Do not select any users in 'Users to Fill Form'
			 * page and then click on 'Next'. Expected Result:'Resources to Fill
			 * Form' screen is displayed.
			 */
			// 416247
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Resource 'RS' in 'Resource to Fill Form'
			 * page and then click on 'Next'. Expected Result:Form 'F1' is
			 * listed in the 'Form Configuration' page.
			 */
			// 416248
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectResourcesForForm(selenium,
						strResource, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Add questions to the questionnaire Expected
			 * Result:No Expected Result
			 */
			// 416249
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Form>>Form security and click on
			 * 'Security' associated with the form 'F1'. Expected Result:'Form
			 * Security Settings: F1' screen is displayed.
			 */
			// 416250
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select user B to activate the form 'F1' and save
			 * the page. Expected Result:'Form Security Settings' page is
			 * displayed.
			 */
			// 416251

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameB, true, true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Users. Click on the 'Edit' link
			 * associated with user A and select 'Update status' right for a
			 * resource RS, select 'Form - Do not participate in forms for
			 * resources' right from 'Advanced Options' section and save the
			 * page. Expected Result:'Users List' page is displayed.
			 */
			// 416252
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValues[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.DoNotParticipateInFomForResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * STEP : Action:Login as user B and navigate to Form>>Activate
			 * forms. Expected Result:Form 'F1' is listed in the 'Activate
			 * forms'.
			 */
			// 416253

			/*
			 * STEP : Action:Click on 'Send form', select Resource 'RS' to fill
			 * out the form 'F1', resource 'RS' to receive completed form 'F1'
			 * and click on 'Activate form'. Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 416254

			/*
			 * STEP : Action:Login as User A who has 'Update status' right on
			 * the resource 'RS'. Expected Result:User A do not receive the
			 * blank form F1 sent to the resource RS.
			 */
			// 416255

			String strTestData[] = new String[10];
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserNameA + "/" + strInitPwd + " and  "
						+ strUserNameB + "/" + strInitPwd + " and  "
						+ strUserNameC + "/" + strInitPwd;
				strTestData[3] = strResource;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 10th step";
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69670";
			gstrTO = "Verify that a user with the right 'Form - Do not participate " +
					"in forms for resources' does not receive blank form sent to a resource.";
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
'Description		:Verify that a user with 'Update status' right on a resource receives the blank form sent to the resource.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:9/6/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

@Test
public void testBQS69665() throws Exception {
	
	String strFuncResult = "";
	boolean blnLogin = false;
	Login objLogin = new Login();
	CreateUsers objCreateUsers = new CreateUsers();
	StatusTypes objStatusTypes = new StatusTypes();
	ResourceTypes objResourceTypes = new ResourceTypes();
	Resources objResources = new Resources();
	Forms objForms = new Forms();
		try {
			gstrTCID = "69665"; // Test Case Id
			gstrTO = " Verify that a user with 'Update status' right on a resource" +
					" receives the blank form sent to the resource.";// Test
																																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			String strUserNameC = "Usr_C" + System.currentTimeMillis();
			String strUsrFulNameC = strUserNameC;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Other To Fill Out";
			String strComplFormDel = "User To Individual Resources";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";

			/*
			 * STEP : Action:Precondition: 1. User A is created. 2. User B is
			 * created selecting 'Form - User may activate forms' right. 3. User
			 * C is created providing email and pager address. Expected
			 * Result:No Expected Result
			 */
			// 416178

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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

			// USER A
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER B
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER C
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameC,
								strInitPwd, strConfirmPwd, strUsrFulNameC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strPrimaryEMail,
						strEMail, strPagerValue, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameC, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Form>>>Configure
			 * Forms. Expected Result:'Form Configuration' screen is displayed.
			 */
			// 416181
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a form 'F1' selecting: 1. 'User initiate and
			 * other to fill out' for 'Form Activation' and 'User to individual
			 * Resources' for 'Completed form delivery' 2.Do not select
			 * "New Form" Checkbox (To create an OLD form) 3.Select to receive
			 * completed form via email, pager and web 4.Click on 'Next'
			 * Expected Result:'Users to Fill Out Form' screen is displayed.
			 */
			// 416184

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strDescription, strFormActiv,
						strComplFormDel, true, true, true, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Do not select any users in 'Users to Fill Form'
			 * page and then click on 'Next'. Expected Result:'Resources to Fill
			 * Form' screen is displayed.
			 */
			// 416185
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Resource 'RS' in 'Resource to Fill Form'
			 * page and then click on 'Next'. Expected Result:Form 'F1' is
			 * listed in the 'Form Configuration' page.
			 */
			// 416186
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectResourcesForForm(selenium,
						strResource, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Add questions to the questionnaire Expected
			 * Result:No Expected Result
			 */
			// 416187
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Form>>Form security and click on
			 * 'Security' associated with the form 'F1'. Expected Result:'Form
			 * Security Settings: F1' screen is displayed.
			 */
			// 416190
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select user B to activate the form 'F1' and save
			 * the page. Expected Result:'Form Security Settings' page is
			 * displayed.
			 */
			// 416193
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameB, true, true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Users. Click on the 'Edit' link
			 * associated with user A and select 'Update status' right for a
			 * resource RS and save the page. Expected Result:'Users List' page
			 * is displayed.
			 */
			// 416196

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValues[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			String strTestData[] = new String[10];
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserNameA + "/" + strInitPwd + " and  "
						+ strUserNameB + "/" + strInitPwd + " and  "
						+ strUserNameC + "/" + strInitPwd;
				strTestData[3] = strResource;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 10th step";
				strTestData[6] = strQuestion;
				strTestData[9] = strResrcTypName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69665";
			gstrTO = "Verify that a user with 'Update status' " +
					"right on a resource receives" +
					" the blank form sent to the resource.";
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

		
	
	@Test
	public void testBQS66038() throws Exception {

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-66038 ";
			gstrTO = "Verify that a user with 'Update status' right on a resource"
					+ " receives expired status notification when the status of "
					+ "the resource expires.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");

			int intShiftTime = Integer.parseInt(propEnvDetails.getProperty("ShiftTime"));

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String StatusTime = "";
			String strUpdTime = "";
			String strUpdTime_Shift = "";
			int intTimeDiffOutPut = 0;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurDate = "";

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// Status Type
			String strStatusTypeValueExpire = "Multi";
			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;

			String strStatusTypeValueShift = "Multi";
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			strStatusValueShift[0] = "";
			strStatusValueShift[1] = "";

			strStatusValueExpire[0] = "";
			strStatusValueExpire[1] = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = strTmText;
			String strExpHr = "00";
			String strExpMn = "05";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserNameA;

			String strRoleValue = "";

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			strSTvalue[1] = "";
			String strSubjName = "";

			String strMsgBodyExpireEmail = "";
			String strMsgBodyExpirePager = "";
			String strMsgBodyShiftEmail = "";
			String strMsgBodyShiftPager = "";
			String strMsgBodyShiftEmailAnother = "";
			String strMsgBodyShiftPagerAnother = "";

			int intEMailRes = 0;
			int intPagerRes = 0;

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";

			int intPositionShift = 0;
			int intPositionExpire = 0;

			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				selenium.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameExpire + "']"));

					log4j.info("Status type " + statTypeNameExpire
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								selenium, statTypeNameExpire);
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
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								selenium, statTypeNameExpire,
								strStatusNameExpire1, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
								selenium, statTypeNameExpire,
								strStatusNameExpire2, strStatusTypeValueExpire,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameExpire, strStatusNameExpire1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameExpire, strStatusNameExpire2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueExpire[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameExpire
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameExpire
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				// Save status type
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				selenium.click("link=Return to Status Type List");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeNameShift + "']"));

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								selenium, statTypeNameShift);
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
								selenium, statTypeNameShift,
								strStatusNameShift1, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								selenium, statTypeNameShift,
								strStatusNameShift2, strStatusTypeValueShift,
								strStatTypeColor, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameShift, strStatusNameShift1);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[0] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchStatValInStatusList(selenium,
								statTypeNameShift, strStatusNameShift2);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strStatusValueShift[1] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeNameShift
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
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
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(
						selenium, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
						selenium, strUserNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselEmailInSysNotfPref(
						selenium, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselPagerInSysNotfPref(
						selenium, "EXPIRED_STATUS", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savAndNavToSySNotiPrefPage(selenium,strUserNameA);
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

				strFuncResult = objST.editStatusTypePage(selenium,
						statTypeNameShift, strStatusTypeValueShift);

				StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							selenium, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(selenium,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
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

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameExpire, strSTvalue[0],
						strStatusNameExpire1, strStatusValueExpire[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource, statTypeNameShift, strSTvalue[1],
						strStatusNameShift1, strStatusValueShift[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 310,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("//span[text()='"
						+ strStatusNameExpire1
						+ "']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime, 5,
						"HH:mm");
				strUpdTime = strAdUpdTime;

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time: " + strUpdTime);

				strMsgBodyExpireEmail = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameExpire
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBodyExpirePager = "EMResource expired status: "
						+ strResource + ". " + statTypeNameExpire
						+ " status expired " + strCurDate + " " + strUpdTime
						+ ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, intTimeDiffOutPut, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strUpdTime = strUpdTime_Shift;
				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");

				log4j.info("Shift Time: " + strUpdTime_Shift);

				strMsgBodyShiftEmail = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strUpdTime
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBodyShiftEmailAnother = "For "
						+ strUsrFulName
						+ "\nRegion: "
						+ strRegn
						+ "\n\nThe "
						+ statTypeNameShift
						+ " status for "
						+ strResource
						+ " expired "
						+ strCurDate
						+ " "
						+ strAdUpdTimeShift
						+ ".\n\nClick the link below to go to the EMResource login screen:"
						+

						"\n\n        "+propEnvDetails.getProperty("MailURL")
						+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
						+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications,"
						+ " log onto EMResource and uncheck the notification fields on the User Info screen.";

				strMsgBodyShiftPager = "EMResource expired status: "
						+ strResource + ". " + statTypeNameShift
						+ " status expired " + strCurDate + " " + strUpdTime
						+ ".";

				strMsgBodyShiftPagerAnother = "EMResource expired status: "
						+ strResource + ". " + statTypeNameShift
						+ " status expired " + strCurDate + " "
						+ strAdUpdTimeShift + ".";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(60000);
				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int i = 0; i < 2; i++) {
				try {
					assertTrue(strFuncResult.equals(""));

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBodyShiftPager)
								|| strMsg.equals(strMsgBodyShiftPagerAnother)) {
							intPagerRes++;
							log4j.info(strMsgBodyShiftPager
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBodyExpirePager)) {
							intPagerRes++;
							log4j.info(strMsgBodyExpirePager
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

			}

			for (int i = 0; i < 4; i++) {
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status Notification: "
							+ strResource;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBodyShiftEmail)
								|| strMsg.equals(strMsgBodyShiftEmailAnother)) {
							intEMailRes++;
							log4j.info(strMsgBodyShiftEmail
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBodyExpireEmail)) {
							intEMailRes++;
							log4j.info(strMsgBodyExpireEmail
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66038";
			gstrTO = "Verify that a user with 'Update status' right on a resource "
					+ "receives expired status notification when the status of the"
					+ " resource expires.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
			selenium.selectWindow("");
		}

	}
	
	@Test
	public void testBQS105969() throws Exception {
		String strFuncResult = "";
		String strFuncResultRefresh = "";
		Login objLogin = new Login();// object of class Login

		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		General objMail = new General();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "BQS-105969";
			gstrTO = "Verify that a user with 'Update status' right on a resource "
					+ "receives status update prompt when the status of the resource expires.";

			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));
			String StatusTime = "";
			String strUpdTime_Shift = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strStatusTypeValueExpire = "Multi";
			String strStatusTypeValueShift = "Multi";

			String statTypeNameExpire = "AutoMSTExpire_" + strTimeText;
			String statTypeNameShift = "AutoMSTShift_" + strTimeText;

			String strStatusNameExpire1 = "SaE" + strTimeText;
			String strStatusNameExpire2 = "SbE" + strTimeText;

			String strStatusNameShift1 = "SaS" + strTimeText;
			String strStatusNameShift2 = "SbS" + strTimeText;

			String strStatusValueExpire[] = new String[2];
			String strStatusValueShift[] = new String[2];

			String strSTvalue[] = new String[2];
			strSTvalue[0] = "";

			String strExpHr = "00";
			String strExpMn = "05";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String[] strArFunRes = new String[5];
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenTimeMinTotal = "";

			String strGenTimeHrs_1 = "";
			String strGenTimeMin_1 = "";
			String strGenTimeMinTotal_1 = "";
			int intTimeDiffOutPut = 0;

			int intPositionShift = 0;
			int intPositionExpire = 0;

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			// Navigate user default region
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
				strFuncResult = objRole.navRolesListPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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

			// Navigate to status type list

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueExpire, statTypeNameExpire,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.savAndVerifyMultiST(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeNameExpire);
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
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire1,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
						selenium, statTypeNameExpire, strStatusNameExpire2,
						strStatusTypeValueExpire, strStatTypeColor, strExpHr,
						strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeNameExpire, strStatusNameExpire2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValueExpire[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValueShift, statTypeNameShift,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRolesValue = { strRoleValue };
				strFuncResult = objST.slectAndDeselectRoleInST(selenium, false,
						false, strRolesValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals("", strFuncResult);

					strFuncResult = objST.savAndVerifyMultiST(selenium,
							statTypeNameExpire);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(selenium,
							statTypeNameShift);
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
					strFuncResult = objST.createSTWithinMultiTypeST(selenium,
							statTypeNameShift, strStatusNameShift1,
							strStatusTypeValueShift, strStatTypeColor, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeST(selenium,
							statTypeNameShift, strStatusNameShift2,
							strStatusTypeValueShift, strStatTypeColor, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchStatValInStatusList(selenium,
							statTypeNameShift, strStatusNameShift1);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strStatusValueShift[0] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchStatValInStatusList(selenium,
							statTypeNameShift, strStatusNameShift2);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strStatusValueShift[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to resource type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fill resource type fields
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Save and verify resource type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to resource list page
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate user default region
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
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
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.editStatusTypePage(selenium,
						statTypeNameShift, "Multi");

				StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.provideShiftTimeForStatusTypes(
							selenium, strStatusTime[0], strStatusTime[1]);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(selenium,
						statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
				strGenTimeMinTotal = strGenTimeHrs + ":" + strGenTimeMin;
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
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Logout and login as User A. User A receives status update
			 * prompt.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeNameShift);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Click on 'Remind me in 10 minutes' at time say X 'Region
			 * Default' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.remindMeAndCheckPrompt(selenium,
						statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes3 = new String[5];
			String strRemindTime = "";

			try {
				assertEquals("", strFuncResult);

				strArFunRes3 = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes3[4];

				strRemindTime = dts.addTimetoExisting(strArFunRes3[2] + ":"
						+ strArFunRes3[3], 10, "HH:mm");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameExpire,
						statTypeNameShift };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource + " \nStatus is Overdue");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusTypeForOverdue(
						selenium, strSTvalue[0], strStatusValueExpire[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusTypeForOverdue(
						selenium, strSTvalue[1], strStatusValueShift[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strStatArr[] = { strStatusNameExpire1,
						strStatusNameShift1 };
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strStatArr);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					strFuncResult = objMail.refreshPage(selenium);
				} catch (Exception e) {

				}
				strArFunRes = objMail.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];

				strGenTimeHrs_1 = strArFunRes[2];
				strGenTimeMin_1 = strArFunRes[3];
				strGenTimeMinTotal_1 = strGenTimeHrs_1 + ":" + strGenTimeMin_1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(300000);

				intPositionShift = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameShift1
								+ "']/preceding-sibling::span").intValue();
				intPositionShift = intPositionShift + 2;

				intPositionExpire = selenium.getXpathCount(
						"//div[@class='emsCenteredLabel'][text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ "text()='" + strStatusNameExpire1
								+ "']/preceding-sibling::span").intValue();
				intPositionExpire = intPositionExpire + 2;

				String strElementIdExpire = "//div[@class='emsCenteredLabel']"
						+ "[text()='" + strResource
						+ "']/following-sibling::div/span[" + intPositionExpire
						+ "][@class='overdue']";

				strFuncResult = objMail.waitForMailNotification(selenium, 30,
						strElementIdExpire);

				try {
					assertEquals("", strFuncResult);
					try {
						strFuncResult = objMail.refreshPage(selenium);
					} catch (Exception e) {

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				intTimeDiffOutPut = dts.getTimeDiffWithTimeFormatInOurTime(
						strGenTimeMinTotal_1, strGenTimeMinTotal, "HH:mm",
						60000);

				if ((intShiftTime - intTimeDiffOutPut) > 0) {
					intTimeDiffOutPut = intShiftTime - intTimeDiffOutPut;
					intTimeDiffOutPut = intTimeDiffOutPut + 1;
					intTimeDiffOutPut = intTimeDiffOutPut * 60;
					intTimeDiffOutPut = intTimeDiffOutPut * 1000;
					Thread.sleep(intTimeDiffOutPut);

					try {
						assertEquals("", strFuncResult);

						String strElementIdShift = "//div[@class='emsCenteredLabel']"
								+ "[text()='"
								+ strResource
								+ "']/following-sibling::div/span["
								+ intPositionShift + "][@class='overdue']";

						strFuncResult = objMail.waitForMailNotification(
								selenium, 30, strElementIdShift);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeNameExpire);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Update Status' prompt for " + statTypeNameExpire
						+ " is NOT displayed or it is not expanded",
						strFuncResult);

				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeNameShift);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Update Status' prompt for " + statTypeNameShift
						+ " is NOT displayed or it is not expanded",
						strFuncResult);

				String strSnapTimeAr[] = selenium.getText("css=#statusTime")
						.split(" ");

				int intWaitTimeDif = dts.getTimeDiffWithTimeFormatInOurTime(
						strRemindTime, strSnapTimeAr[2], "HH:mm", 60000);
				Thread.sleep((intWaitTimeDif) * 60 * 1000);

				objMail.refreshPage(selenium);
				strFuncResult = "";
				try {
					assertEquals("", strFuncResult);
					strFuncResultRefresh = objViews.checkUpdateStatPrompt(
							selenium, strResource, statTypeNameExpire);

					if (strFuncResultRefresh
							.equals("'Update Status' prompt for "
									+ statTypeNameExpire
									+ " is NOT displayed or it is not expanded")) {
						log4j.info("wait for 1 mins");
						try {
							assertEquals("", strFuncResult);

							objMail
									.waitForMailNotification(selenium, 60,
											"//span[@class='overdue'][text()='Status is Overdue']");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						strFuncResultRefresh = objViews.checkUpdateStatPrompt(
								selenium, strResource, statTypeNameExpire);

						if (strFuncResultRefresh
								.equals("'Update Status' prompt for "
										+ statTypeNameExpire
										+ " is NOT displayed or it is not expanded")) {
							objMail
									.waitForMailNotification(selenium, 60,
											"//span[@class='overdue'][text()='Status is Overdue']");

							strFuncResultRefresh = objViews
									.checkUpdateStatPrompt(selenium,
											strResource, statTypeNameExpire);

							if (strFuncResultRefresh.equals("")) {

							} else {
								log4j
										.info("Update status prompt is NOT"
												+ " displayed even after wait for 12 mins");
							}
						}

					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeNameExpire);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource, statTypeNameShift);
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
			gstrTCID = "BQS-105969";
			gstrTO = "Verify that a user with 'Update status' right on a resource"
					+ " receives status update prompt when the status of the resource expires.";
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
	
	//start//testBQS112340//
	/************************************************************************************
	'Description	:Verify that user with 'Edit Resource Only' right and update right on
	                 a resource can edit sub-resources for their facility.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/2/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                           Modified By
	'Date					                                               Name
	*************************************************************************************/

	@Test
	public void testBQS112340() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		General objGeneral = new General();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "BQS-112340";
			gstrTO = "Verify that user with 'Edit Resource Only' right and update right on a"
					+ " resource can edit sub-resources for their facility.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			String strTmText = dts.getCurrentDate("HHmm");															
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRS2_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strSubResource2 = "AutoSRs2_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "A" + strTmText;
			String strRSValue[] = new String[4];
			String strState = "Alabama";
			String strCountry = "Barbour County";
			// Sub Resource Icon
			String strIconImg = rdExcel.readInfoExcel("ResourceIcon", 3, 2,
					strFILE_PATH);
			String strEditSubResource = "EditAutoSRs1_" + strTimeText;
			String strEditAbbrv = "B" + strTmText;			
			String strEditStandResType = "Hospital";
			String strEditContFName = "Editauto";
			String strEditContLName = "Editqsg";	
			
			String strEditSubResource1 = "Edit2SRs1_" + strTimeText;
			String strEditAbbrv1 = "c" + strTmText;			
			String strEditStandResType1 = "Ambulance";
			String strEditContFName1 = "Editau1";
			String strEditContLName1 = "Edqsg1";	
						
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

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			//Section
			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { strStatType1, strStatType2 };

		log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");

		/*
		 * STEP : Action:Precondition:Status type ST1 and ST2 are created in region RG1.
				Resource Type (Normal) RT1 is created selecting status type ST1.
				Resource RS1 and RS2 are created under resource type RT1.
				Sub-Resource type SRT1 is created selecting status type ST2.
				Sub-resource SRS1 and SRS2 are created under the parent resource RS1 and RS2 respectively
				 selecting sub-resource type sRT1
				User U1 is created with the following:
				1. 'View Resource' and 'Update resource' right on resource RS1.
				2. 'View resource' right on RS2
				3. Role to view status types ST1 and ST2
				4. 'Edit Resources Only' right
				5. View V1 is created selecting ST1, ST2, RS1 and RS2
				6. User has configured sub resource selecting 'sRT1' and 'ST2' 
				 Expected Result:No Expected Result
		 */
		// 625815

				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);

				try {
					blnLogin = true;
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

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strNSTValue, strStatType1,
							strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
							seleniumPrecondition, strStatType1);
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
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strNSTValue, strStatType2,
							strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
							seleniumPrecondition, strStatType2);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strSTvalue[1] = strStatValue;
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
					strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
							seleniumPrecondition, strResrctTypName,
							"css=input[name='statusTypeID'][value='"
									+ strSTvalue[0] + "']");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.saveAndvrfyResType(
							seleniumPrecondition, strResrctTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes
							.fetchResTypeValueInResTypeList(seleniumPrecondition,
									strResrctTypName);
					if (strFuncResult.compareTo("") != 0) {

						strRTValue[0] = strFuncResult;
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch resource type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// RS1
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navResourcesList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs
							.navToCreateResourcePage(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createResourceWithMandFields(
							seleniumPrecondition, strResource1, strAbbrv,
							strResrctTypName, strStandResType, strContFName,
							strContLName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndNavToAssignUsr(
							seleniumPrecondition, strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifyResourceInRSList(
							seleniumPrecondition, strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRSValue[0] = objRs.fetchResValueInResList(
							seleniumPrecondition, strResource1);
					if (strRSValue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch Resource value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// RS2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navResourcesList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs
							.navToCreateResourcePage(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createResourceWithMandFields(
							seleniumPrecondition, strResource2, strAbbrv,
							strResrctTypName, strStandResType, strContFName,
							strContLName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndNavToAssignUsr(
							seleniumPrecondition, strResource2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifyResourceInRSList(
							seleniumPrecondition, strResource2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRSValue[1] = objRs.fetchResValueInResList(
							seleniumPrecondition, strResource2);
					if (strRSValue[1].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch Resource value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// SRT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes
							.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
							seleniumPrecondition, strSubResrctTypName,
							"css=input[name='statusTypeID'][value='"
									+ strSTvalue[1] + "']");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSubResourceType(
							seleniumPrecondition, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.saveAndvrfyResType(
							seleniumPrecondition, strSubResrctTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes
							.fetchResTypeValueInResTypeList(seleniumPrecondition,
									strSubResrctTypName);
					if (strFuncResult.compareTo("") != 0) {

						strRTValue[1] = strFuncResult;
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch resource type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				//SRS1
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navResourcesList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
							strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
							strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
							strSubResource1, strAbbrv, strSubResrctTypName,
							strStandResType, false, strContFName, strContLName, "",
							"");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifySubResourceInRSList(
							seleniumPrecondition, strSubResource1,strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				//SRS2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navResourcesList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
							strResource2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
							strResource2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
							strSubResource2, strAbbrv, strSubResrctTypName,
							strStandResType, false, strContFName, strContLName, "",
							"");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifySubResourceInRSList(
							seleniumPrecondition, strSubResource2,strResource2);
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
					String[] strSTsvalue = { strSTvalue[0], strSTvalue[1] };
					strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
							seleniumPrecondition, strRoleName, strRoleRights,
							strSTsvalue, true, strSTsvalue, false, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
							strRoleName);
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
				// View
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.navRegionViewsList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] strSTvalues = { strSTvalue[0], strSTvalue[1] };
					String[] strRSValues = { strRSValue[0], strRSValue[1] };
					strFuncResult = objViews.createView(seleniumPrecondition,
							strViewName, strVewDescription, strViewType, true,
							false, strSTvalues, false, strRSValues);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				// Section
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.navToEditResDetailViewSections(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.dragAndDropSTtoSection(seleniumPrecondition,
									strArStatType1, strSection1, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.navToEditResDetailViewSections(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
							strSection1);
					if (strSectionValue.compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch section value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// USER
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
							seleniumPrecondition, strRoleValue, true);
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
							false, false, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
					strFuncResult = objCreateUsers.advancedOptns(
							seleniumPrecondition, strOptions, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
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

				// 6. User has configured sub resource selecting 'sRT1' and 'ST2'
				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
							strUserName_1, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					blnLogin = true;
					strFuncResult = objViews
							.navRegionViewsList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.navToEditResDetailViewSections(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.navToEditSubResDetailViewSections(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strstValues[] = { strSTvalue[1] };
					strFuncResult = objViews
							.selectStatusTypesInEditSubResourceSectionsPge(
									seleniumPrecondition, strSubResrctTypName,
									strRTValue[1], strstValues, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
					blnLogin = false;
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
		/*
		 * STEP : Action:Login as user U1, navigate to Setup >> Resources
		 * Expected Result:Resources RS1 is listed on 'Resource List'
		 * screen. Resources RS2 is not listed on 'Resource List' screen. 
		 */
		// 625816

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.login(selenium, strUserName_1,
							strInitPwd);
					blnLogin = true;
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
					strFuncResult = objRs.VerifyResource(selenium, strResource1,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.VerifyResource(selenium, strResource2,
							false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

		/*
		 * STEP : Action:Click on 'Sub-Resources' link corresponding to resource RS1.
		 *  Expected Result:'Sub-Resource List for < Resource name (RS1) >' screen is displayed 
		 *  'Create New Sub-Resource' button is available.SRS1 is listed.
		 */
		// 625817
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifySubResource(selenium, strSubResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP : Action:Click on 'Edit' link corresponding to sub-resource SRS1 
		 *  Expected Result:'Edit Sub-Resource of < resource name RS1 >' screen is displayed. 
		 */
		// 625889

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageWithEditLink(
						selenium, strResource1, strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP :Action: Edit mandatory data and click on 'Save' 
		 * Expected Result:Newly updated values are displayed appropriately under their respective columns.
		 */
		// 625890
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(selenium,
						strEditSubResource, strEditAbbrv, strEditStandResType,
						true, strEditContFName, strEditContLName, strState,
						strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium, strEditSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifySubResDataInRSListPge(selenium,
						strEditSubResource, strEditAbbrv, strSubResrctTypName, strIconImg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP :Action:Navigate to Views >> V1 and click on resource RS1  
		 * Expected Result:'View Resource Detail' screen is displayed.
		'Sub Resource' section is displayed where Sub-resource 'SRS1' is displayed under 'sRT1' and 'ST2'.
		'Create New Sub-Resource' button is available. 
		 */
		// 625891
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String[] strStatTypep = { strStatType2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strEditSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on sub resource 'SRS1' 
		 * Expected Result:'View Resource Detail' screen of 'SRS1' is displayed. 
		 */
		// 628747
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToSubResViewResourceDetailPage(
						selenium, strEditSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailOfSubRes(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Edit all the mandatory data and save. 
		 * Expected Result:	User is directed to the 'View Resource Detail' screen of 'SRS1'.
  		   All the edited data are retained. 
		 */
		// 628748
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(selenium,
						strEditSubResource1, strEditAbbrv1, strEditStandResType1,
						true, strEditContFName1, strEditContLName1, strState,
						strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndViewResDetailScreenOfSubRS(
						selenium, strEditSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = {
						{ "Type:", strSubResrctTypName },
						{ "Contact:", strEditContFName1 + " " + strEditContLName1 } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditSubResource1, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Once again navigate to Views >> V1 and click on resource RS2 
		 *  Expected Result:'View Resource Detail' screen is displayed.
           'Create New Sub-Resource' button is not available. 
		 */
		// 628749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent(propElementDetails.
						getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub-Resource' button is not available under the 'Sub-Resources' section");
			} catch (AssertionError Ae) {
				log4j.info("'Create New Sub-Resource' button is available under the 'Sub-Resources' section");
				gstrReason = strFuncResult+"'Create New Sub-Resource' button is  available under the 'Sub-Resources' section";
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
			gstrTCID = "BQS-112340";
			gstrTO = "Verify that user with 'Edit Resource Only' right and update right on a"
					+ " resource can edit sub-resources for their facility.";
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
	
  // end//testBQS112340//
	
	//start//testBQS112341//
	/************************************************************************************
	'Description	:Verify that user with 'Setup Resources' right and update right on a 
	                  resource can edit sub-resources under it.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/2/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                           Modified By
	'Date					                                               Name
	*************************************************************************************/

	@Test
	public void testBQS112341() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		General objGeneral = new General();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "BQS-112341";
			gstrTO = "Verify that user with 'Setup Resources' right and update right on a resource " +
					"can edit sub-resources under it.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			String strTmText = dts.getCurrentDate("HHmm");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRS2_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strSubResource2 = "AutoSRs2_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "A" + strTmText;
			String strRSValue[] = new String[4];
			String strState = "Alabama";
			String strCountry = "Barbour County";

			String strEditSubResource = "EditAutoSRs1_" + strTimeText;
			String strEditAbbrv = "B" + strTmText;
			String strEditStandResType = "Hospital";
			String strEditContFName = "Editauto";
			String strEditContLName = "Editqsg";
			
			String strEditSubResource2 = "EditAutoSRs2_" + strTimeText;
			String strEditAbbrv2 = "B" + strTmText;
			String strEditStandResType2 = "Hospital";
			String strEditContFName2 = "Edit2auto";
			String strEditContLName2 = "Edit2qsg";
			// Sub Resource Icon
			String strIconImg = rdExcel.readInfoExcel("ResourceIcon", 3, 2,
					strFILE_PATH);
			String strEditSubResource1 = "Edit2SRs1_" + strTimeText;
			String strEditAbbrv1 = "c" + strTmText;
			String strEditStandResType1 = "Ambulance";
			String strEditContFName1 = "Editau1";
			String strEditContLName1 = "Edqsg1";

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

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// Section
			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { strStatType1, strStatType2 };

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");

		/*
		 * STEP : Action:Precondition:Status type ST1 and ST2 are created in region RG1.
			Resource Type (Normal) RT1 is created selecting status type ST1.
			Resource RS1 and RS2 are created under resource type RT1.
			Sub-Resource type SRT1 is created selecting status type ST2.
			Sub-resource SRS1 and SRS2 are created under the parent resource RS1 and RS2 respectively
			 selecting sub-resource type SRT1
			User U1 is created with the following:
			1. 'View Resource' and 'Update resource' right on resource RS1.
			2. 'View resource' right on RS2
			3. Role to view status types ST1 and ST2
			4. 'Setup Resources' right
			5. View V1 is created selecting ST1, ST2, RS1 and RS2
			6. User has configured sub resource selecting 'sRT1' and 'ST2' 
				 Expected Result:No Expected Result
		 */
		// 625815

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SRS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource2, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2, strResource2);
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
				String[] strSTsvalue = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTsvalue, true, strSTsvalue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRoleName);
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
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0], strSTvalue[1] };
				String[] strRSValues = { strRSValue[0], strRSValue[1] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalues, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strArStatType1, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						seleniumPrecondition, strRoleValue, true);
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
						false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 6. User has configured sub resource selecting 'sRT1' and 'ST2'
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[1] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								seleniumPrecondition, strSubResrctTypName,
								strRTValue[1], strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
		/*
		 * STEP : Action:Login as user U1, navigate to Setup >> Resources
		 * Expected Result:Resources RS1 is listed on 'Resource List'
		 * screen. Resources RS2 are listed on 'Resource List' screen. 
		 */
		// 625816

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
				blnLogin = true;
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
				strFuncResult = objRs.VerifyResource(selenium, strResource1,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifyResource(selenium, strResource2,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on 'Sub-Resources' link corresponding to resource RS1.
		 *  Expected Result:'Sub-Resource List for < Resource name (RS1) >' screen is displayed 
		 *  'Create New Sub-Resource' button is available.SRS1 is listed.
		 */
		// 625817
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifySubResource(selenium,
						strSubResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
		/*
		 * STEP : Action:Click on 'Edit' link corresponding to sub-resource SRS1 
		 *  Expected Result:'Edit Sub-Resource of < resource name RS1 >' screen is displayed. 
		 */
		// 625889

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageWithEditLink(
						selenium, strResource1, strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP :Action: Edit mandatory data and click on 'Save' 
		 * Expected Result:Newly updated values are displayed appropriately under their respective columns.
		 */
		// 625890
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(selenium,
						strEditSubResource, strEditAbbrv, strEditStandResType,
						true, strEditContFName, strEditContLName, strState,
						strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium, strEditSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifySubResDataInRSListPge(selenium,
						strEditSubResource, strEditAbbrv, strSubResrctTypName, strIconImg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP :Action:Navigate to Setup >> Resources, click on resource 'Sub-resources' 
		 * link corresponding to resource RS2 
		 * Expected Result:'Sub-Resource List for < Resource name (RS2) >' screen is displayed
             'Create New Sub-Resource' button is available.
  	  		'Edit' link corresponding to sub-resource SRS2 is available. 
		 */
		// 625890
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table[2]/"
						+ "tbody/tr/td[3][text()='" + strSubResource2
						+ "']/parent::tr/td[1]/a[1][text()='Edit']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Edit' link corresponding to sub-resource "
							+ strSubResource2 + " is available. ");
				} else {
					gstrReason = "'Edit' link corresponding to sub-resource "
							+ strSubResource2 + " is NOT available. ";
					log4j.info("'Edit' link corresponding to sub-resource "
							+ strSubResource2 + " is NOT available. ");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageWithEditLink(
						selenium, strResource2, strSubResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP :Action: Edit mandatory data and click on 'Save' 
		 * Expected Result:	Newly updated values are displayed appropriately under their respective columns. 
		 */
		// 625890
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(selenium,
						strEditSubResource2, strEditAbbrv2, strEditStandResType2,
						true, strEditContFName2, strEditContLName2, strState,
						strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium, strEditSubResource2, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifySubResDataInRSListPge(selenium,
						strEditSubResource2, strEditAbbrv2, strSubResrctTypName, strIconImg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP :Action:Navigate to Views >> V1 and click on resource RS1  
		 * Expected Result:'View Resource Detail' screen is displayed.
		'Sub Resource' section is displayed where Sub-resource 'SRS1' is displayed under 'sRT1' and 'ST2'.
		'Create New Sub-Resource' button is available. 
		 */
		// 625891
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { strStatType2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strEditSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on sub resource 'SRS1' 
		 * Expected Result:'View Resource Detail' screen of 'SRS1' is displayed. 
		 */
		// 628747
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToSubResViewResourceDetailPage(
						selenium, strEditSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailOfSubRes(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Edit all the mandatory data and save. 
		 * Expected Result:	User is directed to the 'View Resource Detail' screen of 'SRS1'.
  		   All the edited data are retained. 
		 */
		// 628748
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(selenium,
						strEditSubResource1, strEditAbbrv1,
						strEditStandResType1, true, strEditContFName1,
						strEditContLName1, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndViewResDetailScreenOfSubRS(
						selenium, strEditSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = {
						{ "Type:", strSubResrctTypName },
						{ "Contact:",
								strEditContFName1 + " " + strEditContLName1 } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditSubResource1, strResouceData);
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
			gstrTCID = "BQS-112341";
			gstrTO = "Verify that user with 'Setup Resources' right and update right on a resource "
					+ "can edit sub-resources under it.";// TO
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
	  // end//testBQS112341//
}
