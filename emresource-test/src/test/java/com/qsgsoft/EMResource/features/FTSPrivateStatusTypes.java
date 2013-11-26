package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*************************************************************
' Description         :This class includes test cases related
' Requirement Group   :Private Status Types	   
' Requirement         :Private Status types 
' Date		          :29-August-2012
' Author	          :QSG
'-------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	               <Name>
'*************************************************************/

public class FTSPrivateStatusTypes {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSPrivateStatusTypes");
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

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		
		seleniumFirefox.setTimeout("");
		selenium.setTimeout("");
		seleniumPrecondition.setTimeout("");
		
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
		seleniumPrecondition.stop();
		
		seleniumFirefox.stop();

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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/********************************************************************************
	'Description	:Create private status type ST selecting a role R1 both under �Roles with Update rights� sections, associate ST with resource RS at resource level and verify that user with role R1 and with �Update Status� right on RS CAN update the status of ST from following screens:
					a. Region view
					b. Map screen (from resource pop window)
					c. Custom view
					d. View Resource Detail
					e. Event detail
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testFTS53253() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "53253 ";
			gstrTO = "Create private status type ST selecting a role R1 both under �Roles "
					+ "with Update rights� sections, associate ST with resource RS at resource"
					+ " level and verify that user with role R1 and with �Update Status� right"
					+ " on RS CAN update the status of ST from following screens: A. Region view"
					+ "b. Map screen (from resource pop window)"
					+ "c. Custom view d. View Resource Detail e. Event detail";
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

			String statTypeName = "ST" + strTimeText;
			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;

			String strStatusTypeValues[] = new String[5];
			
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";
			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

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

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			
			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statpNumTypeName, statpTextTypeName,
					statpMultiTypeName, statpSaturatTypeName };
		/*
		 * 'Precondition	:1. Resource type RT is created selecting a role based status type ST
				2. Resource RS is created providing address under resource type RT
				3. View V1 is created selecting ST and RT
				4. Event template ET is created selecting ST and RT
				5. Event E1 is created selecting RS
				6. Users U1 is created and has following rights:
				a) "View Custom View"
				b) "Update Status" and "View Resource" rights on RS
				c) Role to view the status type ST
				7. User U1 has added resource RS and status types ST to his/her custom view 
		 */
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes.fetchSTValueInStatTypeList(selenium,
						statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objRoles.navCreateRolePge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] updateRightValue = { { strStatusTypeValues[0],
						"true" } };
				String[][] strViewRightValue = { { strStatusTypeValues[0],
						"true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strViewRightValue,
						updateRightValue, true);
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

			/*1. Resource type RT is created selecting a role based status type ST */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='" +  strStatusTypeValues[0]
								+ "']");

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
				strRTVal = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource RS is created providing address under resource type
			 * RT
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

			/*3. View V1 is created selecting ST and RT */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				String strSTValues[] = {  strStatusTypeValues[0] };
				strFuncResult = objViews.createView(selenium, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*	4. Event template ET is created selecting ST and RT */

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

				String[] strResTypeValue = { strRTVal };
				String[] strStatusTypeVal = { strStatusTypeValues[0] };
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

			/*5. Event E1 is created selecting RS */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
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

			/*6. Users U1 is created and has following rights:
				a) "View Custom View"
				b) "Update Status" and "View Resource" rights on RS
				c) Role to view the status type ST */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd, strUsrFulName_1);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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

			/*
			 * 7. User U1 has added resource RS and status types ST to his/her
			 * custom view
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
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Status Types No
			 * Expected Result
			 */

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
				
