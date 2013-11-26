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

/************************************************************************************
 * ' Description :This class contains test cases from CopyRegionView requirement
 * ' Requirement :Copy Region View 
 * ' Requirement Group :Viewing & managing EMResource entities on the 'View'interface
 * ' Product      :EMResource Base '
 ************************************************************************************/

public class CopyRegionView {
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CopyRegionView");
	
	static{
		BasicConfigurator.configure();
	}
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;

	String gstrTCID, gstrTO, gstrResult, gstrReason;

	Selenium selenium,seleniumFirefox,seleniumPrecondition;

	public Properties propElementDetails;
	public Properties propEnvDetails;
	public Properties pathProps;
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	private String browser;
	double gdbTimeTaken; 

	public static Date gdtStartDate;

	private String json;
	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;

	/****************************************************************************************************************
	 * This function is called the setup() function which is executed before
	 * every test.
	 * 
	 * The function will take care of creating a new selenium session for every
	 * test
	 * 
	 ****************************************************************************************************************/
	@Before
	public void setUp() throws Exception {

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		// Retrieve browser information
		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {

			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.json,
					propEnvDetails.getProperty("urlEU"));

		} else {
			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.browser,
					propEnvDetails.getProperty("urlEU"));

		}
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		browser = propEnvDetails.getProperty("BrowserFirefox");
		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, this.browser, propEnvDetails
				.getProperty("urlEU"));
		
		// Start Selenium RC
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
	
		
		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	/****************************************************************************************************************
	 * * This function is called the teardown() function which is executed after
	 * every test.
	 * 
	 * The function will take care of stopping the selenium session for every
	 * test and writing the execution result of the test.
	 * 
	 ****************************************************************************************************************/
	@After
	public void tearDown() throws Exception {

		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		seleniumFirefox.stop();
		
		try {
			selenium.close();
		} catch (Exception e) {

		}
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
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

		// Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		// Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		// Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		// Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason=gstrReason.replaceAll("'", " ");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}

	/***********************************************************************************
	 * 'Description :Verify that a View can be created by copying from an existing View. 
	 *  'Arguments  :None 
	 *  'Returns    :None 
	 *  'Date       :29-05-2012 
	 *  'Author     :QSG
	 * '--------------------------------------------------------------------------------
	 * 'Modified Date
	 *  Modified By 
	 ***********************************************************************************/

	@Test
	public void testBQS69824() throws Exception {
		
		String strFailMsg = "";
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ReadData objReadData = new ReadData();
		Login objLogin = new Login();// object of class Login
		Views objView = new Views();
		Views objViews = new Views();
		StatusTypes objST = new StatusTypes();
		Roles objRole = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "69824"; // Test Case Id
			gstrTO = " Verify that a View can be created by copying from an existing View.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTmText = dts.getCurrentDate("HHmm");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
		
			String strMSTValue = "Multi";
			String strNSTValue = "Number";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strNumStat = "Aa1_" + strTimeText;
			String strMulStat = "Aa2_" + strTimeText;
			String strTextStat = "Aa3_" + strTimeText;
			String strSatuStat = "Aa4_" + strTimeText;
			String strStatTypDefn = "Auto";
		
			String[] strStatTypeArr = { strNumStat, strMulStat, strTextStat,
					strSatuStat };
			String strStatValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];
			String strRSTvalue[] = new String[4];
			
			String strResource = "AutoRs_" + strTimeText;
			String strRSValue[] = new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			//String strPassword="abc123";
			//String strFullUserName="Full"+strUserName;
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strRoleValue = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strSection = "AB_" + strTimeText;

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strMulStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNumStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strTextStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strSatuStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strRSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (int intST = 0; intST < strRSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(seleniumPrecondition,
							strRSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strFailMsg.compareTo("") != 0) {

					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFailMsg = "";
					strRSValue[0] = strResVal;
				} else {
					strFailMsg = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strRSTvalue, true,
						strRSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleName);

				if (strRoleValue.compareTo("") != 0) {
					strFailMsg = "";

				} else {
					strFailMsg = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strRSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumFirefox, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navRegionViewsList(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews
						.navToEditResDetailViewSections(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			// String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			/*
			 * STEP : Action:Preconditions: <br> <br>1. Role based status types
			 * NST (Number), MST(Multi), SST (saturation score), TST (Text) are
			 * created. <br>2. NST , MST, SST, TST status types are moved to
			 * section SEC1. <br>3. Resource type RT is created selecting NST ,
			 * MST, SST, TST. <br>4. Resource RS is created under RT. <br>5.
			 * User U1 is created selecting the following rights: <br> <br>a.
			 * View Resource and Update Resource right on RS. <br>b. Role R1 to
			 * view and update NST , MST, SST, TST status types. <br>c.
			 * 'Configure Region View' right. <br>6. View V1 is created
			 * selecting RS, NST, MST, TST and SST. Expected Result:No Expected
			 * Result
			 */

			String strArResource[] = { strResource };
			String strNewViewName = "AutoView" + System.currentTimeMillis();


			// 417102

			/*
			 * STEP : Action:Login as user U1, navigate to Setup >> Views, click
			 * on 'Copy' link next to V1. Expected Result:'Edit View' screen is
			 * displayed. <br> <br>NST, MST, TST and SST check boxes are
			 * selected under 'Select Status types' screen. <br> <br>RS check
			 * box is selected under 'Select Resources' screen.
			 */

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objView.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objView.navToEditViewByCopyView(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (String strStat : strRSTvalue) {
				try {
					assertTrue(strFailMsg.equals(""));
					strFailMsg = objView.verifyStatTypeAndResInEditView(
							selenium, "statusTypeID", strStat, true);
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFailMsg;
				}

			}
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objView.verifyStatTypeAndResInEditView(selenium,
						"resourceID", strRSValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			// 417117

			/*
			 * STEP : Action:Provide view name as V2 and save Expected
			 * Result:The view V2 is listed on the 'Region Views List' screen.
			 */

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objView.fillMandFieldsInEditViewAndVerifyView(
						selenium, strNewViewName, strVewDescription,
						strViewType);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			// 417118

			/*
			 * STEP : Action:Navigate to view >> V2. Expected Result:Resource RS
			 * is displayed under RT along with status types NST, MST, SST and
			 * TST. <br> <br>The view V2 is same as view V1
			 */
			// 417119

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objView.navToUserView(selenium, strNewViewName);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objView.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strArResource, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFailMsg;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69824";
			gstrTO = "Verify that a View can be created by copying from an existing View.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

}
