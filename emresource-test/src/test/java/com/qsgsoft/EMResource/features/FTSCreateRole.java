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
' Description :This class includes FTSCreateRole requirement 
'				testcases
' Date		  :16-Jan-2013
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSCreateRole  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateRole");
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
	
	Selenium selenium,seleniumPrecondition,seleniumFirefox;
	
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

		seleniumFirefox = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		try {
			selenium.close();
		} catch (Exception e) {
			
		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		seleniumFirefox.stop();
		
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
	'Description	:Verify that a document can be added to the event while editing.
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-March-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS111918() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles = new Roles();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "111918";
			gstrTO = "Verify that a document can be added to the event while editing.";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strRolesName_1 = "Role_1" + strTimeText;

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strStatValue = "";
			String strSTvalue[] = new String[1];

			/*
			 * 1 Login as RegAdmin and navigate to Setup >> Roles 'Roles List'
			 * screen is displayed.
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status types 'MST','NST','SST','TST' are created.
			 */
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, strNumStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, strNumStatType);
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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Click on 'Create New Role' button 'Create Role' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" }};
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.cancelAndNavToRoleListPage(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "111918";
			gstrTO = "Verify that a document can be added to the event while editing.";
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
	'Description	:Verify that a user can be provided a role with 'Maintain Event 
	Template' and 'User-setup user accounts' rights
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-March-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS111920() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetUp=new EventSetup();
		General objGeneral=new General();
		EventNotification objEventNotification=new EventNotification();
		

		try {
			gstrTCID = "111920";
			gstrTO = "Verify that a user can be provided a role with " +
					"'Maintain Event Template' and 'User-setup user " +
					"accounts' rights";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue="";

			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			
			String strTempName1 = "ET1" + System.currentTimeMillis();
			
		
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
				strFuncResult = objEventSetUp.navToEventSetupPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);;
				strFuncResult = objEventSetUp.navToCreateNewEventTemplatePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 7 Click on 'Create New Event Template' button. 'Create New Event
			 * Template' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String[] strResTypeValue = {  };
				String[] strStatusTypeVal = {  };
				strFuncResult = objEventSetUp.CreateETBySelctngRTAndST(
						selenium, strTempName1, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Precondition:
			 * 
			 * 1. User 'U1' is created selecting 'User-Setup user account'
			 * right. No Expected Result
			 */	
				
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Click on 'Create New Role' button 'Create Role' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Create a role 'R1' by selecting 'Maintain Event Template' right
			 * from 'Select the Rights for this Role' section. Role R1 is
			 * displayed in 'Roles List' page.
			 */
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 4 Navigate to Setup >>Users and click on the 'Edit' link
			 * associated with user U1,select role R1 from 'User Type & Roles'
			 * section and save the page. 'Users List' page is displayed.
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
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Login as user 'U1' 'Region Default' screen is displayed.
			 */
			
		

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Navigate to Event >> Event Setup 'Event Template List' screen
			 * is displayed. Event Templates in the region are listed with
			 * 'Edit' and 'Notification' link associated with them.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetUp.navToEventSetupPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
							+ strTempName1
							+ "']/parent::tr/td[1]/a[2][text()='Notifications']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("")){
					log4j.info("'Notification' link is displayed corresponding to event templates "+strTempName1);
					
				}else{
					log4j.info("'Notification' link is NOT Displayed corresponding to event templates "+strTempName1);
					strFuncResult="'Notification' link is NOT Displayed corresponding to event templates "+strTempName1;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
							+ strTempName1
							+ "']/parent::tr/td[1]/a[1][text()='Edit']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("")){
					log4j.info("'Edit' link is displayed corresponding to event templates "+strTempName1);
					
				}else{
					log4j.info("'Edit' link is NOT Displayed corresponding to event templates "+strTempName1);
					strFuncResult="'Edit' link is NOT Displayed corresponding to event templates "+strTempName1;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Create event template ET, providing data in all the mandatory
			 * fields and click on 'Save'. 'Event Notification Preferences for <
			 * Event template name >' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetUp.navToCreateNewEventTemplatePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String[] strResTypeValue = {  };
				String[] strStatusTypeVal = { };
				strFuncResult = objEventSetUp.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			/*
			 * 9 Select check box associated with user 'U1' for 'Email',
			 * 'Pager', 'Web' (check box is selected by default for all the
			 * users ) and click on 'Save' Event template 'ET' is listed on
			 * 'Event Template List' screen. Event Templates in the region are
			 * listed with 'Edit' and 'Notification' link associated with them.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventNotification.selectEventNofifForUser(
						selenium, strUsrFulName_1, strTempName, true, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {

				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
						+ strTempName
						+ "']/parent::tr/td[1]/a[text()='Notifications']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
							.info("'Notification' link is displayed corresponding to event templates "
									+ strTempName);

				} else {
					log4j
							.info("'Notification' link is NOT Displayed corresponding to event templates "
									+ strTempName);
					strFuncResult = "'Notification' link is NOT Displayed corresponding to event templates "
							+ strTempName;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);

				String strElementID = "//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
						+ strTempName + "']/parent::tr/td[1]/a[text()='Edit']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
							.info("'Edit' link is displayed corresponding to event templates "
									+ strTempName);

				} else {
					log4j
							.info("'Edit' link is NOT Displayed corresponding to event templates "
									+ strTempName);
					strFuncResult = "'Edit' link is NOT Displayed corresponding to event templates "
							+ strTempName;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "111920";
			gstrTO = "Verify that a user can be provided a role with 'Maintain Event Template' and 'User-setup user accounts' rights";
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
	'Description	:Verify that role can be created selecting both view and update right on 'Status Types'
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-March-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS111917() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		EventSetup objEventSetup = new EventSetup();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		Views objViews=new Views();
		Roles objRoles=new Roles();
		ViewMap objMap = new ViewMap();

		try {
			gstrTCID = "111917";
			gstrTO = "Verify that role can be created selecting both" +
					" view and update right on 'Status Types'";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "auto event";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			
			//Data for creating View
			String strViewName_1="autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue="";
			
			// sec data
			String strSection1 = "AB1_" + strTimeText;

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status type 'NST', 'MST', 'SST', 'TST' are created.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						seleniumPrecondition, strTSTValue, strTxtStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type 'RT' is created selecting status types 'NST',
			 * 'MST', 'SST', 'TST'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3. Resource 'RS' is created providing address under resource Type
			 * 'RT'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. View 'V1' is created selecting status types 'NST', 'MST',
			 * 'SST', 'TST' and resource 'RS'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName_1, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*5. User 'U1' is created with following rights:

				a. Maintain events right.
				b. View resource on Resource RS. */
				
				
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
			

			/*
			 * 7. User 'U1' has �Update Status� right on resource RS.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);
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

			
			/*
			 * 5. Event template 'ET1' is created selecting status types 'NST',
			 * 'MST', 'SST', 'TST' and resource type 'RT'
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
				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
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
			 * 6. Event 'EVE' is created under the template 'ET1' selecting
			 * 'RS'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventWitManyRS(
						seleniumPrecondition, strTempName, strRSValue,
						strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strNumStatType, strTxtStatType,
						strSatuStatType, strMultiStatType };
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' screen is displayed.
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
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { {strSTvalue[0],"true"},{ strSTvalue[1],"true"},
						{strSTvalue[2],"true"}, {strSTvalue[3] ,"true"}};
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, true, true, strSTViewValue, strSTViewValue,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Navigate to Setup>>Users and click on the 'Edit' link
			 * associated with user U1 and select the role R1 from 'User Type &
			 * Roles' section and save the page. 'Users List' screen is
			 * displayed.
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
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5 Login as User 'U1' and navigate to View >> V1, click on key
			 * icon associated with 'RS',update the status of 'NST', 'MST',
			 * 'SST', 'TST' and click on 'Save'. The updated status values are
			 * displayed on view 'V1' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium,
						strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[1], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[2], false, "", "");			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[3]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strMultiStatType, strStatusName1, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strNumStatType, "101", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strSatuStatType, "429", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strTxtStatType, "102", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
		/*
		 * 6 Click on Resource 'RS',click on key icon associated with
		 * 'RS',update the status of 'NST', 'MST', 'SST', 'TST' and click on
		 * 'Save'. The updated status values are displayed on 'View Resource
		 * Detail' screen of resource 'RS'
		 */
					
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strRSValue[0], strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[0], false, "", "");	
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strRSValue[0], strSTvalue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[1], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strRSValue[0], strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"103", strSTvalue[2], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strRSValue[0], strSTvalue[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[3]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strMultiStatType, strTxtStatType,
						strSatuStatType, strNumStatType };
				String[] strUpdtVal = { strStatusName2, "103", "429", "102" };
				strFuncResult = objViews.verifyOnlyUpdSTValInResDetail(
						selenium, strStatType, strUpdtVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 7 Navigate to View >> Map, select resource 'RS' under 'Find
		 * Resource' dropdown and update the status value of of 'NST',
		 * 'MST', 'SST', 'TST' on 'resource pop up window' and click on
		 * 'Save'. 'Regional Map View'screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[1], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[2], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[3]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 8 Select resource 'RS' from 'Find Resource' dropdown. The updated
		 * status values are displayed on 'Resource popup window'of resource
		 */
			
			try {
				assertEquals("", strFuncResult);

				String[] strUpdtVal = { strStatusName1, "101", "429", "102" };
				for (String s : strUpdtVal) {
					try {
						assertEquals("", strFuncResult);

						strFuncResult = objMap.verifyUpdValInMap(selenium, s);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 9 Click on the event banner 'EV' which is displayed at the top
		 * and update the status values of statuses 'NST', 'MST', 'SST',
		 * 'TST' by clicking on key icon associated with resource 'RS'.
		 * Updated status values are displayed on the 'Event Status' screen
		 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[1], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[2], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[3]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strMultiStatType, strStatusName1, "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strNumStatType, "101", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strSatuStatType, "429", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						strTxtStatType, "102", "4");
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
			gstrTCID = "111917";
			gstrTO = "Verify that role can be created selecting both view and update right on 'Status Types'";
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
	'Description	:Verify that a user can be provided a role with 'Maintain Event Template' right
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-March-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS111919() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		EventSetup objEventSetUp=new EventSetup();
		General objGeneral=new General();
		

		try {
			gstrTCID = "111919";
			gstrTO = "Verify that a user can be provided a role with 'Maintain Event Template' right";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue="";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			
	
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		

			/*
			 * 1 Precondition:
			 * 
			 * 1. User 'U1' is created. No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Create a role 'R1' by selecting 'Maintain Event Template' right
			 * from 'Select the Rights for this Role' section. Role R1 is
			 * displayed in 'Roles List' page.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Create a role 'R1' by selecting 'Maintain Event Template' right
			 * from 'Select the Rights for this Role' section. Role R1 is
			 * displayed in 'Roles List' page.
			 */
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			/*
			 * 4 Navigate to Setup >>Users and click on the 'Edit' link
			 * associated with user U1,select role R1 from 'User Type & Roles'
			 * section and save the page. 'Users List' page is displayed.
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
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5 Login as user 'U1' 'Region Default' screen is displayed.
			 */
		

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Navigate to Event >> Event Setup 'Event Template List' screen
			 * is displayed. Event Templates in the region are listed with
			 * 'Edit' and 'Notification' link associated with them.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetUp.navToEventSetupPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 7 Click on 'Create New Event Template' button. 'Create New Event
			 * Template' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetUp.navToCreateNewEventTemplatePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetUp.CreateETByMandFields(selenium,
						strTempName, strTempDef, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objGeneral.assertEQUALS(selenium, "css=h1",
						"Event Notification Preferences for " + strTempName);

				if (strFuncResult.equals("Element NOT Present")) {
					log4j
							.info("�Event notification Preferences for < Event Template name >�"
									+ " screen is NOT displayed.");
					strFuncResult = "";

				} else {
					log4j
							.info("�Event notification Preferences for < Event Template name >�"
									+ " screen is displayed.");
					strFuncResult = "�Event notification Preferences for < Event Template name >�"
							+ " screen is displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
							+ strTempName
							+ "']/parent::tr/td[1]/a[text()='Notifications']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("Element NOT Present")){
					log4j.info("'Notification' link is NOT displayed corresponding to event templates");
					strFuncResult="";
					
				}else{
					log4j.info("'Notification' link is Displayed corresponding to event templates");
					strFuncResult="'Notification' link is Displayed corresponding to event templates";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {

				assertEquals("", strFuncResult);

				String strElementID="//div[@id='mainContainer']/table[2]/tbody/tr/td[3][text()='"
							+ strTempName
							+ "']/parent::tr/td[1]/a[text()='Edit']";
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
				
				if(strFuncResult.equals("")){
					log4j.info("'Edit' link is displayed corresponding to event templates");
					
				}else{
					log4j.info("'Edit' link is NOT Displayed corresponding to event templates");
					strFuncResult="'Edit' link is NOT Displayed corresponding to event templates";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "111919";
			gstrTO = "Verify that a user can be provided a role with 'Maintain Event Template' right";
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
	'Description	:Verify that a user can be provided a role with right �User must update overdue status�
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-March-2013
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                                                Modified By
	'<Date>                                                       <Name>
	**********************************************************************************/
	@Test
	public void testFTS111912() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Date_Time_settings dts = new Date_Time_settings();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral = new General();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		ViewMap objMap = new ViewMap();

		try {
			gstrTCID = "111912";
			gstrTO = "Verify that a user can be provided a role "
					+ "with right �User must update overdue status�";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strSTvalue[] = new String[4];
			String strStatusValue[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strExpHr = "00";
			String strExpMn = "05";

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
			 * 1 Preconditions:
			 * 
			 * 1.Status types 'NST', 'MST', 'SST' and 'TST' are created
			 * selecting expiration time (ex:5min) and role R1 under view
			 * ,update right for status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn,
						false);
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

				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						strNumStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTimeWithoutChangeStatus(seleniumPrecondition,
								strMultiStatType, strStatusName1, strMSTValue,
								strStatTypeColor, strExpHr, strExpMn, "", true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								strMultiStatType, strStatusName2, strMSTValue,
								strStatTypeColor, strExpHr, strExpMn,
								strStatusName1, "", true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
						seleniumPrecondition, strMultiStatType, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType, strStatTypDefn,
						false);
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

				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						strTxtStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType, strStatTypDefn,
						false);
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

				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						strSatuStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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

			/*
			 * 2.Resource type RT is created by selecting status types 'NST',
			 * 'MST', 'SST' and 'TST'.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3.Resource RS is created proving address under RT.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. User U1 is created.
			 */

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");
			/*
			 * 2 Login as RegAdmin and navigate to Setup >> Roles, click on
			 * 'Create New Role'. 'Create Role' page is displayed.
			 */

			/*
			 * 3 Create a role 'R1' by selecting �User must update overdue
			 * status� right from 'Select the Rights for this Role' section.
			 * Role R1 is displayed in 'Roles List' page
			 */
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserMustUpdateOverdueStatus");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Navigate to Setup >>Users and click on the 'Edit' link
			 * associated with user U1,select update right for resource RS,role
			 * R1 from 'User Type & Roles' section and save the page. 'Users
			 * List' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);
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

			/*
			 * 5 Login as user U1 'Status Update prompt' for status types
			 * NST,MST,TST and SST is received.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

				String strSTNames[] = { strNumStatType, strMultiStatType,
						strTxtStatType, strSatuStatType };

				for (String s : strSTNames) {
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.checkUpdateStatPrompt(
								selenium, strResource, s);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Update the statuses NST,MST,SST,TST Statuses of status types
			 * NST,MST,TST and SST are updated appropriately.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[2], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[3]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navToRegionalMapView(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 8 Select resource 'RS' from 'Find Resource' dropdown. The updated
			 * status values are displayed on 'Resource popup window'of resource
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navResPopupWindow(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.navResPopupWindow(selenium, strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMap.verifyTimeCorrespondingUpdTimeInMap(
						selenium, "429");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strUpdtVal = { strStatusName1, "101", "429", "102" };
				for (String s : strUpdtVal) {
					try {
						assertEquals("", strFuncResult);

						strFuncResult = objMap.verifyUpdValInMap(selenium, s);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				}
				if (strFuncResult.equals("")) {

					log4j
							.info("Updated status values for status types NST,MST,TST "
									+ "and SST is displayed on resource popup window of "
									+ "resource RS. ");
				} else {
					log4j
							.info("Updated status values for status types NST,MST,TST "
									+ "and SST is NOT displayed on resource popup window of "
									+ "resource RS. ");
					strFuncResult = "Updated status values for status types "
							+ "NST,MST,TST and SST is NOT displayed on resource "
							+ "popup window of resource RS. ";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource RS is displayed ");

				String strElementID = "//a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {

					log4j
							.info("Resource info pop window of RS is displayed with 'View Info'"
									+ "links. ");
				} else {
					log4j
							.info("Resource info pop window of RS is NOT displayed with 'View Info'"
									+ "links. ");
					strFuncResult = "Resource info pop window of RS is NOT displayed with 'View Info'"
							+ "links. ";
				}

			} catch (AssertionError Ae) {
				log4j.info("Resource RS is NOT displayed ");
				gstrReason = strFuncResult + " Resource RS is NOT displayed ";
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "//a[text()='Update Status']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {

					log4j
							.info("Resource info pop window of RS is displayed with "
									+ "'Update status' links. ");
				} else {
					log4j
							.info("Resource info pop window of RS is NOT displayed with "
									+ "'Update status' links. ");
					strFuncResult = "Resource info pop window of RS is NOT displayed with "
							+ "'Update status' links. ";
				}

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			/*
			 * 8 Wait until the expiration time. User 'U1' receive 'Status
			 * Update prompt' for status types NST,MST,TST and SST.
			 */

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(330000);

				strFuncResult = objGeneral.waitForMailNotification(selenium,
						60,
						"//span[@class='overdue'][text()='Status is Overdue']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTNames[] = { strNumStatType, strMultiStatType,
						strTxtStatType, strSatuStatType };

				for (String s : strSTNames) {
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViews.checkUpdateStatPrompt(
								selenium, strResource, s);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");

			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "111912";
			gstrTO = "Verify that a user can be provided a role with"
					+ " right �User must update overdue status�";
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
	
	//start//testFTS111913//
	/***************************************************************
	'Description	:Verify that a user can be provided a role with right 'Reset Password'
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/17/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS111913() throws Exception {
	try{	
		gstrTCID = "111913";	//Test Case Id	
		gstrTO = " Verify that a user can be provided a role with right 'Reset Password'";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	
		Date_Time_settings dts = new Date_Time_settings();
		String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		gstrTimetake = dts.timeNow("HH:mm:ss");

		String strLoginUserName, strLoginPassword, strRegn,  strInitPwd, strConfirmPwd, strByRole, strByResourceType, strByUserInfo, strNameFormat, strRoleName, strOptions, strRoleValue, strNewPasswd;

		String strFuncResult = "";
		strRoleName="";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		SearchUserByDiffCrteria objSearchUserByDiffCrteria=new SearchUserByDiffCrteria();
		// Search user criteria
		 strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		 strByResourceType = rdExcel.readInfoExcel("User_Template",
				7, 12, strFILE_PATH);
		 strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
				13, strFILE_PATH);
		 strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
				14, strFILE_PATH);

		strInitPwd = rdExcel.readData("Login", 4, 2);
		strConfirmPwd = rdExcel.readData("Login", 4, 2);
		strNewPasswd=rdExcel.readData("Login", 5, 2);
					
		String strUserName1 = "AutoUsr_1" + System.currentTimeMillis();
		String strUsrFulName1 = strUserName1;
		
		String strUserName2 = "AutoUsr_2" + System.currentTimeMillis();
		String strUsrFulName2 = strUserName2;
		
		String strSTvalue[] = new String[1];
		strSTvalue[0] = "";
		
		strRoleName = "AutoR_" + strTimeText;
		String strRoleRights[][] = {};
		strRoleValue = "";
		
		strLoginUserName = rdExcel.readData("Login", 3, 1);
		strLoginPassword = rdExcel.readData("Login", 3, 2);
		strRegn = rdExcel.readData("Login", 3, 4);
		/*
		* STEP :
		  Action:Preconditions:
		1. Active user U1 and inactive user U2 are created.
		  Expected Result:No Expected Result
		*/
		
		log4j
		.info("~~~~~Precondition Execution Starts~~~~~");
		//621802
		strFuncResult =objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1, strInitPwd, strConfirmPwd, strUsrFulName1);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName2, strInitPwd, strConfirmPwd, strUsrFulName2);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName2, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.deactivateUser(seleniumPrecondition, strUserName2, strUsrFulName2, true);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		log4j
		.info("~~~~~Precondition Execution Ends~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to Setup >> Roles, click on 'Create New Role'.
		  Expected Result:'Create Role' screen is displayed.
		*/
		//621803

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.navRolesListPge(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Create a role 'R1' by selecting 'User Reset Password Only' right from 'Select the Rights for this Role' section.
		  Expected Result:Role R1 is displayed in 'Roles List' screen.
		*/
		//621805
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
					strRoleName, strRoleRights, strSTvalue, false,
					strSTvalue, false, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
		    assertEquals("", strFuncResult);
		    strOptions = propElementDetails
		      .getProperty("CreateNewUsr.AdvOptn.UserResetPasswordOnly");
		    strFuncResult = objCreateUsers.advancedOptns(selenium,
		      strOptions, true);
		 } catch (AssertionError Ae) {
		    gstrReason = strFuncResult;
		 }

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.savAndVerifyRoles(selenium, strRoleName);
		}
		catch (AssertionError Ae)
		{
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


		/*
		* STEP :
		  Action:Navigate to Setup>>Users and click on the 'Edit' link associated with user U1 and select the role R1 from 'User Type & Roles' section and save the page.
		  Expected Result:'Users List' screen is displayed.
		*/
		//621806

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Login as user 'U1', Navigate to SetUp >> Users, select include inactive users check box displayed at top right corner.
		  Expected Result:Users 'U1' and 'U2' are listed on 'Users List' screen.
			Option to 'Edit' user is not available.
			All the users in the region are listed with 'password' link associated with them.
		'Create user' button is not available.
		*/
		//621840
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName1, strInitPwd);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.CheckInactiveAndActiveUsers(selenium, strUserName1, strUserName2, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.CheckPasswordPresentAndNotAvailableEditandCreateUserLink(selenium, true, strUserName2, true, true);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		/*
		* STEP :
		  Action:Provide user 'U2' name in search field,click on 'Search.
		  Expected Result:User 'U2' is retrieved,full name of user is striked out.
		*/
		//621842

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.VrfyUserWithSearchUser(selenium,
					strUserName2, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {

		    assertTrue(selenium
		      .isElementPresent("//td[@class='inactive'][contains(text(),'"
		        + strUsrFulName2 + "')]"));

		    log4j.info("full name of user is striked out.");

		   } catch (AssertionError Ae) {
			   gstrReason = "full name of user is not striked out.";

		   }
		/*
		* STEP :
		  Action:Click on password link associated with user 'U2'
		  Expected Result:'Reset user password' screen is displayed with instruction 'To change your password please click here.'
		*/
		//621844
		/*
		* STEP :
		  Action:Click on 'here' link
		  Expected Result:'Set up your Password' screen is displayed.
		*/
		//621845

		/*
		* STEP :
		  Action:Provide a new password (other than previously provided) for 'New Password' and 'Verify Password' fields and click on 'Submit'
		  Expected Result:'User List' screen is displayed.
		*/
		//621846
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.changePasswordInUserList(selenium,
					strNewPasswd, strUserName2);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		* STEP :
		  Action:Click on password link associated with user 'U1'
		  Expected Result:'Reset user password' screen is displayed with instruction 'To change your password please click here.'
		*/
		//621847
		/*
		* STEP :
		  Action:click on 'here' link, Provide a new password (other than previously provided) for 'New Password' and 'Verify Password' fields on 'Set up your Password' screen and click on 'Submit'
		  Expected Result:'User List' screen is displayed.
		*/
		//621849
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objSearchUserByDiffCrteria.searchUserByDifCriteria(selenium,
					strUserName1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.changePasswordInUserList(selenium,
					strNewPasswd, strUserName1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;

		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			gstrResult = "PASS";
		}
		catch (AssertionError Ae)
		{
			gstrResult = "FAIL";
			gstrReason = gstrReason+" "+strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "FTS-111913";
		gstrTO = "Verify that a user can be provided a role with right 'Reset Password'";
		gstrResult = "FAIL";
		log4j.info(e);
		log4j.info("========== Test Case '" + gstrTCID
		+ "' has FAILED ==========");
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("----------------------------------------------------------");
		gstrReason = e.toString();
	   }
	}

	//end//testFTS111913//
	
	//start//testFTS111914//
	/***************************************************************
	'Description		:Verify that a user can be provided a role with right 'View custom view'
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:6/18/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS111914() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		Views objViews=new Views();
		Roles objRoles=new Roles();
		Preferences objPreferences=new Preferences();
		General objGeneral=new General();
		try {
			gstrTCID = "111914"; // Test Case Id
			gstrTO = " Verify that a user can be provided a role with right 'View custom view'";// Test																					// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];
			String strStatusValue[] = new String[2];

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResVal = "";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			

			String strRolesName = "Role" + strTimeText;
			String strRoleValue="";
			
			String strCategory = rdExcel.readInfoExcel("Find Res", 4, 3,
					strFILE_PATH);
			String strRegion = rdExcel.readInfoExcel("Find Res", 4, 4,
					strFILE_PATH);
			String strCityZipCd = rdExcel.readInfoExcel("Find Res", 4, 5,
					strFILE_PATH);
			String strState = rdExcel.readInfoExcel("Find Res", 4, 6,
					strFILE_PATH);

			/*
			 * STEP : Action:Preconditions:
			 * 1.Status types 'NST', 'MST', 'TST', 'SST' are created.
			 * 2.Resource type RT is created by selecting status types 'NST',
			 * 'MST', 'TST', 'SST.
			 * 3.Resource RS is created proving address under RT.
			 * 4. User U1 is created with 'User - Setup User Accounts' right.
			 * Expected Result:No Expected Result
			 */
			// 621851
			
			log4j.info("~~~~~TEST CASE - PRECONDITION EXECUTION STARTS~~~~~");
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status type 'NST', 'MST', 'SST', 'TST' are created.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, strMultiStatType,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName1);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, strMultiStatType, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						seleniumPrecondition, strTSTValue, strTxtStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type 'RT' is created selecting status types 'NST',
			 * 'MST', 'SST', 'TST'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3. Resource 'RS' is created providing address under resource Type
			 * 'RT'.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* 4. User U1 is created with 'User - Setup User Accounts' right.*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);
				
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
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName_1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup >> Roles,
			 * click on 'Create New Role'. Expected Result:'Create Role' page is
			 * displayed.
			 */
			// 621852
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			} 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*
			 * STEP : Action:Create a role 'R1' by selecting 'View Custom View'
			 * right from 'Select the Rights for this Role' section. Expected
			 * Result:Role R1 is displayed in 'Roles List' page.
			 */
			// 621853
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.savAndVerifyRoles(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup>>Users and click on the 'Edit'
			 * link associated with user U1 and select the role R1 from 'User
			 * Type & Roles' section and save the page. Expected Result:'Users
			 * List' page is displayed.
			 */
			// 621854
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
						12, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
						strFILE_PATH);
				
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user U1 and navigate to View >> Custom
			 * Expected Result:'Custom View-Table' screen with following message
			 * is displayed.
			 * 'No Custom View You have not yet created a Custom View
			 * configuration.
			 * Click here to setup your custom view.'
			 */
			// 622212
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.varNoCustomViewErrorMsgInCustomViewTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> User Info Expected
			 * Result:'Update User Info' screen is displayed.
			 */
			// 622213
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navigateToUserInfo(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Default View' drop down Expected
			 * Result:'Custom' view is not available.
			 */
			// 622228
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=option[value='-1002']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Custom option is available under 'Default View' drop down.");
					strFuncResult = "Custom option is  available under 'Default View' drop down.";
					gstrReason = strFuncResult;
				} else {
					log4j.info("Custom option is not available under 'Default View' drop down.");
					strFuncResult="";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'User
			 * List' screen is displayed.
			 */
			// 622232
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link associated with user 'U1'
			 * Expected Result:'Edit User' screen is displayed.
			 */
			// 622234
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Default View' drop down under 'Views'
			 * section Expected Result:'Custom' view is not available.
			 */
			// 622236
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=option[value='-1002']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Custom option is available under 'Default View' drop down.");
					strFuncResult = "Custom option is  available under 'Default View' drop down.";
					gstrReason = strFuncResult;
				} else {
					log4j.info("Custom option is not available under 'Default View' drop down.");
					strFuncResult="";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View>>Custom, Create a custom view
			 * selecting resource RS and status type NST, MST, SST, TST.
			 * Expected Result:Resource 'RS' is displayed under 'RT' along with
			 * status types 'NST', 'MST', 'SST' and 'TST' on 'Custom View -
			 * Table' screen.
			 */
			// 621857
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory,
						strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
			    assertEquals("", strFuncResult);
			    String[] strStatusType = {strNumStatType,strTxtStatType,strSatuStatType,strMultiStatType};
			    strFuncResult = objPreferences.editCustomViewOptionsNew(
						selenium, strStatusType);
			} catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = {strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTablNewMapOrTableOption(selenium,
								strResrctTypName, strResource, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Customize' link. Expected Result:'Edit
			 * Custom View' screen is displayed.
			 */
			// 621858
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navEditCustomViewPageFromViewCustom(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on options, deselect status types 'NST',
			 * 'MST' and 'save'. Expected Result:Resource 'RS' is displayed
			 * under 'RT' along with status types 'SST' and 'TST' on 'Custom
			 * View - Table' screen.
			 */
			// 622246
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTValue[][] = { { strSTvalue[0], "false" },{strSTvalue[1], "false"} };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = {strSatuStatType,strTxtStatType};
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTablNewMapOrTableOption(selenium,
								strResrctTypName, strResource, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized view Expected
			 * Result:'Edit Custom View' screen is displayed.
			 */
			// 622331
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on options, select status types 'NST', 'MST'
			 * and 'save'. Expected Result:Resource 'RS' is displayed under 'RT'
			 * along with status types 'NST', 'MST', 'SST' and 'TST' on 'Custom
			 * View - Table' screen.
			 */
			// 622333
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTValue[][] = { { strSTvalue[0], "true" },{strSTvalue[1], "true"} };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = {strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTablNewMapOrTableOption(selenium,
								strResrctTypName, strResource, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> User Info,Click on
			 * 'Default View' drop down Expected Result:'Custom' option is
			 * available.
			 */
			// 622334
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navigateToUserInfo(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=option[value='-1002']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Custom option is available under 'Default View' drop down.");
					
				} else {
					log4j.info("Custom option is not available under 'Default View' drop down.");
					strFuncResult = "Custom option is not available under 'Default View' drop down.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Users,Click on 'Edit' link
			 * associated with user 'U1' Expected Result:'Edit User' screen is
			 * displayed.
			 */
			// 622335
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
				strUserName_1, strByRole, strByResourceType,
				strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Default View' drop down under 'Views'
			 * section Expected Result:'Custom' option is available.
			 */
			// 622336
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=option[value='-1002']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Custom option is available under 'Default View' drop down.");
					
				} else {
					log4j.info("Custom option is not available under 'Default View' drop down.");
					strFuncResult = "Custom option is not available under 'Default View' drop down.";
					gstrReason = strFuncResult;
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
			gstrTCID = "FTS-111914";
			gstrTO = "Verify that a user can be provided a role with right 'View custom view'";
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

 //end//testFTS111914//
	
//start//testFTS112347//
	/************************************************************************************
	'Description	:Verify that user provided with 'Edit Resources Only' right in a role
	 and without update status right on a resource can create/edit a sub-resource under it.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/2/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                              Modified By
	'Date					                                                  Name
	*************************************************************************************/
	
	@Test
	public void testFTS112347() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		Views objViews = new Views();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "112347"; // Test Case Id
			gstrTO = "Verify that user provided with 'Setup Resources' right in a role and without update "
					+ "status right on any resource can create/edit a sub-resource under it.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relativ
																			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs1_" + strTimeText;
			String strEditSubResource = "EditAutoSRs1_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strEditAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strEditStandResType = "Hospital";
			String strEditContFName = "Editauto";
			String strEditContLName = "Editqsg";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[1];

			// Sub Resource Icon
			String strIconImg = rdExcel.readInfoExcel("ResourceIcon", 2, 2,
					strFILE_PATH);
			String strEditIconImg = rdExcel.readInfoExcel("ResourceIcon", 3, 2,
					strFILE_PATH);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { strStatType1, strStatType2 };
				
			  log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");

		/*
		 * STEP : Action:Precondition: Status type ST1 and ST2 are created in region RG1.
			Resource Type (Normal) RT1 is created selecting status type ST1.
			Resource RS1 created under resource type RT1.
			Sub-Resource SRT1 is created selecting status type ST2.
			Role R1 is created selecting 'Setup Resources' right
			User U1 is created with the following:
			1. 'View Resource' right on resource RS1.
			3. Role 'R1' to view status types ST1 and ST2
			4. View V1 is created selecting ST1, ST2, RS1 
			5. User has configured sub resource selecting 'sRT1' and 'ST2'
	    Expected Result:No Expected Result
		 */
		// 625815

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {
					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTsvalue = { strSTvalue[0], strSTvalue[1] };
				String[] strSTsvalues = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTsvalue, true, strSTsvalues, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRoleName);
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, false, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0], strSTvalue[1] };
				String[] strRSValues = { strRSValue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalues, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strArStatType1, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[1] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								seleniumPrecondition, strSubResrctTypName,
								strRTValue[1], strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("~~~~~TEST_CASE" + gstrTCID + " EXECUTION ENDS~~~~~~~~~~");
				
		/*
		 * STEP : Action:Login as user U1, navigate to Setup >> Resources
		 * Expected Result:Resources RS1 listed on 'Resource List' screen. 
		 */
		// 625816

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
				blnLogin = true;
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
				strFuncResult = objRs.VerifyResource(selenium, strResource1,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : ActionClick on 'Sub-Resources' link corresponding to resource RS1. 
		 * Expected Result:Sub-Resource List for < Resource name (RS1) >' screen is displayed
           'Create New Sub-Resource' button is available. 
		 */
		// 625817
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Sub-Resource' button. 
			 * 	Expected Result:'Create New Sub-Resource for < resource (RS1) >' screen is displayed 
			 */
			// 625889
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.navToCreateSubResourcePage(selenium,
							strResource1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}		
		/*
		 * STEP : Action:Provide mandatory data and click on 'Save' 
		 * Result:	Newly created sub-resource is listed on 'Sub-Resource List for < resource name (RS1) >' screen is displayed.
		 * Data provided while creating are displayed appropriately under the following columns:
			1. Action ('Edit' link is available)
			2. Icon (displayed as selected for standard resource type)
			3. Name
			4. Abbreviation
			5. Resource type (Sub-resource type name) 
		 */
		// 625890
			try {
				assertEquals("", strFuncResult);
				strState = "Alabama";
				strCountry = "Barbour County";
				strFuncResult = objRs.createSubResourceWitLookUPadres(selenium,
						strSubResource, strAbbrv, strSubResrctTypName,
						strStandResType, true, strContFName, strContLName,
						strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifySubResDataInRSListPge(selenium,
						strSubResource, strAbbrv, strSubResrctTypName,
						strIconImg);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			/*
			 * STEP :Action:Navigate to Setup >> Resources 
			 * Result:'Resource List' screen is displayed.
	               '(1)' is displayed for 'Sub-Resource' corresponding to resource RS1. 
			 */
			// 625891

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (1)";
				strFuncResult = objRs.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	

		/*
		 * STEP : Action:Navigate to Views >> V1 and click on resource RS1 
		 *  Expected Result:'View Resource Detail' screen is displayed.
			'Sub Resource' section is displayed
			 where Sub-resource 'SRS1' is NOT displayed under 'sRT1' and 'ST2'.
			'Create New Sub-Resource' button is available. 
		 */
		// 628748

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType2 };
				strFuncResult = objViews
						.verfySubResourceDetailsInViewResDetailFalse(selenium,
								strSubResrctTypName, strStatType,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.vrfySubResourceSectionInViewResDetail(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is available.");
				} else {
					gstrReason = "'Create New Sub-Resource' button is Not available.";
					log4j.info("'Create New Sub-Resource' button is Not available.");
					strFuncResult = "";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP :Action:Navigate to Setup >> Resources 
		 * Result:Edit subresource page of parent resource
		 */
		// 628749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageWithEditLink(
						selenium, strResource1, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Edit all the mandatory data and save. 
		 *  Expected Result:User is directed to the 'View Resource Detail' screen of 'SRS1'.
            All the edited data are retained. 
		 */
		// 628749

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(selenium,
						strEditSubResource, strEditAbbrv, strEditStandResType,
						true, strEditContFName, strEditContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium, strEditSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.verifySubResDataInRSListPge(selenium,
						strEditSubResource, strEditAbbrv, strSubResrctTypName,
						strEditIconImg);
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
			gstrTCID = "FTS-112347";
			gstrTO = "Verify that user provided with 'Setup Resources' right in a role and without update " +
					"status right on any resource can create/edit a sub-resource under it.";// TO
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

 // end//testFTS112347//
	
//start//testFTS112348//
	/************************************************************************************
	'Description	:Verify that user provided with 'Edit Resources Only' right in a role
	 and without update status right on a resource can create/edit a sub-resource under it.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:24/7/2013
	'Author			:QSG
	'------------------------------------------------------------------------------------
	'Modified Date				                                           Modified By
	'Date					                                               Name
	*************************************************************************************/

	@Test
	public void testFTS112348() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRole = new Roles();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "112348"; // Test Case Id
			gstrTO = "Verify that user provided with 'Edit Resources Only' right in a role and without " +
					"update status right on any resource cannot create/edit a sub-resource under it.";// TO

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
																			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs1_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

		/*
		 * STEP : Action:Precondition:Status type ST1 and ST2 are created in region RG1.
			Resource Type (Normal) RT1 is created selecting status type ST1.
			Resource RS1 is are created under resource type RT1.
			Sub-Resource SRT1 is created selecting status type ST2.
			View V1 is created selecting status types ST1 and resource RS1.
			Role 'R1' has 'Edit Resources Only' right
			User U1 is created with the following:
			1. 'View Resource' and 'Associate with' right on resource RS1.
			2. Role 'R1' to view status types ST1 and ST2
			3. View V1 is created selecting ST1, ST2, RS1
			4. User has configured sub resource selecting 'sRT1' and 'ST2
            Expected Result:No Expected Result
		 */
		// 625815

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[0] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SRS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTsvalue = { strSTvalue[0], strSTvalue[1] };
				String[] strSTsvalues = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTsvalue, true, strSTsvalues, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRoleName);
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
			
			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTvalues = { strSTvalue[0], strSTvalue[1] };
				String[] strRSValues = { strRSValue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalues, false, strRSValues);
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						true, false, false, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 6. User has configured sub resource selecting 'sRT1' and 'ST2'
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[1] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								seleniumPrecondition, strSubResrctTypName,
								strRTValue[1], strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
		/*
		 * STEP : Action:Login as user U1, navigate to Setup >> Resources
		 * Expected Result:Resources RS1 is NOT listed on 'Resource List screen.
		 */
		// 625816

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);
				blnLogin = true;
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
				strFuncResult = objRs.VerifyResource(selenium, strResource1,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Views >> V1 Expected Result:Resource
		 * RS1 is displayed under RT1 along with status type ST1
		 */
		// 625889
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1 };
				String[] strResources = { strResource1 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on resource RS1 
		 *  Expected Result:'View Resource Detail' screen is displayed.
          'Create new sub-resource' button is not displayed under sub-resource section. 
		 */
		// 625890
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent(propElementDetails
						.getProperty("CreateResource.ValuesubResource")));
				log4j.info("'Create New Sub-Resource' button is NOT available " +
						"under the 'Sub-Resources' section");
			} catch (AssertionError Ae) {
				log4j.info("'Create New Sub-Resource' button is available " +
						"under the 'Sub-Resources' section");
				gstrReason = strFuncResult
						+ "'Create New Sub-Resource' button is  available " +
						"under the 'Sub-Resources' section";
			}	
		/*
		 * STEP : Action:Click on sub resource 'sRS1' 
		 *  Expected Result:'View Resource Detail' screen of 'sRS' is displayed.
  	  		Link 'edit resource details' is not displayed. 
		 */
		// 628749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToSubResViewResourceDetailPage(
						selenium, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.varEditResourceLink(selenium, false);
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
			gstrTCID = "FTS-112348";
			gstrTO = "Verify that user provided with 'Edit Resources Only' right in a role and without " +
					"update status right on any resource cannot create/edit a sub-resource under it.";
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
// end//testFTS112348//
}
