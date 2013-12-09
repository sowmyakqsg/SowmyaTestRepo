package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/****************************************************************
' Description         : This class includes test cases related to
' Requirement Group   : Smoke Test Suite
' Requirement         : Setup 
' Date		          : 30-May-2012
' Author	          : QSG
'-----------------------------------------------------------------
' Modified Date                                       Modified By
' <Date>                           	                  <Name>
'*****************************************************************/

public class Smoke_SetupTest  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Smoke_Setup");
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
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
			    4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
			    .getProperty("urlEU"));
		
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
		
	/********************************************************
	'Description	:Verify that a user-link can be deleted
	'Arguments		:None
	'Returns		:None
	'Date	 		:11-Sep-2012
	'Author			:QSG
	'--------------------------------------------------------
	'Modified Date                              Modified By
	'<Date>                                      <Name>
	*********************************************************/

	@Test
	public void testSmoke91675() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		UserLinks objUserLinks = new UserLinks();

		try {
			gstrTCID = "91675 ";
			gstrTO = "Verify that a user-link can be deleted";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			// String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = "";
			String strLablText = "";
						
		/*
		 * STEP 2: Login as RegAdmin and navigate to Setup>>User links User Link
		 * list screen is displayed
		 */	
			log4j.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strRegn = rdExcel.readData("Login", 3, 4);
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
		 * 1 Precondition: A user link has been created and is shown under
		 * 'User Links' section which is displayed at the top right corner
		 * of the screen (in the menu header). No Expected Result
		 */
			try{
				assertEquals("",strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");

				String strUploadFilePath = pathProps
						.getProperty("CreateEve_UploadImageFile_OpenPath");
				
				strLablText="Link"+System.currentTimeMillis();
				String strExternalURL="www.google.com";
				boolean blnQuicklaunch=false;
				
				strFuncResult=objUserLinks.createUserLink(selenium, 
						strLablText, strExternalURL,
						blnQuicklaunch, strAutoFilePath, strUploadFilePath,
						strAutoFileName,true);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}			
			try{
				assertEquals("",strFuncResult);
				strFuncResult=objUserLinks.showTheLinkAndVerifyInSubNav(selenium, strLablText);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}			
		/*
		 * STEP 3: Click on ''Delete'' corresponding to a user link.<-> The
		 * user link is no longer present in the user link list screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.deleteUserLink(selenium,
						strLablText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP 4: Place the mouse over the ''User links'' link at the top right
		 * of the screen.<-> The user link is no longer displayed in the list.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUserLinks.verifyUserLinkInHeader(selenium,
						strLablText);
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
			gstrTCID = "91675";
			gstrTO = "Verify that a user-link can be deleted";
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
/***********************************************************************
'Description	:Verify that RegAdmin can remove access from regions for
                   a user who currently have access to multiple regions.
'Arguments		:None
'Returns		:None
'Date			:9/12/2012
'Author			:QSG
'------------------------------------------------------------------------
'Modified Date				                                 Modified By
'Date					                                      Name
**************************************************************************/

	@Test
	public void testSmoke91699() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Regions objRegions = new Regions();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "91699"; // Test Case Id
			gstrTO = " Verify that RegAdmin can remove access from regions for a user who currently "
					+ "have access to multiple regions.";// Test // Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// Region data
			String strRegionName1 =  rdExcel.readData("Regions", 4, 5);
			String strRegionName2 =  rdExcel.readData("Regions", 5, 5);
			String strRegionValues[] = new String[3];

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
		  Action:Preconditions:
	      User 'U1' is created in Region A,providing mandatory data and giving access to multiple 
	      regions(RegionA,B,C).
		  Expected Result:No Expected Result.
		*/

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID + " EXECUTION STATRTS~~~~~");

			
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
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Region 1 Value
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn);
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
				strRegionValues[1] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegionName1);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				strRegionValues[2] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegionName2);
				if (strRegionValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
						strRegionValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						strRegionValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");		
	/*
	* STEP :
	  Action:Login as RegAdmin into region A and navigate to Setup>>Users.
	  Expected Result:'Users List' page is displayed.
	*/
	//552507
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Click on the 'Regions' link associated with user U1.
	  Expected Result:'Edit User Regions' screen is displayed with
      1. User Profile
      Username: 
      Full Name:  
      Organization: 
      2. User Regions 
      Regions:
      (List of all the regions with check Box )
      Check Boxes for the regions A,B,C are selected.
	*/
	//552512
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium,
								strUserName, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFldsInEditUsrRgn(selenium, strUserName);
				assertTrue(selenium.isElementPresent("css=input[value='"+strRegionValues[0]+"']"));
				log4j.info("List of all the regions with check Box is displayed");
			} catch (AssertionError Ae) {
				log4j.info("List of all the regions with check Box is NOT  displayed");
				strFuncResult="List of all the regions with check Box is NOT displayed";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[1], strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[2], strRegionName2);
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
	  Action:Login as user U1.
	  Expected Result:'Select Region' screen is displayed.
      Regions A,B and C re the options in the 'Region' dropdown field.
	*/
	//552521
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToSelectRegionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionListForUser(selenium, strRegionValues[0], true, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionListForUser(selenium, strRegionValues[1], true, strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionListForUser(selenium, strRegionValues[2], true, strRegionName2);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
	/*
	* STEP :
	  Action:Login as RegAdmin into region A and navigate to Setup>>Users. 
      Click on the 'Regions' link associated with user U1.
	  Expected Result:'Edit User Regions' screen is displayed.
	*/
	//552522
			try {
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium,
								strUserName, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	/*
	* STEP :
	  Action:Deselect check boxes for Regions B,C and click on Save.
	  Expected Result:'Users List' page is displayed.
	*/
	//552523
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[2], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
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
	  Action:Login as user U1.
	  Expected Result:User is logged into Region A.
      'Select Region' screen is not displayed.
	*/
	//552525
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
				try {
					assertEquals(strRegn, selenium.getText("id=regionName"));
					log4j.info("User is logged into Region A " + strRegn);
				} catch (AssertionError Ae) {
					log4j.info("User is NOT logged into Region A " + strRegn);
					strFuncResult = "User is NOT logged into Region A "
							+ strRegn;
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToSelectRegionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Select Region' screen is NOT displayed.",
						strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-91699";
			gstrTO = "Verify that RegAdmin can remove access from regions for a user"
					+ " who currently have access to multiple regions.";
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
/***************************************************************
'Description	:Verify that a user can be edited.
'Arguments		:None
'Returns		:None
'Date			:13-09-2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date			                        	Modified By
'Date					                            Name
***************************************************************/

	@Test
	public void testSmoke97754() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		RegionalInfo objReg = new RegionalInfo();
		Preferences objPref = new Preferences();
		try {
			gstrTCID = "97754"; // Test Case Id
			gstrTO = " Verify that a user can be edited.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName, strLoginPassword, strRegn, strUserName, strInitPwd, strConfirmPwd, 
			strUsrFulName, strFirstName, strMiddleName, strLastName, strOrganization, strPhoneNo, strPrimaryEMail,
			strEMail, strPagerValue, strAdminComments;

			// login details
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			strRegn = rdExcel.readData("Login", 3, 4);

			strUserName = "AutoUsr" + System.currentTimeMillis();
			strInitPwd = "abc123";
			strConfirmPwd = "abc123";

			strUsrFulName = "Ful" + strUserName;
			strFirstName = "auto";
			strMiddleName = "l";
			strLastName = "q";
			strOrganization = "qsg";
			strPhoneNo = "2228889";
			strPrimaryEMail = "autoemr@qsgsoft.com";
			strEMail = "autoemr@qsgsoft.com";
			strPagerValue = "autoemr@qsgsoft.com";
			strAdminComments = "";

			String strEdUserFulName = "FulEd" + strUserName;
			String strEdFirstName = "autoEd";
			String strEdMiddleName = "s";
			String strEdLastName = "g";
			String strEdOrganization = "ems";
			String strEdPhoneNo = "8228889";
			String strEdPrimaryEMail = "autoemr@qsgsoft.com";
			String strEdEMail = "autoemr@qsgsoft.com";
			String strEdPagerValue = "autoemr@qsgsoft.com";
			strAdminComments = "";
			
			// search user data
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
		/*
		 * STEP : Action:Preconditions:1. User 'U1' is created
		 * providing all the data. Expected Result:No Expected Result
		 */
		// 577562
		
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
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
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

		/*
		 * STEP : Action:Login as RegAdmin, navigate to Setup >> Users
		 * Expected Result:'Users List' page is displayed
		 */
		// 577563
		/*
		 * STEP : Action:Click on edit link associated with user 'U1' edit
		 * all the data. Expected Result:User U1 is listed in the 'Users
		 * List' screen under Setup. <br> <br>Updated data for user U1 is
		 * displayed on the 'Users List' page.
		 */
		// 577564
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
			
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrFulName(selenium,
						strEdUserFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strEdFirstName, strEdMiddleName,
						strEdLastName, strEdOrganization, strEdPhoneNo,
						strEdPrimaryEMail, strEdEMail, strEdPagerValue,
						strAdminComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUserDetailsInUserList(
						selenium, strUserName, strEdUserFulName,
						strEdOrganization);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Regional Info >> Users Expected
		 * Result:User U1 is listed in the 'Users List'. <br> <br>Updated
		 * data for user U1 is displayed on the 'Users List' page.
		 */
		// 577565

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReg.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReg.verifyUserDetailsInRegInfoUserList(
						selenium, strUserName, strEdUserFulName,
						strEdOrganization,strEdPhoneNo,strEdPrimaryEMail+","+strEdEMail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Login as user U1 Expected Result:'Region Default'screen is displayed.
		 * User U1's updated full name is
		 * displayed at the bottom right of the application
		 */
		// 577566

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varFullNameAtTheFooter(selenium,
						strEdUserFulName, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to 'Preferences >> User Info' Expected
		 * Result:Updated data is retained on the 'Update User Info' screen.
		 */
		// 577567

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPref.navigateToUserInfo(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.vrfyUserFields(selenium,
						strEdUserFulName, strEdFirstName, strEdMiddleName,
						strEdLastName, strEdOrganization, strEdPhoneNo,
						strEdPrimaryEMail, strEdEMail, strEdPagerValue, "");
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
			gstrTCID = "BQS-97754";
			gstrTO = "Verify that a user can be edited.";
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
/**************************************************************************
'Description	:Verify that a user can be deactivated and then reactivated.
'Arguments		:None
'Returns		:None
'Date			:9/12/2012
'Author			:QSG
'---------------------------------------------------------------------------
'Modified Date				                                     Modified By
'Date					                                         Name
****************************************************************************/

	@Test
	public void testSmoke99633() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "99633"; // Test Case Id
			gstrTO = " Verify that a user can be deactivated and then reactivated.";// Test
																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			// search user data
			String strFILE_PATH = pathProps.getProperty("TestData_path");
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
		  Active User U1 is created.
		  Expected Result:No Expected Result
		*/
		//552487

	    log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
	
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup>>Users.
		  Expected Result:'Users List' screen is displayed.
		*/
		//552489

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the 'Edit' link associated with user U1
		  Expected Result:'Edit User' screen is displayed.
		*/
		//552490
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:select 'User Status' check box under 'Edit User' screen of user 'U1'and save the page.
		  Expected Result:'Confirm User Deactivation' screen is displayed with
	      Warning
		  The user has been saved.
		  You are now about to deactivate the account for <Full name of user> (username: U1)
		  Please Note:
		  �This user will NOT be able to log into EMSystem.
		  �All email addresses for this user will be deleted.
		  �All pager addresses for this user will be deleted.
		  Are you sure you would like to deactivate this user?
		  'Yes, Deactivate this User' and 'No, Do NOT Deactivate this User' buttons.
		*/
		//552491
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.activateAndDeactivateUser(
						selenium, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.varConfirmUserDeactivationPage(
						selenium, strUsrFulName, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Yes, Deactivate this User' button.
		  Expected Result:You have successfully deactivated <full name of user> (username: ...)
		  This user can no longer login.
		  Any email and pager addresses have been deleted.
		  'Return to user list' button is present.
		*/
		//552492			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.NavUserDeactivatePage(selenium,
						strUserName, strUsrFulName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Return to User List' button. 
	      Select 'include inactive users' check box in 'User List' screen.
		  Expected Result:1. Active and inactive users are displayed on the 'Users List' screen.
	      2. Full name of inactive user U1 is struck out.
		*/
		//552493
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as user U1.
		  Expected Result:'Account Deactivated' screen with the message
		  This account has been deactivated.
		  Please contact EMSystem at 1-888-735-9559 (Prompt 1 then 6) for assistance.' is displayed. 
		*/
		//552494
			try {
				assertEquals("", strFuncResult);
				String strText="Please contact EMSystem at 1-888-735-9559 (Prompt 1 then 6) for assistance.";
				String strMsg="This account has been deactivated.";
				strFuncResult = objLogin.loginAsInActiveUsrVarMsg(selenium, strUserName, strInitPwd,strText,strMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup>>Users.
		  Expected Result:'Users List' screen is displayed.
		*/
		//552497
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
		* STEP :
		  Action:Select 'include inactive users' check box. 
			Click on the 'Edit' link associated with user U1 and select 'User Status' check box and save the page.
			Expected Result:'User Activation Complete' screen is displayed with message
			you have successfully re-activated <user full name> (username: ...)
			You or the user will need to reenter any email or pager addresses.
			Return to user list button is present.
		*/
		//552498
				
		/*
		* STEP :
		  Action:Click on 'Return to User List' button.
		  Expected Result:'Users List' screen is displayed with user U1.
		*/
		//552499
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.activateUserVarMsg(selenium, strUserName, strUsrFulName, true);
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
		  Action:Login as user U1.
		  Expected Result:User U1 is logged into EMResource application successfully
		*/
		//552500
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("User " + strUserName
						+ " is logged into EMResource application successfully");
			} catch (AssertionError Ae) {
				log4j.info("User "
						+ strUserName
						+ " is NOT logged into EMResource application successfully");
				strFuncResult = "User "
						+ strUserName
						+ " is NOT logged into EMResource application successfully";
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
			gstrTCID = "BQS-99633";
			gstrTO = "Verify that a user can be deactivated and then reactivated.";
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
/***************************************************************
'Description	:Verify that a region interface can be created.
'Arguments		:None
'Returns		:None
'Date			:9/14/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/
	@Test
	public void testSmoke91700() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;// keep track of logout of application
		Login objLogin = new Login();// object of class Login
		Interface objInterface = new Interface();

		try {
			gstrTCID = "91700"; // Test Case Id
			gstrTO = " Verify that a region interface can be created.";//TO
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
			// interface data

			String strIntLable = "Post HAvBED 2.5.2 Information";
			String strWebAction = "postHAvBEDStatus";
			String strInterfaceNmae = "AutoInt" + strTimeText;
			String strResId = "Use the EMResource Resource ID";
		/*
		 * STEP : Action:Login as ReginAdmin ,navigate to Setup>>Interface.
		 * Expected Result:'Interface List' screen is displayed with
		 * columns: 1. Action 2. Name 3. Active 4. Web Services Action 5.
		 * Type 6. Schema 7. Description 'Create New Interface' button is
		 * available at top left of the screen above column.
		 */
	    //552545
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		
			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navToInterfaceList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Create New Interface' button
		  Expected Result:'Select Interface Type' screen is displayed with:
		  Select Interface Type Drop down;
		  Get CAD Status
		  Get EDXL-HAVE 1.0
		  Get HAvBED 2.5.2 information
		  Get Hospital Status
		  Post HAvBED 2.5.2 information
		  Post Resource Detail Status
		  Update CAD Status
		  Update resource availability
		  Next and Cancel buttons are available.
		*/
		//552547
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface
						.varHeadersInInterfaceList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select any of the interface from Select Interface Type Drop down and Click on next.
		  Expected Result:'Create Interface' screen is displayed with    
		  Name: 
	      Description:  
		  Contact Information:   
		  Web Services Action:(Selected interface type in previous step is displayed.) 
		  Resource Identification Method: (drop down)
		  Active: Check to make this interface active (Check box).
		*/
		//552558
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.CreatRgnInterfaceWithMandFlds(
						selenium, strIntLable, strWebAction, strInterfaceNmae,
						strResId);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Provide data in all the mandatory fields and select Active check box and Click on Save.
		  Expected Result:'Interface List' screen is displayed.
	      Created Interface is listed under it and data provided while creating interface is 
	      displayed under appropriate columns.
		*/
		//552559
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.savAndVerifyInterface(selenium,
						strInterfaceNmae);
				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2]"
									+ "[text()='"
									+ strInterfaceNmae
									+ "']/parent::tr/td[4][text()='"
									+ strWebAction + "']"));
					log4j.info(strWebAction + "  listed in the Interface list");
				} catch (AssertionError Ae) {
					strFuncResult = strWebAction
							+ " NOT listed in the Interface list";
					strFuncResult = gstrReason;
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[2]"
									+ "[text()='"
									+ strInterfaceNmae
									+ "']/parent::tr/td[5][text()='"
									+ strIntLable + "']"));
					log4j.info(strIntLable + "  listed in the Interface list");
				} catch (AssertionError Ae) {
					strFuncResult = strIntLable
							+ " NOT listed in the Interface list" + Ae;
					strFuncResult = gstrReason;
				}

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
			gstrTCID = "BQS-91700";
			gstrTO = "Verify that a region interface can be created.";
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

/********************************************************************************
'Description	:Verify that user with �Override Viewing restrictions� right can
'				 view all the refined status type on 1. Region View screen 2. 
'				 View Resource Detail screen 3. Map screen 4. Event Status screen
'Arguments		:None
'Returns		:None
'Date	 		:18-Sep-2012
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                                                    Modified By
'10-Sep-2012                                                      <Name>
**********************************************************************************/

	@Test
	public void testSmoke99786() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEventList = new EventList();
		Roles objRole = new Roles();
		
	
		try {
			gstrTCID = "99786 ";
			gstrTO = "Verify that user with �Override Viewing restrictions� "
					+ "right can view all the refined status type on 1. Region"
					+ " View screen 2. View Resource Detail screen 3. Map screen"
					+ " 4. Event Status screen";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName = "A1NST" + strTimeText;
			String statTextTypeName = "A1TST" + strTimeText;
			String statMultiTypeName = "A1MST" + strTimeText;
			String statSaturatTypeName = "A1SST" + strTimeText;

			String statPrivateNumTypeName = "A2NST" + strTimeText;
			String statPrivateTextTypeName = "A2TST" + strTimeText;
			String statPrivateMultiTypeName = "A2MST" + strTimeText;
			String statPrivateSaturatTypeName = "A2SST" + strTimeText;

			String statShardNumTypeName = "A3NST" + strTimeText;
			String statShardTextTypeName = "A3TST" + strTimeText;
			String statShardMultiTypeName = "A3MST" + strTimeText;
			String statShardSaturatTypeName = "A3SST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strPrivateStatusName1 = "pSa" + strTimeText;
			String strPrivateStatusName2 = "pSb" + strTimeText;

			String strShardStatusName1 = "sSa" + strTimeText;
			String strShardStatusName2 = "sSb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strPrivateStatusValue[] = new String[2];
			strPrivateStatusValue[0] = "";
			strPrivateStatusValue[1] = "";

			String strShardStatusValue[] = new String[2];
			strShardStatusValue[0] = "";
			strShardStatusValue[1] = "";

			// General variable
			String strStatValue = "";

			String strSTvalue[] = new String[12];
			String strRSValue[] = new String[2];
			strRSValue[0] = "";
			strRSValue[1] = "";

			String strResrctTypName1 = "AutoRt_1" + strTimeText;
			String strResrctTypName2 = "AutoRt_2" + strTimeText;
			String strRTValue[] = new String[2];
			strRTValue[0] = "";
			strRTValue[1] = "";

			String strResource1 = "AutoRs1" + strTimeText;
			String strResource2 = "AutoRs2" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strState = "Alabama";
			String strCountry = "Barbour County";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			String strSection1 = "AB_1" + strTimeText;
			String strArStatType[] = { statNumTypeName, statTextTypeName,
					statMultiTypeName, statSaturatTypeName,
					statPrivateNumTypeName, statPrivateTextTypeName,
					statPrivateMultiTypeName, statPrivateSaturatTypeName,
					statShardNumTypeName, statShardTextTypeName,
					statShardMultiTypeName, statShardSaturatTypeName };

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRolesName = "AutoRol" + strTimeText;
			String strRoleValue = "";

		/*
		 * STEP :Precondition:1. Role based status type rNST,rMST,rSST,rTST, shared status type sNST,sMST,sSST,sTST
		 *  and private status type pNST,pMST,pSST,pTST, are created which are associated to resource type 'RT1' and 'RT2'
					2. Resource 'RS1' is created under 'RT1' and resource 'RS2' is created under 'RT2' providing address.
					3. View 'V1' is created selecting all the above status types role, shared and private (12) status types to resources RS1 and RS2.
					4. Section 'S1' is created selecting all the status types.
					5. Event template 'ET' is created selecting 'RT1' and RT2 along with the status types mentioned above.
					6. Event 'Eve1' is created under 'ET' selecting RS1 and RS2
					7. User 'U1' is created with a role R1.
					8. User U1 does not have view right on resource RS1 and RS2. 
		   Expected Result:No Expected Result 			
		 */
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 1. Role based status type rNST,rMST,rSST,rTST, shared status type
		 * sNST,sMST,sSST,sTST and private status type pNST,pMST,pSST,pTST,
		 * are created which are associated to resource type 'RT1' and 'RT2'
		 */
			
			//Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*****************************************************************************************/
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			/*****************************************************************************************/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, strNSTValue, statPrivateNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statPrivateNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[4]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, strTSTValue, statPrivateTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statPrivateTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[5]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, strSSTValue, statPrivateSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statPrivateSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[6]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, strMSTValue, statPrivateMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statPrivateMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[7]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statPrivateMultiTypeName, strPrivateStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statPrivateMultiTypeName, strPrivateStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statPrivateMultiTypeName, strPrivateStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPrivateStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statPrivateMultiTypeName, strPrivateStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPrivateStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*****************************************************************************************/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statShardNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statShardNumTypeName, strVisibilty, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statShardNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[8]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Creating TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statShardTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statShardTextTypeName, strVisibilty, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statShardTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[9]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Creating SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statShardSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statShardSaturatTypeName, strVisibilty, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statShardSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[10]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Status Type Multi status type is created. 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statShardMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statShardMultiTypeName, strVisibilty, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statShardMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[11]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statShardMultiTypeName, strShardStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statShardMultiTypeName, strShardStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statShardMultiTypeName, strShardStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strShardStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statShardMultiTypeName, strShardStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strShardStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*****************************************************************************************/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objRole.rolesMandatoryFlds(seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			//RT1
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);				
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
				for(int i=1;i<12;i++){
					seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
							+ strSTvalue[i] + "']");
				}
								
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchRTValueInRTList(seleniumPrecondition, strResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//RT2

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
				for(int i=1;i<12;i++){
					seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
							+ strSTvalue[i] + "']");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTValue[1] = objRT.fetchRTValueInRTList(seleniumPrecondition, strResrctTypName2);
				if (strRTValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 2. Resource 'RS1' is created under 'RT1' and resource 'RS2' is
		 * created under 'RT2' providing address.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName2,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 3. View 'V1' is created selecting all the above status types
		 * role, shared and private (12) status types to resources RS1 and
		 * RS2.
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		 * 4. Section 'S1' is created selecting RS1,NST,MST,SST and TST.
		 * Section 'S2' is created selecting RS2, NST,MST,SST and TST .
		 */
		//Section 1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 5. Event template 'ET' is created selecting 'RT1' and RT2 along
		 * with the status types mentioned above.
		 */			
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
				String[] strResTypeValue = { strRTValue[0], strRTValue[1] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strSTvalue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 6. Event 'Eve1' is created under 'ET' selecting RS1 and RS2
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(seleniumPrecondition,
						strTempName, strRSValue, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*7. User 'U1' is created with a role R1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 8. User U1 does not have view right on resource RS1 and RS2.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, false,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1], false, false,
						false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		    log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
		    
		    try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");					
		/*
		 * 2 Login as RegAdmin navigate to setup >> users 'User list' screen
		 * is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 3 Edit user 'U1' and assign right �Override Viewing restrictions�
		 * from advanced option.
		 * 
		 * Refine resource 'RS1' and deselect the check box for
		 * 'rNST','rMST', 'sSST', 'sTST', 'pMST', 'pSST' and save. 'User
		 * list' screen is displayed
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.OverRidingViewingRestrctn");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[10], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[9], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		/*
		 * 4 Login as user 'U1' and navigate to 'View >> V1' Role based
		 * status types rNST,rMST,rSST, rTST, shared status types
		 * sNST,sMST,sSST, sTST and private status types pNST,pMST,pSST,pTST
		 * are displayed under resource type 'RT1' and 'RT2'
		 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName1, strRS,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource2 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName2, strRS,
						strRTValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName,
						statPrivateMultiTypeName, statPrivateNumTypeName,
						statPrivateSaturatTypeName, statPrivateTextTypeName,
						statShardMultiTypeName, statShardNumTypeName,
						statShardSaturatTypeName, statShardTextTypeName };

				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName,
						statPrivateMultiTypeName, statPrivateNumTypeName,
						statPrivateSaturatTypeName, statPrivateTextTypeName,
						statShardMultiTypeName, statShardNumTypeName,
						statShardSaturatTypeName, statShardTextTypeName };

				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 5 Click on resource 'RS1' 'View Resource Detail' screen is
		 * displayed.
		 * 
		 * All the role, shared and private status types (12) are displayed
		 * under section 'S1'
		 */
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objViews.navToViewResourceDetailPage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String[] strStatType = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName,
						statPrivateMultiTypeName, statPrivateNumTypeName,
						statPrivateSaturatTypeName, statPrivateTextTypeName,
						statShardMultiTypeName, statShardNumTypeName,
						statShardSaturatTypeName, statShardTextTypeName };
				
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 6 Navigate to 'View >> Map', select resource 'RS1' from the 'Find
		 * Resources' drop down. All the role, shared and private status
		 * types are displayed in the 'Resource Info' pop up window.
		 */
			try {
				assertEquals("", strFuncResult);
				
				String[] strStatType = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName,
						statPrivateMultiTypeName, statPrivateNumTypeName,
						statPrivateSaturatTypeName, statPrivateTextTypeName,
						statShardMultiTypeName, statShardNumTypeName,
						statShardSaturatTypeName, statShardTextTypeName };
				String[] strEventStatType={};
				
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 7 Select resource 'RS2' from the 'Find Resources' drop down. All
		 * the role, shared and private status types are displayed in the
		 * 'Resource Info' pop up window.
		 */
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType={};

				String[] strStatType = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName,
						statPrivateMultiTypeName, statPrivateNumTypeName,
						statPrivateSaturatTypeName, statPrivateTextTypeName,
						statShardMultiTypeName, statShardNumTypeName,
						statShardSaturatTypeName, statShardTextTypeName };
				
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource2, strEventStatType,
						strStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
		/*
		 * 8 Click on the event banner 'Eve' Role based status types
		 * rNST,rMST,rSST, rTST, shared status types sNST,sMST,sSST, sTST
		 * and private status types pNST,pMST,pSST,pTST are displayed under
		 * resource type 'RT1' and 'RT2'
		 */
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName,
						statPrivateNumTypeName, statPrivateTextTypeName,
						statPrivateMultiTypeName, statPrivateSaturatTypeName,
						statShardNumTypeName, statShardTextTypeName,
						statShardMultiTypeName, statShardSaturatTypeName };

				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName1, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName,
						statPrivateNumTypeName, statPrivateTextTypeName,
						statPrivateMultiTypeName, statPrivateSaturatTypeName,
						statShardNumTypeName, statShardTextTypeName,
						statShardMultiTypeName, statShardSaturatTypeName };

				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName2, strResource2,
						strStatTypeArr);
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
			gstrTCID = "99786";
			gstrTO = "Verify that user with �Override Viewing restrictions� "
					+ "right can view all the refined status type on 1. Region "
					+ "View screen 2. View Resource Detail screen 3. Map screen"
					+ " 4. Event Status screen";
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
	/********************************************************************************************
	'Description  :For a user U1, deselect a status type in the 'Refine Visible Status Types' 
	               window for a resource and save, verify that the user cannot view the 
	               deselected status type from all the view screens for that particular resource.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :9/24/2012
	'Author		  :QSG
	'---------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	**********************************************************************************************/

	@Test
	public void testSmoke99742() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();
		Preferences objPreferences = new Preferences();
		General objGeneral=new General();
	
		
		try {
			gstrTCID = "99742"; // Test Case Id
			gstrTO = " For a user U1, deselect a status type in the 'Refine Visible Status Types' window for a resource" +
					" and save, verify that the user cannot view the deselected status type from all the view screens for" +
					" that particular resource.";//TO																																														// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTestData[] = new String[10];
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn =rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];
			// Resource
			String strResource1 = "AutoRS_1" + strTimeText;
			String strResource2 = "AutoRS_2" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[2];
			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue[] = new String[1];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
		
			String strTempName = "Autotemp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";
			//Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { statTypeName1, statTypeName2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

		/*
		* STEP :
		  Action:Preconditions:
			1. Role-based status types ST1 and ST2 are created selecting role 'R1'
			 under 'Roles with view rights' section.
			2. ST1 and ST2 are associated with resource type RT.
			3. Resources RS1 and RS2 are created under resource type RT with address.
			4. View V1 is created with status types ST1, ST2 and resources RS1 and RS2.
			5. Event Template ET is created with ST1, ST2 and RT.
			6. Event E1 is created under ET selecting RS1 and RS2.
			7. Status types ST1 and ST2 are under status type section S1.
			8. User U1 has the following rights:
			a) Role R1
			b) 'View Resource' and 'Update Status' rights on resource RS1 and RS2.
			c) 'View Custom View' right.
			9. User U1 has added resources RS1, RS2 and status types ST1 and ST2 to his/her custom view.
					  Expected Result:No Expected Result
		*/
		//577751
		
		log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
			     + " EXECUTION STATRTS~~~~~");

			try {
				
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
			
			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		    //ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue={{strRoleValue[0],"true"}};
				String[][] strRoleViewValue={{strRoleValue[0],"true"}};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(seleniumPrecondition, false, false, 
						strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName1);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		    //ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue={{strRoleValue[0],"true"}};
				String[][] strRoleViewValue={{strRoleValue[0],"true"}};
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(seleniumPrecondition, false, false, 
						strRoleViewValue, strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName2);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(seleniumPrecondition, strResource1,
						strAbbrv, strResrcTypName,
						"FN", "LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource1);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//View			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue={strStatusTypeValues[0],strStatusTypeValues[1]};
				String[] strRSValue={strRSValues[0],strRSValues[1]};
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType,
						true, false, strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		
			
			//USER
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(seleniumPrecondition, strResource1,
						strRSValues[0], false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(seleniumPrecondition, strResource2,
						strRSValues[1], false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Event template
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
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource2, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(seleniumPrecondition,
						strEveName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			//Adding custom view
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(selenium, strResource1,
						strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = {statTypeName1, statTypeName2};
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(selenium, strResource2,
						strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = {statTypeName1, statTypeName2};
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION ENDS~~~~~");
		
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");

		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup>>Users
		  Expected Result:'Users List' screen is displayed
		*/
		//577752
		
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
		* STEP :
		  Action:Select to edit user U1, click on the 'Refine' link corresponding to 
		  resource RS1 under 'Resource Rights' section
		  Expected Result:'Refine Visible Status Types' window is displayed. 
          Status types ST1 and ST2 are displayed.
		*/
		//577753
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(selenium, statTypeName1,
						strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySTInRefineVisibleST(selenium, statTypeName2,
						strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Deselect 'ST1' status type for resource RS1 and click on Save.
		  Expected Result:'Edit User' for U1 screen is displayed.
		*/
		//577776
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(selenium, strStatusTypeValues[0],
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Save'
		  Expected Result:'User Lists' screen is displayed.
		*/
		//577780
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
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
		  Action:Logout and login as user U1 and navigate to View>>V1
		  Expected Result:N/A is displayed in the status type cell of ST1 for resource RS1.
          "--" is displayed in the status type cell of ST1 for resource RS2.
		  "--" is displayed in the status type cell of ST1 and ST2 for resource RS1 and RS2.
		*/
		//577754
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr={statTypeName1};
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium, strStatTypeArr, false, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr={statTypeName2};
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium, strStatTypeArr, true, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr={statTypeName1,statTypeName2};
				strFuncResult = objViews.chkSTAssoOrNotInViewScreen(selenium, strStatTypeArr, true, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the key icon corresponding to RS1
		  Expected Result:Only ST2 is displayed on the 'Update status' screen.
		*/
		//577755
			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=img.noprint";
				strFuncResult=objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViews.checkStatTypeInUpdateStat(selenium, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViews.checkStatTypeInUpdateStat(selenium, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the key icon corresponding to RS2
		  Expected Result:Both ST1 and ST2 are displayed on the 'Update status' screen.
		*/
		//577756
			try {
				assertEquals("Status Type "+statTypeName1+"is NOT displayed in Update Status", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strNavElement="css=tr.rtr_"+strRsTypeValues[0]+"_"+strRSValues[1]+".even > td > a > img.noprint";
				strFuncResult=objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViews.checkStatTypeInUpdateStat(selenium, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViews.checkStatTypeInUpdateStat(selenium, statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to View>>Custom
		  Expected Result:N/A is displayed in the status type cell of ST1 for resource RS1.
         "--" is displayed in the status type cell of ST1 for resource RS2.
				  "--" is displayed in the status type cell of ST1 and ST2 for resource RS1 and RS2.
		*/
		//577757

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr={statTypeName1};
				strFuncResult=objPreferences.chkSTAssoOrNotInCustomViewTable(selenium, strResource1, 
						strStatTypeArr, true, strRSValues[0], strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr={statTypeName2};
				strFuncResult=objPreferences.chkSTAssoOrNotInCustomViewTable(selenium, strResource1, 
						strStatTypeArr, false, strRSValues[0], strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr={statTypeName1,statTypeName2};
				strFuncResult=objPreferences.chkSTAssoOrNotInCustomViewTable(selenium, strResource1, 
						strStatTypeArr, true, strRSValues[0], strRsTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


		/*
		* STEP :
		  Action:Navigate to the map view of custom view, select resource RS1 from the 'Find Resource' dropdown.
		  Expected Result:Only ST2 is displayed in the resource pop up window of RS1.
		*/
		//577758
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType={};
				String[] strRoleStatType={statTypeName2};
				strFuncResult=objViewMap.verifyStatTypesInResourcePopup_ShowMap(selenium, strResource1,
						strEventStatType, strRoleStatType, false, true);
				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				try {
					assertFalse(strStatType
							.contains(statTypeName1));
					log4j.info("Role Based Status type " +statTypeName1+ " is not displayed");
				} catch (AssertionError ae) {
					log4j.info("Role Based Status type " +statTypeName1+ " is still displayed");
					strFuncResult = " Role Based Status type "+ statTypeName1+ " is still displayed";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select resource RS2 from the 'Find Resource' drop down.
		  Expected Result:Both ST1 and ST2 are displayed in the resource pop up window of RS2.
		*/
		//577759
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType={};
				String[] strRoleStatType={statTypeName1,statTypeName2};
				strFuncResult=objViewMap.verifyStatTypesInResourcePopup_ShowMap(selenium, strResource2,
						strEventStatType, strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to View>>Map, select resource RS1 from the 'Find Resource' dropdown.
		  Expected Result:Only ST2 is displayed in the resource pop up window of RS1.
		*/
		//577760
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType={};
				String[] strRoleStatType={statTypeName2};
				strFuncResult=objViewMap.verifyStatTypesInResourcePopup(selenium, strResource1,
						strEventStatType, strRoleStatType, false, true);
				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				try {
					assertFalse(strStatType
							.contains(statTypeName1));
					log4j.info("Role Based Status type " +statTypeName1+ " is not displayed");
				} catch (AssertionError ae) {
					log4j.info("Role Based Status type " +statTypeName1+ " is still displayed");
					strFuncResult = " Role Based Status type "+ statTypeName1+ " is still displayed";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select resource RS2 from the 'Find Resource' dropdown.
		  Expected Result:Both ST1 and ST2 are displayed in the resource pop up window of RS2.
		*/
		//577761
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType={};
				String[] strRoleStatType={statTypeName1,statTypeName2};
				strFuncResult=objViewMap.verifyStatTypesInResourcePopup(selenium, strResource2,
						strEventStatType, strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the event banner of E1
		  Expected Result:N/A is displayed in the status type cell of ST1 for resource RS1.
          "--" is displayed in the status type cell of ST1 for resource RS2.
				  "--" is displayed in the status type cell of ST1 and ST2 for resource RS1 and RS2.
		*/
		//577762

			try {
				assertEquals("", strFuncResult);
				//Click on Event name in event banner
				selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName+"']");
				selenium.waitForPageToLoad(gstrTimeOut);
				String strElementID="//table[starts-with(@id,'rtt_"+strRsTypeValues[0]+"')]/tbody/tr[1]/td[3]";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("N/A is displayed in the status type cell of "+statTypeName1+"for resource"+strResource1+"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[starts-with(@id,'rtt_"+strRsTypeValues[0]+"')]/tbody/tr[1]/td[4]";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("-- is displayed in the status type cell of "+statTypeName2+"for resource"+strResource1+"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[starts-with(@id,'rtt_"+strRsTypeValues[0]+"')]/tbody/tr[2]/td[3]";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("-- is displayed in the status type cell of "+statTypeName1+"for resource"+strResource2+"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[starts-with(@id,'rtt_"+strRsTypeValues[0]+"')]/tbody/tr[2]/td[4]";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				log4j.info("-- is displayed in the status type cell of "+statTypeName2+"for resource"+strResource2+"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the name of resource RS1
		  Expected Result:Only ST2 is displayed on the 'View Resource Detail' screen under section S1.
		*/
		//577763
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViews.navToViewResourceDetailPage(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep={statTypeName2,statTypeName1};
				strFuncResult=objViews.verifySTInViewResDetail(selenium, strSection, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the event banner of E1, click on the name of resource RS2
		  Expected Result:Both ST1 and ST2 are displayed on the 'View Resource Detail' screen under section S1.
		*/
		//577764
			try {
				assertEquals("The Status Type "+statTypeName1+" is NOT displayed on " +
								"the view resource detail screen. ", strFuncResult);
				//Click on Event name in event banner
				selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEveName+"']");
				selenium.waitForPageToLoad(gstrTimeOut);
				strFuncResult=objViews.navToViewResourceDetailPage(selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep={statTypeName1,statTypeName2};
				strFuncResult=objViews.verifySTInViewResDetail(selenium, strSection, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strInitPwd;
				strTestData[3] = statTypeName1 + statTypeName2;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 17th step";
				strTestData[6] = strResource1+"/"+strResource2;
				strTestData[7] = strSection;
				strTestData[8] = strEveName;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "BQS-99742";
		gstrTO = "For a user U1, deselect a status type in the 'Refine Visible Status Types' window for a resource and save, verify that the user cannot view the deselected status type from all the view screens for that particular resource.";
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
	
	/********************************************************************************************
	'Description	:Verify that user receives the following when status of a resource expires at
	                the EXPIRATION time: 1. Status update prompt. 2. Expired status notifications
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-Sep-2012
	'Author			:QSG
	'--------------------------------------------------------------------------------------------
	'Modified Date                                                               Modified By
	'<Date>		                                                                 <Name>
	*********************************************************************************************/	
	@Test
	public void testSmoke121331() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		ViewMap objMap = new ViewMap();
		try {
			gstrTCID = "121331";
			gstrTO = "Verify that user receives the following when status of a resource expires at the " +
					"EXPIRATION time:"
					+ " 1. Status update prompt."
					+ " 2. Expired status notifications";//TO
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strLastUpdMulValue = "";
			String strLastUpdNumValue = "";
			String strLastUpdSatuValue = "";
			String strLastUpdTxtValue = "";
			String strLastUpdNedocValue = "";
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strNSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 2, 2,
					strFILE_PATH);
			String strMSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 5, 2,
					strFILE_PATH);
			String strTSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 3, 2,
					strFILE_PATH);
			String strSSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 4, 2,
					strFILE_PATH);
			String strNedocUpdTimeBef = rdExcel.readInfoExcel("Timer", 6, 2,
					strFILE_PATH);
			
			String strNSTUpdTimeAft = rdExcel.readInfoExcel("Timer", 2, 4,
					strFILE_PATH);
			String strTSTUpdTimeAft = rdExcel.readInfoExcel("Timer", 3, 4,
					strFILE_PATH);
			String strMSTUpdTimeAft = rdExcel.readInfoExcel("Timer", 2, 4,
					strFILE_PATH);
			String strSSTUpdTimeAft = rdExcel.readInfoExcel("Timer", 3, 4,
					strFILE_PATH);		
			String strNedocUpdTimeAft = rdExcel.readInfoExcel("Timer", 3, 4,
					strFILE_PATH);

			String strCurDate = "09/24/2012";
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strNumUpdateValue1 = "2";
			String strNumUpdateValue2 = "1";

			String strTxtUpdateValue1 = "tr";
			String strTxtUpdateValue2 = "ff";

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7","8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8","9" };
			
			String strNedocUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6"};
	        String strNedocUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7" };

			String strScUpdValCheck1 = "393";
			String strScUpdValCheck2 = "429";
			
			String strNedocUpdValCheck1 = "135 - Overcrowded";
			String strNedocUpdValCheck2 = "241 - Disaster";

			String strComment1 = "st1";
			String strComment2 = "st2";
			String strComment3 = "st3";
			String strComment4 = "st4";
			String strComment5 = "st5";

			String strNSTValue = "Number";
			String strNumStatType = "AutoNSt_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strMSTValue = "Multi";
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strTSTValue = "Text";
			String strTxtStatType = "AutoTSt_" + strTimeText;

			String strSSTValue = "Saturation Score";
			String strSatuStatType = "AutoScSt_" + strTimeText;
			
			String strNEDOCValue = "NEDOCS Calculation";
			String statrNEDOCStatTypeName = "NEDOC" + strTimeText;

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strExpHr = "00";
			String strExpMn = "05";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status types as rows. Status, comments " +
					"and timestamps as columns.)";

			String strSTvalue[] = new String[5];
			String strRSValue[] = new String[1];
			String strResVal = "";
			strSTvalue[0] = "";
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
						
			String strSubjName = "EMResource Expired Status Notification: "
					+ strResource;

			String strMsgBody2 = "";
			String strMsgBody1 = "";

			String strMsgBody4 = "";
			String strMsgBody3 = "";

			String strMsgBody6 = "";
			String strMsgBody5 = "";

			String strMsgBody8 = "";
			String strMsgBody7 = "";
			
			String strMsgBody10 = "";
			String strMsgBody9 = "";

			int[] intEMailRes = new int[6];
			int[] intPagerRes = new int[5];
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String[] strRoleStatType = { strNumStatType, strMultiStatType,
					strTxtStatType, strSatuStatType,statrNEDOCStatTypeName };
			String strStatusValue[] = new String[1];		
		/*
		* STEP :
		  Action:Preconditions:
		   1.Test user has created following status types providing expiration time as 5 minutes:					
			 a. Number status type 'NST', associated with a timer say (Count down to expiration, then stop counting)				
			 b. Text status type 'TST', associated with a timer say (count up to expiration, then start counting up again form zero)					
			 c. Saturation score status type 'SST', associated with a timer say (count up to expiration, then stop counting)					
             d. Multi status type 'MST' is created and a status S1 under MST is created associating with a timer say
              (count up (regardless of expiration)) and expiration time of 5 minutes.		
             e. NEDOC Calculation status type 'NSST' associated with a timer say (count down to expiration, then count up) 			
		   3. Resource type RT is created selecting all the above status types.					
		   4. Resource RS is created under RT providing address.					
		   5. View 'V1' of type 'Resource' is created selecting all the above status types and 'RS'.					
		   6. Role ROL has view and update rights on all the above status types.					
		   7. For a test user U1:					
					a. Email and pager addresses are given
					b. is assigned a role 'ROL'
					c. has been given the 'Update status' right on resource RS
					d. has opted to receive expired status notification via e-mail and pager.
					e. has 'Must update overdue status' right
		 Expected Result:No Expected Result
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
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.TimerType"),
						"label=Count down to expiration, then stop counting");
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.savAndVerifySTNew(
							seleniumPrecondition, strNumStatType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strNumStatType);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strSTvalue[0] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strMSTValue,
							strMultiStatType, strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.savAndVerifyMultiST(
							seleniumPrecondition, strMultiStatType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(
							seleniumPrecondition, strMultiStatType);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strSTvalue[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
							seleniumPrecondition, strMultiStatType,
							strStatusName1, strMSTValue, strStatTypeColor,
							strExpHr, strExpMn, "", "", false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					seleniumPrecondition.select(
							propElementDetails
									.getProperty("StatusType.CreateStatType.TimerType"),
							"label=Count down to expiration, then count up");
					strFuncResult = objST.saveAndVerifyStatus(
							seleniumPrecondition, strMultiStatType,
							strStatusName1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchStatValInStatusList(
							seleniumPrecondition, strMultiStatType,
							strStatusName1);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strStatusValue[0] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST
							.navStatusTypList(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strTSTValue, strTxtStatType,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					seleniumPrecondition.click(propElementDetails
							.getProperty("StatusType.CreateStatType.Expire"));
					seleniumPrecondition.select(
							propElementDetails
									.getProperty("StatusType.CreateStatType.Expire.Hours"),
							"label=" + strExpHr);
					seleniumPrecondition.select(
							propElementDetails
									.getProperty("StatusType.CreateStatType.Expire.Mins"),
							"label=" + strExpMn);

					seleniumPrecondition.select(
							propElementDetails
									.getProperty("StatusType.CreateStatType.TimerType"),
							"label=Count up to expiration, then stop counting");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.savAndVerifySTNew(
								seleniumPrecondition, strTxtStatType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, strTxtStatType);
						if (strStatValue.compareTo("") != 0) {
							strFuncResult = "";
							strSTvalue[2] = strStatValue;
						} else {
							strFuncResult = "Failed to fetch status type value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.selectStatusTypesAndFilMandFlds(
								seleniumPrecondition, strSSTValue,
								strSatuStatType, strStatTypDefn, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						seleniumPrecondition.click(propElementDetails
								.getProperty("StatusType.CreateStatType.Expire"));
						seleniumPrecondition.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.Expire.Hours"),
								"label=" + strExpHr);
						seleniumPrecondition.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.Expire.Mins"),
								"label=" + strExpMn);

						seleniumPrecondition.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.TimerType"),
								"label=Count up to expiration, then start counting up again from zero");
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objST.savAndVerifySTNew(
									seleniumPrecondition, strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strStatValue = objST.fetchSTValueInStatTypeList(
									seleniumPrecondition, strSatuStatType);
							if (strStatValue.compareTo("") != 0) {
								strFuncResult = "";
								strSTvalue[3] = strStatValue;
							} else {
								strFuncResult = "Failed to fetch status type value";
							}
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objST.selectStatusTypesAndFilMandFlds(
									seleniumPrecondition, strNEDOCValue,
									statrNEDOCStatTypeName, strStatTypDefn, false);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							seleniumPrecondition
									.click(propElementDetails
											.getProperty("StatusType.CreateStatType.Expire"));
							seleniumPrecondition
									.select(propElementDetails
											.getProperty("StatusType.CreateStatType.Expire.Hours"),
											"label=" + strExpHr);
							seleniumPrecondition
									.select(propElementDetails
											.getProperty("StatusType.CreateStatType.Expire.Mins"),
											"label=" + strExpMn);

							seleniumPrecondition
									.select(propElementDetails
											.getProperty("StatusType.CreateStatType.TimerType"),
											"label=Count down to expiration, then count up");
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objST.savAndVerifySTNew(
										seleniumPrecondition, statrNEDOCStatTypeName);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strStatValue = objST
										.fetchSTValueInStatTypeList(
												seleniumPrecondition,
												statrNEDOCStatTypeName);
								if (strStatValue.compareTo("") != 0) {
									strFuncResult = "";
									strSTvalue[4] = strStatValue;
								} else {
									strFuncResult = "Failed to fetch status type value";
								}
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRT
									.navResourceTypList(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRT.resrcTypeMandatoryFlds(
									seleniumPrecondition, strResrctTypName,
									"css=input[name='statusTypeID'][value='"
											+ strSTvalue[0] + "']");
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						for (int intST = 0; intST < strSTvalue.length; intST++) {
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objRT.selAndDeselSTInEditRT(
										seleniumPrecondition,
										strSTvalue[intST], true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRT.saveAndvrfyResType(
									seleniumPrecondition, strResrctTypName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRs
									.navResourcesList(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							String strState = "Alabama";
							String strCountry = "Barbour County";
							strFuncResult = objRs.createResourceWitLookUPadres(
									seleniumPrecondition, strResource,
									strAbbrv, strResrctTypName, strContFName,
									strContLName, strState, strCountry,
									strStandResType);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strResVal = objRs.fetchResValueInResList(
									seleniumPrecondition, strResource);
							if (strResVal.compareTo("") != 0) {
								strFuncResult = "";
								strRSValue[0] = strResVal;
							} else {
								strFuncResult = "Failed to fetch Resource value";
							}
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRole
									.navRolesListPge(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRole.CreateRoleWithAllFields(
									seleniumPrecondition, strRoleName,
									strRoleRights, strSTvalue, true,
									strSTvalue, true, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strRoleValue = objRole.fetchRoleValueInRoleList(
									seleniumPrecondition, strRoleName);
							if (strRoleValue.compareTo("") != 0) {
								strFuncResult = "";

							} else {
								strFuncResult = "Failed to fetch Role value";
							}
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
							strFuncResult = objCreateUsers
									.fillUsrMandatoryFlds(seleniumPrecondition,
											strUserName, strInitPwd,
											strConfirmPwd, strUsrFulName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.slectAndDeselectRole(seleniumPrecondition,
											strRoleValue, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.selectResourceRights(seleniumPrecondition,
											strResource, false, true, false,
											true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.advancedOptns(
											seleniumPrecondition,
											propElementDetails
													.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
											true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.nonMandatoryUsrProfileFlds(seleniumPrecondition, "",
											"", "", "", "", "", strEMail,
											strEMail, "");
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
							strFuncResult = objCreateUsers.navEditUserPge(
									seleniumPrecondition, strUserName, strByRole,
									strByResourceType, strByUserInfo, strNameFormat);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
									seleniumPrecondition, strUserName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.fillRecvExpStatusNotifinEditUsr(
									seleniumPrecondition, true, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}


						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.navRegionViewsList(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.createView(
									seleniumPrecondition, strViewName,
									strVewDescription, strViewType, true,
									false, strSTvalue, false, strRSValue);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objLogin
									.logout(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

				/*
				 * Login as Test user
				 * Expected Result:'Update Status' prompt is displayed with:
	                               NST, MST, TST, SST and NSST expanded. 
				 */
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objLogin.newUsrLogin(selenium,
									strUserName, strInitPwd);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strNumStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strMultiStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strTxtStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, statrNEDOCStatTypeName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
			/*
			 * Update the statuses of all the status types with a status value and providing comments.
			 * Save the page and navigate to view 'V1' screen.  
			 * Expected Result:Updated status is displayed appropriately on view 'V1' screen. 
			 *                 Comments and last updated date and time are displayed appropriately on 
			 *                 the view screen under respective columns.
			 * Updated user is displayed on the tool tip when hovered over the status value cell.
			 * Timer type is displayed appropriately for all status types. 
			 */
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusNSTWithComments(selenium,
											strNumUpdateValue1, strSTvalue[0],
											strComment1);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusMSTWithComments(selenium,
											strStatusValue[0], strSTvalue[1],
											strComment2);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusNSTWithComments(selenium,
											strTxtUpdateValue1, strSTvalue[2],
											strComment3);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusSSTWithComments(selenium,
											strScUpdValue1, strSTvalue[3],
											strComment4);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objViews
										.fillAndSaveUpdateNEDOCSTWithComments(
												selenium, strNedocUpdValue1,
												strSTvalue[4], strComment5,
												false);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.saveAndNavToViewScreen(
									selenium, "Region Default");
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						String strSnapTime = selenium
								.getText("css=#statusTime");
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.navToUserView(selenium,
									strViewName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						strNSTUpdTimeBef = strSnapTime + strNSTUpdTimeBef;
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.verifyUpdSTCommentsAndLastUpdTimeInViews(
											selenium, strResource,
											strNumStatType, strNumUpdateValue1,
											strComment1, strNSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.verifyUpdTimeInViewToolTipWithComments(
											selenium, strResource,
											strNumStatType, strNSTUpdTimeBef,
											strUsrFulName, strComment1);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strMSTUpdTimeBef = strSnapTime + strMSTUpdTimeBef;
							strFuncResult = objViews
									.verifyUpdSTCommentsAndLastUpdTimeInViews(
											selenium, strResource,
											strMultiStatType, strStatusName1,
											strComment2, strMSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.verifyUpdTimeInViewToolTipWithComments(
											selenium, strResource,
											strMultiStatType, strMSTUpdTimeBef,
											strUsrFulName, strComment2);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strTSTUpdTimeBef = strSnapTime + strTSTUpdTimeBef;
							strFuncResult = objViews
									.verifyUpdSTCommentsAndLastUpdTimeInViews(
											selenium, strResource,
											strTxtStatType, strTxtUpdateValue1,
											strComment3, strTSTUpdTimeBef);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.verifyUpdTimeInViewToolTipWithComments(
											selenium, strResource,
											strTxtStatType, strTSTUpdTimeBef,
											strUsrFulName, strComment3);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strSSTUpdTimeBef = strSnapTime + strSSTUpdTimeBef;
							strFuncResult = objViews
									.verifyUpdSTCommentsAndLastUpdTimeInViews(
											selenium, strResource,
											strSatuStatType, strScUpdValCheck1,
											strComment4, strSSTUpdTimeBef);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.verifyUpdTimeInViewToolTipWithComments(
											selenium, strResource,
											strSatuStatType, strSSTUpdTimeBef,
											strUsrFulName, strComment4);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strNedocUpdTimeBef = strSnapTime
									+ strNedocUpdTimeBef;
							strFuncResult = objViews
									.verifyUpdSTCommentsAndLastUpdTimeInViews(
											selenium, strResource,
											statrNEDOCStatTypeName,
											strNedocUpdValCheck1,
											strComment5, strNedocUpdTimeBef);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.verifyUpdTimeInViewToolTipWithComments(
											selenium, strResource,
											statrNEDOCStatTypeName,
											strNedocUpdTimeBef,
											strUsrFulName, strComment5);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							String[] strEventStatType = {};
							strFuncResult = objMap
									.verifyStatTypesInResourcePopup(selenium,
											strResource, strEventStatType,
											strRoleStatType, false, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}
						
						strNSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 2, 2,
								strFILE_PATH);
						strMSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 5, 2,
								strFILE_PATH);
						strTSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 3, 2,
								strFILE_PATH);
						strSSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 4, 2,
								strFILE_PATH);						
						strNedocUpdTimeBef = rdExcel.readInfoExcel("Timer", 6, 2,
								strFILE_PATH);
						try {
							assertEquals("", strFuncResult);
							strNSTUpdTimeBef = strNSTUpdTimeBef
									.replace(" ", "");
							strNSTUpdTimeBef = "(" + strSnapTime
									+ strNSTUpdTimeBef + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strNumUpdateValue1, strComment1, 3,
											strNSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}

						try {
							assertEquals("", strFuncResult);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strNSTUpdTimeAft = strNSTUpdTimeAft
									.replace(" ", "");
							strNSTUpdTimeAft = "(" + strSnapTime
									+ strNSTUpdTimeAft + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strNumUpdateValue1, strComment1, 3,
											strNSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);

							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}

						try {
							assertEquals("", strFuncResult);
							strMSTUpdTimeBef = strMSTUpdTimeBef
									.replace(" ", "");
							strMSTUpdTimeBef = "(" + strSnapTime
									+ strMSTUpdTimeBef + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strStatusName1, strComment2, 1,
											strMSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}

						try {
							assertEquals("", strFuncResult);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strMSTUpdTimeAft = strMSTUpdTimeAft
									.replace(" ", "");
							strMSTUpdTimeAft = "(" + strSnapTime
									+ strMSTUpdTimeAft + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strStatusName1, strComment2, 1,
											strMSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
						try {
							assertEquals("", strFuncResult);
							strTSTUpdTimeBef = strTSTUpdTimeBef
									.replace(" ", "");
							strTSTUpdTimeBef = "(" + strSnapTime
									+ strTSTUpdTimeBef + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strTxtUpdateValue1, strComment3, 7,
											strTSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strTSTUpdTimeAft = strTSTUpdTimeAft
									.replace(" ", "");
							strTSTUpdTimeAft = "(" + strSnapTime
									+ strTSTUpdTimeAft + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strTxtUpdateValue1, strComment3, 7,
											strTSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
						try {
							assertEquals("", strFuncResult);
							strSSTUpdTimeBef = strSSTUpdTimeBef
									.replace(" ", "");
							strSSTUpdTimeBef = "(" + strSnapTime
									+ strSSTUpdTimeBef + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strScUpdValCheck1, strComment4, 5,
											strSSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}

						try {
							assertEquals("", strFuncResult);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strSSTUpdTimeAft = strSSTUpdTimeAft
									.replace(" ", "");
							strSSTUpdTimeAft = "(" + strSnapTime
									+ strSSTUpdTimeAft + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strScUpdValCheck1, strComment4, 5,
											strSSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
						
						try {
							assertEquals("", strFuncResult);
							strNedocUpdTimeBef = strNedocUpdTimeBef
									.replace(" ", "");
							strNedocUpdTimeBef = "(" + strSnapTime
									+ strNedocUpdTimeBef + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strNedocUpdValCheck1, strComment5, 9,
											strNedocUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}

						try {
							assertEquals("", strFuncResult);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strNedocUpdTimeAft = strNedocUpdTimeAft
									.replace(" ", "");
							strNedocUpdTimeAft = "(" + strSnapTime
									+ strNedocUpdTimeAft + ")";
							strFuncResult = objMap
									.verifyUpdValCommentsAndLastUpdTimeInResPopup(
											selenium, strResource,
											strNedocUpdValCheck1, strComment5, 9,
											strNedocUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}

						try {
							assertEquals("", strFuncResult);
							gstrReason = "";
							String[] strArFunRes = new String[5];
							strArFunRes = objMap.getUpdTimeValue(selenium, "2");
							strFuncResult = strArFunRes[4];
							strLastUpdNumValue = strArFunRes[2] + ":"
									+ strArFunRes[3];
							String strAdUpdTime = dts.addTimetoExisting(
									strLastUpdNumValue, 5, "HH:mm");
							strLastUpdNumValue = strAdUpdTime;
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							String[] strArFunRes = new String[5];
							strArFunRes = objMap.getUpdTimeValue(selenium, "1");

							strFuncResult = strArFunRes[4];
							strLastUpdMulValue = strArFunRes[2] + ":"
									+ strArFunRes[3];

							String strAdUpdTime = dts.addTimetoExisting(
									strLastUpdMulValue, 5, "HH:mm");
							strLastUpdMulValue = strAdUpdTime;
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							String[] strArFunRes = new String[5];
							strArFunRes = objMap.getUpdTimeValue(selenium, "4");

							strFuncResult = strArFunRes[4];
							strLastUpdTxtValue = strArFunRes[2] + ":"
									+ strArFunRes[3];
							String strAdUpdTime = dts.addTimetoExisting(
									strLastUpdTxtValue, 5, "HH:mm");
							strLastUpdTxtValue = strAdUpdTime;
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						String strCurDateMnth = "";
						try {
							assertEquals("", strFuncResult);
							String[] strArFunRes = new String[5];
							strArFunRes = objMap.getUpdTimeValue(selenium, "3");

							strFuncResult = strArFunRes[4];
							strLastUpdSatuValue = strArFunRes[2] + ":"
									+ strArFunRes[3];

							String strAdUpdTime = dts.addTimetoExisting(
									strLastUpdSatuValue, 5, "HH:mm");
							strLastUpdSatuValue = strAdUpdTime;

							String strCurYear = dts.getCurrentDate("yyyy");
							strCurDateMnth = strArFunRes[0] + strArFunRes[1]
									+ strCurYear;

							strCurDate = dts.converDateFormat(strCurDateMnth,
									"ddMMMyyyy", "MM/dd/yyyy");
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							Thread.sleep(480000);
							strMsgBody1 = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strNumStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strLastUpdNumValue
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "
									+ propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBody2 = "EMResource expired status: "
									+ strResource + ". " + strNumStatType
									+ " status expired " + strCurDate + " "
									+ strLastUpdNumValue + ".";

							strMsgBody3 = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strMultiStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strLastUpdMulValue
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "
									+ propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBody4 = "EMResource expired status: "
									+ strResource + ". " + strMultiStatType
									+ " status expired " + strCurDate + " "
									+ strLastUpdMulValue + ".";

							strMsgBody5 = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strTxtStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strLastUpdTxtValue
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "
									+ propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBody6 = "EMResource expired status: "
									+ strResource + ". " + strTxtStatType
									+ " status expired " + strCurDate + " "
									+ strLastUpdTxtValue + ".";

							strMsgBody7 = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strSatuStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strLastUpdSatuValue
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "
									+ propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBody8 = "EMResource expired status: "
									+ strResource + ". " + statrNEDOCStatTypeName
									+ " status expired " + strCurDate + " "
									+ strLastUpdNedocValue + ".";
					
							strMsgBody10 = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ statrNEDOCStatTypeName
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strLastUpdNedocValue
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "
									+ propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBody9 = "EMResource expired status: "
									+ strResource + ". " + strSatuStatType
									+ " status expired " + strCurDate + " "
									+ strLastUpdSatuValue + ".";

							String[] strArPagerBody = { strMsgBody2,
									strMsgBody4, strMsgBody6, strMsgBody8,strMsgBody10 };
							String[] strArMailBody = { strMsgBody1,
									strMsgBody3, strMsgBody5, strMsgBody7 ,strMsgBody9};
							
							selenium.selectWindow("");
							strFuncResult = objMail
									.loginAndnavToInboxInWebMail(selenium,
											strLoginName, strPassword);
							try {
								assertTrue(strFuncResult.equals(""));

								for (int intCnt = 0; intCnt < 4; intCnt++) {
									if (intCnt != 0) {
										selenium.selectFrame("relative=up");
										selenium.selectFrame("horde_main");
										// click on Back to Inbox
										selenium.click("link=Back to Inbox");
										selenium.waitForPageToLoad("90000");
									}
									strSubjName = "EMResource Expired Status: "
											+ strAbbrv;
									strFuncResult = objMail.verifyEmail(
											selenium, strFrom, strTo,
											strSubjName);

									try {
										assertTrue(strFuncResult.equals(""));
										for (int intMail = 0; intMail < strArPagerBody.length; intMail++) {
											String strMsg = selenium
													.getText("css=div.fixed.leftAlign");
											if (strMsg
													.compareTo(strArPagerBody[intMail]) == 0) {
												intPagerRes[intMail]++;
												log4j.info(strArPagerBody[intMail]
														+ " is displayed for Pager Notification");
												break;
											}
										}
									} catch (AssertionError Ae) {
										gstrReason = gstrReason + " "
												+ strFuncResult;
									}

									selenium.selectFrame("relative=up");
									selenium.selectFrame("horde_main");
									// click on Back to Inbox
									selenium.click("link=Back to Inbox");
									selenium.waitForPageToLoad("90000");

									strSubjName = "EMResource Expired Status Notification: "
											+ strResource;
									strFuncResult = objMail.verifyEmail(
											selenium, strFrom, strTo,
											strSubjName);

									try {
										assertTrue(strFuncResult.equals(""));
										for (int intMail = 0; intMail < strArMailBody.length; intMail++) {
											String strMsg = selenium
													.getText("css=div.fixed.leftAlign");
											if (strMsg
													.compareTo(strArMailBody[intMail]) == 0) {
												intEMailRes[intMail]++;
												log4j.info(strArMailBody[intMail]
														+ " is displayed for Email Notification");
												break;
											}
										}
									} catch (AssertionError Ae) {
										gstrReason = gstrReason + " "
												+ strFuncResult;
									}
								}

								for (int intCnt = 0; intCnt < 4; intCnt++) {
									if (intEMailRes[intCnt] == 0) {
										log4j.info("Email notifications for "
												+ strRoleStatType[intCnt]
												+ " is NOT displayed");
										gstrReason = gstrReason
												+ " Email notifications for "
												+ strRoleStatType[intCnt]
												+ " is NOT displayed";
									}
									if (intPagerRes[intCnt] == 0) {
										log4j.info("Pager notifications for "
												+ strRoleStatType[intCnt]
												+ " is NOT displayed");
										gstrReason = gstrReason
												+ " Pager notifications for "
												+ strRoleStatType[intCnt]
												+ " is NOT displayed";
									}
									log4j.info(intEMailRes[intCnt] + " "
											+ intPagerRes[intCnt]);
								}

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								selenium.selectWindow("");
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews
											.fillUpdStatusNSTWithComments(
													selenium,
													strNumUpdateValue2,
													strSTvalue[0], strComment1);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}

								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews
											.fillUpdStatusMSTWithComments(
													selenium,
													strStatusValue[0],
													strSTvalue[1], strComment2);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews
											.fillUpdStatusNSTWithComments(
													selenium,
													strTxtUpdateValue2,
													strSTvalue[2], strComment3);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews
											.fillUpdStatusSSTWithComments(
													selenium, strScUpdValue2,
													strSTvalue[3], strComment4);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
									try {
										assertEquals("", strFuncResult);
										strFuncResult = objViews
												.fillAndSaveUpdateNEDOCSTWithComments(
														selenium,
														strNedocUpdValue2,
														strSTvalue[4],
														strComment5, false);
									} catch (AssertionError Ae) {
										gstrReason = strFuncResult;
									}

								try {
									assertEquals("", strFuncResult);
									String[] strArUpdVal = {
											strNumUpdateValue2, strStatusName1,
											strTxtUpdateValue2,
											strScUpdValCheck2,strNedocUpdValCheck2 };
									strFuncResult = objMap.saveAndVerifyUpdtST(
											selenium, strResource, strArUpdVal);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								
								try {
									assertEquals("", strFuncResult);
									gstrResult = "PASS";

								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}

							} catch (AssertionError Ae) {

							}
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "121331";
			gstrTO = "Verify that user receives the following when status of a resource expires at the" +
					" EXPIRATION time: " +
					"1. Status update prompt." +
					" 2. Expired status notifications";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
	
	/***********************************************************************
	'Description	:Verify that user receives the following when the status of a resource expires at the SHIFT time: 1. Status update prompt. 2. Expired status notification
	'Precondition	:Preconditions:
				1. Test user has created following status types providing shift time:
				
				a. Number status type 'NST', associated with a timer say (Count down to expiration, then stop counting)
				
				b. Text status type 'TST', associated with a timer say (count up to expiration, then start counting up again form zero)
				
				c. Saturation score status type 'SST', associated with a timer say (count up to expiration, then stop counting)
				
				d. Multi status type 'MST' is created and a status S1 under MST is created associating with a timer say (count up (regardless of expiration)).
				
				3. Resource type RT is created selecting all the above status types.
				
				4. Resource RS is created under RT providing address.
				
				5. View 'V1' of type 'Resource' is created selecting all the above status types and 'RS'.
				
				6. Role ROL has view and update rights on all the above status types.
				
				7. For a test user U1:
				
				a. Email and pager addresses are given
				b. is assigned a role 'ROL'
				c. has been given the 'Update status' right on resource RS
				d. has opted to receive expired status notification via e-mail and pager.
				e. has 'Must update overdue status' right 
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>		                               <Name>
	************************************************************************/

/*	
	@Test
	public void test85149() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		General objMail=new General();
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT=new ResourceTypes();
		Resources objRs=new Resources();
		CreateUsers objCreateUsers=new CreateUsers();
		Views objViews=new Views();
		Roles objRole=new Roles();
		ViewMap objMap=new ViewMap();
			
		try {
			gstrTCID = "85149";
			gstrTO = "Verify that user receives the following when the status of a resource expires at the SHIFT time: 1. Status update prompt. 2. Expired status notification";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

					
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
		

			String strLastUpdMulValue="";
			String strLastUpdNumValue="";
			String strLastUpdSatuValue="";
			String strLastUpdTxtValue="";
					
			//Web mail user details
			String strLoginName = rdExcel.ReadData("WebMailUser", 2, 1);
			String strPassword =rdExcel.ReadData("WebMailUser", 2, 2);
			
		
			// login details
			String strLoginUserName = rdExcel.ReadData("Login", 3, 1);
			String strLoginPassword = rdExcel.ReadData("Login", 3, 2);
			String strRegn = rdExcel.ReadData("Login", 3, 4);
			String strEMail=rdExcel.ReadData("WebMailUser", 2, 1);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strNSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 2,
					2, strFILE_PATH);
			String strMSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 5,
					2, strFILE_PATH);
			String strTSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 3,
					2, strFILE_PATH);
			String strSSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 4,
					2, strFILE_PATH);
			
			String strNSTUpdTimeAft=rdExcel.readInfoExcel("Timer", 2,
					4, strFILE_PATH);
			String strTSTUpdTimeAft=rdExcel.readInfoExcel("Timer", 3,
					4, strFILE_PATH);
			
			String strMSTUpdTimeAft=rdExcel.readInfoExcel("Timer", 2,
					4, strFILE_PATH);
			String strSSTUpdTimeAft=rdExcel.readInfoExcel("Timer", 3,
					4, strFILE_PATH);
			
			String strCurDate="09/24/2012";
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strNumUpdateValue1="2";
			String strNumUpdateValue2="1";
			
			String strTxtUpdateValue1="tr";
			String strTxtUpdateValue2="ff";
			
			String strScUpdValue1[]={"0","1","2","3","4","5","6","7","8"};
			String strScUpdValue2[]={"1","2","3","4","5","6","7","8","9"};
			
			String strScUpdValCheck1="393";
			String strScUpdValCheck2="429";
			
			String strComment1="st1";
			String strComment2="st2";
			String strComment3="st3";
			String strComment4="st4";
			
			String strNSTValue="Number";
			String strNumStatType="AutoNSt_"+strTimeText;
			String strStatTypDefn="Auto";
							
			String strMSTValue="Multi";
			String strMultiStatType="AutoMSt_"+strTimeText;
			
			String strTSTValue="Text";
			String strTxtStatType="AutoTSt_"+strTimeText;
						
			String strSSTValue="Saturation Score";
			String strSatuStatType="AutoScSt_"+strTimeText;
			
			String strStatusName1="Sta"+strTimeText;
					
			String strStatTypeColor="Black";
			
			String strResrctTypName="AutoRt_"+strTimeText;
			String strStatValue="";
					
			String strResource="AutoRs_"+strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
					
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=rdExcel.ReadData("Login", 4, 2);
			String strConfirmPwd=rdExcel.ReadData("Login", 4, 2);
			String strUsrFulName="autouser";
			
			String strRoleValue="";
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";
			
			String strSTvalue[]=new String[4];
			String strRSValue[]=new String[1];
			
			String strResVal="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			strSTvalue[0]="";
			String strSubjName="EMResource Expired Status Notification: "+strResource;
		
			String strMsgBody2="";
			String strMsgBody1="";
				
			String strMsgBody4="";
			String strMsgBody3="";
			
			String strMsgBody6="";
			String strMsgBody5="";
			
			String strMsgBody8="";
			String strMsgBody7="";
			
			int []intEMailRes=new int[4];
			int[] intPagerRes=new int[4];
			String strFrom="notification@emsystems.com";
			String strTo=strLoginName;
		
			String[] strRoleStatType = { strNumStatType,strMultiStatType,strTxtStatType,strSatuStatType };		
			String strStatusValue[]=new String[1];
			String StatusTime="";
			String strUpdTime="";
			int intShiftTime=20;
			
			String strNSTAdUpdTime="";
			String strMSTAdUpdTime="";
			String strTSTAdUpdTime="";
			String strSSTAdUpdTime="";
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
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strNumStatType, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				StatusTime=selenium.getText("css=#statusTime");
				
				String strStatusTime[]=StatusTime.split(" ");
				
				strUpdTime = strStatusTime[2];
				strNSTAdUpdTime = dts.addTimetoExisting(strUpdTime, intShiftTime,
						"HH:mm");
				strUpdTime = strNSTAdUpdTime;
				log4j.info(strUpdTime);

				strStatusTime = strUpdTime.split(":");

				// Select shift time
				selenium.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				selenium
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				selenium
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);
				selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count down to expiration, then stop counting");
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.savAndVerifySTNew(selenium, strNumStatType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
					
				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(selenium, strNumStatType);
					if(strStatValue.compareTo("")!=0){
						strFuncResult="";
						strSTvalue[0]=strStatValue;
					}else{
						strFuncResult="Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST
							.selectStatusTypesAndFilMandFlds(selenium,
									strMSTValue, strMultiStatType,
									strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					
					StatusTime=selenium.getText("css=#statusTime");
					
					strStatusTime=StatusTime.split(" ");
					
					strUpdTime = strStatusTime[2];
					strMSTAdUpdTime = dts.addTimetoExisting(strUpdTime, intShiftTime,
							"HH:mm");
					strUpdTime = strMSTAdUpdTime;
					log4j.info(strUpdTime);

					strStatusTime = strUpdTime.split(":");

					// Select shift time
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ShiftTime1"));
					selenium
							.select(
									propElementDetails
											.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
									strStatusTime[0]);
					selenium
							.select(
									propElementDetails
											.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
									strStatusTime[1]);
					
					strFuncResult = objST.savAndVerifyMultiST(selenium, strMultiStatType);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchSTValueInStatTypeList(
							selenium, strMultiStatType);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strSTvalue[1] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.createSTWithinMultiTypeST(
							selenium, strMultiStatType, strStatusName1,
							strMSTValue, strStatTypeColor,  false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
							
				
				try {
					assertEquals("", strFuncResult);
					selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count down to expiration, then count up");
					strFuncResult = objST.saveAndVerifyStatus(selenium, strMultiStatType, strStatusName1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatValue = objST.fetchStatValInStatusList(selenium,
							strMultiStatType, strStatusName1);
					if (strStatValue.compareTo("") != 0) {
						strFuncResult = "";
						strStatusValue[0] = strStatValue;
					} else {
						strFuncResult = "Failed to fetch status value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.navStatusTypList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strTxtStatType, strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					
					StatusTime=selenium.getText("css=#statusTime");
					
					strStatusTime=StatusTime.split(" ");
					
					strUpdTime = strStatusTime[2];
					strTSTAdUpdTime = dts.addTimetoExisting(strUpdTime, intShiftTime,
							"HH:mm");
					strUpdTime = strMSTAdUpdTime;
					log4j.info(strUpdTime);

					strStatusTime = strUpdTime.split(":");

					// Select shift time
					selenium.click(propElementDetails
							.getProperty("StatusType.CreateStatType.ShiftTime1"));
					selenium
							.select(
									propElementDetails
											.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
									strStatusTime[0]);
					selenium
							.select(
									propElementDetails
											.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
									strStatusTime[1]);
					
					selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count up to expiration, then stop counting");
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.savAndVerifySTNew(selenium, strTxtStatType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(selenium, strTxtStatType);
						if(strStatValue.compareTo("")!=0){
							strFuncResult="";
							strSTvalue[2]=strStatValue;
						}else{
							strFuncResult="Failed to fetch status type value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strSatuStatType, strStatTypDefn, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						
						StatusTime=selenium.getText("css=#statusTime");
						
						strStatusTime=StatusTime.split(" ");
						
						strUpdTime = strStatusTime[2];
						strMSTAdUpdTime = dts.addTimetoExisting(strUpdTime, intShiftTime,
								"HH:mm");
						strUpdTime = strMSTAdUpdTime;
						log4j.info(strUpdTime);

						strStatusTime = strUpdTime.split(":");

						// Select shift time
						selenium.click(propElementDetails
								.getProperty("StatusType.CreateStatType.ShiftTime1"));
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
										strStatusTime[0]);
						selenium
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
										strStatusTime[1]);
						
						selenium.select(propElementDetails.getProperty("StatusType.CreateStatType.TimerType"), "label=Count up to expiration, then start counting up again from zero");
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objST.savAndVerifySTNew(selenium, strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strStatValue = objST.fetchSTValueInStatTypeList(selenium, strSatuStatType);
							if(strStatValue.compareTo("")!=0){
								strFuncResult="";
								strSTvalue[3]=strStatValue;
							}else{
								strFuncResult="Failed to fetch status type value";
							}
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRT.navResourceTypList(selenium);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRT.resrcTypeMandatoryFlds(selenium, strResrctTypName, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						for(int intST=0;intST<strSTvalue.length;intST++){
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objRT.selAndDeselSTInEditRT(selenium, strSTvalue[intST], true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRT.saveAndvrfyResType(selenium, strResrctTypName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRs.navResourcesList(selenium);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							String strState = "Alabama";
							String strCountry = "Barbour County";
							strFuncResult = objRs.createResourceWitLookUPadres(selenium, strResource, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
								

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}		
						
											
						try {
							assertEquals("", strFuncResult);
							strResVal = objRs.fetchResValueInResList(selenium, strResource);
							
							if(strResVal.compareTo("")!=0){
								strFuncResult="";
								strRSValue[0]=strResVal;
							}else{
								strFuncResult="Failed to fetch Resource value";
							}
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRole.navRolesListPge(selenium);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
												
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRole.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strRoleValue = objRole.fetchRoleValueInRoleList(selenium, strRoleName);
							
							if(strRoleValue.compareTo("")!=0){
								strFuncResult="";
								
							}else{
								strFuncResult="Failed to fetch Role value";
							}
							
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
							strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.selectResourceRights(selenium, strResource, false, true, false, true);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MustOverDue"), true);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(selenium, "", "", "", "", "", "", strEMail, strEMail, "");
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							selenium.click("css=input[name=expiredStatusEmailInd]");
							selenium.click("css=input[name=expiredStatusPagerInd]");
							
							strFuncResult = objCreateUsers.savVrfyUser(selenium, strUserName);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult =objViews.navRegionViewsList(selenium);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult =objViews.createView(selenium, strViewName, strVewDescription, strViewType, true, false, strSTvalue, false, strRSValue);
							
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
							strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
	
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(selenium, strResource, strNumStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(selenium, strResource, strMultiStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(selenium, strResource, strTxtStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(selenium, strResource, strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
															
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium, strNumUpdateValue1, strSTvalue[0],strComment1);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
												
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium, strStatusValue[0], strSTvalue[1],strComment2);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium, strTxtUpdateValue1, strSTvalue[2],strComment3);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium, strScUpdValue1, strSTvalue[3],strComment4);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
					
						try {
							assertEquals("", strFuncResult);
							
							strFuncResult = objViews.saveAndNavToViewScreen(selenium, "Region Default");
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						String strSnapTime=selenium.getText("css=#statusTime");
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.navToUserView(selenium, strViewName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						strNSTUpdTimeBef=strSnapTime+strNSTUpdTimeBef;
						try {
							assertEquals("", strFuncResult);						
						
							strFuncResult = objViews.verifyUpdSTCommentsAndLastUpdTimeInViews(selenium, strResource, strNumStatType, strNumUpdateValue1,strComment1,strNSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							
							strFuncResult = objViews.verifyUpdTimeInViewToolTipWithComments(selenium, strResource,strNumStatType, strNSTUpdTimeBef, strUsrFulName, strComment1);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
					
						
						try {
							assertEquals("", strFuncResult);						
							strMSTUpdTimeBef=strSnapTime+strMSTUpdTimeBef;
							strFuncResult = objViews.verifyUpdSTCommentsAndLastUpdTimeInViews(selenium, strResource, strMultiStatType, strStatusName1,strComment2,strMSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.verifyUpdTimeInViewToolTipWithComments(selenium, strResource, strMultiStatType, strMSTUpdTimeBef, strUsrFulName, strComment2);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
						
							strTSTUpdTimeBef=strSnapTime+strTSTUpdTimeBef;
							strFuncResult = objViews.verifyUpdSTCommentsAndLastUpdTimeInViews(selenium, strResource, strTxtStatType, strTxtUpdateValue1,strComment3,strTSTUpdTimeBef);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
					
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.verifyUpdTimeInViewToolTipWithComments(selenium, strResource, strTxtStatType,  strTSTUpdTimeBef, strUsrFulName, strComment3);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							
							strSSTUpdTimeBef=strSnapTime+strSSTUpdTimeBef;
							strFuncResult = objViews.verifyUpdSTCommentsAndLastUpdTimeInViews(selenium, strResource, strSatuStatType, strScUpdValCheck1,strComment4,strSSTUpdTimeBef);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
					
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.verifyUpdTimeInViewToolTipWithComments(selenium, strResource, strSatuStatType, strSSTUpdTimeBef, strUsrFulName, strComment4);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						
						try {
							assertEquals("", strFuncResult);

							String[] strEventStatType = {};
							
							strFuncResult = objMap.verifyStatTypesInResourcePopup(
									selenium, strResource, strEventStatType,
									strRoleStatType, false, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}
						strNSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 2,
								2, strFILE_PATH);
						strMSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 5,
								2, strFILE_PATH);
						strTSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 3,
								2, strFILE_PATH);
						strSSTUpdTimeBef=rdExcel.readInfoExcel("Timer", 4,
								2, strFILE_PATH);
						try {
							assertEquals("", strFuncResult);
							strNSTUpdTimeBef=strNSTUpdTimeBef.replace(" ", "");
							strNSTUpdTimeBef="("+strSnapTime+strNSTUpdTimeBef+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strNumUpdateValue1, strComment1,3,strNSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							
						}
						
						try {
							assertEquals("", strFuncResult);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strNSTUpdTimeAft=strNSTUpdTimeAft.replace(" ", "");
							strNSTUpdTimeAft="("+strSnapTime+strNSTUpdTimeAft+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strNumUpdateValue1, strComment1,3,strNSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
								
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
						
						try {
							assertEquals("", strFuncResult);
							strMSTUpdTimeBef=strMSTUpdTimeBef.replace(" ", "");
							strMSTUpdTimeBef="("+strSnapTime+strMSTUpdTimeBef+")";
							
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strStatusName1, strComment2,1,strMSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							
						}
						
						try {
							assertEquals("", strFuncResult);
						
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strMSTUpdTimeAft=strMSTUpdTimeAft.replace(" ", "");
							strMSTUpdTimeAft="("+strSnapTime+strMSTUpdTimeAft+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strStatusName1, strComment2,1,strMSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
								
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
						try {
							assertEquals("", strFuncResult);
							strTSTUpdTimeBef=strTSTUpdTimeBef.replace(" ", "");
							strTSTUpdTimeBef="("+strSnapTime+strTSTUpdTimeBef+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strTxtUpdateValue1, strComment3,7,strTSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							
						}
						
						try {
							assertEquals("", strFuncResult);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strTSTUpdTimeAft=strTSTUpdTimeAft.replace(" ", "");
							strTSTUpdTimeAft="("+strSnapTime+strTSTUpdTimeAft+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strTxtUpdateValue1, strComment3,7,strTSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
								
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
						try {
							assertEquals("", strFuncResult);
							strSSTUpdTimeBef=strSSTUpdTimeBef.replace(" ", "");
							strSSTUpdTimeBef="("+strSnapTime+strSSTUpdTimeBef+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strScUpdValCheck1, strComment4,5,strSSTUpdTimeBef);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							
						}
						
						try {
							assertEquals("", strFuncResult);
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
							strSSTUpdTimeAft=strSSTUpdTimeAft.replace(" ", "");
							strSSTUpdTimeAft="("+strSnapTime+strSSTUpdTimeAft+")";
							strFuncResult = objMap.verifyUpdValCommentsAndLastUpdTimeInResPopup(selenium, strResource, strScUpdValCheck1, strComment4,5,strSSTUpdTimeAft);
							try {
								assertEquals("", strFuncResult);
								
							} catch (AssertionError Aee) {
								gstrReason = strFuncResult;
							}
						}
					
						String strCurDateMnth="";
						int intWaitTimeDif = 0;
						try{
							assertEquals("", strFuncResult);
							String[] strArFunRes=new String[5];
							strArFunRes=objMap.getUpdTimeValue(selenium, "3");
						
							strFuncResult=strArFunRes[4];		
													
							String strCurYear=dts.getCurrentDate("yyyy");
							strCurDateMnth=strArFunRes[0]+strArFunRes[1]+strCurYear;
							
							strCurDate=dts.converDateFormat(strCurDateMnth,"ddMMMyyyy","MM/dd/yyyy");
							intWaitTimeDif = dts.getTimeDiff(strSSTAdUpdTime,
									strArFunRes[2]+":"+strArFunRes[3]);
							
						}
						catch (AssertionError Ae)
						{
							gstrReason = strFuncResult;
						}
											
						try {
							assertEquals("", strFuncResult);					
						
							Thread.sleep((intWaitTimeDif+2) * 1000);
							log4j.info((intWaitTimeDif+2) * 1000);
														
							strMsgBody1="For "+strUsrFulName+"\nRegion: "+strRegn+"\n\nThe "+strNumStatType+" status for "+strResource+" expired "+strCurDate+" "+strNSTAdUpdTime+".\n\nClick the link below to go to the EMResource login screen:"+
							
							"\n\n        https://emresource-test.emsystem.com"+"\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
							+"\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBody2="EMResource expired status: "+strResource+". "+strNumStatType+" status expired "+strCurDate+" "+strNSTAdUpdTime+".";
			
			
							strMsgBody3="For "+strUsrFulName+"\nRegion: "+strRegn+"\n\nThe "+strMultiStatType+" status for "+strResource+" expired "+strCurDate+" "+strMSTAdUpdTime+".\n\nClick the link below to go to the EMResource login screen:"+
			
							"\n\n        https://emresource-test.emsystem.com"+"\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
							+"\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";
			
							strMsgBody4="EMResource expired status: "+strResource+". "+strMultiStatType+" status expired "+strCurDate+" "+strMSTAdUpdTime+".";
			
							strMsgBody5="For "+strUsrFulName+"\nRegion: "+strRegn+"\n\nThe "+strTxtStatType+" status for "+strResource+" expired "+strCurDate+" "+strTSTAdUpdTime+".\n\nClick the link below to go to the EMResource login screen:"+
			
							"\n\n        https://emresource-test.emsystem.com"+"\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
							+"\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";
			
							strMsgBody6="EMResource expired status: "+strResource+". "+strTxtStatType+" status expired "+strCurDate+" "+strMSTAdUpdTime+".";
			
			
							strMsgBody7="For "+strUsrFulName+"\nRegion: "+strRegn+"\n\nThe "+strSatuStatType+" status for "+strResource+" expired "+strCurDate+" "+strSSTAdUpdTime+".\n\nClick the link below to go to the EMResource login screen:"+
			
							"\n\n        https://emresource-test.emsystem.com"+"\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
							+"\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";
			
							strMsgBody8="EMResource expired status: "+strResource+". "+strSatuStatType+" status expired "+strCurDate+" "+strSSTAdUpdTime+".";
			
							String[] strArPagerBody={strMsgBody2,strMsgBody4,strMsgBody6,strMsgBody8};
							String[] strArMailBody={strMsgBody1,strMsgBody3,strMsgBody5,strMsgBody7};
							selenium.selectWindow("");
							strFuncResult=objMail.loginAndnavToInboxInWebMail(selenium, strLoginName, strPassword);
							try {
								assertTrue(strFuncResult.equals(""));
								
								for(int intCnt=0;intCnt<4;intCnt++){
									if(intCnt!=0){
										selenium.selectFrame("relative=up");
										selenium.selectFrame("horde_main");
										//click on Back to Inbox
										selenium.click("link=Back to Inbox");
										selenium.waitForPageToLoad("90000");
									}
									strSubjName="EMResource Expired Status: "+strAbbrv;
									strFuncResult=objMail.verifyEmail(selenium, strFrom, strTo, strSubjName);
																
									try {
										assertTrue(strFuncResult.equals(""));
										for(int intMail=0;intMail<strArPagerBody.length;intMail++){
											String strMsg= selenium.getText("css=div.fixed.leftAlign");
											if(strMsg.compareTo(strArPagerBody[intMail])==0){
												intPagerRes[intMail]++;
												log4j.info(strArPagerBody[intMail]+" is displayed for Pager Notification" );
												break;
											}
										}
									} catch (AssertionError Ae) {
										gstrReason = gstrReason+" "+strFuncResult;
									}							
																
								
									selenium.selectFrame("relative=up");
									selenium.selectFrame("horde_main");
									//click on Back to Inbox
									selenium.click("link=Back to Inbox");
									selenium.waitForPageToLoad("90000");
									
									strSubjName="EMResource Expired Status Notification: "+strResource;
									strFuncResult=objMail.verifyEmail(selenium, strFrom, strTo, strSubjName);
									
									try {
										assertTrue(strFuncResult.equals(""));
										for(int intMail=0;intMail<strArMailBody.length;intMail++){
											String strMsg= selenium.getText("css=div.fixed.leftAlign");
											if(strMsg.compareTo(strArMailBody[intMail])==0){
												intEMailRes[intMail]++;
												log4j.info(strArMailBody[intMail]+" is displayed for Email Notification" );
												break;
											}	
										}
									} catch (AssertionError Ae) {
										gstrReason = gstrReason+" "+strFuncResult;
									}
								}
								
								for(int intCnt=0;intCnt<4;intCnt++){
									if(intEMailRes[intCnt]==0){
										log4j.info("Email notifications for " +strRoleStatType[intCnt]+" is NOT displayed");
										gstrReason=gstrReason+" Email notifications for " +strRoleStatType[intCnt]+" is NOT displayed";
									}
									if(intPagerRes[intCnt]==0){
										log4j.info("Pager notifications for " +strRoleStatType[intCnt]+" is NOT displayed");
										gstrReason=gstrReason+" Pager notifications for " +strRoleStatType[intCnt]+" is NOT displayed";
									}
									log4j.info(intEMailRes[intCnt]+" "+intPagerRes[intCnt]);
								}
							
									
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							
							try {
								assertEquals("", gstrReason);
								selenium.selectWindow("");
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium, strNumUpdateValue2, strSTvalue[0],strComment1);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								
														
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium, strStatusValue[0], strSTvalue[1],strComment2);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium, strTxtUpdateValue2, strSTvalue[2],strComment3);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								try {
									assertEquals("", strFuncResult);
									strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium, strScUpdValue2, strSTvalue[3],strComment4);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
							
								
								try {
									assertEquals("", strFuncResult);		
									String[] strArUpdVal={strNumUpdateValue2,strStatusName1,strTxtUpdateValue2,strScUpdValCheck2};
									strFuncResult=objMap.saveAndVerifyUpdtST(selenium, strResource, strArUpdVal);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
							
								try {
									assertEquals("", strFuncResult);			
									gstrResult="PASS";
								
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								
							} catch (AssertionError Ae) {
							
							}
								
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
																		
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
			
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85149";
			gstrTO = "Verify that user receives the following when the status of a resource expires at the SHIFT time: 1. Status update prompt. 2. Expired status notification";
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
	}*/
	
	//start//testBQS126268//
	/***************************************************************
	'Description		:Verify that section can be selected while editing a status type
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/7/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testSmoke126268() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();

		try {

			gstrTCID = "126268"; // Test Case Id
			gstrTO = " Verify that section can be selected while editing a status type";// Test
																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Sections
			String strSection1 = "AB_1" + strTimeText;
			String strSection2 = "AB_2" + strTimeText;
			String strSection3 = "AB_3" + strTimeText;

			String strSectionValue[] = new String[3];
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;

			// pNST
			String statpNumTypeName = "pNST" + strTimeText;
			// sSST
			String statShardSaturatTypeName = "sSST" + strTimeText;

			String str_roleStatusTypeValues[] = new String[5];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			String strStatusTypeValues[] = new String[2];

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String strRTValue = "";

			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			String strResVal = "";

			// User
			String strUserName1 = "auto1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName1 = strUserName1 + "fulName";

			// Roles
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";

			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strBefDelSTCount[] = new String[3];
			String strEditSection = "EditSEC_" + strTimeText;

			/*
			 * STEP : Action:Preconditions: 1. Sections 'Sec1','Sec2' and 'Sec3'
			 * are created. 2. Status types 'pNST' and 'sSST' are created and
			 * are under section 'Sec1' 3. Status types
			 * 'NST','MST','TST','SST','NEDOC' are created. 4. Resource Type
			 * 'RT' is created selecting 'NST','MST','TST','SST' and 'NEDOC'
			 * status types. 5. Resource 'RS' is created under 'RT' with
			 * address. 6. Status types 'pNST' and 'sSST' are added to resource
			 * 'RS' at resource level. 7.User 'U1' has the following rights: a)
			 * Configure Region Views b) Setup Status Types c) Role R1 to view
			 * and update status types d) View and Update right on resource 'RS'
			 * Expected Result:No Expected Result
			 */
			// 662782

			log4j.info("~~~~~PRE-CONDITION  " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Section1
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {};
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue[0] = objViews.fetchSectionID(
						seleniumPrecondition, strSection1);
				if (strSectionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Section2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {};
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue[1] = objViews.fetchSectionID(
						seleniumPrecondition, strSection2);
				if (strSectionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Scetion3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {};
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection3, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSectionValue[2] = objViews.fetchSectionID(
						seleniumPrecondition, strSection3);
				if (strSectionValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status types
			// Role based status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// pNST
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statpNumTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						seleniumPrecondition, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statpNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpNumTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// sSST

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statShardSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypeVisibility(
						seleniumPrecondition, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						seleniumPrecondition, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statShardSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statShardSaturatTypeName);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strArrSTvalue[] = { str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_roleStatusTypeValues[4] };
				assertEquals("", strFuncResult);

				for (int i = 0; i < strArrSTvalue.length; i++) {
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strArrSTvalue[i], true);
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrcTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strStatusTypeValues[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strStatusTypeValues[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.savAndVerifyEditRSLevelPage(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Roles

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_roleStatusTypeValues[4], strStatusTypeValues[0],
						strStatusTypeValues[1] };

				String[] strViewRightValue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_roleStatusTypeValues[4], strStatusTypeValues[0],
						strStatusTypeValues[1] };

				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);
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

			log4j.info("~~~~~PRE-CONDITION  " + gstrTCID + " ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info(" ~~~~~~~Test Case " + gstrTCID
					+ " execution starts ~~~~~~~~~ ");

			/*
			 * STEP : Action:Login as user 'U1', Navigate to Setup >> Status
			 * Types. Expected Result:'Status Types List' screen is displayed.
			 * 'Sec1'is displayed under 'Section' column for status types 'pNST'
			 * and 'sSST'. 'Uncategorized'is displayed under 'Section' column
			 * for status types 'NST','MST','TST','SST' and 'NEDOC'.
			 */
			// 662802

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statpNumTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statShardSaturatTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArr[] = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName,
					statrNedocTypeName };

			for (int i = 0; i < strArr.length; i++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes
							.savAndVerifySectionInStatPage(selenium, strArr[i],
									"Uncategorized", false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			/*
			 * STEP : Action:Click on edit link associated with status type
			 * 'NST' Expected Result:'Edit Number Status Type' screen is
			 * displayed.
			 */
			// 662803
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrNumTypeName, strNSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select 'Sec2' from 'Sections' drop down and 'Save'
			 * Expected Result:'Sec2'is displayed under 'Section' column for
			 * status types 'NST'.
			 */
			// 662804
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statrNumTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit status types 'MST','TST','SST','NEDOC',Select
			 * 'Sec2' from 'Sections' drop down and 'Save' Expected
			 * Result:'Sec2'is displayed under 'Section' column for status types
			 * 'MST','TST','SST','NEDOC'
			 */
			// 662805

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrTextTypeName, strTSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statrTextTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrSaturatTypeName, strSSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statrSaturatTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrNedocTypeName, strNDSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statrNedocTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrMultiTypeName, strMSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(
						selenium, statrMultiTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map , Select resource 'RS' from
			 * 'Find Resource' drop down. Expected Result:Resource popup window
			 * for 'RS' is displayed on 'Map' with 'View Info' and 'Update
			 * Status' link on it.
			 */
			// 662806

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'View Info' link on 'Resource pop up'
			 * window Expected Result:'View Resource Detail' screen for resource
			 * 'RS' is displayed. Status types 'pNST' and 'sSST' are listed
			 * under 'Sec1'. Status types 'NST','MST','TST','SST' and 'NEDOC'
			 * are listed under 'Sec2'.
			 */
			// 662807

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArStatType[] = { statpNumTypeName,
					statShardSaturatTypeName };

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArStatType1[] = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName,
					statrNedocTypeName };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetailForSecTble(selenium,
						strSection2, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views, Click on 'Customize
			 * Resource Detail View' Expected Result:Count '2' is displayed
			 * corresponding to section 'Sec1'. Status types 'pNST' and 'sSST'
			 * are listed under 'Sec1'. Count '5' is displayed corresponding to
			 * section 'Sec2'. Status types 'NST','MST','TST','SST' and 'NEDOC'
			 * are listed under 'Sec2'. '0' is displayed corresponding to
			 * section 'Sec3'.
			 */
			// 662808

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strBefDelSTCount = objViews.GetSTCountInEditResDetailViewSec(
						selenium, strSection1);
				log4j.info(strBefDelSTCount[1]
						+ "is displayed corresponding to section  "
						+ strSection1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection1, statpNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection1, statShardSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strBefDelSTCount = objViews.GetSTCountInEditResDetailViewSec(
						selenium, strSection2);
				log4j.info(strBefDelSTCount[1]
						+ "is displayed corresponding to section  "
						+ strSection2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strBefDelSTCount = objViews.GetSTCountInEditResDetailViewSec(
						selenium, strSection3);
				log4j.info(strBefDelSTCount[1]
						+ "is displayed corresponding to section  "
						+ strSection3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit section name of Sec1 to Sec1_edit Expected
			 * Result:Status types 'pNST' and 'sSST' are listed under
			 * 'Sec1_edit'.
			 */
			// 662809

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.editSection(selenium, strSection1,
						strEditSection);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strEditSection, statpNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strEditSection, statShardSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Move status type 'sSST' from 'Sec1_edit' to 'Sec3'
			 * and save. Expected Result:'Region Views List' screen is
			 * displayed.
			 */
			// 662810

			String strDropStatType[] = { statShardSaturatTypeName };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTFormOneSectionToAnother(
						selenium, strDropStatType, strEditSection, strSection3,
						false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' Expected
			 * Result:Status type 'pNST' is listed under 'Sec1_edit'. Status
			 * types 'NST','MST','TST','SST' and 'NEDOC' are listed under
			 * 'Sec2'. Status type 'sSST' is listed under 'Sec3'.
			 */
			// 662811

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strEditSection, statpNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection3, statShardSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(
						selenium, strSection2, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map , Select resource 'RS' from
			 * 'Find Resource' drop down. Expected Result:Resource popup window
			 * for 'RS' is displayed on 'Map' with 'View Info' and 'Update
			 * Status' link on it.
			 */
			// 662942

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'View Info' link on 'Resource pop up'
			 * window Expected Result:'View Resource Detail' screen for resource
			 * 'RS' is displayed. Status types 'pNST' is listed under
			 * 'Sec1_edit'. Status types 'NST','MST','TST','SST' and 'NEDOC' are
			 * listed under 'Sec2'. Status type 'sSST' is listed under 'Sec3'
			 */
			// 662943

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArStatTypeNew[] = { statpNumTypeName };

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strEditSection, strArStatTypeNew);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArStatType1New[] = { statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName,
					statrNedocTypeName };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetailForSecTble(selenium,
						strSection2, strArStatType1New);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strArStatType3New[] = { statShardSaturatTypeName };

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetailForThirdTble(selenium,
						strSection3, strArStatType3New);
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
			gstrTCID = "BQS-126268";
			gstrTO = "Verify that section can be selected while editing a status type";
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

	// end//testBQS126268//
	
   //start//testBQS126267//
	/************************************************************************************
	'Description	:Verify that while creating a new status type section can be selected
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/7/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                          Modified By
	'Date					                                              Name
	*************************************************************************************/

	@Test
	public void testSmoke126267() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
	    CreateUsers objCreateUsers=new CreateUsers();
		Views objViews = new Views();
		
		try {
			gstrTCID = "126267"; // Test Case Id
			gstrTO = " Verify that while creating a new status type section can be selected";//TO
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";
			
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String statrNedocTypeName = "rNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";
			
			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs1_A" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
						
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = "abc123";
			String strConfirmPwd = "abc123";
			String strUsrFulName = "Ful" + strUserName;

			// search user data
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// sec data
			String strSection1 = "SEC1_" + strTimeText;
			String strSection2 = "SEC2_" + strTimeText;
			String strSectionValue[] = new String[2];
			String strBefDelSTCount[] = new String[5];
			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
		/*
		* STEP :
		  Action:Precondition:
		  1. User 'U1' in region 'RG1' has the following rights:
		    a) Configure Region Views
			b) Setup Status Types
			c) Setup Resource Types
			d) Setup Resources
			e) Role R1
		  Expected Result:No Expected Result
		*/
		//662734
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STARTS~~~~~");
			
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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			
			log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
			
		/*
		* STEP :
		  Action:Login as user 'U1',Navigate to Setup >> Status types
		  Expected Result:'Status Types List' screen is displayed.
          'Create New Status Type' and 'Standard Status Type Labels' buttons are available at top left.
		*/
		//662735
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
		/*
		* STEP :
		  Action:Create status types 'NST' and 'MST'
		  Expected Result:Status Types 'NST' and 'MST' are displayed on 'Status Types List' screen.
		  'Uncategorized'is displayed under 'Section' column for status types 'NST' and 'MST'
		*/
		//662737
			// Role based status type
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.VarDifftStatusTypesInDropDown(
						selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSection = "Uncategorized";
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrNumTypeName, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSection = "Uncategorized";
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrMultiTypeName, strSection, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(selenium, statrMultiTypeName,
								str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(selenium, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Navigate to Setup >> Views, Click on 'Customize Resource Detail View'
		  Expected Result:'Edit Resource Detail View Sections' screen is displayed.
		  Status types 'NST' and 'MST' are displayed under 'uncategorized' section.
		*/
		//662738
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews
						.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Create section 'Section1', drag and drop status types 'NST','MST' to section 'Section1'
		  Expected Result:Count 2 is displayed corresponding to section  'Section1'
		  Status types 'NST','MST' are listed under it
		*/
		//662739
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {statrNumTypeName,statrMultiTypeName };
				strFuncResult = objViews
						.dragAndDropSTtoSection(selenium,
								strStatTypeArr, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strBefDelSTCount = objViews.GetSTCountInEditResDetailViewSec(
						selenium, strSection1);
				log4j.info(strBefDelSTCount[1]
						+ "is displayed corresponding to section  "
						+ strSection1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSectionValue[0] = objViews.fetchSectionID(selenium,
						strSection1);
				if (strSectionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Create section 'Section2'
		  Expected Result:Section 'Section2' is listed under 'Edit Resource Detail View Sections' screen.
		  '0' is displayed corresponding to section 'Section2'
		*/
		//662740
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = {};
				strFuncResult = objViews
						.dragAndDropSTtoSection(selenium,
								strStatTypeArr, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strBefDelSTCount = objViews.GetSTCountInEditResDetailViewSec(
						selenium, strSection2);
				log4j.info(strBefDelSTCount[1]
						+ "is displayed corresponding to section  "
						+ strSection1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strSectionValue[1] = objViews.fetchSectionID(selenium,
						strSection1);
				if (strSectionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Status types , Click on 'Create New Status Types' button and select
		   'Saturation Score' from 'Select Type' button and click on next
		  Expected Result:'Create Saturation Score Status Type' screen is displayed.
		*/
		//662741
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
		/*
		* STEP :
		  Action:Click on 'Sections' drop down under 'Create Saturation Score Status Type' page.
		  Expected Result:Status type sections 'Section1' and 'Section2' are listed under it along with
		   other sections created in the region.
		*/
		//662742
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue[0], true,
								strSSTValue, "Create");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Provide mandatory data and select 'Section2' from 'Sections' drop down
		  Expected Result:'Section2' is displayed under 'Section' column for status type 'SST' on 
		  'Status Types List' screen.
		*/
		//662743
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selSectionForStatusType(selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrSaturatTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Create status types 'TST' and 'NEDOC' selecting 'Section2' from 'Sections' drop down
		  Expected Result:'Section2' is displayed under 'Section' column for status types 'TST' and
		  'NEDOC' on 'Status Types List' screen.
		*/
		//662744
			//TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selSectionForStatusType(selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrTextTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrTextTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selSectionForStatusType(selenium, strSection2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrNedocTypeName, strSection2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Views, Click on 'Customize Resource Detail View'
		  Expected Result:Count '3' is displayed corresponding to section 'Section2'.
		  Status types 'TST','SST' and 'NEDOC' are added to the section 'Section2'
		*/
		//662745
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews
						.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strBefDelSTCount = objViews.GetSTCountInEditResDetailViewSec(
						selenium, strSection1);
				log4j.info(strBefDelSTCount[1]
						+ "is displayed corresponding to section  "
						+ strSection1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(selenium,
						strSection2, statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(selenium,
						strSection2, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSectionInEditResDetViewSec(selenium,
						strSection2, statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resource Types, Create resource Type 'RT' selecting status types
		   'NST','MST','TST','SST' and 'NEDOC'
		  Expected Result:Resource Type 'RT' is listed under 'Resource Type List' screen
		*/
		//662760
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[4] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources, Create resource 'RS' under 'RT'
		  Expected Result:Resource 'RS' is listed under 'Resource List' screen
		*/
		//662761
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrcTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strResVal = objResources.fetchResValueInResList(
						selenium, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
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
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Navigate to Setup >> Views , Create view 'V1' selecting status types 'NST','MST',
		  'TST','SST','NEDOC', resource type 'RT' and resource 'RS'
		  Expected Result:View 'V1' is listed under 'Region Views List' screen.
		*/
		//662762
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],str_roleStatusTypeValues[4] };
				String[] strReSValue = { strRSValue[0]};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strReSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Navigate to View >> V1
		  Expected Result:Resource RS is displayed under RT along with status types NST,MST,SST,TST and 
		  'NEDOC'
		*/
		//662775
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				String[] strResourceArr = { strResource };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName, strResourceArr, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on resource 'RS'
		  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.
		  Status types 'NST' and 'MST' are listed under 'Section1'.
		  Status types 'TST','SST' and 'NEDOC' are listed under 'Section2'.
		*/
		//662776
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = { { "Type:", strResrcTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName, statrNumTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue[0], strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNedocTypeName,
						statrSaturatTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailForSecTble(
						selenium, strSection2, strStatType);
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
			gstrTCID = "BQS-126267";
			gstrTO = "Verify that while creating a new status type section can be selected";
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
	// end//testBQS126267//
	


}
