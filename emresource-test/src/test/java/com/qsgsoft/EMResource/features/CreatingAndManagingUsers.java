package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/***********************************************************************
 * ' Description       :This class contains test cases from  requirement
 * ' Requirement       :Creating and Managing Users 
 * ' Requirement Group :Resource Hierarchies
 * ' Product           :EMResource v3.21 
 * ' Date              :6/27/2013 
 * ' Author            :QSG '
 **********************************************************************/

public class CreatingAndManagingUsers {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CreateUser");
	static{
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
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

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
			selenium.close();
		} catch (Exception e) {

		}
		try {

			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		selenium.stop();
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

	// start//testBQS107191//
	/***************************************************************
	 * 'Description :Verify that sub-resources are not listed while editing users. 
	 * 'Precondition : 
	 * 'Arguments :None 
	 * 'Returns :None 
	 * 'Date :6/27/2013
	 * 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By 'Date Name
	 ***************************************************************/

	@Test
	public void testBQS107191() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "107191"; // Test Case Id
			gstrTO = " Verify that sub-resources are not listed while editing users.";// Test
			// Objective
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

			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;

			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[2];

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

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Precondition: Status type ST1 and ST2 are created
			 * in region RG1. Resource Type (Normal) RT1 is created selecting
			 * status type ST1. Resource RS1 is created selecting RT1
			 * Sub-Resource Type 'SRT1' is created selecting status type 'ST2'.
			 * Sub-resource 'SRS1' is created under parent resource 'RS1'
			 * selecting Sub-Resource type 'SRT1'. User 'U1' is created.
			 * Expected Result:No Expected Result
			 */
			// 609120
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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
			// RS
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
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT
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
			// SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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
			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 609121
			try {
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
				blnLogin = true;
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
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'User
			 * List' screen is displayed. User 'U1' is listed under it,with
			 * a.Edit b.Password c.Regions d.Switch links associated with it
			 * under 'Action' column.
			 */
			// 609122
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyActionFieldForUser(
						selenium, strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Edit' link associated with user 'U1'
			 * Expected Result:'Edit User' screen is displayed. Data provided
			 * while creting user is retained in all fields.
			 */
			// 609123
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
				strFuncResult = objCreateUsers.vrfyUserFields(selenium,
						strUsrFulName_1, "", "", "", "", "", "", "", "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Scroll down till 'Resource Rights' section.
			 * Expected Result:Sub-resource 'SRS1' is not listed under 'Resource
			 * Rights' section.
			 */
			// 609124
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySubResourceInResRights(
						selenium, strSubResource, false);
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
			gstrTCID = "BQS-107191";
			gstrTO = "Verify that sub-resources are not listed while editing users.";
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

	// end//testBQS107191//
	//start//testBQS107190//
	/***************************************************************
	'Description		:Verify that sub-resources are not listed while creating users.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/1/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	 ***************************************************************/

	@Test
	public void testBQS107190() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "107190"; // Test Case Id
			gstrTO = " Verify that sub-resources are not listed while creating users.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";


			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative
			// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;

			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";

			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;

			String strRTValue[] = new String[2];
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strResVal = "";
			String strRSValue[] = new String[2];

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Precondition:
			 * 1. Status type 'ST1' and 'ST2' are created in region RG1.
			 * 2. Resource Type (Normal) 'RT1' is created selecting status type 'ST1'.
			 * 3. Resource 'RS1' is created selecting 'RT1'
			 * 4. Sub-Resource Type 'SRT1' is created selecting status type 'ST2'.
			 * 5. Sub-resource 'SRS1' is created under parent resource 'RS1'
			 * selecting Sub-Resource type 'SRT1'. Expected Result:No Expected
			 * Result
			 */
			// 608942
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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
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
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
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
			// RS
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
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SRT
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
			// SRS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 608943
			try {
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
				blnLogin = true;
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
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'User
			 * List' screen is displayed.
			 * 
			 * 'Create New User' button is present at top left.
			 * 
			 * All the users in the region are listed in 'User List' screen,with
			 * a.Edit b.Password c.Regions d.Switch links associated with it
			 * under 'Action' column.
			 */
			// 608944

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{
				assertTrue(selenium.isElementPresent(propElementDetails.getProperty("CreateNewUsrLink")));
				log4j.info("'Create New User' button is present at top left");
			}catch(AssertionError Ae){
				gstrReason = "'Create New User' button is not present at top left";
				log4j.info("'Create New User' button is not present at top left");
			}

			/*
			 * STEP : Action:Click on 'Create New User' button Expected
			 * Result:'Create New User' screen is displayed.
			 */
			// 608945
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						selenium, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide data in all mandatory fields,Scroll down
			 * till 'Resource Rights' section. Expected Result:Sub-resource
			 * 'SRS1' is not listed under 'Resource Rights' section.
			 * 
			 * 'View Resource' right for all the resources is selected by
			 * default.
			 */
			// 608946
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifySubResourceInResRights(
						selenium, strSubResource, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyViewResourceRight(selenium, "SELECT_ALL", "viewRight", true);
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
			gstrTCID = "BQS-107190";
			gstrTO = "Verify that sub-resources are not listed while creating users.";
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

	//end//testBQS107190//

	//start//testBQS107194//
	/***************************************************************
	'Description	:Verify that user with setup resource types right can create a sub resource type.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/1/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	 ***************************************************************/

	@Test
	public void testBQS107194() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral=new General();
		try {
			gstrTCID = "107194"; // Test Case Id
			gstrTO = "Verify that user with setup resource types right can create a sub resource type.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			String strFILE_PATH = pathProps.getProperty("TestData_path");																// URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Precondition:
			 Status types 'ST1'(Normal) and 'ST2' (event related) are created in region RG1.
             User 'U1' is created selecting 'Setup Resource Types' rights 
			 */
			// 608942

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
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
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
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 608943
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				blnLogin = true;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:	Navigate to Setup >> Resource Type 
			 * Expected Result:'Resource Type List' screen is displayed.
         'Create New Resource Type' button is present at top left corner. 
			 */
			// 608944
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
				String strElementID = propElementDetails
						.getProperty("CreateNewRsrcTypeLink");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New Resource Type' button is present at top left corner.");
				} else {
					log4j.info("'Create New Resource Type' button is  NOT present at top left corner.");
					strFuncResult = "'Create New Resource Type' button is NOT present at top left corner.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Resource Type' button 
			 * Result:'Create New Resource Type' screen is displayed 
			 */
			// 608945
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navToCreateNewResrcTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Enter mandatory data, select Sub-Resource check box and status type
			 *  'ST2' and click on 'Save'.
			 *  Expected Result:'The following error occurred on this page:
			�Sub-Resource Types may not use Event only Status Types (< Status Type name >)'is displayed.
			User is retained 'Create New Resource Type' page.
			'Sub-Resource Type' is not created 
			 */
			// 608946
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeOnlyMandatoryFlds(
						selenium, strResrctTypName, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.savandVerifyErrorMsg(selenium,
						strResrctTypName, strStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect 'ST2' and select 'ST1' and click on 'Save'. 
			 *  Expected Result:Sub-Resource Type is listed on the 'Resource Type List' page under column header listed below:
			1. Action
			2. Name
			3. Sub-Resource (Yes is displayed for resource type created selecting sub-resource check box)
			4. Active (this column is present if include inactive resource type is selected)
			5. Description (with the description provided) 
			 */
			// 608946

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.savRTandVerifyDataInRTListPage(selenium,
								strResrctTypName, "", true);
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
			gstrTCID = "107194"; // Test Case Id
			gstrTO = "Verify that user with setup resource types right can create a sub resource type.";// TO
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

	//end//testBQS107194//

	//start//testBQS107193//
	/***************************************************************
		'Description	:Verify that newly added rights for a resource is inherited by the sub resource.
		'Precondition	:
		'Arguments		:None
		'Returns		:None
		'Date			:7/15/2013
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
	 ***************************************************************/

	@Test
	public void testBQS107193() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral=new General();
		Roles objRoles =new Roles();
		Views objViews=new Views();
		try {
			gstrTCID = "107193"; // Test Case Id
			gstrTO = " Verify that newly added rights for a resource is inherited by the sub resource.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strNumStatType = "AutoNSt_" + strTimeText;
			String strTxtStatType = "AutoTSt_" + strTimeText;
			String strSatuStatType = "AutoScSt_" + strTimeText;
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strStatTypeName1 = "St1_" + strTimeText;
			String strStatTypeName2 = "St2_" + strTimeText;

			String strStatTypDefn = "Auto";

			String strNSTValue = "Number";
			String strTSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strStatusValue[] = new String[2];

			String strSTvalue[] = new String[6];

			String strUpdate1="10";
			String strUpdate2="20";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];

			String strResource = "AutoRs_" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";


			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];

			String strRolesName1 = "Rol_" + strTimeText;
			String strRoleValue = "";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strSection1 = "AB_1" + strTimeText;
			String[] strSectionValue = new String[2];

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


			/*
			 * STEP : Action:Preconditions:
			 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			 * 2. NST , MST, SST, TST ,ST1,ST2 status types are under status
			 * type section SEC1.
			 * 3. Resource type RT is created selecting NST,MST,TST,SST.
			 * 4. Sub Resource Type RT1 is created selecting ST1,ST2.
			 * 5. Resource RS is created under RT.
			 * 6. Sub-Resource 'RS1' is created under parent resource 'RS'
			 * selecting sub-resource tyoe 'RT1'
			 * 7. User U1 is created selecting the following rights: a. 'View
			 * Resource' and 'Update resource' right on RS. b. Role R1 to view
			 * and update NST , MST, SST, TST,ST1,ST2 status types. c.
			 * 'Configure Region View' right.
			 * 8. View 'V1' is created selecting status types NST,MST,TST,SST
			 * and resource type RT Expected Result:No Expected Result
			 */
			// 609155
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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

			/*
			 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType, strStatTypDefn,
						true);
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

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, strTxtStatType, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtStatType);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//MST
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
				strFuncResult = objStatusTypes.savAndVerifyMultiST(seleniumPrecondition,
						strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMultiStatType);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, strMultiStatType, strStatusName1,
						strMSTValue, strStatTypeColor, true);
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
			//SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, strSatuStatType, strStatTypDefn,
						true);
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

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[4] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[5] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 2. Resource type RT is created selecting NST,MST,TST,SST.
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

			for (int intST = 1; intST < strSTvalue.length; intST++) {
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
				strFuncResult = objRT.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 *  Sub Resource Type 'sRT1' is created selecting ST1,ST2.
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
						strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[4] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSubResourceType(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*3. Resource 'RS' is created under 'RT1'.
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
			 * 5. Sub-Resource 'RS1' is created under parent resource 'RS'
			 * selecting sub-resource type 'RT1'
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition, strResource);
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
						seleniumPrecondition, strSubResource,strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Role 1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" },{ strSTvalue[4], "true" },{ strSTvalue[5], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTUpdateValue,
						strSTUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			//Section
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
				String[] strArStatType1 = { strNumStatType,strTxtStatType ,strSatuStatType,strMultiStatType,strStatTypeName1,strStatTypeName2};
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
				strSectionValue[0] = objViews.fetchSectionID(seleniumPrecondition,
						strSection1);
				if (strSectionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//user
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						true, true, false, true);
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
			//View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTValue = { strSTvalue[0],strSTvalue[1],strSTvalue[2],strSTvalue[3] };
				String[] strRSvalue = {  };
				strFuncResult = objViews.createViewNew(seleniumPrecondition, strViewName_1, strVewDescription, 
						strViewType, true, false, strSTValue, false, strRSvalue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as User 'U1' navigate to Setup >> Views
			 * Expected Result:'Region Views List' screen is displayed.
			 * 
			 * 'Create New View','Re-Order Views' and 'Customize Resource Detail
			 * view' buttons are present at top left of the screen.
			 */
			// 609156
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
				blnLogin = true;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("css=input[value='Create New View']"));
				log4j.info("'Create New View' button displayed");
			} catch (AssertionError Ae) {
				strFuncResult="'Create New View' button not displayed";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("View.CreateNewView.ReOrderViews")));
				log4j.info("Re-Order Views' button displayed");
			} catch (AssertionError Ae) {
				strFuncResult="'Re-Order Views' button not displayed";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent(propElementDetails
								.getProperty("RegionView.CustomizeResDetailView")));
				log4j.info("Customize Resource Detail View' button displayed");
			} catch (AssertionError Ae) {
				strFuncResult="'Customize Resource Detail View not displayed";
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' button
			 * Expected Result:'Edit Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * 'Save','Cancel' and 'Sub-resource' buttons are displayed on
			 * bottom left of the screen.
			 * 
			 * 'Sort all' button at bottom right of the screen.
			 */
			// 609157
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium.isElementPresent("css=input.emsButtonText"));
				assertTrue(selenium.isElementPresent("xpath=(//input[@value='Cancel'])[2]"));
				assertTrue(selenium.isElementPresent("//input[@value='Sub-resources']"));
				assertTrue(selenium.isElementPresent("//input[@value='Sort All']"));
				log4j.info("'Save','Cancel' and 'Sub-resource' buttons are displayed on bottom left of the screen.\n 'Sort all' button at bottom right of the screen.");
			} catch (AssertionError Ae) {
				strFuncResult="'Save','Cancel' and 'Sub-resource' buttons are Not displayed on bottom left of the screen. \n'Sort all' button at bottom right of the screen.";
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resources' button Expected
			 * Result:'Edit Sub Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * Sub-Resoure Type 'RT1' is listed under it.
			 */
			// 609158
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Check box associated with 'RT1' and select
			 * status types ST1,ST2 and click on 'Save' Expected Result:'Region
			 * Views List' screen is displayed.
			 */
			// 609159
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[4],strSTvalue[5] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName,
								strRTvalue[1], strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> V1 Expected Result:RS is
			 * displayed under RT along with status types NST,MST,SST,TST.
			 * 
			 * Key icon is displayed next to resource 'RS'
			 */
			// 609160
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				String strResource1[]={strResource};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResource1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
					.info("Key icon is displayed next to resource 'RS1' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'RS1' ";
					log4j
					.info("Key icon is NOT displayed next to resource 'RS1' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Details provided while creating resource 'RS' are displayed
			 * appropriately.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed. Sub-resource 'RS1' is
			 * displayed under 'RT1' along with status types ST1,ST2
			 * 
			 * Key icon is displayed next to sub-resource 'RS1'
			 * 
			 * 'Contacts' section is displayed, User with associated right on
			 * that resource are listed under it.
			 */
			// 609161
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
				String[][] strResouceData = { { "Type:", strResrctTypName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] strStatType={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(selenium, strSection1, strSectionValue[0], strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { strStatTypeName1, strStatTypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strSubResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strSubResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
					.info("Key icon is displayed next to resource 'RS1' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'RS1' ";
					log4j
					.info("Key icon is NOT displayed next to resource 'RS1' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[@id='contacts']/tbody/tr/td[text"
						+ "()='" + strUsrFulName_1 + "']";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				if(strFuncResult.equals(""))
				{
					log4j.info("User with associated right on that resource are listed in 'Contacts' section.");
				}else{
					log4j.info("User with associated right on that resource are NOT listed in 'Contacts' section.");
					strFuncResult = "User with associated right on that resource are NOT listed in 'Contacts' section.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on key icon associated with sub-resource
			 * 'RS1' Expected Result:'Update Status' screen is displayed.
			 */
			// 609162
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUpdateStatusByKey(selenium, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Update the status for status types ST1,ST2 and
			 * click on 'Save'. Expected Result:'View Resource Detail' screen
			 * for resource 'RS' is displayed.
			 * 
			 * Updated values for status types 'ST1' and 'ST2' are displayed
			 * under sub-resource section.
			 */
			// 609163
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate1, strSTvalue[4], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdate2, strSTvalue[5], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
				int intCnt = 0;
				do {
					try {

						assertEquals("View Resource Detail", selenium
								.getText(propElementDetails
										.getProperty("Header.Text")));
						break;
					} catch (AssertionError Ae) {
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				} while (intCnt < 60);
				assertEquals("View Resource Detail", selenium
						.getText(propElementDetails.getProperty("Header.Text")));
				log4j.info("View Resource Detail screen is displayed");

			} catch (AssertionError ae) {
				log4j.info("View Resource Detail screen is NOT displayed");
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetailOfSubRes(selenium, strRTvalue[1], strUpdate1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetailOfSubRes(selenium,strRTvalue[1],strUpdate2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as RegAdmin navigate to Setup >> Users
			 * Expected Result:'User List' Screen is displayed.
			 * 
			 * User 'U1' is listed under it.
			 */
			// 609164
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
				blnLogin=true;
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
				strFuncResult = objCreateUsers
						.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.VrfyUserWithSearchUser(selenium, strUserName_1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on edit link associated with user 'U1'
			 * Expected Result:'Edit User' screen is displayed.
			 */
			// 609165
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName_1, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Deselect update resource right and select
			 * 'Associate' right for resource 'RS' and 'Save' Expected
			 * Result:'User List' Screen is displayed.
			 */
			// 609166
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0],
						true, false, false, true);
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
			 * STEP : Action:Login as user 'U1' navigate to View >> V1 Expected
			 * Result:RS is displayed under RT along with status types
			 * NST,MST,SST,TST.
			 * 
			 * Key icon is not displayed next to resource 'RS'
			 */
			// 609167
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				String strResource1[]={strResource};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResource1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				assertFalse(selenium.isElementPresent(strElementID));
				log4j.info("Key icon is not displayed next to resource 'RS1' ");

			} catch (AssertionError Ae) {
				strFuncResult = "Key icon is displayed next to resource 'RS1' ";
				log4j.info("Key icon is displayed next to resource 'RS1' ");
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed. Sub-resource 'RS1' is
			 * displayed under 'RT1' along with status types ST1,ST2
			 * 
			 * Key icon is not displayed next to resource 'RS1'
			 * 
			 * User 'U1' is listed under Contacts section
			 */
			// 609168
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] strStatType={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(selenium, strSection1, strSectionValue[0], strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { strStatTypeName1, strStatTypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strSubResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strSubResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				assertFalse(selenium.isElementPresent(strElementID));
				log4j.info("Key icon is not displayed next to resource 'RS1' ");

			} catch (AssertionError Ae) {
				strFuncResult = "Key icon is displayed next to resource 'RS1' ";
				log4j.info("Key icon is displayed next to resource 'RS1' ");
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID="//table[@id='contacts']/tbody/tr/td[text"
						+ "()='" + strUsrFulName_1 + "']";
				strFuncResult=objGeneral.CheckForElements(selenium, strElementID);
				if(strFuncResult.equals(""))
				{
					log4j.info("User with associated right on that resource are listed in 'Contacts' section.");
				}else{
					log4j.info("User with associated right on that resource are NOT listed in 'Contacts' section.");
					strFuncResult = "User with associated right on that resource are NOT listed in 'Contacts' section.";
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
			gstrTCID = "BQS-107193";
			gstrTO = "Verify that newly added rights for a resource is inherited by the sub resource.";
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
	//end//testBQS107193//

	//start//testBQS107192//
	/***********************************************************************************
	'Description	:Verify that sub resources inherits rights from the parent resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/1/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	 ***********************************************************************************/

	@Test
	public void testBQS107192() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Roles objRoles = new Roles();
		ResourceTypes objRT = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews=new Views();
		General objGeneral=new General();
		try {
			gstrTCID = "107192"; // Test Case Id
			gstrTO = " Verify that user can demote a resource that is included in a region view.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strTSTValue = "Number";
			String strMSTValue = "Multi";
			String strSSTValue = "Saturation Score";
			String strNumStatType = "NST_" + strTimeText;
			String strTxtStatType = "TST_" + strTimeText;
			String strSatuStatType = "SST_" + strTimeText;
			String strMultiStatType = "MST_" + strTimeText;
			String strStatTypeName1 = "ST1_" + strTimeText;
			String strStatTypeName2 = "ST2_" + strTimeText;
			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatValue = "";
			String strStatusValue[] = new String[1];
			String strSTvalue[] = new String[6];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];

			String strResource = "AutoRs_1" + strTimeText;
			String strSubResource= "ASubRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strLongitude="";
			String strLatitude="";
			String strRSValue[] = new String[2];

			// Data for creating View
			String strViewName = "autoView" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";

			// sec data
			String strSection1 = "ABSec_" + strTimeText;
			String strSectionValue = "";
			String[] strStatTypeArr={strMultiStatType,strNumStatType,strTxtStatType,strSatuStatType,
					strStatTypeName1,strStatTypeName2};

			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strUpdatTxtValue1="100";
			String strUpdatTxtValue2="200";

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Precondition:
			1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			2. NST , MST, SST, TST ,ST1,ST2 status types are under status type section SEC1.
			3. Resource type RT is created selecting NST,MST,TST,SST.
			4. Sub Resource Type RT1 is created selecting ST1,ST2.
			5. Resource RS is created under RT.
			6. Sub-Resource 'RS1' is created under parent resource 'RS' selecting sub-resource tyoe 'RT1'
			7. User U1 is created selecting the following rights:
				a. 'View Resource' and 'Update resource' right on RS.
				b. Role R1 to view and update NST , MST, SST, TST,ST1,ST2 status types.
				c. 'Configure Region View' right.
			8. View 'V1' is created selecting status types NST,MST,TST,SST and resource type RT  
			 */
			// 608942

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

			/*
			 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
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

			// TST

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
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// MST
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
					strSTvalue[2] = strStatValue;
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

			// SST
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

			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[4] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[5] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource type RT is created selecting NST,MST,TST,SST.
			 */

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
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4. Sub Resource Type 'sRT1' is created selecting ST1,ST2.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[4] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * . Resource 'RS'1 is created under 'RT'.
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition, strResource);
				int intCnt=0;
				do{
					try {

						assertTrue(seleniumPrecondition.isElementPresent("id=latitude"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;

					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				strLatitude = seleniumPrecondition.getValue("id=latitude");
				strLongitude = seleniumPrecondition.getValue("id=longitude");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * . Resource 'SRS1' is created under 'SRT'.
			 */
			//SRS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource);
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
						seleniumPrecondition, strSubResource,strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Role R1 is created selecting status type ST under view and
			 * update.
			 */
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
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" },
						{ strSTvalue[4], "true" }, { strSTvalue[5], "true" }};
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName_1);
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
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_A, strInitPwd, strConfirmPwd,
						strUsrFulName_A);
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
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], true, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_A, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 7.View 'V1' is created selecting status type NST,MST,SST,TST
			 * ,'RT' and resource 'RS1' Expected Result:No Expected Result
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
				String strSTValues[] = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				String strRSValues[] = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTValues, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumPrecondition, strStatTypeArr, strSection1, true);
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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE" + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1' navigate to Setup >> Views 
			 *  ExpectedResult:'Region Views List' screen is displayed.
           'Create New View','Re-Order Views' and 'Customize Resource Detail view' buttons
            are present at top left of the screen. 
			 */
			// 608943

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("View.CreateNewView");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New View' button is present at top left of the screen.");
				} else {
					strFuncResult = "'Create New View' button is NOT present at top left of the screen.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("View.CreateNewView.ReOrderViews");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Re-Order Views' button is present at top left of the screen.");
				} else {
					strFuncResult = "'Re-Order Views' button is NOT present at top left of the screen.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("RegionView.CustomizeResDetailView");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Customize Resource Detail view' button is present at top left of the screen.");
				} else {
					strFuncResult = "'Customize Resource Detail view' button is NOT present at top left of the screen.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	

			/*
			 * STEP : Action:Click on 'Customize Resource Detail View' button 		
			 * Expected Result:'Edit Resource Detail View Sections' screen is displayed.
			'Save','Cancel' and 'Sub-resource' buttons are displayed on bottom left of the screen.
			'Sort all' button at bottom right of the screen. 
			 */
			// 608944

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Save']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j
					.info("'Save','Cancel' and 'Sub-resource' buttons are" +
							" displayed on bottom left of the screen. ");
					log4j
					.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are " +
							"NOT displayed on bottom left of the screen. ";
					log4j
					.info("'Save','Cancel' and 'Sub-resource' buttons are " +
							"NOT displayed on bottom left of the screen. ");
					log4j
					.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resources' button 
			 * Result:'Edit Sub Resource Detail View Sections' screen is displayed.
            Sub-Resoure Type 'RT1' is listed under it. 
			 */
			// 608945

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select Check box associated with 'RT1' and select status types ST1,ST2 and click on 'Save' 
			 *  Expected Result:'Region Views List' screen is displayed. 
			 */
			// 608946
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[4], strSTvalue[5] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> V1 
			 *  Expected Result:RS is displayed under RT along with status types NST,MST,SST,TST.
           Key icon is displayed next to resource 'RS' 
			 */
			// 608946

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	

			try {
				assertEquals("", strFuncResult);		
				String[] strResources={strResource};
				strFuncResult = objViews.checkResTypeAndResourceInUserView(selenium,
						strViewName, strResrctTypName, strResources, strRTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={strMultiStatType,strNumStatType,strSatuStatType,strTxtStatType};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'RS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'RS' ";
					log4j.info("Key icon is NOT displayed next to resource 'RS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' 
			 *  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.
			Details provided while creating resource 'RS' are displayed appropriately.
			Status types NST,MST,SST,TST are displayed under section 'SEC1'
			'Sub-resources' section is displayed.
			Sub-resource 'RS1' is displayed under 'RT1' along with status types ST1,ST2
			Key icon is displayed next to resource 'RS1'
			'Contacts' section is displayed, User with associated right on that resource are listed under it. 
			 */
			// 608946

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
				String[][] strResouceData = {
						{ "Type:", strResrctTypName },
						{ "Address:", "AL " },
						{ "County:", "Barbour County" },
						{ "Lat/Longitude:", strLatitude + " / " + strLongitude },
						{ "EMResource/AHA ID:", strRSValue[0] },
						{ "Contact:", strContFName + " " + strContLName }};

				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { strNumStatType, strTxtStatType,
						strSatuStatType, strMultiStatType };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypep);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { strStatTypeName1, strStatTypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "+strSubResrctTypName+"']" +
						"/tbody/tr/td/a[text()='"+strSubResource+"']/parent::td/" +
						"preceding-sibling::td/a/img[@src='img/icons/keys.gif']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("Key icon is displayed next to resource 'SRS' ");

				} else {
					strFuncResult = "Key icon is NOT displayed next to resource 'SRS' ";
					log4j.info("Key icon is NOT displayed next to resource 'SRS' ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='contacts']/tbody/tr/td[text"
						+ "()='" + strUsrFulName_A + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("User with associated right on that resource are listed under it.");
				} else {
					log4j.info("User with associated right on that resource are NOT listed under it.");
					strFuncResult = "User with associated right on that resource are NOT listed under it.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on key icon associated with sub-resource 'SRS1' 
			 *  Expected Result:'Update Status' screen is displayed. 
			 */
			// 608946
			try {
				assertEquals("", strFuncResult);
				String strNavElement = "css=img.noprint";
				strFuncResult = objViews.navToUpdateStatus(selenium, strNavElement);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Update the status for status types ST1,ST2 and click on 'Save'. 
			 *  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.
        Updated values for status types 'ST1' and 'ST2' are displayed under sub-resource section. 
			 */
			// 608946
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdatTxtValue1, strSTvalue[4], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strUpdatTxtValue2, strSTvalue[5], false, "", "");
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
				strFuncResult = objViews.verifyUpdSTValInResDetailOfSubRes(
						selenium, strRTvalue[1], strUpdatTxtValue1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetailOfSubRes(
						selenium, strRTvalue[1], strUpdatTxtValue2);
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
			gstrTCID = "107192"; // Test Case Id
			gstrTO = "Verify that sub resources inherits rights from the parent resource.";// TO
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
	//end//testBQS107192//
}
