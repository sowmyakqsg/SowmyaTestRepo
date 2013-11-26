package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**********************************************************************
' Description         :This class includes  requirement testcases
' Requirement Group   :Multi Region
' Requirement         :Regions with mutual agreement and direct access
' Date		          :31-oct-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class RegionsWithMutualAgreementAndDirectAccess {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RegionsWithMutualAgreementAndDirectAccess");
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
	String gstrTimeOut="";
	
	Selenium selenium,seleniumFirefox, seleniumPrecondition;


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
		

		selenium.start();
		selenium.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

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
		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		seleniumFirefox.stop();
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

/**********************************************************************************************
'Description	:Verify that the resource of RG1 cannot be edited from RG2 when user has access
                 to only RG2 and RG3; other region view agreement is done between RG3 and RG1.
'Arguments		:None
'Returns		:None
'Date			:10/30/2012
'Author			:QSG
'-----------------------------------------------------------------------------------------------
'Modified Date				                                                        Modified By
'Date					                                                            Name
************************************************************************************************/

	@Test
	public void testBQS71296() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		Regions objRegions = new Regions();
		try {
			gstrTCID = "71296"; // Test Case Id
			gstrTO = " Verify that the resource of RG1 cannot be edited from RG2 " +
					"when user has access to only RG2 and RG3; other region view " +
					"agreement is done between RG3 and RG1.";// Test
																																														// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = "Amarillo, TX";
			String strRegn2 = rdExcel.readData("Login", 3, 4);
			String strRegn3 = "Statewide Oklahoma";
			String strRegionValue[] = new String[1];

			// ST
			String strStatType1 = "AutoSt1_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strNumStatTypDefn = "Auto";
			String[] strST = new String[1];
			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[2];
			// RS
			String strResource = "AutoRs1_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			String strCategory = "(Any)";
			String strCityZipCd = "";

			/*
			 * STEP : Action:Preconditions: 1. User U1 has access to Regions RG2
			 * and RG3 and does NOT have access to Region RG1. 2. Other region
			 * view agreement is done between: RG3 and RG1 and NOT between RG1
			 * and RG2. 3. User U1 is assigned to view other region view of RG1
			 * from RG3. 4. User U1 has the right to view custom view in RG2. 5.
			 * User U1 does NOT have access to Region RG1. 6. User U1 has 'Setup
			 * resources' right in region RG2. 7. In region RG1, RS1 is created
			 * under RT1 providing address and is shared. Expected Result:No
			 * Expected Result
			 */

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
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
						strNumStatTypeValue, strStatType1, strNumStatTypDefn,
						false);
				seleniumPrecondition.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strStatType1);
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
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, true,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
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

			// Region2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user
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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			// fetch Region value
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn3);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Region3 data
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn3);
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRegionvalue = "2211";// Amarillo, TX
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, true);
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
			 * STEP : Action:Login as user U1 in regin RG2 and navigate to
			 * Preferences>>Customized view and click on 'Add more resources'
			 * and search for RS1, select it and click on 'Add to custom view'.
			 * Expected Result:'Edit Custom View' screen is displayed.
			 */
			// 426456
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResource, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Options and select status types and save.
			 * Expected Result:Custom view is displayed with RS.
			 */
			// 426462
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { strStatType1 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show Map'. Expected Result:Map format of
			 * custom view is displayed.
			 */
			// 426464
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on Resource icon of RS1. Expected
			 * Result:'Edit info' link is not present in the resource pop up
			 * window.
			 */
			// 426469

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.varEditInfolink(selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'View Info'. Expected Result:'Edit
			 * resource details' link is not present in the resource detail
			 * screen.
			 */
			// 426471
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-71296";
			gstrTO = "Verify that the resource of RG1 cannot be edited from RG2 when user has access to only RG2 and RG3; other region view agreement is done between RG3 and RG1.";
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
/*****************************************************************************************
'Description	:Verify that custom view can be created by selecting region RG1's resource
                 from RG2 when user has access to only RG2 and RG3; other region view 
                 agreement is done between RG3 and RG1.
'Arguments		:None
'Returns		:None
'Date			:11/27/2012
'Author			:QSG
'-----------------------------------------------------------------------------------------
'Modified Date				                                               Modified By
'Date					                                                   Name
*******************************************************************************************/

	@Test
	public void testBQS71256() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Regions objRegions = new Regions();
		General objGeneral = new General();
		try {
			gstrTCID = "71256"; // Test Case Id
			gstrTO = " Verify that custom view can be created by selecting region RG1's " +
					"resource from RG2 when user has access to only RG2 and RG3; other" +
					" region view agreement is done between RG3 and RG1.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			// user
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = "Amarillo, TX";
			String strRegn2 = rdExcel.readData("Login", 3, 4);
			String strRegn3 = "Statewide Oklahoma";
			String strRegionValue[] = new String[1];

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			// role based
			String statrNumTypeName = "rNST" + strTimeText;
			String statrTextTypeName = "rTST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			String statrSaturatTypeName = "rSST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;

			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";

			// private
			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String str_privateStatusTypeValues[] = new String[4];

			String str_privateStatusName1 = "pSa" + strTimeTxt;
			String str_privateStatusName2 = "pSb" + strTimeTxt;

			String str_privateStatusValue[] = new String[2];
			str_privateStatusValue[0] = "";
			str_privateStatusValue[1] = "";

			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String str_sharedStatusName1 = "sSa" + strTimeTxt;
			String str_sharedStatusName2 = "sSb" + strTimeTxt;

			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			// rolebased event related
			String staterNumTypeName = "erNST" + strTimeText;
			String staterTextTypeName = "erTST" + strTimeText;
			String staterMultiTypeName = "erMST" + strTimeText;
			String staterSaturatTypeName = "erSST" + strTimeText;
			String str_roleEventSTValues[] = new String[4];

			String str_rolEventStatusName1 = "eS1" + strTimeTxt;
			String str_rolEventStatusName2 = "eS2" + strTimeTxt;

			String str_roleventStatusValue[] = new String[2];
			str_roleventStatusValue[0] = "";
			str_roleventStatusValue[1] = "";

			// private event related
			String statepNumTypeName = "epNST" + strTimeText;
			String statepTextTypeName = "epTST" + strTimeText;
			String statepMultiTypeName = "epMST" + strTimeText;
			String statepSaturatTypeName = "epSST" + strTimeText;
			String str_privatEventSTValues[] = new String[4];

			String str_peventStatusName1 = "ep1" + strTimeTxt;
			String str_peventStatusName2 = "ep2" + strTimeTxt;

			String str_peventStatusValue[] = new String[2];
			str_peventStatusValue[0] = "";
			str_peventStatusValue[1] = "";

			// Shred event related
			String stateNumTypeName = "eSNST" + strTimeText;
			String stateTextTypeName = "eSTST" + strTimeText;
			String stateMultiTypeName = "eSMST" + strTimeText;
			String stateSaturatTypeName = "eSSST" + strTimeText;
			String str_eventStatusTypeValues[] = new String[4];

			String str_eventStatusName1 = "eSa" + strTimeTxt;
			String str_eventStatusName2 = "eSb" + strTimeTxt;

			String str_eventStatusValue[] = new String[2];
			str_eventStatusValue[0] = "";
			str_eventStatusValue[1] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResourceA = "AutoRs1_A" + strTimeText;
			String strResourceB = "AutoRs1_B" + strTimeText;
			String strResourceC = "AutoRs1_C" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[3];
			String strCategory = "(Any)";
			String strCityZipCd = "";

			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			/*
			 * STEP : Action:Preconditions: 1. User U1 has access to Regions RG2
			 * and RG3 and does NOT have access to Region RG1. 2. Other region
			 * view agreement is done between: RG3 and RG1 and NOT between RG1
			 * and RG2. 3. User U1 is assigned to view other region view of RG1
			 * from RG3. 4. User U1 has the right to view custom view and the
			 * right to edit status change preferences in RG2. Email and pager
			 * addresses are provided for user U1 5. ETST1 (Event related Text)
			 * SST1 (Saturation score) and ESST1 (Event related Saturation
			 * score). In Region RG1: RT1 is associated with the following
			 * status types:
			 * 
			 * Shared: NST1 (Number), ENST1 (Event related Number) MST1 (Multi),
			 * EMST1 (Event related Multi) TST1 (Text), ETST1 (Event related
			 * Text) SST1 (Saturation score) and ESST1 (Event related Saturation
			 * score)
			 * 
			 * Role-based: rNST1 (Number), rENST1 (Event related Number) rMST1
			 * (Multi), rEMST1 (Event related Multi) rTST1 (Text), rETST1 (Event
			 * related Text) rSST1 (Saturation score) rESST1 (Event related
			 * Saturation score)
			 * 
			 * Private: pNST1 (Number), pENST1 (Event related Number) pMST1
			 * (Multi), pEMST1 (Event related Multi) pTST1 (Text), pETST1 (Event
			 * related Text) pSST1 (Saturation score) pESST1 (Event related
			 * Saturation score) 6. RS1a, RS1b and RS1c are created under RT1
			 * with address Expected Result:No Expected Result
			 */
			// 425788
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
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
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrTextTypeName);
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
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, true);
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
						strStatTypDefn, true);
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
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, str_roleStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
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
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// private status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpNumTypeName);
				if (str_privateStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statpTextTypeName);
				if (str_privateStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpSaturatTypeName);
				if (str_privateStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statpMultiTypeName);
				if (str_privateStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, str_privateStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName1);
				if (str_privateStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privateStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								str_privateStatusName2);
				if (str_privateStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// Shared status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statsNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
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
						seleniumPrecondition, strTSTValue, statsTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, strSSTValue, statsSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
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
						seleniumPrecondition, strMSTValue, statsMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// event related rolebased
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, staterNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleEventSTValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, staterNumTypeName);
				if (str_roleEventSTValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, staterTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleEventSTValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								staterTextTypeName);
				if (str_roleEventSTValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue, staterSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleEventSTValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								staterSaturatTypeName);
				if (str_roleEventSTValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, staterMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleEventSTValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								staterMultiTypeName);
				if (str_roleEventSTValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, staterMultiTypeName, str_rolEventStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, staterMultiTypeName, str_rolEventStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleventStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								staterMultiTypeName, str_rolEventStatusName1);
				if (str_roleventStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleventStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								staterMultiTypeName, str_rolEventStatusName2);
				if (str_roleventStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// event related shred
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, stateNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, stateNumTypeName);
				if (str_eventStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, stateTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, stateTextTypeName);
				if (str_eventStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue, stateSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								stateSaturatTypeName);
				if (str_eventStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, stateMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								stateMultiTypeName);
				if (str_eventStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, stateMultiTypeName, str_eventStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, stateMultiTypeName, str_eventStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, stateMultiTypeName,
								str_eventStatusName1);
				if (str_eventStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, stateMultiTypeName,
								str_eventStatusName2);
				if (str_eventStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			// event related private
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, statepNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statepNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privatEventSTValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statepNumTypeName);
				if (str_privatEventSTValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, statepTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statepTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privatEventSTValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statepTextTypeName);
				if (str_privatEventSTValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue, statepSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statepSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privatEventSTValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statepSaturatTypeName);
				if (str_privatEventSTValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, statepMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statepMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_privatEventSTValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statepMultiTypeName);
				if (str_privatEventSTValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statepMultiTypeName, str_peventStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statepMultiTypeName, str_peventStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_peventStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statepMultiTypeName, str_peventStatusName1);
				if (str_peventStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_peventStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statepMultiTypeName, str_peventStatusName2);
				if (str_peventStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Resource Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privateStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleEventSTValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleEventSTValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleEventSTValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_roleEventSTValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privatEventSTValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privatEventSTValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privatEventSTValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_privatEventSTValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[3] + "']");

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

			// Resources
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// /RSa
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResourceA, strAbbrv, strResrcTypName,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResourceA);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RSb
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResourceB, strAbbrv, strResrcTypName,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResourceB);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RSc
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResourceC, strAbbrv, strResrcTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResourceC);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[2] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// event template
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
				String[] strStatusTypeval = { str_eventStatusTypeValues[0],
						str_eventStatusTypeValues[1],
						str_eventStatusTypeValues[2],
						str_eventStatusTypeValues[3],
						str_privateStatusTypeValues[0],
						str_privateStatusTypeValues[1],
						str_privateStatusTypeValues[2],
						str_privateStatusTypeValues[3],

						str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3],
						str_roleEventSTValues[0], str_roleEventSTValues[1],
						str_roleEventSTValues[2], str_roleEventSTValues[3],

						str_privatEventSTValues[0], str_privatEventSTValues[1],
						str_privatEventSTValues[2], str_privatEventSTValues[3] };

				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResourceA, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResourceB, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResourceC, true, false);
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
			// Region2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
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
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// fetch Region value
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn3);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(seleniumPrecondition, strUserName1,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Region3 data
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn3);
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRegionvalue = "2211";// Amarillo, TX
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, true);
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
			 * STEP : Action:7. RS1a and RS1b are shared and RS1c is not shared.
			 * 8. Event EVE under template ET is created selecting RS1a, RS1b
			 * and RS1c. Expected Result:No Expected Result
			 */
			// 425798

			/*
			 * STEP : Action:Login as User U1 in RG2, navigate to
			 * Preferences>>Customized view and click on 'Add more resources'
			 * and search for resources RS1c. Expected Result:RS1c is not
			 * displayed.
			 */
			// 425927
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesVarErrMsg(selenium,
						strResourceC, strCategory, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Search for resource RS1a. Expected Result:RS1a is
			 * displayed.
			 */
			// 425929

			/*
			 * STEP : Action:Select RS1a and search for resource RS1b. Expected
			 * Result:RS1b is displayed.
			 */
			// 425934

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				String[] strResources = { strResourceA, strResourceB };
				strFuncResult = objPreferences.findResources(selenium,
						strResources, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select RS1b and search for resources RS1a and RS1b.
			 * Expected Result:RS1a and RS1b are selected.
			 */
			// 425935
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResourceA, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResourceB, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertTrue(selenium
							.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResourceA
									+ "']/parent::tr/td[1]/input"));
					assertTrue(selenium
							.isChecked("//table[@id='tbl_resourceID']/tbody/tr/td[2][text()='"
									+ strResourceB
									+ "']/parent::tr/td[1]/input"));
					log4j.info(strResourceA + " And" + strResourceB
							+ " are selected.");
				} catch (AssertionError Ae) {
					log4j.info(strResourceA + " And" + strResourceB
							+ " are NOT selected.");
					strFuncResult = strResourceA + " And" + strResourceB
							+ " are NOT selected.";
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Add to custom view'. Expected
			 * Result:'Edit custom view' screen is displayed with resources RS1a
			 * and RS1b under section RT1. The resource name is displayed inside
			 * the section as follows: Resource name [Resource type name]
			 */
			// 425936
			try {
				assertEquals("", strFuncResult);
				// click on Add To Custom View
				selenium.click(propElementDetails
						.getProperty("EditCustomView.FindRes.AddToCustomView"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='itemBox']/ul[2]/li[1]/div[text()='"
						+ ""
						+ strResourceA
						+ " ["
						+ strResrcTypName
						+ " - Amarillo, TX]']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j
						.info("'Edit custom view' screen is displayed with resources."
								+ strResourceA);
			} catch (AssertionError Ae) {
				log4j
						.info("'Edit custom view' screen is NOT displayed with resources."
								+ strResourceA);
				strFuncResult = "'Edit custom view' screen is NOT displayed with resources."
						+ strResourceA;
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='itemBox']/ul[2]/li[2]/div[text()='"
						+ ""
						+ strResourceB
						+ " ["
						+ strResrcTypName
						+ " - Amarillo, TX]']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j
						.info("'Edit custom view' screen is displayed with resources."
								+ strResourceB);
			} catch (AssertionError Ae) {
				log4j
						.info("'Edit custom view' screen is NOT displayed with resources."
								+ strResourceB);
				strFuncResult = "'Edit custom view' screen is NOT displayed with resources."
						+ strResourceB;
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Options'. Expected Result:'"Edit Custom
			 * View Options (Columns)' screen is displayed. NST1, MST1, TST1,
			 * SST1 are displayed under the Uncategorized section. ENST1, EMST1,
			 * ETST1, ESST1, rNST1, pNST1, rENST1, pENST1, rMST1, pMST1, rEMST1,
			 * pEMST1, rTST1, pTST1, rETST1, pETST1, rSST1, pSST1, rESST1 and
			 * pESST1 are NOT displayed."
			 */
			// 425937
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences
						.chkSTPresenceInEditCustViewOptionPage(selenium,
								strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName,
						staterNumTypeName, staterTextTypeName,
						staterMultiTypeName, staterSaturatTypeName,
						statepNumTypeName, statepTextTypeName,
						statepMultiTypeName, statepSaturatTypeName,
						stateNumTypeName, stateTextTypeName,
						stateMultiTypeName, stateSaturatTypeName };

				strFuncResult = objPreferences.checkSTsInEditCustViewOptions(
						selenium, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select all 4 status types and select the following
			 * 4 options and save: 1. Display comments as an additional column
			 * 2. Display last update time as an additional column 3. Display
			 * last update user as an additional column 4. Show summary totals
			 * of numeric fields Expected Result:Custom view screen is displayed
			 * with Resources RS1a and RS1b as two rows under the column with
			 * column header RT1 4 columns with column header as NST1, MST1,
			 * TST1 and SST1 are available for RT1 'Status type Summary' section
			 * is displayed with NST1 and SST1 'Comments', 'Last update time',
			 * 'Last update by user' columns are present. 'Summary totals'
			 * section is displayed as the bottom row for RT1 section.
			 */
			// 425938
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences.editCustomViewWith4Options(
						selenium, strStatusType, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statsMultiTypeName,
						statsNumTypeName, statsSaturatTypeName,
						statsTextTypeName };
				String[] strResources = { strResourceA, strResourceB };
				strFuncResult = objPreferences
						.verifyRTSTAndRSInCustView(selenium, strResrcTypName,
								strResources, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='viewContainer']/table[2]/thead/tr/th[2][text()='Status Type Summary']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j.info("'Status type Summary' section is displayed");
			} catch (AssertionError Ae) {
				log4j.info("'Status type Summary' section is NOT displayed");
				strFuncResult = "'Status type Summary' section is NOT displayed";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='viewContainer']/table[2]/thead/tr/th[2][text()="
						+ "'Status Type Summary']/ancestor::table/tbody/tr/td[2][text()='"
						+ statsNumTypeName + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j.info("'Status type Summary' section is displayed with "
						+ statsNumTypeName);
			} catch (AssertionError Ae) {
				log4j
						.info("'Status type Summary' section is NOT displayed with "
								+ statsNumTypeName);
				strFuncResult = "'Status type Summary' section is NOT displayed with "
						+ statsNumTypeName;
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='viewContainer']/table[2]/thead/tr/th[2][text()="
						+ "'Status Type Summary']/ancestor::table/tbody/tr/td[2][text()='"
						+ statsSaturatTypeName + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j.info("'Status type Summary' section is displayed with "
						+ statsSaturatTypeName);
			} catch (AssertionError Ae) {
				log4j
						.info("'Status type Summary' section is NOT displayed with "
								+ statsSaturatTypeName);
				strFuncResult = "'Status type Summary' section is NOT displayed with "
						+ statsSaturatTypeName;
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[starts-with(@id,'rgt')]/thead/tr/th[7][text()='Comment']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j.info("'Comments' coloumn is present.");
			} catch (AssertionError Ae) {
				log4j.info("'Comments' coloumn is NOT present.");
				strFuncResult = "'Comments' coloumn is NOT present.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertEquals(
						"Last�Update",
						selenium
								.getText("//table[starts-with(@id,'rgt')]/thead/tr/th[8]"));
				log4j.info("'Last update time' coloumn is present.");
			} catch (AssertionError Ae) {
				log4j.info("'Last update time' coloumn is NOT present.");
				strFuncResult = "'Last update time' coloumn is NOT present.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertEquals(
						"By�User",
						selenium
								.getText("//table[starts-with(@id,'rgt')]/thead/tr/th[9]"));
				log4j.info("''Last update by user' coloumn is present.");
			} catch (AssertionError Ae) {
				log4j.info("''Last update by user' coloumn is NOT present.");
				strFuncResult = "''Last update by user' coloumn is NOT present.";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[starts-with(@id,'rgt')]/tbody/tr/td[2][text()='Summary']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j
						.info("'Summary totals' section is displayed as the bottom row for "
								+ strResrcTypName + " section.");
			} catch (AssertionError Ae) {
				log4j
						.info("'Summary totals' section is NOT displayed as the bottom row for "
								+ strResrcTypName + " section.");
				strFuncResult = "'Summary totals' section is NOT displayed as the bottom row for "
						+ strResrcTypName + " section.";
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show map' link. Expected Result:Resources
			 * RS1a and RS1b are displayed on Map.
			 */
			// 425939
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResourceA, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResourceB, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on icon of RS1a. Expected Result:The pop up
			 * window displays NST1, MST1, TST1, SST1, ENST1, EMST1, ETST1,
			 * ESST1 rNST1, pNST1, rENST1a, pENST1a, rMST1, pMST1, rEMST1,
			 * pEMST1, rTST1, pTST1, rETST1, pETST1, rSST1, pSST1, rESST1,
			 * pESST1 are NOT displayed.
			 */
			// 425940
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = { stateNumTypeName,
						stateTextTypeName, stateMultiTypeName,
						stateSaturatTypeName };
				String[] strStatusType = { statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };

				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResourceA, strEventStatType, strStatusType,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = { staterNumTypeName,
						staterTextTypeName, staterMultiTypeName,
						staterSaturatTypeName, statepNumTypeName,
						statepTextTypeName, statepMultiTypeName,
						statepSaturatTypeName };

				String[] strStatusType = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };

				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResourceA, strEventStatType, strStatusType,
								false, false);
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
			gstrTCID = "BQS-71256";
			gstrTO = "Verify that custom view can be created by selecting region RG1's resource from RG2 when user has access to only RG2 and RG3; other region view agreement is done between RG3 and RG1.";
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
	/***************************************************************************
	'Description	:Verify that user cannot view the event related status types
	                 associated with a private event of region RG1 from RG2.
	'Arguments		:None
	'Returns		:None
	'Date			:11/5/2012
	'Author			:QSG
	'---------------------------------------------------------------------------
	'Modified Date				                                  Modified By
	'Date					                                      Name
	***************************************************************************/
	@Test
	public void testBQS71293() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		General objMail = new General();
		Regions objRegions = new Regions();

		try {
			gstrTCID = "71293"; // Test Case Id
			gstrTO = " Verify that user cannot view the event related status"
					+ " types associated with a private event of region RG1 from RG2.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = rdExcel.readData("Login", 16, 4); //"Amarillo, TX";
			String strRegn2 = rdExcel.readData("Login", 3, 4);
			String strRegn3 = rdExcel.readData("Login", 7, 4);//"Statewide Oklahoma";
			String strRegionValue[] = new String[2];

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			// Shared
			String statsNumTypeName = "sNST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			String statsMultiTypeName = "sMST" + strTimeText;
			String statsSaturatTypeName = "sSST" + strTimeText;
			String str_sharedStatusTypeValues[] = new String[4];

			String strStatTypeColor = "Black";
			String str_sharedStatusName1 = "sSa" + strTimeText;
			String str_sharedStatusName2 = "sSb" + strTimeText;
			String str_sharedStatusValue[] = new String[2];
			str_sharedStatusValue[0] = "";
			str_sharedStatusValue[1] = "";

			String stateNumTypeName = "eNST" + strTimeText;
			String stateTextTypeName = "eTST" + strTimeText;
			String stateMultiTypeName = "eMST" + strTimeText;
			String stateSaturatTypeName = "eSST" + strTimeText;
			String str_eventStatusTypeValues[] = new String[4];

			String str_eventStatusName1 = "eSa" + strTimeText;
			String str_eventStatusName2 = "eSb" + strTimeText;
			String str_eventStatusValue[] = new String[2];
			str_eventStatusValue[0] = "";
			str_eventStatusValue[1] = "";

			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResourceA = "AutoRs1_A" + strTimeText;
			String strResourceB = "AutoRs1_B" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			String strCategory = "(Any)";
			String strCityZipCd = "";
			// Role
			String strRolesName = "Rol" + strTimeText;
			String strRoleValue = "";
			// user
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strAnyUser = "AutoUsrAny" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName1;
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// sec data
			String strSection1 = "AB1_" + strTimeText;
	
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			String[] strSStatTypep = { statsNumTypeName, statsTextTypeName,
					statsMultiTypeName, statsSaturatTypeName };
			String[] strEStatTypep = { stateNumTypeName, stateTextTypeName,
					stateMultiTypeName, stateSaturatTypeName };
				/*
			 * STEP:Preconditions:1.User U1 has access to Regions RG2 and RG3 and does NOT have access to Region RG1.
			  2. Other region view agreement is done between: RG3 and RG1 and NOT between RG1 and RG2. 
			  3. User U1 has the following rights in RG2 and RG3 
			              a. View all resources in private event
			               b. View custom view.
			                c. Editstatus change notification prefs
			                 d. Email and pager addresses are provided 
			                 e. User U1 is assigned to view other region view of RG1from RG3. 
			  4. In Region RG1: RT1 is associated with the following status types which are shared: NST1 (Number), ENST1 
			     (Event related Number) MST1 (Multi), EMST1 (Event related Multi) TST1* (Text), ETST1 (Event related Text) 
			     SST1 (Saturation score) and ESST1 (Event related Saturation score) 5. Status type section SEC1 is created selecting
			     NST1, MST1, TST1, SST1, ENST1, EMST1, ETST1 and ESST1. 6. RS1a, RS1b are created under RT1 providing address.
			  7.RS1a and RS1b are shared.
			  8.In region RG1, private event PEVE is created selecting resources RS1a and RS1b. 
			  9. No other non private event should be created selecting ENST1, EMST1, ETST1 and ESST1. Expected Result:No Expected Result
			 */
			// 426389
				log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Shared status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNSTValue, statsNumTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (str_sharedStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strTSTValue, statsTextTypeName, strStatTypDefn,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsTextTypeName);
				if (str_sharedStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strSSTValue, statsSaturatTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsSaturatTypeName);
				if (str_sharedStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strMSTValue, statsMultiTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statsMultiTypeName);
				if (str_sharedStatusTypeValues[3].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName, str_sharedStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName1);
				if (str_sharedStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_sharedStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statsMultiTypeName,
								str_sharedStatusName2);
				if (str_sharedStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// event related statustypes
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, stateNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateNumTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, stateNumTypeName);
				if (str_eventStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strTSTValue, stateTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateTextTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, stateTextTypeName);
				if (str_eventStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue, stateSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateSaturatTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								stateSaturatTypeName);
				if (str_eventStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, stateMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						stateMultiTypeName, strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								stateMultiTypeName);
				if (str_eventStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, stateMultiTypeName, str_eventStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, stateMultiTypeName, str_eventStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, stateMultiTypeName,
								str_eventStatusName1);
				if (str_eventStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_eventStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, stateMultiTypeName,
								str_eventStatusName2);
				if (str_eventStatusValue[1].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_sharedStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_sharedStatusTypeValues[3] + "']");

				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[0] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[2] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ str_eventStatusTypeValues[3] + "']");

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResourceA, strAbbrv, strResrcTypName,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResourceA);
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
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResourceB, strAbbrv, strResrcTypName,
						true, strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResourceB);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section1
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
						strRegn1);
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
				String[] strStatTypeArr = { stateNumTypeName,
						stateTextTypeName, stateMultiTypeName,
						stateSaturatTypeName, statsNumTypeName,
						statsTextTypeName, statsMultiTypeName,
						statsSaturatTypeName };
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Private event
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
				strFuncResult="";
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeVal = { strRsTypeValues[0] };
				String[] strStatusTypeval = { str_sharedStatusTypeValues[0],
						str_sharedStatusTypeValues[1],
						str_sharedStatusTypeValues[2],
						str_sharedStatusTypeValues[3],

						str_eventStatusTypeValues[0],
						str_eventStatusTypeValues[1],
						str_eventStatusTypeValues[2],
						str_eventStatusTypeValues[3] };

				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(
						seleniumPrecondition, strTempName, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try{assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName, strEveName, strTempDef, false);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Event.CreateEve.private"));
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResourceA, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResourceB, true, false);
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

			// Role

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
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, false);

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
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Any User

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strAnyUser, strInitPwd, strConfirmPwd, strUsrFulName);
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
						seleniumPrecondition, strResourceA, strRSValue[0], false, true,
						false, true);
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
			// Region2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.OverrideViewingRestrictions");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			// fetch Region value
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn3);
				if (strRegionValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue[1] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn1);
				if (strRegionValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(seleniumPrecondition, strUserName1,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName1, strRegionValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Region3 data
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn3);
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
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionValue[1], true, true);
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
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");

		/*
		 * STEP : Action:Login as user U1 in region RG2 and create a custom
		 * view selecting RS1a and RS1b. Expected Result:'Custom View -
		 * Table' screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResourceA, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRegion = "(Any)";
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						selenium, strResourceB, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statsNumTypeName, statsTextTypeName,
						statsMultiTypeName, statsSaturatTypeName };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on resource name RS1a on the custom view.
		 * Expected Result:Only NST1, MST1, TST1 and SST1 are displayed on
		 * resource detail screen ENST1, EMST1, ETST1 and ESST1 are NOT
		 * displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strSStatTypep);
				for (int i = 0; i < strEStatTypep.length; i++) {
					try {
						assertFalse(selenium
								.isElementPresent("//table[starts-with(@id,"
										+ "'stGroup')]/tbody/tr/td[2]/a[text()='"
										+ strEStatTypep[i] + "']"));
						log4j.info("The Status Type " + strEStatTypep[i]
								+ " is NOT displayed on the "
								+ "view resource detail screen. ");
					} catch (AssertionError Ae) {
						log4j.info("The Status Type " + strEStatTypep[i]
								+ " is displayed on"
								+ " the view resource detail screen. ");
						strFuncResult = "The Status Type " + strEStatTypep[i]
								+ " is displayed on "
								+ "the view resource detail screen. ";
						gstrReason = strFuncResult;

					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on 'Show Map' link. Expected Result:Resources
		 * RS1a and RS1b are displayed on Map.
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
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResourceA, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verRSInFindResDropDown(selenium,
						strResourceB, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the icon of RS1a. Expected Result:Only
		 * NST1, MST1, TST1 and SST1 are displayed on the pop up window
		 * ENST1, EMST1, ETST1 and ESST1 are NOT displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResourceA, strEventStatType, strSStatTypep,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatType = selenium.getText(propElementDetails
						.getProperty("ViewMap.ResPopup.StatTypeList"));
				for (int i = 0; i < strEStatTypep.length; i++) {
					try {
						assertFalse(strStatType.contains(strEStatTypep[i]));
						log4j.info("Role Based Status type " + strEStatTypep[i]+ " is NOT displayed");
					} catch (AssertionError ae) {
						log4j.info("Role Based Status type " + strEStatTypep[i]
								+ " is still displayed");
						strFuncResult = " Role Based Status type "
								+ strEStatTypep[i] + " is still displayed";
						gstrReason = strFuncResult;
					}					
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Preferences>>Status Change Prefs, add
		 * resource RS1a and select email, web and pager for ENST1, EMST1
		 * and ESST1 and save the page. Expected Result:'My Status Change
		 * Preferences' screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResourceA, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strEditAbove = "200";
				String strEditBelow = "40";
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strEditAbove, strEditBelow, strRSValue[0],
								str_eventStatusTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strEditAbove = "200";
				String strEditBelow = "40";
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strEditAbove, strEditBelow, strRSValue[0],
								str_eventStatusTypeValues[3], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strEditAbove = "200";
				String strEditBelow = "40";
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strEditAbove, strEditBelow, strRSValue[0],
								str_eventStatusTypeValues[2], true, true);
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
		 * STEP : Action:Login as any user in region RG1 who can update the
		 * statuses of ENST1, EMST1 and ESST1 for RS1a, and update these
		 * statuses. Expected Result:User U1 does not receive any status
		 * change notifications.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strAnyUser,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, str_eventStatusTypeValues[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						str_eventStatusValue[0], str_eventStatusTypeValues[2],
						false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdateValue = "4";
				String strComments = "NumberST";
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdateValue, str_eventStatusTypeValues[0],
						strComments, "Regional Map View");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strSubjName="Status Change for "+strResourceA;
			int intCntSub = 0;
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 0; i < 9; i++) {
			try{
			    assertEquals("", strFuncResult);
				strFuncResult=objMail.verifyEmailSubNameAndRead(selenium, strSubjName);
			    if(strFuncResult.equals("The mail with subject " + strSubjName
			      + " is NOT present in the inbox")){
			     
			     strFuncResult="";
			     intCntSub++;
			     log4j.info("The mail with subject " + strSubjName
			       + " is  NOT present in the inbox");
			    }else{
			     log4j.info("The mail with subject " + strSubjName
			       + " is  present in the inbox");
			     gstrReason = "The mail with subject " + strSubjName
			       + " is  present in the inbox";
			    }		    
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}
			}
			try {
				assertEquals("", strFuncResult);
				if( intCntSub == 8){
					gstrResult = "PASS";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			gstrResult = "PASS";
			gstrReason = "";
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-71293";
			gstrTO = "Verify that user cannot view the event related status types associated with a private event of region RG1 from RG2.";
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
