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
' Description :This class includes Rename Folder requirement 
'				testcases
' Precondition:
' Date		  :16-July-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class RenameFolder  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RenameFolder");
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
	'Description	:Verify that a folder can be renamed.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:3-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS200() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary=new DocumentLibrary();
		
		try {
			gstrTCID = "200";
			gstrTO = "Verify that a folder can be renamed.";
			gstrResult = "FAIL";
			gstrReason = "";

			 Date_Time_settings dts = new Date_Time_settings();
			 String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			 selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			 gstrTimetake = dts.timeNow("HH:mm:ss");

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j
					.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			String strLoginUserName="";
			String strLoginPassword="";
			
			String strRegn="";
			
			String strFolderName="";
			String strFldDesc="";
			String strFldSelValue="";
			
			
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
			
			/*1 	Navigate to Regional Info>>Document Library 		No Expected Result */
		
			try{
				assertEquals("",strFuncResult);
				
				strFuncResult=objDocumentLibrary.navToDocumentLibrary(selenium);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
		
			try {
				assertEquals("", strFuncResult);


				strFolderName = "AutoF" + strTimeText;
				strFldDesc = strFolderName;
				strFldSelValue = "";
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName, strFldDesc, true, strFldSelValue,
						"",true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFldSelValue = objDocumentLibrary.fetchFolderValueGeneral(
						selenium, strFolderName, strFldDesc);

				if (strFldSelValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*4 	Click on ''Rename a Folder'' button again 		No Expected Result*/ 
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objDocumentLibrary.navRenameFolderPge(selenium);
				gstrReason = strFuncResult;
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			/*5 	Select folder F1 and click on ''Rename'' 		No Expected Result */
			
			try {
				assertEquals("", strFuncResult);

				strFolderName="EDIT"+strFolderName;
				strFuncResult = objDocumentLibrary
						.renameFolderFrmSelectFoldrPge(selenium, strFolderName,
								strFldDesc, strFldSelValue, false, true);
				gstrReason = strFuncResult;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try{
				assertEquals("",strFuncResult);
				
				gstrResult = "PASS";
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "200";
			gstrTO = "Verify that a folder can be renamed.";
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

	
