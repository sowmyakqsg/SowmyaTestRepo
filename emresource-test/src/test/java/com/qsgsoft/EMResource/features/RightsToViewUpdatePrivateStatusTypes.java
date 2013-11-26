
package com.qsgsoft.EMResource.features;

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
import static org.junit.Assert.*;
/**********************************************************************
' Description :This class includes Rights to view/update Private status
'			   types requirement testcases
' Precondition:
' Date		  :22-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class RightsToViewUpdatePrivateStatusTypes {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RightsToViewUpdatePrivateStatusTypes");
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
	
	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	
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

		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

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
	
	/********************************************************************************
	'Description	Provide a user the �View Resource� right on resource RS and verify
	'			   that a private status type associated with RS can be added to the 
	'				custom view only if the user has any of the resource rights on resource RS.
	'Precondition	:1. Status types ST1 and ST2 are created selecting 'Private' option.
					2. ST1 is associated with resource type RT.
					3. Resource RS is created under resource type RT with address.
					4. ST2 is associated to resource RS at the resource level.
					5. Status types ST1 and ST2 are under status type section S1.
					6. Users U1, U2, U3 and U4 are created without providing any resource rights on RS and has 'View Custom' view right. 		
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-Oct-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testBQS45738() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "BQS-45738 ";
			gstrTO = "Provide a user the �View Resource� right on resource RS " +
					"and verify that a private status type associated with RS" +
					" can be added to the custom view only if the user has any" +
					" of the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName = strUserName;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUsrFulName_3 = strUserName_3;

			String strUserName_4 = "AutoUsr_4" + System.currentTimeMillis();
			String strUsrFulName_4 = strUserName_4;

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };

		
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			
			/*1. Status types ST1 and ST2 are created selecting 'Private' option. 
		*/
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
								strStatusTypeValue, statTypeName1,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. ST1 is associated with resource type RT.
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*3. Resource RS is created under resource type RT with address. */
			
			
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

			
			/*4. ST2 is associated to resource RS at the resource level. */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[1], true);
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
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}
			
			/*5. Status types ST1 and ST2 are under status type section S1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				strSectionValue = objViews
						.fetchSectionID(seleniumFirefox, strSection1);
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
				strFuncResult = objLogin.logout(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
			 * 6. Users U1, U2, U3 and U4 are created without providing any
			 * resource rights on RS and has 'View Custom' view right.
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, false);

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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_3, strInitPwd, strConfirmPwd,
						strUsrFulName_3);

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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_4, strInitPwd, strConfirmPwd,
						strUsrFulName_4);

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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
		

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

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

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Edit user U3, provide only 'Associated with' and 'View
			 * Resource' right for RS and save. No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 6 Edit user U4, provide only 'View Resource' right for RS and
			 * save. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 7 Login as user U1, navigate to Preferences>>Customized view and
			 * select to add resources to the custom view No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Search for resource RS, select RS and click on 'Add to Custom
			 * View' No Expected Result
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objPreferences.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 9 Click on 'Options' ST1 and ST2 are displayed in the 'Edit
			 * Custom View Options (Columns)' screen under status type section
			 * S1.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 10 Select ST1 and ST2 and click on Save 'Custom View - Table' is
			 * displayed with ST1 and ST2 for resource RS.
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, false, true);
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
				String strRT = strResrctTypName;
				String strST = statTypeName2;
				String[] strResources = { strResource };
				strFuncResult = objPreferences
						.createCustomViewWithRSAndSTCustMapOrViewTable(
								selenium, strResources, strRT, strST, false,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 11 Click on 'Show map' at the top right corner of the screen RS
			 * is displayed on the map area.
			 * 
			 * RS is available on the 'Find Resource' dropdown.
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
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType1,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 13 Login as user U2, navigate to Preferences>>Customized view and
			 * select to add resources to the custom view No Expected Result
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objPreferences.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType1);
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
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, false, true);
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
				String strRT = strResrctTypName;
				String strST = statTypeName2;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType1,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 19 Login as user U3, navigate to Preferences>>Customized view and
			 * select to add resources to the custom view No Expected Result
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_3,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objPreferences.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType1);
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
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, false, true);
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
				String strRT = strResrctTypName;
				String strST = statTypeName2;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType1,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 25 Login as user U4, navigate to Preferences>>Customized view and
			 * select to add resources to the custom view No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_4,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 26 Search for resource RS, select RS and click on 'Add to Custom
			 * View' No Expected Result
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				String strRT = strResrctTypName;
				String strST = statTypeName1;
				String[] strResources = { strResource };
				strFuncResult = objPreferences.createCustomViewWithRSAndST(
						selenium, strResources, strRT, strST, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 27 Click on 'Options' ST1 and ST2 are NOT displayed in the 'Edit
			 * Custom View Options (Columns)' screen.
			 */
			
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objPreferences.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType1);
				
				if(strFuncResult.contains("Section " + strSection1 + " is NOT Present")){
					strFuncResult="";
				}else{
					strFuncResult="Section " + strSection1 + " is Present";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 28 Save the view RS is not displayed on the 'Custom View - Table'
			 */

			try {
				assertEquals("", strFuncResult);
				
				if(selenium.isElementPresent(propElementDetails
						.getProperty("View.Custom.ShowTabl"))){
					selenium.click(propElementDetails
							.getProperty("View.Custom.ShowTabl"));
					selenium.waitForPageToLoad(gstrTimeOut);
				}
				try {
					assertEquals(selenium
							.getText("//div[@id='viewContainer']/h1"),
							"No Statuses in Custom View");
					
					assertEquals(selenium
							.getText("//div[@id='viewContainer']/p"),
							"There are no statuses (columns) to display in your custom view.");
					log4j
							.info("No status types are displayed and message stating "
									+ "'No Statuses in Custom View' is displayed in 'Custom View"
									+ " - Table' screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("No status types are displayed and message stating 'No Statuses"
									+ " in Custom View' is displayed in 'Custom View - Table' screen. ");
					strFuncResult = "No status types are displayed and message stating 'No Statuses in"
							+ " Custom View' is displayed in 'Custom View - Table' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName + "," + strUserName_1 + "," + strUserName_2
								+ "," + strUserName_3 + "," + strUserName_4
								+ "/" + strInitPwd,
						statTypeName1 + "," + statTypeName2, "",
						"From 26th Step", strResource, strSection1, "",
						"",
						strSection1};

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
			gstrTCID = "BQS-45738";
			gstrTO = "Provide a user the �View Resource� right on resource" +
					" RS and verify that a private status type associated" +
					" with RS can be added to the custom view only if the " +
					"user has any of the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

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
	'Description	:Provide a user the �View Resource� right on resource RS and verify
	'				 that a private status type associated with RS can be viewed on all
	'				 the view screens only if the user has any of the resource rights on resource RS.
						'
	'Precondition	:1. Private status types ST1 and ST2 are created.
						2. ST1 is associated with resource type RT.
						3. Resource RS is created under resource type RT with address.
						4. ST2 is associated to resource RS at the resource level.
						
						5. View V1 has status type ST1, ST2 and resource RS.
						6. Event Template ET is created with ST1, ST2 and RT.
						7. Event E1 is created under ET selecting RS.
						8. Status types ST1 and ST2 are under status type section S1. 		
	'Arguments		:None
	'Returns		:None
	'Date	 		:26-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testBQS45737() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList=new EventList();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "BQS-45737 ";
			gstrTO = "Provide a user the �View Resource� right on resource RS and "
					+ "verify that a private status type associated with RS can be"
					+ " viewed on all the view screens only if the user has any of "
					+ "the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName = strUserName;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUsrFulName_3 = strUserName_3;

			String strUserName_4 = "AutoUsr_4" + System.currentTimeMillis();
			String strUsrFulName_4 = strUserName_4;

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };


			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";
			
			//Data for creating View
			String strViewName_1="autoView_1" + strTimeText;
			String strVewDescription="";
			String strViewType="";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
			 * 1. Status types ST1 and ST2 are created selecting 'Private'
			 * option.
			 */
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
								strStatusTypeValue, statTypeName1,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. ST1 is associated with resource type RT.
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS is created under resource type RT with address.
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
			 * 4. ST2 is associated to resource RS at the resource level.
			 */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5. View V1 has status type ST1, ST2 and resource RS.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				
				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
				
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6. Status types ST1 and ST2 are under status type section S1.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				strSectionValue = objViews
						.fetchSectionID(seleniumFirefox, strSection1);
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
				strFuncResult = objLogin.logout(seleniumFirefox);
		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
			 * 6. Event Template ET is created with ST1, ST2 and RT.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(selenium,
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
			 * 7. Event E1 is created under ET selecting RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult=objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(selenium,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
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
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
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

			
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
			
			/*
			 * 2 Login as a user with 'User-Setup User Accounts' right and
			 * navigate to Setup>>Users No Expected Result
			 */

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
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to create a new user, create user U1 providing 'Update
			 * Status', and 'View Resource' rights on resource RS No Expected
			 * Result
			 */
			
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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

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
			 * 4 Select to create a new user, create user U2 providing 'Run
			 * Reports', and 'View Resource' rights on resource RS No Expected
			 * Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select to create a new user, create user U3 providing
			 * 'Associated with', and 'View Resource' rights on resource RS No
			 * Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_3, strInitPwd, strConfirmPwd,
						strUsrFulName_3);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select to create a new user, create user U4 providing only
			 * 'View Resource' right on resource RS No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_4, strInitPwd, strConfirmPwd,
						strUsrFulName_4);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Login as user U1 and navigate View>>V1 Resource RS is displayed
			 * with status types ST1 and ST2.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName, strRS,
						strRTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/
			
			
			/*
			 * 8 Click on the banner of event E1 Resource RS is displayed with
			 * status types ST1 and ST2 on the 'Event Status' screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Click on the name of resource RS Status types ST1 and ST2 are
			 * displayed under status type section S1 on the 'View Resource
			 * Detail' screen.
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
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 10 Navigate View>>Map RS is displayed in the 'Find Resource'
			 * dropdown.
			 */
			/*
			 * 11 Select resource RS from the 'Find Resource' dropdown Status
			 * types ST1 and ST2 are displayed on the resource pop up window.
			 */
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 12 Login as user U2 and navigate View>>V1 Resource RS is
			 * displayed with status types ST1 and ST2.
			 */
			
			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName, strRS,
						strRTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
		/*	try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			*/
			
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 17 Login as user U3 and navigate View>>V1 Resource RS is
			 * displayed with status type ST.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_3,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strRS = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName, strRS,
						strRTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*	try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTInUserViewScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			*/
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType1, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			/*
			 * 22 Login as user U4 and navigate View>>V1 ST1 and ST2 are not
			 * displayed for Resource RS on the view screen.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_4,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"There are no resources to display in this view.");
					log4j
							.info("ST1 and ST2 are NOT  displayed for Resource RS on the view screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS on the view screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS on the view screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 23 Click on the banner of event E1 ST1 and ST2 are not displayed
			 * for Resource RS 'Event Status' screen.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"Either there are no resources participating in this event" +
							", " +
							"or you do not have viewing rights to any resources/status" +
							" types involved in this event.");
					log4j
							.info("ST1 and ST2 are NOT displayed for Resource RS 'Event Status' screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 25 Select resource RS from the 'Find Resource' dropdown ST1 and
			 * ST2 are NOT displayed on the resource pop up window.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(
							selenium
									.getText("//div[@class='emsCenteredLabel']/following-sibling::div/center"),
							"(no status reported)");
					log4j
							.info("No status types are displayed and message stating"
									+ " 'no status reported' is displayed in resource pop up window'. ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status types are displayed and message stating "
									+ "'no status reported' is NOT displayed in resource pop up window'. ");
					strFuncResult = "Status types are displayed and message stating "
							+ "'no status reported' is NOT displayed in resource pop up window'. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName + "," + strUserName_1 + "," + strUserName_2
								+ "," + strUserName_3 + "," + strUserName_4
								+ "/" + strInitPwd,
						statTypeName1 + "," + statTypeName2, strViewName_1,
						"From 26th Step", strResource, strSection1, strEveName,
						strTempName,
						strSection1};

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
			gstrTCID = "BQS-45737";
			gstrTO = "Provide a user the �View Resource� right on resource "
					+ "RS and verify that a private status type associated "
					+ "with RS can be viewed on all the view screens only if "
					+ "the user has any of the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

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
	
	
	/*********************************************************************************
	'Description	:Verify that a role can be saved by selecting a private status type
	'				 under "Select the Status Types this Role may update:" section.
					 Role R1 is created  
	'Arguments		:None
	'Returns		:None
	'Date	 		:22-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS49738() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application		
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles=new Roles();
		StatusTypes objStatusTypes=new StatusTypes();
		

		try {
			gstrTCID = "BQS-49738 ";
			gstrTO = "Verify that a role can be saved by selecting a private status type"
					+ "under Select the Status Types this Role may update: section.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST_" +strTimeText;
			String strStatTypDefn = "Automation";
			 // Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRolesName1 = "Rol_" + strTimeText;
			String strRoleValue = "";

			
			/*Precondition: Role R1 is created 	
			 * 	No Expected Result */
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID  + " EXECUTION STATRTS~~~~~");
				
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
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError ae) {
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
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID  + " EXECUTION ENDS~~~~~");
				
			/*
			 * 2 Login as RegAdmin and Navigate to Setup>>Status Types
			 * "Status Type List" screen is displayed.
			 */
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");	
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
					strFuncResult=objStatusTypes.navStatusTypList(selenium);			
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			/*
			 * 3 Create a private status type ST (i.e. by selecting the
			 * option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility") without selecting any roles
			 * under "Roles with update rights:" section ST is listed in the
			 * "Status Type List" screen
			 */
				
				try {
					assertEquals("", strFuncResult);
					String strStatusTypeValue = "Number";
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							selenium, strStatusTypeValue, statTypeName,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					selenium.click("css=input[name='visibility'][value='PRIVATE']");
					if (selenium.isChecked("css=input[name='SELECT_ALL'][value='roleUpdate']")) {
						selenium.click("css=input[name='SELECT_ALL'][value='roleUpdate']");
					}
					strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
							statTypeName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strStatusTypeValues[0] = objStatusTypes
							.fetchSTValueInStatTypeList(selenium, statTypeName);
					if (strStatusTypeValues[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				/*
				 * 4 Navigate to Setup>>Roles, select to edit role R1
				 * "Edit Role" screen is displayed.
				 * 
				 * Private status type ST is:
				 * 
				 * 1. Unchecked and disabled under
				 * "Select the Status Types this Role may view:" section. 
				 * 2.Unchecked and enabled under
				 * "Select the Status Types this Role may update:" section.
				 */
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult=objRoles.navRolesListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);					
					strFuncResult=objRoles.navEditRolesPge(selenium, strRolesName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.privateSTinEditRole(
							selenium, strStatusTypeValues[0], false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				
				/*
				 * 5 Select ST under
				 * "Select the Status Types this Role may update:" section and
				 * save the role R1. R1 is saved and the user is taken to
				 * "Roles List" screen.
				 */
				
				try {
					assertEquals("", strFuncResult);					
					String[][] strSTViewValue={};
					String[][] strSTUpdateValue={{strStatusTypeValues[0],"true"}};
					strFuncResult=objRoles.slectAndDeselectSTInCreateRole(selenium, false, false,
							strSTViewValue, strSTUpdateValue, false)	;				
				} catch (AssertionError Ae) {

					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.savAndVerifyRoles(selenium,
							strRolesName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * 6 Select to edit role R1 "Edit Role" screen is displayed.
				 * 
				 * Private status type ST is:
				 * 
				 * 1. Unchecked and disabled under
				 * "Select the Status Types this Role may view:" section. 2.
				 * Remains checked and enabled under
				 * "Select the Status Types this Role may update:" section.
				 */
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult=objRoles.navRolesListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult=objRoles.navEditRolesPge(selenium, strRolesName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.privateSTinEditRole(
							selenium,  strStatusTypeValues[0], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.savAndVerifyRoles(selenium, strRolesName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				/*
				 * 7 Select to create a new role "Create New Role" screen is
				 * displayed.
				 * 
				 * Private status type ST is:
				 * 
				 * 1. Unchecked and disabled under
				 * "Select the Status Types this Role may view:" section.
				 *  2.Unchecked and enabled under
				 * "Select the Status Types this Role may update:" section.
				 */
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.rolesMandatoryFlds(selenium,
							strRolesName1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
								
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.privateSTinEditRole(
							selenium,  strStatusTypeValues[0], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*
				 * 8 Select ST under
				 * "Select the Status Types this Role may update:" section and
				 * save the role R2. R2 is listed in the "Roles List" screen.
				 */ 

				try {
					assertEquals("", strFuncResult);					
					String[][] strSTViewValue={};
					String[][] strSTUpdateValue={{strStatusTypeValues[0],"true"}};
					strFuncResult=objRoles.slectAndDeselectSTInCreateRole(selenium, false, false,
							strSTViewValue, strSTUpdateValue, false)	;				
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}


				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.savAndVerifyRoles(selenium,
							strRolesName1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				/*
				 * 9 Select to edit role R2 "Edit Role" screen is displayed.
				 * 
				 * Private status type ST is:
				 * 
				 * 1. Unchecked and disabled under
				 * "Select the Status Types this Role may view:" section.
				 *  2.Remains checked and enabled under
				 * "Select the Status Types this Role may update:" section.
				 */
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.navEditRolesPge(selenium,
							strRolesName1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.privateSTinEditRole(
							selenium,  strStatusTypeValues[0], true);
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-49738 ";
			gstrTO = "Verify that a role can be saved by selecting a private status type"
					+ "under Select the Status Types this Role may update: section.";
			gstrResult = "FAIL";
			gstrReason = "";
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
'Description		:Create role R1 without selecting a private status type ST under 'select the Status Types this Role may Update' section & verify that ST can be viewed by any user with role R1 in the following setup pages:<br>
					a. Status Type List<br>
					b. Create/Edit Resource Type<br>
					c. Create/Edit Role<br>
					d. Edit Resource Level Status Types<br>
					e. Create New/Edit/Copy View<br>
					f. Edit Resource Detail View Sections<br>
					g. Create/Edit Event Temp<br>
					e. Edit event<br>
					f. While configuring the form
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:8/27/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS48964() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Resources objResources = new Resources();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		Forms objForms = new Forms();
		try {
			gstrTCID = "48964"; // Test Case Id
			gstrTO = " Create role R1 without selecting a private status type ST under "
					+ "'select the Status Types this Role may Update' section & verify"
					+ " that ST can be viewed by any user with role R1 in the following setup pages:<br>"
					+ "a. Status Type List<br>"
					+ "b. Create/Edit Resource Type<br>"
					+ "c. Create/Edit Role<br>"
					+ "d. Edit Resource Level Status Types<br>"
					+ "e. Create New/Edit/Copy View<br>"
					+ "f. Edit Resource Detail View Sections<br>"
					+ "g. Create/Edit Event Temp<br>"
					+ "e. Edit event<br>"
					+ "f. While configuring the form";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName1 = "AutoST_1" + strTimeText;
			String statTypeName2 = "AutoST_2" + strTimeText;
			String strStatTypDefn = "Automation";
			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValues[] = new String[1];

			// Data for creating user
			String strUserName = "AutoUsr" + System.currentTimeMillis();
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

			// View
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			// Role
			String strRolesName = "AutoRol_" + strTimeText;
			String strRoleValue = "";

	
			String strTempName = "Autotmp" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_" + strTimeText;
			String strInfo = "Automation";

			// form
			String strFormTempTitle = "OF" + strTimeText;
			String strFormActiv = "As Certain Status Changes";
			String strFormDesc = "Automation";
			/*
			 * STEP : Action:Preconditions: 1. User A is created with the
			 * following rights: a.Setup Status Types. b.Setup Resource Types.
			 * c.Setup Resources. d.Setup Roles. e.Maintain Events. f.Maintain
			 * Event Templates. g.Configure region views. h.Form - User may
			 * create and modify forms 2. Resource type RT is associated with a
			 * private status type pST. 3. Resource RS is created under resource
			 * Type RT. 4.View V1 is created selecting pST and RT. 5.Event
			 * template ET is created selecting ST and RT. 6.Event E1 is created
			 * under the template ET selecting RS. 7. User A has only
			 * "View Resource" right and not any of the affiliated resource
			 * rights (Update Status/Run Reports/Associated with). Expected
			 * Result:No Expected Result
			 */
			// 279475

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
				seleniumPrecondition.click("css=input['name=visibility'][value='PRIVATE']");
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
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, false);
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

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

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
				String[] strSTvalue = { strStatusTypeValues[0] };
				String[] strRSValue = { strResVal };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
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
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition,
						strRsTypeValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Event template
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
				String[] strStatusTypeval = { strStatusTypeValues[0] };
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
						strTempName, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(
						seleniumPrecondition, strResource, true, false);
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
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.MaintEvnts");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
						.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpRoles");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, false, false, false,
						true);
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

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin and navigate to Setup >> Roles
			 * Expected Result:"Roles List" screen is displayed
			 */
			// 279476

			try {
				
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

			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create a role R1 without selecting status type ST
			 * under 'select the Status Types this Role may Update' section
			 * Expected Result:R2 is listed in the "Roles List" screen
			 */
			// 279480

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = { strStatusTypeValues[1] };
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName);
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
			 * STEP : Action:Navigate to Setup >> Users, edit user A and assign
			 * role R1 to user A Expected Result:No Expected Result
			 */
			// 279488

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
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
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
			 * STEP : Action:Login as user A and navigate to Setup >> Resource
			 * Types, select to edit resource type RT. Expected Result:pST is
			 * displayed in the "Edit Resource Type" screen
			 */
			// 279493
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(
						selenium, statTypeName1, strStatusTypeValues[0], true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.saveAndNavToResTypeList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resource Types and select to
			 * create a new resource type Expected Result:pST is displayed in
			 * the "Create New Resource Type" screen
			 */
			// 279497
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navToCreateNewResrcTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.checkSTInEditResTypePage(
						selenium, statTypeName1, strStatusTypeValues[0], true,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Roles and select to create a
			 * new role Expected Result:In the "Create New Role" screen, status
			 * Type pST is displayed under following sections: a.Select the
			 * Status Types this Role may view. b.Select the Status Types this
			 * Role may update By default the checkbox associated with pST is
			 * disabled under "Select the Status Types this Role may view."
			 * section and is enabled under
			 * "Select the Status Types this Role may update." section.
			 */
			// 279534
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(
						selenium, strStatusTypeValues[0], true,
						strStatusTypeValues[0], true, statTypeName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.varSTDisabilityInEditRolePgeForViewSec(selenium,
								strStatusTypeValues[0], false, statTypeName1,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.varSTDisabilityInEditRolePgeForUpdateSec(selenium,
								strStatusTypeValues[0], true, statTypeName1,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Roles and click on 'Edit' link
			 * associated with role 'R1'. Expected Result:Status Type pST is
			 * displayed under following sections: a.Select the Status Types
			 * this Role may view. b.Select the Status Types this Role may
			 * update By default the checkbox associated with pST is disabled
			 * under "Select the Status Types this Role may
			 * view." section and is enabled under "Select the Status Types this
			 * Role may update." section.
			 */
			// 279533
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.navEditRolesPge(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.varSTPresentOrNotInEditRolePage(
						selenium, strStatusTypeValues[0], true,
						strStatusTypeValues[0], true, statTypeName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.varSTDisabilityInEditRolePgeForViewSec(selenium,
								strStatusTypeValues[0], false, statTypeName1,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles
						.varSTDisabilityInEditRolePgeForUpdateSec(selenium,
								strStatusTypeValues[0], true, statTypeName1,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Resources, click on 'Edit
			 * Status Types' link associated with resource 'RS'. Expected
			 * Result:pST is displayed under 'Additional Status Types' section
			 */
			// 279500
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.checkSTInEditRSLevePage(selenium,
						statTypeName1, strStatusTypeValues[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views and select to create a
			 * new view Expected Result:'Create New View' screen is displayed
			 * and pST is displayed under 'Select Status Types' section.
			 */
			// 279506
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[0], statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Views and click on 'Edit' link
			 * associated with view 'V1' Expected Result:'Edit View' screen is
			 * displayed pST is displayed under 'Select Status Types' section.
			 */
			// 279511

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[0], statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views and click on 'Copy' link
			 * associated with view 'V1' Expected Result:'Edit View' screen is
			 * displayed pST is displayed under 'Select Status Types' section.
			 */
			// 279517

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditViewByCopyLink(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.chkSTPresentOrNotForView(selenium,
						true, strStatusTypeValues[0], statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views and click on 'Customize
			 * Resource Detail View' Expected Result:Status typepST is displayed
			 * under 'Uncategorized' section on 'Edit Resource Detail View
			 * Sections' screen
			 */
			// 279520
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
				strFuncResult = objViews.chkSTEditResDetailViewSec(selenium,
						true, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup and click on
			 * 'Create New Event Template' button Expected Result:pST is
			 * displayed under 'Status Types' section
			 */
			// 279523
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInCreateEventTemplatePage(
						selenium, strStatusTypeValues[0], true, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Setup and click on
			 * 'Edit' link associated with event template 'ET' Expected
			 * Result:'Edit Event Template' screen is displayed with the status
			 * type pST under 'Status Types' section.
			 */
			// 279525
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventTemplatePage(
						selenium, strStatusTypeValues[0], true, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Event >> Event Management, click on
			 * 'Edit' link associated with event 'E1' Expected Result:'Edit
			 * Event' screen is displayed with status type pST under 'Status
			 * types' section.
			 */
			// 279529
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToeditEventPage(selenium,
						strEveName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.chkSTInEditEventPage(selenium,
						strStatusTypeValues[0], true, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Forms >> Configure Forms and click on
			 * 'Create New Form Template' Expected Result:'Create New Form
			 * Template' screen is displayed.
			 */
			// 279531
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navToFormConfig(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.navTocreateNewFormTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide form title and description and select 'As
			 * Status changes' from 'Form Activation' drop-down list and click
			 * on 'Next'. Expected Result:Status type pST is displayed under
			 * 'Form Activation Status Type' section
			 */
			// 279532
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms
						.filAllFldsInCreatNewFormAsStatusChanges(selenium,
								strFormTempTitle, strFormDesc, strFormActiv,
								false, false, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objForms.checkSTInFormActivationStPage(
						selenium, statTypeName1, strStatusTypeValues[0], true);
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
			gstrTCID = "BQS-48964";
			gstrTO = " Create role R1 without selecting a private status type ST under "
					+ "'select the Status Types this Role may Update' section & verify"
					+ " that ST can be viewed by any user with role R1 in the following setup pages:<br>"
					+ "a. Status Type List<br>"
					+ "b. Create/Edit Resource Type<br>"
					+ "c. Create/Edit Role<br>"
					+ "d. Edit Resource Level Status Types<br>"
					+ "e. Create New/Edit/Copy View<br>"
					+ "f. Edit Resource Detail View Sections<br>"
					+ "g. Create/Edit Event Temp<br>"
					+ "e. Edit event<br>"
					+ "f. While configuring the form";// Test Objective
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
'Description	:Provide a user the �View Resource� right on resource RS and verify
'				 that for a private status type associated with RS, status change 
'					preferences can be set if the user has any of the resource rights on resource RS.
					'
'Precondition	:1. Status types ST1 and ST2 are created selecting 'Private' option.
					2. ST1 is associated with resource type RT.
					3. Resource RS is created under resource type RT with address.
					4. ST2 is associated to resource RS at the resource level.
					5. Status types ST1 and ST2 are under status type section S1.
					6. Users U1, U2, U3 and U4 are created without providing any resource rights on RS and has 'Edit Status Change Notification Preferences' right. 	
					'
'Arguments		:None
'Returns		:None
'Date	 		:26-Sep-2012
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
**********************************************************************************/


	@Test
	public void testBQS98661() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Preferences objPreferences = new Preferences();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		

		try {
			gstrTCID = "BQS-98661 ";
			gstrTO = "Provide a user the �View Resource� right on resource RS and"
					+ " verify that for a private status type associated with RS,"
					+ " status change preferences can be set if the user has any "
					+ "of the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName = strUserName;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUsrFulName_3 = strUserName_3;

			String strUserName_4 = "AutoUsr_4" + System.currentTimeMillis();
			String strUsrFulName_4 = strUserName_4;

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };

			

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
			 * 1. Status types ST1 and ST2 are created selecting 'Private'
			 * option.
			 */
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
								strStatusTypeValue, statTypeName1,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. ST1 is associated with resource type RT.
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS is created under resource type RT with address.
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
			 * 4. ST2 is associated to resource RS at the resource level.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Status types ST1 and ST2 are under status type section S1.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}



			try{
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox,
						strArStatType1, strSection1, true);

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
				strSectionValue = objViews
						.fetchSectionID(seleniumFirefox, strSection1);
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
				strFuncResult = objLogin.logout(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
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
			 * 6. Users U1, U2, U3 and U4 are created without providing any
			 * resource rights on RS and has 'Edit Status Change Notification
			 * Preferences' right.
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U1
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
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
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

			// U2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_3, strInitPwd, strConfirmPwd,
						strUsrFulName_3);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U4
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_4, strInitPwd, strConfirmPwd,
						strUsrFulName_4);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as a user with 'User-Setup User Accounts' right and
			 * navigate to Setup>>Users No Expected Result
			 */

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

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Edit user U1, provide only 'Update Status' and 'View Resource'
			 * right for RS and save. No Expected Result
			 */
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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

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
			 * 4 Edit user U2, provide only 'Run Reports' and 'View Resource'
			 * right for RS and save. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Edit user U3, provide only 'Associated with' and 'View
			 * Resource' right for RS and save. No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Edit user U4, provide only 'View Resource' right for RS and
			 * save. No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_4, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Login as user U1, navigate to Preferences>>Status Change Prefs
			 * and click on 'Add' No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

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
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Select notification methods (email/pager and web) for ST1 and
			 * ST2 and click on Save Selected notification methods for ST1 and
			 * ST2 are displayed in 'My Status Change Preferences' screen for
			 * resource RS.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strSTvalue[0],
								statTypeName1, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strSTvalue[1],
								statTypeName2, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10 Login as user U2, navigate to Preferences>>Status Change Prefs
			 * and click on 'Add' No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 11 Search for resource RS, select RS and click on 'Notifications'
			 * ST1 and ST2 are displayed under section S1 in the 'Edit My Status
			 * Change Preferences' screen.
			 */

			/*
			 * 12 Select notification methods (email/pager and web) for ST1 and
			 * ST2 and click on Save Selected notification methods for ST1 and
			 * ST2 are displayed in 'My Status Change Preferences' screen for
			 * resource RS.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strSTvalue[0],
								statTypeName1, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strSTvalue[1],
								statTypeName2, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 13 Login as user U3, navigate to Preferences>>Status Change Prefs
			 * and click on 'Add' No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_3,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Search for resource RS, select RS and click on 'Notifications'
			 * ST1 and ST2 are displayed under section S1 in the 'Edit My Status
			 * Change Preferences' screen.
			 */

			/*
			 * 15 Select notification methods (email/pager and web) for ST1 and
			 * ST2 and click on Save Selected notification methods for ST1 and
			 * ST2 are displayed in 'My Status Change Preferences' screen for
			 * resource RS.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strSTvalue[0],
								statTypeName1, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditMySTPrfPage(selenium,
								strSectionValue, strSection1, strArStatType1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strResVal, strSTvalue[1],
								statTypeName2, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 16 Login as user U4, navigate to Preferences>>Status Change Prefs
			 * and click on 'Add' No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_4,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strResVal);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strRT = selenium
						.getText("//div[@id='mainContainer']/form/h1");
				log4j.info(strRT);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/h1[contains(text(),'"
									+ strResource
									+ "')]/following-sibling::"
									+ "table/tbody/tr/td[text()='No visible status types are available.']"));

					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/h1[contains(text(),'"
									+ strResrctTypName
									+ "')]/following-sibling::"
									+ "table/tbody/tr/td[text()='No visible status types are available.']"));

					log4j
							.info("'No visible status types are available' message "
									+ "is displayed under the selected resource and its "
									+ "corresponding resource type. ");
				} catch (AssertionError Ae) {

					log4j
							.info("'No visible status types are available' message "
									+ "is NOT displayed under the selected resource and its "
									+ "corresponding resource type. ");
					strFuncResult = "'No visible status types are available' message "
							+ "is NOT displayed under the selected resource and its "
							+ "corresponding resource type. ";
					gstrReason = strFuncResult;

				}

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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-98661";
			gstrTO = "Provide a user the �View Resource� right on resource RS and "
					+ "verify that for a private status type associated with RS, "
					+ "status change preferences can be set if the user has any of "
					+ "the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

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
	'Description	:Verify that when the resource rights are removed for a user, the 
	'				user cannot view a private status type for the respective resource.
	'Precondition	:1. Private status types ST1 and ST2 are created.
					2. ST1 is associated with resource type RT.
					3. Resource RS is created under resource type RT with address.
					4. ST2 is associated to resource RS at the resource level.
					
					5. View V1 has status type ST1, ST2 and resource RS.
					6. Event Template ET is created with ST1, ST2 and RT.
					7. Event E1 is created under ET selecting RS.
					8. Status types ST1 and ST2 are under status type section S1.
					
					9. Users U1, U2 and U3 have 'Update Status', 'Run Report' and 'Associated with' right on resource RS respectively.
					10. all 3 users have 'View Resource' right on resource RS and has 'View Custom View' right.
					11. All 3 users have added resource RS and status types ST1 and ST2 to their respective custom view. 		
	'Arguments		:None
	'Returns		:None
	'Date	 		:03-Oct-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/


	@Test
	public void testBQS48405() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		Preferences objPreferences = new Preferences();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		EventSetup objEventSetup = new EventSetup();

		try {
			gstrTCID = "BQS-48405 ";
			gstrTO = "Provide a user the �View Resource� right on resource RS and"
					+ " verify that for a private status type associated with RS,"
					+ " status change preferences can be set if the user has any "
					+ "of the resource rights on resource RS.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName = strUserName;

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUsrFulName_2 = strUserName_2;

			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUsrFulName_3 = strUserName_3;

			String statTypeName1 = "ST_1" + strTimeText;
			String statTypeName2 = "ST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statTypeName1, statTypeName2 };

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
			 * 1. Status types ST1 and ST2 are created selecting 'Private'
			 * option.
			 */
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
								strStatusTypeValue, statTypeName1,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statTypeName2,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. ST1 is associated with resource type RT.
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Resource RS is created under resource type RT with address.
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
			 * 4. ST2 is associated to resource RS at the resource level.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Status types ST1 and ST2 are under status type section S1.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strVewDescription = "Automation";
				strViewType = "Resource (Resources and status types as rows. Status,"
						+ " comments and timestamps as columns.)";

				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

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
						seleniumFirefox, strArStatType1, strSection1, true);

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
				strSectionValue = objViews.fetchSectionID(seleniumFirefox,
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
				strFuncResult = objLogin.logout(seleniumFirefox);

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEventTemplate(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(selenium,
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
			 * 7. Event E1 is created under ET selecting RS.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(selenium,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. Users U1, U2, U3 and U4 are created without providing any
			 * resource rights on RS and has 'Edit Status Change Notification
			 * Preferences' right.
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
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U1
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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_2, strInitPwd, strConfirmPwd,
						strUsrFulName_2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						true, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_3, strInitPwd, strConfirmPwd,
						strUsrFulName_3);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], true, false,
						false, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 10. all 3 users have 'View Resource' right on resource RS and has
			 * 'View Custom View' right.
			 */
			/*
			 * 11. All 3 users have added resource RS and status types ST1 and
			 * ST2 to their respective custom view.
			 */

			// U1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewNewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewNewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_3,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewNewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statTypeName1);

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

				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTValue[][] = { { strSTvalue[1], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as a user with 'User-Setup User Accounts' right and
			 * navigate to Setup>>Users No Expected Result
			 */

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

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Edit users U1, U2, U3 and deselect the checkboxes 'Update
			 * Status', 'Run Report' and 'Associated with' rights respectively
			 * except 'View Resource' right corresponding to resource RS and
			 * save the users. No Expected Result
			 */

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

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

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

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_2, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Edit user U3, provide only 'Associated with' and 'View
			 * Resource' right for RS and save. No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_3, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Login as user U1 and navigate View>>V1 ST1 and ST2 are not
			 * displayed for Resource RS on the view screen.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"There are no resources to display in this view.");
					log4j
							.info("ST1 and ST2 are NOT  displayed for Resource RS on the view screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS on the view screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS on the view screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Navigate View>>Custom ST1 and ST2 are not displayed for
			 * Resource RS on the custom view-Table screen.
			 * 
			 * ST1 and ST2 are not displayed in the resource pop up window of RS
			 * on the custom view-Map screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(selenium
							.getText("//div[@id='viewContainer']/h1"),
							"No Statuses in Custom View");

					assertEquals(selenium
							.getText("//div[@id='viewContainer']/p"),
							"There are no statuses (columns) to display in your custom view.");
					log4j
							.info("No status types are displayed and message stating "
									+ "'No Statuses in Custom View' is displayed in 'Custom View"
									+ " - Table' screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("No status types are displayed and message stating 'No Statuses"
									+ " in Custom View' is displayed in 'Custom View - Table' screen. ");
					strFuncResult = "No status types are displayed and message stating 'No Statuses in"
							+ " Custom View' is displayed in 'Custom View - Table' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='resourceFinder']/option[text()='"
									+ strResource + "']"));

					log4j
							.info("RS is NOT displayed on the 'Find Resource' drop down. ");

				} catch (AssertionError Ae) {
					log4j
							.info("RS is displayed on the 'Find Resource' drop down. ");
					strFuncResult = "RS is displayed on the 'Find Resource' drop down. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate View>>Map, select resource RS from the 'Find Resource'
			 * dropdown ST1 and ST2 are NOT displayed on the resource pop up
			 * window.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(
							selenium
									.getText("//div[@class='emsCenteredLabel']/following-sibling::div/center"),
							"(no status reported)");
					log4j
							.info("No status types are displayed and message stating"
									+ " 'no status reported' is displayed in resource pop up window'. ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status types are displayed and message stating "
									+ "'no status reported' is NOT displayed in resource pop up window'. ");
					strFuncResult = "Status types are displayed and message stating "
							+ "'no status reported' is NOT displayed in resource pop up window'. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName1 + "']"));
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j
							.info("The Status Type is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ statTypeName1 + "']"));

					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ statTypeName2 + "']"));
					log4j.info("No status types are displayed in dropdown. ");
				} catch (AssertionError Ae) {
					log4j.info("status types are displayed in dropdown. ");
					strFuncResult = "status types are displayed in dropdown. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on the banner of event E1 ST1 and ST2 are not displayed
			 * for Resource RS on the 'Event Status' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navEventStatusPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"Either there are no resources participating in this event"
									+ ", "
									+ "or you do not have viewing rights to any resources/status"
									+ " types involved in this event.");
					log4j
							.info("ST1 and ST2 are NOT displayed for Resource RS 'Event Status' screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// U2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_2,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"There are no resources to display in this view.");
					log4j
							.info("ST1 and ST2 are NOT  displayed for Resource RS on the view screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS on the view screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS on the view screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(selenium
							.getText("//div[@id='viewContainer']/h1"),
							"No Statuses in Custom View");

					assertEquals(selenium
							.getText("//div[@id='viewContainer']/p"),
							"There are no statuses (columns) to display in your custom view.");
					log4j
							.info("No status types are displayed and message stating "
									+ "'No Statuses in Custom View' is displayed in 'Custom View"
									+ " - Table' screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("No status types are displayed and message stating 'No Statuses"
									+ " in Custom View' is displayed in 'Custom View - Table' screen. ");
					strFuncResult = "No status types are displayed and message stating 'No Statuses in"
							+ " Custom View' is displayed in 'Custom View - Table' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='resourceFinder']/option[text()='"
									+ strResource + "']"));

					log4j
							.info("RS is NOT displayed on the 'Find Resource' drop down. ");

				} catch (AssertionError Ae) {
					log4j
							.info("RS is displayed on the 'Find Resource' drop down. ");
					strFuncResult = "RS is displayed on the 'Find Resource' drop down. ";
					gstrReason = strFuncResult;

				}
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
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(
							selenium
									.getText("//div[@class='emsCenteredLabel']/following-sibling::div/center"),
							"(no status reported)");
					log4j
							.info("No status types are displayed and message stating"
									+ " 'no status reported' is displayed in resource pop up window'. ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status types are displayed and message stating "
									+ "'no status reported' is NOT displayed in resource pop up window'. ");
					strFuncResult = "Status types are displayed and message stating "
							+ "'no status reported' is NOT displayed in resource pop up window'. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName1 + "']"));
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j
							.info("The Status Type is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ statTypeName1 + "']"));

					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ statTypeName2 + "']"));
					log4j.info("No status types are displayed in dropdown. ");
				} catch (AssertionError Ae) {
					log4j.info("status types are displayed in dropdown. ");
					strFuncResult = "status types are displayed in dropdown. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navEventStatusPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"Either there are no resources participating in this event"
									+ ", "
									+ "or you do not have viewing rights to any resources/status"
									+ " types involved in this event.");
					log4j
							.info("ST1 and ST2 are NOT displayed for Resource RS 'Event Status' screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// U3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_3,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"There are no resources to display in this view.");
					log4j
							.info("ST1 and ST2 are NOT  displayed for Resource RS on the view screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS on the view screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS on the view screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(selenium
							.getText("//div[@id='viewContainer']/h1"),
							"No Statuses in Custom View");

					assertEquals(selenium
							.getText("//div[@id='viewContainer']/p"),
							"There are no statuses (columns) to display in your custom view.");
					log4j
							.info("No status types are displayed and message stating "
									+ "'No Statuses in Custom View' is displayed in 'Custom View"
									+ " - Table' screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("No status types are displayed and message stating 'No Statuses"
									+ " in Custom View' is displayed in 'Custom View - Table' screen. ");
					strFuncResult = "No status types are displayed and message stating 'No Statuses in"
							+ " Custom View' is displayed in 'Custom View - Table' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='resourceFinder']/option[text()='"
									+ strResource + "']"));

					log4j
							.info("RS is NOT displayed on the 'Find Resource' drop down. ");

				} catch (AssertionError Ae) {
					log4j
							.info("RS is displayed on the 'Find Resource' drop down. ");
					strFuncResult = "RS is displayed on the 'Find Resource' drop down. ";
					gstrReason = strFuncResult;

				}
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
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals(
							selenium
									.getText("//div[@class='emsCenteredLabel']/following-sibling::div/center"),
							"(no status reported)");
					log4j
							.info("No status types are displayed and message stating"
									+ " 'no status reported' is displayed in resource pop up window'. ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status types are displayed and message stating "
									+ "'no status reported' is NOT displayed in resource pop up window'. ");
					strFuncResult = "Status types are displayed and message stating "
							+ "'no status reported' is NOT displayed in resource pop up window'. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName1 + "']"));
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statTypeName2 + "']"));

					log4j
							.info("The Status Type is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j
							.info("The Status Type is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ statTypeName1 + "']"));

					assertFalse(selenium
							.isElementPresent("//select[@id='statusType']/option[text()='"
									+ statTypeName2 + "']"));
					log4j.info("No status types are displayed in dropdown. ");
				} catch (AssertionError Ae) {
					log4j.info("status types are displayed in dropdown. ");
					strFuncResult = "status types are displayed in dropdown. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navEventStatusPage(selenium,
						strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				try {
					assertEquals(
							selenium.getText("//div[@id='viewContainer']"),
							"Either there are no resources participating in this event"
									+ ", "
									+ "or you do not have viewing rights to any resources/status"
									+ " types involved in this event.");
					log4j
							.info("ST1 and ST2 are NOT displayed for Resource RS 'Event Status' screen. ");

				} catch (AssertionError Ae) {
					log4j
							.info("ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ");
					strFuncResult = "ST1 and ST2 are displayed for Resource RS 'Event Status' screen. ";
					gstrReason = strFuncResult;

				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName + "," + strUserName_1 + "," + strUserName_2
								+ "," + strUserName_3 + strInitPwd,
						statTypeName1 + "," + statTypeName2, strViewName_1,
						"From 18th Step", strResource, strSection1, strEveName,
						strTempName, strSection1, strResrctTypName };

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
			gstrTCID = "BQS-48405";
			gstrTO = "Verify that when the resource rights are removed for "
					+ "a user, the user cannot view a private status type for "
					+ "the respective resource.";
			gstrResult = "FAIL";
			gstrReason = "";

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
