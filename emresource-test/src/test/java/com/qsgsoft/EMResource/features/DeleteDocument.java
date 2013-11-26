package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Properties;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
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
' Requirement         :Delete a document
' Date		          :4th-Sep-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'*******************************************************************/
public class DeleteDocument {
	
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.DeleteDocument");
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
	'Description	:Verify that a document can be deleted.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4th-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS221() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();

		try {
			gstrTCID = "221";
			gstrTO = "Verify that a document can be deleted.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3, 4);
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			
			//Document Details
			
			String strFolderName = "";
			String strFldDesc = "";
			String strFldSelValue = "";

			String strDocTitle = "";
	
		/* Navigate to Regional Info>>Document Library 
		 * No Expected Result 
		 */ 
		strLoginUserName = rdExcel.readData("Login", 3, 1);
		strLoginPassword = rdExcel.readData("Login", 3, 2);

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
		try {
			assertEquals("", strFuncResult);

			strFolderName = "AutoF" +strTimeText;
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
		    strDocTitle = "AutoDocTxt" + strTimeText;
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("CreateEve_UploadFile_FileName");
			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadTxtFile_OpenPath");
			strFuncResult = objDocumentLibrary.addNewDocument(selenium, strDocTitle,
					strFolderName, false, strFldSelValue, strAutoFilePath,
					strAutoFileName, strUploadFilePath);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
			
			log4j.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");

		/* Click on ''Delete'' button associated with document doc1.
		 * Confirmation is asked with the message ''Are you absolutely sure that you want to delete this document?
		 *  It will remove the document from the entire region, not just your view.''
		 *   with buttons ''Ok'' and ''Cancel'' 
		 * No Expected Result 
		 */
			
		/* Click on ''Ok'' 
		 * Doc1 is successfully deleted 
		 */ 				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objDocumentLibrary.deleteDocument(selenium,
						strFolderName, strFldSelValue, strDocTitle);
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
			gstrTCID = "221";
			gstrTO = "Verify that a document can be deleted.";
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

