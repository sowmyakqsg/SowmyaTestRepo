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
' Description         :This class includes Delete Folder requirement test cases
' Requirement Group   :Reports
' Requirement         :Creating and managing users
' Date		          :23-Nov-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                         Modified By
' <Date>                           	                     <Name>
'*******************************************************************/

public class FTSCreatingAndManagingUsers_AutoIT {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreatingAndManagingUsers_AutoIT");
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
	/***************************************************************
	'Description	:Verify that user with 'Report-Statewide Resource details' right do not 
	                 have the option to generate 'Statewide resource details' report in any 
	                 other region apart from 'Statewide Florida' region.
	'Arguments		:None
	'Returns		:None
	'Date			:11/23/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS100368() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();
		Reports objReports = new Reports();
		try {
			gstrTCID = "100368"; // Test Case Id
			gstrTO = " Verify that user with 'Report-Statewide Resource details' right do not have the option to generate 'Statewide resource details' report in any other region apart from 'Statewide Florida' region.";// Test
																																																							// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyHHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 22, 4);
			String strRegn2 = rdExcel.readData("Login", 26, 4);
			String strRegionValue[] = new String[1];

			// user
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			System.out.println(strUserName1);
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".xls";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			/*
			 * STEP : Action:Precondition: 1. User U1 is created in 'Statewide
			 * Florida' region and is provided access to 'Central Florida'
			 * region. 2. User is provided a right 'Report-Statewide Resource
			 * report' right' in both the regions. Expected Result:No Expected
			 * Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// fetch Region value
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegions.fetchRegionValue(selenium,
						strRegn2);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName1,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						selenium, strUserName1, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Region3 data
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StateWideResourceDetailReport");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as user U1 to 'Central Florida' region.
			 * Expected Result:'Reports' tab is not visible in the 'menu'
			 * header.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium.isElementPresent("link=Report"));
					log4j.info("'Reports' tab is NOT visible in the 'menu' header. ");
				} catch (AssertionError Ae) {
					log4j.info("'Reports' tab is visible in the 'menu' header. ");
					strFuncResult = "'Reports' tab is visible in the 'menu' header. ";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
			/*
			 * STEP : Action:Click on the region name and select 'Statewide
			 * Florida' region. Expected Result:'Region Default' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				selenium.click("id=regionName");
				selenium.waitForPageToLoad(gstrTimeOut);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Reports >> Resource reports. Expected
			 * Result:'Resource Reports Menu' screen is displayed 'Statewide
			 * Resource Details' link is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				try {
					assertTrue(selenium
							.isElementPresent("link=Statewide Resource Details"));
					log4j.info("'Statewide Resource details' link is Displayed");
				} catch (AssertionError Ae) {
					log4j.info("'Statewide Resource details' link is NOT Displayed. ");
					strFuncResult = "'Statewide Resource details' link is NOT displayed. ";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Statewide Resource Details' link, select
			 * any of the check box under 'Standard Resources' and click on
			 * 'Next'. Expected Result:'Statewide Resource Detail Report (Step 2
			 * of 2)' page is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStdResTypeVal = { "106" };
				String[] strStdRt = { "Hospital" };
				strFuncResult = objReports.selectStdResTypeManyForReport(
						selenium, strStdResTypeVal, strStdRt, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select any of the check box under 'Standard
			 * statuses' and click on 'Generate report' Expected
			 * Result:'Statewide Resource Details' report excel is generated.
			 */
			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "4004";
				String strStdStatusName = "Average Wait Time";
				strFuncResult = objReports.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt < 300);

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
			gstrTCID = "100368";
			gstrTO = "Verify that user with 'Report-Statewide Resource details' right do not have the option to generate 'Statewide resource details' report in any other region apart from 'Statewide Florida' region.";
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
