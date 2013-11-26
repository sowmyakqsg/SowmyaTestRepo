package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************
' Description :This class includes requirement testcases
' Precondition:
' Date		  :22-Aug-2012
' Author	  :QSG
'--------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'********************************************************/

public class EditStatusTypeAtResourceLevel {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditStatusTypeAtResourceLevel");
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
	public Properties propPathDetails;
	String gstrTimeOut = "";
	Selenium selenium, seleniumFirefox, seleniumPrecondition;

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

		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
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
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		selenium.close();
		
		 // kill browser
		  selenium.stop();
		  seleniumFirefox.close();
		  seleniumFirefox.stop();
		  
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
	
	/***********************************************************************
	'Description		:Verify that a user with �Setup Resource Type� right 
	                     can add the status types at the resource level.
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/7/2012
	'Author			    :QSG
	'----------------------------------------------------------------------
	'Modified Date				                                Modified By
	'Date					                                    Name
	***********************************************************************/

	@Test
	public void testBQS49988() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		try {

			gstrTCID = "49988"; // Test Case Id
			gstrTO = " Verify that a user with �Setup Resource Type� right can add the status types"
					+ " at the resource level.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strRegn = rdExcel.readData("Login", 3, 4);
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = "abc123";
			String strConfirmPwd = strInitPwd;
			String strUsrFulName = "FN" + strUserName;
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strStatType3 = "AutoSt3_" + strTimeText;
			String strTxtStatTypeValue = "Text";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[3];

			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			String strRT = "";
			
			/*
			 * STEP : Action:Login as RegAdmin to EMResource and from
			 * Setup>>Users page, create user U1 selecting only the following
			 * rights: 1. 'Setup Resource Types' 2. 'Setup Resources' Expected
			 * Result:No Expected Result
			 */
			// 284611
		
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType1, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType2, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strStatType3, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[2] = strStatValue;
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strST[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strST[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRT = objRT.fetchRTValueInRTList(seleniumPrecondition, strResType);
				if (strRT.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch resource type value";
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");

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
			// user
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium,
						strUserName, strByRole, strByResourceType,
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
			 * STEP : Action:Logout and login as user U1, and navigate to
			 * Setup>>Resources screen Expected Result:Resource list screen is
			 * displayed with the list of all resources in the region. 'Create
			 * New Resource' button is available. 'Edit Status Types' link is
			 * available for all the listed resources. 'Edit' and 'Users' links
			 * are available corresponding to each resources.
			 */
			// 284612

			try {
				assertEquals("", strFuncResult);
				strLoginUserName = strUserName;
				strLoginPassword = strInitPwd;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.varFieldsInResListPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed Status types associated with the resource
			 * type RT cannot be removed. i.e. Check boxes corresponding to
			 * status types ST1 and ST2 are selected and disabled. Other status
			 * types in the region are available to be added to the resource RS.
			 * i.e. check boxes corresponding to all other status types are
			 * unchecked and enabled.
			 */
			// 284613

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strST[0], strST[1] };
				String[] strSTSval = { strST[2] };
				strFuncResult = objResources.navToEditResLevelST(selenium,
						strResource, strSTvalue, strSTSval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select ST3 and click on 'Save' Expected Result:User
			 * is taken to Resource List screen.
			 */
			// 284614

			try {
				assertEquals("", strFuncResult);
				String strStatTypeVal = strST[2];// AutoST349988
				strFuncResult = objResources.selAndDeselSTInEditResLevelST(
						selenium, strStatTypeVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.saveResAndNavToEditResLvlPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS Expected Result:ST3 is selected and enabled in the
			 * 'Edit Resource-Level Status Types' screen
			 */
			// 284615

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { strST[0], strST[1] };
				String[] strSTSval = { strST[2] };
				strFuncResult = objResources.navToEditResLevelST(selenium,
						strResource, strSTvalue, strSTSval, false, false);
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
			gstrTCID = "BQS-49988";
			gstrTO = "Verify that a user with �Setup Resource Type� right can add the status "
					+ "types at the resource level.";
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

	@Test
	public void testBQS36784() throws Exception {

		boolean blnLogin = false;
		boolean blnCreateSec = true;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login

		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();

		ViewMap objViewMap = new ViewMap();
		Date_Time_settings dts = new Date_Time_settings();
		String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

		String strSection = "AB_" + strTimeText;
		try {
			gstrTCID = "36784";
			gstrTO = "Add a status type to resource RS and verify that a user with"
					+ " �Update Status� right on the resource RS, �Must Update "
					+ "Overdue Status� right and the role to update the status type ST"
					+ " is prompted to update the status of ST on login.";

			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strNumUpdateValue1 = "2";

			String strTxtUpdateValue1 = "tr";

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };

			String strScUpdValCheck1 = "393";

			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			// Status Type
			String strStatusTypeValue = "Number";
			String statTypeName = "AutoST_" + strTimeText;

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource1 = "AutoRsa_" + strTimeText;
			String strResource2 = "AutoRsb_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = "Full" + strUserName1;

			String strUserName2 = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName2 = "Full" + strUserName2;
			String strRoleValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[2];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};

			String strNSTValue = "Number";
			String strNumStatType = "A1_" + strTimeText;

			String strMSTValue = "Multi";
			String strMultiStatType = "A2_" + strTimeText;

			String strTSTValue = "Text";
			String strTxtStatType = "A3_" + strTimeText;

			String strSSTValue = "Saturation Score";
			String strSatuStatType = "A4_" + strTimeText;

			String strStatusName1 = "Sta" + strTimeText;

			String strStatusValue[] = new String[1];

			String strArStatType[] = { strNumStatType, strMultiStatType,
					strTxtStatType, strSatuStatType };
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			String strStVal = "";
			// Navigate user default region
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStVal = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNumStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strNumStatType);
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMSTValue, strMultiStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strMultiStatType);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMultiStatType, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTSTValue, strTxtStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strTxtStatType);
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strSSTValue, strSatuStatType, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition,
						strSatuStatType);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='" + strStVal
								+ "']");
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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				blnCreateSec = true;
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
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
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
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
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
					      strByUserInfo, strNameFormat);

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
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName2,
								strInitPwd, strConfirmPwd, strUsrFulName2);

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
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource1, false, true, false, true);

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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName2, strByRole, strByResourceType,
					      strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strConfirmPwd);
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
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(selenium,
							strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName2,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strNumStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strTxtStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strSatuStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.remindMeAndNavToRegDefault(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// wait for 10 mins
			Thread.sleep(810000);
			Thread.sleep(120000);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strNumStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strMultiStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strTxtStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkUpdateStatPrompt(selenium,
						strResource1, strSatuStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strNumUpdateValue1, strSTvalue[0], "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusMSTWithComments(selenium,
						strStatusValue[0], strSTvalue[1], "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusNSTWithComments(selenium,
						strTxtUpdateValue1, strSTvalue[2], "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillUpdStatusSSTWithComments(selenium,
						strScUpdValue1, strSTvalue[3], "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						"Region Default");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { strNumStatType, strMultiStatType,
						strTxtStatType, strSatuStatType };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strNumUpdateValue1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strTxtUpdateValue1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strScUpdValCheck1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			if (blnCreateSec == true) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.login(selenium, strLoginUserName,
							strLoginPassword);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.navUserDefaultRgn(selenium,
							strRegn);

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
					strFuncResult = objViews
							.navToEditResDetailViewSections(selenium);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.deleteSection(selenium, strSection);
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
					strFuncResult = objST.editStatusTypePage(selenium,
							strNumStatType, strNSTValue);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.activateAndDeactivateST(selenium,
							strNumStatType, false, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.editStatusTypePage(selenium,
							strMultiStatType, strMSTValue);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.activateAndDeactivateST(selenium,
							strMultiStatType, false, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.editStatusTypePage(selenium,
							strTxtStatType, strTSTValue);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.activateAndDeactivateST(selenium,
							strTxtStatType, false, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.editStatusTypePage(selenium,
							strSatuStatType, strSSTValue);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.activateAndDeactivateST(selenium,
							strSatuStatType, false, true);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					blnCreateSec = false;

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			if (blnCreateSec == false) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} else {
				try {
					assertEquals("", strFuncResult);

					try {
						assertEquals("", gstrReason);
						gstrReason = "Test case passed.Created Section "
								+ strSection
								+ " is not deleted.Please delete the section";
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + "Created Section "
								+ strSection
								+ " is not deleted.Please delete the section";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "36784";
			gstrTO = "Add a status type to resource RS and verify that a user "
					+ "with �Update Status� right on the resource RS, �Must Update"
					+ " Overdue Status� right and the role to update the status type"
					+ " ST is prompted to update the status of ST on login.";
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
'Description		:Add a status type to resource RS and verify that custom view can be created selecting newly added status type and resource RS.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:10/29/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS36781() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		ViewMap objViewMap = new ViewMap();
		Preferences objPreferences = new Preferences();
		MobileView objMobileView = new MobileView();

		try {
			gstrTCID = "36781"; // Test Case Id
			gstrTO = " Add a status type to resource RS and verify that custom"
					+ " view can be created selecting newly added status type"
					+ " and resource RS.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTestData[] = new String[10];

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[3];

			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[2];

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName = "AutoRol_1" + strTimeText;
			String strRoleValue[] = new String[1];

			// Section
			String strSection = "AB_1" + strTimeText;
			String strArStatType[] = { strStatType1, strStatType2 };

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Preconditions: 1. Resource type RT is associated
			 * with status type ST1 and not with status type ST2. 2. Resources
			 * RS1 and RS2 are created under RT. 3. Status type ST2 is added
			 * under status type section SEC 4. User U1 has following rights:
			 * Setup Resource Types Setup Resources View custom view Expected
			 * Result:No Expected Result
			 */

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strStatType1, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strStatType2, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
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
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(seleniumPrecondition,
						strResrcTypName, strST[0]);
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
				strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrcTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource2, strAbbrv, strResrcTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			// User
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
						strRoleValue[0], true);
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
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.savVrfyUserWithSearchUser(seleniumPrecondition,
								strUserName, strByRole, strByResourceType,
							      strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			/*
			 * STEP : Action:Login as user U1 and navigate to Setup>>Resource
			 * page Expected Result:'Resource List' page is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit Status Types' link corresponding to
			 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditRTInResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and save Expected
			 * Result:user is returned to the 'Resource List' page
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strST[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences>>Customized View, click on
			 * 'Add More Resources' on the 'Edit Custom View' screen Expected
			 * Result:'Find Resources' screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide search criteria as RS in the search filter
			 * and click on 'Search' Expected Result:Resources RS1 and RS2 are
			 * retrieved and displayed
			 */
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String strResource = "AutoRs";
				strFuncResult = objPreferences.findResourcesInAddMoreRes(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select both resources RS1 and RS2 and click on 'Add
			 * to Custom View' Expected Result:'Edit Custom View' screen is
			 * dispalyed
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Options' Expected Result:'Edit Custom
			 * View Options (Columns)' screen is displayed with status type ST2
			 * under status type section SEC status type ST1 is also listed in
			 * the page under appropriate status type section.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.checkStatTypeInEditCustViewOptions(selenium,
								strStatType1);
				try {
					assertTrue(selenium.isElementPresent("//b[text()='"
							+ strStatType2 + "']"));
					log4j
							.info("Status Type "
									+ strStatType2
									+ " is displayed in Edit Custom View Options (Columns)");
				} catch (AssertionError Ae) {
					strFuncResult = "Status Type "
							+ strStatType2
							+ " is NOT displayed in Edit Custom View Options (Columns)";
					log4j
							.info("Status Type "
									+ strStatType2
									+ " is NOT displayed in Edit Custom View Options (Columns)");
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status type ST2 and click on Save Expected
			 * Result:'Custom View - Table' is displayed with status type ST2
			 * for resource RS1. Resource RS2 is displayed with N/A under ST2
			 * column.
			 */
			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = { { strST[0], "true" },
						{ strST[1], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1, strStatType2 };
				strFuncResult = objViews
						.checkResTypeRSAndSTInViewCustTabl(selenium,
								strResrcTypName, strResource1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTAssoOrNotInCustomViewScreen(
						selenium, strStatType2, false, strResource2, 2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show Map' link displayed at the top right
			 * corner of the screen Expected Result:'Custom View - Map' screen
			 * is displayed Status types ST1 and ST2 are displayed on the
			 * resource pop window of RS1 Only status type ST1 is displayed on
			 * the resource pop window of RS2
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { strStatType1, strStatType2 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { strStatType1 };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource2, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the 'Mobile View' link displayed at the
			 * footer Expected Result:'Resources' option is displayed in the
			 * 'Main Menu' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the 'Resources' link on the 'Main Menu'
			 * screen Expected Result:Custom link is displayed in the
			 * 'Resources' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView
						.navToCustomPageInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Custom' link Expected Result:Resources
			 * RS1 ad RS2 are displayed
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1, strResource2 };
				strFuncResult = objMobileView.chkResInCustomPageInMobileView(
						selenium, strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource name RS1 Expected Result:Status
			 * types ST1 and ST2 are displayed in the details page of RS1 under
			 * appropriate sections.
			 */

			/*
			 * STEP : Action:Click on 'Back' Expected Result:'Custom' screen is
			 * displayed
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 + ":",
						strStatType2 + ":" };
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource1, strSection, strStatTypeArr,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on resource name RS2 Expected Result:Only
			 * Status type ST1 is displayed under appropriate status type
			 * section
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { strStatType1 + ":" };
				strFuncResult = objMobileView.chkSTInCustomPageInMobileView(
						selenium, strResource2, strSection, strStatTypeArr,
						false);
				try {
					assertFalse(selenium
							.isElementPresent("//div/h3/following-sibling::table[@class='topicListItem']"
									+ "/tbody/tr/td[contains(text(),'"
									+ strStatType2 + ":" + "')]"));
					log4j.info(strStatType2
							+ " is NOT displayed correctly under section."
							+ strSection);
				} catch (AssertionError ae) {
					log4j.info(strStatType2
							+ " is displayed correctly under section."
							+ strSection);
					strFuncResult = strStatType2
							+ " is displayed correctly under section."
							+ strSection;
					gstrReason = strFuncResult;
				}
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
				strTestData[3] = strStatType1 + "/" + strStatType2;
				strTestData[6] = strResource1 + "/" + strResource2;
				strTestData[7] = strSection;
				strTestData[5] = "Verify from 17th step";
				strTestData[9] = strRegn;

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-36781";
			gstrTO = "Add a status type to resource RS and verify that custom view can be created selecting newly added status type and resource RS.";
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
