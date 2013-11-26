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

/*******************************************************************
' Description :This class includes EditResourceType requirement 
'				testcases
' Date		  :16-Jan-2013
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSCreateTextStatusType {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditResourceType");
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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

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
		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	//start//testFTS124973//
	/***************************************************************
	'Description		:Verify that user can update a text status type with blank value.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/22/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS124973() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objST = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objView = new Views();

		try {
			gstrTCID = "124973"; // Test Case Id
			gstrTO = " Verify that user can update a text status type with blank value.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strStatusTypeValue = "Text";

			String statTypeName = "AutoTST_" + System.currentTimeMillis();
			String strStatTypDefn = "Auto Test Status Type";

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strSTvalue[] = new String[1];
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRSValue[] = new String[1];

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strPassword = "abc123";
			String strUsrFulName = "autouser";

			
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strTxtUpdateValue = "";
			String strComments = "Comments";
			String strUpdTime = "";
			/*
			 * STEP : Action:1.Text status type 'TST'' created without selecting
			 * 'Check if blank values are NOT allowed' checkbox <br>2. 'TST' is
			 * associated with 'RT' and a resource 'RS' is created under 'RT'
			 * <br>3. View 'V1' is created selecting 'TST' and 'RS' <br>4. User
			 * 'A' has update right on resource 'RS' <br>5. Navigate to View >>
			 * V1,Attempt to update TST with blank value Expected Result:TST is
			 * updated with blank value. <br> <br>Updated time is displayed when
			 * mouse is hovered on status type.
			 */
			// 661420
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
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
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResType);
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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResType, strContFName, strContLName, strState,
						strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objView
						.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objView.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue);

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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strPassword,
						strPassword, strUsrFulName);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
					strPassword);

			String strSnapTime = selenium.getText("css=#statusTime");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView.fillUpdStatusNSTWithComments(selenium,
						strTxtUpdateValue, strSTvalue[0], strComments);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strNSTUpdTimeBef = rdExcel.readInfoExcel("Timer", 2, 2,
					strFILE_PATH);
			strNSTUpdTimeBef = strSnapTime + strNSTUpdTimeBef;

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objView.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource, statTypeName, strTxtUpdateValue,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComments);
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
			gstrTCID = "FTS-124973";
			gstrTO = "Verify that user can update a text status type with blank value.";
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

	// end//testFTS124973//
	//start//testFTS124972//
	/***************************************************************
	'Description		:Verify that a text status type cannot be updated with the blank status value when the option 'Check if blank values are NOT allowed' is selected.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:8/24/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS124972() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objST = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objView = new Views();

		try {
			gstrTCID = "124972"; // Test Case Id
			gstrTO = " Verify that a text status type cannot be updated with the blank status value when the option 'Check if blank values are NOT allowed' is selected.";// Test
																																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			//String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "AutoTST_" + System.currentTimeMillis();
			String strStatTypDefn = "Auto Test Status Type";
			String strStatusTypeValue = "Text";
			String strStatValue = "";
			String[] strSTvalue = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strRoleValue = "";
			String strResVal = "";
			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strRolesName_1 = "Role_1" + strTimeText;

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strPassword = "abc123";
			String strUsrFulName = "autouser";

			String strTxtUpdateValue = "";
			String strComments = "Comments";

			//String strResType = "AutoRt_" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Autauga County";

			/*
			 * STEP : Action:1.Text status type 'TST'' created selecting 'Check
			 * if blank values are NOT allowed' checkbox <br>2. 'TST' is
			 * associated with 'RT' and a resource 'RS' is created under 'RT'
			 * <br>3. View 'V1' is created selecting 'TST' and 'RS' <br>4. User
			 * 'A' has update right on resource 'RS' <br>5. Navigate to View >>
			 * V1,Attempt to update TST with blank value Expected Result:An
			 * appropriate error message should be displayed,status should not
			 * be updated with blank value.
			 */
			// 661419

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

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
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectDeSelRequired(seleniumPrecondition,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
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
						strResrctTypName, strContFName, strContLName, strState,
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

			// View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView
						.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objView.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue);

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

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User

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
						seleniumPrecondition, strUserName, strPassword,
						strPassword, strUsrFulName);

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue, true);

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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
					strPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objView.fillUpdStatusNSTWithComments(selenium,
						strTxtUpdateValue, strSTvalue[0], strComments);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objST.vfyErrMsgForBlankTextStatTypes(selenium,
						statTypeName);

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
			gstrTCID = "FTS-124972";
			gstrTO = "Verify that a text status type cannot be updated with the blank status value when the option 'Check if blank values are NOT allowed' is selected.";
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

	// end//testFTS124972//
	

	// start//testFTS70458//
	/***************************************************************
	 * 'Description 	:Verify that user can reset value upon expiration for a text
	 *               	status type which is set to expire at a expiration time.
	 *  'Precondition 	:
	 *  'Arguments		:None
	 *  'Returns		:None
	 *  'Date			:9/11/2013 
	 *  'Author			:QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date                           Modified By
	 * 'Date                                    Name
	 ***************************************************************/

	@Test
	public void testFTS70458() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		General objGeneral = new General();
		try {
			gstrTCID = "70458"; // Test Case Id
			gstrTO = " Verify that user can reset value upon expiration for a text status type which is set to expire at a expiration time.";// Test
																																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String strStatusTxtValue = "Text";
			String statTypeName1 = "Tst_1" + strTimeText;
			String strResrcTypName1 = "Resty_1" + strTimeText;
			String strStatTypDefn = "Def";
			String strStandResType = "Aeromedical";
			String strResource1 = "Res_1" + strTimeText;

			String strAbbrv = "Abb";
			String strContFName = "FN";
			String strContLName = "LN";
			String strState = "Alabama";
			String strCounty = "Barbour County";
			String strRoleName = "Rol_1" + strTimeText;
			String strUserName = "User" + System.currentTimeMillis();
			String strUsrFulName = "UserName";
			String strViewName = "View_1" + strTimeText;
			String strVewDescription = "ViewDes" + strTimeText;
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			String strSection = "Sec_1" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRoleValue[] = new String[1];
			String strRTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			/*
			 * STEP : Action:Precondition: 1. Test user has created a text
			 * status type 'TST' where the checkbox for 'Reset Value' is checked
			 * and expiration time of 5 Min is provided. 2. 'TST' is associated
			 * with 'RT' and a resource 'RS' is created under 'RT' 3. Resource
			 * View 'V1' is created selecting 'TST' and 'RS' 4. Section 'S1' is
			 * created for status type 'TST' 5. User 'A' has update right on
			 * resource 'RS' 6. Role 'R' is assigned to user 'A' which has
			 * update right on 'TST' Expected Result:No Expected Result
			 */
			// 426687

			// Login
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strExpHr = "00";
				String strExpMn = "05";
				strFuncResult = objStatusTypes
						.selectSTypesAndFilMandFldsWithExTime(
								seleniumPrecondition, strStatusTxtValue,
								statTypeName1, strStatTypDefn, strExpHr,
								strExpMn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.SelectAndDeselectResetValueForST(seleniumPrecondition,
								true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName1);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadresNew(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCounty, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[0] };
				String[][] strRoleRightss = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRightss,
						updateRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating U1 with associate rights

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
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource1, false, true, false,
						true);
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

			// Creating V1
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
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeName = { statTypeName1 };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeName, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as user 'A' and navigate to 'View >> V1' and
			 * update the status of 'TST' Expected Result:The status value and
			 * comments are updated on the view screen
			 */
			// 426689

			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
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
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "CommentTST";
				String strUpdateValue = "101";
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdateValue, strSTvalue[0], strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "CommentTST";
				String strUpdateValue = "101";
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource1, statTypeName1, strUpdateValue,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Wait for the Expiration time. Refresh the View 'V1'
			 * screen at the Expiration time Expected Result:Status value is
			 * reset to blank (--) along with the comments.
			 */
			// 426703
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(350000);
				strFuncResult = objGeneral.refreshPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "";
				String strUpdateValue = "--";
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource1, statTypeName1, strUpdateValue,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the resource 'RS' Expected Result:'View
			 * Resource Detail' screen is displayed. Status value is reset to
			 * blank (--) along with the comments.
			 */
			// 426721

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
				String strComments = "";
				String strUpdtVal = "--";
				strFuncResult = objViews.verifyUpdSTValWithCommentsInResDetail(
						selenium, strSection, statTypeName1, strUpdtVal,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'View >> Map' and click on the resource
			 * icon of 'RS' Expected Result:Resource info pop up window is
			 * displayed. Status value is reset to blank (--)
			 */
			// 426723

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdtVal = "0";
				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strUpdtVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the link 'Update Status' Expected
			 * Result:Update Status screen is displayed where the status value
			 * is displayed as '0' and Comments field is cleared.
			 */
			// 426730

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "";
				String strUpdateValue = "";
				strFuncResult = objViews.verUpdSTvalueInUpdateStatusPage(
						selenium, strUpdateValue, strSTvalue[0], strComments);
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
			gstrTCID = "FTS-70458";
			gstrTO = "Verify that user can reset value upon expiration for a text status type which is set to expire at a expiration time.";
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

	// end//testFTS70458//
	

	//start//testFTS70459//
	/***************************************************************
	'Description		:Verify that user can reset value upon expiration for a text status type which is set to expire at shift time.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/12/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				             Modified By
	'Date					                 Name
	***************************************************************/

	@Test
	public void testFTS70459() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		General objGeneral = new General();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "70459"; // Test Case Id
			gstrTO = " Verify that user can reset value upon expiration for a text status type which is set to expire at shift time.";// Test
																																		// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			// ST
			String strStatusTxtValue = "Text";
			int intShiftTime = Integer.parseInt(propEnvDetails
					.getProperty("ShiftTime"));
			String StatusTime = "";
			String strUpdTime_Shift = "";
			String statTypeName1 = "Tst_1" + strTimeText;
			String strStatTypDefn = "Def";
			String strSTvalue[] = new String[1];

			// RT
			String strResrcTypName1 = "Resty_1" + strTimeText;
			String strRTvalue[] = new String[1];

			// RS
			String strStandResType = "Aeromedical";
			String strResource1 = "Res_1" + strTimeText;
			String strAbbrv = "Abb";
			String strContFName = "FN";
			String strContLName = "LN";
			String strState = "Alabama";
			String strCounty = "Barbour County";
			String strRSValue[] = new String[1];
			// Role
			String strRoleName = "Rol_1" + strTimeText;
			String strRoleValue[] = new String[1];
			// User
			String strUserName = "User" + System.currentTimeMillis();
			String strUsrFulName = "UserName";
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
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
			// View
			String strViewName = "View_1" + strTimeText;
			String strVewDescription = "ViewDes" + strTimeText;
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// Section
			String strSection = "Sec_1" + strTimeText;

			/*
			 * STEP : Action:Precondition: 1. Test user has created a text
			 * status type 'TST' where the checkbox for 'Reset Value' is checked
			 * and Shift time of is provided. 2. 'TST' is associated with 'RT'
			 * and a resource 'RS' is created under 'RT' 3. Resource View 'V1'
			 * is created selecting 'TST' and 'RS' 4. Section 'S1' is created
			 * for status type 'TST' 5. User 'A' has update right on resource
			 * 'RS' 6. Role 'R' is assigned to user 'A' which has update right
			 * on 'TST' Expected Result:No Expected Result
			 */
			// 426760

			// Login
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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

			// Creating ST
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
						seleniumPrecondition, strStatusTxtValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.SelectAndDeselectResetValueForST(seleniumPrecondition,
								true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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

				strFuncResult = objStatusTypes.editStatusTypePage(
						seleniumPrecondition, statTypeName1, strStatusTxtValue);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes
							.provideShiftTimeForStatusTypes(
									seleniumPrecondition, strStatusTime[0],
									strStatusTime[1]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName1, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName1);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating RS

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadresNew(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName1, strContFName, strContLName, strState,
						strCounty, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
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
				String[] updateRightValue = { strSTvalue[0] };
				String[][] strRoleRightss = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRightss,
						updateRightValue, true, updateRightValue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating U1 with associate rights

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
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource1, false, true, false,
						true);
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

			// Creating V1
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
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeName = { statTypeName1 };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeName, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as user 'A' and navigate to 'View >> V1' and
			 * update the status of 'TST' Expected Result:The status value and
			 * comments are updated on the view screen
			 */
			// 426761
			log4j.info("~~~~~TEST CASE  - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
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
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "CommentTST";
				String strUpdateValue = "101";
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strUpdateValue, strSTvalue[0], strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "CommentTST";
				String strUpdateValue = "101";
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource1, statTypeName1, strUpdateValue,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Wait for the Shift time. <br>Refresh the View 'V1'
			 * screen at the Shift time Expected Result:Status value is reset to
			 * blank (--) along with the comments.
			 */
			// 426762

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(350000);
				strFuncResult = objGeneral.refreshPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "";
				String strUpdateValue = "--";
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource1, statTypeName1, strUpdateValue,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the resource 'RS' Expected Result:'View
			 * Resource Detail' screen is displayed. Status value is reset to
			 * blank (--) along with the comments.
			 */
			// 426763

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
				String strComments = "";
				String strUpdtVal = "--";
				strFuncResult = objViews.verifyUpdSTValWithCommentsInResDetail(
						selenium, strSection, statTypeName1, strUpdtVal,
						strComments);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to 'View >> Map' and click on the resource
			 * icon of 'RS' Expected Result:Resource info pop up window is
			 * displayed. Status value is reset to blank (--)
			 */
			// 426764

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdtVal = "0";
				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strUpdtVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the link 'Update Status' Expected
			 * Result:Update Status screen is displayed where the status value
			 * is displayed as '0' and Comments field is cleared.
			 */
			// 426765

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strComments = "";
				String strUpdateValue = "";
				strFuncResult = objViews.verUpdSTvalueInUpdateStatusPage(
						selenium, strUpdateValue, strSTvalue[0], strComments);
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
			gstrTCID = "FTS-70459";
			gstrTO = "Verify that user can reset value upon expiration for a text status type which is set to expire at shift time.";
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

	// end//testFTS70459//
}