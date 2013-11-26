package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Forms;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.UserLinks;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*********************************************************************************************
' Description         :This class includes  requirement testcases
' Requirement Group   :Setting up User Links 
' Requirement         :New for EMR 3.15 - Number of allowable quick links is increased to six.
' Date		          :12-Sep-2013
' Author	          :QSG
'--------------------------------------------------------------------------------------------
' Modified Date                                                            Modified By
' <Date>                           	                                       <Name>
'*********************************************************************************************/

public class FTSNewForEMRNumOfAllowableQuicklinksIsIncToSix {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features." +
					"FTSNewForEMRNumOfAllowableQuicklinksIsIncToSix");
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
	public Properties propPathDetails;
	String gstrTimeOut = "";
	Selenium selenium, seleniumPrecondition;

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

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

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
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();

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
	
	//start//testFTS23424//
	/*********************************************************************************************
	'Description	:Quick links can further be added when not more than six of them are displayed.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/12/2013
	'Author			:QSG
	'---------------------------------------------------------------------------------------------
	'Modified Date				                                                       Modified By
	'Date					                                                           Name
	*********************************************************************************************/

	@Test
	public void testFTS23424() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();

		try {
			gstrTCID = "23424"; // Test Case Id
			gstrTO = " Quick links can further be added when not more than six of them are displayed.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);

			// User Link

			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String strLablText7 = "QLink7" + strTimeText;
			String[] strQuickLinks={strLablText1,strLablText2,strLablText3,
					strLablText4,strLablText5,strLablText6,strLablText7};
			String strLablText = "QLink" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage.jpg";
		/*
		* STEP :
		  Action:Precondition:
			1. More than six quick links are created.
			2. No quick links are shown.
		  Expected Result:No Expected Result
		*/
		//123912

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + "PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + "EXECUTION STARTS~~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to 'Setup >> User Links'.
		  Expected Result:No Expected Result
		*/
		//123913
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn);
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
		* STEP :
		  Action:Click on 'Create New User Link'
		  Expected Result:'Create New User Link' screen is displayed.
		*/
		//123914
		/*
		* STEP :
		  Action:Attach a file, provide URL, select 'Quick Link' and click on 'Save'.
		  Expected Result:Quick link is created successfully.
		*/
		//123915
			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUserLinks.createUserLink(selenium,
						strLablText, strExternalURL, true,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'show' associated with newly created quick link.
		  Expected Result:Newly created quick link is displayed on top of the screen.
		*/
		//123916
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(selenium,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Select 'show' for six quick links.
		  Expected Result:The six quick links are displayed on top of the screen.
		*/
		//123925

			for (int i = 0; i < strQuickLinks.length-1; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(selenium,
							strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}		
		/*
		* STEP :
		  Action:Select 'show' for 7th quick link.
		  Expected Result:The Seventh quick links also displayed on top of the screen.
		*/
		//123926
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(selenium,
						strLablText6);
			} catch (AssertionError Ae) {
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-23424";
			gstrTO = "Quick links can further be added when not more than six of them are displayed.";
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

	//end//testFTS23424//

	//start//testFTS23414//
	/***********************************************************************
	'Description	:Verify that a 7th quick link cannot be created for form.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/12/2013
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date				                              Modified By
	'Date					                                  Name
	*************************************************************************/

	@Test
	public void testFTS23414() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Forms objForms = new Forms();

		try {
			gstrTCID = "23414"; // Test Case Id
			gstrTO = " Verify that a 7th quick link cannot be created for form.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			// User Link
			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String strLablText7 = "QLink7" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5, strLablText6,
					strLablText7 };
			String strLablText = "QLink" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage.jpg";
			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
		/*
		* STEP :
		  Action:Precondition:
			1. Six quick links for form F1 are created.
			2. User U1 has activate right on form F1.
			3. The six quick links are displayed on top of the screen for user U1.
			4. No quick links for website are created.
					  Expected Result:No Expected Result
		*/
		//123852

		log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navTocreateNewFormTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						seleniumPrecondition, strFormTempTitleOF,
						strDescription, strFormActiv, strComplFormDel, false,
						false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navFormSecuritySettingPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(
						seleniumPrecondition, strUserName, strFormTempTitleOF,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							seleniumPrecondition, strQuickLinks[i], strExternalURL, false,
							true, strFormTempTitleOF, true, strUploadFilePath,
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("~~~~~TEST-CASE" + gstrTCID + "PRECONDITION ENDS~~~~~");
		log4j.info("~~~~~TEST CASE" + gstrTCID + "EXECUTION STARTS~~~~~~");
		
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to 'Setup >> User Links'.
		  Expected Result:No Expected Result
		*/
		//123853
			try {
				assertEquals("", strFuncResult);
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
			
			for (int i = 0; i < strQuickLinks.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.showTheLinkAndVerify(selenium,
							strQuickLinks[i], "Quick Link");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
		/*
		* STEP :
		  Action:Click on 'Create New User Link'
		  Expected Result:'Create New User Link' screen is displayed.
		*/
		//123854
			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUserLinks.createUserLinkForForm(selenium, strLablText,
						strExternalURL, true, strAutoFilePath,
						strUploadFilePath, strAutoFileName, false, true,
						strFormTempTitleOF, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Attach a file, select form F1, select 'Quick Link' and click on 'Save'.
		  Expected Result:An appropriate error message stating no more than six quick 
		  links can be shown is displayed.
		  The quick link is not created.
		*/
		//123855
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clkOnSaveAndVfyErrMsg(selenium);
			} catch (AssertionError Ae) {
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-23414";
			gstrTO = "Verify that a 7th quick link cannot be created for form.";
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
	//end//testFTS23414//
	
	//start//testFTS23413//
	/***************************************************************
	'Description		:Verify that a 7th quick link cannot be created for a website.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/12/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23413() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		UserLinks objUserLinks = new UserLinks();

		try {
			gstrTCID = "23413"; // Test Case Id
			gstrTO = " Verify that a 7th quick link cannot be created for a website.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String strLablText7 = "QLink7" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5, strLablText6 };
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			/*
			 * STEP : Action:Precondition: <br>1. Six quick links for website
			 * are created and displayed on the top of all the screens. <br>2.
			 * No quick links for forms are created. Expected Result:No Expected
			 * Result
			 */
			// 123847

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User
			 * Links'. Expected Result:No Expected Result
			 */
			// 123848

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New User Link' Expected
			 * Result:'Create New User Link' screen is displayed.
			 */
			// 123849

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Attach a file, provide URL, select 'Quick Link' and
			 * click on 'Save'. Expected Result:An error message stating no more
			 * than six quick links can be shown is displayed. The quick link is
			 * not created.
			 */
			// 123850

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadImageFile_OpenPath");

				strExternalURL = "www.google.com";

				strFuncResult = objUserLinks.createUserLink(selenium,
						strLablText7, strExternalURL, true, strAutoFilePath,
						strUploadFilePath, strAutoFileName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clkOnSaveAndVfyErrMsg(selenium);
			} catch (AssertionError Ae) {
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
			gstrTCID = "FTS-23413";
			gstrTO = "Verify that a 7th quick link cannot be created for a website.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

	// end//testFTS23413//
	//start//testFTS23415//
	/***************************************************************
	'Description		:Verify that only six quick links for website can be created.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/12/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS23415() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		UserLinks objUserLinks = new UserLinks();

		try {
			gstrTCID = "23415"; // Test Case Id
			gstrTO = " Verify that only six quick links for website can be created.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;

			String[] strQuickLinks = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5 };
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";

			/*
			 * STEP : Action:Precondition: <br>1. Five quick links for website
			 * are created and displayed on the top of all the screens. <br>2.
			 * No quick links for forms are created. Expected Result:No Expected
			 * Result
			 */
			// 123792

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User
			 * Links'. Expected Result:No Expected Result
			 */
			// 123793

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New User Link' Expected
			 * Result:'Create New User Link' screen is displayed.
			 */
			// 123795

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Attach a file, provide URL, select 'Quick Link' and
			 * click on 'Save'. Expected Result:Quick link is created
			 * successfully.
			 */
			// 123820

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadImageFile_OpenPath");

				strExternalURL = "www.google.com";

				strFuncResult = objUserLinks.createUserLink(selenium,
						strLablText6, strExternalURL, true, strAutoFilePath,
						strUploadFilePath, strAutoFileName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'show' associated with newly created quick
			 * link. Expected Result:Newly created quick link is displayed on
			 * top of the screen along with the existing five quick links.
			 */
			// 123823

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(selenium,
						strLablText6);
			} catch (AssertionError Ae) {
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
			gstrTCID = "FTS-23415";
			gstrTO = "Verify that only six quick links for website can be created.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

	// end//testFTS23415//
	//start//testFTS23418//
	/***************************************************************
	'Description		:Verify that a 7th quick link cannot be created for a combination of websites & forms.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/13/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				              Modified By
	'Date					                  Name
	***************************************************************/

	@Test
	public void testFTS23418() throws Exception {

		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		Forms objForms = new Forms();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "23418"; // Test Case Id
			gstrTO = "Verify that a 7th quick link cannot be created for a combination of websites & forms.";// Test
																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// User Link

			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String strLablText7 = "QLink7" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2,strLablText3 };
			String[] strQuickLinks1 = { strLablText4, strLablText5,strLablText6 };
			
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";

			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition: <br>1. Five quick links for form F1
			 * are created. <br>2. User U1 has activate right on form F1. <br>3.
			 * The five quick links are displayed on top of the screen for user
			 * U1. <br>4. No quick links for website are created. Expected
			 * Result:No Expected Result
			 */
			// 123824

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navTocreateNewFormTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						seleniumPrecondition, strFormTempTitleOF,
						strDescription, strFormActiv, strComplFormDel, false,
						false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navFormSecuritySettingPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(
						seleniumPrecondition, strUserName, strFormTempTitleOF,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, false, true, strFormTempTitleOF,
							true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ClkOnShowQuickLink(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			}
			
			for (int i = 0; i < strQuickLinks1.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks1[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE" + gstrTCID + "PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + "EXECUTION STARTS~~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User
			 * Links'. Expected Result:No Expected Result
			 */
			// 123825

			try {
				assertEquals("", strFuncResult);
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
			 * STEP : Action:Click on 'Create New User Link' Expected
			 * Result:'Create New User Link' screen is displayed.
			 */
			// 123826
			/*
			 * STEP : Action:Attach a file, select form F1, select 'Quick Link'
			 * and click on 'Save'. Expected Result:Quick link is created
			 * successfully.
			 */
			// 123827
			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadImageFile_OpenPath");

				strExternalURL = "www.google.com";

				strFuncResult = objUserLinks.createUserLink(selenium,
						strLablText7, strExternalURL, true, strAutoFilePath,
						strUploadFilePath, strAutoFileName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clkOnSaveAndVfyErrMsg(selenium);
			} catch (AssertionError Ae) {
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
			gstrTCID = "FTS-23418";
			gstrTO = "Verify that a 7th quick link cannot be created for a combination of websites & forms.";
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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

	// end//testFTS23418//
	
	//start//testFTS23416//
	/***************************************************************
	'Description		:Verify that only six quick links for form can be created.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/17/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				             Modified By
	'Date					                 Name
	***************************************************************/

	@Test
	public void testFTS23416() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		Forms objForms = new Forms();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "23416"; // Test Case Id
			gstrTO = " Verify that only six quick links for form can be created.";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";	

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// QuickLinks
			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5 };
			String[] strQuickLinks1 = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5, strLablText6 };
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage.jpg";
			String strUploadFilePath1=pathProps.getProperty("CreateEve_UploadImageFile_OpenPath");

			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition: 1. Five quick links for form F1 are
			 * created. 2. User U1 has activate right on form F1. 3. The five
			 * quick links are displayed on top of the screen for user U1. 4. No
			 * quick links for website are created. Expected Result:No Expected
			 * Result
			 */
			// 123824
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
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
			// Form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navTocreateNewFormTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						seleniumPrecondition, strFormTempTitleOF,
						strDescription, strFormActiv, strComplFormDel, false,
						false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Adding question to from
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navFormSecuritySettingPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(
						seleniumPrecondition, strUserName, strFormTempTitleOF,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating the quick links
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, false, true, strFormTempTitleOF,
							true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ClkOnShowQuickLink(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User
			 * Links'. Expected Result:No Expected Result
			 */
			// 123825

			try {
				assertEquals("", strFuncResult);
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

			/*
			 * STEP : Action:Click on 'Create New User Link' Expected
			 * Result:'Create New User Link' screen is displayed.
			 */
			// 123826

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Attach a file, select form F1, select 'Quick Link'
			 * and click on 'Save'. Expected Result:Quick link is created
			 * successfully.
			 */
			// 123827

			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUserLinks.createUserLinkForForm(selenium,
						strLablText6, strExternalURL, true,
						strAutoFilePath, strUploadFilePath1, strAutoFileName, false, true, strFormTempTitleOF, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'show' associated with newly created quick
			 * link. Expected Result:No Expected Result
			 */
			// 123828
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ClkOnShowQuickLink(selenium,
						strLablText6);
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
			 * STEP : Action:Login as U1. Expected Result:Newly created quick
			 * link is displayed on top of the screen along with the existing
			 * five quick links.
			 */
			// 123841

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks1.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.verifyQuickLinkAtTheTopOfTheScreen(selenium,
									strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-23416";
			gstrTO = "Verify that only six quick links for form can be created.";
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

	// end//testFTS23416//
	
	//start//testFTS23417//
	/***************************************************************
	'Description	:Verify that only six quick links can be created for combination of website and form links.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/18/2013
	'Author			:Suhas
	'---------------------------------------------------------------
	'Modified Date				           Modified By
	'Date					               Name
	***************************************************************/

	@Test
	public void testFTS23417() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		Forms objForms = new Forms();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "23417"; // Test Case Id
			gstrTO = " Verify that only six quick links can be created for combination of website and form links.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// User Link
			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2 };
			String[] strQuickLinks1 = { strLablText3, strLablText4,
					strLablText5 };
			String[] strQuickLinks2 = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5, strLablText6 };
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage4.jpg";

			String strUploadFilePath1 = pathProps
					.getProperty("CreateEve_UploadImageFile_OpenPath");
			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition: Only five quick links with
			 * combination of website and forms are created and displayed on the
			 * top of all the screens. Expected Result:No Expected Result
			 */
			// 123842
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
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
			// Form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navTocreateNewFormTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						seleniumPrecondition, strFormTempTitleOF,
						strDescription, strFormActiv, strComplFormDel, false,
						false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Adding question to from
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navFormSecuritySettingPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(
						seleniumPrecondition, strUserName, strFormTempTitleOF,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating quick links
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, false, true, strFormTempTitleOF,
							true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ClkOnShowQuickLink(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			for (int i = 0; i < strQuickLinks1.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks1[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User
			 * Links'. Expected Result:No Expected Result
			 */
			// 123843
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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

			/*
			 * STEP : Action:Click on 'Create New User Link' Expected
			 * Result:'Create New User Link' screen is displayed.
			 */
			// 123844

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Attach a file, provide URL or select a form F1,
			 * select 'Quick Link' and click on 'Save'. Expected Result:Quick
			 * link is created successfully.
			 */
			// 123845

			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUserLinks.createUserLinkForForm(selenium,
						strLablText6, strExternalURL, true, strAutoFilePath,
						strUploadFilePath1, strAutoFileName, false, true,
						strFormTempTitleOF, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ClkOnShowQuickLink(selenium,
						strLablText6);
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
			 * STEP : Action:Click on 'show' associated with newly created quick
			 * link. Expected Result:Newly created quick link is displayed on
			 * top of the screen along with the existing five quick links.
			 */
			// 123846

			// Login as user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks2.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.verifyQuickLinkAtTheTopOfTheScreen(selenium,
									strQuickLinks2[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-23417";
			gstrTO = "Verify that only six quick links can be created for combination of website and form links.";
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

	// end//testFTS23417//
	
	//start//testFTS23419//
	/*****************************************************************************************
	'Description	:Verify that a user link for website cannot be edited and changed to quick
	                 link for website when there are already six quick links for website.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/30/2013
	'Author			:Suhas
	'-----------------------------------------------------------------------------------------
	'Modified Date				                                          Modified By
	'Date					                                              Name
	******************************************************************************************/
	
	@Test
	public void testFTS23419() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		try {
			gstrTCID = "23419"; // Test Case Id
			gstrTO = "Verify that a user link for website cannot be edited and changed to quick" +
					" link for website when there are already six quick links for website.";// TO
			gstrReason = "";
			gstrResult = "FAIL";
	
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			// User Link
			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String strLablText7 = "QLink7" + strTimeText;
			
			String[] strQuickLinks = { strLablText1, strLablText2,
					strLablText3, strLablText4, strLablText5};
			String[] strQuickLinks1 = { strLablText6,strLablText7};
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage4.jpg";	

	
			/*
			 * STEP:Action:1. Five quick links for website are created and displayed on 
			 * the top of all the screens.   2. No quick links for form are created.
			 * Expected Result:No Expected Result
			 */
			// 123842
			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID+ " EXECUTION STARTS~~~~~");
	
			// Login
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks[i], strExternalURL, true,
							strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
	
			for (int i = 0; i < strQuickLinks1.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks1[i], strExternalURL, false,
							strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRECONDITION" + gstrTCID+ " EXECUTION ENDS~~~~~");
	
			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User Links'. 
			 * Expected Result:No Expected Result
			 */
			// 123843
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
	
			try {
				assertEquals("", strFuncResult);
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
			 * STEP : Action:Click on 'Edit' associated with user link. 
			 * Expected Result:'Edit User Link' screen is displayed. 
			 */
			// 123844
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navEditUserLinkPage(selenium,
						strLablText6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select quick link and save. 
			 *  Expected Result:The page is saved and the quick link is displayed on the top of the screen. 
			 */
			// 123845
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.selAndDeselQuickLink(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.saveUserLink(selenium, strLablText6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
						selenium, strLablText6);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP :Action:Select another user link and click on 'Edit'. 
			 *  Expected Result:No Expected Result 
			 */
			// 123846
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navEditUserLinkPage(selenium,
						strLablText7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP :Action:Select quick link and click on 'Save'. 
			 * Expected Result:An error message stating no more than six quick links can be shown is displayed.
  	  		   The user link is not changed to quick link.
  	  		   The original six quick links are retained in the list and on top of the screen. 
			 */
			// 123846
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.selAndDeselQuickLink(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clkOnSaveAndVfyErrMsg(selenium);
			} catch (AssertionError Ae) {
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "23419"; // Test Case Id
			gstrTO = "Verify that a user link for website cannot be edited and changed to quick"
					+ " link for website when there are already six quick links for website.";// TO
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

	// end//testFTS23419//

	//start//testFTS23421//
	/***************************************************************
	'Description		:Verify that a user link for website cannot be edited and changed to quick link for 
	                     website when there are already six quick links with combination of form and website.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/3/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				                Modified By
	'Date					                    Name
	***************************************************************/

	@Test
	public void testFTS23421() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		Forms objForms = new Forms();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "23421"; // Test Case Id
			gstrTO = " Verify that a user link for website cannot be edited and changed to quick link for "
					+ "website when there are already six quick links with combination of form and website.";// Test//Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// User Link
			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2, strLablText3 };
			String[] strQuickLinks1 = { strLablText4, strLablText5,
					strLablText6 };

			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage4.jpg";
			String strLablText7 = "Userlink";

			String strUploadFilePath1 = pathProps
					.getProperty("CreateEve_UploadImageFile_OpenPath");
			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition: <br>Only five quick links combination
			 * of form and website are created and displayed on the top of all
			 * the screens. Expected Result:No Expected Result
			 */
			// 123906
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
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

			// Form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navTocreateNewFormTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						seleniumPrecondition, strFormTempTitleOF,
						strDescription, strFormActiv, strComplFormDel, false,
						false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Adding question to from
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navFormSecuritySettingPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(
						seleniumPrecondition, strUserName, strFormTempTitleOF,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating quick links
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, false, true, strFormTempTitleOF,
							true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ClkOnShowQuickLink(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			for (int i = 0; i < strQuickLinks1.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks1[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin and navigate to 'Setup >> User
			 * Links'. Expected Result:No Expected Result
			 */
			// 123907

			try {
				assertEquals("", strFuncResult);
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
			 * STEP : Action:Click on 'Edit' associated with user link. Expected
			 * Result:'Edit User Link' screen is displayed.
			 */
			// 123908

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUserLinks.createUserLinkForForm(selenium,
						strLablText7, strExternalURL, false, strAutoFilePath,
						strUploadFilePath1, strAutoFileName, true, false,
						strFormTempTitleOF, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ClkOnShowQuickLink(selenium,
						strLablText7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select quick link and save. Expected Result:The
			 * page is saved and the quick link is displayed on the top of the
			 * screen.
			 */
			// 123909

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navEditUserLinkPage(selenium,
						strLablText7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select another user link and click on 'Edit'.
			 * Expected Result:No Expected Result
			 */
			// 123910

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillUserLinkFieldsInEditUserPage(
						selenium, strLablText7, strExternalURL, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select quick link and click on 'Save'. Expected
			 * Result:An error message stating no more than six quick links can
			 * be shown is displayed. The user link is not changed to quick
			 * link. The original six quick links are retained in the list and
			 * on top of the screen.
			 */
			// 123911

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clkOnSaveAndVfyErrMsg(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int i = 0; i < strQuickLinks1.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.verifyQuickLinkAtTheTopOfTheScreen(selenium,
									strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			for (int i = 0; i < strQuickLinks.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.verifyQuickLinkAtTheTopOfTheScreen(selenium,
									strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-23421";
			gstrTO = "Verify that a user link for website cannot be edited and changed to quick link for website when there are already six quick links with combination of form and website.";
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

	// end//testFTS23421//
	
//start//testFTS23420//
	/****************************************************************************************
	'Description		:Verify that a user link for form cannot be edited and changed to  
	                     quick link for form when there are already six quick links for form.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/3/2013
	'Author				:Suhas
	'----------------------------------------------------------------------------------------
	'Modified Date				                                             Modified By
	'Date					                                                 Name
	******************************************************************************************/

	@Test
	public void testFTS23420() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();
		boolean blnLogin = false;
		UserLinks objUserLinks = new UserLinks();
		Forms objForms = new Forms();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "23420"; // Test Case Id
			gstrTO = " Verify that a user link for form cannot be edited and changed to quick link for" +
					" form when there are already six quick links for form.";//TO
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Regions", 4, 3);
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// User Link
			String strLablText1 = "QLink1" + strTimeText;
			String strLablText2 = "QLink2" + strTimeText;
			String strLablText3 = "QLink3" + strTimeText;
			String strLablText4 = "QLink4" + strTimeText;
			String strLablText5 = "QLink5" + strTimeText;
			String strLablText6 = "QLink6" + strTimeText;
			String[] strQuickLinks = { strLablText1, strLablText2, strLablText3 };
			String[] strQuickLinks1 = { strLablText4, strLablText5,
					strLablText6 };

			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage4.jpg";
			String strLablText7 = "Userlink";

			String strUploadFilePath1 = pathProps
					.getProperty("CreateEve_UploadImageFile_OpenPath");
			// form
			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormActiv = "User Initiate & Fill Out Him/Herself";
			String strComplFormDel = "Template Defined";
			String strQuestion = "Q" + strTimeText;
			String strDescription = "Description";
			String strquesTypeID = "Free text field";

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Precondition:
				1.User U1 has activate right on F1.
				2.Five quick links for form F1 are created and displayed on the top of all the screens of user U1.
				3.No quick links for website are created. 
 			   Expected Result:No Expected Result
			 */
			// 123906
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Login
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
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

			// Form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navTocreateNewFormTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.filAllFieldsInCreatNewFormForHimORHer(
						seleniumPrecondition, strFormTempTitleOF,
						strDescription, strFormActiv, strComplFormDel, false,
						false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Adding question to from
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.navFormSecuritySettingPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUserInFormSettingPage(
						seleniumPrecondition, strUserName, strFormTempTitleOF,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Creating quick links
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.deleteAllUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < strQuickLinks.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
							seleniumPrecondition, strQuickLinks[i],
							strExternalURL, false, true, strFormTempTitleOF,
							true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ClkOnShowQuickLink(
							seleniumPrecondition, strQuickLinks[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			for (int i = 0; i < strQuickLinks1.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.navTocreateUserLink(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
							seleniumPrecondition, strQuickLinks1[i],
							strExternalURL, true, strUploadFilePath, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
							seleniumPrecondition, strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP:Action:Login as RegAdmin and navigate to 'Setup >> User Links'.
			 *  Expected Result:No Expected Result
			 */
			// 123907

			try {
				assertEquals("", strFuncResult);
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
			 * STEP : Action:Click on 'Edit' associated with user link. Expected
			 * Result:'Edit User Link' screen is displayed.
			 */
			// 123908

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strFuncResult = objUserLinks.createUserLinkForForm(selenium,
						strLablText7, strExternalURL, false, strAutoFilePath,
						strUploadFilePath1, strAutoFileName, true, false,
						strFormTempTitleOF, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ClkOnShowQuickLink(selenium,
						strLablText7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select quick link and save.
			 *  Expected Result:The page is saved and the quick link is displayed on the top of the
			 * screen.
			 */
			// 123909

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navEditUserLinkPage(selenium,
						strLablText7);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select another user link and click on 'Edit'.
			 * Expected Result:No Expected Result
			 */
			// 123910

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillUserLinkFieldsInEditUserPage(
						selenium, strLablText7, strExternalURL, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select quick link and click on 'Save'. Expected
			 * Result:An error message stating no more than six quick links can
			 * be shown is displayed. The user link is not changed to quick
			 * link. The original six quick links are retained in the list and
			 * on top of the screen.
			 */
			// 123911

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clkOnSaveAndVfyErrMsg(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int i = 0; i < strQuickLinks1.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.verifyQuickLinkAtTheTopOfTheScreen(selenium,
									strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			for (int i = 0; i < strQuickLinks.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objUserLinks
							.verifyQuickLinkAtTheTopOfTheScreen(selenium,
									strQuickLinks1[i]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-23420";
			gstrTO = "Verify that a user link for form cannot be edited and changed to quick link for " +
					"form when there are already six quick links for form.";
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

	// end//testFTS23421//

}
