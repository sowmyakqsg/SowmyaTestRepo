package com.qsgsoft.EMResource.features;

import java.util.Date;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.SearchUserByDiffCrteria;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;

import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

import static org.junit.Assert.*;
/**********************************************************************
' Description :This class includes Switch User requirement testcases
' Precondition:
' Date		  :4-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class SwitchUser  {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.SwitchUser");
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
	
	
	
	/*******************************************************************
	'Description	:Verify that a Web Service user can be created. 
	'Precondition	:User U1 is created selecting a right from 'Advanced
	'				 Options' section (eg. User - Setup User Accounts). 
	'Arguments		:None
	'Returns		:None
	'Date	 		:9-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS69042() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class //
														// CreateUsers
		General objGeneral = new General();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "BQS-69042 ";
			gstrTO = "Verify that RegAdmin " + "can switch to another user. ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strElementID = "";
			String strUserNameRight = "";

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * Precondition: User U1 is created selecting a right from 'Advanced
			 * Options' section (eg. User - Setup User Accounts). No Expected
			 * Result
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users. 'Users
			 * List' screen is displayed.
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
				selenium
						.click("//table[@id='tblUsers']/tbody/tr/td[2][text()='"
								+ strUserName
								+ "']/parent::tr/td[1]/a[text()='Switch']");
				selenium.waitForPageToLoad(gstrTimeOut);
				log4j
						.info("RegAdmin is logged into the account of user "
								+ strUserName + " and has the rights of "
								+ strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Selecting the basic window
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				strElementID = propElementDetails
						.getProperty("UsrName.RightCorner");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3: Click on 'Switch' link associated with user U1.<->
			 * RegAdmin is logged into the account of user U1 and has the rights
			 * of U1. Full name of U1 is displayed at the bottom right corner.
			 */

			try {
				assertEquals("", strFuncResult);
				strUserNameRight = selenium.getText(propElementDetails
						.getProperty("UsrName.RightCorner"));
				log4j.info(strUserNameRight);
				String strFulName[] = strUserNameRight.split("\\|");

				strFulName[0] = strFulName[0].substring(0, 23);

				for (String s : strFulName) {
					System.out.println(s);
				}
				if (strFulName[0].equals(strUsrFulName)) {
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69042";
			gstrTO = "Verify that RegAdmin can switch to another user. ";
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
