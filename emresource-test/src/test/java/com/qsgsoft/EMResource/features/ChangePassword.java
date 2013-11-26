package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*******************************************************************************
' Description		:This class contains test cases from requirement Preferences
' Requirement		:Change password
' Requirement Group	:Preferences>> Change Password
' Product		    :EMResource Base
' Date			    :02-06-2012
' Author		    :QSG
--------------------------------------------------------------------------------
' Modified Date				                                    Modified By
' Date					                                        Name
'*******************************************************************************/

public class ChangePassword {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ChangePassword");

	static {
		BasicConfigurator.configure();
	}
	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	// Selenium Object
	Selenium selenium, seleniumPrecondition;

	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails; 										
	public Properties propEnvDetails;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	
	@SuppressWarnings("unused")
	private String browser;
	double gdbTimeTaken; 
	Properties pathProps;
	public static Date gdtStartDate;
	@SuppressWarnings("unused")
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;
	
/**************************************************************************************
	* This function is called the setup() function which is executed before every test.
	*
	* The function will take care of creating a new selenium session for every test
	*
****************************************************************************************/
@Before
public void setUp() throws Exception {

	Paths_Properties objAP = new Paths_Properties();
	pathProps = objAP.Read_FilePath();
	//Create object to read environment properties file
	ReadEnvironment objReadEnvironment = new ReadEnvironment();
	propEnvDetails = objReadEnvironment.readEnvironment();
	//Retrieve browser information
	browser=propEnvDetails.getProperty("Browser");
	gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
	//Retrieve the value of page load time limit
	gstrTimeOut=propEnvDetails.getProperty("TimeOut");
	//create an object to refer to Element ID properties file
	@SuppressWarnings("unused")
	ElementId_properties objelementProp = new ElementId_properties();
	//Create a new selenium session
	selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
		4444, propEnvDetails.getProperty("Browser"), propEnvDetails
		.getProperty("urlEU"));
	
	seleniumPrecondition = new DefaultSelenium(propEnvDetails
			.getProperty("Server"), 4444, propEnvDetails
			.getProperty("BrowserPrecondition"), propEnvDetails
			.getProperty("urlEU"));
	//Start Selenium RC
	
	seleniumPrecondition.start();
	seleniumPrecondition.windowMaximize();
	seleniumPrecondition.setTimeout("");
	
	
	selenium.start();
	//Maximize the window
	selenium.windowMaximize();
	selenium.setTimeout("");
	
	
	//Define object to call support functions to read excel, date etc
	gdtStartDate = new Date();
	objOFC = new OfficeCommonFunctions();
	objrdExcel = new ReadData();

}
/****************************************************************************************************************	* This function is called the teardown() function which is executed after every test.
	*
	* The function will take care of stopping the selenium session for every test and writing the execution
	* result of the test. 
	*
****************************************************************************************************************/
@After
public void tearDown() throws Exception {
	
	try {
		selenium.close();
	} catch (Exception e) {

	}
	selenium.stop();
	try {
		seleniumPrecondition.close();
	} catch (Exception e) {

	}
	seleniumPrecondition.stop();
	// determine log message
	if (gstrResult.toUpperCase().equals("PASS"))
	{
		log4j.info("-------------------Test Case Execution " + gstrTCID
		+ " has PASSED------------------");
	} else if (gstrResult.toUpperCase().equals("SKIP"))
	{
		log4j.info("-------------------Test Case Execution " + gstrTCID
		+ " was SKIPPED------------------");
	} else
	{
		log4j.info("-------------------Test Case Execution " + gstrTCID
						+ " has FAILED------------------");
	}

	//Retrieve the path of the Result file
	String FILE_PATH = "";
	Paths_Properties objAP = new Paths_Properties();
	pathProps = objAP.Read_FilePath();
	FILE_PATH = pathProps.getProperty("Resultpath");
	// Retrieve the total execution time
	gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
	Date_Time_settings dts = new Date_Time_settings();
	//Get the current date
	gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
	//Get the Build ID of the application
	gstrBuild = propEnvDetails.getProperty("Build");
	//Check if result should be written to Excel or Test Management Tool
	String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
	boolean blnwriteres = blnresult.equals("true");
	  gstrReason=gstrReason.replaceAll("'", " ");
	//Write Result of the test.
	objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
					sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
}

/**********************************************************************
'Description	:Verify that user can change password from Preferences.
'Arguments		:None
'Returns		:None
'Date			:02-06-2012
'Author			:QSG
'---------------------------------------------------------------------
'Modified Date				                             Modified By
'Date					                                 Name
**********************************************************************/
	@Test
	public void testBQS84154() throws Exception {
		
		String strFuncResult = "";
		CreateUsers objUser=new CreateUsers();	
		Login objLogin = new Login();
		Preferences objPreferences = new Preferences();
		try {
			gstrTCID = "84154"; // Test Case Id
			gstrTO = " Verify that user can change password from Preferences.";//TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn=objrdExcel.readData("Login", 3, 4);
			String strNewPasswd = objrdExcel.readData("Login", 5, 2);
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strPassword="abc123";
			String strFullUserName="Full"+strUserName;
		/*
		 * STEP : Action:Precondition: <br>1. User 'A' is created. Expected
		 * Result:No Expected Result
		 */
		// 501028

			String strFailMsg = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strPassword, strPassword, strFullUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objUser.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
		/*
		 * STEP : Action:Login as user 'A' and navigate to 'Preferences >>
		 * Change Password' Expected Result:'Change Password' page is
		 * displayed with instruction 'To change your password please click
		 * here.'
		 */
		// 501029
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToPrefChangePwd(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select 'Click here' Expected Result:'Set up your
		 * Password' page is displayed.
		 */
		// 501030
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToSetUpPwdFromChangePwd(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Provide a different password (other than previously
		 * provided) for 'New Password' and 'Verify Password' and select
		 * 'Submit' Expected Result:User is directed to the 'Region Default'
		 * view.
		 */
		// 501031
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.changePasswdAndVerifyUser(
						selenium, strNewPasswd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Log Out from the application and attempt to login
		 * with the old password. Expected Result:Login fails
		 */
		// 501032
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.loginWithInvalidPwd(selenium,
						strUserName, strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Login with the new password Expected Result:Login
		 * is successful and user is directed to the 'Region Default' view.
		 */
		// 501033

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strNewPasswd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason =strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-84154";
			gstrTO = "Verify that user can change password from Preferences.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = gstrReason + " " + e.toString();
		}
	}
}

