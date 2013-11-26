package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;


import java.util.Date;
import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;


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
' Description :This class includes NewFeatureFor3Point15 requirement 
'				testcases
' Precondition:
' Date		  :4-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class NewFeatureFor3Point15  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.NewFeatureFor3Point15");
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
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
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
	'Description	:Verify that user created selecting the right 'Form - User may 
	'				 create and modify forms' can configure forms.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:22-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS91752() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Forms objForms=new Forms();
		General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-91752 ";
			gstrTO = "Verify that user created selecting the right 'Form - "
					+ "User may create and modify forms' can configure forms.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");


			String strLoginUserName = "";// Admin login user name
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strOptions = "";

			// User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			String strFormTempTitleNFExisting = "NFE" + strTimeText;
			String strFormTempTitleNF ="NF" +strTimeText;
			String strFormTempTitleOFExisting = "OFE" +strTimeText;
			String strFormTempTitleOF ="OF" + strTimeText;
			String strFormDesc =  "AutoDescription";

			String strQuestion="";
			String strDescription ="";
			
			String strPreForm="";
			
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			
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

				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleNFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleNFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Create old form
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleOFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleOFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/* 1 Login as RegAdmin. No Expected Result */

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

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Navigate to 'Setup >> Users' and click on 'Create New User'.
			 * 'Create New User' screen is displayed.
			 */
			/*
			 * 3 Provide mandatory data and select the right 'Form - User may
			 * create and modify forms' and save. User U1 is created.
			 */
			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			// Logout as region admin
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is available.
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

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

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

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Click on 'Configure Forms'. 'Form Configuration' page is
			 * displayed. 'Create New Form Template' button is available.
			 */
			
			/*
			 * 7 Create a new form NF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("'Create New Form Template' button is available. ");
				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strFormTempTitleNFExisting
							+ "']/parent::tr/td[1]/a[text()='Edit']";
				
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				log4j.info("'Create New Form Template' button is NOT available. ");
				strFuncResult="'Create New Form Template' button is NOT available. ";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Existing forms are displayed with the options"
						+ " 'Edit' and 'Questionnaire'. ");
				log4j
						.info("Form is created and displayed in 'Form Configuration'"
								+ " screen with 'Edit' options. ");

				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strFormTempTitleNFExisting
						+ "']/parent::tr/td[1]/a[text()='Questionnaire']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Existing forms are NOT displayed with the options"
						+ " 'Edit' and 'Questionnaire'. ");
				strFuncResult = "Form is NOT created and displayed in 'Form Configuration'"
						+ " screen with 'Edit' options";
				gstrReason = strFuncResult;
				log4j
						.info("Form is NOT created and displayed in 'Form Configuration'"
								+ " screen with 'Edit'options. ");
			}

		

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Form is created and displayed in 'Form Configuration'"
								+ " screen with 'Questionnaire' options. ");
			} catch (AssertionError Ae) {
				strFuncResult = "Form is NOT created and displayed in 'Form Configuration'"
						+ " screen with 'Questionnaire' options";
				gstrReason = strFuncResult;
				log4j
						.info("Form is NOT created and displayed in 'Form Configuration'"
								+ " screen with  'Questionnaire' options. ");
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Create a new form NF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			try {
				assertEquals("", strFuncResult);
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				
				gstrReason = strFuncResult;
				
			}
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleNF = "EDIT" + "NF" + strFormTempTitleNF;
				strPreForm=strFormTempTitleNF;
				
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 11 Create a old form OF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			/*
			 * 12 Edit the name, description of OF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "EDIT" + strFormTempTitleOF;
				strFormDesc = "EDIT"+strFormDesc;
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
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

				
				strFuncResult = objForms.navToEditQuestion(selenium, strQuestion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Edit the questionnaire Q1 (add/remove/edit questions). The
			 * updated data is displayed when clicked on 'Questionnaire'.
			 */

			try {
				assertEquals("", strFuncResult);

				strQuestion="EDIT"+strQuestion;
				strDescription="EDIT"+strDescription;
				
				strFuncResult = objForms.CreateQuestionFlds(selenium,
						strFormTempTitleOF, strQuestion, strDescription, false, false);
				
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
			gstrTCID = "BQS-91752";
			gstrTO = "Verify that user created selecting the right 'Form - "
					+ "User may create and modify forms' can configure forms.";
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
	  'Description  :Edit a user to deselect the role R1 which was created
	  '              selecting the right 'Form - User may create and 
	  '              modify forms' and verify that user cannot configure forms.
	  'Precondition :1. Role R1 is created selecting the right 
	                 "Form - User may create and modify forms".
	                 2. User U1 is created selecting role R1.
	                 3. The right 'Form - User may create and
	                    modify forms' is not selected for user U1. 
	 'Arguments    :None 
	 'Returns      :None 
	 'Date         :4-May-2012 
	 'Author       :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date                             Modified By '
	 * <Date>                                     <Name>
	 ***************************************************************/

	@Test
	public void testBQS21520() throws Exception {

		boolean blnLogin = false;// keep track of logout of application

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		// CreateUsers
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();// Object
																							// of class
																							// SearchUserByDiffCrteria
		Roles objRoles=new Roles();
		Forms objForms=new Forms();

		try {
			gstrTCID = "BQS-21520 ";
			gstrTO = "Edit a user to deselect the role R1 which was "
				+ "created selecting the right "
				+ "'Form - User may create and modify forms' "
				+ "and verify that user cannot configure forms.  ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			

			String strLoginUserName = "";// Admin login user name
			String strLoginPassword = "";

			String strRegn = rdExcel.readData("Login", 3, 4);

			String strOptions = "";

			// User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

		
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";
			
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			/* 1 Login as RegAdmin. No Expected Result */
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
			
			/*
			 * 2 Create a role R1 without selecting the right 'Form - User may
			 * create and modify forms'. No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition, strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);

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
			

			/*
			 * 2 Navigate to 'Setup >> Users' and click on 'Create New User'.
			 * 'Create New User' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Provide mandatory data and select the right 'Form - User may
			 * create and modify forms' and save. User U1 is created.
			 */
			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/* 2 Login as RegAdmin. No Expected Result */
			
			log4j
			.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j
			.info("~~~~~TEST CASE " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Selecting Region
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to 'Setup >> Users' and click on 'Edit' associated
			 * with U1. 'Edit User' screen is displayed.
			 */
			
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

			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 3 Navigate to 'Setup >> Users' and click on 'Edit' associated
			 * with U1. 'Edit User' screen is displayed.
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}

			/*4 	Deselect the role R1 and save. 		No Expected Result */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, false);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is not available.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}
		
			
			/*
			 * 5 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is not available.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Hover the mouse over 'Form' tab. 'Configure Forms' option is
			 * not available in the dropdown.
			 */
			
			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);
				

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseHover(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);
				gstrResult = "PASS";
				
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}

			
			
		} catch (Exception e) {
			gstrTCID = "BQS-21520";
			gstrTO = "Edit a user to deselect the role R1 which was "
					+ "created selecting the right "
					+ "'Form - User may create and modify forms' "
					+ "and verify that user cannot configure forms.  ";
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
	'Description	:Edit a user and select the right "Form - User may create and 
	'				 modify forms" and verify that user can configure forms.
	'Precondition	:User U1 is created without selecting the right 'Form - User 
	'				may create and modify forms'. (within test case).
	'Arguments		:None
	'Returns		:None
	'Date	 		:24-May-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS91754() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Forms objForms=new Forms();
		
		try {
			gstrTCID = "BQS-91754 ";
			gstrTO = "Edit a user and select the right 'Form - User may create "
					+ "and modify forms' and verify that user can configure forms.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			

			String strLoginUserName = "";// Admin login user name
			String strLoginPassword = "";

			String strRegn = rdExcel.readData("Login", 3, 4);

			String strOptions = "";

			// User mandatory fields
			String strUserName = "auto" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = strUserName + "fulName";


			// Search user criteria
			String 	strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);


			String strFormTempTitleNFExisting = "NFE" + strTimeText;
			String strFormTempTitleNF ="NF"+strTimeText;
			String strFormTempTitleOF ="OF"+strTimeText;
			String strFormDesc =  "AutoDescription";

			String strQuestion="";
			String strDescription ="";
			
			String strPreForm="";
			
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
	
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

				// Data for creating user
				
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

				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleNFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleNFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j
					.info("~~~~~PRE-CONDITION" + gstrTCID
							+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseHover(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError a) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError a) {
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
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.UserCreateModifyForms");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
				
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		
			// Logout as region admin
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
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

			/*
			 * 4 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is available.
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

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

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
			 * 6 Click on 'Configure Forms'. 'Form Configuration' page is
			 * displayed. 'Create New Form Template' button is available.
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Create a new form NF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			try {
				assertEquals("", strFuncResult);
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				
				gstrReason = strFuncResult;
				
			}
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleNF = "EDIT" + "NF" + strFormTempTitleNF;
				strPreForm=strFormTempTitleNF;
				
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 11 Create a old form OF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			/*
			 * 12 Edit the name, description of OF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "EDIT" + strFormTempTitleOF;
				strFormDesc = "EDIT"+strFormDesc;
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
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

				
				strFuncResult = objForms.navToEditQuestion(selenium, strQuestion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Edit the questionnaire Q1 (add/remove/edit questions). The
			 * updated data is displayed when clicked on 'Questionnaire'.
			 */

			try {
				assertEquals("", strFuncResult);

				strQuestion="EDIT"+strQuestion;
				strDescription="EDIT"+strDescription;
				
				strFuncResult = objForms.CreateQuestionFlds(selenium,
						strFormTempTitleOF, strQuestion, strDescription, false, false);
				
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
			gstrTCID = "BQS-91754";
			gstrTO = "Edit a user and select the right 'Form - User may create and modify forms'"
					+ " and verify that user can configure forms.";
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
	'Description	:Create a role, add the 'Form - User may create and modify forms'
	'				 right to the role, create a user by selecting that role and verify that the user
	'				 can configure forms.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:16-July-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS91755() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Forms objForms=new Forms();
		Roles objRoles=new Roles();
		General objGeneral=new General();

		try {
			gstrTCID = "BQS-91755 ";
			gstrTO = "Create a role, add the 'Form - User may create and modify" +
					" forms' right to the role, create a user by selecting" +
					" that role and verify that the user can configure forms.";
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

			String strOptions = "";

			// User mandatory fields
			String strUserName = "auto" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = strUserName + "fulName";

			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			
			
			String strFormTempTitleNFExisting = "NFE" + strTimeText;
			String strFormTempTitleNF ="NF" +strTimeText;
			String strFormTempTitleOFExisting = "OFE" +strTimeText;
			String strFormTempTitleOF ="OF" + strTimeText;
			String strFormDesc =  "AutoDescription";

			String strQuestion="";
			String strDescription ="";
			
			String strPreForm="";
			
			
			
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			
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

				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleNFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleNFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Create old form
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleOFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleOFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/* 1 Login as RegAdmin. No Expected Result */

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

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3 Create a role 'R1' by selecting 'Edit Resource only' right from
			 * 'Select the Rights for this Role' section. Role R1 is displayed
			 * in 'Roles List' page.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRoleName);
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

			/*
			 * 2 Navigate to 'Setup >> Users' and click on 'Create New User'.
			 * 'Create New User' screen is displayed.
			 */
			/*
			 * 3 Provide mandatory data and select the right 'Form - User may
			 * create and modify forms' and save. User U1 is created.
			 */
			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
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
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Logout as region admin
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is available.
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

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

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

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Click on 'Configure Forms'. 'Form Configuration' page is
			 * displayed. 'Create New Form Template' button is available.
			 */
			
			/*
			 * 7 Create a new form NF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("'Create New Form Template' button is available. ");
				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strFormTempTitleNFExisting
							+ "']/parent::tr/td[1]/a[text()='Edit']";
				
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				log4j.info("'Create New Form Template' button is NOT available. ");
				strFuncResult="'Create New Form Template' button is NOT available. ";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Existing forms are displayed with the options"
						+ " 'Edit' and 'Questionnaire'. ");
				log4j
						.info("Form is created and displayed in 'Form Configuration'"
								+ " screen with 'Edit' options. ");

				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strFormTempTitleNFExisting
						+ "']/parent::tr/td[1]/a[text()='Questionnaire']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Existing forms are NOT displayed with the options"
						+ " 'Edit' and 'Questionnaire'. ");
				strFuncResult = "Form is NOT created and displayed in 'Form Configuration'"
						+ " screen with 'Edit' options";
				gstrReason = strFuncResult;
				log4j
						.info("Form is NOT created and displayed in 'Form Configuration'"
								+ " screen with 'Edit'options. ");
			}

		

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Form is created and displayed in 'Form Configuration'"
								+ " screen with 'Questionnaire' options. ");
			} catch (AssertionError Ae) {
				strFuncResult = "Form is NOT created and displayed in 'Form Configuration'"
						+ " screen with 'Questionnaire' options";
				gstrReason = strFuncResult;
				log4j
						.info("Form is NOT created and displayed in 'Form Configuration'"
								+ " screen with  'Questionnaire' options. ");
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Create a new form NF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			try {
				assertEquals("", strFuncResult);
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				
				gstrReason = strFuncResult;
				
			}
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleNF = "EDIT" + "NF" + strFormTempTitleNF;
				strPreForm=strFormTempTitleNF;
				
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 11 Create a old form OF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			/*
			 * 12 Edit the name, description of OF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "EDIT" + strFormTempTitleOF;
				strFormDesc = "EDIT"+strFormDesc;
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
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

				
				strFuncResult = objForms.navToEditQuestion(selenium, strQuestion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Edit the questionnaire Q1 (add/remove/edit questions). The
			 * updated data is displayed when clicked on 'Questionnaire'.
			 */

			try {
				assertEquals("", strFuncResult);

				strQuestion="EDIT"+strQuestion;
				strDescription="EDIT"+strDescription;
				
				strFuncResult = objForms.CreateQuestionFlds(selenium,
						strFormTempTitleOF, strQuestion, strDescription, false, false);
				
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
			gstrTCID = "BQS-91755";
			gstrTO = "Create a role, add the 'Form - User may create and modify forms'"
					+ " right to the role, create a user by selecting that role and verify"
					+ " that the user can configure forms.";
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
	'Description	:Create a role, create a user by selecting that role and then add
	 'Form - User may create and modify forms' right to the role, and verify that the
	  user can configure forms.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:01-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS91756() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Forms objForms=new Forms();
		General objGeneral=new General();
		Roles objRoles=new Roles();
		
		try {
			gstrTCID = "BQS-91756 ";
			gstrTO = "Create a role, create a user by selecting that role and then add" +
					" 'Form - User may create and modify forms' right to the role, and" +
					" verify that the user can configure forms.";
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

			String strOptions = "";

			// User mandatory fields
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";

			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			
			
			String strFormTempTitleNFExisting = "NFE" + strTimeText;
			String strFormTempTitleNF ="NF" +strTimeText;
			String strFormTempTitleOFExisting = "OFE" +strTimeText;
			String strFormTempTitleOF ="OF" + strTimeText;
			String strFormDesc =  "AutoDescription";

			String strQuestion="";
			String strDescription ="";
			
			String strPreForm="";
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			
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

				strFuncResult = objForms.navToFormConfig(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleNFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleNFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Create old form
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(seleniumPrecondition,
						strFormTempTitleOFExisting, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, false,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectUsersForForm(seleniumPrecondition, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.selectResourcesForForm(seleniumPrecondition, "",
						strFormTempTitleOFExisting);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/* 1 Login as RegAdmin. No Expected Result */

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

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3 Create a role 'R1' by selecting 'Edit Resource only' right from
			 * 'Select the Rights for this Role' section. Role R1 is displayed
			 * in 'Roles List' page.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRoleName);
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

			/*
			 * 2 Navigate to 'Setup >> Users' and click on 'Create New User'.
			 * 'Create New User' screen is displayed.
			 */
			/*
			 * 3 Provide mandatory data and select the right 'Form - User may
			 * create and modify forms' and save. User U1 is created.
			 */
			try {
				assertEquals("", strFuncResult);

				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				strInitPwd = rdExcel.readData("Login", 4, 2);
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

				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
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

			// Logout as region admin
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is available.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseHover(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*6 	Login as U1 and click on 'Form' tab. 		'Configure Forms' option is not available.*/
			

			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);

				strFuncResult = objForms
						.ConfigFormsElementPresentByMouseClick(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*7 	Hover the mouse over 'Form' tab. 		'Configure Forms' option is not available. */

			try {
				assertEquals("'Configure Forms' option "
						+ "is NOT available in the dropdown.", strFuncResult);

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/* 8 Login as RegAdmin. No Expected Result */
			
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
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
		
				strFuncResult = objRoles.navEditRolesPge(selenium, strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 9 Edit the role R1 and select the right 'Form - User may create
			 * and modify forms'. No Expected Result
			 */
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
		
				strFuncResult = objRoles.savAndVerifyRoles(selenium, strRoleName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 10 Login as U1 and click on 'Form' tab. 'Configure Forms' option
			 * is available.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* 8 Login as RegAdmin. No Expected Result */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;

				strFuncResult = objLogin.login(selenium, strUserName, strInitPwd);

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
				log4j.info("'Create New Form Template' button is available. ");
				String strElementID="//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
							+ strFormTempTitleNFExisting
							+ "']/parent::tr/td[1]/a[text()='Edit']";
				
				strFuncResult = objGeneral.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				log4j.info("'Create New Form Template' button is NOT available. ");
				strFuncResult="'Create New Form Template' button is NOT available. ";
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Existing forms are displayed with the options"
						+ " 'Edit' and 'Questionnaire'. ");
				log4j
						.info("Form is created and displayed in 'Form Configuration'"
								+ " screen with 'Edit' options. ");

				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2][text()='"
						+ strFormTempTitleNFExisting
						+ "']/parent::tr/td[1]/a[text()='Questionnaire']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Existing forms are NOT displayed with the options"
						+ " 'Edit' and 'Questionnaire'. ");
				strFuncResult = "Form is NOT created and displayed in 'Form Configuration'"
						+ " screen with 'Edit' options";
				gstrReason = strFuncResult;
				log4j
						.info("Form is NOT created and displayed in 'Form Configuration'"
								+ " screen with 'Edit'options. ");
			}

		

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Form is created and displayed in 'Form Configuration'"
								+ " screen with 'Questionnaire' options. ");
			} catch (AssertionError Ae) {
				strFuncResult = "Form is NOT created and displayed in 'Form Configuration'"
						+ " screen with 'Questionnaire' options";
				gstrReason = strFuncResult;
				log4j
						.info("Form is NOT created and displayed in 'Form Configuration'"
								+ " screen with  'Questionnaire' options. ");
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Create a new form NF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			try {
				assertEquals("", strFuncResult);
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				
				gstrReason = strFuncResult;
				
			}
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleNF = "EDIT" + "NF" + strFormTempTitleNF;
				strPreForm=strFormTempTitleNF;
				
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleNF, strFormDesc, strFormActiv,
						strComplFormDel, false, false, false, false, true,
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
						strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 11 Create a old form OF1. Form is created and displayed in 'Form
			 * Configuration' screen with 'Edit', 'Questionnaire' options.
			 */
			/*
			 * 12 Edit the name, description of OF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objForms.navTocreateNewFormTemplate(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "OF" + System.currentTimeMillis();
				strFormDesc = "AutoDescription";
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";
				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Edit the name, description of form NF1. The updated data is
			 * displayed in the list of 'Form Configuration' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToEditFormTemplate(selenium,
						strFormTempTitleOF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFormTempTitleOF = "EDIT" + strFormTempTitleOF;
				strFormDesc = "EDIT"+strFormDesc;
				String strFormActiv = "User Initiate & Other To Fill Out";
				String strComplFormDel = "User To Individual Resources";

				strFuncResult = objForms.fillAllFieldsInCreateNewForm(selenium,
						strFormTempTitleOF, strFormDesc, strFormActiv,
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
						strFormTempTitleOF);
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

				
				strFuncResult = objForms.navToEditQuestion(selenium, strQuestion);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Edit the questionnaire Q1 (add/remove/edit questions). The
			 * updated data is displayed when clicked on 'Questionnaire'.
			 */

			try {
				assertEquals("", strFuncResult);

				strQuestion="EDIT"+strQuestion;
				strDescription="EDIT"+strDescription;
				
				strFuncResult = objForms.CreateQuestionFlds(selenium,
						strFormTempTitleOF, strQuestion, strDescription, false, false);
				
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
			gstrTCID = "BQS-91756";
			gstrTO = "Create a role, create a user by selecting that role and then" +
					" add 'Form - User may create and modify forms' right to the role," +
					" and verify that the user can configure forms.";
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
