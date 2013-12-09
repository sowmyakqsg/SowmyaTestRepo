package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import static org.junit.Assert.*;
import org.junit.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*************************************************************
' Description       :This class includes requirement testcases
' Requirement Group :Smoke Test Suite 
' Requirement       :Multi Region Features
' Date		        :08-May-2012
' Author	        :QSG
'--------------------------------------------------------------
' Modified Date                                     Modified By
' <Date>                           	                <Name>
'***************************************************************/

public class Smoke_RegionalInfoTest {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Smoke_RegionalInfo");
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
	String gstrTimeOut;

	Selenium selenium, seleniumPrecondition;

	@Before
	public void setUp() throws Exception {

		dtStartDate = new Date();
		gstrBrowserName = "IE 8";
		gstrBuild = "";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		selenium.stop();

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

		gdbTimeTaken = objOFC.TimeTaken(dtStartDate);// and execution time
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");

		// gstrBuild=PropEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	 // start//testSmoke117549//
	/**************************************************************************************
	 * 'Description  :Verify that a new message can be created
	 * 'Precondition :
	 * 'Arguments    :None 
	 * 'Returns      :None
	 * 'Date         :8/05/2013 
	 * 'Author :QSG
	 * '-----------------------------------------------------------------------------------
	 * 'Modified Date                                                               Date 
	 *  Modified By                                                                 'Name
	 **************************************************************************************/

	@Test
	public void testSmoke122767() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		RegionalInfo objRegionalInfo = new RegionalInfo();

		try {
			gstrTCID = "122767"; // Test Case Id
			gstrTO = " Verify that a new message can be created";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMsgDate = "";
			String strMsgTitle = "";
			String strMessage = "";
			String strContactEmail = "";
			String strVerifyMsgDate = "";

		log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");

		/*
		 * STEP 1:Login as admin user and navigate to Regional Info >> Calendar 
		 * Expected Result:'Message Bulletin Board' screen is displayed 
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navMessageBulletin(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
		/*
		 * STEP 1:	Click on 'Create New Message' 
		 * Expected Result:'Create Bulletin Message' screen is displayed. 
		 */
			
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
		 * STEP 1:Provide mandatory fields, select current date and save  
		 * Expected Result:	Message created is displayed in 'Message Bulletin Board' screen under the columns:
			1. Action
			2. Title
			3. Date
			4. Message
			5. Contact 
		 */
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
				log4j.info("Create Message Bulletin Message screen is displayed. ");
				log4j.info("Message created is displayed in '"
						+ "'Message Bulletin Board '' screen under the columns:1. Action"
						+ "2. Title" + "3. Date" + "4. Message" + "5. Contact ");

			} catch (AssertionError Ae) {

				log4j.info("Create Message Bulletin Message screen is NOT displayed. ");

				gstrReason = strFuncResult
						+ "Message created is NOT displayed in '"
						+ "'Message Bulletin Board '' screen under the columns:1. Action"
						+ "2. Title" + "3. Date" + "4. Message" + "5. Contact "
						+ "Create Message"
						+ " Bulletin Message screen is NOT displayed. ";

				log4j.info("Message created is NOT displayed in '"
						+ "'Message Bulletin Board '' screen under the columns:1. Action"
						+ "2. Title" + "3. Date" + "4. Message" + "5. Contact ");
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "122767"; // Test Case Id
			gstrTO = " Verify that a new message can be created";// Test
																	// Objective
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	
 // start//testSmoke124382//
	/**************************************************************************************
	 * 'Description  :Verify that a document can be added in a folder
	 * 'Precondition :
	 * 'Arguments    :None 
	 * 'Returns      :None
	 * 'Date         :8/05/2013 
	 * 'Author :QSG
	 * '-----------------------------------------------------------------------------------
	 * 'Modified Date                                                               Date 
	 *  Modified By                                                                 'Name
	 **************************************************************************************/

	@Test
	public void testSmoke124382() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		General objGeneral = new General();
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		DocumentLibrary objDocumentLibrary = new DocumentLibrary();

		try {
			gstrTCID = "124382"; // Test Case Id
			gstrTO = " Verify that a document can be added in a folder";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strUserName = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName = strUserName;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

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

		log4j.info("------Precondition " + gstrTCID + " execution Starts-------");

		/*
		 * STEP 1:Login as user with 'Maintain Document Library' and 'Edit Regional
		 *  Message Bulletin Board' rights and navigate to Regional Info >> Document Library 
		 * Expected Result:'Document Library' screen is displayed 
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintainDocLibrary");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditMsgBultnBoard");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("-------Precondition " + gstrTCID + " execution Ends-------");
		log4j.info("-------Test case " + gstrTCID + " execution starts--------");
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
		 * STEP 1:Click on 'Create New Folder' button, enter only mandatory fields,
		 *  select 'All folders' for 'Create in' field and click on 'Save'. 
		 * Expected Result:Folder is successfully created and displayed in 'Document Library' screen
		 *  under All folders section.  
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
		/*
		 * STEP 1:Click on 'Add a New Document' button. 
		 * Expected Result:'Add A Document' screen is displayed 
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
		/*
		 * STEP 1:	Enter only mandatory data and save 
		 * Expected Result:Document is successfully created and displayed in 'Document Library' 
		 * screen under the folder specified while creating.
          User name, Date/time of creation are displayed next to the document. 
		 */
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
						strUserName, strTimeDate);
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
			gstrTCID = "124382"; // Test Case Id
			gstrTO = " Verify that a document can be added in a folder";// Test
																		// Objective
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
