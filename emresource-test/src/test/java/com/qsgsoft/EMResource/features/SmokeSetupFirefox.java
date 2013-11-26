package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.UserLinks;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.qsgsoft.EMResource.shared.*;

/**********************************************************************
' Description         :This class includes test cases related to
' Requirement Group   :   Smoke Test Suite
' Requirement         :   Setup 
' Precondition:
' Date		          :30-May-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class SmokeSetupFirefox {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.DeleteUserLink");
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
	
	Selenium selenium,seleniumPrecondition;
	
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
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
						.getProperty("urlEU"));
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
			    4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
			    .getProperty("urlEU"));

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
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();

		try {
			selenium.close();
		} catch (Exception e) {

		}
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
	'Description		:Verify that a user-link can be created.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/12/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'2/25/2012					Name
	***************************************************************/

	@Test
	public void testSmoke97760() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		UserLinks objUserLinks = new UserLinks();
		try {
			gstrTCID = "97760"; 
			gstrTO = " Verify that a user-link can be created.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User Link
			String strLablText = "USER" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";
			
			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup>>User links
			 * Expected Result:'User Link List' page is displayed.
			 */
			// 577596
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create new user link' Expected
			 * Result:'Create a new user link' screen is displayed The following
			 * options are available in the 'Create a new user link' screen: a.
			 * Label Text b. Image File c. Select type of link to create The
			 * following options are available under this field: i. URL for an
			 * external site (which is selected by default) ii. EMResource Form
			 * d. Quick Link
			 */
			// 577597
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navTocreateUserLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.verFieldsInCreateUserLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide Label text, attach a file and provide a URL
			 * and save. Expected Result:The created user link is listed on the
			 * User Link list screen with the following columns: a. Action i.
			 * Edit ii. Delete iii. Show iv. Up b. Link: Label text provided c.
			 * Show as: (Hide) d. Image: The attached image e. Destination URL:
			 * URL provided f. Image size: Attached file size (in pixels) g.
			 * File size: Attached file size (in bytes) Option 'User links' is
			 * not displayed at the top right of the screen
			 */
			// 577598
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
						selenium, strLablText, strExternalURL, false,
						strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						strLablText, strShow, strExternalURL, strImgSize,
						strFileSize);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show' associated with user link Expected
			 * Result:The link 'Show' is changed to 'Hide' 'User Link' is
			 * displayed under the column 'Show As' The image is not displayed
			 * at the top of the screen (as a 'Quick Link').
			 */
			// 577600
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.showTheLinkAndVerify(selenium,
						strLablText, "User Link");
				try {
					assertFalse(selenium
							.isElementPresent("//div[@id='quickUserLinks-in']/a/img[@title='"
									+ strLablText + "']"));
					log4j.info("The image is NOT displayed at the top of the screen (as a ''Quick Link'').");
				} catch (AssertionError Ae) {
					strFuncResult = "The image is displayed at the top of the screen (as a ''Quick Link'').";
					log4j.info("The image is displayed at the top of the screen (as a ''Quick Link'').");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP : Action:Place the mouse over the 'User links' link at the
			 * top right of the screen Expected Result:The user link is
			 * displayed with the image and the label text.
			 */
			// 577601
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the link. Expected Result:The provided URL
			 * is opened in a new window
			 */
			// 577602
			try {
				assertEquals("", strFuncResult);
				String strTitle = "Google";
				strFuncResult = objUserLinks
						.openImgLinkAndVerifyUsrLinkByUsrLinkName(selenium,
								strExternalURL, strTitle, strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>User links, Click on 'Hide'
			 * associated with link. Expected Result:Link 'Hide' is changed to
			 * 'Show' 'Hide' is displayed under the column 'Show As' Option
			 * 'User links' is not displayed at the top right of the screen
			 */
			// 577603
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.clickOnHideAndVerifyShowNew(selenium,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText,
						false);
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
			gstrTCID = "BQS-97760";
			gstrTO = "Verify that a user-link can be created.";
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
'Description		:Verify that a user-link can be edited.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:9/13/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testSmoke97791() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		UserLinks objUserLinks = new UserLinks();
		try {
			gstrTCID = "97791";
			gstrTO = "Verify that a user-link can be edited.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User Link
			String strLablText = "AutoUL" + strTimeText;
			String strExternalURL = "www.google.com";

			String strEdLablText = "AutoEUL" + strTimeText;
			String strEdExternalURL = "www.yahoo.com";

			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Precondition: <br> <br>1. User link 'Test' is
			 * created providing URL and selecting the option 'Show' Expected
			 * Result:No Expected Result
			 */
			// 577694
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navTocreateUserLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
						selenium, strLablText, strExternalURL, false,
						strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.showTheLinkAndVerify(selenium,
						strLablText, "User Link");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup>>User links
			 * Expected Result:'User Link list' screen is displayed
			 */
			// 577695
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' corresponding to a user link 'Test'
			 * created by providing a URL Expected Result:The data entered is
			 * retained in the 'Edit user link' screen. <br> <br>The option to
			 * change the attached file is not available.
			 */
			// 577696
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToEditLinkPage(selenium,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.veriftDataRetainedInEditUserPage(
						selenium, strLablText, strExternalURL, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Edit the 'Label Text', 'URL' and save. Expected
			 * Result:The edited user link is listed on the User Link list
			 * screen with the following columns: <br> <br>a. Action <br>i. Edit
			 * <br>ii. Delete <br>iii. Hide <br>iv. Up <br> <br>b. Link: Label
			 * text <br> <br>c. Show as: User Link <br> <br>d. Image: The
			 * attached image <br> <br>e. Destination URL: Updated URL <br>
			 * <br>f. Image size: Attached file size (in pixels) <br> <br>g.
			 * File size: Attached file size (in bytes)
			 */
			// 577697
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillUserLinkFieldsInEditUserPage(
						selenium, strEdLablText, strEdExternalURL, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Hide";
				String strLablHeaderActionUp = "Up";
				strFuncResult = objUserLinks.verifyLinkHeadersLinks(selenium,
						strEdLablText, strLablHeaderAction,
						strLablHeaderActionEdit, strLablHeaderActionDelete,
						strLablHeaderActionShow, strLablHeaderActionUp);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strShow = "User Link";
				String strImgSize = "32x27";
				String strFileSize = "16.55 kb";
				strFuncResult = objUserLinks.varOtherFldsInUserLink(selenium,
						strEdLablText, strShow, strEdExternalURL, strImgSize,
						strFileSize);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Place the mouse over the 'User links' link at the
			 * top right of the screen. Expected Result:The edited user link is
			 * displayed in the list.
			 */
			// 577698
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium,
						strEdLablText, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the link Expected Result:The new URL is
			 * opened in a new window.
			 */
			// 577699
			try {
				assertEquals("", strFuncResult);
				String strTitle = "Yahoo India";
				strFuncResult = objUserLinks
						.openImgLinkAndVerifyUsrLinkByUsrLinkName(selenium,
								strEdExternalURL, strTitle, strEdLablText);
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
			gstrTCID = "BQS-97791";
			gstrTO = "Verify that a user-link can be edited.";
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
			selenium.selectWindow("");
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}
	//start//testBQS103456//
	/***************************************************************
	'Description	:Verify that a quick-link can be edited.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/6/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				                         Modified By
	'Date					                             Name
	***************************************************************/

	@Test
	public void testSmoke103456() throws Exception {
		Login objLogin = new Login();// object of class Login
		String strFuncResult = "";
		boolean blnLogin = false;
		CreateUsers objCreateUsers = new CreateUsers();
		UserLinks objUserLinks = new UserLinks();
		Forms objForms = new Forms();
		
		try {
			gstrTCID = "103456"; // Test Case Id
			gstrTO = " Verify that a quick-link can be edited.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Data for creating user
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserNameA;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strUploadFilePath = pathProps
					.getProperty("CreateEve_UploadImageFile_OpenPath");

			String strLablText = "Link" + System.currentTimeMillis();
			strLablText = "USER" + strTimeText;
			String strExternalURL = "www.google.com";
			strUploadFilePath = pathProps.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";

			/*
			 * STEP : Action:Preconditions: <br> <br>1.Quick link of 'URL for an
			 * external site' is created.(Ex:Test external site) <br> <br>2.
			 * Quick link for 'EMResource form' is created.(Ex:Test Form) <br>
			 * <br>3. Users 'U1' and 'U2' are created Expected Result:No
			 * Expected Result
			 */
			// 594745

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION STATRTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
				strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				String strFormTempTitleOF = "";
				strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
						seleniumPrecondition, strLablText,
						strExternalURL, true, false, strFormTempTitleOF, true,
						strUploadFilePath,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			String strFormTempTitleNF = "NF" + strTimeText;
			String strFormDesc = "AutoDescription";
			String strQuestion = "Q" + strTimeText;
			String strquesTypeID = "Free text field";
			String strFormActiv = "User Initiate & Other To Fill Out";
			String strComplFormDel = "User To Users And Resources";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(
						seleniumPrecondition, strFormTempTitleNF, strFormDesc,
						strFormActiv, strComplFormDel, false, false, false,
						false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUserName = "";
				strFuncResult = objForms.selectUsersForForm(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource = "";
				strFuncResult = objForms.selectResourcesForForm(
						seleniumPrecondition, strResource, strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Adding a questionarri to a form
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToCreateNewQuestion(
						seleniumPrecondition, strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strQuestion = "Q" + System.currentTimeMillis();
				String strDescription = "Description";
				strquesTypeID = "Free text field";
				strFuncResult = objForms.ToCreateQuestion(seleniumPrecondition,
						strFormTempTitleNF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Quick link 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strFormTempTitleOF = strFormTempTitleNF;
			String strLablText1 = "USER1" + strTimeText;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.createUserLinkFirefoxForForm(
						seleniumPrecondition, strLablText1, strExternalURL,
						false, true, strFormTempTitleOF, true,
						strUploadFilePath, true);
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
						seleniumPrecondition, strUserNameA, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserNameB, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole,
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup >>User
			 * links Expected Result:'User Link List' screen is displayed
			 */
			// 594746

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

		/*
		 * STEP : Action:Click on 'Edit' corresponding to a quick link
		 * created for 'URL for an external site'. Expected Result:The data
		 * entered in all the fields is retained in the 'Edit user link'
		 * screen. <br> <br>The option to change the attached file is not
		 * available.
		 */
		// 594754

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToEditLinkPage(
						seleniumPrecondition, strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.veriftDataRetainedInEditUserPage(seleniumPrecondition,
								strLablText, strExternalURL, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Edit the label text and save. Expected Result:The
		 * Quick link is listed on the User Link list screen with the
		 * following columns: <br>a. Action <br>i. Edit <br>ii. Delete
		 * <br>iii. Hide <br>iv. Up <br>v. Down <br>b. Link: Updated Label
		 * text <br>c. Show as: Quick Link <br>d. Image: The attached image
		 * <br>e. Destination URL: URL <br>f. Image size: Attached file size
		 * (in pixels) <br>g. File size: Attached file size (in bytes)
		 */
		// 594756

			String strEdLablText = "EditLabel" + strTimeText;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillUserLinkFieldsInEditUserPage(
						seleniumPrecondition, strEdLablText,

						strExternalURL, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
						seleniumPrecondition, strEdLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Hide";
				String strLablHeaderActionUp = "Up";
				strFuncResult =objUserLinks.verifyLinkHeadersLinks(seleniumPrecondition,
						strEdLablText, strLablHeaderAction,
						strLablHeaderActionEdit,
						strLablHeaderActionDelete, strLablHeaderActionShow,
						strLablHeaderActionUp);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strShow = "Quick Link";
				String strImgSize = "32x27";
				String strFileSize = "16.55 kb";
				strFuncResult =objUserLinks.varOtherFldsInUserLink(seleniumPrecondition,
						strEdLablText, strShow, strExternalURL,
						strImgSize, strFileSize);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >>User links,Click on 'Edit'
			 * corresponding to a quick link created for 'EMResource form'
			 * Expected Result:The data entered in all the fields is retained in
			 * the 'Edit user link' screen. <br> <br>The option to change the
			 * attached file is not available.
			 */
			// 594757

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToEditLinkPage(
						seleniumPrecondition, strLablText1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.verDataRetainInEditUsrLinkPageForForm(
								seleniumPrecondition, strLablText1,
								strFormTempTitleNF, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Edit the label text and save. Expected Result:The
		 * Quick link is listed on the User Link list screen with the
		 * following columns: <br>a. Action <br>i. Edit <br>ii. Delete
		 * <br>iii. Hide <br>iv. Up <br>v. Down <br>b. Link: Updated Label
		 * text <br>c. Show as: Quick Link <br>d. Image: The attached image
		 * <br>e. Destination URL: URL <br>f. Image size: Attached file size
		 * (in pixels) <br>g. File size: Attached file size (in bytes)
		 */
		// 594758

			String strFrmEdLablText = "EditLabel" + strTimeText;
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ProvDataForLabelTextFld(
						seleniumPrecondition, strFrmEdLablText, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
						seleniumPrecondition, strFrmEdLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Show";
				String strLablHeaderActionHide = "Hide";
				String strLablHeaderActionUp = "Up";
				String strLablHeaderActionDown = "Down";
				strFuncResult = objUserLinks
						.verifyLinkHeadersInUserLinkListPage(
								seleniumPrecondition, strFrmEdLablText,
								strLablHeaderAction, strLablHeaderActionEdit,
								strLablHeaderActionDelete,
								strLablHeaderActionHide,
								strLablHeaderActionShow, strLablHeaderActionUp,
								strLablHeaderActionDown, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strShow = "Quick Link";
				String strImgSize = "32x27";
				String strFileSize = "16.55 kb";
				strFuncResult = objUserLinks.varOtherFldsInUserLink(
						seleniumPrecondition, strFrmEdLablText, strShow,
						strFormTempTitleNF, strImgSize, strFileSize);
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
			gstrTCID = "BQS-103456";
			gstrTO = "Verify that a quick-link can be edited.";
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

		// end//testSmoke103456//
	}
	//start//testSmoke103454//
	/***************************************************************
	'Description	:Verify that a quick-link can be created.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/6/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				                     Modified By
	'Date					                         Name
	***************************************************************/

	@Test
	public void testSmoke103454() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		UserLinks objUserLinks = new UserLinks();
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		General objGeneral = new General();
		try {
			gstrTCID = "103454"; // Test Case Id
			gstrTO = " Verify that a quick-link can be created.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Data for creating user
			String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr_2" + System.currentTimeMillis();
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
			String strDescription = "Description";
			String strQuestion = "Q" + strTimeText;
			String strquesTypeID = "Free text field";

			// User Link
			String strLablText = "ULink1_" + strTimeText;
			String strLablText2 = "ULink2_" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage.jpg";
		/*
		* STEP :
		  Action:Preconditions:
		In region 'RG1' User 'U1' and 'U2' are created selecting 'Form - User may activate forms' right.
        F1 by selecting 'New forms' and by selecting 'User initiate and fill out him/herself' for
         'Form Activation' and 'Template defined' for 'Completed form delivery'  providing User U1 the 
         right 'Activate form' and user U2 the right 'Run report'
		  Expected Result:No Expected Result
		*/
		//594700
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
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

			// USER 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName2, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName2, strByRole,
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
				String strUserName = "";
				strFuncResult = objForms.selectUsersForFormNew(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Adding a questionarri to a form
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
						seleniumPrecondition, strUserName1, strFormTempTitleOF,
						true, false, false);
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
						seleniumPrecondition, strUserName2, strFormTempTitleOF,
						false, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				
			/*
			* STEP :
			  Action:Login as RegAdmin and navigate to Setup >>User links
			  Expected Result:'User Link List' screen is displayed with list of user links and quick
			   links created in region.
	          'Create New User Link' button is displayed above the list.
			*/
			//594701
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navToUserLinkList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = (propElementDetails
						.getProperty("UserLink.CreateNewUsrLink"));
				strFuncResult = objGeneral.CheckForElements(
						seleniumPrecondition, strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New User Link' button is displayed above the list.");
				} else {
					log4j.info("'Create New User Link' button is NOT displayed above the list.");
					strFuncResult = "'Create New User Link' button is NOT displayed above the list.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			/*
			* STEP :
			  Action:Click on 'Create new user link'
			  Expected Result:The following options are available in the 'Create a new user link' screen:
				a. Label Text
				b. Image File ('Browse' button)
				c. Select type of link to create
				i. URL for an external site (which is selected by default)
				ii. EMResource Form
				d. Quick Link (Check box deselected by default)
			*/
			//594702
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.navTocreateUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.verFieldsInCreateUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Provide Label text, attach a file and provide a URL, select 'Quick link' and save.
			  Expected Result:The created user link is listed on the User Link list screen
					  User link is listed on the User Link list screen with the following columns:
					a. Action
					i. Edit
					ii. Delete
					iii. Show
					iv. Up
					b. Link: Label text provided
					c. Show as: (Hide)
					d. Image: The attached image
					e. Destination URL: URL provided
					f. Image size: Attached file size (in pixels)
					g. File size: Attached file size (in bytes)
			*/
			//594703

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
						seleniumPrecondition, strLablText, strExternalURL,
						true, strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Show";
				String strLablHeaderActionUp = "Up";
				strFuncResult = objUserLinks.verifyLinkHeadersLinks(
						seleniumPrecondition, strLablText, strLablHeaderAction,
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
				strFuncResult = objUserLinks.varOtherFldsInUserLink(
						seleniumPrecondition, strLablText, strShow,
						strExternalURL, strImgSize, strFileSize);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Login as user U1
			  Expected Result:The image is not displayed at the top of the screen (as a ''Quick Link'').
			*/
			//594704
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName1, strInitPwd);
				if (strFuncResult.equals("")) {
					assertFalse(seleniumPrecondition
							.isElementPresent("//div[@id='quickUserLinks-in']/a/img[@title='"
									+ strLablText + "']"));
					log4j.info("The image is NOT displayed at the top of the screen (as a ''Quick Link'').");

				} else {
					log4j.info("The image is displayed at the top of the screen (as a ''Quick Link'').");
					strFuncResult = "The image is displayed at the top of the screen (as a ''Quick Link'').";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Login as  RegAdmin, navigate to Setup >>User links
			  Expected Result:'User Link List' screen is displayed with list of user links 
			  and quick links created in region.
			*/
			//594705
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

			/*
			* STEP :
			  Action:Click on 'Show'
			  Expected Result:The link 'Show' is changed to 'Hide' and 'Quick Link' is displayed under the column 'Show As'
				The image is displayed at the top of the screen.
				The tool tip on the image displays the label text provided
			*/
			//594706
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.ShowQuickLinkAndVerify(
						seleniumPrecondition, strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Click on the link(image) at the top of the screen
			  Expected Result:The URL is opened in a new window
			*/
			//594707
			try {
				assertEquals("", strFuncResult);
				String strTitle = "Google";
				strFuncResult = objUserLinks.openQuickLinkAndVerifyUrl(
						seleniumPrecondition, strExternalURL, strTitle,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Login as user U1, click on the link(image) at the top of the screen.
			  Expected Result:The URL is opened in a new window
			*/
			//594708
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strUserName1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strTitle = "Google";
				strFuncResult = objUserLinks.openQuickLinkAndVerifyUrl(
						seleniumPrecondition, strExternalURL, strTitle,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Login as RegAdmin navigate to Setup>>User links and click on 'Create new user link'
		  Expected Result:'Create a new user link' screen is displayed. 
			The following options are available in the 'Create a new user link' screen:
			a. Label Text
			b. Image File ('Browse' button)
			c. Select type of link to create
			i. URL for an external site (which is selected by default)
			ii. EMResource Form
			d. Quick Link (Check box deselected by default)
		*/
		//594709
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						.navTocreateUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.verFieldsInCreateUserLink(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Provide Label text, attach a file, select ''EMResource Form'' for ''Select type of link to create'',
			   select the form F1, select ''Quick link'' and save.
			  Expected Result:The created user link is listed on the User Link list screen with the following columns:
				a. Action
				i. Edit
				ii. Delete
				iii. Show
				iv. Up
				b. Link: Label text provided
				c. Show as: (Hide)
				d. Image: The attached image
				e. Destination URL: Form name
				f. Image size: Attached file size (in pixels)
				g. File size: Attached file size (in bytes)
			*/
			//594710
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.fillcreateUserLinkFldsFirefox(
						seleniumPrecondition, strLablText2, strExternalURL,
						true, strUploadFilePath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strLablHeaderAction = "Action";
				String strLablHeaderActionEdit = "Edit";
				String strLablHeaderActionDelete = "Delete";
				String strLablHeaderActionShow = "Show";
				String strLablHeaderActionUp = "Up";
				strFuncResult = objUserLinks.verifyLinkHeadersLinks(
						seleniumPrecondition, strLablText2,
						strLablHeaderAction, strLablHeaderActionEdit,
						strLablHeaderActionDelete, strLablHeaderActionShow,
						strLablHeaderActionUp);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strShow = "(Hide)";
				String strImgSize = "32x27";
				String strFileSize = "16.55 kb";
				strFuncResult = objUserLinks.varOtherFldsInUserLink(
						seleniumPrecondition, strLablText2, strShow,
						strExternalURL, strImgSize, strFileSize);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as user U1
		  Expected Result:The image is not displayed at the top of the screen (as a ''Quick Link'').
		*/
		//594711
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strUserName1, strInitPwd);
				if (strFuncResult.equals("")) {
					assertFalse(selenium
							.isElementPresent("//div[@id='quickUserLinks-in']/a/img[@title='"
									+ strLablText + "']"));
					log4j.info("The image is NOT displayed at the top of the screen (as a ''Quick Link'').");
				} else {
					log4j.info("The image is displayed at the top of the screen (as a ''Quick Link'').");
					strFuncResult = "The image is displayed at the top of the screen (as a ''Quick Link'').";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
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
				strTestData[2] = strUserName1 + "/" + strUserName2 + "/" + "/"
						+ strInitPwd;
				strTestData[4] = strFormTempTitleOF;
				strTestData[5] = "Verify from 13th step.";
				strTestData[6] = strFormTempTitleOF;
				strTestData[7] = strquesTypeID;
				strTestData[9] = strLablText;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103454";
			gstrTO = "Verify that a quick-link can be created.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnLogin) {
			strFuncResult = objLogin.logout(seleniumPrecondition);
			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}

	// end//testSmoke103454//
}