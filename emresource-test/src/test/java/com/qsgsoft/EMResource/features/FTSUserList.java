package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description         :This class includes  requirement testcases
' Requirement Group   :Creating & managing users 
' Requirement         :User list
' Date		          :06-nov-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                        Modified By
' <Date>                           	                     <Name>
'*******************************************************************/
public class FTSUserList {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSUserList");
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
	public Properties propPathDetails;
	String gstrTimeOut="";
	
	Selenium selenium;


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

/***************************************************************
'Description		:Verify that last login date and time is updated accordingly for a user in the 'Users List' screen.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:11/6/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/
	@SuppressWarnings("unused")
	@Test
	public void testFTS91319() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		try {
			gstrTCID = "91319"; // Test Case Id
			gstrTO = " Verify that last login date and time is updated accordingly " +
					"for a user in the 'Users List' screen.";
			gstrReason = "";
			gstrResult = "FAIL";
			
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// New user
			String strUserNameNew = "AutoUsrNew" + System.currentTimeMillis();
			String strUsrFulNameNew = "FN" + strUserNameNew;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// event time data
			String[] strArFunRes = new String[5];
			String strCurYear = dts.getTimeOfParticularTimeZone("CST", "yyyy");;
			String strRegGenTime = "";
			String strGenDate = "";

			String strGenTimeHrs = "";
			String strGenTimeMin = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " Starts----------");

			// Precondition
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user
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
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as RegAdmin,Navigate to setup>> users
			 * Expected Result:'User List' screen is displayed with all the
			 * users in the region. Users are listed under following columns: a.
			 * Actions b. Username c. Full Name d. Organization e. Last Login
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyActionFieldForUser(
						selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strlog = "(never)";
				strFuncResult = objCreateUsers.verifyUserDetailsInUserList(
						selenium, strUserName, strUsrFulName, "");
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
									+ strUserName
									+ "']"
									+ "/parent::tr/td[5][text()='"
									+ strlog
									+ "']"));
					log4j.info(strlog
							+ "is displayed under 'Last Login' column.");
				} catch (AssertionError Ae) {
					strFuncResult = strlog
							+ "is NOT displayed under 'Last Login' column.";
					log4j.info(strlog
							+ "is NOT displayed under 'Last Login' column.");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New User', Create user 'U1' by
			 * filling appropriate data in the mandatory fields. Expected
			 * Result:User list 'screen' is displayed with all the active users
			 * in the region and 'user U1' is listed under it. Columns under
			 * 'User List' screen are updated accordingly. 'never' is displayed
			 * for user 'U1' under last login column.
			 */
			// 554596
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameNew, strInitPwd, strConfirmPwd,
						strUsrFulNameNew);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameNew, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strlog = "(never)";
				strFuncResult = objCreateUsers.verifyUserDetailsInUserList(
						selenium, strUserNameNew, strUsrFulNameNew, "");
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
									+ strUserNameNew
									+ "']"
									+ "/parent::tr/td[5][text()='"
									+ strlog
									+ "']"));
					log4j.info(strlog
							+ "is displayed under 'Last Login' column.");
				} catch (AssertionError Ae) {
					strFuncResult = strlog
							+ "is NOT displayed under 'Last Login' column.";
					log4j.info(strlog
							+ "is NOT displayed under 'Last Login' column.");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and Login as user 'U1' Expected Result:User
			 * is taken to the 'Change Password' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameNew,
						strInitPwd);
				try {
					assertEquals("Region Default", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("User default region is opened");
				} catch (AssertionError Ae) {
					strFuncResult = "User default region is NOT opened" + Ae;
					log4j.info("User default region is NOT opened" + Ae);
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide data in 'New Password' and 'Verify
			 * Password' fields and click on 'Submit' Expected Result:User U1 is
			 * successfully logged in and 'Region Default' screen is displayed.
			 * (Note down the application date and time displayed at right side
			 * footer of the screen)
			 */
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Logout and Login as user RegAdmin,Navigate to
			 * setup>> users Expected Result:'User List' screen is displayed
			 * with all the users in the region. Lost login field for user 'U1'
			 * is updated with the login date and time.
			 */
			// 554612
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
				assertEquals("", strFuncResult);// 11/06/12
				String strlog = strGenDate + " " + strRegGenTime;
				strFuncResult = objCreateUsers.verifyUserDetailsInUserList(
						selenium, strUserNameNew, strUsrFulNameNew, "");
				try {
					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
									+ strUserNameNew
									+ "']"
									+ "/parent::tr/td[5][text()='"
									+ strlog
									+ "']"));
					log4j.info(strlog
							+ "is displayed under 'Last Login' column.");
				} catch (AssertionError Ae) {
					strFuncResult = strlog
							+ "is NOT displayed under 'Last Login' column.";
					log4j.info(strlog
							+ "is NOT displayed under 'Last Login' column.");
					gstrReason = strFuncResult;
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91319";
			gstrTO = "Verify that last login date and time is updated accordingly for a user in the 'Users List' screen.";
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

/***********************************************************************************
'Description		:Verify that all the inactive users are listed on 'Users List'
'					 screen when 'include inactive users' check box is selected.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:11/30/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testFTS91318() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		try {
			gstrTCID = "91318"; // Test Case Id
			gstrTO = " Verify that all the inactive users are listed on 'Users List' screen when 'include"
					+ " inactive users' check box is selected.";
																
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");


			// user
			String strUserName1 = "IActiveU_1" + System.currentTimeMillis();
			String strUserName2 = "IActiveU_2" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = "FN" + strUserName1;
			String strUsrFulName2 = "FN" + strUserName2;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			/*
			 * STEP : Action:Preconditions: 1. Role 'R1' is created. 2. Active
			 * user 'U1' and 'User A' is created providing data in all fields
			 * and selecting role 'R1'. 3. Inactive user 'User B' is created
			 * providing date in all fields and selecting role 'R1'. (Make sure
			 * no other inactive user is present in the region) Expected
			 * Result:No Expected Result
			 */
			// 551170
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " Starts----------");

			// Precondition
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*	// New region creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN, "",
						"", "", "", "", "", strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
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
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						selenium, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);

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

			// user1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "FirstName", "MiddleNmae", "LastName", "QSG",
						"111222333444", strEMail, strEMail, strEMail,
						"AutoUser");
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
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName2,
								strInitPwd, strConfirmPwd, strUsrFulName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, "FirstName1", "MiddleNmae1", "LastName1",
						"QSG1", "1112223334441", strEMail, strEMail, strEMail,
						"AutoUser2");
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
						selenium, strUserName2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserName2, strUsrFulName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as RegAdmin,Navigate to setup>> users
			 * Expected Result:'User list' screen is displayed with all the
			 * active users in the region. Create New User button is present at
			 * top right corner of screen. 'Include inactive users' check box is
			 * present at left corner of the screen and is unchecked by default,
			 * total number of users in that region is displayed next to check
			 * box. (Note:Note down the number of users)
			 */
			// 551184
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName1 + "']"));
				log4j.info("Active User " + strUserName1
						+ " Present in the User List ");

			} catch (AssertionError Ae) {
				strFuncResult = "Active User " + strUserName1
						+ " NOT Present in the User List " + Ae;
				log4j.info("Active User " + strUserName1
						+ " NOT Present in the User List " + Ae);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateNewUsrLink");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j
						.info("Create New User button is present at top right corner of screen. ");
			} catch (AssertionError Ae) {
				strFuncResult = " Create New User button is NOT present at top right corner of screen."
						+ Ae;
				log4j
						.info("Create New User button is present NOT at top right corner of screen. "
								+ Ae);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName1 + "']"));
				log4j.info("Active User " + strUserName1
						+ " Present in the User List ");

			} catch (AssertionError Ae) {
				strFuncResult = "Active User " + strUserName1
						+ " NOT Present in the User List " + Ae;
				log4j.info("Active User " + strUserName1
						+ " NOT Present in the User List " + Ae);
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verfyUsrCountOfActiveandInactiveUsers(selenium, true,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select 'Include inactive user' check box. Expected
			 * Result:'User list' screen is displayed with all the active and
			 * inactive users in the region. 'Include inactive users' check box
			 * is present at left corner of the screen and is checked , total
			 * number of users in that region is displayed next to check box.
			 * Total number of users is incremented by one.
			 */
			// 551185
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verfyUsrCountOfActiveandInactiveUsers(selenium, true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(selenium
						.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
								+ "td[2][text()='" + strUserName1 + "']"));
				assertTrue(selenium
						.isElementPresent("//td[@class='inactive'][contains(text(),'"
								+ strUsrFulName2 + "')]"));
				log4j.info("Active and inactive users are displayed "
						+ "on the 'Users List' screen.");
			} catch (AssertionError Ae) {
				strFuncResult = "Active and inactive users are NOT displayed"
						+ " on the 'Users List' screen. ";
				log4j.info("Active and inactive users are NOT displayed "
						+ "on the 'Users List' screen.");
				gstrReason =  strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason =  strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91318";
			gstrTO = "Verify that all the inactive users are listed on 'Users List' screen when 'include inactive users' check box is selected.";
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
	
	
	/***********************************************************************************
	'Description		:Verify that user created is listed on the 'Users List' screen. 
	'Precondition		:1. Role 'R1' is created.
						 2. Active user 'User A' is created providing data in all fields.
						 3. Inactive user 'User B' is created. 
	'Arguments		:None
	'Returns		:None
	'Date			:5-Dec-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS91316() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		try {
			gstrTCID = "91316"; // Test Case Id
			gstrTO = "Verify that user created is listed on the 'Users List' screen.";

			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strUserNameActive = "ActiveUsr" + System.currentTimeMillis();
			String strUsrFulNameActive = strUserNameActive;


			String strUserName1 = "BUsr1" + System.currentTimeMillis();
			String strUsrFulName1 = strUserName1;

			String strUserNameInActive = "InActiveUsr"
					+ System.currentTimeMillis();
			String strUsrFulNameInActive = strUserNameInActive;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strOrganization="";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";
			String strUsrCntBefr="";
			String strUsrCntAfter="";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " Starts----------");

			// Precondition
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 1. Role 'R1' is created. */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(selenium,
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
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium, strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. Active user 'User A' is created providing data in all fields. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameActive, strInitPwd, strConfirmPwd,
						strUsrFulNameActive);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strFirstName = "Auto";
				String strMiddleName = "QSG";
				String strLastName = "Auto";
				strOrganization = "QSG";
				String strPhoneNo = "56898956059";
				String strAdminComments = "Comments";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strEMail, strEMail,
						strEMail, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameActive, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*3. Inactive user 'User B' is created. */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameInActive, strInitPwd, strConfirmPwd,
						strUsrFulNameInActive);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameInActive, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserNameInActive, strUsrFulNameInActive, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Login as RegAdmin,Navigate to setup>> users User list 'screen'
			 * is displayed with all the active users in the region and 'user A'
			 * is listed under it.
			 * 
			 * Create New User button is present at top left corner of screen.
			 * 
			 * 'Include inactive users' check box is present at right corner of
			 * the screen and unchecked by default and is enabled , total number
			 * of users in that region is displayed next to check box.
			 * (Note:Note down the number of users)
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/"
						+ "td[2][text()='" + strUserNameActive + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/td[3]" +
						"[@class='inactive'][text()='"+strUsrFulNameInActive+"']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("User list 'screen' is displayed with all the active users in "
								+ "the region and 'user A' is listed under it. ");
				
				String strElementID = "css=input[value='Create New User']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j
						.info("User list 'screen' is NOT displayed with all the active users in "
								+ "the region and 'user A' is listed under it. ");
				strFuncResult = "User list 'screen' is NOT displayed with all the active users in "
						+ "the region and 'user A' is listed under it. ";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Create New User button is present ");
				strFuncResult = objCreateUsers
						.verfyUsrCountOfActiveandInactiveUsers(selenium, false,
								false, true);
				
				strUsrCntBefr = selenium.getText("//td[@align='right']/"
						+ "span[@id='count']");
				String[] strUsrCntArray = strUsrCntBefr.split(" ");
				strUsrCntBefr = strUsrCntArray[0];
				
			} catch (AssertionError Ae) {

				log4j.info("Create New User button is NOT present ");
				strFuncResult = "Create New User button is NOT present ";
				gstrReason = strFuncResult;
			}
			
			
			/*3 	Click on 'Create New User' button. 		Create New User screen is displayed 
			 * with following fields:

				1. User profile

				Username:
				Initial Password:
				Confirm Password:
				Full Name:
				First Name:
				Middle Name:
				Last Name:
				Organization:
				Contact Phone:
				Primary E-Mail:
				E-Mail Addresses (comma separate multiple addresses):
				Text Pager Addresses (comma separate multiple addresses):
				Administrative Comments:

				2. User Type & Roles

				Web Services User: Check if this user can be used for web service requests (check box)

				Roles : (check box)

				3. Views
				Default View:(drop down)
				Views in This Region:(check box)

				4. Resource Rights Search:
				(Any Resource Type)
				with following search boxes
				-Resource Name
				-Contains
				-Equals
				-Starts With
				-Ends With
				-Not Equal To

				5. User Preferences
				Receive expired status notifications: E-Mail Pager (Check boxes)
				Receive notifications for successful HHS HAvBED transmissions: E-Mail Pager (check boxes)
				Receive notifications for failed HHS HAvBED transmissions: E-Mail Pager (check boxes)

				6. Advanced Options */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varUsrProfileFldsInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varUsrTypeAndRolesInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varViewsInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varResourceRightsInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varAdvanceOptnInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varUsrPreferenceInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
				
			
			/*4 	Create user 'U1' by filling appropriate data in the following fields:
				1. Username
				2. Full Name
				3. Organization
				4. Phone
				5. Emails

				Provide role 'R1' and click on 'Save'. 		'User list' screen is displayed with all the active users

				Data provided for user 'U1' is displayed properly under the columns �Actions�, �Username�, �Full Name�, �Organization� and �Last login�.

				User B is not listed in the users list.

				Total number of users is incremented by one.

				�Roles�, �Phone� and �Emails� columns are not available in user list 'screen'.

				Print, Export and Help links are displayed at right corner of screen.

				User 'U1' is placed in the list based on the alphabetical order of Full Name of users in region.

				Option to search the users is available.

				
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
						strUserName1, strInitPwd, strConfirmPwd,
						strUsrFulName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strFirstName = "Auto";
				String strMiddleName = "QSG";
				String strLastName = "Auto";
				strOrganization = "QSG";
				String strPhoneNo = "56898956059";
				String strAdminComments = "Comments";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strEMail, strEMail,
						strEMail, strAdminComments);

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
						selenium, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifyUserDetailsInUserListAlonWithHeaders(selenium,
								strUserName1, strUsrFulName1, strOrganization,
								"(never)");
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
				String strElementID = "//table[@id='tblUsers']/tbody/tr/td[3]" +
						"[@class='inactive'][text()='"+strUsrFulNameInActive+"']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j.info("User " + strUsrFulNameInActive
						+ " is NOT listed in the users list. ");

				strUsrCntAfter = selenium.getText("//td[@align='right']/"
						+ "span[@id='count']");
				String[] strUsrCntArray = strUsrCntAfter.split(" ");
				strUsrCntAfter = strUsrCntArray[0];

				int intUserCntBefr = Integer.parseInt(strUsrCntBefr);
				int intUserCntAfter = Integer.parseInt(strUsrCntAfter);

				if (intUserCntAfter == intUserCntBefr + 1) {
					strFuncResult = "";
					log4j.info("Total number of users is incremented by one. ");
				} else {
					log4j
							.info("Total number of users is NOT incremented by one. ");
					strFuncResult = "Total number of users is NOT incremented by one. ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				log4j.info("User " + strUsrFulNameInActive
						+ " is listed in the users list. ");
				strFuncResult = "User " + strUsrFulNameInActive
						+ " is listed in the users list. ";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifyInUserListPgeHeadersFalseCondition(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElements[] = { "link=print", "link=export",
						"link=help" };

				for (int i = 0; i < strElements.length; i++) {
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElements[i]);
				}
				if (strFuncResult.compareTo("") == 0) {
					log4j.info("Print, Export and Help links are displayed"
							+ " at right corner of screen. ");
				} else {
					log4j.info("Print, Export and Help links are NOT displayed"
							+ " at right corner of screen. ");
					strFuncResult = "Print, Export and Help links are NOT displayed"
							+ " at right corner of screen. ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateNewUsr.Search");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				log4j.info("Option to search the users is available. ");

				int intXpathCnt1 = selenium.getXpathCount(
						"//table[@id='tblUsers']" + "/tbody/tr/td[2][text()='"
								+ strUserNameActive + "']"
								+ "/parent::tr/preceding-sibling::tr")
						.intValue();

				int intXpathCnt2 = selenium.getXpathCount(
						"//table[@id='tblUsers']" + "/tbody/tr/td[2][text()='"
								+ strUserName1 + "']"
								+ "/parent::tr/preceding-sibling::tr")
						.intValue();

				if (intXpathCnt2 > intXpathCnt1) {
					log4j
							.info("User '"
									+ strUserName1
									+ "' is placed in the list based"
									+ " on the alphabetical order of Full Name of users in region. ");
				} else {
					log4j
							.info("User '"
									+ strUserName1
									+ "' is NOT placed in the list based"
									+ " on the alphabetical order of Full Name of users in region. ");
					strFuncResult = "User '"
							+ strUserName1
							+ "' is NOT placed in the list based"
							+ " on the alphabetical order of Full Name of users in region. ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				log4j.info("Option to search the users is NOT available. ");
				strFuncResult = "Option to search the users is NOT available. ";
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
			gstrTCID = "91316";
			gstrTO = "Verify that user created is listed on the 'Users List' screen.";
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
	
	/***********************************************************************************
	'Description		:Verify that the user details edited are updated on the 'Users List' screen.
	'Precondition		:1. Role 'R1' is created.
						 2. Active user 'User A' is created providing data in all fields.
						 3. Inactive user 'User B' is created. 
	'Arguments		:None
	'Returns		:None
	'Date			:7-Dec-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS91317() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		try {
			gstrTCID = "91317"; // Test Case Id
			gstrTO = "Verify that the user details edited are updated on the 'Users List' screen.";

			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strUserNameActive = "ActiveUsr" + System.currentTimeMillis();
			String strUsrFulNameActive = strUserNameActive;


			String strUserName1 = "BUsr1" + System.currentTimeMillis();
			String strUsrFulName1 = strUserName1;

					
			String strUserNameInActive = "InActiveUsr"
					+ System.currentTimeMillis();
			String strUsrFulNameInActive = strUserNameInActive;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strOrganization="";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";
			String strUsrCntBefr="";
			String strUsrCntAfter="";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " Starts----------");

			// Precondition
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 1. Role 'R1' is created. */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(selenium,
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
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium, strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. Active user 'User A' is created providing data in all fields. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameActive, strInitPwd, strConfirmPwd,
						strUsrFulNameActive);

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
				String strFirstName = "Auto";
				String strMiddleName = "QSG";
				String strLastName = "Auto";
				strOrganization = "QSG";
				String strPhoneNo = "56898956059";
				String strAdminComments = "Comments";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strEMail, strEMail,
						strEMail, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameActive, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			/*3. Inactive user 'User B' is created. */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameInActive, strInitPwd, strConfirmPwd,
						strUsrFulNameInActive);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strFirstName = "Auto";
				String strMiddleName = "QSG";
				String strLastName = "Auto";
				strOrganization = "QSG";
				String strPhoneNo = "56898956059";
				String strAdminComments = "Comments";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strEMail, strEMail,
						strEMail, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameInActive, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.deactivateUser(selenium,
						strUserNameInActive, strUsrFulNameInActive, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Login as RegAdmin,Navigate to setup>> users User list 'screen'
			 * is displayed with all the active users in the region and 'user A'
			 * is listed under it.
			 * 
			 * Create New User button is present at top left corner of screen.
			 * 
			 * 'Include inactive users' check box is present at right corner of
			 * the screen and unchecked by default and is enabled , total number
			 * of users in that region is displayed next to check box.
			 * (Note:Note down the number of users)
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/"
						+ "td[2][text()='" + strUserNameActive + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/td[3]" +
						"[@class='inactive'][text()='"+strUsrFulNameInActive+"']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("User list 'screen' is displayed with all the active users in "
								+ "the region and 'user A' is listed under it. ");
				
				String strElementID = "css=input[value='Create New User']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j
						.info("User list 'screen' is NOT displayed with all the active users in "
								+ "the region and 'user A' is listed under it. ");
				strFuncResult = "User list 'screen' is NOT displayed with all the active users in "
						+ "the region and 'user A' is listed under it. ";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Create New User button is present ");
				strFuncResult = objCreateUsers
						.verfyUsrCountOfActiveandInactiveUsers(selenium, false,
								false, true);
				
				strUsrCntBefr = selenium.getText("//td[@align='right']/"
						+ "span[@id='count']");
				String[] strUsrCntArray = strUsrCntBefr.split(" ");
				strUsrCntBefr = strUsrCntArray[0];
				
			} catch (AssertionError Ae) {

				log4j.info("Create New User button is NOT present ");
				strFuncResult = "Create New User button is NOT present ";
				gstrReason = strFuncResult;
			}
			
			
			/*3 	Click on 'Create New User' button. 		Create New User screen is displayed 
			 * with following fields:

				1. User profile

				Username:
				Initial Password:
				Confirm Password:
				Full Name:
				First Name:
				Middle Name:
				Last Name:
				Organization:
				Contact Phone:
				Primary E-Mail:
				E-Mail Addresses (comma separate multiple addresses):
				Text Pager Addresses (comma separate multiple addresses):
				Administrative Comments:

				2. User Type & Roles

				Web Services User: Check if this user can be used for web service requests (check box)

				Roles : (check box)

				3. Views
				Default View:(drop down)
				Views in This Region:(check box)

				4. Resource Rights Search:
				(Any Resource Type)
				with following search boxes
				-Resource Name
				-Contains
				-Equals
				-Starts With
				-Ends With
				-Not Equal To

				5. User Preferences
				Receive expired status notifications: E-Mail Pager (Check boxes)
				Receive notifications for successful HHS HAvBED transmissions: E-Mail Pager (check boxes)
				Receive notifications for failed HHS HAvBED transmissions: E-Mail Pager (check boxes)

				6. Advanced Options */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varUsrProfileFldsInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varUsrTypeAndRolesInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varViewsInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varResourceRightsInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varAdvanceOptnInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.varUsrPreferenceInCreateUserPge(selenium);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
				
			
			/*4 	Create user 'U1' by filling appropriate data in the following fields:
				1. Username
				2. Full Name
				3. Organization
				4. Phone
				5. Emails

				Provide role 'R1' and click on 'Save'. 		'User list' screen is displayed with all the active users

				Data provided for user 'U1' is displayed properly under the columns �Actions�, �Username�, �Full Name�, �Organization� and �Last login�.

				User B is not listed in the users list.

				Total number of users is incremented by one.

				�Roles�, �Phone� and �Emails� columns are not available in user list 'screen'.

				Print, Export and Help links are displayed at right corner of screen.

				User 'U1' is placed in the list based on the alphabetical order of Full Name of users in region.

				Option to search the users is available.

				
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
						strUserName1, strInitPwd, strConfirmPwd,
						strUsrFulName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strFirstName = "Auto";
				String strMiddleName = "QSG";
				String strLastName = "Auto";
				strOrganization = "QSG";
				String strPhoneNo = "56898956059";
				String strAdminComments = "Comments";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strEMail, strEMail,
						strEMail, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
		/*	try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/"
						+ "td[2][text()='" + strUserName1 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/td[3]" +
						"[@class='inactive'][text()='"+strUsrFulNameInActive+"']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("User list 'screen' is displayed with all the active users in "
								+ "the region");
				
				String strElementID = "css=input[value='Create New User']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j
						.info("User list 'screen' is NOT displayed with all the active users in "
								+ "the region");
				strFuncResult = "User list 'screen' is NOT displayed with all the active users in "
						+ "the region";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Create New User button is present ");
				strFuncResult = objCreateUsers
						.verfyUsrCountOfActiveandInactiveUsers(selenium, false,
								false, true);
				
				
				
			} catch (AssertionError Ae) {

				log4j.info("Create New User button is NOT present ");
				strFuncResult = "Create New User button is NOT present ";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
			
				strUsrCntAfter = selenium.getText("//td[@align='right']/"
						+ "span[@id='count']");
				String[] strUsrCntArray = strUsrCntAfter.split(" ");
				strUsrCntAfter = strUsrCntArray[0];

				int intUserCntBefr = Integer.parseInt(strUsrCntBefr);
				int intUserCntAfter = Integer.parseInt(strUsrCntAfter);

				if (intUserCntAfter == intUserCntBefr + 1) {
					strFuncResult = "";
					log4j.info("Total number of users is incremented by one. ");
					strUsrCntBefr=strUsrCntAfter;
				} else {
					log4j
							.info("Total number of users is NOT incremented by one. ");
					strFuncResult = "Total number of users is NOT incremented by one. ";
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
				strFuncResult = objCreateUsers.navToEditUserPage(selenium, strUserName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strUsrEdFulName="Ed_"+strUsrFulName1;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrFulName(selenium, strUsrEdFulName);
					

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifyUserDetailsInUserListAlonWithHeaders(selenium,
								strUserName1, strUsrEdFulName, strOrganization,
								"(never)");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/"
						+ "td[2][text()='" + strUserName1 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/td[3]" +
						"[@class='inactive'][text()='"+strUsrFulNameInActive+"']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
						
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("User list 'screen' is displayed with all the active users in "
								+ "the region");
				
				strFuncResult = objCreateUsers
				.verfyUsrCountOfActiveandInactiveUsers(selenium, false,
						false, true);

			} catch (AssertionError Ae) {
				log4j
						.info("User list 'screen' is NOT displayed with all the active users in "
								+ "the region");
				strFuncResult = "User list 'screen' is NOT displayed with all the active users in "
						+ "the region";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				

				strUsrCntAfter = selenium.getText("//td[@align='right']/"
						+ "span[@id='count']");
				String[] strUsrCntArray = strUsrCntAfter.split(" ");
				strUsrCntAfter = strUsrCntArray[0];

				int intUserCntBefr = Integer.parseInt(strUsrCntBefr);
				int intUserCntAfter = Integer.parseInt(strUsrCntAfter);

				if (intUserCntAfter == intUserCntBefr ) {
					strFuncResult = "";
					log4j.info("Total number of users is not incremented by one. ");			
					
				} else {
					log4j
					.info("Total number of users is incremented by one. ");
					strFuncResult = "Total number of users is incremented by one. ";
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
				String strElementID = "//table[@id='tblUsers']/tbody/tr/"
						+ "td[2][text()='" + strUserNameActive + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/"
						+ "td[2][text()='" + strUserName1 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tblUsers']/tbody/tr/td[3]" +
						"[@class='inactive'][text()='"+strUsrFulNameInActive+"']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j.info("User " + strUsrFulNameInActive
						+ " is NOT listed in the users list. ");
				
				strFuncResult = objCreateUsers
				.verifyInUserListPgeHeadersFalseCondition(selenium);
				
			} catch (AssertionError Ae) {
				log4j.info("User " + strUsrFulNameInActive
						+ " is listed in the users list. ");
				strFuncResult = "User " + strUsrFulNameInActive
						+ " is listed in the users list. ";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifyUserDetailsInUserListAlonWithHeaders(selenium,
								strUserName1, strUsrEdFulName, strOrganization,
								"(never)");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.verifyUserDetailsInUserListAlonWithHeaders(selenium,
								strUserNameActive, strUsrFulNameActive, strOrganization,
								"(never)");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				String strElements[] = { "link=print", "link=export",
						"link=help" };

				for (int i = 0; i < strElements.length; i++) {
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElements[i]);
				}
				if (strFuncResult.compareTo("") == 0) {
					log4j.info("Print, Export and Help links are displayed"
							+ " at right corner of screen. ");
				} else {
					log4j.info("Print, Export and Help links are NOT displayed"
							+ " at right corner of screen. ");
					strFuncResult = "Print, Export and Help links are NOT displayed"
							+ " at right corner of screen. ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateNewUsr.Search");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				log4j.info("Option to search the users is available. ");

				int intXpathCnt1 = selenium.getXpathCount(
						"//table[@id='tblUsers']" + "/tbody/tr/td[2][text()='"
								+ strUserNameActive + "']"
								+ "/parent::tr/preceding-sibling::tr")
						.intValue();

				int intXpathCnt2 = selenium.getXpathCount(
						"//table[@id='tblUsers']" + "/tbody/tr/td[2][text()='"
								+ strUserName1 + "']"
								+ "/parent::tr/preceding-sibling::tr")
						.intValue();

				if (intXpathCnt2 > intXpathCnt1) {
					log4j
							.info("User '"
									+ strUserName1
									+ "' is placed in the list based"
									+ " on the alphabetical order of Full Name of users in region. ");
				} else {
					log4j
							.info("User '"
									+ strUserName1
									+ "' is NOT placed in the list based"
									+ " on the alphabetical order of Full Name of users in region. ");
					strFuncResult = "User '"
							+ strUserName1
							+ "' is NOT placed in the list based"
							+ " on the alphabetical order of Full Name of users in region. ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				log4j.info("Option to search the users is NOT available. ");
				strFuncResult = "Option to search the users is NOT available. ";
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
			gstrTCID = "91317";
			gstrTO = "Verify that the user details edited are updated on the 'Users List' screen.";
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
