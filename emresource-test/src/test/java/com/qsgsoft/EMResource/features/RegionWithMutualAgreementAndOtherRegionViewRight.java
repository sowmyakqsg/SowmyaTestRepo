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
' Description		:This class contains test cases from requirement
' Requirement		:Multi Region 
' Requirement Group	:Setting up Resources 
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class RegionWithMutualAgreementAndOtherRegionViewRight {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RegionWithMutualAgreementAndOtherRegionViewRight");
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
	Selenium selenium,seleniumPrecondition;
	String gstrTimeOut;
	

   /****************************************************************************************************************
	* This function is called the setup() function which is executed before every test.
	*
	* The function will take care of creating a new selenium session for every test
	*
    ****************************************************************************************************************/

		@Before
		public void setUp() throws Exception {

			dtStartDate = new Date();
			gstrBrowserName = "IE 8";
			gstrBuild = "";

			ReadEnvironment objReadEnvironment = new ReadEnvironment();
			propEnvDetails = objReadEnvironment.readEnvironment();

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


    /****************************************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and writing the execution
	* result of the test. 
	*
    ****************************************************************************************************************/

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

			gdbTimeTaken = objOFC.TimeTaken(dtStartDate);// and execution time
			String FILE_PATH = "";
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			FILE_PATH = pathProps.getProperty("Resultpath");
			Date_Time_settings dts = new Date_Time_settings();
			gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");

			// gstrBuild=PropEnvDetails.getProperty("Build");
			String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
			boolean blnwriteres = blnresult.equals("true");
			gstrReason=gstrReason.replaceAll("'", " ");
			objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
					gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
					gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
		}
		
	
	
	/***************************************************************
	'Description		:Verify that the resource of RG1 cannot be edited from RG2 when RG1 and RG2 have other region agreement.
	'Precondition		:1. User U1 is assigned to view other region view of RG1 from RG2.
	                     2. User U1 has 'Setup resources' right in region RG2.
                         3. In region RG1, RS1 is created under RT1 providing address and is shared.
						 4. In RG1, the region default view includes RS1. 		
	'Arguments		:None
	'Returns		:None
	'Date			:6/7/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS71246() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Resources objResources = new Resources();
		Views objViews = new Views();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Preferences objPreferences = new Preferences();
		ViewMap objViewMap = new ViewMap();
		Regions objRegions = new Regions();
		try {
			gstrTCID = "71246"; // Test Case Id
			gstrTO = " Verify that the resource of RG1 cannot be edited from"
					+ " RG2 when RG1 and RG2 have other region agreement.";// Test
																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			String strRegn = "Arkansas";
			String strRegionName = "Statewide Oklahoma";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Data for creating user
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strOptions = "";
			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// ST
			String strStatusTypeValues[] = new String[4];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String statTypeName3 = "AutoST_3" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strCountry = "Brown County";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Search RS
			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strState = "Indiana";
			String[] strEventStatType = {};
			String[] strRoleStatType = {};

			/*
			 * Preconditions: 1. User U1 is assigned to view other region view
			 * of RG1 from RG2. 2. User U1 has 'Setup resources' right in region
			 * RG2. 3. In region RG1, RS1 is created under RT1 providing address
			 * and is shared. 4. In RG1, the region default view includes RS1.
			 */

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			try {
				
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
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
				seleniumPrecondition.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName1);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST2
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
				seleniumPrecondition.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST6
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName3,
						strStatTypDefn, false);
				seleniumPrecondition.click("css=input['name=visibility'][value='SHARED']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(seleniumPrecondition,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName3);

				if (strStatusTypeValues[2].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strStatusTypeValues[2], true);
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

			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(seleniumPrecondition,
								strResource, strAbbrv, strResrcTypName, true,
								"FN", "LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews
						.selAndDeselShowAllStAndShowAllRTInViewPage(seleniumPrecondition,
								strViewName, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRSInEditView(seleniumPrecondition,
						strResVal, true, true);
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
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// USER
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
				String strRegionvalue = "2155";// Arkansas
				strFuncResult = objCreateUsers.slectAndDeselectOtherRegion(
						seleniumPrecondition, strRegionvalue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as user U1 in regin RG2 and navigate to
			 * Preferences>>Customized view and click on 'Add more resources'
			 * and search for RS1, select it and click on 'Add to custom view'
			 * then clik on 'options'. Expected Result:'Edit Custom View Options
			 * (Columns)' screen is displayed.
			 */
			// 425613

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
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
						strCityZipCd, strState, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select status types and save. Expected
			 * Result:Custom view is displayed with RS1.
			 */
			// 425614
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objPreferences.editCustomViewOptions(selenium,
						strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Show Map'. Expected Result:Map format of
			 * custom view is displayed.
			 */
			// 425615
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
			// 425616
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strRoleStatType,
								true, true);
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
			// 425617
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.varEditResourceLink(selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:From User U1 in RG2, navigate to Other
			 * regions>>RG1. Expected Result:Region default view of RG1 is
			 * displayed.
			 */
			// 425618
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navOtherRegionsOFRegion(selenium,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on the resource name RS1. Expected
			 * Result:'Edit resource details' link is NOT present in the
			 * resource detail screen.
			 */
			// 425620

			// click on resource link
			selenium
					.click("//div[@id='mainContainer']/div/table/tbody/tr/td[2]/a[text()='"
							+ strResource + "']");
			selenium.waitForPageToLoad(gstrTimeOut);
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.varEditResourceLink(selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-71246";
			gstrTO = "Verify that the resource of RG1 cannot be edited from RG2"
					+ " when RG1 and RG2 have other region agreement.";
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