				strFuncResult = objStatusTypes.navStatusTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to create a number status type, create number status
			 * type NST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save NST NST is listed in the
			 * "Status Type List"
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statpNumTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select to create a text status type, create text status type
			 * TST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save TST TST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statpTextTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select to create a saturation score status type, create
			 * saturation score status type SST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save SST SST is listed in the
			 * "Status Type List"
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpSaturatTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select to create a multi status type, create multi status type
			 * MST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save MST MST is listed in the
			 * "Status Type List"
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpMultiTypeName);
				if (strStatusTypeValues[4].compareTo("") != 0) {
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
						selenium, statpMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to Setup >> Resource, and click on 'Edit Status Types'
			 * link associated with RS, associate NST, MST, TST and SST and
			 * click on 'Save'. RT is listed in the "Resource Type List" screen
			 */

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[4], true);
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
			
			
			/*
			 * 8 Navigate to Setup>>Views, select to edit view V1 and select
			 * status types NST, MST, TST and SST to view V1 and save V1 is
			 * listed in the "Region Views List"
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objViews.navToEditView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 10 Navigate to Event>>Event Management and edit event E1, select
		 * status types NST, MST, TST and SST and save No Expected Result
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objEventSetup.navEditEventPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 9 Click on 'Customize Resource Detail View' Screen, create a
		 * section 'Sec' then click on 'Uncategorized' section, drag and
		 * drop the status types NST, MST, TST and SST to section 'Sec' and
		 * click on 'Save' "Region Views List" screen is displayed.
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
				strFuncResult = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
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
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox, strArStatType1, strSection1, true);
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
			
		/*
		 * 11 Logout & login as user U1. Navigate to Views>>V1 Status types
		 * NST, MST, TST and SST are displayed in the view V1 screen
		 */
		
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				String[] strArStatType2 = { statTypeName, statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 12 Click on the key associated with RS NST, MST, TST and SST are
		 * displayed in the "Update Status" screen
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 13 Update the status of NST, MST, TST and SST by providing valid
		 * values for all 4 status types Updated values are displayed on the
		 * view V1 screen
		 */
			
			//NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[1], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strStatusTypeValues[2], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[3]);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[4], false, "",
						"");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpNumTypeName, "10", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpTextTypeName, "Text", "7");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpSaturatTypeName, "393", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpMultiTypeName, strStatusName1, "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 14 Navigate to Preferences>>Customized View, click on "Options",
		 * add status types NST, MST, TST and SST to the custom view and
		 * save Status types NST, MST, TST and SST are displayed in the
		 * "Custom View - Table"
		 */
			
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
				String strSTValue[][] = { { strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[3], "true" },
						{ strStatusTypeValues[4], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName, statTypeName };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTablNew(
						selenium, strResrctTypName, strResource, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 5 Click on the key associated with RS NST, MST, TST and SST are
		 * displayed in the "Update Status" screen
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 16 Update the status of NST, MST, TST and SST by providing valid
		 * values for all 4 status types Updated status values are displayed
		 * for NST, MST, TST and SST in the "Custom View - Table"
		 */			
			//NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"11", strStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"EText", strStatusTypeValues[2], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[4], false, "",
						"");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statpNumTypeName, "11", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statpTextTypeName, "EText", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statpSaturatTypeName, "429", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInCustomViewTabl(
						selenium, statpMultiTypeName, strStatusName2, "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 17 Click on "Custom View - Map" link Updated status values are
		 * displayed for NST, MST, TST and SST in the resource pop-up window
		 * of RS.
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 18 Click on the "Update Status" link in the
		 * "Resource pop-up window 		NST, MST, TST and SST are displayed in the "
		 * Update Status" screen " +
		 */				
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[1], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strStatusTypeValues[2], false, "", "");				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[3]);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[4], false, "",
						"");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 19 Update the status of NST, MST, TST and SST by providing valid
		 * values for all 4 status types Updated status values are displayed
		 * for NST, MST, TST and SST in the resource pop-up window of RS.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "10");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "Text");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "393");
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
		/*
		 * 20 Navigate to Views>>Map MST is displayed in the "Status Type"
		 * drop-down.
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
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

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statpMultiTypeName + "']"));

				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}			
		/*
		 * 21 Click on the "Update Status" link in the
		 * "Resource pop-up window 		NST, MST, TST and SST are displayed in the "
		 * Update Status" screen " +
		 */				
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 22 Update the status of NST, MST, TST and SST by providing valid
		 * values for all 4 status types Updated status values are displayed
		 * for NST, MST, TST and SST in the resource pop-up window of RS.
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"11", strStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"EText", strStatusTypeValues[2], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[4], false, "",
						"");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
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
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "11");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "EText");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, "429");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 23 Click on the "View Info" link on the resource pop-up window of
		 * RS Status types NST, MST, TST and SST are displayed in the
		 * "View Resource Detail" screen under status type section "Sec"
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 24 Click on the status cell of NST NST is displayed in the
		 * "Update Status" screen
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdStatusByFrmViewResDetail(
						selenium, strResVal, strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 25 Click on "Show All Statuses" link in the "Update Status"
		 * screen MST, TST and SST are displayed in the "Update Status"
		 * screen along with NST
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToShowAllStatus(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 26 Update the status of NST, MST, TST and SST by providing valid
		 * values for all 4 status types Updated status values are displayed
		 * for NST, MST, TST and SST in the "View Resource Detail" screen
		 * under status type section "Sec" .
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"10", strStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Text", strStatusTypeValues[2], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[3]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[4], false, "",
						"");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statpNumTypeName, "10", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statpTextTypeName, "Text", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statpMultiTypeName, strStatusName1, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdSTValInResDetail(selenium,
						strSection1, statpSaturatTypeName, "393", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 27 Click on the event banner of E1 Status types NST, MST, TST and
		 * SST are displayed in the "Event Status" screen
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 28 Click on the key associated with RS NST, MST, TST and SST are
		 * displayed in the "Update Status" screen
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 29 Update the status of NST, MST, TST and SST by providing valid
		 * values for all 4 status types Updated status values are displayed
		 * for NST, MST, TST and SST in the "Event Status" screen.
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"11", strStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"EText", strStatusTypeValues[2], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strStatusTypeValues[3]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strStatusTypeValues[4], false, "",
						"");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpNumTypeName, "11", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpTextTypeName, "EText", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpSaturatTypeName, "429", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statpMultiTypeName, strStatusName2, "5");
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
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "53253";
			gstrTO = "Create private status type ST selecting a role R1 both under �Roles "
					+ "with Update rights� sections, associate ST with resource RS at resource"
					+ " level and verify that user with role R1 and with �Update Status� right"
					+ " on RS CAN update the status of ST from following screens: A. Region view"
					+ "b. Map screen (from resource pop window)"
					+ "c. Custom view d. View Resource Detail e. Event detail";
			gstrResult = "FAIL";
			gstrReason = "";

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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	
	/********************************************************************************
	'Description	:Create private status type ST, associate ST with resource RS at
	                resource level and verify that users can view the status type only if 
	                they have any of the affiliated resource rights on RS from following screens:
					a. Region view
					b. Map screen (from resource pop window)
					c. Custom view
					d. View Resource Detail
					e. Event detail
					f. Mobile view
	'Arguments		:None
	'Returns		:None
	'Date	 		:11-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testFTS53247() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventList objEventList = new EventList();

		try {
			gstrTCID = "53247 ";
			gstrTO = "Create private status type ST, associate ST with "
					+ "resource RS at resource level and verify that users "
					+ "can view the status type only if they have any of the"
					+ " affiliated resource rights on RS from following screens:"
					+ "a. Region view"
					+ "b. Map screen (from resource pop window)"
					+ "c. Custom view" + "d. View Resource Detail"
					+ "e. Event detail" + "f. Mobile view";
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

			String statTypeName = "ST" + strTimeText;
			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";			
			String strStatusTypeValues[] = new String[5];			
		
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";
			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

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

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName_1 = "AutoUsr1" + System.currentTimeMillis();
			String strUserName_2 = "AutoUsr2" + System.currentTimeMillis();
			String strUserName_3 = "AutoUsr3" + System.currentTimeMillis();
			String strUserName_4 = "AutoUsr4" + System.currentTimeMillis();
			
			String strUsrFulName_1 = strUserName_1;
			String strUsrFulName_2 = strUserName_2;
			String strUsrFulName_3 = strUserName_3;
			String strUsrFulName_4 = strUserName_4;
			
			
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statpNumTypeName, statpTextTypeName,
					statpMultiTypeName, statpSaturatTypeName };

		/*
		 * 'Precondition	:
					1. Resource type RT is created selecting a role based status type ST
					2. Resource RS is created providing address under resource type RT
					3. View V1 is created selecting ST and RT
					4. Event template ET is created selecting ST and RT
					5. Event E1 is created selecting RS
					6. Users U1, U2, U3, U4 are created and has following rights:
					a) "View Custom View"
					b) "View Resource" right on RS
					c) Role to view the status type ST
					7. All the users have added resource RS and status types ST to their respective custom views 	
		 */
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes.fetchSTValueInStatTypeList(selenium,
						statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(selenium, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] updateRightValue = { { strStatusTypeValues[0],
						"true" } };
				String[][] strViewRightValue = { { strStatusTypeValues[0],
						"true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strViewRightValue,
						updateRightValue, true);
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

		/*1. Resource type RT is created selecting a role based status type ST */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='" +  strStatusTypeValues[0]
								+ "']");
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
				strRTVal = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 2. Resource RS is created providing address under resource type
		 * RT
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

	    /*3. View V1 is created selecting ST and RT */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				String strSTValues[] = {  strStatusTypeValues[0] };
				strFuncResult = objViews.createView(selenium, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

	  /*4. Event template ET is created selecting ST and RT */

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
				String[] strResTypeValue = { strRTVal };
				String[] strStatusTypeVal = { strStatusTypeValues[0] };
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

		/*5. Event E1 is created selecting RS */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
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
			
		/*6. Users U1, U2, U3, U4 are created and has following rights:
			a) "View Custom View"
			b) "View Resource" right on RS
			c) Role to view the status type ST */
		
		//U1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_1, strInitPwd, strConfirmPwd, strUsrFulName_1);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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

			//U2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_2, strInitPwd, strConfirmPwd, strUsrFulName_2);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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
			
			//U3
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_3, strInitPwd, strConfirmPwd, strUsrFulName_3);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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
			
			//U4
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName_4, strInitPwd, strConfirmPwd, strUsrFulName_4);

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, false,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
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
			 * 7. All the users have added resource RS and status types ST to
			 * their respective custom views
			 */
			
			//U1
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
				strFuncResult = objPreferences.createCustomViewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//U2

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
				strFuncResult = objPreferences.createCustomViewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//U3
			
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
				strFuncResult = objPreferences.createCustomViewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//U4
			
			
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences.createCustomViewWitTablOrMapOption(selenium,
						strRS, strResrctTypName, statTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Login as RegAdmin and navigate to Setup>>Status Types No
			 * Expected Result
			 */

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
				
				strFuncResult = objStatusTypes.navStatusTypList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Select to create a number status type, create number status
			 * type NST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save NST NST is listed in the
			 * "Status Type List"
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statpNumTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select to create a text status type, create text status type
			 * TST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save TST TST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statpTextTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select to create a saturation score status type, create
			 * saturation score status type SST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save SST SST is listed in the
			 * "Status Type List"
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpSaturatTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select to create a multi status type, create multi status type
			 * MST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save MST MST is listed in the
			 * "Status Type List"
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpMultiTypeName);
				if (strStatusTypeValues[4].compareTo("") != 0) {
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
						selenium, statpMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to Setup >> Resource, and click on 'Edit Status Types'
			 * link associated with RS, associate NST, MST, TST and SST and
			 * click on 'Save'. RT is listed in the "Resource Type List" screen
			 */

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[4], true);
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
			
			/*
			 * 8 Navigate to Setup>>Resource Types, click on the "Users" link
			 * associated with RS. "Assign Users to "RS"" screen is displayed
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objResources.navResourcesList(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objResources.navToAssignUsersOFRes(selenium, strResource);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
		/*9	Select the data as follows and save the page:
			1. "Update Status" right corresponding to user U1
			2. "Run Reports" right corresponding to user U2
			3. "Associated with" right corresponding to user U3 */
							
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.assignUsrToResource(selenium,
						false, true, false, true, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.assignUsrToResource(selenium,
						false, false, true, true, strUsrFulName_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.assignUsrToResource(selenium,
						true, false, false, true, strUsrFulName_3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 10 Navigate to Setup>>Views, select to edit view V1 and select
		 * status types NST, MST, TST and SST to view V1 and save V1 is
		 * listed in the "Region Views List"
		 */
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToEditView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
							
		/*
		 * 12 Navigate to Event>>Event Management and edit event E1, select
		 * status types NST, MST, TST and SST and save No Expected Result
		 */
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objEventSetup.navToEventManagementNew(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objEventSetup.navEditEventPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 11 Click on 'Customize Resource Detail View' Screen, create a
		 * section 'Sec' then click on 'Uncategorized' section, drag and
		 * drop the status types NST, MST, TST and SST to section 'Sec' and
		 * click on 'Save' "Region Views List" screen is displayed.
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
		/*
		 * 13 Logout & login as user U1. Navigate to Views>>V1 Status types
		 * NST, MST, TST and SST are displayed in the view V1 screen
		 */		
						
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;				
				strFuncResult = objLogin.login(selenium, strUserName_1, strInitPwd);
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
				String[] strArStatType2 = { statTypeName, statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 14 Navigate to Views>>Custom and navigate to "Custom View - Map"
		 * screen NST, MST, TST and SST are displayed in the resource pop-up
		 * window of RS.
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
		/*
		 * 15 Navigate to Views>>Map MST is displayed in the "Status Type" drop-down.
		 * NST, MST, TST and SST are displayed in the resource pop-up window of RS.
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
			
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statpMultiTypeName + "']"));
				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}			
		/*
		 * 16 Click on the "View Info" link on the resource pop-up window of
		 * RS Status types NST, MST, TST and SST are displayed in the
		 * "View Resource Detail" screen under status type section "Sec"
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
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
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 17 Click on the event banner of E1 Status types NST, MST, TST and
		 * SST are displayed in the "Event Status" screen
		 */			
			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName, };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 18 Logout & login as user U2. Navigate to Views>>V1 Status types
		 * NST, MST, TST and SST are displayed in the view V1 screen
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
				String[] strArStatType2 = { statTypeName, statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 19 Navigate to Views>>Custom and navigate to "Custom View - Map"
		 * screen NST, MST, TST and SST are displayed in the resource pop-up
		 * window of RS.
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
		/*
		 * 20 Navigate to Views>>Map MST is displayed in the "Status Type" drop-down.
		 * NST, MST, TST and SST are displayed in the resource pop-up window of RS.
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
			
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statpMultiTypeName + "']"));
				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;
			}
						
		/*
		 * 21 Click on the "View Info" link on the resource pop-up window of
		 * RS Status types NST, MST, TST and SST are displayed in the
		 * "View Resource Detail" screen under status type section "Sec"
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
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
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 22 Click on the event banner of E1 Status types NST, MST, TST and
		 * SST are displayed in the "Event Status" screen
		 */
			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName, };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}				
		/*
		 * 23 Logout & login as user U3. Navigate to Views>>V1 Status types
		 * NST, MST, TST and SST are displayed in the view V1 screen
		 */			
			//U3		
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
				String[] strArStatType2 = { statTypeName, statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 24 Navigate to Views>>Custom and navigate to "Custom View - Map"
		 * screen NST, MST, TST and SST are displayed in the resource pop-up
		 * window of RS.
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
		/*
		 * 25 Navigate to Views>>Map MST is displayed in the "Status Type" drop-down. 
		 * NST, MST, TST and SST are displayed in the resource pop-up window of RS.
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

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statpMultiTypeName + "']"));

				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 26 Click on the "View Info" link on the resource pop-up window of
		 * RS Status types NST, MST, TST and SST are displayed in the
		 * "View Resource Detail" screen under status type section "Sec"
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName, };			
				strFuncResult = objViews.verifySTInViewResDetail(selenium,
						strSection1, strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 27 Click on the event banner of E1 Status types NST, MST, TST and
		 * SST are displayed in the "Event Status" screen
		 */
			try {
				assertEquals("", strFuncResult);
				String strArStatType2[] = { statpNumTypeName,
						statpTextTypeName, statpMultiTypeName,
						statpSaturatTypeName, };
				strFuncResult = objEventList.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 28 Logout & login as user U4. Navigate to Views>>V1 Status types
		 * NST, MST, TST and SST are NOT displayed in the view V1 screen for
		 * resource RS
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
				strFuncResult = objLogin.login(selenium, strUserName_4,
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
				String[] strArStatType2 = { statTypeName, statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 29 Navigate to Views>>Custom and navigate to "Custom View - Map"
		 * screen NST, MST, TST and SST are NOT displayed in the resource
		 * pop-up window of RS.
		 */			
			try {
				assertEquals("Status Type " + statpTextTypeName
						+ " is NOT displayed in the User view screen",
						strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String strArStatType3[] = {statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };				
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType3,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 30 Navigate to Views>>Map MST is NOT displayed in the
		 * "Status Type" drop-down.
		 * NST, MST, TST and SST are NOT displayed in the resource pop-up
		 * window of RS.
		 */
			try {
				String strMsg = " Role Based Status type " + statpNumTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statpTextTypeName + " is NOT displayed"
						+ " Role Based Status type " + statpMultiTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statpSaturatTypeName + " is NOT displayed";

				assertEquals(strMsg, strFuncResult);
				String strArStatType3[] = {statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };

				String[] strEventStatType = {};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strArStatType3, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				String strMsg = " Role Based Status type " + statpNumTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statpTextTypeName + " is NOT displayed"
						+ " Role Based Status type " + statpMultiTypeName
						+ " is NOT displayed" + " Role Based Status type "
						+ statpSaturatTypeName + " is NOT displayed";
				assertEquals(strMsg, strFuncResult);
				strFuncResult = "";

				assertFalse(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statpMultiTypeName + "']"));

				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is displayed in dropdown. ";
				gstrReason = strFuncResult;
			}			
		/*
		 * 31 Click on the "View Info" link on the resource pop-up window of
		 * RS Status types NST, MST, TST and SST are NOT displayed in the
		 * "View Resource Detail" screen
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
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
									+ statpNumTypeName + "']"));

					log4j.info("The Status Type "
							+ statpNumTypeName
							+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type "
							+ statpNumTypeName
							+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statpNumTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statpTextTypeName + "']"));

					log4j.info("The Status Type "
							+ statpTextTypeName
							+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type "
							+ statpTextTypeName
							+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statpTextTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statpMultiTypeName + "']"));

					log4j.info("The Status Type "
							+ statpMultiTypeName
							+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type "
							+ statpMultiTypeName
							+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statpMultiTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {
					assertFalse(selenium
							.isElementPresent("//table[starts-with(@id,'stGroup')]/tbody/tr/td[2]/a[text()='"
									+ statpSaturatTypeName + "']"));

					log4j.info("The Status Type "
							+ statpSaturatTypeName
							+ " is NOT displayed on the view resource detail screen. ");
				} catch (AssertionError Ae) {
					log4j.info("The Status Type "
							+ statpSaturatTypeName
							+ " is  displayed on the view resource detail screen. ");
					strFuncResult = "The Status Type "
							+ statpSaturatTypeName
							+ " is  displayed on the view resource detail screen. ";
					gstrReason = strFuncResult;

				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 32 Click on the event banner of E1 Status types NST, MST, TST and
		 * SST are NOT displayed in the "Event Status" screen
		 */			
			try {
				assertEquals("", strFuncResult);
				
				String strArStatType2[] = { statpNumTypeName,
						statpTextTypeName, statpMultiTypeName,
						statpSaturatTypeName, };
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Status type NOT displayed", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "53247";
			gstrTO = "Create private status type ST, associate ST with "
					+ "resource RS at resource level and verify that users "
					+ "can view the status type only if they have any of the"
					+ " affiliated resource rights on RS from following screens:"
					+ "a. Region view"
					+ "b. Map screen (from resource pop window)"
					+ "c. Custom view" + "d. View Resource Detail"
					+ "e. Event detail" + "f. Mobile view";
			gstrResult = "FAIL";
			gstrReason = "";

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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	/********************************************************************************
	'Description	:Create a private status type ST, associate ST with resource RS at resource level and verify that ST is displayed on following screens for system admin:
					a. Region view
					b. Map (in status type dropdown & in resource pop up window)
					d. View Resource Detail
					e. Custom view
					f. Event details
					g. Mobile view
					h. While configuring form
					i. Edit My Status Change Preferences
					j. Create/Edit Interfaces
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testFTS53238() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "53238 ";
			gstrTO = "Create a private status type ST, associate ST with resource "
					+ "RS at resource level and verify that ST is displayed on following"
					+ " screens for system admin: "
					+ "a. Region view"
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail"
					+ "e. Custom view"
					+ "f. Event details"
					+ "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";
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

			String statTypeName_1 = "ST_1" + strTimeText;
			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";		
			String strStatusTypeValues[] = new String[5];
			
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";
			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strTempName_1 = "ET1" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName_1 = "Event_1" + System.currentTimeMillis();
			String strEveName = "Event" + System.currentTimeMillis();			
			String strInfo = "Automation";
			String strEventValue_1 = "";
			String strEventValue = "";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statpNumTypeName, statpTextTypeName,
					statpMultiTypeName, statpSaturatTypeName };
		/*
		 * 	'Precondition	:1. Resource type RT is created selecting a role based status type ST
					2. Resource RS is created providing address under resource type RT
					3. View V1 is created selecting ST and RT
					4. Event template ET is created selecting ST and RT
					5. Event E1 is created selecting RS
					6. Users U1 is created and has following rights:
					a) "View Custom View"
					b) "Update Status" and "View Resource" rights on RS
					c) Role to view the status type ST
					7. User U1 has added resource RS and status types ST to his/her custom view 	
		 */
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
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
				blnLogin = true;
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

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statTypeName_1, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName_1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] updateRightValue = { { strStatusTypeValues[0],
						"true" } };
				String[][] strViewRightValue = { { strStatusTypeValues[0],
						"true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strViewRightValue,
						updateRightValue, true);
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

			/*1. Resource type RT is created selecting a role based status type ST */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='" +  strStatusTypeValues[0]
								+ "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTVal = objResourceTypes.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);

				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2. Resource RS is created providing address under resource type
			 * RT
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			/*3. View V1 is created selecting ST and RT */
			
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
				String strSTValues[] = {  strStatusTypeValues[0] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*	4. Event template ET is created selecting ST and RT */

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

				String[] strResTypeValue = { strRTVal };
				String[] strStatusTypeVal = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName_1, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName_1);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*5. Event E1 is created selecting RS */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventEndNever(seleniumPrecondition,
						strTempName_1, strResource, strEveName_1, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue_1 = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName_1);
				if (strEventValue_1.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*6. Users U1 is created and has following rights:
				a) "View Custom View"
				b) "Update Status" and "View Resource" rights on RS
				c) Role to view the status type ST */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd, strUsrFulName_1);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.CustomView"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
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

			try {
				assertEquals("", strFuncResult);
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

			/*
			 * 3 Select to create a number status type, create number status
			 * type NST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save NST NST is listed in the
			 * "Status Type List"
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strNSTValue, statpNumTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statpNumTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select to create a text status type, create text status type
			 * TST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save TST TST is listed in the
			 * "Status Type List"
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strTSTValue, statpTextTypeName, strStatTypDefn,
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statpTextTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Select to create a saturation score status type, create
			 * saturation score status type SST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save SST SST is listed in the
			 * "Status Type List"
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strSSTValue, statpSaturatTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpSaturatTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select to create a multi status type, create multi status type
			 * MST selecting the option
			 * "Only users affiliated with specific resources may view or update this status type"
			 * for "Status Type Visibility" and save MST MST is listed in the
			 * "Status Type List"
			 */
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpMultiTypeName);
				if (strStatusTypeValues[4].compareTo("") != 0) {
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
						selenium, statpMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Navigate to Setup >> Resource Types. 'Resource Type List'
			 * screen is displayed
			 */

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[4], true);
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
			
		/*
		 * 8 Navigate to Setup >> Resource, and click on 'Edit Status Types'
		 * link associated with RS1, associate NST, MST, TST and SST and
		 * click on 'Save'. 'RS1' is displayed in 'Resource Type List'
		 * screen
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				for (int i = 1; i < strStatusTypeValues.length; i++) {
					strFuncResult = objResources.selAndDeselSTInEditResLevelST(
							selenium, strStatusTypeValues[i], true);
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 9 Navigate to Setup >> views, create a view 'V1' selecting NST,
		 * MST, TST and SST and resource 'RS1' and click on 'Save'. View
		 * 'V1' is displayed in 'Region Views List' screen.
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToEditView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						selenium, strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToRgionViewList(selenium,
						strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 10 Click on 'Customize Resource Detail View' Screen, create a
		 * section 'Sec' then click on 'Uncategorized' section, Drag and
		 * drop the status type NST, MST, TST and SST to section 'Sec' and
		 * click on 'Save' 'Region Views List' screen is displayed.
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
		/*
		 * 17 Navigate to Event >> Event Setup and Click on 'Edit' link
		 * associated with event template 'ET1' ('RT' is already been
		 * selected in the earlier steps) select NST, MST, TST and SST and
		 * click on 'Save'. 'Event Template List' screen is displayed with
		 * the event template 'ET1'
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objEventSetup.editEventTemplate(selenium, strTempName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName_1, true, strStatusTypeValues[1],
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName_1, true, strStatusTypeValues[2],
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName_1, true, strStatusTypeValues[3],
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName_1, true, strStatusTypeValues[4],
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}					
		/*
		 * 11 Navigate to View >> V1. Status type NST, MST, TST and SST is
		 * displayed with the resource 'RS1'
		 */			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName,
						strResources, strRTVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArStatType2 = { statTypeName_1, statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 12 Click on Resource 'RS1'. Status type NST, MST, TST and SST is
		 * displayed under the section 'Sec' in 'View Resource Detail'
		 * section.
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
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 13 Navigate to View >> Map, select resource 'RS1' under 'Find
		 * Resource' dropdown. Status type NST, MST, TST and SST is
		 * displayed on resource pop up wind
		 */		
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
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
		 * 14 Click on 'Status Type (to color icons)' dropdown list Status
		 * Type MST is displayed in the dropdown list.
		 */
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//select[@id='statusType']/option[text()='"
								+ statpMultiTypeName + "']"));

				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;
			}					
		/*
		 * 18 Navigate to Event >> Event Management, create an event 'EV'
		 * under 'ET1' selecting 'RS1' and click on 'Save' now click on the
		 * event banner 'EV' which is displayed at the top. Status Types
		 * ST1, NST, MST, TST and SST is displayed in the 'Event Status'
		 * screen.
		 */	
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventEndNever(selenium,
						strTempName_1, strResource, strEveName, strInfo, true);
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
				strFuncResult =objEventSetup.navEditEventPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);				
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 15 Navigate to Preferences >> Customized View,add resources 'RS1'
		 * and status type NST, MST, TST and SST to custom level and click
		 * on 'Save'. Status type NST, MST, TST and SST is displ
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statpNumTypeName);

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
				String strSTValue[][] = { { strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[3], "true" },
						{ strStatusTypeValues[4], "true" } };
				strFuncResult = objPreferences
						.addSTInEditCustViewOptionPageTablOrMap(selenium,
								strSTValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };

				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTablNew(
						selenium, strResrctTypName, strResource, strArStatType);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 16 Navigate to the Map format of custom view, select resource
		 * 'RS1' in the 'Find Resource 'dropdown' list. Status type NST,
		 * MST, TST and SST is displayed in 'Custom View - Map' screen.
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource, strEventStatType, strArStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName_1 + "/" + strInitPwd,
						statTypeName_1 + "," + statpNumTypeName + ","
								+ statpTextTypeName + "," + statpMultiTypeName
								+ "," + statpSaturatTypeName, strViewName_1,
						"From 19th Step", strResource, strSection1,
						strEveName + "," + strEveName_1, strTempName_1,
						strRegn, strResrctTypName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "53238";
			gstrTO = "Create a private status type ST, associate ST with resource "
					+ "RS at resource level and verify that ST is displayed on following"
					+ " screens for system admin: "
					+ "a. Region view"
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail"
					+ "e. Custom view"
					+ "f. Event details"
					+ "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";
			gstrResult = "FAIL";
			gstrReason = "";

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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}
	/********************************************************************************
	'Description	:Create a private status type ST, associate ST with resource RS at resource TYPE 
					level and verify that ST is displayed on following screens for system admin:
					a. Region view
					b. Map (in status type dropdown & in resource pop up Window)
					d. View Resource Detail
					e. Custom view
					f. Event details
					g. Mobile view
					h. While configuring form
					i. Edit My Status Change Preferences
					j. Create/Edit Interfaces
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-Dec-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testFTS53140() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "53140 ";
			gstrTO = "Create a private status type ST, associate ST with "
					+ "resource RS at resource TYPE level and verify that "
					+ "ST is displayed on following screens for system admin:"
					+ "a. Region view"
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail" + "e. Custom view"
					+ "f. Event details" + "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";
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

			String statTypeName_1 = "ST_1" + strTimeText;
			String statpNumTypeName = "pNST" + strTimeText;
			String statpTextTypeName = "pTST" + strTimeText;
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;

			String strStatusTypeValues[] = new String[5];

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// RT data
			String strResrctTypName = "RT" + System.currentTimeMillis();
			String strRTVal = "";
			// Resource
			String strResource = "RS" + System.currentTimeMillis();
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";

			String strTempName_1 = "ET1" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName_1 = "Event_1" + System.currentTimeMillis();
			String strEveName = "Event" + System.currentTimeMillis();

			String strInfo = "Automation";
			String strEventValue_1 = "";
			String strEventValue = "";

			// Role
			String strRolesName = "Rol" + System.currentTimeMillis();
			String strRoleValue = "";

			// USER
			String strUserName_1 = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType1[] = { statpNumTypeName, statpTextTypeName,
					statpMultiTypeName, statpSaturatTypeName };

		/*
		 * Precondition	:1. Resource type RT is associated with status type ST1.
				2. Resource RS1 is created under resource Type RT.
				3. Event template ET is created selecting ST1 and RT.
				4. Event E1 is created under the template ET selecting RS. 
		 */
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statTypeName_1,

						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] =
				objStatusTypes.fetchSTValueInStatTypeList(selenium,
						statTypeName_1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objRoles.navCreateRolePge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.roleMandtoryFlds(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] updateRightValue = { { strStatusTypeValues[0],
						"true" } };
				String[][] strViewRightValue = { { strStatusTypeValues[0],
						"true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						selenium, false, false, strViewRightValue,
						updateRightValue, true);
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
		 * 1. Resource type RT is associated with status type ST1.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						selenium, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
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
				strRTVal = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName);
				if (strRTVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 2. Resource RS1 is created under resource Type RT.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource, strAbbrv,

						strResrctTypName, strContFName, strContLName, strState,

						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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

		/* 3. Event template ET is created selecting ST1 and RT. */

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
				String[] strResTypeValue = { strRTVal };
				String[] strStatusTypeVal = { strStatusTypeValues[0] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						selenium, strTempName_1, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(selenium,
						strTempName_1);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 4. Event E1 is created under the template ET selecting RS.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				strFuncResult = "Failed";
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventEndNever(selenium,
						strTempName_1, strResource, strEveName_1,
						strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strEventValue_1 = objEventSetup.fetchEventValueInEventList(
						selenium, strEveName_1);
				if (strEventValue_1.compareTo("") != 0) {
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
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView"), true);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

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

		/*
		 * 3 Select to create a number status type, create number status
		 * type NST selecting the option "Only users affiliated with
		 * specific resources may view or update this status
		 * 
		 * type" for "Status Type Visibility" and save NST NST is listed in
		 * the "Status Type List"
		 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strNSTValue,
								statpNumTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
						statpNumTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 5 Select to create a text status type, create text status type
		 * TST selecting the option "Only users affiliated with specific
		 * resources may view or update this status
		 * 
		 * type" for "Status Type Visibility" and save TST TST is listed in
		 * the "Status Type List"
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strTSTValue,
								statpTextTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,

						statpTextTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 6 Select to create a saturation score status type, create
		 * saturation score status type SST selecting the option "Only users
		 * affiliated with specific resources may view or update this status
		 * 
		 * type" for "Status Type Visibility" and save SST SST is listed in
		 * the "Status Type List"
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strSSTValue,
								statpSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpSaturatTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 4 Select to create a multi status type, create multi status type
		 * MST selecting the option "Only users affiliated with specific
		 * resources may view or update this status
		 * 
		 * type" for "Status Type Visibility" and save MST MST is listed in
		 * the "Status Type List"
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(selenium,
								strMSTValue, statpMultiTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statpMultiTypeName);
				if (strStatusTypeValues[4].compareTo("") != 0) {
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
						selenium, statpMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						selenium, statpMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 7 Navigate to Setup >> Resource Types. 'Resource Type List'
		 * screen is displayed
		 */

		/*
		 * 8 Navigate to Setup >> Resource Type, and click on 'Edit' link
		 * associated with RT and select NST, MST, TST and SST under 'Status
		 * Types' section and click on 'Save'. 'RT' is displayed in
		 * 'Resource Type List' screen
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						selenium, strStatusTypeValues[4], true);
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

		/*
		 * 9 Navigate to Setup >> views, create a view 'V1' selecting NST,
		 * MST, TST and SST and resource 'RS1' and click on 'Save'. View
		 * 'V1' is displayed in 'Region Views List' screen.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strVewDescription = "Automation";
				strViewType = "Summary Plus (Resources as rows."
						+ " Status types and comments as columns.)";
				String strSTValues[] = { strStatusTypeValues[1],
						strStatusTypeValues[2], strStatusTypeValues[3],
						strStatusTypeValues[4] };
				strFuncResult = objViews.createView(selenium, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * 10 Click on 'Customize Resource Detail View' Screen, create a
		 * section 'Sec' then click on 'Uncategorized' section, Drag and
		 * drop the status type NST, MST, TST and SST to section 'Sec' and
		 * click on 'Save' 'Region Views List' screen is displayed.
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
				strFuncResult = objViews.dragAndDropSTtoSection(seleniumFirefox, strArStatType1, strSection1, true);
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
				blnLogin = false;
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
		 * 11 Navigate to View >> V1. Status type NST, MST, TST and SST is
		 * displayed with the resource 'RS1'
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResrctTypName,
						strResources, strRTVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArStatType2 = { statpMultiTypeName,
						statpNumTypeName, statpSaturatTypeName,
						statpTextTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 12 Click on Resource 'RS1'. Status type NST, MST, TST and SST is
		 * displayed under the section 'Sec' in 'View Resource Detail'
		 * section.
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
				strFuncResult = objViews.verifySTInViewResDetailNew(selenium,
						strSection1, strSectionValue, strArStatType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 13 Navigate to View >> Map, select resource 'RS1' under 'Find
		 * Resource' dropdown. Status type NST, MST, TST and SST is
		 * displayed on resource pop up window.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
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
		 * 14 Click on 'Status Type (to color icons)' dropdown list Status
		 * Type MST is displayed in the dropdown list.
		 */
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
				.isElementPresent("//select[@id='statusType']/option[text()='"
						+ statpMultiTypeName +

						"']"));

				log4j.info("status type " + statpMultiTypeName
						+ " is displayed in dropdown. ");
			} catch (AssertionError Ae) {
				log4j.info("status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ");
				strFuncResult = "status type " + statpMultiTypeName
						+ " is NOT displayed in dropdown. ";
				gstrReason = strFuncResult;

			}
		/*
		 * 17 Navigate to Event >> Event Setup and Click on 'Edit' link
		 * associated with event template 'ET1' ('RT' is already been
		 * selected in the earlier steps) select NST, MST, TST and SST and
		 * click on 'Save'. 'Event Template List' screen is displayed with
		 * the event template 'ET1'
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.editEventTemplate(selenium,
						strTempName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTValues[] = { strStatusTypeValues[1],
						strStatusTypeValues[2], strStatusTypeValues[3], };
				for (int i = 0; i < strSTValues.length; i++) {
					strFuncResult = objEventSetup
					.selectAndDeselectSTInEditEventTemplatePage(selenium,
					strTempName_1, true, strSTValues[i], false);
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventTemplatePage(selenium,
								strTempName_1, true, strStatusTypeValues[4],
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 18 Navigate to Event >> Event Management, create an event 'EV'
		 * under 'ET1' selecting 'RS1' and click on 'Save' now click on the
		 * event banner 'EV' which is displayed at the top. Status Types
		 * ST1, NST, MST, TST and SST is displayed in the 'Event Status'
		 * screen.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
	
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventEndNever(selenium,
						strTempName_1, strResource, strEveName,
						strInfo, true);
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
				strFuncResult = objEventSetup.navEditEventPage(selenium,
				strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true,
								strStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true,
								strStatusTypeValues[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true,
								strStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.selectAndDeselectSTInEditEventPage(selenium,
								strEveName, true,
								strStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 15 Navigate to Preferences >> Customized View,add resources 'RS1'
		 * and status type NST, MST, TST and SST to custom level and click
		 * on 'Save'. Status type NST, MST, TST and SST is displ
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
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strRS[] = { strResource };
				strFuncResult = objPreferences
						.createCustomViewWitTablOrMapOption(selenium, strRS,
								strResrctTypName, statpNumTypeName);
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
				String strSTValue[][] = { { strStatusTypeValues[1], "true" },
						{ strStatusTypeValues[2], "true" },
						{ strStatusTypeValues[3], "true" },
						{ strStatusTypeValues[4], "true" } };
				strFuncResult = objPreferences
				.addSTInEditCustViewOptionPageTablOrMap(selenium, strSTValue,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				strFuncResult = objViews
				.checkResTypeRSAndSTInViewCustTablNew(selenium,
						strResrctTypName,
						strResource, strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * 16 Navigate to the Map format of custom view, select resource
		 * 'RS1' in the 'Find Resource 'dropdown' list. Status type NST,
		 * MST, TST and SST is displayed in 'Custom View - Map' screen.
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
				strFuncResult = objViewMap.navResPopupWindow(selenium,

				strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strArStatType[] = { statpNumTypeName, statpTextTypeName,
						statpMultiTypeName, statpSaturatTypeName };
				String[] strEventStatType = {};
				strFuncResult = objViewMap

				.verifyStatTypesInResourcePopup_ShowMap(selenium, strResource,

				strEventStatType, strArStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

				String[] strTestData = {
						propEnvDetails.getProperty("Build"),
						gstrTCID,
						strUserName_1 + "/" + strInitPwd,
						statTypeName_1 + "," + statpNumTypeName + ","
								+ statpTextTypeName + "," +

								statpMultiTypeName + "," +

								statpSaturatTypeName, strViewName_1,
						"From 19th Step", strResource, strSection1,
						strEveName + "," + strEveName_1,

						strTempName_1, strRegn, strResrctTypName };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "53140";
			gstrTO = "Create a private status type ST, associate ST with "
					+ "resource RS at resource TYPE level and verify that "
					+ "ST is displayed on following screens for system admin:"
					+ "a. Region view"
					+ "b. Map (in status type dropdown & in resource pop up window)"
					+ "d. View Resource Detail" + "e. Custom view"
					+ "f. Event details" + "g. Mobile view"
					+ "h. While configuring form"
					+ "i. Edit My Status Change Preferences"
					+ "j. Create/Edit Interfaces";
			gstrResult = "FAIL";
			gstrReason = "";

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
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}
		}
	}

}
