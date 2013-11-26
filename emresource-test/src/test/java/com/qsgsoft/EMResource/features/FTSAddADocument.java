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
' Description :This class includes Add a document requirement testcases
' Date		  :20-July-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                <Name>
'*******************************************************************/

public class FTSAddADocument  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSAddADocument");
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
	'Description	:Verify that the process of adding a document can be cancelled.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-July-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS216() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();

		try {
			gstrTCID = "216";
			gstrTO = "Verify that the process of adding a document can be cancelled.";
			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = "";
			String strLoginPassword = "";

			String strRegn = "";

			String strFolderName[] = {"AutoFldr1"+strTimeText,"AutoFldr2"+strTimeText};
			String strFldDesc = "";
			String[] strFldSelValue = new String[2];
			strFldSelValue[0]="";
			strFldSelValue[1]="";
			

			String strDocTitle = "";

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
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

				strFuncResult = objDocumentLibrary
						.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFldDesc = strFolderName[0];
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName[0], strFldDesc, true, strFldSelValue[0], "",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFldSelValue[0] = objDocumentLibrary.fetchFolderValueGeneral(
						selenium, strFolderName[0], strFldDesc);

				if (strFldSelValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			/* 2 Click on ''Add a New Document'' button. No Expected Result */
			
			/*
			 * 3 Enter data in all fields and click on ''Cancel'' Document is
			 * not added in ''Document Library'' screen
			 */ 
			
			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strDocTitle = "AutoDocTxt" + System.currentTimeMillis();

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadTxtFile_OpenPath");

				strFuncResult = objDocumentLibrary.addNewDocumentWithSavACancelOption(selenium,
						strDocTitle, strFolderName[0], false, strFldSelValue[0],
						strAutoFilePath, strAutoFileName, strUploadFilePath,false);

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
			gstrTCID = "216";
			gstrTO = "Verify that the process of adding a document can be cancelled.";
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
	'Description	:Verify that three types (text, pdf, html) of files can be added.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:02-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS214() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();

		try {
			gstrTCID = "214";
			gstrTO = "Verify that three types (text, pdf, html) of files can be added.";
			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strTestData[]=new String[10];
			
			String strLoginUserName = "";
			String strLoginPassword = "";

			String strRegn = "";

			String strFolderName = "AutoFldr1" + strTimeText;
			String strFldDesc = "";
			String strFldSelValue[] = new String[1];
			strFldSelValue[0] = "";

			String strDocTitleTxt = "";
			String strDocTitleHTML = "";
			String strDocTitlePDF="";

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			/* 1 Navigate to Regional Info>>Document Library No Expected Result */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objDocumentLibrary
						.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFldDesc = strFolderName;
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName, strFldDesc, true, strFldSelValue[0], "",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFldSelValue[0] = objDocumentLibrary.fetchFolderValueGeneral(
						selenium, strFolderName, strFldDesc);

				if (strFldSelValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST-CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*2 	Click on ''Add a New Document'' button. 		No Expected Result*/
			/*3 	Add a text file. 		File is displayed in ''Document Library'' screen */
			
			try {
				assertEquals("", strFuncResult);

				strDocTitleTxt = "AutoDocTxt" + System.currentTimeMillis();

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadTxtFile_OpenPath");

				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitleTxt, strFolderName, false, strFldSelValue[0],
						strAutoFilePath, strAutoFileName, strUploadFilePath);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "Automation event";
				strFuncResult = objDocumentLibrary
						.checkAttachedFilesForFolders(selenium, strFolderName,
								strDocTitleTxt, strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*7 	Add a html file. 		File is displayed in ''Document Library'' screen*/
			/*8 	Click on the file name 		The file is opened.*/


			try {
				assertEquals("", strFuncResult);

				strDocTitleHTML = "AutoDocHTML" + System.currentTimeMillis();

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadHtmlFile_OpenPath");

				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitleHTML, strFolderName, false, strFldSelValue[0],
						strAutoFilePath, strAutoFileName, strUploadFilePath);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strHtmlText = "Test EMResource Events";
				strFuncResult = objDocumentLibrary
						.checkAttachedFilesForFolders(selenium, strFolderName,
								strDocTitleHTML, strHtmlText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*4 	Click on the file name 		The file is opened.*/
			/*5 	Add a pdf file. 		File is displayed in ''Document Library'' screen */
			/*6 	Click on the file name 		The file is opened. */
			try {
				assertEquals("", strFuncResult);

				strDocTitlePDF = "AutoDocPDF" + System.currentTimeMillis();

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadPdfFile_OpenPath");

				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitlePDF, strFolderName, false, strFldSelValue[0],
						strAutoFilePath, strAutoFileName, strUploadFilePath);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
				
				//Write result data
				strTestData[0]= propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strLoginUserName+"/"+strLoginPassword;
				strTestData[3]=strFolderName;
				strTestData[4]=strDocTitlePDF;
				strTestData[5]="Attach PDF file for the folder need to be opened";
				strTestData[6]=strRegn;
				
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "DocumentLibrary");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "214";
			gstrTO = "Verify that three types (text, pdf, html) of files can be added.";
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

	
