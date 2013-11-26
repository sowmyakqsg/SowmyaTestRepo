package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**********************************************************************
' Description :This class includes EditBulletinMessage requirement 
'				testcases
' Precondition:
' Date		  :29-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSEditBulletinMessage  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditBulletinMessage");
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
	
	
	
	/********************************************************************************
	'Description	:Change the year of a message and verify that the message is moved to the respective screen.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS172() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		
		try {
			gstrTCID = "BQS-172 ";
			gstrTO = "Change the year of a message and verify that " +
					"the message is moved to the respective screen.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			

			log4j
					.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			String strLoginUserName="";
			String strLoginPassword="";
			
			String strRegn="";
			
			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			String strVerifyMsgDate="";
			
			String strEditMsgDate = "";
			String strEditVerifyMsgDate="";
			
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;
				
				strRegn=rdExcel.readData("Login", 3, 4);
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
			/*STEP 1: Navigate to Regional Info>>Calendar<-> No Expected Result */
			
			try{
				assertEquals("",strFuncResult);
				
				strFuncResult=objRegionalInfo.navMessageBulletin(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "Automation";
				strContactEmail = "autoemr@qsgsoft.com";
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");
				strFuncResult = objRegionalInfo.createMessage(selenium,
						strMsgDate, strVerifyMsgDate, strMsgTitle, strMessage,
						strContactEmail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Click on ''Edit'' associated with a message No Expected Result
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navEditMessageBulletinPge(
						selenium, strMsgTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Edit date and change the year to a future year and save. The
			 * message is no longer displayed in the ''Message Bulletin Board ''
			 * screen.
			 */
			

			try {
				assertEquals("", strFuncResult);
				strEditMsgDate = strMsgDate;
				strEditMsgDate=dts.AddYearToExisting(strEditMsgDate, 1, "MM/dd/yyyy");
				strEditVerifyMsgDate = dts.converDateFormat(strEditMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");

				strFuncResult = objRegionalInfo.onlyFillMsgFieldsAndSave(
						selenium, strEditMsgDate, strMsgTitle,
						strMessage, strContactEmail, false);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.saveMessageAndVerifyThePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo
						.verifyMessageInMsgBulletingBoard(selenium,
								strEditVerifyMsgDate, false, strMsgTitle, false,
								strMessage, false, strContactEmail, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 4 Click on ''Next year'' until the newly selected year is
			 * displayed Message is displayed in this screen
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navFutureYear(selenium, 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo
						.verifyMessageInMsgBulletingBoard(selenium,
								strEditVerifyMsgDate, true, strMsgTitle, true,
								strMessage, true, strContactEmail, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 5 Click on Edit again No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navEditMessageBulletinPge(
						selenium, strMsgTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Edit date and change the year to a previous year and save.
			 * Message created is NOT displayed in ''Message Bulletin Board''
			 * screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objRegionalInfo.onlyFillMsgFieldsAndSave(
						selenium, strMsgDate, strMsgTitle,
						strMessage, strContactEmail, false);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.saveMessageAndVerifyThePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo
						.verifyMessageInMsgBulletingBoard(selenium,
								strVerifyMsgDate, true, strMsgTitle, true,
								strMessage, true, strContactEmail, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Click on ''previous year'' until the newly selected year is
			 * displayed Message is displayed in this screen
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navFutureYear(selenium, 1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo
						.verifyMessageInMsgBulletingBoard(selenium,
								strVerifyMsgDate, false, strMsgTitle, false,
								strMessage, false, strContactEmail, false);
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
			gstrTCID = "BQS-172";
			gstrTO = "Change the year of a message and verify that the " +
					"message is moved to the respective screen.";
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
	'Description	:Cancel the process of editing a message and verify that changes are not updated.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:3-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS174() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		
		try {
			gstrTCID = "BQS-174 ";
			gstrTO = "Cancel the process of editing a message and" +
					" verify that changes are not updated.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			

			log4j
					.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			String strLoginUserName="";
			String strLoginPassword="";
			
			String strRegn="";
			
			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			String strVerifyMsgDate="";
			
			String strEditMsgDate = "";
			String strEditMsgTitle = "";
			String strEditMessage = "";
			String strEditContactEmail = "";
			String strEditVerifyMsgDate="";
			
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;
				
				strRegn=rdExcel.readData("Login", 3, 4);
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
			/*STEP 1: Navigate to Regional Info>>Calendar<-> No Expected Result */
			
			try{
				assertEquals("",strFuncResult);
				
				strFuncResult=objRegionalInfo.navMessageBulletin(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "Automation";
				strContactEmail = "autoemr@qsgsoft.com";
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");
				strFuncResult = objRegionalInfo.createMessage(selenium,
						strMsgDate, strVerifyMsgDate, strMsgTitle, strMessage,
						strContactEmail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP 2: Click on ''Edit'' associated with a message Edit Bulletin
			 * <->Message screen is displayed All the data entered while
			 * creating message are retained.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navEditMessageBulletinPge(
						selenium, strMsgTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 3: Edit data in all fields and save<-> Changes are updated
			 * for the message in ''Message Bulletin Board'' screen.
			 */

			try {
				assertEquals("", strFuncResult);
				strEditMsgDate = dts.timeNow("MM/dd/yyyy");
				strEditMessage = "Edit" + strMessage;
				strEditContactEmail = "Edit" + strContactEmail;
				strEditMsgTitle = "Edit" + strMsgTitle;
				strEditMsgDate=dts.getFutureDay(1, "MM/dd/yyyy");
				strEditVerifyMsgDate = dts.converDateFormat(strEditMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");

				strFuncResult = objRegionalInfo.onlyFillMsgFieldsAndSave(
						selenium, strEditMsgDate, strEditMsgTitle,
						strEditMessage, strEditContactEmail, false);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.cancelMessageCreationAndVerifyThePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo
						.verifyMessageInMsgBulletingBoard(selenium,
								strVerifyMsgDate, true, strMsgTitle, true,
								strMessage, true, strContactEmail, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo
						.verifyMessageInMsgBulletingBoard(selenium,
								strEditVerifyMsgDate, false, strEditMsgTitle, false,
								strEditMessage, false, strEditContactEmail, false);
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
			gstrTCID = "BQS-174";
			gstrTO = "Cancel the process of editing a message" +
					" and verify that changes are not updated.";
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

	
