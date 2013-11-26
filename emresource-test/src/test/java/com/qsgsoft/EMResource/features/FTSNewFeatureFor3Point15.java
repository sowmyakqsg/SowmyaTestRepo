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

/**********************************************************************
' Description :This class includes NewFeatureFor3Point15 requirement 
'				testcases
' Precondition:
' Date		  :4-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSNewFeatureFor3Point15  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSNewFeatureFor3Point15");
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
	
	
	
	/********************************************************************************
	'Description	:Edit a user and remove the right "Form -User may create and 
	'				 modify forms" and verify that user cannot configure forms.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-July-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS21514() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Forms objForms=new Forms();
		
		try {
			gstrTCID = "21514 ";
			gstrTO = "Edit a user and remove the right Form -User may create and "
					+ "modify forms and verify that user cannot configure forms.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			String strLoginUserName = "";// Admin login user name
			String strLoginPassword = "";

			String strRegn = "";

			String strOptions = "";

			// User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			// Search user criteria
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			/*
			 * 1 Precondition: User U1 is created selecting the right
			 * "Form - User may create and modify forms". No Expected Result
			 */
			
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

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 9, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);


				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				strInitPwd = rdExcel.readInfoExcel("User_Template", 7, 9,
						strFILE_PATH);

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(selenium
							.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							1);

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserName + "']"));
					log4j.info("User " + strUserName
							+ " Present in the User List ");

				} catch (AssertionError Ae) {
					strFuncResult = "User " + strUserName
							+ " NOT Present in the User List " + Ae;
					log4j.info("User " + strUserName
							+ " NOT Present in the User List " + Ae);
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 3: Navigate to 'Setup >> Users' and click on 'Edit'
			 * associated with U1. 'Edit User' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 4: Deselect the right "Form - User may create and modify forms"
			 * and save. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);

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
			 * STEP 5: Login as U1 and click on 'Form' tab.<-> 'Configure Forms'
			 * option is not available.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 6: Hover the mouse over 'Form' tab.<-> 'Configure Forms'
			 * option is not available in the dropdown.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseHover(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "21514";
			gstrTO = "Edit a user and remove the right Form -User may create "
					+ "and modify forms and verify that user cannot configure forms.";
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
	
	/********************************************************************************
	'Description	:Edit a user to associate the user with a role R1 that was created
	'				 selecting the right 'Form - User may create and modify forms' and
	'				 verify that user can configure forms.
	'Precondition	:1. Role R1 is created selecting the right 'Form - User may create
	'				 and modify forms'.
	'				 2. User U1 is created neither selecting role R1 nor the right 'Form
	'				 - User may create and modify forms'. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:9-July-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS21519() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		// CreateUsers
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Forms objForms = new Forms();
		Roles objRoles = new Roles();
		General objGeneral=new General();

		try {
			gstrTCID = "21519 ";
			gstrTO = "Edit a user to associate the user with a role R1 that "
					+ "was created selecting the right 'Form - User may create"
					+ " and modify forms' and verify that user can configure forms.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			//String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			String strLoginUserName = "";// Admin login user name
			String strLoginPassword = "";

			String strRegn = "";

			String strOptions = "";

			// User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			// Search user criteria
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";

			String strFormTempTitle = "";
			String strFormDesc = "";

			String strQuestion = "";
			String strDescription = "";

			String strPreForm = "";

			String strRoleName = "";
			String strRoleValue = "";

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			/*PRECONDITION STARTS*/
			
			/*
			 * 1 Precondition: 1. Role R1 is created selecting the right 'Form -
			 * User may create and modify forms'. 2. User U1 is created neither
			 * selecting role R1 nor the right 'Form - User may create and
			 * modify forms'. No Expected Result
			 */
			
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
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleName = "AutoRole" + System.currentTimeMillis();
				String[][] strRoleRights = {};
				String[] strViewRightValue = {};
				String[] updateRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
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

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 9, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}

			/*
			 * try { assertEquals("", strFuncResult);
			 * 
			 * strOptions = propElementDetails
			 * .getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
			 * strFuncResult = objCreateUsers.advancedOptns(selenium,
			 * strOptions, true);
			 * 
			 * } catch (AssertionError Ae) { gstrReason = strFuncResult;
			 * 
			 * }
			 */
			try {
				assertEquals("", strFuncResult);

				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				strInitPwd = rdExcel.readInfoExcel("User_Template", 7, 9,
						strFILE_PATH);

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(selenium
							.getXpathCount("//table[@id='tblUsers']/tbody/tr"),
							1);

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/"
									+ "td[2][text()='" + strUserName + "']"));
					log4j.info("User " + strUserName
							+ " Present in the User List ");

				} catch (AssertionError Ae) {
					strFuncResult = "User " + strUserName
							+ " NOT Present in the User List " + Ae;
					log4j.info("User " + strUserName
							+ " NOT Present in the User List " + Ae);
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*PRECONDITION ENDS*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* STEP 2: Login as RegAdmin.<-> No Expected Result */
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
			 * STEP 3: Navigate to 'Setup >> Users' and click on 'Edit'
			 * associated with U1.<-> 'Edit User' screen is displayed.
			 */
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
			
			/* STEP 4: Select the role R1 and save.<-> No Expected Result */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);
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
			 * STEP 5: Login as U1 and click on 'Form' tab.<-> 'Configure Forms'
			 * option is available.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* click on 'Form' tab. 'Configure Forms' option is available */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 6 Hover the mouse over 'Form' tab. 'Configure Forms' option is
			 * available in the dropdown.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseHover(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("FormConfig.CreateNewFormTemp");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				log4j.info("'Create New Form Template' button is available.");

				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = "'Create New Form Template' button is NOT available."
						+ strFuncResult;
				log4j
						.info("'Create New Form Template' button is NOT available.");
			}

			/*
			 * STEP 8: Create a new form NF1.<-> Form is created and displayed
			 * in 'Form Configuration' screen with 'Edit', 'Questionnaire'
			 * options.
			 */
			try {
				assertEquals("", strFuncResult);

				strFormTempTitle = "NF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms
						.fillAllFieldsInCreateNewForm(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								strComplFormDel, false, false, false, false,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(selenium, "",
						strFormTempTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 7: Click on 'Configure Forms'.<-> 'Form Configuration' page
			 * is displayed. 'Create New Form Template' button is available.
			 * Existing forms are displayed with the options 'Edit' and
			 * 'Questionnaire'.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2]"
						+ "[text()='"
						+ strFormTempTitle
						+ "']/ancestor::table/thead/"
						+ "tr/th[1][text()='Action']/ancestor::table/tbody/tr/td[2]"
						+ "[text()='"
						+ strFormTempTitle
						+ "']/parent::tr/td[1]/a[text" + "()='Questionnaire']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Existing forms are displayed with the option 'Questionnaire'.");

				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2]"
						+ "[text()='"
						+ strFormTempTitle
						+ "']/ancestor::table/thead/tr/"
						+ "th[1][text()='Action']/ancestor::table/tbody/tr/td[2][text()"
						+ "='"
						+ strFormTempTitle
						+ "']/parent::tr/td[1]/a[text()='Edit']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j
				.info("Existing forms are NOT displayed with the option 'Questionnaire'.");
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Existing forms are displayed with the option 'Edit' ");

				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				log4j
						.info("Existing forms are NOT displayed with the option 'Edit' ");
			}
			/*
			 *STEP 9: Edit the name, description of form NF1.<-> The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */ 
			try {
				assertEquals("", strFuncResult);

				strFormTempTitle = "EDIT" + "NF" + System.currentTimeMillis();
				strPreForm = strFormTempTitle;

				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms
						.fillAllFieldsInCreateNewForm(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								strComplFormDel, false, false, false, false,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(selenium, "",
						strFormTempTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 12: Create a old form OF1.<-> Form is created and displayed
			 * in 'Form Configuration' screen with 'Edit', 'Questionnaire'
			 * options.
			 */
			try {
				assertEquals("", strFuncResult);

				strFormTempTitle = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitle, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(selenium, "",
						strFormTempTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 13: Edit the name, description of OF1.<-> The updated data
			 * is displayed in the list of 'Form Configuration' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFormTempTitle = "EDIT" + strFormTempTitle;
				strFormDesc = "EDIT" + strFormDesc;
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitle, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(selenium, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(selenium, "",
						strFormTempTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitle);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 14: Add a questionnaire Q1 (add questions) to the form
			 * OF1.<-> The added questions are displayed correctly when clicked
			 * on 'Questionnaire'.
			 */ 

			try {
				assertEquals("", strFuncResult);

				strQuestion = "Q" + System.currentTimeMillis();
				strDescription = "Description";
				String strquesTypeID = "Free text field";

				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitle, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToEditQuestion(selenium,
						strQuestion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP 15: Edit the questionnaire Q1 (add/remove/edit
			 * questions).<-> The updated data is displayed when clicked on
			 * 'Questionnaire'.
			 */

			try {
				assertEquals("", strFuncResult);

				strQuestion = "EDIT" + strQuestion;
				strDescription = "EDIT" + strDescription;

				strFuncResult = objForms.CreateQuestionFlds(selenium,
						strFormTempTitle, strQuestion, strDescription, false,
						false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName + "/" + strInitPwd,
						"",
						"",
						"Add questionnaire to the Form:" + strPreForm
								+ "and edit the questionnaire" };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Forms");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "21519";
			gstrTO = "Edit a user to associate the user with a role R1 that was "
					+ "created selecting the right 'Form - User may create and modify"
					+ " forms' and verify that user can configure forms.";
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
