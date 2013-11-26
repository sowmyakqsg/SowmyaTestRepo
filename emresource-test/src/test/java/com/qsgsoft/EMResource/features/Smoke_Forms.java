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
' Description       :This class includes  requirement testcases
' Requirement Group :Smoke Test Suite
' Requirement       :Forms 
' Date		        :22-Aug-2012
' Author	        :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class Smoke_Forms {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Smoke_Forms");
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
		gstrBrowserName = "Firefox 4.0.1";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("BrowserFirefox")
				.substring(1)
				+ " "
				+ propEnvDetails.getProperty("BrowserVersionFirefox");

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

	/********************************************************************************
	'Description	:Create a form (New form) to be filled out by himself and completed
	'				 form to be received by Users, activate and fill out the form and 
	'				 verify that the completed form and the report displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:27-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                   <Name>
	**********************************************************************************/

	@Test
	public void testSmoke85333() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();

		try {
			gstrTCID = "85333";
			gstrTO = "Create a form (New form) to be filled out by himself and"
					+ " completed form to be received by Users, activate and "
					+ "fill out the form and verify that the completed form ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleNF = "NF" + strTimeText;
			String strFormDesc = "AutoDescription";

			// Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";

		 log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			
			/*2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";
				
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				
				
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Fill Out Him/Herself";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms.fillAllFieldsInCreateNewFormWithDifOptions(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, true, true, true, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleNF, strUserNameA, true,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. 'Text' question Q has been added to the questionnaire of New
			 * form F1.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToAddQestnPageOfNF(selenium, strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
						strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
						strLabl);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnTextInfo(selenium,
						strTextTitl, strTextAbb, strTextLength, true, true);
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "
								+ strUserNameB + "/" + strInitPwd, "",
						strFormTempTitleNF, "Verify After 7th step",
						strNFQuestTitl, strTextTitl };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "SmokeForms");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85333";
			gstrTO = "Create a form (New form) to be filled out by himself and"
					+ " completed form to be received by Users, activate and "
					+ "fill out the form and verify that the completed form "
					+ "and the report displays correct data.";
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
	'Description	:Create a form (OLD form) to be filled out by himself and completed 
	'	           form to be received by Users, activate and fill out the form and verify
	'			   that the completed form and the report displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:29-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85332() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();

		try {
			gstrTCID = "85332";
			gstrTO = "Create a form (OLD form) to be filled out by himself and"
					+ " completed form to be received by Users, activate and fill "
					+ "out the form and verify that the completed form and the "
					+ "report displays correct data. ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormDesc = "AutoDescription";

			String strQuestion = "";
			String strDescription = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

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

			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */

			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Fill Out Him/Herself";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms
						.fillAllFieldsInCreateNewFormWithDifOptions(selenium,
								strFormTempTitleOF, strFormDesc, strFormActiv,
								strComplFormDel, true, true, true, false,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameA, true, true,
						false);
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
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}

			/*
			 * 13 Add a questionnaire Q1 (add questions) to the form OF1. The
			 * added questions are displayed correctly when clicked on
			 * 'Questionnaire'.
			 */
			try {
				assertEquals("", strFuncResult);

				strQuestion = "Q" + System.currentTimeMillis();
				strDescription = "Description";
				String strquesTypeID = "Free text field";

				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "
								+ strUserNameB + "/" + strInitPwd, "",
						strFormTempTitleOF, "Verify After 7th step",
						strQuestion };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85332";
			gstrTO = "Create a form (OLD form) to be filled out by himself and completed"
					+ " form to be received by Users, activate and fill out the form and "
					+ "verify that the completed form and the report displays correct data.";
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
	'Description	:Create a form (NEW form) to be sent to others and completed form
	'				 to be received by Users and Resources, activate and fill out the 
	'				 form and verify that the completed form displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:27-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85331() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();

		try {
			gstrTCID = "85331";
			gstrTO = "Create a form (NEW form) to be sent to others and "
					+ "completed form to be received by Users and Resources,"
					+ " activate and fill out the form and verify that the "
					+ "completed form displays correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			String strUserNameC = "Usr_C" + System.currentTimeMillis();
			String strUsrFulNameC = strUserNameC;

			String strUserNameD = "Usr_D" + System.currentTimeMillis();
			String strUsrFulNameD = strUserNameD;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleNF = "NF" + strTimeText;
			String strFormDesc = "AutoDescription";

			// Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statNumTypeName = "NST" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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

				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 1. User B is created */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User D is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameD,
								strInitPwd, strConfirmPwd, strUsrFulNameD);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameD, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameC,
								strInitPwd, strConfirmPwd, strUsrFulNameC);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, true, true,
						true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameC, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

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

			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */

			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Users And Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, true, true, true, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(selenium,
						strUserNameB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(selenium, "",
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleNF, strUserNameA, true, true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. 'Text' question Q has been added to the questionnaire of New
			 * form F1.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToAddQestnPageOfNF(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
						strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
						strLabl);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnTextInfo(selenium,
						strTextTitl, strTextAbb, strTextLength, true, true);
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "
								+ strUserNameB + "/" + strInitPwd + " and  "
								+ strUserNameC + "/" + strInitPwd + " and  "
								+ strUserNameD + "/" + strInitPwd,strResource,
						strFormTempTitleNF, "Verify From 9th step",
						strNFQuestTitl, strTextTitl };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85331";
			gstrTO = "Create a form (NEW form) to be sent to others and completed"
					+ " form to be received by Users and Resources, activate and"
					+ " fill out the form and verify that the completed form"
					+ " displays correct data.";
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
	'Description	:Create a form (OLD form) to be sent to others and completed form 
	'				 to be received by Users and Resources, activate and fill out the
	'				 form and verify that the completed form displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:29-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85330() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Forms objForms=new Forms();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes=new StatusTypes();
		
		try {
			gstrTCID = "85330";
			gstrTO = "Create a form (OLD form) to be sent to others and completed" +
					" form to be received by Users and Resources, activate and " +
					"fill out the form and verify that the completed form displays" +
					" correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;
			
			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;
			
			String strUserNameC = "Usr_C" + System.currentTimeMillis();
			String strUsrFulNameC = strUserNameC;
			
			String strUserNameD = "Usr_D" + System.currentTimeMillis();
			String strUsrFulNameD = strUserNameD;


			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			
			String strFormTempTitleOF ="OF" +strTimeText;
			String strFormDesc =  "AutoDescription";
		
			String strQuestion="";
			String strDescription ="";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String statNumTypeName="NST"+strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";
			
			String strStatValue = "";
			
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue="";
			

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(selenium,
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
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium, strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*1. User A is created selecting 'Form - User may activate forms' right. */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ActivateForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
	
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*1. User B is created*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameB, strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*2. User D is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameD, strInitPwd, strConfirmPwd, strUsrFulNameD);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";
				
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameD, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				
				
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameC, strInitPwd, strConfirmPwd, strUsrFulNameC);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";
				
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, true, true,
						true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameC, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				
				
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Users And Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
						strComplFormDel, true, true, true, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(selenium, strUserNameB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(selenium, "",
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameA, true,
						true, false);
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
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
		
			/*
			 * 13 Add a questionnaire Q1 (add questions) to the form OF1. The
			 * added questions are displayed correctly when clicked on
			 * 'Questionnaire'.
			 */
			try {
				assertEquals("", strFuncResult);


				strQuestion="Q"+System.currentTimeMillis();
				strDescription="Description";
				String strquesTypeID="Free text field";
				
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "
								+ strUserNameB + "/" + strInitPwd + " and  "
								+ strUserNameC + "/" + strInitPwd + " and  "
								+ strUserNameD + "/" + strInitPwd,strResource,
								strFormTempTitleOF, "Verify form 9th step",
								strQuestion};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "SmokeForms");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85330";
			gstrTO = "Create a form (OLD form) to be sent to others and completed" +
					" form to be received by Users and Resources, activate and " +
					"fill out the form and verify that the completed form displays" +
					" correct data.";
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
	'Description	:Create a form (OLD form) to be activated on a numeric status 
	'				 change, select user U1 to fill out the form and verify that the 
	'				 completed form and the report displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                 <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85336() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "85336 ";
			gstrTO = "Create a form (OLD form) to be activated on a numeric status change,"
					+ " select user U1 to fill out the form and verify that the completed "
					+ "form and the report displays correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormDesc = "AutoDescription";

			String strQuestion = "";
			String strDescription = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statNumTypeName = "NST" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRoleName = "Roles" + strTimeText;
			String strRoleVal = "";

			String strWhenToSend = "Whenever status is above";
			String strNumericLmtValue = "10";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Number status type NST is created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Resource RS is created with address under resource type RT is
			 * associated status type NST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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

				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleRights = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 4. User A has role to update status type NST and update resource
			 * right on resource RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

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
			
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "As Certain Status Changes";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms.fillAllFieldsInCreateNewFormWitoutPageVef(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
						strComplFormDel, true, true, true, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectSatusTypesForFormNSTAndSST(selenium,
						strSTvalue, strWhenToSend, strNumericLmtValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForFormNew(selenium, strUserNameB);
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
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
		
			/*
			 * 13 Add a questionnaire Q1 (add questions) to the form OF1. The
			 * added questions are displayed correctly when clicked on
			 * 'Questionnaire'.
			 */
			try {
				assertEquals("", strFuncResult);


				strQuestion="Q"+System.currentTimeMillis();
				strDescription="Description";
				String strquesTypeID="Free text field";
				
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameA, false,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "+ strUserNameB + "/" + strInitPwd,
						strResource,
						strFormTempTitleOF,
						"Verify from 10th step",
						strQuestion, 
						statNumTypeName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85336";
			gstrTO = "Create a form (OLD form) to be activated on a numeric status change,"
					+ " select user U1 to fill out the form and verify that the completed "
					+ "form and the report displays correct data.";
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
	'Description	:Create a form (New form) to be activated on a numeric status change,
	 select user U1 to fill out the form and verify that the completed form and the report
	  displays correct data
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85337() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "85337";
			gstrTO = "Create a form (New form) to be activated on a numeric status"
					+ " change, select user U1 to fill out the form and verify that the"
					+ " completed form and the report displays correct data";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleNF = "NF" + strTimeText;
			String strFormDesc = "AutoDescription";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statNumTypeName = "NST" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Automation";

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRoleName = "Roles" + strTimeText;
			String strRoleVal = "";

			String strWhenToSend = "Whenever status is above";
			String strNumericLmtValue = "10";

			// Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Number status type NST is created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Resource RS is created with address under resource type RT is
			 * associated status type NST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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

				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleRights = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 4. User A has role to update status type NST and update resource
			 * right on resource RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

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

			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */

			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "As Certain Status Changes";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms
						.fillAllFieldsInCreateNewFormWitoutPageVef(selenium,
								strFormTempTitleNF, strFormDesc, strFormActiv,
								strComplFormDel, true, true, true, false, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms
						.selectSatusTypesForFormNSTAndSST(selenium, strSTvalue,
								strWhenToSend, strNumericLmtValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForFormNew(selenium,
						strUserNameB);
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

				strFuncResult = objForms.navToAddQestnPageOfNF(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
						strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
						strLabl);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnTextInfo(selenium,
						strTextTitl, strTextAbb, strTextLength, true, true);
				selenium.selectWindow("");
				Thread.sleep(5000);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleNF, strUserNameA, false,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				
				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "+ strUserNameB + "/" + strInitPwd,
						strResource,
						strFormTempTitleNF, "Verify from 10th step",
						strNFQuestTitl, statNumTypeName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85337";
			gstrTO = "Create a form (New form) to be activated on a numeric status"
					+ " change, select user U1 to fill out the form and verify that "
					+ "the completed form and the report displays correct data";
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
	'Description	:Create a form (OLD form) to be activated on a multi status change,
	'				 select user U1 to fill out the form and verify that the completed
	'				 form and the report displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                               <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85338() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "85338 ";
			gstrTO = "Create a form (OLD form) to be activated on a numeric status change,"
					+ " select user U1 to fill out the form and verify that the completed "
					+ "form and the report displays correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormDesc = "AutoDescription";

			String strQuestion = "";
			String strDescription = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statMultiTypeName="MST"+strTimeText;
			String strMSTValue = "Multi";
			String strStatTypDefn = "Automation";
			
			String strStatTypeColor="Black";
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRoleName = "Roles" + strTimeText;
			String strRoleVal = "";
			
		log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Number status type NST is created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(selenium,
						statMultiTypeName);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statMultiTypeName, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statMultiTypeName, strStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, statMultiTypeName, strStatusName1);
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
						selenium, statMultiTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5. Resource RS is created with address under resource type RT is
			 * associated status type NST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

		/*
		 * 4. User A has role to update status type NST and update resource
		 * right on resource RS.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

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
					
		/*
		 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
		 * Configuration' screen is displayed.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */
			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "As Certain Status Changes";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms.fillAllFieldsInCreateNewFormWitoutPageVef(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
						strComplFormDel, true, true, true, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatusVal = { strStatusValue[0], strStatusValue[1] };
				strFuncResult = objForms.selectSatusTypesForFormMST(selenium,
						strSTvalue, strStatusVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(selenium,
						strUserNameB);
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
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			/*
			 * 13 Add a questionnaire Q1 (add questions) to the form OF1. The
			 * added questions are displayed correctly when clicked on
			 * 'Questionnaire'.
			 */
			try {
				assertEquals("", strFuncResult);
				strQuestion="Q"+System.currentTimeMillis();
				strDescription="Description";
				String strquesTypeID="Free text field";
				
				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);		
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameA, false,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				
				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "+ strUserNameB + "/" + strInitPwd,
						strResource,
						strFormTempTitleOF,
						"Verify from 10th step",
						strQuestion,
						statMultiTypeName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "SmokeForms");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85338";
			gstrTO = "Create a form (OLD form) to be activated on a multi status change," +
					" select user U1 to fill out the form and verify that the completed" +
					" form and the report displays correct data.";
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
	'Description	:Create a form (New form) to be activated on a multi status change,
	 select user U1 to fill out the form and verify that the completed form and the report
	  displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85339() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "85339 ";
			gstrTO = "Create a form (New form) to be activated on a multi status change,"
					+ " select user U1 to fill out the form and verify that the completed "
					+ "form and the report displays correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleNF = "NF" + strTimeText;
			String strFormDesc = "AutoDescription";

			// Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statMultiTypeName = "MST" + strTimeText;
			String strMSTValue = "Multi";
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRoleName = "Roles" + strTimeText;
			String strRoleVal = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Number status type NST is created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						selenium, statMultiTypeName, strStatusName1);
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
						selenium, statMultiTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Resource RS is created with address under resource type RT is
			 * associated status type NST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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

				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[][] strRoleRights = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 4. User A has role to update status type NST and update resource
			 * right on resource RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

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

			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */

			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "As Certain Status Changes";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms
						.fillAllFieldsInCreateNewFormWitoutPageVef(selenium,
								strFormTempTitleNF, strFormDesc, strFormActiv,
								strComplFormDel, true, true, true, false, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatusVal = { strStatusValue[0], strStatusValue[1] };
				strFuncResult = objForms.selectSatusTypesForFormMST(selenium,
						strSTvalue, strStatusVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForFormNew(selenium,
						strUserNameB);
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

				strFuncResult = objForms.navToAddQestnPageOfNF(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
						strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
						strLabl);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.newFormQuestnTextInfo(selenium,
						strTextTitl, strTextAbb, strTextLength, true, true);
				selenium.selectWindow("");
				Thread.sleep(5000);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleNF, strUserNameA, false,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "+ strUserNameB + "/" + strInitPwd,
						strResource,
						strFormTempTitleNF,
						"Verify from 10th step",
						strNFQuestTitl,
						statMultiTypeName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85339";
			gstrTO = "Create a form (New form) to be activated on a multi status change,"
					+ " select user U1 to fill out the form and verify that the completed "
					+ "form and the report displays correct data.";
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
	'Description	:Create a form (OLD form) to be activated on a saturation score 
	'				 status change, select user U1 to fill out the form and verify 
	'				 that the completed form and the report displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85340() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "85340 ";
			gstrTO = "Create a form (OLD form) to be activated on a saturation score"
					+ " status change, select user U1 to fill out the form and verify "
					+ "that the completed form and the report displays correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleOF = "OF" + strTimeText;
			String strFormDesc = "AutoDescription";

			String strQuestion = "";
			String strDescription = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statSaturatTypeName = "SST" + strTimeText;
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRoleName = "Roles" + strTimeText;
			String strRoleVal = "";

			String strWhenToSend = "Whenever status is above";
			String strNumericLmtValue = "10";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Number status type NST is created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5. Resource RS is created with address under resource type RT is
			 * associated status type NST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 4. User A has role to update status type NST and update resource
			 * right on resource RS.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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

			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */

			try {
				assertEquals("", strFuncResult);
				String strFormActiv = "As Certain Status Changes";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms
						.fillAllFieldsInCreateNewFormWitoutPageVef(selenium,
								strFormTempTitleOF, strFormDesc, strFormActiv,
								strComplFormDel, true, true, true, false,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.selectSatusTypesForFormNSTAndSST(selenium, strSTvalue,
								strWhenToSend, strNumericLmtValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(selenium,
						strUserNameB);
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
				strFuncResult = objForms.navToCreateNewQuestion(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 13 Add a questionnaire Q1 (add questions) to the form OF1. The
			 * added questions are displayed correctly when clicked on
			 * 'Questionnaire'.
			 */
			try {
				assertEquals("", strFuncResult);
				strQuestion = "Q" + System.currentTimeMillis();
				strDescription = "Description";
				String strquesTypeID = "Free text field";

				strFuncResult = objForms.ToCreateQuestion(selenium,
						strFormTempTitleOF, strQuestion, strDescription,
						strquesTypeID, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleOF, strUserNameA, false,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "+ strUserNameB + "/" + strInitPwd,
						strResource,
						strFormTempTitleOF,
						"Verify from 10th step",
						strQuestion,
						statSaturatTypeName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85340";
			gstrTO = "Create a form (OLD form) to be activated on a saturation score status "
					+ "change, select user U1 to fill out the form and verify that the completed"
					+ " form and the report displays correct data.";
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
	'Description	:Create a form (New form) to be activated on a saturation score 
	'				status change, select user U1 to fill out the form and verify that
	'				 the completed form and the report displays correct data.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:6-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'25-Feb-2013                                 <Name>
	**********************************************************************************/
	

	@Test
	public void testSmoke85341() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();
		Forms objForms = new Forms();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		ResourceTypes objRT = new ResourceTypes();
		Roles objRoles = new Roles();

		try {
			gstrTCID = "85341";
			gstrTO = "Create a form (New form) to be activated on a saturation score "
					+ "status change, select user U1 to fill out the form and verify "
					+ "that the completed form and the report displays correct data.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// User mandatory fields
			String strUserNameA = "Usr_A" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulNameA = strUserNameA;

			String strUserNameB = "Usr_B" + System.currentTimeMillis();
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			String strFormTempTitleNF = "NF" + strTimeText;
			String strFormDesc = "AutoDescription";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String statSaturatTypeName = "SST" + strTimeText;
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";

			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strRoleName = "Roles" + strTimeText;
			String strRoleVal = "";

			String strWhenToSend = "Whenever status is above";
			String strNumericLmtValue = "10";

			// Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Number status type NST is created. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Resource RS is created with address under resource type RT is
			 * associated status type NST.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

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
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleVal = objRoles.fetchRoleValueInRoleList(selenium,
						strRoleName);
				if (strRoleVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. User A is created selecting 'Form - User may activate forms'
			 * right.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.RepFormDetail");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			/*
			 * 4. User A has role to update status type NST and update resource
			 * right on resource RS.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 2. User B is created by proving email and pager address. */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strFirstName = "";
				String strMiddleName = "";
				String strLastName = "";
				String strOrganization = "";
				String strPhoneNo = "";
				String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strEMail = rdExcel.readData("WebMailUser", 2, 1);
				String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
				String strAdminComments = "";

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
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

			/*
			 * 2 Login as RegAdmin and navigate to Form>>>Configure Forms. 'Form
			 * Configuration' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Click on 'Create New Form Template' button and create a form
			 * selecting:
			 * 
			 * 1. 'User initiate and Fill out Him/Herself' for 'Form Activation'
			 * and 'User to individual users' for 'Completed form delivery'
			 * 
			 * 2. Select "New Form" Check box (To create a new form)
			 * 
			 * 3. Select to receive completed form via email, pager and web
			 * 
			 * 4. Click on 'Next' 'Form Configuration' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1 Preconditions:
			 * 
			 * 1. A form F1 is configured as NEW form type (i.e. selecting the
			 * check box 'New Form'), selecting 'User Initiate & Fill out
			 * Him/Herself' for 'Form Activation' and 'User to Individual Users'
			 * for 'Completed Form Delivery' and also select to receive
			 * completed form via email, pager and web.
			 */

			try {
				assertEquals("", strFuncResult);
				String strFormActiv = "As Certain Status Changes";
				String strComplFormDel = "User To Individual Users";
				strFuncResult = objForms
						.fillAllFieldsInCreateNewFormWitoutPageVef(selenium,
								strFormTempTitleNF, strFormDesc, strFormActiv,
								strComplFormDel, true, true, true, false, true,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.selectSatusTypesForFormNSTAndSST(selenium, strSTvalue,
								strWhenToSend, strNumericLmtValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.selectUsersForFormNew(selenium,
						strUserNameB);
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
				strFuncResult = objForms.navToAddQestnPageOfNF(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.newFormQuestnBasicInfo(selenium,
						strNFQuestTitl, strDesc, strAbbr, strInstructn, true,
						strLabl);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.newFormQuestnTextInfo(selenium,
						strTextTitl, strTextAbb, strTextLength, true, true);
				selenium.selectWindow("");
				Thread.sleep(5000);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navFormSecuritySettingPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.addSecurityRightToIndividualUsers(
						selenium, strFormTempTitleNF, strUserNameA, false,
						true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd + " and  "+ strUserNameB + "/" + strInitPwd,
						strResource,
						strFormTempTitleNF,
						"Verify from 10th step",
						strNFQuestTitl,
						statSaturatTypeName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath,
						"SmokeForms");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "85341";
			gstrTO = "Create a form (New form) to be activated on a saturation score "
					+ "status change, select user U1 to fill out the form and verify "
					+ "that the completed form and the report displays correct data.";
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
