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
' Description :This class includes FTSCreateBulletinMessage requirement 
'				testcases
' Precondition:
' Date		  :15-Sep-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSCreateBulletinMessage  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateBulletinMessage");
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
	'Description	:Verify that a new message can be created by entering data in all fields
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:14-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS97819() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		
		try {
			gstrTCID = "97819 ";
			gstrTO = "Verify that a new message can be created by entering mandatory data only.";
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
			
			/*
			 * STEP 2: Click on ''Create New Message'<->' Create Message Bulletin Message
			 * screen is displayed.
			 */
			
			/*
			 * STEP 3: Fill only mandatory fields, select current date and save
			 *<-> Message created is displayed in ''Message Bulletin Board ''
			 * screen under the columns: 1. Action 2. Title 3. Date 4. Message
			 * 5. Contact
			 */

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "Automation";
				strContactEmail = "autoemr@qsgsoft.com";
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");
		
				strFuncResult = objRegionalInfo.createMessage(selenium,
						strMsgDate,strVerifyMsgDate, strMsgTitle, strMessage, strContactEmail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
				log4j
						.info("Create Message Bulletin Message screen is displayed. ");
				log4j
						.info("Message created is displayed in '"
								+ "'Message Bulletin Board '' screen under the columns:1. Action"
								+ "2. Title" + "3. Date" + "4. Message"
								+ "5. Contact ");

			} catch (AssertionError Ae) {

				log4j
						.info("Create Message Bulletin Message screen is NOT displayed. ");

				gstrReason = strFuncResult
						+ "Message created is NOT displayed in '"
						+ "'Message Bulletin Board '' screen under the columns:1. Action"
						+ "2. Title" + "3. Date" + "4. Message" + "5. Contact "
						+ "Create Message"
						+ " Bulletin Message screen is NOT displayed. ";

				log4j
						.info("Message created is NOT displayed in '"
								+ "'Message Bulletin Board '' screen under the columns:1. Action"
								+ "2. Title" + "3. Date" + "4. Message"
								+ "5. Contact ");
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "97819";
			gstrTO = "Verify that a new message can be created by entering data in all fields";
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
	'Description	:Verify that a message can be created for future year.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:14-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS97814() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		
		
		try {
			gstrTCID = "97814 ";
			gstrTO = "Verify that a message can be created for future year.";
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
			
			
			
			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			String strVerifyMsgDate="";
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;
				
			
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
			
			/*
			 * STEP 2: Click on ''Create New Message'<->' Create Message Bulletin Message
			 * screen is displayed.
			 */
			
			/*
			 * STEP 3: Fill only mandatory fields, select current date and save
			 *<-> Message created is displayed in ''Message Bulletin Board ''
			 * screen under the columns: 1. Action 2. Title 3. Date 4. Message
			 * 5. Contact
			 */

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgDate = dts.AddDaytoExistingDate(strMsgDate, 1, "MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "Automation";
				strContactEmail = "autoemr@qsgsoft.com";
				strVerifyMsgDate = dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");
		
				strFuncResult = objRegionalInfo.createMessage(selenium,
						strMsgDate,strVerifyMsgDate, strMsgTitle, strMessage, strContactEmail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
				log4j
						.info("Create Message Bulletin Message screen is displayed. ");
				log4j
						.info("Message created is displayed in '"
								+ "'Message Bulletin Board '' screen under the columns:1. Action"
								+ "2. Title" + "3. Date" + "4. Message"
								+ "5. Contact ");

			} catch (AssertionError Ae) {

				log4j
						.info("Create Message Bulletin Message screen is NOT displayed. ");

				gstrReason = strFuncResult
						+ "Message created is NOT displayed in '"
						+ "'Message Bulletin Board '' screen under the columns:1. Action"
						+ "2. Title" + "3. Date" + "4. Message" + "5. Contact "
						+ "Create Message"
						+ " Bulletin Message screen is NOT displayed. ";

				log4j
						.info("Message created is NOT displayed in '"
								+ "'Message Bulletin Board '' screen under the columns:1. Action"
								+ "2. Title" + "3. Date" + "4. Message"
								+ "5. Contact ");
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "97814";
			gstrTO = "Verify that a message can be created for future year.";
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
	'Description		:Verify that a message can be created for previous year.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:12/3/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS168() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		General objGeneral=new General();
		try {
			gstrTCID = "168";
			gstrTO = "Verify that a message can be created for previous year.";
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
				
			
			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			/* 1 Navigate to Regional Info>>Calendar No Expected Result */
			
			
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;
				
			
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
			
			try{
				assertEquals("",strFuncResult);
				
				strFuncResult=objRegionalInfo.navMessageBulletin(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			/*2 	Click on ''Create New Message'' 		No Expected Result */
			/*
			 * 3 Select immediate previous year date and save User is taken to
			 * the ''Message Bulletin Board '' screen.
			 */
			
			

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgDate = dts.getFutureYear(-1, "MM/dd/yyyy");	
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "Automation";
				strContactEmail = "autoemr@qsgsoft.com";

				strFuncResult = objRegionalInfo.fillMsgFieldsAndSave(selenium,
						strMsgDate, strMsgTitle, strMessage,
						strContactEmail, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.saveMessageAndVerifyThePage(
						selenium);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Click on ''Previous Year'' button Message is displayed in this
			 * screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navPreviousYear(selenium, 1);
			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/form/table/thead/"
						+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
						+ "/tbody/tr/td[2][text()='" + strMsgTitle + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Message " + strMsgTitle + " is displayed in this screen. ");
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				log4j.info("Message " + strMsgTitle + " is NOT displayed in this screen. ");
				gstrReason = strFuncResult;
			}
			
		} catch (Exception e) {
			gstrTCID = "168";
			gstrTO = "Verify that a message can be created for previous year.";
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
	'Description		:Cancel the process of creating a message and verify that message is not created.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:12/3/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS170() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		General objGeneral=new General();
		try {
			gstrTCID = "170";
			gstrTO = "Cancel the process of creating a message and " +
					"verify that message is not created.";
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
				
			
			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			
			strLoginUserName=rdExcel.readData("Login", 3, 1);
			strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			/* 1 Navigate to Regional Info>>Calendar No Expected Result */
			
			
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;
				
			
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
			
			try{
				assertEquals("",strFuncResult);
				
				strFuncResult=objRegionalInfo.navMessageBulletin(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			/*2 	Click on ''Create New Message'' 		No Expected Result */
			
			/*
			 * 3 Fill data in mandatory fields and click on ''Cancel'' Message
			 * is not created in ''Message Bulletin Board'' screen.
			 */
			
			

			try {
				assertEquals("", strFuncResult);

				strMsgDate = dts.timeNow("MM/dd/yyyy");
				strMsgTitle = "AutoMessage" + System.currentTimeMillis();
				strMessage = "Automation";
				strContactEmail = "autoemr@qsgsoft.com";

				strFuncResult = objRegionalInfo.fillMsgFieldsAndSave(selenium,
						strMsgDate, strMsgTitle, strMessage,
						strContactEmail, false);
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
				String strElementID = "//div[@id='mainContainer']/form/table/thead/"
						+ "tr/th[1][text()='Action']/ancestor::thead/parent::table"
						+ "/tbody/tr/td[2][text()='" + strMsgTitle + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j.info("Message " + strMsgTitle + " is NOT displayed in this screen. ");
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				log4j.info("Message " + strMsgTitle + " is  displayed in this screen. ");
				gstrReason = strFuncResult;
			}
			
			
			
			
		} catch (Exception e) {
			gstrTCID = "170";
			gstrTO = "Cancel the process of creating a message and verify that message is not created.";
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

	
