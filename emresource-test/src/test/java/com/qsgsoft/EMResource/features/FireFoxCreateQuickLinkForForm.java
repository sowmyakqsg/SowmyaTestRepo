package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************************
' Description        :This class includes 
' Requirement        :Create a quick link for a website
' Requirement Group  :Setting User links up               
' Date		         :22-May-2012
' Author	         :QSG
'-------------------------------------------------------------------
' Modified Date                                          Modified By
' <28/8/2012>                           	                <Name>
'*******************************************************************/

public class FireFoxCreateQuickLinkForForm {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RightsToViewUpdatePrivateStatusTypes");
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
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
						.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		selenium.close();
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
		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}


/***************************************************************
'Description		:Verify that a quick link can be created of type
                     ''EMResource Form'' for an old form to be filled by himself
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:9/18/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

@Test
	public void testBQS715() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		UserLinks objUserLinks = new UserLinks();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Forms objForms = new Forms();
		try {
			gstrTCID = "715"; // Test Case Id
			gstrTO = " Verify that a quick link can be created of type ''EMResource Form'' for an old form to be filled by himself";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Data for creating user
			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUserName3 = "AutoUsr_3" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";
			// User Link

			String strLablText = "USER" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";
			/*
			 * STEP : Action:Precondition: Users U1 and U2 have the right 'Form
			 * - User may activate forms'. Expected Result:No Expected Result
			 */
			// 382455

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ActivateForms"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName2, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ActivateForms"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName3, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Form>>Configure
			 * forms Expected Result:List of forms is displayed
			 */
			// 2261
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a form F1 without selecting ''New forms''
			 * and by selecting ''User initiate and fill out him/herself'' for
			 * ''Form Activation'' and ''Template defined'' for ''Completed form
			 * delivery'' Select user U3 to receive completed form. Expected
			 * Result:No Expected Result
			 */
			// 2262

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						selenium, strFormTempTitleOF, strDescription,
						strFormActiv, strComplFormDel, false, false, false,
						false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(selenium,
						strUserName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Form>>Form Security and provide User U1
			 * the right ''Activate form'' and user U2 the right ''Run report''
			 * Expected Result:No Expected Result
			 */
			// 2263
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName1, strFormTempTitleOF, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName2, strFormTempTitleOF, false, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>User links and click on ''Create
			 * new user link'' Expected Result:''Create a new user link'' screen
			 * is displayed.
			 */
			// 2264
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
						selenium, strLablText, strExternalURL, false, true,
						strFormTempTitleOF, true, strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide Label text, attach a file, select
			 * ''EMResource Form'' for ''Select type of link to create'', select
			 * the form F1, select ''Quick link'' and save. Expected Result:The
			 * created user link is listed on the User Link list screen with the
			 * following columns: a. Action i. Edit ii. Delete iii. Show iv. Up
			 * b. Link: Label text provided c. Show as: (Hide) d. Image: The
			 * attached image e. Destination URL: Form name f. Image size:
			 * Attached file size (in pixels) g. File size: Attached file size
			 * (in bytes)
			 */
			// 2265
			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Show";
				String strLablHeaderActionUp = "Up";
				strFuncResult = objUserLinks.verifyLinkHeadersLinks(selenium,
						strLablText, strLablHeaderAction,
						strLablHeaderActionEdit, strLablHeaderActionDelete,
						strLablHeaderActionShow, strLablHeaderActionUp);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strShow = "(Hide)";
				String strImgSize = "32x27";
				String strFileSize = "16.55 kb";
				strFuncResult = objUserLinks.varOtherFldsInUserLink(selenium,
						strLablText, strShow, strFormTempTitleOF, strImgSize,
						strFileSize);
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='quickUserLinks-in']/a/img[@title='"
									+ strLablText + "']"));
					log4j
							.info("The image is not displayed at the top of the screen (as a ''Quick Link''). ");

				} catch (AssertionError Ae) {
					log4j
							.info("The image is displayed at the top of the screen (as a ''Quick Link''). ");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user U1 Expected Result:The image is not
			 * displayed at the top of the screen (as a ''Quick Link'').
			 */
			// 2266
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
				try {
					assertFalse(selenium
							.isElementPresent("id('quickUserLinks-in')/a/img"));
					log4j
							.info("The image is not displayed at the top of the screen (as a ''Quick Link'').");
				} catch (AssertionError Ae) {
					log4j
							.info("The image is displayed at the top of the screen (as a ''Quick Link'').");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:From RegAdmin, navigate to Setup>>User links
			 * Expected Result:No Expected Result
			 */
			// 2267
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on ''Show'' Expected Result:The link ''Show''
			 * is changed to ''Hide'' and ''Quick Link'' is displayed under the
			 * column ''Show As'' The image is displayed at the top of the
			 * screen for user U1. The tool tip on the image displays the label
			 * text provided
			 */
			// 2268
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.showTheLinkAndVerify(selenium,
						strLablText, "Quick Link");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				String strTestData[] = new String[10];
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strUserName2 + "/"
						+ strUserName3 + "/" + strInitPwd;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 10th step.";
				strTestData[6] = strQuestion;
				strTestData[7] = strquesTypeID;
				strTestData[9] = strLablText;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-715";
			gstrTO = "Verify that a quick link can be created of type ''EMResource Form'' for an old form to be filled by himself";
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
'Description		:Verify that a quick link can be created of type ''EMResource Form'' for a new form to be filled by others. (New forms service)
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:10/19/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS716() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		UserLinks objUserLinks = new UserLinks();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Forms objForms = new Forms();
		try {
			gstrTCID = "716"; // Test Case Id
			gstrTO = " Verify that a quick link can be created of type ''EMResource Form'" +
					"' for a new form to be filled by others. (New forms service)";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Data for creating user
			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUserName3 = "AutoUsr_3" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Other To Fill Out";
			String strComplFormDel = "User To Individual Users";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";

			// Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";

			// User Link
			String strLablText = "USER" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";
			/*
			 * STEP : Action:Precondition: Users U1 and U2 have the right 'Form
			 * - User may activate forms'. Expected Result:No Expected Result
			 */
			// 382452
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ActivateForms"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName2, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ActivateForms"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName3, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Form>>Configure
			 * forms Expected Result:List of forms is displayed
			 */
			// 2275
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a form F1 by selecting ''New forms'' and by
			 * selecting ''User initiate and other to fill out'' for ''Form
			 * Activation'' and ''User to individual users'' for ''Completed
			 * form delivery'' Select user U3 to fill out the form. Expected
			 * Result:No Expected Result
			 */
			// 2276
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.fillAllFieldsInCreateNewForm(selenium,
								strFormTempTitleOF, strDescription,
								strFormActiv, strComplFormDel, false, false,
								false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForForm(selenium,
						strUserName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResource = "";
				strFuncResult = objForms.selectResourcesForForm(selenium,
						strResource, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Adding a questionarri to a form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToAddQestnPageOfNF(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
						strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
						strLabl);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.newFormQuestnTextInfo(selenium,
						strTextTitl, strTextAbb, strTextLength, true, true);
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Form>>Form Security and provide User U1
			 * the right ''Activate form'' and user U2 the right ''Run report''
			 * Expected Result:No Expected Result
			 */
			// 2277
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName1, strFormTempTitleOF, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(selenium,
						strUserName2, strFormTempTitleOF, false, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>User links and click on ''Create
			 * new user link'' Expected Result:''Create a new user link'' screen
			 * is displayed.
			 */
			// 2278
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
						selenium, strLablText, strExternalURL, false, true,
						strFormTempTitleOF, true, strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide Label text, attach a file, select
			 * ''EMResource Form'' for ''Select type of link to create' ',
			 * select the form F1, select ''Quick link'' and save. Expected
			 * Result:The created user link is listed on the User Link list
			 * screen with the following columns: a. Action i. Edit ii. Delete
			 * iii. Show iv. Up b. Link: Label text provided c. Show as: (Hide)
			 * d. Image: The attached image e. Destination URL: Form name f.
			 * Image size: Attached file size (in pixels) g. File size: Attached
			 * file size (in bytes)
			 */
			// 2279
			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Show";
				String strLablHeaderActionUp = "Up";
				strFuncResult = objUserLinks.verifyLinkHeadersLinks(selenium,
						strLablText, strLablHeaderAction,
						strLablHeaderActionEdit, strLablHeaderActionDelete,
						strLablHeaderActionShow, strLablHeaderActionUp);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strShow = "(Hide)";
				String strImgSize = "32x27";
				String strFileSize = "16.55 kb";
				strFuncResult = objUserLinks.varOtherFldsInUserLink(selenium,
						strLablText, strShow, strFormTempTitleOF, strImgSize,
						strFileSize);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user U1 Expected Result:The image is not
			 * displayed at the top of the screen (as a ''Quick Link'').
			 */
			// 2280
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
				try {
					assertFalse(selenium
							.isElementPresent("id('quickUserLinks-in')/a/img"));
					log4j
							.info("The image is not displayed at the top of the screen (as a ''Quick Link'').");
				} catch (AssertionError Ae) {
					log4j
							.info("The image is displayed at the top of the screen (as a ''Quick Link'').");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:From RegAdmin, navigate to Setup>>User links
			 * Expected Result:No Expected Result
			 */
			// 2281
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on ''Show'' Expected Result:The link ''Show''
			 * is changed to ''Hide'' and ''Quick Link'' is displayed under the
			 * column ''Show As'' The image is displayed at the top of the
			 * screen for user U1. The tool tip on the image displays the label
			 * text provided
			 */
			// 2282
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.showTheLinkAndVerify(selenium,
						strLablText, "Quick Link");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				String strTestData[] = new String[10];
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strUserName2 + "/"
						+ strUserName3 + "/" + strInitPwd;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 10th step.";
				strTestData[6] = strNFQuestTitl+","+strQuestion;
				strTestData[7] = strquesTypeID;
				strTestData[9] = strLablText;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-716";
			gstrTO = "Verify that a quick link can be created of type ''EMResource Form'' for a new form to be filled by others. (New forms service)";
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

