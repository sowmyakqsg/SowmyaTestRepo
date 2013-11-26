package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**********************************************************************
' Description :This class includes Create User requirement testcases
' Precondition:
' Date		  :4-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class CreateUser  {
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
	
	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	
	
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
		

		seleniumFirefox = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		
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

			seleniumFirefox.close();
		} catch (Exception e) {

		}
		try {

			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		
		seleniumPrecondition.stop();
		selenium.stop();
		seleniumFirefox.stop();
		  
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
	
	
	
	/***************************************************************
	'Description	:Verify that a Web Service user can be created. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS66025() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application
		
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers

		try {
			gstrTCID = "BQS-66025 ";
			gstrTO = "Verify that a Web Service user can be created. ";
			gstrResult = "FAIL";
			gstrReason = "";
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strUserName = "auto" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = strUserName + "fulName";
			
			//Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			
			/*
			 * STEP 1:Login as RegAdmin and navigate to Setup>>Users<-> 'Users
			 * List' page is displayed
			 */

			// verify user login
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// verify user default region is displayed
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 2:Click on 'Create new user' <->'Create New User' page is
			 * displayed
			 */
			
			/*
			 * STEP 3:Create a user U1 providing mandatory data and by selecting
			 * 'Web Service' right<-> User U1 is listed in the 'Users List'
			 * screen under Setup
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.WebServiceUsr");
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
			
			// verify user is saved and displayed in the 'user list'
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				selenium.type(propElementDetails.getProperty("Login.UserName"),
						strUserName);
				selenium.type(propElementDetails.getProperty("Login.Password"),
						strInitPwd);

				selenium.click(propElementDetails.getProperty("Login.Submit"));
				selenium.waitForPageToLoad(gstrTimeOut);

				assertFalse(selenium.isElementPresent(propElementDetails
						.getProperty("SetUpPwd.NewUsrName")));

				log4j.info("'Set Up Your Password' screen is NOT displayed. ");
			} catch (AssertionError Ae) {

				strFuncResult = "'Set Up Your Password' screen is  displayed. "
						+ Ae;
				log4j
						.info("'Set Up Your Password' screen is  displayed. "
								+ Ae);
			}
			
			// verify user login
			
			/*
			 * STEP 4:Login as user U1 <->'Set Up Your Password' screen is not
			 * displayed. User U1 is logged into EMResource application
			 * successfully
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				gstrResult = "PASS";

				log4j
						.info("User U1 is  logged into EMResource application successfully");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "	User U1 is NOT logged into EMResource application successfully";
				log4j
						.info("	User U1 is NOT logged into EMResource application successfully");
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66025";
			gstrTO = "Verify that a Web Service user can be created. ";
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
	'Description	:Verify that a user can be provided the right to
	'				 initiate chat session.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:23-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS68420() throws Exception {

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		InstantMessaging objInstantMessaging=new InstantMessaging();

		try {
			gstrTCID = "BQS-68420 ";
			gstrTO = "Verify that a user can be provided the right "
					+ "to initiate chat session.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
		
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			String strOptions = "";
			
			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			

			
			//Regional admin login
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			
			/*
			 * STEP 1: Login as RegAdmin in region X(where 'Instant Messaging'
			 * option is enabled) and navigate to Setup>>Users.<-. 'Users List'
			 * page is displayed.
			 */

			//Verify login
			try {
				assertEquals("", strFuncResult);
				

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Verify user default region is displayed
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objCreateUsers.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Verify User list page is displayed
			
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
			
			//Verify user mandatory fields are filled
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ChatSession");

				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * STEP 2: Create a user U1 providing mandatory data and by
			 * selecting 'Instant Messaging - Initiate Chat Session' right from
			 * 'Advanced Options' section.<-> User U1 is listed in the 'Users
			 * List' screen under Setup.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Verify user is displayed in user list
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				

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
			 *STEP 3: Login as user U1 and click on IM tab.<-> 'New Private Chat' and
			 * 'New Conference' chat buttons are available.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstantMessaging
						.navInstantMesgingPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("InstantMessaging.NewPrivateChat"))
							&& selenium
									.isElementPresent(propElementDetails
											.getProperty("InstantMessaging.NewConference")));
					log4j.info("'New Private Chat' and 'New Conference' chat"
							+ " buttons are available. ");
				} catch (AssertionError Ae) {
					strFuncResult = Ae.toString();
					gstrReason = strFuncResult
							+ "'New Private Chat' and 'New Conference' chat"
							+ " buttons are NOT available. ";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstantMessaging
						.navCreateNewPrivateChatPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP 4: Click on 'New Private Chat'.<-> 'Create New Chat (1 of
			 * 2)' window is popped up. The following radio buttons are
			 * displayed in the window. 1. List All Users. 2. Find user(s)
			 * where, contains. 3. Find user(s) for resource.
			 */
			
			//Verify create new private chat page is displyed
			try {
				assertEquals("", strFuncResult);
				selenium.click(propElementDetails
						.getProperty("IM.NewPrivateChat.Cancel"));


				strFuncResult = objInstantMessaging.navNewConference(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 5: Click on 'Cancel' and then click on 'New Conference'
			 * button.<-> 'Create New Conference' window is popped up with 'Room
			 * Name' and 'Description' fields.
			 */
			//Verify new conference page is displayed

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
			gstrTCID = "BQS-68420";
			gstrTO = "Verify that a user can be provided the right to"
					+ " initiate chat session.";
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
	
	
	/*****************************************************************************************
		 'Description :Verify that a user can be provided the right to
		 '     edit status change 
		 'Precondition :1. Status type ST(number) is created selecting 
		 '     role R1 under both 'Roles with view rights' and
		 '       'Roles with update rights'.
		 '     2. Resource type RT is associated with status type ST.
		 '     3. Resource RS is created with address under resource type RT.(created within tc)
		 '     4. User U2 is created with role R1 and 'Update right' for resource RS.(created within tc) 
		 'Arguments  :None
		 'Returns  :None
		 'Date    :8-May-2012
		 'Author   :QSG
		 '--------------------------------------------------------------------------------------
		 'Modified Date                                       Modified By
		 '15-May-2012                                                 <Name>
		 ***********************************************************************************/

	@Test
	public void testBQS69710() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		Resources objResources = new Resources();
		General objMail = new General();// object of class General
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();

		try {
			gstrTCID = "BQS-69710 ";
			gstrTO = "Verify that a user can be provided "
					+ "the right to edit status change ";
			gstrResult = "FAIL";
			gstrReason = "";

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

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

			// Admin login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			// Region Name
			String  strRegn = rdExcel.readData("Login", 3, 4);

			// Users mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";
			String strUsrFulNameUpdate = "";

			String strUserNameUpdate = "";

			String strOptions = "";

			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

			// String strSTValue="";

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
			//String strInfo = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strSTvalue[] = new String[1];
			strSTvalue[0] = "";
			String strRSValue[] = new String[1];
			
			String strCurrDate2="";
			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Users. 'Users List'
			 * page is displayed.
			 */

			

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(selenium,
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
				strFuncResult = objST.savAndVerifySTNew(selenium, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName);
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

			// User U2 is created with role R1 and 'Update right' for resource
			// RS.
			// TestRole value="6057"
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// User U2 is created with role and update right

			try {
				assertEquals("", strFuncResult);

				// Data for creating user with update resource right
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

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

			/*
			 * STEP 3: Create a user U1 providing mandatory data and by
			 * selecting 'Edit Status Change Notification Preferences' right
			 * from 'Advanced Options' section. User U1 is listed in the 'Users
			 * List' screen under Setup.
			 */

			// User U1

			/*
			 * Create a user U1 providing mandatory data and by selecting 'Edit
			 * Status Change Notification Preferences' right from 'Advanced
			 * Options' section
			 */

			try {
				assertEquals("", strFuncResult);

				// Data for creating user with update resource right
				strUserNameUpdate = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulNameUpdate = strUserNameUpdate + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameUpdate, strInitPwd, strConfirmPwd,
						strUsrFulNameUpdate);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);


				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameUpdate, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			/*
			 * STEP 4: Login as user U1 and navigate to Preferences>>Status
			 * Change Prefs.<-> 'My Status Change Preferences' screen is
			 * displayed.
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

				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserNameUpdate, strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/* * STEP 5: Click on 'Add' button and search for resource RS, select
			 * resource RS and click on 'Notification'.<-> 'Edit My Status
			 * Change Preferences' screen is displayed.
			 */

			
			/* * STEP 6: Select web, email and pager notifications methods for
			 * status type ST with appropriate above(say X) value and below
			 * value (say Y) and save the page. save the page. 'My Status Change
			 * Preferences' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				String strSearchCategory = "(Any)";

				strFuncResult = objPreferences.addResourcesInST(selenium,
						strResource, strSearchCategory, strRSValue[0],
						strSTvalue[0]);
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
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
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
				strFndStYear =dts.getTimeOfParticularTimeZone("CST", "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserNameUpdate,
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

				String strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statTypeName + " status from 0 to 2.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statTypeName + " status from 0 to 2.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
				try {
					assertTrue(strFuncResult.equals(""));
					intResCnt++;
					Thread.sleep(20000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				strSubjName = "Change for Rs";

				strMsgBody2 = statTypeName + " from 0 to 2; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulNameUpdate
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

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}
					selenium.close();
					
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69710";
			gstrTO = "Verify that a user can be provided "
					+ "the right to edit status change ";
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
	 'Description :Verify that a user can be provided with
	                  right to setup resource.
	 'Precondition :1.Status type 'ST' is created.
	                  2.Resource type RT is created by selecting status type 'ST'
	                  3.Resource RS1 and RS2 are created proving address under RT.
	                  4.View V1 is created by selecting status type ST and resource type RT(master check box).
	                  5. User U1 is created provide �Update status' right on 'RS1' and not on 'RS2' 
	 'Arguments  :None
	 'Returns  :None
	 'Date    :17th-May-2012
	 'Author   :QSG
	 '---------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Date>                                     <Name>
	 ***************************************************************/
	@Test
	public void testBQS66155() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Roles objRoles = new Roles();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		try {
			gstrTCID = "BQS-66155 ";
			gstrTO = "Verify that a user can be provided the right to setup resource.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String statStatusTypeName="ST"+strTimeText;
			String strStatTypDefn="Automation";
			String strSTType="Number";
			String strStatValue="";
			String strSTvalue[]=new String[1];
			
			String strResrctTypName = "AutoRt_" + strTimeText;
			
			
			String strResource_1 ="AutoRs_1" + strTimeText;
			String strResource_2 ="AutoRs_2" + strTimeText;
			String strResource_3 ="AutoRs_3" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[]=new String[3];
			
			//Edit _1
			
			String strEditResource_1 = "AutoEdit_1"+strResource_1;
			String strEditContFName_1 = "AutoEdit_1"+strContFName;
			String strEditContLName_1 = "AutoEdit_1"+strContLName;
			String strEditAbbrv_1 = "Edi";
			String strEditStandResType_1 = "Hospital";

			
			//Edit _2
			
			String strEditResource_2 = "AutoEdit_2"+strResource_1;
			String strEditContFName_2= "AutoEdit_2"+strContFName;
			String strEditContLName_2 = "AutoEdit_2"+strContLName;
			String strEditAbbrv_2 = "Edi";
			String strEditStandResType_2 = "Aeromedical";
			
			
			//Edit _3
			String strEditResource_3 = "AutoEdit_3"+strResource_1;
			String strEditContFName_3= "AutoEdit_3"+strContFName;
			String strEditContLName_3 = "AutoEdit_3"+strContLName;
			String strEditAbbrv_3 = "Edi";
			String strEditStandResType_3 = "Hospital";
			
			//Edit _4
			
			String strEditResource_4 = "AutoEdit_4"+strResource_1;
			String strEditContFName_4 = "AutoEdit_4"+strContFName;
			String strEditContLName_4 = "AutoEdit_4"+strContLName;
			String strEditAbbrv_4 = "Edi";
			String strEditStandResType_4 = "Aeromedical";

			
			//Edit _5
			
			String strEditResource_5 = "AutoEdit_5"+strResource_1;
			String strEditContFName_5= "AutoEdit_5"+strContFName;
			String strEditContLName_5 = "AutoEdit_5"+strContLName;
			String strEditAbbrv_5 = "Edi";
			String strEditStandResType_5 = "Hospital";
			
			
			//Edit _6
			String strEditResource_6 = "AutoEdit_6"+strResource_1;
			String strEditContFName_6= "AutoEdit_6"+strContFName;
			String strEditContLName_6 = "AutoEdit_6"+strContLName;
			String strEditAbbrv_6 = "Edi";
			String strEditStandResType_6 = "Aeromedical";
			
			
			String strViewName="";
			String strVewDescription="";
			String strViewType="";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
	
			
			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";
			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			
			
			/* 1.Status type 'ST' is created. */
			
			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
			
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
			}
			
			
			
			//Creating ST
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSTType, statStatusTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statStatusTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* 2.Resource type RT is created by selecting status type 'ST'. */
			
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
			
			/*3.Resource RS1 and RS2 are created proving address under RT. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(seleniumPrecondition,
						strResource_1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition, strResource_1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RS2
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(seleniumPrecondition,
						strResource_2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition, strResource_2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*4.View V1 is created by selecting status type ST and resource type RT(master check box). */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strViewName = "autoView" + System.currentTimeMillis();
				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";

				String strRs[] = { strRSValue[0], strRSValue[1] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRs);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*5. User U1 is created provide �Update status' right on 'RS1' and not on 'RS2' 
*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource_1, strRSValue[0], false, true,
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

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			
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
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
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

			
			/*
			 * 3 Create a role 'R1' by selecting 'Setup Resource' right from
			 * 'Select the Rights for this Role' section. Role R1 is displayed
			 * in 'Roles List' page.
			 */
			

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
			
			/*
			 * 4 Navigate to Setup>>Users and click on the 'Edit' link
			 * associated with user U1 and select the role R1 from 'User Type &
			 * Roles' section and save the page. 'Users List' page is displayed.
			 */
			
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

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName_1);
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
				strFuncResult =objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 6 Click on 'Create New Resource' and create resource RS3 by
			 * filling all the mandatory data. Resource RS3 is listed in the
			 * 'Resource List' screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
			
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources
						.createResourceWitLookUPadresWhenUserLogins(selenium,
								strResource_3, strAbbrv, strResrctTypName,
								strContFName, strContLName, strState,
								strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium, strResource_3);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[2] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Click on the 'Edit' link associated with resource RS1 and
			 * change the mandatory data. The updated data is displayed on the
			 * 'Resource List' screen.
			 */
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_1, strEditAbbrv_1, strResrctTypName,
						strEditStandResType_1, false, false, "", "", false, "", "",
						strState, "", strCountry, "", strEditContFName_1, strEditContLName_1, "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strEditResource_1, "No", "", strEditAbbrv_1, strResrctTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 8 Navigate to a view>>V1, click on a resource RS1 then click on
			 * 'edit resource details' link and edit the mandatory data. The
			 * updated data is displayed on the 'View Resource Detail' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResourceFromUserView(
						selenium, strEditResource_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";

				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_2, strEditAbbrv_2,
						strResrctTypName, strEditStandResType_2, false, false,
						"", "", false, "", "", strState, "", strCountry, "",
						strEditContFName_2, strEditContLName_2, "", "", "", "",
						"", "", "");
				selenium.click("//input[@value='Save']");
				selenium.waitForPageToLoad("30000");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditResource_2, strResouceData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = {};
				boolean blnCheckEveStat = false;
				boolean blnCheckRoleStat = false;
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strEditResource_2, strEventStatType,
						strRoleStatType, blnCheckEveStat, blnCheckRoleStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 10: Click on 'Edit Info' and edit the mandatory data. The
			 * updated data is displayed on the 'View Resource Detail' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToEditResourceFromMapView(
						selenium, strEditResource_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_3, strEditAbbrv_3,
						strResrctTypName, strEditStandResType_3, false, false,
						"", "", false, "", "", strState, "", strCountry, "",
						strEditContFName_3, strEditContLName_3, "", "", "", "",
						"", "", "");
				selenium.click("//input[@value='Save']");
				selenium.waitForPageToLoad("30000");
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
				// click on resource link
				selenium
						.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
								+ strEditResource_3 + "']");
				selenium.waitForPageToLoad(gstrTimeOut);
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditResource_3, strResouceData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RS2
			
			/*
			 * 11 Navigate to Setup>>Resources, click on the 'Edit' link
			 * associated with resource RS2 and change the mandatory data. The
			 * updated data is displayed on the 'Resource List' screen.
			 */ 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_4, strEditAbbrv_4, strResrctTypName,
						strEditStandResType_4, false, false, "", "", false, "", "",
						strState, "", strCountry, "", strEditContFName_4, strEditContLName_4, "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strEditResource_4, "No", "", strEditAbbrv_4, strResrctTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 12 Navigate to a view>>V1, click on a resource RS2 then click on
			 * 'edit resource details' link and edit the mandatory data. The
			 * updated data is displayed on the 'View Resource Detail' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResourceFromUserView(
						selenium, strEditResource_4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";

				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_5, strEditAbbrv_5,
						strResrctTypName, strEditStandResType_5, false, false,
						"", "", false, "", "", strState, "", strCountry, "",
						strEditContFName_5, strEditContLName_5, "", "", "", "",
						"", "", "");
				selenium.click("//input[@value='Save']");
				selenium.waitForPageToLoad("30000");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditResource_5, strResouceData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = {};
				boolean blnCheckEveStat = false;
				boolean blnCheckRoleStat = false;
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strEditResource_5, strEventStatType,
						strRoleStatType, blnCheckEveStat, blnCheckRoleStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 13 Navigate to view>>Map, click on the resource icon RS2.
			 * Resource pop up window is displayed for RS2.
			 */
			
			/*
			 * 14 Click on 'Edit Info' and edit the mandatory data. The updated
			 * data is displayed on the 'View Resource Detail' screen.
			 */ 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToEditResourceFromMapView(
						selenium, strEditResource_5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_6, strEditAbbrv_6,
						strResrctTypName, strEditStandResType_6, false, false,
						"", "", false, "", "", strState, "", strCountry, "",
						strEditContFName_6, strEditContLName_6, "", "", "", "",
						"", "", "");
				selenium.click("//input[@value='Save']");
				selenium.waitForPageToLoad("30000");
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
				// click on resource link
				selenium
						.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
								+ strEditResource_6 + "']");
				selenium.waitForPageToLoad(gstrTimeOut);
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditResource_6, strResouceData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}



			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		} catch (Exception e) {
			gstrTCID = "BQS-66155";
			gstrTO = "Verify that a user can be provided the right to setup resource.";
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
	'Description		:Verify that user can be created by providing only mandatory data.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/27/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS88053() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;// keep track of logout of application
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		RegionalInfo objRegionalInfo = new RegionalInfo();
		try {
			gstrTCID = "88053"; // Test Case Id
			gstrTO = " Verify that user can be created by providing only mandatory data.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// USER
			String strUserName = "Autousr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Fn" + strUserName;
		 // Search Resource criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

		/*
		* STEP :
		  Action:Login as RegAdmin, navigate to Setup >> Users
		  Expected Result:'Users List' page is displayed
		*/
		//538330
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
		//538332
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
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
		//538372
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.vrfyUsrNameAndFNInRegInfo(selenium, strUserName, strUsrFulName);
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
		  Action:Login as user U1 using the appropriate password provided at the time user was created.
		  Expected Result:User is taken to the 'Change Password' screen.
		*/
	
			
		/*
		* STEP :
		  Action:Provide data in 'New Password' and 'Verify Password' fields and click on 'Submit'
		  Expected Result:User U1 is successfully logged in and 'Region Default' screen is displayed.
          User U1's full name is displayed at the bottom right of the application.
		*/
	
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				assertEquals(strRegn, selenium.getText("id=regionName"));
				log4j
						.info("User "
								+ strUserName
								+ "is successfully logged in and 'Region Default' screen is displayed. ");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium,
						strUsrFulName, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Log Out' link.
		  Expected Result:User is logged out and EMResource login screen is displayed.
		*/
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login again as user U1 providing valid username and password.
		  Expected Result:'Region Default' screen is displayed.
          'Change Password' screen is not displayed i.e the user is not asked to change password the second time he/she logs in.
		*/
	
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				assertEquals(strRegn, selenium.getText("id=regionName"));
				log4j.info("'Region Default' screen is displayed. ");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertFalse(selenium.isTextPresent("Set Up Your Password"));
				log4j.info("'Change Password' screen is not displayed ");
			} catch (AssertionError Ae) {
				gstrReason = "Set Up Your Password is displayed";
				log4j.info("Set Up Your Password is displayed");
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
		gstrTCID = "BQS-88053";
		gstrTO = "Verify that user can be created by providing only mandatory data.";
		gstrResult = "FAIL";
		log4j.info(e);
		log4j.info("========== Test Case '" + gstrTCID
		+ "' has FAILED ==========");
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("----------------------------------------------------------");
		gstrReason = e.toString();
	   }	   
	   if (blnLogin) {
			strFuncResult= objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

	/***************************************************************
	'Description	:Verify that a user can be provided the right to
	'				 setup User accounts.
	'Precondition	:1. Active users U1, U2 and U3 are created.
	'				 2. Inactive user U4 is created. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS66669() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "BQS-66669 ";
			gstrTO = "Verify that a user can be provided the "
					+ "right to setup User accounts.";
			gstrResult = "FAIL";
			gstrReason = "";
			

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			
			String strInitPwd =  rdExcel.readData("Login", 4, 2);;
			String strConfirmPwd = strInitPwd;
			
			//Active User U1
			String strUserName_u1 = "AutoUseru1"+System.currentTimeMillis();
			String strUsrFulName_U1 =strUserName_u1;
			
			//Active User U2
			String strUserName_u2 = "AutoUseru2"+System.currentTimeMillis();
			String strUsrFulName_U2 =strUserName_u2;
			
			
			//Edit Active User U2
			String strEditUserName_u2 = "EditAutoUseru2"+System.currentTimeMillis();
			String strEditUsrFulName_U2 =strEditUserName_u2;
			String strEditInitPwd_U2 =  rdExcel.readData("Login", 5, 2);;
		
			//Active User U3
			String strUserName_u3 = "AutoUseru3"+System.currentTimeMillis();
			String strUsrFulName_U3 =strUserName_u3;
			
			
			//Inactive User U4
			String strUserName_u4 = "AutoUseru4"+System.currentTimeMillis();
			String strUsrFulName_U4 =strUserName_u4;
			
			
			//Active User U4
			String strUserName_u5 = "AutoUseru5"+System.currentTimeMillis();
			String strUsrFulName_U5 =strUserName_u5;
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
		
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*1. Active users U2 and U3 are created. */

			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Active users U2
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_u2, strInitPwd, strConfirmPwd,
						strUsrFulName_U2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_u2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Active users U3

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_u3, strInitPwd, strConfirmPwd,
						strUsrFulName_U3);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_u3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/* 2. Inactive user U4 is created. */
			
			// Active users U4

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_u4, strInitPwd, strConfirmPwd,
						strUsrFulName_U4);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_u4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.deactivateUser(seleniumPrecondition,
						strUserName_u4, strUsrFulName_U4, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
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

				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*3 	Click on 'Create new user'. 		'Create New User' page is displayed. 
			*/
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_u1, strInitPwd, strConfirmPwd,
						strUsrFulName_U1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 4 Create a user U1 providing mandatory data and by selecting
			 * 'User - Setup User Accounts' right from 'Advanced Options'
			 * section. User U1 is listed in the 'Users List' screen under
			 * Setup.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");

				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Create New User' page is NOT displayed. ";
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_u1, strByRole, strByResourceType,
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
			
			/*5 	Login as user U1 and navigate to Setup>>Users. 		'Users List' screen is displayed. */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_u1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 6 Click on 'Create New User' and create user U5 by filling all
			 * the mandatory data. User U5 is listed in the 'Users List' screen.
			 */ 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_u5, strInitPwd, strConfirmPwd,
						strUsrFulName_U5);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_u5, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
			
				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 7 Select 'include inactive users' check box. Active and inactive
			 * users are displayed on the 'Users List' screen.
			 */ 
			
			
			
			try {
				assertEquals("", strFuncResult);
				if (selenium.isChecked(propElementDetails
						.getProperty("UserList.IncludeActvUsers")) == false) {
					selenium.click(propElementDetails
							.getProperty("UserList.IncludeActvUsers"));

					int intCnt = 0;

					try {

						while (selenium.isVisible(propElementDetails
								.getProperty("Reloading.Element"))
								&& intCnt < 60) {
							intCnt++;
							Thread.sleep(100);
						}

					} catch (Exception e) {
						log4j.info(e);
					}
				}

				try {

					assertTrue(selenium
							.isElementPresent("//td[@class='inactive'][contains(text(),'"
									+ strUsrFulName_U4 + "')]"));

					log4j.info("Active and inactive users are displayed "
							+ "on the 'Users List' screen.");

				} catch (AssertionError Ae) {
					strFuncResult = "Active and inactive users are NOT displayed"
							+ " on the 'Users List' screen. ";

					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Click on the 'Edit' link associated with user U2 and edit the
			 * mandatory data. The updated data is displayed on the 'Users List'
			 * screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_u2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);
				selenium.type(propElementDetails
						.getProperty("CreateNewUsr.FulUsrName"), strEditUsrFulName_U2);
				
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_u2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[3][text()='"
								+ strEditUsrFulName_U2 + "']"));

				log4j
						.info("The updated data is displayed on the 'Users List' screen. ");

			} catch (AssertionError Ae) {
				log4j
						.info("The updated data is NOT displayed on the 'Users List' screen. ");
				strFuncResult = "The updated data is NOT displayed on the 'Users List' screen. ";

				gstrReason = strFuncResult;
			}

			/*
			 * 9 Click on the 'Edit' link associated with user U3 and select the
			 * option to deactivate the user. "Confirm User Deactivation" screen
			 * is displayed.
			 */
			/*
			 * 10 Click on button "Yes, Deactivate this User".
			 * "User Deactivated" screen is displayed.
			 */
			/*
			 * 11 Click on button "Return to User List". User is returned to the
			 * 'Users List' page.
			 */
			
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName_u3,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName_u3, strUsrFulName_U3, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 12 Click on the 'Edit' link associated with user U4 and select
			 * the option to activate the user. "User Activation Complete"
			 * screen is displayed.
			 */
			/*
			 * 13 Click on button "Return to User List". User is returned to the
			 * 'Users List' page.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.activateUser(selenium,
						strUserName_u4, strUsrFulName_U4, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			/*
			 * 14 Click on the 'Password' link associated with user U2 then
			 * click on 'here' link and change the password. 'Region Default'
			 * screen is displayed.
			 */
			/*
			 * 15 Login as user U2 with the new password. User U2 is logged into
			 * application successfully.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName_u2,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.changePassword(selenium,
						strEditInitPwd_U2, strUserName_u2);

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
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_u2,
						strEditInitPwd_U2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				gstrResult = "PASS";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66669";
			gstrTO = "Verify that a user can be provided"
					+ " the right to setup User accounts.";
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
	'Description	:Verify that a user can be provided the right to
	'				 update region setup information. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS66072() throws Exception {

		boolean blnLogin=false;//keep track of logout of application
		
		String strFuncResult = "";
		
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Regions objRegion=new Regions();

		try {
			gstrTCID = "BQS-66072 ";
			gstrTO = "Verify that a user can be provided the right to update"
					+ " region setup information. ";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strOptions = "";

			// admin login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 3, 3);

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
			
		
			
			String strEditRegnName = "Z_Auto"+ System.currentTimeMillis();
			
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "qsg";
			String strContLN = "Auto";
			String strAlertFrequency = "15";
			String strEmailFrequency = "Never";
	
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			
			/*
			 * 3 Create a user U1 providing mandatory data and by selecting 'May
			 * update region setup information' right from 'Advanced Options'
			 * section. User U1 is listed in the 'Users List' screen under
			 * Setup.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("createUser.advancedoption.MayUpdateRegion");
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
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 4 Login as user U1 and navigate to Setup>>Regions. 'Region List'
			 * screen is displayed.
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
				strFuncResult = objRegion.navRegionList(selenium);
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/"
									+ "td[2][text()='" + strRegn + "']"));
					log4j.info("Region" + strRegn
							+ "is listed in region list screen");

				} catch (AssertionError Ae) {
					gstrReason = "Region" + strRegn
							+ "is  NOT listed in region list screen";
					log4j.info("Region" + strRegn
							+ "is  NOT listed in region list screen");
				}

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 6: Click on 'Edit' link associated with his logged in
			 * region. <-Result->: 'Edit Region' screen is displayed.
			 */
			/*
			 * STEP 7:Edit the mandatory data and click on 'Save'.
			 *  		The updated data is displayed on the 'Region List' screen. 
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.editRegnMandatryFlds(selenium,
						strRegn, strEditRegnName, strTimeZone,
						strContFN, strContLN, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegion.saveAndVerifyRegion(selenium, strEditRegnName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 8:Navigate to View>>Map. 	
			 * 	'Regional Map View' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navRegnalMapView(selenium);
				String strFilePath=pathProps.getProperty("UserDetails_path");
				objOFC.WriteTestDatatoSpecifiedCell(strEditRegnName, strFilePath, "Regions", 3, 3);
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 9:Click on 'Save as Region Default'. 
			 * 		Browser popup window is displayed 
			 */

			/*
			 * STEP 10:Close the browser popup window.<-Result->:The default
			 * region map will be saved successfully.
			 */

			try {

				selenium.click("css=input[value='Save as Region Default']");

				int intCnt = 0;
				boolean blnCnt = true;

				do {
					try {
						assertEquals(
								"The default region map view has been updated successfully.",
								selenium.getAlert());

						log4j
								.info("The default region map will  be saved successfully. ");

						blnCnt = false;

						gstrResult = "PASS";
					} catch (AssertionError Ae) {
						intCnt++;
						Thread.sleep(1000);
					} catch (Exception e) {
						intCnt++;
						Thread.sleep(1000);

						if (intCnt == 60) {
							gstrReason = "The default region map will NOT be saved successfully. ";
						}

					}
				} while (intCnt < 60 && blnCnt);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Regional Map View' screen is NOT displayed. "
						+ " 'Save as Region Default' button is NOT available. ";
			}
		
		

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66072";
			gstrTO = "Verify that a user can be provided the right to"
					+ " update region setup information. ";
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
	'Description	:Verify that a user can be provided the right to
	'				 configure region views.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS66095() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Views objViews = new Views();
		Roles objRoles=new Roles();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();


		try {
			gstrTCID = "BQS-66095 ";
			gstrTO = "Verify that a user can be provided the right "
					+ "to configure region views.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			//Admin login details
			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			//Default Region
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String statNumTypeName="A1_"+strTimeText;
			String strSTValue = "Number";
			String strSTvalue[]=new String[1];
			String strStatTypDefn = "Automation";
			//General variable 
			String strStatValue = "";
		
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue="";
			
			String strResource ="AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[]=new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			//Data for creating View
			String strViewName_1="autoView_1" + strTimeText;
			String strViewName_2="autoView_2" + strTimeText;
			String strViewName_3="autoView_3" + strTimeText;
			
			String strVewDescription="";
			String strViewType="";
			
			String strUpdatedName="";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			String strRolesName = "Role" + strTimeText;
			String strRoleValue="";
			
			
			String strSection="AB_"+strTimeText;
			
			String []strStatTypeArr={statNumTypeName};
		
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 1.Status type 'ST' is created by selecting role 'R1' under both
			 * view and update right on the status type.
			 */

			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
						strRolesName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			//Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Creating ST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSTValue, statNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				;
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						selenium, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(selenium,
						statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*2.Resource type RT is created by selecting status type 'ST'.*/

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
				strRTValue = objRT.fetchRTValueInRTList(selenium, strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*3.Resource RS is created under RT. */
			
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
			
			/*
			 * 4.View V1 is created by selecting status type ST and resource
			 * type RT(master check box).
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				
				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";
				
				
				strFuncResult = objViews.createView(selenium, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			/*5. User U1 is created */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[@id='listViews']/tbody/tr/"
									+ "td[2][text()='Region Default (default)']/"
									+ "preceding-sibling::td/a[text()='Edit']")
							&& selenium
									.isElementPresent("//table[@id='listViews']/tbody/tr/"
											+ "td[2][text()='Region Default (default)']/"
											+ "preceding-sibling::td/a[text()='Delete']")
							&& selenium
									.isElementPresent("//table[@id='listViews']/tbody/tr/"
											+ "td[2][text()='Region Default (default)']/"
											+ "preceding-sibling::td/a[text()='Users']"));

					log4j
							.info("Edit,Delete and Users link is NOT associated" +
									" with the Region default view");
				} catch (AssertionError Ae) {
					strFuncResult = "Edit,Delete and Users link is associated " +
							"with the Region default view"
							+ Ae;
					log4j
							.info("Edit,Delete and Users link is associated" +
									" with the Region default view"
									+ Ae);
					
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 6: Click on 'Create New View', provide the mandatory data
			 * for view 'V2' and click on 'Save'.<-> View V2 is displayed in
			 * 'Region Views List' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				
				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";
				
				
				strFuncResult = objViews.createView(selenium, strViewName_2,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 7: Click on 'Edit' link associated with view 'V2' edit the
			 * mandatory data and click on 'Save'. <->Updated data for view V2
			 * is displayed on 'Region Views List' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strUpdatedName = "Edit" + strViewName_2;
				strVewDescription = "Automation";
				strViewType = "Summary (Resources as rows. Status types as columns.)";
				strFuncResult = objViews
						.updateViewFlds(selenium, strViewName_2, strUpdatedName,
								strViewType,
								strVewDescription);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 8: Click on 'Edit' link associated with view 'V2' <->Changes
			 * made are retained on 'Edit View' screen of view 'V2'.
			 */

			try {
				assertEquals("", strFuncResult);

				selenium.click("//table[@id='listViews']/tbody/tr/"
						+ "td[text()='" + strUpdatedName + "']/"
						+ "preceding-sibling::td/a[text()='Edit']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				try {
					assertEquals(selenium.getValue(propElementDetails
							.getProperty("View.CreateNewView.Name")),
							strUpdatedName);
					
				
					assertEquals(selenium.getSelectedLabel(propElementDetails
							.getProperty("View.CreateNewView.ViewType")),
							strViewType);

					log4j.info("Changes made are retained on "
							+ "'Edit View' screen of view 'V2'");

				} catch (AssertionError Ae) {
					
					log4j.info("Changes made are NOT retained on "
							+ "'Edit View' screen of view 'V2'" + Ae);
					
					strFuncResult = "Changes made are NOT retained on "
							+ "'Edit View' screen of view 'V2'" + Ae;
					gstrReason = strFuncResult;
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
				String[] strUserNames =new String[]{};
				strFuncResult = objViews.assignUsersToViews(selenium,
						strUpdatedName, strUserNames, true,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strUserNames = {};

				strFuncResult = objViews.assignUsersToViews(selenium,
						strUpdatedName, strUserNames, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				if (selenium.isChecked("css=#visibleToAll")==false) {
					selenium.click("css=#visibleToAll");

				}
				
				selenium.click("css=input[value='Cancel']");
				selenium.waitForPageToLoad(gstrTimeOut);

				
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * STEP 12: Navigate to a View>>V1 and click on 'customize' link.<->
			 * 'Edit View' screen of V1 is displayed.
			 */
			
			/* STEP 13: Click on 'Save'<-> View V1 screen is displayed. */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navEditViewsPgeByCustomize(selenium,
						strViewName_1);
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

				strFuncResult = objViews.orderAndReorderViews(selenium,
						strViewName_1, strUpdatedName);
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
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox, strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium, strSection,strStatTypeArr);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditResDetViewSecByCustomize(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToViewResDetail(selenium);

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
				strVewDescription = "Automation";
				strViewType = "Summary (Resources as rows. Status types as columns.)";
				strFuncResult = objViews.copyAndCreateView(selenium, strViewName_1, strViewName_3, strVewDescription, strViewType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViews.deleteView(selenium, strViewName_3);
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
			gstrTCID = "BQS-66095";
			gstrTO = "Verify that a user can be provided the right"
					+ " to configure region views.";
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
	'Description	:Verify that a user can be provided the right to
	'				 administer other region views.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:19-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	@Test
	public void testBQS66123() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Regions objRegions = new Regions();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();

		try {
			gstrTCID = "BQS-66123 ";
			gstrTO = "Verify that a user can be provided the right"
					+ " to administer other region views.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn = rdExcel.readData("Login", 7, 4);
			String strRegionName = rdExcel.readData("Login", 8, 4);

			String strUserName = "auto" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 9, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = strUserName + "fulName";


			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			
			String strOptions = "";
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
			
			/*
			 * STEP 2: Login as RegAdmin in region X (Eg.Statewide Oklahoma) and
			 * navigate to Setup >> Roles, click on 'Create New Role'.<->
			 * 'Create Role' page is displayed.
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
			
			/*
			 * 2 Click on 'Create new user'. 'Create New User' page is
			 * displayed.
			 */
			/*
			 * 3 Create a user U1 providing mandatory data and by selecting
			 * 'Administer Other Region Views' right from 'Advanced Options'
			 * section. User U1 is listed in the 'Users List' screen under
			 * Setup.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.AdministrOthrRegnViews");
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 4 Login as user U1 and navigate to Setup>>Other Regions. 'Other
			 * Region List' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			
				selenium.selectWindow("");
				selenium.selectFrame("Data");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
			
				strFuncResult = objRegions.navOtherRegions(selenium);
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 6: Click on 'view agreement' associated with any region.<->
			 * New window is popped up and user can view the aggrement.
			 */
			
			/*
			 *STEP 7: Click on 'assign users' associated with region Y. 'Assign Users
			 * to Y' screen is displayed.
			 * 
			 * The user U1 can assign the users to region Y.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				
				String strAgrementData = rdExcel.readData("Login", 3, 7);
				
				strFuncResult = objRegions.viewAgreementAndAssignUsers(
						selenium, strRegionName, strAgrementData, strUserName,
						true, true);

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 8: Navigate to Setup>>Other Regions click on 'Shared
			 * Resources'. 'View Other Region Security - Resources' screen is
			 * displayed.
			 * STEP 9: Click on 'Back'.<-> 'Other Region List' screen
			 * is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				log4j
						.info("New window is NOT popped up and user can view the aggrement.");

				strFuncResult = objResourceTypes
						.navViewOtherRegionSecurity_Resources(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "New window is NOT popped up and user can view the aggrement."
						+ "'Assign Users to Y' screen is NOT displayed. ";
			}
			
			/*
			 * 10 Click on 'Shared Status Types'.<-> 'View Other Region Security
			 * - Status Types' screen is displayed.
			 */
			/* 11 Click on 'Back'.<-> 'Other Region List' screen is displayed. */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.navViewOtherRegionSecurityPge_StatusType(selenium,
								true);
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
			gstrTCID = "BQS-66123";
			gstrTO = "Verify that a user can be provided the right"
					+ " to administer other region views.";
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
	'Description	:Verify that a user can be provided the right to
	'				 setup status types. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS66134() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		StatusTypes objStatusTypes = new StatusTypes();// object of class
														// StatusTypes

		try {
			gstrTCID = "BQS-66134 ";
			gstrTO = "Verify that a user can be provided the right to setup status types.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH= pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String statMultiTypeName="MST"+strTimeText;
			String strMSTValue = "Multi";
			String strStatTypDefn = "Automation";
			
			String strStatTypeColor1="Black";
			String strStatTypeColor2="Red";
			String strStatTypeColor3="Green";
			String strStatTypeColor4="Black";
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			String strStatusName3="Stc"+strTimeText;
			String strStatusName4="Std"+strTimeText;
		
			String strStatusValue[]=new String[4];
			strStatusValue[0]="";
			strStatusValue[1]="";
			strStatusValue[2]="";
			strStatusValue[3]="";
			
			String statNumTypeName="NST"+strTimeText;
			String strSTValue = "Number";
			
			
			String strSTvalue[]=new String[2];
			
			//General variable 
			String strStatValue = "";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STATRTS~~~~~");
			
			/*Precondition:

				1. Multi status type MST is created with statuses S1, S2 and S3.*/ 
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1, strMSTValue,
						strStatTypeColor1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2, strMSTValue,
						strStatTypeColor2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName3, strMSTValue,
						strStatTypeColor3, true);
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(seleniumPrecondition);
				
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

				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*3 	Click on 'Create new user'. 		'Create New User' page is displayed. */
			
			
			/*
			 * 4 Create a user U1 providing mandatory data and by selecting
			 * 'Setup Status Types' right from 'Advanced Options' section. User
			 * U1 is listed in the 'Users List' screen under Setup.
			 */ 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
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
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5 Login as user U1 and navigate to Setup>>Status types. 'Status
			 * Type List' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				
				strFuncResult = objStatusTypes.navStatusTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		
			
			/*
			 * 6 Click on 'Create New Status Type' and create status type ST by
			 * filling all the mandatory data. Status type ST is created and is
			 * listed in the 'Status Type List' screen.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
		
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSTValue, statNumTypeName,
						strStatTypDefn, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 7 Click on the 'edit' link associated with status type ST and
			 * edit the mandatory data. The updated data is displayed on the
			 * 'Status Type List' screen.
			 */ 
			

			try {
				assertEquals("", strFuncResult);

				String editStatTypeName = "EDIT" + statNumTypeName;
				strStatTypDefn = "EDIT" + strStatTypDefn;

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statNumTypeName, editStatTypeName,
						strStatTypDefn, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 8 Click on 'sort' link associated with status type MST and sort
			 * the statuses. The new sorted order of statuses are displayed in
			 * the 'Status Type List' screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);

				String[] childStatTypeName = { strStatusName1,strStatusName2,
						strStatusName3 };
				strFuncResult = objStatusTypes.sortStatusTypes(selenium,
						statMultiTypeName, childStatTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			/*
			 * 9 Click on the 'statuses' link associated with status type MST,
			 * click on 'Create New Status' button. Create status 'S4' by
			 * filling all the mandatory data. Status S4 is listed in 'Status
			 * List for MST' screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statMultiTypeName, strStatusName4, strMSTValue,
						strStatTypeColor4, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, statMultiTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
			gstrTCID = "BQS-66134";
			gstrTO = "Verify that a user can be provided the right to setup status types.";
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
	'Description	:Verify that a user can be provided the right to
	'				 setup resource types. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS66152() throws Exception {

		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		ResourceTypes objResourceTypes=new ResourceTypes();
		StatusTypes objStatusTypes=new StatusTypes();


		try {
			gstrTCID = "BQS-66152 ";
			gstrTO = "Verify that a user can be provided the right " +
					"to setup resource types. ";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		
			// admin login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			String strOptions = "";
			
			
			String statNumTypeName="NST"+strTimeText;
			String strSTValue = "Number";
			String strSTvalue[]=new String[1];
			String strStatTypDefn = "Automation";
			//General variable 
			String strStatValue = "";
		
			String strResrctTypName = "AutoRt_" + strTimeText;
			String streditResrcTypName="";
			
			/*1 	Login as RegAdmin and navigate to Setup>>Users. 		'Users List' page is displayed. */
	
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(selenium,
						statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 2 Click on 'Create new user'. 'Create New User' page is
			 * displayed.
			 */
			/*
			 * 3 Create a user U1 providing mandatory data and by selecting
			 * 'Setup Resource Types' right from 'Advanced Options'. User U1 is
			 * listed in the 'Users List' screen under Setup.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
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

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 4 Login as user U1 and navigate to Setup>>Resource types.
			 * 'Resource Type List' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 5 Click on 'Create New Resource Type' and create resource type RT
			 * by filling all the mandatory data. Resource type RT is listed in
			 * the 'Resource Type List' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrctTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 6 Click on 'edit' link associated with resource type RT and edit
			 * the mandatory data. The updated data is displayed on the
			 * 'Resource Type List' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);

				streditResrcTypName = "Edit" + strResrctTypName;

				strFuncResult = objResourceTypes.editResrcTypeMandatoryFlds(
						selenium, strResrctTypName, streditResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium, streditResrcTypName);
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
			gstrTCID = "BQS-66152";
			gstrTO = "Verify that a user can be provided the right to" +
					" setup resource types. ";
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
	

	/********************************************************************************
	'Description	:Verify that a user can be provided the right 'Edit resources only'.
	'Precondition	:1.Status type 'ST' is created.
	'			     2.Resource type RT is created by selecting status type 'ST'.
	'				 3.Resource RS1 and RS2 are created proving address under RT.
	'				 4.View V1 is created by selecting status type ST and resource 
					 type RT(master check box).
	'				 
	'Arguments		:None
	'Returns		:None
	'Date	 		:18-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	

	@Test
	public void testBQS66165() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";   

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Roles objRoles = new Roles();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		try {
			gstrTCID = "BQS-66165 ";
			gstrTO = "	Verify that a user can be provided the right 'Edit resources only'.";
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

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			String strOptions = "";
			
			
			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			
			String statStatusTypeName="ST"+strTimeText;
			String strStatTypDefn="Automation";
			String strSTType="Number";
			String strStatValue="";
			String strSTvalue[]=new String[1];
			
			String strResrctTypName = "AutoRt_" + strTimeText;
			
			
			String strResource_1 ="AutoRs_1" + strTimeText;
			String strResource_2 ="AutoRs_2" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[]=new String[3];
			
			//Edit _1
			
			String strEditResource_1 = "AutoEdit_1"+strResource_1;
			String strEditContFName_1 = "AutoEdit_1"+strContFName;
			String strEditContLName_1 = "AutoEdit_1"+strContLName;
			String strEditAbbrv_1 = "Edi";
			String strEditStandResType_1 = "Hospital";

			
			//Edit _2
			
			String strEditResource_2 = "AutoEdit_2"+strResource_1;
			String strEditContFName_2= "AutoEdit_2"+strContFName;
			String strEditContLName_2 = "AutoEdit_2"+strContLName;
			String strEditAbbrv_2 = "Edi";
			String strEditStandResType_2 = "Aeromedical";
			
			
			//Edit _3
			String strEditResource_3 = "AutoEdit_3"+strResource_1;
			String strEditContFName_3= "AutoEdit_3"+strContFName;
			String strEditContLName_3 = "AutoEdit_3"+strContLName;
			String strEditAbbrv_3 = "Edi";
			String strEditStandResType_3 = "Hospital";
			
			
			String strViewName="";
			String strVewDescription="";
			String strViewType="";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
	
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 1.Status type 'ST' is created by selecting role 'R1' under both
			 * view and update right on the status type.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason=strFuncResult;
			}
			
			
			
			//Creating ST
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSTType, statStatusTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statStatusTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* 2.Resource type RT is created by selecting status type 'ST'. */
			
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
			
			/*3.Resource RS1 and RS2 are created proving address under RT. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(seleniumPrecondition,
						strResource_1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition, strResource_1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RS2
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(seleniumPrecondition,
						strResource_2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition, strResource_2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*4.View V1 is created by selecting status type ST and resource type RT(master check box). */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strViewName = "autoView" + System.currentTimeMillis();
				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";

				String strRs[] = { strRSValue[0], strRSValue[1] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRs);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Users. 'Users List'
			 * page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
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

				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create new user'. 'Create New User' page is
			 * displayed.
			 */
			/*
			 * 4 Create a user U1 by filling the mandatory data, selecting role
			 * 'R1' provide �Update status' right on 'RS1' and not on 'RS2' and
			 * by selecting 'Edit Resource only' right from 'Advanced Options'
			 * section. User U1 is listed in the 'Users List' screen under
			 * Setup.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);

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
						selenium, strResource_1, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
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
			/*
			 * 5 Login as user U1 and navigate to Setup>>Resource. 'Resource
			 * List' screen is displayed. 'Create New Resource' button is not
			 * present. Only resource RS1 is displayed and not RS2.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult =objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
			
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				assertFalse(selenium.isElementPresent("css=input[value='Create New Resource']"));
				log4j.info("'Create New Resource' button is NOT present. ");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Resource' button is present. ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/" +
					"tr/td[5][text()='"+strResource_1+"']"));
				log4j.info("Resource RS1 is displayed");
			} catch (AssertionError Ae) {
				strFuncResult="Resource RS1 is NOT displayed";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/" +
					"tr/td[5][text()='"+strResource_2+"']"));
				log4j.info("Resource RS2 is NOT displayed");
			} catch (AssertionError Ae) {
				strFuncResult="Resource RS2 is displayed";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Click on the 'Edit' link associated with resource RS1 and
			 * change the mandatory data. The updated data is displayed on the
			 * 'Resource List' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_1, strEditAbbrv_1, strResrctTypName,
						strEditStandResType_1, false, false, "", "", false, "", "",
						strState, "", strCountry, "", strEditContFName_1, strEditContLName_1, "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strEditResource_1, "No", "", strEditAbbrv_1, strResrctTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 7 Navigate to a view>>V1, click on a resource RS1 then click on
			 * 'edit resource details' link and edit the mandatory data. The
			 * updated data is displayed on the 'View Resource Detail' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResourceFromUserView(
						selenium, strEditResource_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";

				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_2, strEditAbbrv_2,
						strResrctTypName, strEditStandResType_2, false, false,
						"", "", false, "", "", strState, "", strCountry, "",
						strEditContFName_2, strEditContLName_2, "", "", "", "",
						"", "", "");
				selenium.click("//input[@value='Save']");
				selenium.waitForPageToLoad("30000");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditResource_2, strResouceData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = {};
				boolean blnCheckEveStat = false;
				boolean blnCheckRoleStat = false;
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strEditResource_2, strEventStatType,
						strRoleStatType, blnCheckEveStat, blnCheckRoleStat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToEditResourceFromMapView(
						selenium, strEditResource_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strEditResource_3, strEditAbbrv_3,
						strResrctTypName, strEditStandResType_3, false, false,
						"", "", false, "", "", strState, "", strCountry, "",
						strEditContFName_3, strEditContLName_3, "", "", "", "",
						"", "", "");
				selenium.click("//input[@value='Save']");
				selenium.waitForPageToLoad("30000");
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
				// click on resource link
				selenium
						.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
								+ strEditResource_3 + "']");
				selenium.waitForPageToLoad(gstrTimeOut);
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strEditResource_3, strResouceData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
			
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/" +
					"tr/td[5][text()='"+strResource_2+"']"));
				log4j.info("Resource RS2 is NOT displayed");
			} catch (AssertionError Ae) {
				strFuncResult="Resource RS2 is displayed";
				log4j.info(strFuncResult);
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

				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("link=edit resource details"));

					log4j
							.info("'edit resource details' link is NOT present in the"
									+ " 'View Resource Detail' screen. ");

				} catch (AssertionError Ae) {

					log4j
							.info("'edit resource details' link is  present in the"
									+ " 'View Resource Detail' screen. ");
					strFuncResult = "'edit resource details' link is  present in the"
							+ " 'View Resource Detail' screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statStatusTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource_2, strEventStatType,
						strRoleStatType, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium.isElementPresent("link=Edit Info"));

					log4j.info("'Edit Info' link is not present.");

				} catch (AssertionError Ae) {

					log4j.info("'Edit Info' link is not present.");
					strFuncResult = "'Edit Info' link is not present. ";
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
			gstrTCID = "BQS-66165";
			gstrTO = "Verify that a user can be provided the right 'Edit resources only'.";
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
	'Description	:Verify that a user can be provided the right to
	'				 setup status reasons. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS66661() throws Exception {
		
		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		StatusReason objStatusReason=new StatusReason();

		
		try {
			gstrTCID = "BQS-66661 ";
			gstrTO = "Verify that a user can be provided " +
					"the right to setup status reasons.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strOptions = "";

			// admin login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			
			String strEditMandReasonName="";
			String strMandReasonName = "";
			String strDefn = "";
			String strAbbreviation = "";

			/*
			 * 1 Login as RegAdmin and navigate to Setup >> Roles, click on
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
				strFuncResult =objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
			

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpReasons");
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
			
			/*
			 * 5 Login as user U1 and navigate to Setup>>Status Reasons. 'Status
			 * Reason List' screen is displayed.
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

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult=objStatusReason.navStatusReasonList(selenium);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * 6 Click on 'Create Status Reason' and create Status Reason 'SR'
			 * by filling all the mandatory data. Status Reason 'SR' is
			 * displayed in the 'Status Reason List' screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);

				strMandReasonName = "Resn" + System.currentTimeMillis();
				strDefn = rdExcel.readInfoExcel("Status Reason", 3, 2,
						strFILE_PATH);
				strAbbreviation = rdExcel.readInfoExcel("Status Reason", 3, 3,
						strFILE_PATH);

				strFuncResult = objStatusReason.createStatusReasn(selenium,
						strMandReasonName, strDefn, strAbbreviation, true,true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Click on the 'Edit' link associated with status Reason 'SR' and
			 * edit the mandatory data. The updated data is displayed on the
			 * 'Status Reason List' screen
			 */

			try {
				assertEquals("", strFuncResult);

				strEditMandReasonName = "";
				strDefn = "Edit"+strDefn;
				strAbbreviation = "Edit"+strAbbreviation;

				strFuncResult = objStatusReason.editStatusReasn(selenium,
						strMandReasonName, strEditMandReasonName, strDefn,
						strAbbreviation, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Status Reason 'SR' is NOT displayed"
						+ " in the 'Status Reason List' screen. ";
			}
			
			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							strDefn,
							selenium
									.getText("//div[@id='mainContainer']/"
											+ "table[@class='displayTable striped border sortable']/tbody/"
											+ "tr/td[2][text()='"
											+ strMandReasonName
											+ "']/parent::tr/td[4]"));
					
					assertEquals(
							strAbbreviation,
							selenium
									.getText("//div[@id='mainContainer']/"
											+ "table[@class='displayTable striped border sortable']/tbody/"
											+ "tr/td[2][text()='"
											+ strMandReasonName
											+ "']/parent::tr/td[3]"));
					
					log4j.info("Edited Status reason is displayed ");
				} catch (AssertionError Ae) {
					strFuncResult = "Edited Status reason is NOT displayed ";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Status Reason 'SR' is NOT displayed"
						+ " in the 'Status Reason List' screen. ";
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
			gstrTCID = "BQS-66661";
			gstrTO = "Verify that a user can be provided the right to setup status reasons.";
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
	'Description	:Verify that a user can be provided the right to
	'				 setup roles. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:9-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS66918() throws Exception {
		
		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Roles objRoles=new Roles();
		
		try {
			gstrTCID = "BQS-66918 ";
			gstrTO = "Verify that a user can be provided" +
					" the right to setup Roles. ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	
			// admin login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String  strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
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
			String strOptions = "";
			
			String strRolesName = "AutoRole" + strTimeText;
			String strEditRolesName = "";
		
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
				strFuncResult =objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
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

			/*
			 * 5 Login as user U1 and navigate to Setup>>Status Reasons. 'Status
			 * Reason List' screen is displayed.
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

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * STEP 6: Click on 'Create New Role' and create a role R2 by
			 * filling all the mandatory data.<-> Role R2 is listed in the
			 * 'Roles List' screen
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strRolesName = "Role" + System.currentTimeMillis();
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);

				strEditRolesName = "Edit" + strRolesName;
				strFuncResult = objRoles.editRolesMandatoryFlds(selenium,
						strRolesName, strEditRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium, strEditRolesName);
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
			gstrTCID = "BQS-66918";
			gstrTO = "Verify that a user can be provided the right to setup Roles. ";
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
	'Description	:Verify that a user can be provided the right to
	'				 view custom view.
	'Precondition	:1.Status type 'ST' is created by selecting role
	'			    'R1' under both view and update right on the status type.
	'				 2.Resource type RT is created by selecting status type 'ST'.
	'				 3.Resource RS is created under RT. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:25-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS66939() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Preferences objPreferences=new Preferences();
		Roles objRoles=new Roles();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews=new Views();
		
		try {
			gstrTCID = "BQS-66939 ";
			gstrTO = "Verify that a user can be provided the "
					+ "right to view custom view.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName="NST"+strTimeText;
			String strSTValue = "Number";
			String strSTvalue[]=new String[1];
			String strStatTypDefn = "Automation";
			//General variable 
			String strStatValue = "";
		
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue="";
			
			String strResource ="AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[]=new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strRolesName = "Role" + strTimeText;
			String strRoleValue="";
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STATRTS~~~~~");
			
			/*1.Status type 'ST' is created. */
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Creating ST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*2.Resource type RT is created by selecting status type 'ST'.*/

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
			
			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition, strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*3.Resource RS is created under RT. */
			
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				
				
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.savAndVerifyRoles(seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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

			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			} 
			
			
			/*5. User U1 is created */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
				
			
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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
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
			
			// verify new user login
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			 * 7 Navigate to View>>Custom. 'Custom View - Table' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 8 Click on 'Customize' link. 'Edit Custom View' screen is
			 * displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews
						.navEditCustomViewPageFromViewCustom(selenium);

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
			gstrTCID = "BQS-66939";
			gstrTO = "Verify that a user can be provided the right"
					+ "to view custom view.";
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
	'Description	:Verify that a user can be provided the right to
	'				 maintain events. 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:11-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	

	@Test
	public void testBQS67054() throws Exception {

		boolean blnLogin = false;
	
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		
		EventSetup objEventSetup=new EventSetup();
		General objGeneral=new General();
		EventSetup objEve=new EventSetup();

		try {
			gstrTCID = "BQS-67054 ";
			gstrTO = "Verify that a user can be provided "
					+ "the right to maintain events. ";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			// admin login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			
			String strEveTemp="AutoET_"+strTimeText;
			
			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("Event Temp Data", 2, 5,
					strFILE_PATH);
			/*String strIconSrc = rdExcel.readInfoExcel("Event Temp Data", 2, 6,
					strFILE_PATH);*/
			String strIconSrc = rdExcel.readInfoExcel("FirefoxTestData", 2, 3,
				     strFILE_PATH);
			String strResTypeArr[]={};
			String strStatTypeArr[]={};
			
			String strEveName="";
			String strInfo="";
			String strEditEveName="";
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEve.createEventTemplate(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strStatTypeArr, true, false, false);

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

			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

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
				blnLogin=true;
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

			/* 1. User U1 is created. */

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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
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

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
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
			
			/*
			 * 6 Click on 'Create New Event' and select event template ET.
			 * 'Create New Event' screen is displayed.
			 */
			
			/*
			 * 7 Create an event EV by filling all the mandatory data. Event EV
			 * is listed in the 'Event Management' screen.
			 */
			try {
				
				assertEquals("", strFuncResult);
				
				strEveName="EVENT"+System.currentTimeMillis();
				strInfo=rdExcel.readInfoExcel("Event", 3, 2, strFILE_PATH);
				
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strEveTemp, strEveName, strInfo, true);
				
			} catch (AssertionError Ae) {
				gstrReason = "Event Management Screen is NOT displayed";
			}
			
			/*
			 * 8 Click on the 'Edit' link associated with event EV and edit the
			 * mandatory data. The updated data is displayed on the 'Event
			 * Management' screen.
			 */
			try {
				assertEquals("", strFuncResult);

				strEditEveName = "EDIT" + strEveName;
				strFuncResult = objEventSetup.editEvent(selenium, strEveName,
						strEditEveName, strInfo, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Click on the 'End' link associated with event EV and click on
			 * 'OK' on the browser popup window. 1. Event EV has ended.
			 * 
			 * 2. 'Ended' is displayed under the status column.
			 */
			
			try {
				assertEquals("", strFuncResult);

				selenium.click("//div[@id='mainContainer']/table/tbody/"
						+ "tr/td[6][text()='" + strEditEveName
						+ "']/parent::tr/td/a[text()='End']");

				try {
					assertTrue(selenium
							.getConfirmation()
							.matches(
									"^Are you sure you"
											+ " want to end this event[\\s\\S]\n\nPress OK to end the"
											+ " event\\. Press Cancel if you do NOT want to end the event\\.$"));

					log4j.info("Confirmed to end the event and Event is ended");
				} catch (Exception Ae) {
					log4j.info("Confirmation window is NOT displayed");
					strFuncResult = "Confirmation window is NOT displayed";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
			    assertEquals("", strFuncResult);
			    String strElementID = "//div[@id='mainContainer']/table"
			         + "/tbody/tr/td[text()='"
			         + strEditEveName
			         + "']"
			         + "/parent::tr/td/a[contains(text(),'View�History')]";
			    int intCnt=0;
			    while(selenium.isElementPresent(strElementID)==false && intCnt<20){
			     intCnt++;
			     Thread.sleep(3000);
			    }
			    strFuncResult = objGeneral.CheckForElements(selenium,
			      strElementID);
			 } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			 }

			try {
				assertEquals("", strFuncResult);

				try {

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table"
									+ "/tbody/tr/td[text()='"
									+ strEditEveName
									+ "']"
									+ "/parent::tr/td/a[contains(text(),'View�History')]"));

					log4j
							.info("The updated data is displayed on the 'Event Management' screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("The updated data is NOT displayed on the 'Event Management' screen. ");
					strFuncResult = "The updated data is NOT displayed on the 'Event Management' screen.";

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
			gstrTCID = "BQS-67054";
			gstrTO = "Verify that a user can be provided the right to maintain events.  ";
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
	
	/**********************************************************************************
	'Description	:Verify that a user can be provided the right to maintain event templates.
	'Precondition	:1. User A and User B are created selecting 'Maintain Events' right.
	'				 2. User C is created without selecting 'Maintain Events' right.
	'Arguments		:None
	'Returns		:None
	'Date	 		:21-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	*************************************************************************************/
	@Test
	public void testBQS68091() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		EventSetup objEventSetup=new EventSetup();

		try {
			gstrTCID = "BQS-68091 ";
			gstrTO = "Verify that a user can be provided the"
					+ " right to maintain event templates.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			
			// admin login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			
			String strUserName_B = "AutoUsr_B" + System.currentTimeMillis();
			String strUsrFulName_B = strUserName_B;
			
			String strUserName_C = "AutoUsr_C" + System.currentTimeMillis();
			String strUsrFulName_C = strUserName_C;
			
			String strTempName = "ET" + strTimeText;
			String strTempDef = "";
			String strEveColor = "";
			String strAsscIcon = "";
			String strIconSrc = "";
			
			String strEditEveTemp="";
		
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 1. User A and User B are created selecting 'Maintain Events'
			 * right.
			 */
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_A,
								strInitPwd, strConfirmPwd, strUsrFulName_A);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_B,
								strInitPwd, strConfirmPwd, strUsrFulName_B);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_B, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*2. User C is created without selecting 'Maintain Events' right.*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_C,
								strInitPwd, strConfirmPwd, strUsrFulName_C);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_C, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Users. 'Users List'
			 * page is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);

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
			
			
			/*
			 * 3 Create a user U1 providing mandatory data and by selecting
			 * 'Maintain Event Templates' right from 'Advanced Options' section.
			 * User U1 is listed in the 'Users List' screen under Setup
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
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
			
			/*
			 * 5 Login as user U1 and navigate to Event>>Event Setup. 'Event
			 * Template List' screen is displayed.
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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objEventSetup.navToEventSetupPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on 'Create New Event Template', Create an event template
			 * 'ET1' by filling all the mandatory data and by selecting check
			 * box 'Security' and click on 'Save'. Event Notification
			 * Preferences for ET1 screen is displayed.
			 */ 
			
			/*
			 * 7 Select the 'Notification Preferences ' for user U1 and click on
			 * 'Save'. Event template 'ET1' is listed in the 'Event Template
			 * List' screen.
			 */ 
			try {
				assertEquals("", strFuncResult);

				
				strTempDef = rdExcel.readInfoExcel("Event Temp Data", 16, 3,
						strFILE_PATH);
				strEveColor = rdExcel.readInfoExcel("Event Temp Data", 16, 4,
						strFILE_PATH);
				strAsscIcon = rdExcel.readInfoExcel("Event Temp Data", 16, 5,
						strFILE_PATH);
				strIconSrc = rdExcel.readInfoExcel("Event Temp Data", 16, 6,
						strFILE_PATH);

				String[] strResType = {};
				String[] strStatusType = {};

				strFuncResult = objEventSetup.fillMandfieldsAndSaveEveTemp(
						selenium, strTempName, strTempDef, strEveColor,
						strAsscIcon, strIconSrc, "", "", "", "", false,
						strResType, strStatusType, true, true, false);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Click on the 'Edit' link associated with event template ET1 and
			 * edit the mandatory data. The updated data is displayed on the
			 * 'Event Template List' screen.
			 */ 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strEditEveTemp="EDIT"+strTempName;
				strTempDef = rdExcel.readInfoExcel("Event Temp Data", 16, 3,
						strFILE_PATH);
				strEveColor = rdExcel.readInfoExcel("Event Temp Data", 16, 4,
						strFILE_PATH);
				strAsscIcon = rdExcel.readInfoExcel("Event Temp Data", 16, 5,
						strFILE_PATH);
				strIconSrc = rdExcel.readInfoExcel("Event Temp Data", 16, 6,
						strFILE_PATH);

				String[] strResType = {};
				String[] strStatusType = {};

				strFuncResult = objEventSetup.fillMandfieldsAndSaveEveTemp(
						selenium, strEditEveTemp, strTempDef, strEveColor,
						strAsscIcon, strIconSrc, "", "", "", "", false,
						strResType, strStatusType, true, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 9 Click on the 'Security' link associated with with event
			 * template ET1. User A and User B are listed but not User c in the
			 * 'Event Security for ET1' screen.
			 */
			
			/*
			 * 10 Select User A and click on 'Save'. 'Event Template List'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				String[] strUserNameCheck = { strUserName_A, strUserName_B,
						strUserName_C, };
				String[] strUserNameSel = { strUserName_A };
				strFuncResult = objEventSetup.selectEventSecurityforUser(
						selenium, strEditEveTemp, strUserNameCheck,
						strUserNameSel);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("User "+strUserName_C+" is NOT displayed", strFuncResult);

				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 11 Login as User A, Navigate to Event>>Event Management and click
			 * on 'Create New Event'. Event template 'ET1' is listed in the
			 * 'Select Event Template' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult=	objEventSetup.navToEventManagementNew(selenium);		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strEditEveTemp + "']"));
					log4j
							.info("Event '"
									+ strEditEveTemp
									+ "' is listed on 'Event Management' screen.");
				} catch (AssertionError ae) {
					log4j
							.info("Event '"
									+ strEditEveTemp
									+ "' is NOT listed on 'Event Management' screen.");
					strFuncResult = "Event '"
							+ strEditEveTemp
							+ "' is NOT listed on 'Event Management' screen.";
					gstrReason=strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 12 Login as User B, Navigate to Event>>Event Management and click
			 * on 'Create New Event'. Event template 'ET1' is not listed in the
			 * 'Select Event Template' screen
			 */ 

			try {

				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_B, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;

				strFuncResult=	objEventSetup.navToEventManagementNew(selenium);		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				selenium.click(propElementDetails.getProperty("Event.CreateEvent"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[3][text()='"
									+ strEditEveTemp + "']"));
					log4j
							.info("Event Template '"
									+ strEditEveTemp
									+ "' is NOT listed on 'Select Event Template' screen.");
				} catch (AssertionError ae) {
					log4j
							.info("Event Template'"
									+ strEditEveTemp
									+ "' is  listed on 'Select Event Template' screen.");
					strFuncResult = "Event Template '"
							+ strEditEveTemp
							+ "' is  listed on 'Select Event Template' screen.";
					gstrReason=strFuncResult;
				}

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
			gstrTCID = "BQS-68091";
			gstrTO = "Verify that a user can be provided the"
					+ " right to maintain event templates.";
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
	'Description	:Verify that a user can be provided the right to
	'				 edit event notification 
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:8-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS98498 () throws Exception {

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
			gstrTCID = "BQS-98498  ";
			gstrTO = "Verify that a user can be provided the right "
					+ "to edit event notification ";
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
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strLoginPwd = rdExcel.readData("WebMailUser", 2, 2);
			
			String strStartDate = "";
			
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
	
			int intEMailRes = 0;
			int intPagerRes = 0;
			int intResCnt = 0;
			
			
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
			String strAdminFullname = rdExcel.readData("Login", 4, 1);
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
			String strIconSrc =  rdExcel.readInfoExcel("FirefoxTestData", 2, 3,
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

				strFuncResult =objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// navigate to Event Template
				strFuncResult = objEve.createEventTemplate(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// fill the required fields in Create Event Template and save
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strResTypeArr,
						strStatTypeArr, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strETValue = objEve.fetchETInETList(seleniumPrecondition, strEveTemp);
				

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

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objPreferences
						.navEventNotificationPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);
				String strTemplateValue[] = { strETValue };
				strFuncResult = objPreferences
						.selectEvenNofMethodsOnlyWitPageVerification(selenium,
								strTemplateValue, true, true, true);
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
					Thread.sleep(60000);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				strSubjName = strEveName;
				strMsgBody1 = "Event Notice for " + strUsrFulName_1 + ": \n"
						+ strInfo + "\nFrom: "+strAdminFullname+"" + "\nRegion: "
						+ strRegn
						+ "\n\nPlease do not reply to this email message. "
						+ "You must log into EMResource to take any "
						+ "action that may be required." + "\n"
						+ propEnvDetails.getProperty("MailURL");

				strMsgBody2 = strInfo + "\nFrom: "+strAdminFullname+""
						+ "\nRegion: " + strRegn;

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
						}else{
							log4j.info("Mail body is NOT displayed");
							strFuncResult="Mail body is NOT displayed";
							gstrReason =  strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason =strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad(gstrTimeOut);

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
						}else{
							log4j.info("Mail body is NOT displayed");
							strFuncResult="Mail body is NOT displayed";
							gstrReason =  strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason =strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad(gstrTimeOut);

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
						}else{
							log4j.info("Mail body is NOT displayed");
							strFuncResult="Mail body is NOT displayed";
							gstrReason =  strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason =strFuncResult;
					}
					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad(gstrTimeOut);
					
					selenium.click("link=Log out");
					selenium.waitForPageToLoad(gstrTimeOut);
					selenium.close();
					
					
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-98498 ";
			gstrTO = "Verify that a user can be provided the right"
					+ " to edit event notification ";
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
	'Description	:Verify that a user can be provided the right 
	'				 to activate forms.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS68358() throws Exception {
		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Forms objForms=new Forms();
		try {
			gstrTCID = "BQS-68358 ";
			gstrTO = "Verify that a user can be provided the right" +
					" to activate forms.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
		

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			// Search user criteria
			String 	strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
		
			String strFormTempTitleOF ="OF" +strTimeText;
			String strFormDesc =  "AutoDescription";

			String strQuestion="Q"+strTimeText;
			String strDescription="Description";
			String strquesTypeID="Free text field";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
		
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 1. Form F1 is created selecting 'User initiate and other to fill
			 * out' for 'Form Activation' and 'User to individual Res
			 */			
			
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
	
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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

				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleOF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*2. Questionnaire for the form is created. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(seleniumPrecondition,
						strFormTempTitleOF);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
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

			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			} 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/* 1. User U1 is created. */

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
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
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
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName_1, strFormTempTitleOF, true, true, false);

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
			 * 6 Login as user U2 and navigate to Form>>Activate Forms. Form F1
			 * is listed in the 'Activate Forms' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objForms.navActiveFormPge(selenium,
						strFormTempTitleOF);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			
			try {
				assertEquals("", strFuncResult);

				selenium
						.click("//div[@id='mainContainer']/form/table/"
								+ "tbody/tr/td[2][text()='"
								+ strFormTempTitleOF + "']/preceding-sibling"
								+ "::td/a[text()='Send Form']");
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertEquals("Activate Form: " + strFormTempTitleOF + "",
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Activate Form: " + strFormTempTitleOF
							+ " is  displayed");
				} catch (AssertionError Ae) {
					
					log4j.info("Activate Form: " + strFormTempTitleOF
							+ " is NOT displayed"+Ae);
					strFuncResult = "Activate Form: " + strFormTempTitleOF
							+ " is NOT displayed" + Ae;
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				selenium.click("css=input[value='Activate Form']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try {
					assertEquals("Region Default", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Region Default is  displayed");
					
				} catch (AssertionError Ae) {

					log4j.info("Region Default is  displayed"+Ae);
					strFuncResult = "Region Default is  displayed" + Ae;
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
			gstrTCID = "BQS-68358";
			gstrTO = "Verify that a user can be provided the right " +
					"to activate forms.";
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
	'Description	:Verify that a user can be provided the right to
	'				 configure form security.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:3-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/

	@Test
	public void testBQS68380() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
														// CreateUsers
		Forms objForms = new Forms();
		
		try {
			gstrTCID = "BQS-68380 ";
			gstrTO = "Verify that a user can be provided the right to"
					+ " configure form security.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
		

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			// Search user criteria
			String 	strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
		
			String strFormTempTitleOF ="OF" +strTimeText;
			String strFormDesc =  "AutoDescription";

			String strQuestion="Q"+strTimeText;
			String strDescription="Description";
			String strquesTypeID="Free text field";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 1. Form F1 is created selecting 'User initiate and other to fill
			 * out' for 'Form Activation' and 'User to individual Res
			 */			
			
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
	
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
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
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleOF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*2. Questionnaire for the form is created. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(seleniumPrecondition,
						strFormTempTitleOF);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*3. User U1 is created without selecting any rights and roles. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*4. User U2 is created selecting 'Form - User may activate forms' right. */
			
			
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

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				

				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

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

			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
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
						.getProperty("CreateNewUsr.AdvOptn.ConfigFormSecurity");
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
			
			/*
			 * 5 Login as user U1 and navigate to Form>Form Security, click on
			 * Security link associated with form F1. 'Form Security
			 * Settings:F1' screen is displayed.
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
			
			/*
			 * 6 Select user U2 and click on 'Save'. 'Form Security Settings'
			 * screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName_2, strFormTempTitleOF, true, true, false);

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
			 * 6 Login as user U2 and navigate to Form>>Activate Forms. Form F1
			 * is listed in the 'Activate Forms' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objForms.navActiveFormPge(selenium,
						strFormTempTitleOF);

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
			gstrTCID = "BQS-68380";
			gstrTO = "Verify that a user can be provided the right" +
					" to configure form security.";
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
	'Description	:Verify that a user can be provided the right to
	'			     view user information only.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:18-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS69126() throws Exception {

		String strFuncResult = "";
		boolean blnLogin=false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Regions objRegions = new Regions();
		
		try {
			gstrTCID = "BQS-69126 ";
			gstrTO = "Verify that a user can be provided the right "
					+ "to view user information only.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strOptions = "";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
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
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.VewUsrInfoNly");
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

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRegions.navUserListRegnInfoLnk(selenium);

				try {
					assertFalse(selenium.isElementPresent(propElementDetails
							.getProperty("CreateNewUsrLink")));
					log4j.info("CreateNewUsrLink  not present");

				} catch (AssertionError Ae) {
					strFuncResult = "CreateNewUsrLinkpresent";
					log4j.info("CreateNewUsrLinkpresent");
					gstrReason = strFuncResult;
				}

				
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
									+ "[text()='" + strUserName_1 + "']"));
					log4j
							.info("All the active users are listed in the 'Users List' screen. ");

				} catch (AssertionError Ae) {
					strFuncResult = "All the active users are NOT listed in the 'Users List' screen. ";
					log4j
							.info("All the active users are NOT listed in the 'Users List' screen. ");
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
									+ "[text()='"
									+ strUserName_1
									+ "']/parent::tr/td[1]/a[text()='Edit']"));
					log4j.info("Edit link not present");

				} catch (AssertionError Ae) {
					strFuncResult = "Edit link present";
					log4j.info("Edit link present");
					gstrReason = strFuncResult;
				}

				try {

					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
									+ "[text()='"
									+ strUserName_1
									+ "']/parent::tr/td[1]/a[text()='Password']"));
					log4j.info("Password link  not present");
				} catch (AssertionError Ae) {
					strFuncResult = "Password link present";
					log4j.info("Password link present");
					gstrReason = strFuncResult;
				}

				try {
					assertFalse(selenium.isTextPresent(propElementDetails
							.getProperty("SetUP.SetUpLink")));
					log4j.info("SetUp link  not present");

				} catch (AssertionError Ae) {
					strFuncResult = "SetUp link present";
					log4j.info("SetUp link present");
					gstrReason = strFuncResult;
				}

			} catch (AssertionError ae) {
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
			gstrTCID = "BQS-69126";
			gstrTO = "Verify that a user can be provided the right"
					+ " to view user information only.";
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

	
	/********************************************************************************
	'Description	:Verify that a user can be provided the right to edit Regional
	'				 Message Bulletin Board.
	
	'Precondition	:Message M is already created and is listed in the 'Message 
	'				 Bulletin Board'. (created within test case)
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testBQS68391() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		CreateUsers objCreateUsers=new CreateUsers();
		
		try {
			gstrTCID = "BQS-68391 ";
			gstrTO = "Verify that a user can be provided the "
					+ "right to edit Regional Message Bulletin Board.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Users mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";
			String strOptions = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);


			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			String strVerifyMsgDate = "";

			String strTempMsg = "";
			
			String strEditMsgTitle="";

			
			
			log4j
			.info("~~~~~PRE-CONDITION "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strRegn = rdExcel.readData("Login", 3, 4);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navMessageBulletin(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strTempMsg = strMsgTitle;
				strMessage = "";
				strContactEmail = "";
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");
			

				strFuncResult = objRegionalInfo.createMessage(seleniumPrecondition,
						strMsgDate, strVerifyMsgDate, strMsgTitle, strMessage,
						strContactEmail);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j
			.info("~~~~~PRE-CONDITION "+gstrTCID+" EXECUTION ENDS~~~~~");
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users.<-> 'Users
			 * List' page is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
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

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * STEP 3: Create a user U1 providing mandatory data and by selecting
			 * 'Edit Regional Message Bulletin Board' right from 'Advanced
			 * Options' section.<-> User U1 is listed in the 'Users List' screen
			 * under Setup.
			 */

			try {
				assertEquals("", strFuncResult);

				strUserName = "User" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = "Full" + strUserName;

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditMsgBultnBoard");
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

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 4: Login as user U1 and navigate to Regional Info>>Calendar.
			 * <->'Message Bulletin Board (current year)' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRegionalInfo.navMessageBulletin(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 5: Click on 'Create New Message' button and enter the
			 * mandatory data selecting current year.<-> Message is listed in
			 * the 'Message Bulletin Board (current year)' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "";
				strContactEmail = "";
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");

				strFuncResult = objRegionalInfo.createMessage(selenium,
						strMsgDate, strVerifyMsgDate, strMsgTitle, strMessage,
						strContactEmail);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 6: Click on the 'Edit' link associated with message M1 and
			 * edit the name to M2 and change the date to past or future keeping
			 * the current year.<-> Edited data is displayed in the 'Message
			 * Bulletin Board (current year)' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.getFutureDay(2, "MM/dd/yyyy");
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");

				strEditMsgTitle = strMsgTitle;

				strFuncResult = objRegionalInfo.editMessage(selenium,
						strMsgDate, strVerifyMsgDate, strMsgTitle,
						strEditMsgTitle, strMessage, strContactEmail);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 7: Click on the 'Edit' link associated with message M2 and
			 * change the year to past or future. Message M2 is not listed in
			 * the 'Message Bulletin Board (current year)' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				log4j
						.info("Edited data is displayed in the 'Message Bulletin Board"
								+ " (current year)' screen. ");

				strMsgDate = dts.getFutureYear(1, "MM/dd/yyyy");
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");

				strEditMsgTitle = strMsgTitle;

				strFuncResult = objRegionalInfo.editMessage(selenium,
						strMsgDate, strVerifyMsgDate, strMsgTitle,
						strEditMsgTitle, strMessage, strContactEmail);

			} catch (AssertionError Ae) {

				log4j
						.info("Edited data is NOT displayed in the 'Message Bulletin"
								+ " Board (current year)' screen. ");
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 8: Click on 'Delete' link associated with message M. Message
			 * M is not listed in the 'Message Bulletin Board (current year)'
			 * screen.
			 */

			try {
				assertEquals("Message title "+strEditMsgTitle+" is NOT displayed", strFuncResult);
				log4j.info("Message "+strEditMsgTitle+" is NOT  listed in the 'Message "
						+ "Bulletin Board (current year)' screen. ");

				strFuncResult = objRegionalInfo.deleteMessage(selenium,
						strTempMsg);
			} catch (AssertionError Ae) {
				log4j.info("Message "+strEditMsgTitle+" is  listed in the 'Message "
						+ "Bulletin Board (current year)' screen. ");
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
				log4j.info("Message " + strTempMsg
						+ " is NOT listed in the 'Message "
						+ "Bulletin Board (current year)' screen. ");

			} catch (AssertionError Ae) {

				log4j.info("Message " + strTempMsg
						+ " is listed in the 'Message "
						+ "Bulletin Board (current year)' screen. ");
				gstrReason = strFuncResult + "Message " + strTempMsg
						+ " is listed in the 'Message "
						+ "Bulletin Board (current year)' screen. ";
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-68391";
			gstrTO = "Verify that a user can be provided"
					+ " the right to edit Regional Message"
					+ " Bulletin Board.";
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
	//start//testBQS112141//
	/***************************************************************
	'Description	:Verify that user with only 'Edit Resource' right and without update right on resource cannot create sub-resources.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/21/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS112141() throws Exception {
		boolean blnLogin=false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers=new CreateUsers();

		try{	
			gstrTCID = "112141";	//Test Case Id	
			gstrTO = " Verify that user with only 'Edit Resource' right and without update right on resource cannot create sub-resources.";//Test Objective
			gstrReason = "";
			gstrResult = "FAIL";	

			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
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
			
			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[2];

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
			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Precondition:
			 * Status type ST1 and ST2 are created in region RG1.
			 * Resource Type (Normal) RT1 is created selecting status type ST1.
			 * Resource RS1 is created under resource type RT1.
			 * Sub-Resource SRT1 is created selecting status type ST2.
			 * User U1 is created with the following:
			 * 1. 'View Resource' right on resource RS1. 2. Role to view status
			 * types ST1 and ST2 3. 'Edit Resources' right 4. View 'V1' is
			 * created selecting ST1, ST2 and RS1 Expected Result:No Expected
			 * Result
			 */
			// 625812
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				blnLogin=true;
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
			//RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RS
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
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource1, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(seleniumPrecondition, strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(seleniumPrecondition,
						strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.fetchResTypeValueInResTypeList(seleniumPrecondition,
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
			//SRS
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				String[] strSTsvalue = {strSTvalue[0],strSTvalue[1]};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(seleniumPrecondition,
						strRoleName, strRoleRights, strSTsvalue, true,
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
			Views objViews=new Views();
			//View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0],strSTvalue[1] };
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalues, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//USER
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin=false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j
			.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION ENDS~~~~~");

		/*
		* STEP :
		  Action:Login as user U1, navigate to Setup >> Resources
		  Expected Result:Resource RS1 is not listed on 'Resource List' screen.
		*/
		//625813
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
					strInitPwd);
			blnLogin=true;
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
			strFuncResult = objRs.VerifyResource(selenium, strResource1, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Navigate to 'View >> V1' and click on resource 'RS1'
		  Expected Result:'Create New Sub-Resource' button is not available under the 'Sub-Resources' section
		*/
		//628746
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.navToUserView(selenium, strViewName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {

			assertEquals("", strFuncResult);

			strFuncResult = objViews.navToViewResourceDetailPage(selenium,
					strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			assertFalse(selenium.isElementPresent(propElementDetails.getProperty("CreateResource.ValuesubResource")));
			log4j.info("'Create New Sub-Resource' button is not available under the 'Sub-Resources' section");
		} catch (AssertionError Ae) {
			log4j.info("'Create New Sub-Resource' button is available under the 'Sub-Resources' section");
			gstrReason = strFuncResult+"'Create New Sub-Resource' button is  available under the 'Sub-Resources' section";
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
		gstrTCID = "BQS-112141";
		gstrTO = "Verify that user with only 'Edit Resource' right and without update right on resource cannot create sub-resources.";
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

	//end//testBQS112141//
		
	//start//testBQS112138//
	/*********************************************************************************
	'Description	:Verify that user with 'Edit Resource Only' right and update right
	                 on a resource can create sub-resources for their facility.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/21/2013
	'Author			:QSG
	'---------------------------------------------------------------------------------
	'Modified Date				                                           Modified By
	'Date					                                               Name
	**********************************************************************************/

	@Test
	public void testBQS112138() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		Views objViews = new Views();
		General objGeneral=new General();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "112138"; // Test Case Id
			gstrTO = "Verify that user with 'Edit Resource Only' right and update right on a resource "
					+ "can create sub-resources for their facility.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
																			// URL
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
			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[3];

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

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");

		/*
		 * STEP : Action:Precondition:Status type ST1 and ST2 are created in region RG1.
				Resource Type (Normal) RT1 is created selecting status type ST1.
				Resource RS1 and RS2 are created under resource type RT1.
				Sub-Resource SRT1 is created selecting status type ST2.
				View V1 is created selecting status types ST1 and resource RS1.
				User U1 is created with the following:
				1. 'View Resource' and 'Update resource' right on resource RS1.
				2. 'View resource' right on RS2
				3. Role to view status types ST1 and ST2
				4. 'Edit Resources' right 
		 */
		// 625812
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
			// RS
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
				strRSValue[0] = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource1);
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
				strRSValue[1]  = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource2);
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
				String[] strSTsvalue1 = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTsvalue, true, strSTsvalue1, false, true);
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
				String[] strSTvalues = { strSTvalue[0] };
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalues, false, strRSValues);
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
						seleniumPrecondition, strResource1, strRSValue[1],
						false, false, false, true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		 log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION ENDS~~~~~");

		/*
		* STEP :
		  Action:Login as user U1, navigate to Setup >> Resources 
		  Expected Result:Resources RS1 is listed on 'Resource List' screen.
                          Resources RS2 is NOT listed 
		*/
		//625813
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				blnLogin=true;
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
				strFuncResult = objRs.VerifyResource(selenium, strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.VerifyResource(selenium, strResource2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Sub-Resources' link corresponding to resource RS1. 
		  Expected Result:'Sub-Resource List for < Resource name (RS1) >' screen is displayed
                       'Create New Sub-Resource' button is available. 
		*/
		//628746
			// SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is not available under the 'Sub-Resources' section");
				} else {
					log4j.info("'Create New Sub-Resource' button is available under the 'Sub-Resources' section");
					strFuncResult = "'Create New Sub-Resource' button is  available under the 'Sub-Resources' section";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		/*
		* STEP :
		  Action:Navigate to Views >> V1 
		  Expected Result:Resource RS1 is displayed under RT1 along with status type ST1 
		*/
		//628746

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strStatType1};
				String[] strResources={strResource1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium, 
						strResrctTypName, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		* STEP :
		  Action:Click on resource RS1 
		  Expected Result:'View Resource Detail' screen is displayed.
         'Create new sub-resource' button is displayed under sub-resource section. 
		*/
		//628746
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is not available under the 'Sub-Resources' section");
				} else {
					log4j.info("'Create New Sub-Resource' button is available under the 'Sub-Resources' section");
					strFuncResult = "'Create New Sub-Resource' button is  available under the 'Sub-Resources' section";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New Sub-Resource' button. 
		  Expected Result:'Create New Sub-Resource for < resource (RS1) >' screen is displayed 
		*/
		//628746
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		* STEP :
		  Action:Provide mandatory data and click on 'Save'  
		  Expected Result:'View Resource Detail' screen for parent resource is displayed. 
		*/
		//628746
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						selenium, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndViewResDetailScreenOfRS(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


		/*
		* STEP :
		  Action:Navigate to Setup >> Resources 
		  Expected Result:'Resource List' screen is displayed.
             '(1)' is displayed for 'Sub-Resource' corresponding to resource RS1.
		*/
		//628746
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objRs.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
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
			gstrTCID = "BQS-112138";
			gstrTO = "Verify that user with 'Edit Resource Only' right and update right on a resource "
					+ "can create sub-resources for their facility.";
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

	// end//testBQS112138//
	//start//testBQS112140//
		/***************************************************************
		'Description	:Verify that user with 'Setup Resources' right and update right on a resource can create sub-resources under it.
		'Precondition	:
		'Arguments		:None
		'Returns		:None
		'Date			:6/26/2013
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
		***************************************************************/

		@Test
		public void testBQS112140() throws Exception {
			boolean blnLogin=false;
			String strFuncResult = "";
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objRs = new Resources();
			Roles objRole = new Roles();
			General objGeneral=new General();
			CreateUsers objCreateUsers=new CreateUsers();

			try {
				gstrTCID = "112140"; // Test Case Id
				gstrTO = " Verify that user with 'Setup Resources' right and update right on a resource can create sub-resources under it.";// Test
																																			// Objective
				gstrReason = "";
				gstrResult = "FAIL";

				Date_Time_settings dts = new Date_Time_settings();
				gstrTimetake = dts.timeNow("HH:mm:ss");
				seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
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
				
				String strRTValue[] = new String[2];
				String strSubResrctTypName = "AutoSRt_" + strTimeText;
				// RS
				String strResource1 = "AutoRs1_" + strTimeText;
				String strResource2 = "AutoRs2_" + strTimeText;
				String strSubResource = "AutoSRs_" + strTimeText;
				String strTmText = dts.getCurrentDate("HHmm");
				String strAbbrv = "A" + strTmText;
				String strStandResType = "Aeromedical";
				String strContFName = "auto";
				String strContLName = "qsg";
				String strResVal = "";
				String strRSValue[] = new String[3];

				//Sub Resource Icon
				String strIconImg=rdExcel.readInfoExcel("ResourceIcon", 2, 2,
						strFILE_PATH);
				
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
				log4j
						.info("~~~~~PRE-CONDITION" + gstrTCID
								+ " EXECUTION STARTS~~~~~");
		
				/*
				 * STEP : Action:Precondition:
				 * Status type ST1 and ST2 are created in region RG1.
				 * Resource Type (Normal) RT1 is created selecting status type ST1.
				 * Resource RS1 and RS2 are created under resource type RT1.
				 * Sub-Resource SRT1 is created selecting status type ST2.
				 * 
				 * User U1 is created with the following:
				 * 
				 * 1. 'View Resource' and 'Update resource' right on resource RS1.
				 * 2. 'View resource' right on RS2 3. Role to view status types ST1
				 * and ST2 4. 'Setup Resources' right Expected Result:No Expected
				 * Result
				 */
				// 625805
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);

				try {
					blnLogin=true;
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
				//RT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
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
					strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
							strResrctTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.fetchResTypeValueInResTypeList(
							seleniumPrecondition, strResrctTypName);
					if (strFuncResult.compareTo("") != 0) {

						strRTValue[0] = strFuncResult;
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch resource type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				//RS
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
					strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource1, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifyResourceInRSList(seleniumPrecondition, strResource1);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);

					if (strResVal.compareTo("") != 0) {
						strFuncResult = "";
						strRSValue[0] = strResVal;
					} else {
						strFuncResult = "Failed to fetch Resource value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				//RS2
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition, strResource2, strAbbrv, strResrctTypName, strStandResType, strContFName, strContLName);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition, strResource2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifyResourceInRSList(seleniumPrecondition, strResource2);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource2);

					if (strResVal.compareTo("") != 0) {
						strFuncResult = "";
						strRSValue[1] = strResVal;
					} else {
						strFuncResult = "Failed to fetch Resource value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				//SRT
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(seleniumPrecondition,
							strSubResrctTypName,
							"css=input[name='statusTypeID'][value='"
									+ strSTvalue[1] + "']");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
		
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSubResourceType(seleniumPrecondition, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
							strSubResrctTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.fetchResTypeValueInResTypeList(seleniumPrecondition,
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

				// Navigate to Role list
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] strSTsvalue = {strSTvalue[0],strSTvalue[1]};
					strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(seleniumPrecondition,
							strRoleName, strRoleRights, strSTsvalue, true,
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
				Views objViews=new Views();
				//View
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String[] strSTvalues = { strSTvalue[0],strSTvalue[1] };
					String[] strRSValues = { strRSValue[0] };
					strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
							strVewDescription, strViewType, true, false,
							strSTvalues, false, strRSValues);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				//USER
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
					strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
							strRoleValue, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
							seleniumPrecondition, strResource1, strRSValue[0], false, true,
							false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
							seleniumPrecondition, strResource2, strRSValue[1], false, false,
							false, true);
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
					strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
							seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
							strByUserInfo, strNameFormat);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(seleniumPrecondition);
					blnLogin=false;
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TESTCASE" + gstrTCID+ " EXECUTION ENDS~~~~~");
				
				/*
				 * STEP : Action:Login as user U1, navigate to Setup >> Resources
				 * Expected Result:Resources RS1 and RS2 are listed on 'Resource
				 * List' screen.
				 */
				// 625806
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
							strInitPwd);
					blnLogin=true;
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
					strFuncResult = objRs.VerifyResource(selenium, strResource1, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.VerifyResource(selenium, strResource2, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Click on 'Sub-Resources' link corresponding to
				 * resource RS1. Expected Result:'Sub-Resource List for < Resource
				 * name (RS1) >' screen is displayed
				 * 
				 * 'Create New Sub-Resource' button is available.
				 */
				// 625807
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToEditSubResourcePage(selenium,
							strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String strElementID = propElementDetails.getProperty("CreateResource.ValuesubResource");
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
				 * STEP : Action:Click on 'Create New Sub-Resource' button. Expected
				 * Result:'Create New Sub-Resource for < resource (RS1) >' screen is
				 * displayed
				 */
				// 625808
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToCreateSubResourcePage(selenium,
							strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * STEP : Action:Provide mandatory data and click on 'Save' Expected
				 * Result:Newly created sub-resource is listed on 'Sub-Resource List
				 * for < resource name (RS1) >' screen is displayed.
				 * 
				 * Data provided while creating are displayed appropriately under
				 * the following columns: 1. Action ('Edit' link is available) 2.
				 * Icon (displayed as selected for standard resource type) 3. Name
				 * 4. Abbreviation 5. Resource type (Sub-resource type name)
				 */
				// 625809
				try {
					assertEquals("", strFuncResult);
					String strState = "Alabama";
					String strCountry = "Barbour County";
					strFuncResult = objRs.createSubResourceWitLookUPadres(
							selenium, strSubResource, strAbbrv,
							strSubResrctTypName, strStandResType, true,
							strContFName, strContLName, strState, strCountry);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.saveAndVerifySubResourceInRSList(
							selenium, strSubResource, strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strResVal = objRs.fetchSubResValueInResList(
							selenium, strSubResource);

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
					String strElementID = "//div[@id='mainContainer']/table"
							+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
							+ "[text()='" + strSubResource
							+ "']/following-sibling::td[2]" + "[text()='"
							+ strSubResrctTypName + "']";
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElementID);

					if (strFuncResult.equals("")) {
						log4j.info("Created Sub-resource is listed under it.");
					} else {
						strFuncResult = "Created Sub-resource is NOT listed under it.";
						log4j.info("Created Sub-resource is NOT listed under it.");
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.verifySubResDataInRSListPge(selenium, strSubResource, strAbbrv, strSubResrctTypName,strIconImg);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
		/*
		 * STEP : Action:Navigate to Setup >> Resources Expected
		 * Result:'Resource List' screen is displayed.
		 * 
		 * '(1)' is displayed for 'Sub-Resource' corresponding to resource
		 * RS1.
		 */
		// 625810
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objRs.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on 'Sub-Resources' link corresponding to
		 * resource RS2. Expected Result:'Sub-Resource List for < Resource
		 * name (RS2) >' screen is displayed
		 * 
		 * 'Create New Sub-Resource' button is available.
		 */
		// 625811
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToEditSubResourcePage(selenium,
							strResource2);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strElementID = propElementDetails.getProperty("CreateResource.ValuesubResource");
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
					gstrResult = "PASS";
				} catch (AssertionError Ae) {
					gstrResult = "FAIL";
					gstrReason = gstrReason + " " + strFuncResult;
				}

				log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
				log4j.info("----------------------------------------------------------");

			} catch (Exception e) {
				gstrTCID = "BQS-112140";
				gstrTO = "Verify that user with 'Setup Resources' right and update right on a resource can create sub-resources under it.";
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

		//end//testBQS112140//
		
		//start//testBQS112339//
	/********************************************************************************
	'Description	:Verify that user with 'Setup Resources' right and without update 
	                 right on a resource can create sub-resources under it.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/2/2013
	'Author			:QSG
	'--------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*********************************************************************************/

	@Test
	public void testBQS112339() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		General objGeneral=new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "112339"; // Test Case Id
			gstrTO = " Verify that user with 'Setup Resources' right and without update right on " +
					"a resource can create sub-resources under it.";//TO																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
																			// URL
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

			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strSubResource2 = "AutoSRs2_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strResVal = "";
			String strRSValue[] = new String[2];

			// Sub Resource Icon
			String strIconImg = rdExcel.readInfoExcel("ResourceIcon", 2, 2,strFILE_PATH);

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
			
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");

		/*
		* STEP :
		  Action:Precondition:
			Status type ST1 and ST2 are created in region RG1.
			Resource Type (Normal) RT1 is created selecting status type ST1.
			Resource RS1 is created under resource type RT1.
			Sub-Resource SRT1 is created selecting status type ST2.
			User U1 is created with the following:
			1. 'View Resource' right on resource RS1.
			2. Role to view status types ST1 and ST2
			3. 'Setup Resources' right
			4. View 'V1' is created selecting 'ST1', 'ST2' and 'RS1'
						  Expected Result:No Expected Result
		*/
		//625815
			
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
			// RS
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
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
						strSTsvalue, true, strSTvalue, false, true);
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
			Views objViews = new Views();
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
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalues, false, strRSValues);
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
			
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TESTCASE" + gstrTCID+ " EXECUTION ENDS~~~~~");
		/*
		* STEP :
		  Action:Login as user U1, navigate to Setup >> Resources
		  Expected Result:Resource RS1 is listed on 'Resource List' screen.
		*/
		//625816

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				blnLogin=true;
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
				strFuncResult = objRs.VerifyResource(selenium, strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on 'Sub-Resources' link corresponding to resource RS1.
		  Expected Result:'Sub-Resource List for < Resource name (RS1) >' screen is displayed
          'Create New Sub-Resource' button is available.
		*/
		//625817
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails.getProperty("CreateResource.ValuesubResource");
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
		* STEP :
		  Action:Click on 'Create New Sub-Resource' button.
		  Expected Result:'Create New Sub-Resource for < resource (RS1) >' screen is displayed
		*/
		//625889
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide mandatory data and click on 'Save'
		  Expected Result:Newly created sub-resource is listed on 'Sub-Resource List for < resource name 
		  (RS1) >' screen along with appropriate values displayed under the respective column that
		   was provided while creating a sub resource
		*/
		//625890

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						selenium, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						selenium, strSubResource);

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
				String strElementID = "//div[@id='mainContainer']/table"
						+ "[@summary='Sub-Resources']/tbody/tr/td[3]"
						+ "[text()='" + strSubResource
						+ "']/following-sibling::td[2]" + "[text()='"
						+ strSubResrctTypName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Newly created sub-resource is listed on 'Sub-Resource List for < resource name ("
							+ strResource1 + ") >'.");
				} else {
					strFuncResult = "Newly created sub-resource is listed on 'Sub-Resource List for < resource name ("
							+ strResource1 + ") >'.";
					log4j.info("Newly created sub-resource is listed on 'Sub-Resource List for < resource name ("
							+ strResource1 + ") >'.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifySubResDataInRSListPge(selenium,
						strSubResource, strAbbrv, strSubResrctTypName,
						strIconImg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:'Resource List' screen is displayed.
         '(1)' is displayed for 'Sub-Resource' corresponding to resource RS1.
		*/
		//625891

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objRs.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Navigate to 'View >> V1' and click on resource 'RS1'
		  Expected Result:'View Resource Detail' screen is displayed.
				  'Create new sub-resource' button is displayed under sub-resource section.
		*/
		//628747

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
				String strElementID = propElementDetails.getProperty("CreateResource.ValuesubResource");
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
		* STEP :
		  Action:Click on 'Create New Sub-Resource' button.
		  Expected Result:'Create New Sub-Resource for < resource (RS1) >' screen is displayed
		*/
		//628748
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide mandatory data and click on 'Save'
		  Expected Result:'View Resource Detail' screen for parent resource is displayed.
		*/
		//628749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(selenium,
						strSubResource2, strAbbrv, strSubResrctTypName,
						strStandResType, true, strContFName, strContLName,
						strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndViewResDetailScreenOfRS(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:'Resource List' screen is displayed.
				  '(2)' is displayed for 'Sub-Resource' corresponding to resource RS1.
		*/
		//628750

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (2)";
				strFuncResult = objRs.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
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
			gstrTCID = "BQS-112339";
			gstrTO = "Verify that user with 'Setup Resources' right and without update right on a resource can create sub-resources under it.";
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

	// end//testBQS112339//
}
	
