package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description         :This class includes  requirement testcases
' Requirement Group   :Creating & managing users 
' Requirement         :Provide access to regions for a user 
' Date		          :06-nov-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                        Modified By
' <Date>                           	                     <Name>
'*******************************************************************/
public class FTSSwitchUser {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSSwitchUser");
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
	
	/***************************************************************
	'Description		:Verify that RegAdmin can switch to a user who has access to single region.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11/7/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS88871() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		try {
			gstrTCID = "88871"; // Test Case Id
			gstrTO = " Verify that RegAdmin can switch to a user who has access to single region.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			System.out.println(strUserName);
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			/*
			 * STEP : Action:Precondition: User U1 is created in Region RG1
			 * Expected Result:No Expected Result
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

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup>>Users.
			 * Expected Result:'Users List' screen is displayed
			 * Edit,Password,Regions and Switch links are associated with User.
			 */
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyActionFieldForUser(
						selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Switch' link associated with user U1.
			 * Expected Result:RegAdmin is logged into the account of user U1
			 * and has the rights of U1. Full name of U1 is displayed at the
			 * bottom right corner.
			 */
			try {
				assertEquals("", strFuncResult);
				selenium
						.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
								+ strUserName
								+ "']/parent::tr/td[1]/a[text()='Switch']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					assertEquals("Region Default", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Region Default' Screen of" + strUserName
							+ " is displayed ");
					log4j.info("RegAdmin is logged into the account of user "
							+ strUserName + " and has the rights of "
							+ strUserName);
				} catch (AssertionError Ae) {

					strFuncResult = "Region Default' Screen of" + strUserName
							+ " is NOT displayed " + Ae;
					log4j.info("Region Default' Screen of" + strUserName
							+ " is NOT displayed" + Ae);
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium,
						strUsrFulName, strUserName);
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
			gstrTCID = "88871";
			gstrTO = "Verify that RegAdmin can switch to a user who has access to single region.";
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
'Description		:Verify that RegAdmin can switch to a user who has access to multiple regions.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:11/7/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testFTS88872() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Regions objRegion = new Regions();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		try {
			gstrTCID = "88872"; // Test Case Id
			gstrTO = " Verify that RegAdmin can switch to a user who has access to multiple regions.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			ReadEnvironment objReadEnvironment = new ReadEnvironment();
			propEnvDetails = objReadEnvironment.readEnvironment();
			
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
			
			// Region fields
			String strRegionName1 = rdExcel.readData("Regions", 4, 5);
			String strRegionName2 = rdExcel.readData("Regions", 5, 5);

			String strRegionValue[] = new String[2];
			strRegionValue[0] = "";
			strRegionValue[1] = "";

			// user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			/*
			 * STEP : Action:Precondition: User U1 is created by giving access
			 * to multiple regions (RG1,RG2,RG3) Expected Result:No Expected
			 * Result
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
				strFuncResult = objRegion.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);

				strRegionValue[0] = objRegion.fetchRegionValue(selenium,
						strRegionName1);
				if (!strRegionValue[0].equals("")) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch region value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);

				strRegionValue[1] = objRegion.fetchRegionValue(selenium,
						strRegionName2);
				if (!strRegionValue[1].equals("")) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch region value";
				}

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
			try {
				assertEquals("", strFuncResult);
				String strRegionVal[] = { strRegionValue[0], strRegionValue[1] };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						selenium, strUserName, strRegionVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP : Action:Login as RegAdmin to Region RG1 and navigate to
			 * Setup>>Users. Expected Result:'Users List' screen is displayed
			 * Edit,Password,Regions and Switch links are associated with User.
			 */
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyActionFieldForUser(
						selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Switch' link associated with user U1.
			 * Expected Result:RegAdmin is logged into the account of user U1
			 * and has the rights of U1. Full name of U1 is displayed at the
			 * bottom right corner.
			 */
			try {
				assertEquals("", strFuncResult);
				selenium
						.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
								+ strUserName
								+ "']/parent::tr/td[1]/a[text()='Switch']");
				selenium.waitForPageToLoad(gstrTimeOut);
				try {
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					assertEquals("Region Default", selenium
							.getText(propElementDetails
									.getProperty("Header.Text")));

					log4j.info("Region Default' Screen of" + strUserName
							+ " is displayed ");
					log4j.info("RegAdmin is logged into the account of user "
							+ strUserName + " and has the rights of "
							+ strUserName);
				} catch (AssertionError Ae) {

					strFuncResult = "Region Default' Screen of" + strUserName
							+ " is NOT displayed " + Ae;
					log4j.info("Region Default' Screen of" + strUserName
							+ " is NOT displayed" + Ae);
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium,
						strUsrFulName, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Region Name displayed at top right of the
			 * page. Expected Result:Select Region screen is displayed with
			 * message 'You have access to multiple regions. Please select which
			 * one you would like to view.'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navSelRegnPageVarMsg(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select region 'RG2' and click on next Expected
			 * Result:U1 is logged into the account of user U1 in Region 'RG2'
			 * and has the rights of U1 in 'RG2'. Full name of U1 is displayed
			 * at the bottom right corner.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName1);
				log4j
						.info("RegAdmin is logged into the account of user "
								+ strUserName + " and has the rights of "
								+ strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium,
						strUsrFulName, strUserName);
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
			gstrTCID = "88872";
			gstrTO = "Verify that RegAdmin can switch to a user who has access to multiple regions.";
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
