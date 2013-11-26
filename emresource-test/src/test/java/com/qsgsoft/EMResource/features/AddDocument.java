package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************
' Description         :This class includes Test cases
' Requirement Group   :Regional Info >> Document Library 
' Requirement         :Add a document
' Date		          :4th-Sep-2012
' Author	          :QSG
'-------------------------------------------------------
' Modified Date                        Modified By
' <Date>                           	    <Name>
'********************************************************/

public class AddDocument {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSAddADocument");
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
	String gstrTimeOut = "";
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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/***********************************************************************************
	'Description	:Verify that a document can be added by entering only mandatory data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4th-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------------------
	'Modified Date                                                    Modified By
	'<Date>                                                           <Name>
	************************************************************************************/
	@Test
	public void testBQS213() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();
		General objGeneral = new General();

		try {
			gstrTCID = "213";
			gstrTO = "Verify that a document can be added by entering only mandatory data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Document Details
			String strFolderName = "";
			String strFldDesc = "";
			String strFldSelValue = "";
			String strDocTitle = "";

			// time data
			String[] strArFunRes = new String[5];
			String strCurYear = dts.getTimeOfParticularTimeZone("CST", "yyyy");
			log4j.info(strCurYear);
			String strRegGenTime = "";
			String strGenTimeHrs = "";
			String strGenTimeMin = "";
			String strGenDate = "";

			log4j.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
	
		/* Navigate to Regional Info>>Document Library 
		 * No Expected Result 
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
				strFuncResult = objDocumentLibrary
						.navToDocumentLibrary(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/* Click on ''Add a New Document'' button.
		 *  		No Expected Result 
		 */
			try {
				assertEquals("", strFuncResult);
				strFolderName = "AutoF" + strTimeText;
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

		/* 2 Enter only mandatory data and save 
		 * Document is successfully created and displayed in ''Document Library''
		 * screen under the folder specified while creating.
  		 * User name, Date/time of creation are displayed next to the document. 
		 */ 

			try {
				assertEquals("", strFuncResult);
				strDocTitle = "AutoDocTxt" + strTimeText;
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

			try {
				assertEquals("", strFuncResult);
				strArFunRes = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"dd MMM yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				System.out.println(strRegGenTime);
				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strTimeDate = strGenDate + " " + strGenTimeHrs + ":"
						+ strGenTimeMin;
				strFuncResult = objDocumentLibrary.varDocCreatedTime(selenium,
						strLoginUserName, strTimeDate);
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "213";
			gstrTO = "Verify that a document can be added by entering only mandatory data.";
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
