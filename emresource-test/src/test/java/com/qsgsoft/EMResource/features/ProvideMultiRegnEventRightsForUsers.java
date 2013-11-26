package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;

import java.util.Properties;

import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
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

/**********************************************************************
' Description :This class includes Provide 'multi-region event rights'
'				 for user requirement testcases
' Precondition:
' Date		  :17-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class ProvideMultiRegnEventRightsForUsers  {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ProvideMultiRegnEventRightsForUsers");
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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
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
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
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
		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	/***************************************************************************
	'Description	:Select Region B in 'Edit Multi-Region Event Rights'
	'		         screen when editing user account of user U1 from 
	'				 region A and verify that the user U1 can create 
	'				 multi region event in region B.
	'Precondition	:1. User A is created with the following rights.
	'				 a. Access to both the region A and region B.	
	'				 b. 'Maintain Events' right in both the regions.
	'				 c. Multi region event template ET1 is created in region A.
	'				 d. Multi region event template ET2 is created in region B.
	'Arguments		:None
	'Returns		:None
	'Date	 		:17-April-2012
	'Author			:QSG
	'----------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************/
	
	@Test
	public void testBQS69094() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application		
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class	
		SearchUserByDiffCrteria objSearchUserByDiffCrteria=new SearchUserByDiffCrteria();
		Regions objRegions=new Regions();// object of class Regions
		EventSetup objEventSetup=new EventSetup();
		try {
			gstrTCID = "BQS-69094 ";
			gstrTO = "Select Region B in 'Edit Multi-Region Event Rights'"
					+ " screen when editing user account of user U1 from region"
					+ " A and verify that the user U1 can create multi region "
					+ "event in region B.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Regions", 3, 6);			
			String strRegionName = rdExcel.readData("Regions", 4, 6);	
			String strRegionValues[] =new String[2];
			
			// USER
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			
			//search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			// Event template
			String strTempName1 = "Autotmp_1" + strTimeText;
			String strTempName2 = "Autotmp_2" +strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			String strETValues[]=new String[2];
			
			//Event
			String strEveName1="AutoEve_1"+strTimeText;
			String strEveName2="AutoEve_2"+strTimeText;
			
			
			/*Preconditions:1. User A is created with the following rights.
			 * a. Access to both the region A and region B.
			 * b. 'Maintain Events' right in both the regions.
			 * c. Multi region event template ET1 is created in region A.
			 * d. Multi region event template ET2 is created in region B. 
			 */
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(seleniumPrecondition, strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(seleniumPrecondition, strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(seleniumPrecondition, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(seleniumPrecondition,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						strRegionValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			 //Event template1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = {};
				String[] strStatusTypeval = {};
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName1, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValues[0] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName1);
				if (strETValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Event template2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = {};
				String[] strStatusTypeval = {};
				strFuncResult = objEventSetup.filMndfldsEveTempAsMultiEveTemp(
						seleniumPrecondition, strTempName2, strTempDef, strEveColor, true,
						true, strResTypeVal, strStatusTypeval, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strETValues[1] = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName2);
				if (strETValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
			 log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION ENDS~~~~~");
				log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			
			/*
			 * STEP 1: Login as RegAdmin and navigate to Setup>>Users.<-> 'Users
			 * List' page is displayed.
			 */

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
			/*
			 * STEP 3: Click on 'Edit' link associated with user U1 and then click on
			 * 'Multi-Region Event Rights'link under 'User Preferences'.<-> 'Edit
			 * Multi-Region Event Rights' screen is displayed with regions A and
			 * B.
			 */ 
			
			/*
			 * STEP 4: Select regions A and B and save. <->'Edit User' screen is
			 * displayed.
			 */
			/* STEP 5: Save the page. >-<'Users List' page is displayed. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRegnName = {
						"css=input[name='userRegionID'][value='"
								+ strRegionValues[0] + "']",
						"css=input[name='userRegionID'][value='"
								+ strRegionValues[1] + "']" };
				strFuncResult = objRegions.editMultiRegnEventRites(selenium,
						strRegnName);
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
			
			/*
			 *STEP 6: Login as User U1 in region A and navigate to Event>>Event
			 * Management.<-> 'Create new multi-region event' button is available.
			 */ 
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals(true, strFuncResult);

				try {
					assertTrue(selenium.isElementPresent(propElementDetails
							.getProperty("CreateMultiRegnEvent")));
					log4j
							.info("'Create new multi-region event' button is available. ");
				} catch (AssertionError Ae) {
					strFuncResult = "'Create new multi-region event' button is NOT available. ";
					log4j
							.info("'Create new multi-region event' button is NOT available. ");
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValues[1]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValues[0]
								+ "'][value='" + strETValues[1] + "']" };
				strFuncResult = objEventSetup
						.createMultiRegnEventMandatory(selenium, strEveName1,
								strTempDef, strEvntTemplateNames);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*STEP 9:Logout and login as user U1 in region B and navigate to Event>>Event Management. 
			 * 		The event banner of EV1 is displayed in region B. 
			 * 
			 */
			
			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName1 + "']"));
					log4j.info("Multi Event " + strEveName1
							+ "is displayed in the banner");
					strFuncResult = objLogin.logout(selenium);
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName1 + "']"));
					log4j.info("Multi Event " + strEveName1
							+ "is displayed in the banner");
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strEvntTemplateNames = {
						"css=input[name='region-" + strRegionValues[1]
								+ "'][value='" + strETValues[0] + "']",
						"css=input[name='region-" + strRegionValues[0]
								+ "'][value='" + strETValues[1] + "']" };
				strFuncResult = objEventSetup
						.createMultiRegnEventMandatory(selenium, strEveName2,
								strTempDef, strEvntTemplateNames);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*12 	Logout and login as user U1 in region A.
			 *  		The event banner of EV2 is displayed in region A. 
			 */
			
			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName2 + "']"));
					log4j.info("Multi Event " + strEveName2
							+ "is displayed in the banner");
					strFuncResult = objLogin.logout(selenium);
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium.isElementPresent("//a[text()='"
							+ strEveName2 + "']"));
					log4j.info("Multi Event " + strEveName2
							+ "is displayed in the banner");
					gstrResult = "PASS";
				} catch (AssertionError Ae) {
					log4j.info("Multi Event is NOT displayed in the banner");
					strFuncResult = "Multi Event is NOT displayed in the banner";
					gstrReason = "Multi Event is NOT displayed in the banner"
							+ Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69094";
			gstrTO = "Select Region B in 'Edit Multi-Region Event Rights'"
					+ " screen when editing user account of user U1 from region"
					+ " A and verify that the user U1 can create multi region "
					+ "event in region B.";
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
