package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.DocumentLibrary;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description         :This class includes Test cases
' Requirement Group   :Regional Info >> Document Library 
' Requirement         : Move a document
' Date		          :11th-Sep-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'*******************************************************************/

public class MoveADocument {
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

/***************************************************************
'Description		:Verify that a document can be moved from one folder to another.
'Arguments		    :None
'Returns		    :None
'Date			    :9/11/2012
'Author			    :QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS219() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();
		try {
			gstrTCID = "219"; // Test Case Id
			gstrTO = " Verify that a document can be moved from one folder to another.";// Test
																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// Login Details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Folder details
			String strFolderName1 = "AutoF_1" + strTimeText;
			String strFldDesc1 = strFolderName1;
			String strFolderName2 = "AutoF_2" + strTimeText;
			String strFldDesc2 = strFolderName2;
			String strFolderName3 = "AutoF_3" + strTimeText;
			String strFldDesc3 = strFolderName3;
			String strFldSelValue = "";
			String strFldSelValues[] = new String[3];
			String strDocVal[] = new String[1];

			// Document Details
			String strDocTitle = "AutoDocTxt" + strTimeText;
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");
			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadTxtFile_OpenPath");

			

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
				strFuncResult = objDocumentLibrary
						.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Folder1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName1, strFldDesc1, true, strFldSelValue, "",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFldSelValues[0] = objDocumentLibrary
						.fetchFolderValueGeneral(selenium, strFolderName1,
								strFldDesc1);
				if (strFldSelValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Folder2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName2, strFldDesc2, true, strFldSelValue, "",
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFldSelValues[1] = objDocumentLibrary
						.fetchFolderValueGeneral(selenium, strFolderName2,
								strFldDesc2);
				if (strFldSelValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Folder3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName3, strFldDesc3, false, strFldSelValues[1],
						"", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFldSelValues[2] = objDocumentLibrary
						.fetchFolderValueGeneral(selenium, strFolderName3,
								strFldDesc3);
				if (strFldSelValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.addDocument(selenium,
						strDocTitle, "", true, "", strAutoFilePath,
						strAutoFileName, strUploadFilePath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strDocVal[0] = objDocumentLibrary
						.fetcDocValInUncategorizedList(selenium, strDocTitle);
				if (strDocVal[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function Failed to fetch folder value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Regional Info>>Document Library
			 * Expected Result:No Expected Result
			 */
			// 1106
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary
						.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on ''Move'' button associated with document
			 * doc1 in Uncategorized section Expected Result:''Move A Document''
			 * screen is displayed
			 */
			// 1107
			/*
			 * STEP : Action:Click on ''Move'' Without selecting any folder
			 * Expected Result:The message ''Please choose the new folder in
			 * which to move the document'' is displayed
			 */
			// 1108
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary
						.movDocWithtSelFolderVrfyErrMsg(selenium, strDocVal[0],
								strDocTitle);
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
			/*
			 * STEP : Action:Move doc1 to a folder Expected Result:The document
			 * is displayed under the newly selected folder.
			 */
			// 1109
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary
						.movDocToFolderFromUnCategarizedSec(selenium,
								strDocVal[0], strDocTitle, true, false,
								strFolderName1, strFldSelValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Move doc1 to another folder Expected Result:The
			 * document is displayed under the newly selected folder.
			 */
			// 1110
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.moveDocToFolder(selenium,
						strFolderName1, strFldSelValues[0], strFolderName2,
						strFldSelValues[1], false, strDocTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Move doc1 to a sub folder Expected Result:The
			 * document is displayed under the newly selected sub folder.
			 */
			// 1111
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.moveDocToSubFolder(selenium,
						strFolderName2, strFldSelValues[1], strFolderName3,
						strFldSelValues[1], strFolderName3, strFldSelValues[2],
						false, strDocTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Move doc1 to ''All folders'' Expected Result:The
			 * document is displayed under ''Uncategorized documents'' section.
			 */
			// 1112
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary
						.moveDocToAllFoldersFromSubFolder(selenium,
								strFolderName3, strFldSelValues[2], true,
								strDocTitle);
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
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
			gstrTCID = "BQS-219";
			gstrTO = "Verify that a document can be moved from one folder to another.";
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
