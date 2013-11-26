package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;


import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.RegionalInfo;
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
' Description :This class includes EditBulletinMessage requirement 
'				testcases
' Precondition:
' Date		  :29-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class EditBulletinMessage  {
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
	'Description	:Verify that a message can be edited.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:30-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS171() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo=new RegionalInfo();
		
		try {
			gstrTCID = "BQS-171 ";
			gstrTO = "Verify that a message can be edited. ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

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
			 * STEP 2: Click on ''Edit'' associated with a message Edit Bulletin
			 * <->Message screen is displayed All the data entered while
			 * creating message are retained.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRegionalInfo.navEditMessageBulletin(
						selenium, strMsgDate, strMsgTitle, strMessage,
						strContactEmail, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 3: Edit data in all fields and save<-> Changes are updated
			 * for the message in ''Message Bulletin Board'' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				log4j.info("Edit Bulletin Message screen is displayed "
						+ "All the data entered while creating message"
						+ " are retained. ");

				
				strMsgDate =dts.addTimetoExistingHour(strMsgDate, 10, "MM/dd/yyyy");
				
				strMessage = "Edit" + strMessage;
				strContactEmail = "Edit" + strContactEmail;
				String strEditMsgTitle = "Edit" + strMsgTitle;
				strVerifyMsgDate =  dts.converDateFormat(strMsgDate,
						"MM/dd/yyyy", "yyyy-MM-dd");

				strFuncResult = objRegionalInfo.editMessage(selenium,
						strMsgDate, strVerifyMsgDate, strMsgTitle,
						strEditMsgTitle, strMessage, strContactEmail);

			} catch (AssertionError Ae) {

				log4j.info("Edit Bulletin Message screen is NOT displayed "
						+ "All the data entered while creating message"
						+ " are retained. ");

				gstrReason = strFuncResult
						+ "Edit Bulletin Message screen is NOT displayed "
						+ "All the data entered while creating message"
						+ " are retained. ";
			}

			try {
				assertEquals("", strFuncResult);

				log4j.info("Changes are updated for the message"
						+ " in ''Message Bulletin Board'' screen. ");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {

				log4j.info("Changes are  NOT updated for the message"
						+ " in ''Message Bulletin Board'' screen. ");

				gstrReason = strFuncResult
						+ "Changes are NOT updated for the message in "
						+ "''Message Bulletin Board'' screen. ";
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-171";
			gstrTO = "Verify that a message can be edited. ";
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

	
