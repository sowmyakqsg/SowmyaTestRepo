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
' Description :This class includes Delete A Document requirement 
'				testcases
' Precondition:
' Date		  :18-July-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSDeleteADocument  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.DeleteADocument");
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
	'Description	:Verify that the process of deleting a document can be cancelled.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:18-July-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS222() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();

		try {
			gstrTCID = "222";
			gstrTO = "Verify that the process of deleting a document can be cancelled.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			//String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = "";
			String strLoginPassword = "";

			String strRegn = "";

			String strFolderName = "";
			String strFldDesc = "";
			String strFldSelValue = "";

			String strDocTitle = "";

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

				strFolderName = "AutoF" + System.currentTimeMillis();
				strFldDesc = strFolderName;
				strFldSelValue = "";
				strFuncResult = objDocumentLibrary.createNewFolderNew(selenium,
						strFolderName, strFldDesc, true, strFldSelValue, "",
						true);
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

			try {
				assertEquals("", strFuncResult);

				strDocTitle = "AutoDocTxt" + System.currentTimeMillis();

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadTxtFile_OpenPath");

				strFuncResult = objDocumentLibrary.addNewDocument(selenium,
						strDocTitle, strFolderName, false, strFldSelValue,
						strAutoFilePath, strAutoFileName, strUploadFilePath);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			
			//click on folder name
			selenium.click("//div[@id='mainContainer']/div[2]/a/span[text()='"+strFolderName+"']");
			
			/* 3 Click ''Cancel'' in confirmation message<-> Doc1 is not deleted */
			selenium.chooseCancelOnNextConfirmation();
			
			//click on delete button for particular document
			selenium.click("//div[@id='F-" + strFldSelValue
					+ "']/table/tbody/tr/td[3]/a/span[text()='" + strDocTitle
					+ "']/ancestor::tr/td[1]/form/input[@value='Delete']");
			
			try {
				assertTrue(selenium
						.getConfirmation()
						.matches(
								"^Are you absolutely sure that you want "
										+ "to delete this document[\\s\\S] It will"
										+ " remove the document from the entire region,"
										+ " not just your view\\.$"));
				log4j.info("A confirmation window is displayed.");

				try {
					assertTrue(selenium.isElementPresent("//div[@id='F-"
							+ strFldSelValue
							+ "']/table/tbody/tr/td[3]/a/span[text()='"
							+ strDocTitle + "']"));
					log4j.info(strDocTitle + "	NOT deleted ");

					gstrResult = "PASS";
				} catch (AssertionError Ae) {
					gstrReason = strDocTitle + " deleted ";
					log4j.info(strDocTitle + " deleted ");
				}

			} catch (AssertionError Ae) {

				gstrReason = "A confirmation window is NOT displayed." + Ae;
				log4j.info("A confirmation window is NOT displayed.");
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "222";
			gstrTO = "Verify that the process of deleting"
					+ " a folder can be cancelled.";
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

	
