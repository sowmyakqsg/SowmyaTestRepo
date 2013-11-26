package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**************************************************************
' Description         :This class includes Test cases
' Requirement Group   :Regional Info >> Document Library 
' Requirement         :Add a document
' Date		          :4th-Sep-2012
' Author	          :QSG
'--------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'**************************************************************/
public class CommonScenariosForST {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CommonScenariosForST");
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

	Selenium selenium,seleniumFirefox,seleniumPrecondition;
	
	

	@Before
	public void setUp() throws Exception {

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";

	
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
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

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");
		
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
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		// kill browser
		seleniumPrecondition.stop();
		selenium.stop();
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
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
/********************************************************************************
'Description	:Verify that the number status type of a resource can be updated 
                   from the following screens:
					1. View Screen
					2. Resource Detail Screen
					3. Map Screen
					4. Event Detail Screen
'Arguments		:None
'Returns		:None
'Date	 		:4th-Sep-2012
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
**********************************************************************************/
	@Test
	public void testBQS66027() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		EventSetup objEve = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEL = new EventList();
		Date_Time_settings dts = new Date_Time_settings();

		try {
			gstrTCID = "BQS-66027 ";
			gstrTO = " Verify that the number status type of a resource can be updated"
					+ " from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen "
					+ "4. Event Detail Screen";
			gstrResult = "FAIL";
			gstrReason = "";

			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("FirefoxTestData", 2, 2,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("FirefoxTestData", 2, 3,
					strFILE_PATH);

			String strStatusTypeValue = "Number";
			String statTypeName = "A1_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Full" + strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
					
			
			
			String strRoleValue = "";

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";

			String strRTValue[] = new String[1];

			String strArRes[] = { strResource };

			String strUpdtValue1 = "1";
			String strUpdtValue2 = "2";
			String strUpdtValue3 = "3";
			String strUpdtValue4 = "4";

			String strComment1 = "ss1";
			String strComment2 = "ss2";
			String strComment3 = "ss3";
			String strComment4 = "ss4";

			String strSection = "AB_" + strTimeText;
			String strUpdTime = "";
			String strArStatType[] = { statTypeName };

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
				blnLogin = true;
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
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

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
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
				blnLogin = false;
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
				blnLogin = true;
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
				blnLogin = false;
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
				assertTrue(strFuncResult.equals(""));
				blnLogin = true;
				strFuncResult = objEve.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEve.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strSTvalue, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEve.createNewEvent(seleniumPrecondition, strEveTemp,
						strResource, strEveName, "AutoEvent", true);
				log4j.info(strEveName);
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
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strArRes, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue1, strSTvalue[0], strComment1,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource, statTypeName, strUpdtValue1,
						strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment1);
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
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue2, strSTvalue[0], strComment2,
						"View Resource Detail");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdSTValWithCommentsInResDetail(
						selenium, strSection, statTypeName, strUpdtValue2,
						strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewResDetToolTipWithComments(
								selenium, strResource, statTypeName,
								strUpdTime, strUsrFulName, strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue3, strSTvalue[0], strComment3,
						"Regional Map View");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdSTValandComments(selenium,
						strResource, strUpdtValue3, strComment3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEL.checkInEventBanner(selenium, strEveName,
						strResrctTypName, strResource, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue4, strSTvalue[0], strComment4,
						"Event Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews
						.verifyUpdateSTValWithCommentsInEveStat(selenium,
								strResource, statTypeName, strUpdtValue4,
								strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66027";
			gstrTO = " Verify that the number status type of a resource can be updated from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen " + "4. Event Detail Screen";

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
	public void testBQS66062() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		EventSetup objEve = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEL = new EventList();

		try {
			gstrTCID = "BQS-66062 ";
			gstrTO = " Verify that the text status type of a resource can be updated"
					+ " from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen "
					+ "4. Event Detail Screen";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("FirefoxTestData", 2, 2,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("FirefoxTestData", 2, 3,
					strFILE_PATH);

			String strStatusTypeValue = "Text";
			String statTypeName = "A1_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Full" + strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
					
			
			
			String strRoleValue = "";

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";

			String strRTValue[] = new String[1];

			String strArRes[] = { strResource };

			String strUpdtValue1 = "s";
			String strUpdtValue2 = "p";
			String strUpdtValue3 = "r";
			String strUpdtValue4 = "t";

			String strComment1 = "st1";
			String strComment2 = "sw2";
			String strComment3 = "sf3";
			String strComment4 = "sb4";

			String strSection = "AB_" + strTimeText;
			String strUpdTime = "";
			String strArStatType[] = { statTypeName };

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
				blnLogin = true;
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
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

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
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
				blnLogin = false;
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
				blnLogin = true;
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
				blnLogin = false;
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
				assertTrue(strFuncResult.equals(""));
				blnLogin = true;
				strFuncResult = objEve.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEve.createEventTemplate(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strSTvalue, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(seleniumPrecondition));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEve.createNewEvent(seleniumPrecondition, strEveTemp,
						strResource, strEveName, "AutoEvent", true);
				log4j.info(strEveName);
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
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strArRes, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue1, strSTvalue[0], strComment1,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource, statTypeName, strUpdtValue1,
						strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment1);
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
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue2, strSTvalue[0], strComment2,
						"View Resource Detail");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdSTValWithCommentsInResDetail(
						selenium, strSection, statTypeName, strUpdtValue2,
						strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewResDetToolTipWithComments(
								selenium, strResource, statTypeName,
								strUpdTime, strUsrFulName, strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue3, strSTvalue[0], strComment3,
						"Regional Map View");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdSTValandComments(selenium,
						strResource, strUpdtValue3, strComment3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEL.checkInEventBanner(selenium, strEveName,
						strResrctTypName, strResource, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusNSTWithComments(
						selenium, strUpdtValue4, strSTvalue[0], strComment4,
						"Event Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews
						.verifyUpdateSTValWithCommentsInEveStat(selenium,
								strResource, statTypeName, strUpdtValue4,
								strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66062";
			gstrTO = " Verify that the text status type of a resource can be updated from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen " + "4. Event Detail Screen";

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
	'Description  :Verify that multi status type of a resource can be updated from the
	                following screens:  1. View Screen   2. Resource Detail Screen
						                3. Map Screen    4. Event Detail Screen
	'Arguments	  :None
	'Returns	  :None
	'Date	 	  :4th-Sep-2012
	'Author		  :QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS66053() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		EventSetup objEve = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEL = new EventList();

		try {
			gstrTCID = "BQS-66053";
			gstrTO = " Verify that the multi status type of a resource can be updated "
					+ "from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen "
					+ "4. Event Detail Screen";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("Event Temp Data", 2, 5,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("Event Temp Data", 2, 6,
					strFILE_PATH);

			String strStatusTypeValue = "Multi";
			String statTypeName = "A1_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Full" + strUserName;

			String strRoleValue = "";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName3 = "Stc" + strTimeText;
			String strStatusName4 = "Std" + strTimeText;

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";

			String strRTValue[] = new String[1];

			String strArRes[] = { strResource };

			/*
			 * String strUpdtValue1="s"; String strUpdtValue2="p"; String
			 * strUpdtValue3="r"; String strUpdtValue4="t";
			 */

			String strComment1 = "st1";
			String strComment2 = "sw2";
			String strComment3 = "sf3";
			String strComment4 = "sb4";

			String strSection = "AB_" + strTimeText;
			String strUpdTime = "";
			String strArStatType[] = { statTypeName };
			String strStatusValue[] = new String[4];

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
				blnLogin = true;
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeName, strStatusName3, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(selenium,
						statTypeName, strStatusName4, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeName, strStatusName1);
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
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeName, strStatusName3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchStatValInStatusList(selenium,
						statTypeName, strStatusName4);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[3] = strStatValue;
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUser(selenium,
						strUserName);
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
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);

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
				blnLogin = false;
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
				blnLogin = true;
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
				blnLogin = false;
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
				assertTrue(strFuncResult.equals(""));
				blnLogin = true;
				strFuncResult = objEve.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				// navigate to Event Template
				strFuncResult = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				// fill the required fields in Create Event Template and save
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strSTvalue, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));

				strFuncResult = objEve.createNewEvent(selenium, strEveTemp,
						strResource, strEveName, "AutoEvent", true);
				log4j.info(strEveName);
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
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strArRes, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[0], strSTvalue[0],
						strComment1, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource, statTypeName, strStatusName1,
						strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment1);
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
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[1], strSTvalue[0],
						strComment2, "View Resource Detail");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdSTValWithCommentsInResDetail(
						selenium, strSection, statTypeName, strStatusName2,
						strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewResDetToolTipWithComments(
								selenium, strResource, statTypeName,
								strUpdTime, strUsrFulName, strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[2], strSTvalue[0],
						strComment3, "Regional Map View");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdSTValandComments(selenium,
						strResource, strStatusName3, strComment3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEL.checkInEventBanner(selenium, strEveName,
						strResrctTypName, strResource, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusMSTWithComments(
						selenium, strStatusValue[3], strSTvalue[0],
						strComment4, "Event Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews
						.verifyUpdateSTValWithCommentsInEveStat(selenium,
								strResource, statTypeName, strStatusName4,
								strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66053";
			gstrTO = " Verify that the multi status type of a resource can be updated from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen " + "4. Event Detail Screen";

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

	/*************************************************************************************
	'Description	:Verify that saturation score status type of a resource can be updated
	                 from the following screens: 1. View Screes 2. Resource Detail Screen
                     3. Map Screen  4. Event Detail Screen
	'Arguments		:None
	'Returns		:None
	'Date	 		:4th-Sep-2012
	'Author			:QSG
	'--------------------------------------------------------------------------------------
	'Modified Date                                                            Modified By
	'<Date>                                                                   <Name>
	***************************************************************************************/
	@Test
	public void testBQS66063() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		EventSetup objEve = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		EventList objEL = new EventList();

		try {
			gstrTCID = "BQS-66063";
			gstrTO = " Verify that the Saturation Score status type of a resource "
					+ "can be updated from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen " + "4. Event Detail Screen";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("Event Temp Data", 2, 5,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("Event Temp Data", 2, 6,
					strFILE_PATH);

			String strStatusTypeValue = "Saturation Score";
			String statTypeName = "A1_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strStatValue = "";
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "Full" + strUserName;

			// search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strResVal = "";
			strSTvalue[0] = "";
			String strRTValue[] = new String[1];
			String strArRes[] = { strResource };

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUpdtValue1[] = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9" };
			String strUpdtValue2[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strUpdtValue3[] = { "1", "2", "7", "3", "4", "5", "6", "7",
					"8" };
			String strUpdtValue4[] = { "0", "1", "2", "3", "4", "5", "4", "2",
					"9" };

			String strUpdValCheck1 = "429";
			String strUpdValCheck2 = "393";
			String strUpdValCheck3 = "440";
			String strUpdValCheck4 = "445";

			String strComment1 = "st1";
			String strComment2 = "sw2";
			String strComment3 = "sf3";
			String strComment4 = "sb4";

			String strSection = "AB_" + strTimeText;
			String strUpdTime = "";
			String strArStatType[] = { statTypeName };
			
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
			
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
				blnLogin = true;
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName);
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
				strFuncResult = objRT.fetchResTypeValueInResTypeList(selenium,
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
				strFuncResult = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strSTvalue, true,
						strSTvalue, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

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
				strFuncResult = objCreateUsers.selectResourceRights(selenium,
						strResource, false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(selenium,
						strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
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
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue);
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
				blnLogin = false;
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
				blnLogin = true;
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
				blnLogin = false;
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
				assertTrue(strFuncResult.equals(""));
				blnLogin = true;
				strFuncResult = objEve.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				// navigate to Event Template
				strFuncResult = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				// fill the required fields in Create Event Template and save
				strFuncResult = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strSTvalue, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFuncResult = "";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertTrue(strFuncResult.equals(""));
				strFuncResult = objEve.createNewEvent(selenium, strEveTemp,
						strResource, strEveName, "AutoEvent", true);
				log4j.info(strEveName);
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
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strArRes, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusSSTWithComments(
						selenium, strUpdtValue1, strSTvalue[0], strComment1,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdateSTValWithCommentsInViews(
						selenium, strResource, statTypeName, strUpdValCheck1,
						strComment1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment1);
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
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusSSTWithComments(
						selenium, strUpdtValue2, strSTvalue[0], strComment2,
						"View Resource Detail");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews.verifyUpdSTValWithCommentsInResDetail(
						selenium, strSection, statTypeName, strUpdValCheck2,
						strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewResDetToolTipWithComments(
								selenium, strResource, statTypeName,
								strUpdTime, strUsrFulName, strComment2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusSSTWithComments(
						selenium, strUpdtValue3, strSTvalue[0], strComment3,
						"Regional Map View");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdSTValandComments(selenium,
						strResource, strUpdValCheck3, strComment3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEL.checkInEventBanner(selenium, strEveName,
						strResrctTypName, strResource, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSaveUpdStatusSSTWithComments(
						selenium, strUpdtValue4, strSTvalue[0], strComment4,
						"Event Status");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strUpdTime = selenium.getText("css=#statusTime");
				strFuncResult = objViews
						.verifyUpdateSTValWithCommentsInEveStat(selenium,
								strResource, statTypeName, strUpdValCheck4,
								strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.verifyLastUpdTimeInViewToolTipWithComments(selenium,
								strResource, statTypeName, "3", strUpdTime,
								strUsrFulName, strComment4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-66063";
			gstrTO = " Verify that the Saturation Score status type of a resource can be updated from the following screens: "
					+ "1. View Screen "
					+ "2. Resource Detail Screen "
					+ "3. Map Screen " + "4. Event Detail Screen";

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
	
	/***********************************************************************
	'Description	:Verify that user receives the following when status of a 
	                  resource expires at the EXPIRATION time: 
	                  1. Status update prompt. 2. Expired status notifications
	'Arguments		:None
	'Returns		:None
	'Date	 		:20-Sep-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'6-Feb-2013		                               <Name>
	************************************************************************/
	
	@Test
	public void testBQS69083() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();
		General objMail = new General();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		Roles objRole = new Roles();
		ViewMap objMap = new ViewMap();
		EventSetup objEve = new EventSetup();
		ViewMap objViewMap = new ViewMap();

		try {
			gstrTCID = "69083";
			gstrTO = "Verify that user receives the following when an event related "
					+ "status of a resource expires: 1. Status update prompt. "
					+ "2. Expired status notification";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strEveTemp = "AutoET_" + strTimeText;
			String strEveName = "AutoEve_" + strTimeText;

			String strTempDef = rdExcel.readInfoExcel("Event Temp Data", 2, 3,
					strFILE_PATH);
			String strEveColor = rdExcel.readInfoExcel("Event Temp Data", 2, 4,
					strFILE_PATH);
			String strAsscIcon = rdExcel.readInfoExcel("FirefoxTestData", 2, 2,
					strFILE_PATH);
			String strIconSrc = rdExcel.readInfoExcel("FirefoxTestData", 2, 3,
					strFILE_PATH);
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strCurDate = dts.getCurrentDate("MM/dd/yyyy");

			String strStatValue = "";
			String strExpHr = "00";
			String strExpMn = "05";

			String strNumUpdateValue1 = "2";
			String strTxtUpdateValue1 = "tr";
			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strScUpdValCheck1 = "393";

			String strComment1 = "st1";
			String strComment2 = "st2";
			String strComment3 = "st3";
			String strComment4 = "st4";

			String strNSTValue = "Number";
			String strNumStatType = "AutoNSt_" + strTimeText;
			String strStatTypDefn = "Auto";

			String strMSTValue = "Multi";
			String strMultiStatType = "AutoMSt_" + strTimeText;

			String strTSTValue = "Text";
			String strTxtStatType = "AutoTSt_" + strTimeText;

			String strSSTValue = "Saturation Score";
			String strSatuStatType = "AutoScSt_" + strTimeText;

			String strStatusName1 = "Sta" + strTimeText;
			String strStatTypeColor = "Black";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strAbbrv = "A" + strTmText;

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strRoleValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strRTValue[] = new String[1];

			String strResVal = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			strSTvalue[0] = "";
			String strSubjName = "";

			String strMsgBodyEmailNST = "";
			String strMsgBodyPagerNST = "";
			String strMsgBodyEmailNSTAnother = "";
			String strMsgBodyPagerNSTAnother = "";

			String strMsgBodyEmailTST = "";
			String strMsgBodyPagerTST = "";
			String strMsgBodyEmailTSTAnother = "";
			String strMsgBodyPagerTSTAnother = "";

			String strMsgBodyEmailSST = "";
			String strMsgBodyPagerSST = "";
			String strMsgBodyEmailSSTAnother = "";
			String strMsgBodyPagerSSTAnother = "";

			String strMsgBodyEmailMST = "";
			String strMsgBodyPagerMST = "";
			String strMsgBodyEmailMSTAnother = "";
			String strMsgBodyPagerMSTAnother = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;

			String[] strRoleStatType = { strNumStatType, strMultiStatType,
					strTxtStatType, strSatuStatType };
			String strStatusValue[] = new String[1];

		/*Step:Preconditions:1. Test user has created following status types providing expiration time as 5 minutes:				
					a. Number status type 'NST', associated with a timer say (Count down to expiration, then stop counting)				
					b. Text status type 'TST', associated with a timer say (count up to expiration, then start counting up again form zero)				
					c. Saturation score status type 'SST', associated with a timer say (count up to expiration, then stop counting)				
				2. Multi status type 'MST' is created and a status S1 under MST is created associating with a timer say (count up (regardless of expiration)) and expiration time of 5 minutes.			
				3. Resource type RT is created selecting all the above status types.				
				4. Resource RS is created under RT providing address.				
				5. View 'V1' of type 'Resource' is created selecting all the above status types and 'RS'.				
				6. Role ROL has view and update rights on all the above status types.				
				7. For a test user U1:				
					a. Email and pager addresses are given
					b. is assigned a role 'ROL'
					c. has been given the 'Update status' right on resource RS
					d. has opted to receive expired status notification via e-mail and pager.
					e. has 'Must update overdue status' right
		*/
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNSTValue, strNumStatType, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectDeSelEventOnly(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire"));
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Hours"),
						"label=" + strExpHr);
				seleniumPrecondition.select(propElementDetails
						.getProperty("StatusType.CreateStatType.Expire.Mins"),
						"label=" + strExpMn);

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
							strNumStatType);
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
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strMSTValue, strMultiStatType,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectDeSelEventOnly(seleniumPrecondition, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
							strMultiStatType);
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
					strFuncResult = objST.createSTWithinMultiTypeSTExpTime(
							seleniumPrecondition, strMultiStatType, strStatusName1,
							strMSTValue, strStatTypeColor, strExpHr, strExpMn,
							"", "", true);
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
					strFuncResult = objST.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strTSTValue, strTxtStatType,
							strStatTypDefn, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectDeSelEventOnly(seleniumPrecondition, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);

					seleniumPrecondition.click(propElementDetails
							.getProperty("StatusType.CreateStatType.Expire"));
					seleniumPrecondition
							.select(
									propElementDetails
											.getProperty("StatusType.CreateStatType.Expire.Hours"),
									"label=" + strExpHr);
					seleniumPrecondition
							.select(
									propElementDetails
											.getProperty("StatusType.CreateStatType.Expire.Mins"),
									"label=" + strExpMn);

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
								strTxtStatType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strStatValue = objST.fetchSTValueInStatTypeList(
								seleniumPrecondition, strTxtStatType);
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
						strFuncResult = objST.selectStatusTypesAndFilMandFlds(
								seleniumPrecondition, strSSTValue, strSatuStatType,
								strStatTypDefn, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.selectDeSelEventOnly(seleniumPrecondition,
								true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						seleniumPrecondition
								.click(propElementDetails
										.getProperty("StatusType.CreateStatType.Expire"));
						seleniumPrecondition
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Hours"),
										"label=" + strExpHr);
						seleniumPrecondition
								.select(
										propElementDetails
												.getProperty("StatusType.CreateStatType.Expire.Mins"),
										"label=" + strExpMn);

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition,
									strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strStatValue = objST.fetchSTValueInStatTypeList(
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
						for (int intST = 0; intST < strSTvalue.length; intST++) {
							try {
								assertEquals("", strFuncResult);
								strFuncResult = objRT.selAndDeselSTInEditRT(
										seleniumPrecondition, strSTvalue[intST], true);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
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
							strFuncResult = objRT
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
									strResrctTypName, strContFName,
									strContLName, strState, strCountry,
									strStandResType);

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

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objRole.CreateRoleWithAllFields(
									seleniumPrecondition, strRoleName, strRoleRights,
									strSTvalue, true, strSTvalue, true, true);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strRoleValue = objRole.fetchRoleValueInRoleList(
									seleniumPrecondition, strRoleName);

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
							strFuncResult = objCreateUsers
									.navUserListPge(seleniumPrecondition);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.fillUsrMandatoryFlds(seleniumPrecondition,
											strUserName, strInitPwd,
											strConfirmPwd, strUsrFulName);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.slectAndDeselectRole(seleniumPrecondition,
											strRoleValue, true);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.selectResourceRights(seleniumPrecondition,
											strResource, false, true, false,
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
													.getProperty("CreateNewUsr.AdvOptn.MustOverDue"),
											true);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.nonMandatoryUsrProfileFlds(seleniumPrecondition, "",
											"", "", "", "", strEMail, strEMail,
											strEMail, "");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.savVrfyUserWithSearchUser(seleniumPrecondition,
											strUserName, strByRole,
											strByResourceType, strByUserInfo,
											strNameFormat);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.navEditUserPge(
									seleniumPrecondition, strUserName, strByRole,
									strByResourceType, strByUserInfo, strNameFormat);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.clkOnSysNotfinPrefrences(
									seleniumPrecondition, strUserName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers.fillRecvExpStatusNotifinEditUsr(
									seleniumPrecondition, true, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objCreateUsers
									.savAndNavToSySNotiPrefPage(seleniumPrecondition,strUserName);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(strFuncResult.equals(""));
							strFuncResult = objEve.navToEventSetupPge(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(strFuncResult.equals(""));
							// navigate to Event Template
							strFuncResult = objEve
									.createEventTemplate(seleniumPrecondition);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(strFuncResult.equals(""));

							strFuncResult = objEve
									.fillMandfieldsAndSaveEveTemp(seleniumPrecondition,
											strEveTemp, strTempDef,
											strEveColor, strAsscIcon,
											strIconSrc, "", "", "", "", true,
											strRTValue, strSTvalue, true,
											false, false);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertTrue(strFuncResult.equals(""));
							strFuncResult = objEve
									.navToEventManagementNew(seleniumPrecondition);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertTrue(strFuncResult.equals(""));

							strFuncResult = objEve.createNewEvent(seleniumPrecondition,
									strEveTemp, strResource, strEveName,
									"AutoEvent", true);
							log4j.info(strEveName);
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
							strFuncResult = objLogin.newUsrLogin(selenium,
									strUserName, strInitPwd);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strNumStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strMultiStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strTxtStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusNSTWithComments(selenium,
											strNumUpdateValue1, strSTvalue[0],
											strComment1);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusMSTWithComments(selenium,
											strStatusValue[0], strSTvalue[1],
											strComment2);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusNSTWithComments(selenium,
											strTxtUpdateValue1, strSTvalue[2],
											strComment3);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews
									.fillUpdStatusSSTWithComments(selenium,
											strScUpdValue1, strSTvalue[3],
											strComment4);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							strFuncResult = objViews.saveAndNavToViewScreen(
									selenium, "Region Default");
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						try {
							assertEquals("", strFuncResult);

							String[] strEventStatType = {};

							strFuncResult = objViewMap
									.verifyStatTypesInResourcePopup(selenium,
											strResource, strEventStatType,
											strRoleStatType, false, true);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;

						}
						String[] strArUpdVal = { strNumUpdateValue1,
								strStatusName1, strTxtUpdateValue1,
								strScUpdValCheck1 };
						for (String strST : strArUpdVal) {
							try {
								assertEquals("", strFuncResult);

								strFuncResult = objMap.verifyUpdValInMap(
										selenium, strST);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
						}
						try {
							assertEquals("", strFuncResult);

							String strUpdTime = "";
							String strUpdatedDate = selenium
									.getText("//span[text()='"
											+ strNumUpdateValue1
											+ "']/following-sibling::span[1]");

							strUpdatedDate = strUpdatedDate.substring(1, 13);

							String strLastUpdArr[] = strUpdatedDate.split(" ");
							strUpdTime = strLastUpdArr[2];
							String strAdUpdTime = dts.addTimetoExisting(
									strUpdTime, 5, "HH:mm");
							strUpdTime = strAdUpdTime;

							String strCurYear = dts
									.getTimeOfParticularTimeZone("CST", "yyyy");
							String strCurDateMnth = strLastUpdArr[0]
									+ strLastUpdArr[1] + strCurYear;

							strCurDate = dts.converDateFormat(strCurDateMnth,
									"ddMMMyyyy", "MM/dd/yyyy");

							log4j
									.info("Expiration Time for NST: "
											+ strUpdTime);

							String strAdUpdTimeShift = dts.addTimetoExisting(
									strUpdTime, 1, "HH:mm");

							strMsgBodyEmailNST = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strNumStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strUpdTime
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyEmailNSTAnother = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strNumStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyPagerNST = "EMResource expired status: "
									+ strResource + ". " + strNumStatType
									+ " status expired " + strCurDate + " "
									+ strUpdTime + ".";

							strMsgBodyPagerNSTAnother = "EMResource expired status: "
									+ strResource
									+ ". "
									+ strNumStatType
									+ " status expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift + ".";

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							String strUpdTime = "";
							String strUpdatedDate = selenium
									.getText("//span[text()='"
											+ strTxtUpdateValue1
											+ "']/following-sibling::span[1]");

							strUpdatedDate = strUpdatedDate.substring(1, 13);

							String strLastUpdArr[] = strUpdatedDate.split(" ");
							strUpdTime = strLastUpdArr[2];
							String strAdUpdTime = dts.addTimetoExisting(
									strUpdTime, 5, "HH:mm");
							strUpdTime = strAdUpdTime;

							String strCurYear = dts
									.getTimeOfParticularTimeZone("CST", "yyyy");
							String strCurDateMnth = strLastUpdArr[0]
									+ strLastUpdArr[1] + strCurYear;

							strCurDate = dts.converDateFormat(strCurDateMnth,
									"ddMMMyyyy", "MM/dd/yyyy");

							log4j
									.info("Expiration Time for NST: "
											+ strUpdTime);
							String strAdUpdTimeShift = dts.addTimetoExisting(
									strUpdTime, 1, "HH:mm");

							strMsgBodyEmailTST = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strTxtStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strUpdTime
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";
							strMsgBodyEmailTSTAnother = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strTxtStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyPagerTST = "EMResource expired status: "
									+ strResource + ". " + strTxtStatType
									+ " status expired " + strCurDate + " "
									+ strUpdTime + ".";

							strMsgBodyPagerTSTAnother = "EMResource expired status: "
									+ strResource
									+ ". "
									+ strTxtStatType
									+ " status expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift + ".";

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							String strUpdTime = "";
							String strUpdatedDate = selenium
									.getText("//span[text()='"
											+ strScUpdValCheck1
											+ "']/following-sibling::span[1]");

							strUpdatedDate = strUpdatedDate.substring(1, 13);

							String strLastUpdArr[] = strUpdatedDate.split(" ");
							strUpdTime = strLastUpdArr[2];
							String strAdUpdTime = dts.addTimetoExisting(
									strUpdTime, 5, "HH:mm");
							strUpdTime = strAdUpdTime;

							String strCurYear = dts
									.getTimeOfParticularTimeZone("CST", "yyyy");
							String strCurDateMnth = strLastUpdArr[0]
									+ strLastUpdArr[1] + strCurYear;

							strCurDate = dts.converDateFormat(strCurDateMnth,
									"ddMMMyyyy", "MM/dd/yyyy");

							log4j
									.info("Expiration Time for NST: "
											+ strUpdTime);

							String strAdUpdTimeShift = dts.addTimetoExisting(
									strUpdTime, 1, "HH:mm");

							strMsgBodyEmailSST = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strSatuStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strUpdTime
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyEmailSSTAnother = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strSatuStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyPagerSST = "EMResource expired status: "
									+ strResource + ". " + strSatuStatType
									+ " status expired " + strCurDate + " "
									+ strUpdTime + ".";

							strMsgBodyPagerSSTAnother = "EMResource expired status: "
									+ strResource
									+ ". "
									+ strSatuStatType
									+ " status expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift + ".";

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							String strUpdTime = "";
							String strUpdatedDate = selenium
									.getText("//span[text()='" + strStatusName1
											+ "']/following-sibling::span[1]");

							strUpdatedDate = strUpdatedDate.substring(1, 13);

							String strLastUpdArr[] = strUpdatedDate.split(" ");
							strUpdTime = strLastUpdArr[2];
							String strAdUpdTime = dts.addTimetoExisting(
									strUpdTime, 5, "HH:mm");
							strUpdTime = strAdUpdTime;

							String strCurYear = dts
									.getTimeOfParticularTimeZone("CST", "yyyy");
							String strCurDateMnth = strLastUpdArr[0]
									+ strLastUpdArr[1] + strCurYear;

							strCurDate = dts.converDateFormat(strCurDateMnth,
									"ddMMMyyyy", "MM/dd/yyyy");

							log4j
									.info("Expiration Time for NST: "
											+ strUpdTime);

							String strAdUpdTimeShift = dts.addTimetoExisting(
									strUpdTime, 1, "HH:mm");

							strMsgBodyEmailMST = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strMultiStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strUpdTime
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyEmailMSTAnother = "For "
									+ strUsrFulName
									+ "\nRegion: "
									+ strRegn
									+ "\n\nThe "
									+ strMultiStatType
									+ " status for "
									+ strResource
									+ " expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift
									+ ".\n\nClick the link below to go to the EMResource login screen:"
									+

									"\n\n        "+propEnvDetails.getProperty("MailURL")
									+ "\n\nPlease do not reply to this email message. You must log into EMResource to take any action that may be required."
									+ "\n\n\nYou have signed up to receive expired status notifications. If you no longer want to receive expired status notifications, log onto EMResource and uncheck the notification fields on the User Info screen.";

							strMsgBodyPagerMST = "EMResource expired status: "
									+ strResource + ". " + strMultiStatType
									+ " status expired " + strCurDate + " "
									+ strUpdTime + ".";

							strMsgBodyPagerMSTAnother = "EMResource expired status: "
									+ strResource
									+ ". "
									+ strMultiStatType
									+ " status expired "
									+ strCurDate
									+ " "
									+ strAdUpdTimeShift + ".";

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							Thread.sleep(400000);
							strFuncResult = objMail
									.waitForMailNotification(selenium, 60,
											"//span[@class='overdue'][text()='Status is Overdue']");

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);

							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strNumStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strMultiStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strTxtStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.checkUpdateStatPrompt(
									selenium, strResource, strSatuStatType);
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int i = 0; i < 4; i++) {
				try {
					assertTrue(strFuncResult.equals(""));

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);


						if (strMsg.equals(strMsgBodyPagerNST)
								|| strMsg.equals(strMsgBodyPagerNSTAnother)) {
							intPagerRes++;
							log4j.info(strMsgBodyPagerNST
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBodyPagerTST)
								|| strMsg.equals(strMsgBodyPagerTSTAnother)) {
							intPagerRes++;
							log4j.info(strMsgBodyPagerTST
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBodyPagerSST)
								|| strMsg.equals(strMsgBodyPagerSSTAnother)) {
							intPagerRes++;
							log4j.info(strMsgBodyPagerSST
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBodyPagerMST)
								|| strMsg.equals(strMsgBodyPagerMSTAnother)) {
							intPagerRes++;
							log4j.info(strMsgBodyPagerMST
									+ " is displayed for Pager Notification");
						} else {
							log4j.info("Pager is NOT displayed ");
							gstrReason = "Pager is NOT displayed ";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				} catch (AssertionError Ae) {
					gstrReason = gstrReason + " " + strFuncResult;
				}

			}

			for (int i = 0; i < 8; i++) {
				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "EMResource Expired Status Notification: "
							+ strResource;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBodyEmailNST)
								|| strMsg.equals(strMsgBodyEmailNSTAnother)) {
							intEMailRes++;
							log4j.info(strMsgBodyEmailNST
									+ " is displayed for Email Notification");
						} else if (strMsg.equals(strMsgBodyEmailTST)
								|| strMsg.equals(strMsgBodyEmailTSTAnother)) {
							intEMailRes++;
							log4j.info(strMsgBodyEmailTST
									+ " is displayed for Email Notification");
						} else if (strMsg.equals(strMsgBodyEmailSST)
								|| strMsg.equals(strMsgBodyEmailSSTAnother)) {
							intEMailRes++;
							log4j.info(strMsgBodyEmailSST
									+ " is displayed for Email Notification");
						} else if (strMsg.equals(strMsgBodyEmailMST)
								|| strMsg.equals(strMsgBodyEmailMSTAnother)) {
							intEMailRes++;
							log4j.info(strMsgBodyEmailMST
									+ " is displayed for Email Notification");
						} else {
							log4j.info("Email is NOT displayed ");
							gstrReason = "Email is NOT displayed ";
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			// check Email, pager notification
			if (intEMailRes == 8 && intPagerRes == 4) {
				gstrResult = "PASS";
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "69083";
			gstrTO = "Verify that user receives the following when an event related status of a resource expires: 1. Status update prompt. 2. Expired status notification";
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
