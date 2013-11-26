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
' Description :This class includes EditNewFormQuestionnaire requirement 
'				testcases
' Precondition:
' Date		  :22-Aug-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FirefoxEditNewFormQuestionnaire  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditNewFormQuestionnaire");
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
		gstrBrowserName = "Firefox 4.0.1";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("BrowserFirefox").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersionFirefox");
		
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		Paths_Properties objPaths_Properties = new Paths_Properties();
		propElementAutoItDetails = objPaths_Properties.ReadAutoit_FilePath();
		
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
						.getProperty("urlEU"));

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
		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}

	/********************************************************************************
	'Description	:Verify that 'Text' question of an new form questionnaire can be edited.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:22-Aug-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS69380() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
													  // CreateUsers
		Forms objForms=new Forms();
		//General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-69380 ";
			gstrTO = "Verify that 'Text' question of an new form questionnaire can be edited.";
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


			//Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			
			String strFormTempTitleNF ="NF" +strTimeText;
			String strFormDesc =  "AutoDescription";
		
			//Basic Information
			String strNFQuestTitl = "NFQBT" + strTimeText;
			String strDesc = "Descritption";
			String strAbbr = "Abb";
			String strInstructn = "Instruction";
			String strLabl = "Text";
			

			String strTextTitl = "Text" + strTimeText;
			String strTextAbb = "Tabb";
			String strTextLength = "26";
			
			String strEditTextTitl = "Edit" + strTextTitl;

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

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserNameA, strInitPwd, strConfirmPwd, strUsrFulNameA);

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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToFormConfig(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Create New form
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
						false, false);
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
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

		
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 2 Login as RegAdmin and navigate to Form>>Configure Forms. Form
			 * F1 is displayed in 'Form Configuration' screen.
			 */
			
			
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
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/"
									+ "td[2][text()='"
									+ strFormTempTitleNF
									+ "']"));

					log4j.info("Form F1 " + strFormTempTitleNF
							+ " is displayed in 'Form Configuration' screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("Form F1 "
									+ strFormTempTitleNF
									+ " is NOT displayed in 'Form Configuration' screen. ");
					strFuncResult = "Form F1 "
							+ strFormTempTitleNF
							+ " is NOT displayed in 'Form Configuration' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 3 Click on the 'Questionnaire' link associated with form F1.
			 * 'EMSystem Form Creator' window is popped up.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objForms.navToAddQestnPageOfNF(selenium, strFormTempTitleNF);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Edit the mandatory data for 'Text' question Q and click on
			 * 'Preview Form' button. The changes for 'Text' question Q are
			 * updated in 'Review Your Changes' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);

				
				strFuncResult = objForms
						.fillMandTextDataAndVerifyInRevwUrChangsPage(selenium,
								strEditTextTitl);
				selenium.selectWindow("");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Click on 'Save Form' button. 'Form Configuration' screen is
			 * displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				assertEquals("Form Configuration", selenium
						.getText(propElementDetails.getProperty("Header.Text")));

				log4j.info("Form Configuration page is displayed");

			} catch (AssertionError Ae) {

				strFuncResult = "Form Configuration  page is NOT displayed"
						+ Ae;
				log4j.info("Form Configuration  page is NOT displayed" + Ae);
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserNameA + "/" + strInitPwd,
						"",
						strFormTempTitleNF,
						"Login as user A and navigate to Form>>Activate forms,"
								+ " click on 'Fill Form' link associated with form F1.",
						strNFQuestTitl, strTextTitl, strEditTextTitl };

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
			gstrTCID = "BQS-69380";
			gstrTO = "Verify that 'Text' question of an new form questionnaire can be edited.";
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
