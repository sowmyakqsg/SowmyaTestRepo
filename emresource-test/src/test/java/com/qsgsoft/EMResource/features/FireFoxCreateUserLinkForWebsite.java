
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

/**********************************************************************
' Description        :This class includes 
' Requirement        :Create a quick link for a website
' Requirement Group  :Setting User links up               
' Date		         :22-May-2012
' Author	         :QSG
'-------------------------------------------------------------------
' Modified Date                                          Modified By
' <28/8/2012>                           	                <Name>
'*******************************************************************/

public class FireFoxCreateUserLinkForWebsite {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FireFoxCreateUserLinkForWebsite");
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
	String gstrTimeOut = "";

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
				4444, propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
  /********************************************************************************************
   'Description		:Verify that a user link can be created of type ''URL for an external site'
   'Arguments		:None
   'Returns		    :None
   'Date			:8/29/2012
   'Author			:QSG
  '--------------------------------------------------------------------------------------------
   'Modified Date				                                                   Modified By
   'Date					                                                            Name
  *********************************************************************************************/

	@Test
	public void testBQS406() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		UserLinks objUserLinks = new UserLinks();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		try {
			gstrTCID = "406"; // Test Case Id
			gstrTO = " Verify that a user link can be created of type ''URL for an external site''";// Test
																									// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;

			// User Link

			String strLablText = "USER" + strTimeText;
			String strExternalURL = "www.google.com";
			String strUploadFilePath = pathProps
					.getProperty("UsrLnk_UploadFilePath") + "GetLinkImage.jpg";

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

			// USER
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
	/*
	* STEP :
	  Action:Login as RegAdmin and navigate to Setup>>User links
	  Expected Result:No Expected Result
	*/
	//1467

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Click on ''Create new user link''
	  Expected Result:The following options are available in the "Create a new user link'' screen:
	  a. Label Text
	  b. Image File
	  c. Select type of link to create
	        The following options are available under this field:
	               i. URL for an external site (which is selected by default)
	               ii. EMResource Form
	   d. Quick Link
	*/
	//1468

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
	* STEP :
	  Action:Provide Label text, attach a file and provide a URL and save.
	  Expected Result:The created user link is listed on the User Link list screen with the following columns: 
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
	//1469			
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
	* STEP :
	  Action:Place the mouse over the ''User links'' link at the top right  of the screen
	  Expected Result:The user link is not displayed in the list.
	*/
	//1470
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText, false);
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
	* STEP :
	  Action:Login as user U1 and place the mouse over the ''User links'' link at the top right  of the screen
	  Expected Result:The user link is not displayed in the list.
	*/
	//1471
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:From RegAdmin, navigate to Setup>>User links
	  Expected Result:No Expected Result
	*/
	//1472
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
	* STEP :
	  Action:Click on ''Show''
	  Expected Result:The link ''Show'' is changed to ''Hide'' and ''User Link'' is displayed under the column ''Show As''
			  The image is not displayed at the top of the screen (as a ''Quick Link'').
	*/
	//1473
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
	* STEP :
	  Action:Place the mouse over the ''User links'' link at the top right  of the screen
	  Expected Result:The user link is displayed with the image and the label text.
	*/
	//1474
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Click on the link.
	  Expected Result:The URL is opened in a new window
	*/
	//1475
			try {
				assertEquals("", strFuncResult);
				String strTitle = "Google";
				strFuncResult = objUserLinks
						.openImgLinkAndVerifyUsrLinkByUsrLinkName(selenium,
								strExternalURL, strTitle, strLablText);
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
	* STEP :
	  Action:From User U1, place the mouse over the ''User links'' link at the top right of the screen
	  Expected Result:The user link is displayed with the image and the label text.
	*/
	//1476
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on the link.
	  Expected Result:The URL is opened in a new window
	*/
	//1477

			try {
				assertEquals("", strFuncResult);
				String strTitle = "Google";
				strFuncResult = objUserLinks
						.openImgLinkAndVerifyUsrLinkByUsrLinkName(selenium,
								strExternalURL, strTitle, strLablText);
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
	* STEP :
	  Action:From RegAdmin, navigate to Setup>>User links
	  Expected Result:No Expected Result
	*/
	//1478
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
	* STEP :
	  Action:Click on ''Hide''
	  Expected Result:The link ''Hide'' is changed to ''Show''
	   and the ''Hide'' is displayed under the column ''Show As''
	*/
	//1479
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks
						.clickOnHideAndVerifyHideInShowAsCol(selenium,
								strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	/*
	* STEP :
	  Action:Place the mouse over the ''User links'' link at the top right  of the screen
	  Expected Result:The user link is not displayed in the list.
	*/
	//1480
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText,
						false);
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
	* STEP :
	  Action:From User U1, place the mouse over the ''User links'' link at the top right of the screen
	  Expected Result:The user link is not displayed in the list.
	*/
	//1481
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.varUserLink(selenium, strLablText, false);
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
	gstrTCID = "BQS-406";
	gstrTO = "Verify that a user link can be created of type ''URL for an external site''";
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

	/**************************************************************************************************
	'Description	:Verify that a user link can be created by entering data in only mandatory fields.
	'Arguments		:None
	'Returns		:None
	'Date			:27-08-2012
	'Author			:QSG
	'--------------------------------------------------------------------------------------------------
	'Modified Date				                                                          Modified By
	'Date					                                                              Name
	**************************************************************************************************/
	@Test
	public void testBQS719() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		UserLinks objLink = new UserLinks();
		boolean blnUsrLink = false;
		String strExternalURL = "http://www.qsgsoft.com";
		try {
			gstrTCID = "BQS-719";
			gstrTO = " Verify that a user link can be created by entering data in only mandatory fields.";

			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// Admin login credential
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strLinkName = "";
			String strUplPath = pathProps.getProperty("UsrLnk_UploadFilePath")
					+ "GetLinkImage.jpg";

			String strTitle = "Offshore Application & Software Testing Services Company | Outsource Software QA Testing";
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			String strRegn = "";

			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				strRegn = rdExcel.readData("Login", 3, 4);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLink.navToUserLinkList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLink.createUserLinkFirefoxNew(selenium,
						strLinkName, strExternalURL, false, strUplPath, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnUsrLink = true;
				strFuncResult = objLink.showTheLinkAndVerify(selenium,
						strExternalURL, "User Link");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLink.openImgLinkAndVerifyUsrLink(selenium,
						strExternalURL, strTitle);
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
			gstrTCID = "BQS-719";
			gstrTO = " Verify that a user link can be created by entering data in only mandatory fields.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnUsrLink) {
			selenium.selectWindow("");
			strFuncResult = objLink.deleteUsrLink(selenium, strExternalURL);

			try {
				assertEquals("", strFuncResult);

			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult
						+ " User Link is NOT deleted Please Delete and Re-run";
			} catch (Exception e) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult
						+ " User Link is NOT deleted Please Delete and Re-run";
			}

		}
		if (blnLogin) {
			strFuncResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
		}
	}

}

