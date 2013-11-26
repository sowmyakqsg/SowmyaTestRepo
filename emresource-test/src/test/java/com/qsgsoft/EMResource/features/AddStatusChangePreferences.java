package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.Writer;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*************************************************************************************
' Description :This class includes Add Status Change Preferences requirement testcases
' Date		  :20-Oct-2012
' Author	  :QSG
'-------------------------------------------------------------------------------------
' Modified Date                                                      Modified By
' <Date>                           	                                  <Name>
'**************************************************************************************/

@SuppressWarnings("unused")
public class AddStatusChangePreferences  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.AddStatusChangePreferences");
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
	String gstrTimeOut="";	
	Selenium selenium,seleniumPrecondition;

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
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

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
			seleniumPrecondition.close();
		} catch (Exception e) {
		}
		seleniumPrecondition.stop();
		try {
			selenium.close();
		} catch (Exception e) {
		}
		
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

	/*********************************************************************************
	'Description	:Verify that user can receive the status change notifications upon 
	                  status change of a non-event related status type. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:2-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------
	'Modified Date                                                 Modified By
	'<Date>                                                        <Name>
	**********************************************************************************/

	@Test
	public void testBQS99136() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General

		try {
			gstrTCID = "BQS-99136 ";
			gstrTO = "Verify that user can receive the status change" +
					" notifications upon status change of a non-event" +
					" related status type.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

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
	
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;		
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String strStatTypDefn = "Automation";			
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";			
			String strSaturAbove = "400";
			String strSaturBelow = "200";
			
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;		
			int intEMailRes_1 = 0;
			int intPagerRes_1 = 0;			
			int intEMailRes_2= 0;
			int intPagerRes_2 = 0;			
			int intResCnt = 0;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";
		
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";			
			String strCurrDate = "";
			String strCurrDate2="";
			
			// Data for creating View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "";
			String strViewType = "";
		
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
			 * 1. Test user has created status types 'NST', 'MST' & 'SST'
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//NST

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
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SST
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
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrSaturatTypeName);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//MST
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
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrMultiTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*2. Statuses 'S1' & 'S2' are created under 'MST'. */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName1);
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
						seleniumPrecondition, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
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
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[2] + "']");
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

			/*4. Resource 'RS' is created under 'RT' */

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
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource);

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
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
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

			/*
			 * 5. User 'A' has update right on 'RS' and is assigned a role 'R'
			 * which has update right on all four status types.
			 */
			
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
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
			
			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);			
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
			 * 7. View 'V1' is created selecting 'NST', 'MST' & 'SST' and 'RS'
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
				String strSTValues[] = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2] };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");			
	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");
					
			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A, strInitPwd);
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

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */
			
			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'NST', 'MST' & 'SST' are displayed under 'Uncategorized'.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult+"Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 6 For 'NST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 For 'SST', provide value 'X' for 'Above' and 'Y' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			
			
			//SST
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								strSTvalue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 For 'MST', select 'Email', 'Pager' and 'Web' for both 'S1' and
			 * 'S2' 'My Status Change Preferences' page is displayed. 'X' is
			 * displayed under 'Email', 'Pager' and 'Web' for both 'S1' and 'S2'
			 * values.
			 */
			
			//MST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[0], true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[1], true,
								true, true);
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
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUpdateStatusByKey(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium, statrNumTypeName, "101", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Provide the following values: 1. NST: A value above 'A' 2.
			 * MST: Select status 'S2' 3. SST: A value below 'Y' User A receives
			 * the following: 1. 'Email', 'Pager' and 'Web' for 'NST' 2.
			 * 'Email', 'Pager' and 'Web' for 'MST' 3. 'Email', 'Pager' and
			 * 'Web' for 'SST'
			 */
				
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrNumTypeName
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
				Writer output = null;
				String text = strMsgBody1;
				File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUpdateStatusByKey(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[1]);
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium, statrSaturatTypeName, "429", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrSaturatTypeName + " from 0 to 429; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrSaturatTypeName
						+ " status from 0 "
						+ "to 429.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrSaturatTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
				Writer output = null;
				String text = strMsgBody1;
				File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Input1.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail1.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_1 == 2 && intPagerRes_1 == 1) {
						intResCnt++;
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
				
					
					selenium.selectWindow("");
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//MST
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUpdateStatusByKey(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[2], false, "",
						"");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrMultiTypeName, strStatusName2, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrMultiTypeName + " status from -- to "+strStatusName2+".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrMultiTypeName + " status from -- to "+strStatusName2+".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrMultiTypeName + " from -- to "+strStatusName2+"; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrMultiTypeName
						+ " status from -- "
						+ "to "+strStatusName2+".\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrMultiTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
			

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
					

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					// check Email, pager notification
					if (intEMailRes_2 == 2 && intPagerRes_2 == 1) {
						intResCnt++;
					}
					
					selenium.selectWindow("");
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				if(intResCnt==6){
					gstrResult = "PASS";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-99136";
			gstrTO = "Verify that user can receive the status change" +
					" notifications upon status change of a non-event" +
					" related status type.";
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
	'Description	Verify that user can receive the status change notifications upon 
	'				status change of an event related status type.
	'Precondition	:1. Test user has created event related status types 'ENST', 'EMST' & 'ESST' with shift/expiration time
					2. Statuses 'S1' & 'S2' are created under 'MST'.
					3. Resource type 'RT' is created and is associated with all the three status types.
					4. Resource 'RS' is created under 'RT'
					5. User 'A' has update right on 'RS' and is assigned a role 'R' which has update right on all four status types.
					6. User 'A' is provided with 'Email' and 'Pager' address.
					7. User 'A' has 'Edit Status Change Notification Preferences' right
					8. Event Template 'ET' is created selecting 'ENST', 'EMST', 'ESST' and 'RT'
					9. Event 'Eve' is created under it.  
	'Arguments		:None
	'Returns		:None
	'Date	 		:2-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Test
	public void testBQS102854() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General

		try {
			gstrTCID = "BQS-102854 ";
			gstrTO = "Verify that user can receive the status change notifications"
					+ " upon status change of an event related status type.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

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

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;

			int intEMailRes_1 = 0;
			int intPagerRes_1 = 0;

			int intEMailRes_2 = 0;
			int intPagerRes_2 = 0;

			int intResCnt = 0;

			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody2 = "";
			String strSubjName = "";

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			String strCurrDate = "";
			String strCurrDate2="";

			String strExpHr = "00";
			String strExpMn = "05";
			// Status Shift time
			String StatusTime = "";
			String strUpdTime = "";

			String strUpdTime_Shift = "";
			int intShiftTime = 30;
			

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
			 * 1. Test user has created event related status types 'ENST',
			 * 'EMST' & 'ESST' with shift/expiration time
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
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

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statrNumTypeName + "']"));

					log4j.info("Status type " + statrNumTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {

				log4j.info("Status type " + statrNumTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. ");

				gstrReason = "Status type " + statrNumTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. " + Ae;
			}
			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				seleniumPrecondition
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				seleniumPrecondition
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statrSaturatTypeName + "']"));

					log4j.info("Status type " + statrSaturatTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {

				log4j.info("Status type " + statrSaturatTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. ");

				gstrReason = "Status type " + statrSaturatTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. " + Ae;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrSaturatTypeName);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrMultiTypeName);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Statuses 'S1' & 'S2' are created under 'MST'.
			 */

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statrMultiTypeName, strStatusName1,
								strMSTValue, strStatTypeColor, strExpHr,
								strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statrMultiTypeName, strStatusName2,
								strMSTValue, strStatTypeColor, strExpHr,
								strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName1);
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
						seleniumPrecondition, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
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

			for (int i = 1; i < strSTvalue.length; i++) {
				try {
					assertEquals("", strFuncResult);

					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[i], true);
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

			/* 4. Resource 'RS' is created under 'RT' */

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
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
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
			
			/*
			 * 5. User 'A' has update right on 'RS' and is assigned a role 'R'
			 * which has update right on all four status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7. User 'A' has 'Edit Status Change Notification Preferences'
			 * right
			 */

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

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
			 * 8. Event Template 'ET' is created selecting 'ENST', 'EMST',
			 * 'ESST' and 'RT'
			 */
			
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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1], strSTvalue[2] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
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

				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");

			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A,
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

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */

			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'ENST', 'EMST' & 'ESST' are displayed under 'Uncategorized'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 For 'ENST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 For 'EMST', select 'Email', 'Pager' and 'Web' for both 'S1' and
			 * 'S2' 'My Status Change Preferences' page is displayed. 'X' is
			 * displayed under 'Email', 'Pager' and 'Web' for both 'S1' and 'S2'
			 * values.
			 */

			// SST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								strSTvalue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 For 'ESST', provide value 'X' for 'Above' and 'Y' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			// MST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[0], true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[1], true,
								true, true);
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
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");
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
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 10 Provide the following values: 1. NST: A value above 'A' 2.
			 * MST: Select status 'S2' 3. SST: A value below 'Y' User A receives
			 * the following: 1. 'Email', 'Pager' and 'Web' for 'NST' 2.
			 * 'Email', 'Pager' and 'Web' for 'MST' 3. 'Email', 'Pager' and
			 * 'Web' for 'SST'
			 */
				
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrNumTypeName
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
				Writer output = null;
				String text = strMsgBody1;
				File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Input.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[1]);
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
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrSaturatTypeName + " from 0 to 429; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrSaturatTypeName
						+ " status from 0 "
						+ "to 429.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrSaturatTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
				Writer output = null;
				String text = strMsgBody1;
				File file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Input1.txt");
				output = new BufferedWriter(new FileWriter(file));
				output.write(text);
				output.close();

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail1.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						output = null;
						text = strMsg;
						file = new File("C:\\Documents and Settings\\All Users\\Desktop\\Mail.txt");
						output = new BufferedWriter(new FileWriter(file));
						output.write(text);
						output.close();

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_1 == 2 && intPagerRes_1 == 1) {
						intResCnt++;
					}
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
				
					
					selenium.selectWindow("");
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//MST
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[2], false, "",
						"");
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
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrMultiTypeName + " status from -- to "+strStatusName2+".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrMultiTypeName + " status from -- to "+strStatusName2+".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrMultiTypeName + " from -- to "+strStatusName2+"; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrMultiTypeName
						+ " status from -- "
						+ "to "+strStatusName2+".\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrMultiTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
			

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
					

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					// check Email, pager notification
					if (intEMailRes_2 == 2 && intPagerRes_2 == 1) {
						intResCnt++;
					}
					
					selenium.selectWindow("");
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				if(intResCnt==6){
					gstrResult = "PASS";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-102854";
			gstrTO = "Verify that user can receive the status change" +
					" notifications upon status change of a non-event" +
					" related status type.";
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
	'Description	Verify that user can receive the status change notifications upon 
	'				status change of an event related status type.
	'Precondition	:1. Test user has created event related status types 'ENST', 'EMST' & 'ESST' with shift/expiration time
					2. Statuses 'S1' & 'S2' are created under 'MST'.
					3. Resource type 'RT' is created and is associated with all the three status types.
					4. Resource 'RS' is created under 'RT'
					5. User 'A' has update right on 'RS' and is assigned a role 'R' which has update right on all four status types.
					6. User 'A' is provided with 'Email' and 'Pager' address.
					7. User 'A' has 'Edit Status Change Notification Preferences' right
					8. Event Template 'ET' is created selecting 'ENST', 'EMST', 'ESST' and 'RT'
					9. Event 'Eve' is created under it.  
	'Arguments		:None
	'Returns		:None
	'Date	 		:2-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/

	@Ignore
	public void testBQS102854New() throws Exception {

		boolean blnLogin = false;

		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		ViewMap objViewMap = new ViewMap();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General

		try {
			gstrTCID = "BQS-102854 ";
			gstrTO = "Verify that user can receive the status change notifications"
					+ " upon status change of an event related status type.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

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

			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strSTvalue[] = new String[3];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;

			int intEMailRes_1 = 0;
			int intPagerRes_1 = 0;

			int intEMailRes_2 = 0;
			int intPagerRes_2 = 0;


			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			
			
			String strMsgBodyNST = "";
			String strMsgBodyNSTPager = "";
			String strMsgBodyNST2 = "";
			String strMsgBodyNSTPager2 = "";
			
			String strMsgBodySST = "";
			String strMsgBodySSTPager = "";
			String strMsgBodySST2 = "";
			String strMsgBodySSTPager2 = "";
			
			String strMsgBodyMST = "";
			String strMsgBodyMSTPager = "";
			String strMsgBodyMST2 = "";
			String strMsgBodyMSTPager2 = "";
			
			String strSubjName = "";

			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";

			String strCurrDate = "";
			String strCurrDate2="";

			String strExpHr = "00";
			String strExpMn = "05";
			// Status Shift time
			String StatusTime = "";
			String strUpdTime = "";

			String strUpdTime_Shift = "";
			int intShiftTime = 30;
			
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
			 * 1. Test user has created event related status types 'ENST',
			 * 'EMST' & 'ESST' with shift/expiration time
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
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

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statrNumTypeName + "']"));

					log4j.info("Status type " + statrNumTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {

				log4j.info("Status type " + statrNumTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. ");

				gstrReason = "Status type " + statrNumTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. " + Ae;
			}
			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				StatusTime = seleniumPrecondition.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");

				strUpdTime_Shift = strStatusTime[2];
				String strAdUpdTime = dts.addTimetoExisting(strUpdTime_Shift,
						intShiftTime, "HH:mm");
				strUpdTime_Shift = strAdUpdTime;
				log4j.info(strUpdTime_Shift);

				strStatusTime = strUpdTime_Shift.split(":");

				// Select shift time
				seleniumPrecondition.click(propElementDetails
						.getProperty("StatusType.CreateStatType.ShiftTime1"));
				seleniumPrecondition
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeHour1"),
								strStatusTime[0]);
				seleniumPrecondition
						.select(
								propElementDetails
										.getProperty("StatusType.CreateStatType.ShiftTimeMin1"),
								strStatusTime[1]);

				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statrSaturatTypeName + "']"));

					log4j.info("Status type " + statrSaturatTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {

				log4j.info("Status type " + statrSaturatTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. ");

				gstrReason = "Status type " + statrSaturatTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. " + Ae;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrSaturatTypeName);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.createEventSTWitMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrMultiTypeName);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2. Statuses 'S1' & 'S2' are created under 'MST'.
			 */

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statrMultiTypeName, strStatusName1,
								strMSTValue, strStatTypeColor, strExpHr,
								strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statrMultiTypeName, strStatusName2,
								strMSTValue, strStatTypeColor, strExpHr,
								strExpMn, "", "", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName1);
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
						seleniumPrecondition, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3. Resource type 'RT' is created and is associated with all the
			 * three status types.
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

			for (int i = 1; i < strSTvalue.length; i++) {
				try {
					assertEquals("", strFuncResult);

					strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
							strSTvalue[i], true);
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

			/* 4. Resource 'RS' is created under 'RT' */

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
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
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
			
			/*
			 * 5. User 'A' has update right on 'RS' and is assigned a role 'R'
			 * which has update right on all four status types.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7. User 'A' has 'Edit Status Change Notification Preferences'
			 * right
			 */

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6. User 'A' is provided with 'Email' and 'Pager' address.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);

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
			 * 8. Event Template 'ET' is created selecting 'ENST', 'EMST',
			 * 'ESST' and 'RT'
			 */
			
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

				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeVal = { strSTvalue[0], strSTvalue[1], strSTvalue[2] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
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

				strFuncResult=objEventSetup.navToEventManagementNew(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);

				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("--------------- test case " + gstrTCID
					+ " execution starts----------");

			/*
			 * 2 Login as user 'A' and navigate to 'Preferences>>Status Change
			 * Prefs' 'My Status Change Preferences' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_A,
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

			/*
			 * 3 Select 'Add' 'Find Resources' page is displayed.
			 */

			/* 4 Search for resource 'RS' Resource 'RS' is retrieved. */

			/*
			 * 5 Select the checkbox associated with 'RS' and select
			 * 'Notifications' 'Edit My Status Change Preferences' is displayed.
			 * Sub header is displayed as 'RT-RS' is displayed. Status types
			 * 'ENST', 'EMST' & 'ESST' are displayed under 'Uncategorized'.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=#r_name";
				String strText = strResrctTypName + "�" + strResource;
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Sub header is displayed as 'RT-RS'");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Sub header is NOT displayed as 'RT-RS'";
				log4j.info("Sub header is NOT displayed as 'RT-RS'");
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName, statrMultiTypeName,
						statrSaturatTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								selenium, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 For 'ENST', provide value 'A' for 'Above' and 'B' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0], strSTvalue[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strAbove, strBelow, strRSValue[0],
								strSTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 For 'EMST', select 'Email', 'Pager' and 'Web' for both 'S1' and
			 * 'S2' 'My Status Change Preferences' page is displayed. 'X' is
			 * displayed under 'Email', 'Pager' and 'Web' for both 'S1' and 'S2'
			 * values.
			 */

			// SST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(selenium,
								strResource, strRSValue[0], strSTvalue[1],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(selenium,
								strSaturAbove, strSaturBelow, strRSValue[0],
								strSTvalue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 For 'ESST', provide value 'X' for 'Above' and 'Y' for 'Below'.
			 * Select 'Email', 'Pager' and 'Web' for both the values. 'My Status
			 * Change Preferences' page is displayed. 'X' is displayed under
			 * 'Email', 'Pager' and 'Web' for both 'Above' and 'Below' values.
			 */
			// MST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[0], true,
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(selenium,
								strResource, strRSValue[0], strSTvalue[2],
								statrMultiTypeName, strStatusValue[1], true,
								true, true);
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
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName_A, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 9 Navigate to view 'V1' and select to update the status clicking
			 * the 'Key' symbol. 'Update Status' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			/*
			 * 10 Provide the following values: 1. NST: A value above 'A' 2.
			 * MST: Select status 'S2' 3. SST: A value below 'Y' User A receives
			 * the following: 1. 'Email', 'Pager' and 'Web' for 'NST' 2.
			 * 'Email', 'Pager' and 'Web' for 'MST' 3. 'Email', 'Pager' and
			 * 'Web' for 'SST'
			 */
				
			try {
				assertEquals("", strFuncResult);

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName + " status from 0 to 101.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);
			
				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

				
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
			
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrSaturatTypeName + " status from 0 to 429.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

		
				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrMultiTypeName + " status from -- to "+strStatusName2+".";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrMultiTypeName + " status from -- to "+strStatusName2+".";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium
						.getText("//span[text()='101']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurrDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time for NST: " + strUpdTime);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");

				strMsgBodyNST = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strUpdTime
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrNumTypeName
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBodyNST2 = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strAdUpdTimeShift
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrNumTypeName
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBodyNSTPager = statrNumTypeName + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " "
						+ strUpdTime + " \n" + "Region: " + strRegn + "";

				strMsgBodyNSTPager2 = statrNumTypeName + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate + " "
						+ strAdUpdTimeShift + " \n" + "Region: " + strRegn + "";

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium
						.getText("//span[text()='429']/following-sibling::span[1]");

				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdTime = strLastUpdArr[2];

				String strCurYear = dts.getTimeOfParticularTimeZone("CST",
						"yyyy");
				String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
						+ strCurYear;

				strCurrDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
						"MM/dd/yyyy");

				log4j.info("Expiration Time for NST: " + strUpdTime);

				String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
						"HH:mm");


				strMsgBodySST = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strUpdTime
						+ " "
						+ strResource
						+ " "
						+ "changed "
						+ statrSaturatTypeName
						+ " status from 0 "
						+ "to 429.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrSaturatTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
				strMsgBodySST2 = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+ " "
					+ strAdUpdTimeShift
					+ " "
					+ strResource
					+ " "
					+ "changed "
					+ statrSaturatTypeName
					+ " status from 0 "
					+ "to 429.\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrSaturatTypeName
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			
				
				strMsgBodySSTPager = statrSaturatTypeName + " from 0 to 429; "
				+ "Reasons: \nSummary at " + strCurrDate +" "+strUpdTime+ " \n"
				+ "Region: " + strRegn + "";
				
				strMsgBodySSTPager2 = statrSaturatTypeName + " from 0 to 429; "
				+ "Reasons: \nSummary at " + strCurrDate +" "+strAdUpdTimeShift+ " \n"
				+ "Region: " + strRegn + "";
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				
				String strUpdatedDate = selenium
				.getText("//span[text()='"+strStatusName2+"']/following-sibling::span[1]");

		strUpdatedDate = strUpdatedDate.substring(1, 13);

		String strLastUpdArr[] = strUpdatedDate.split(" ");
		strUpdTime = strLastUpdArr[2];

		String strCurYear = dts.getTimeOfParticularTimeZone("CST",
				"yyyy");
		String strCurDateMnth = strLastUpdArr[0] + strLastUpdArr[1]
				+ strCurYear;

		strCurrDate = dts.converDateFormat(strCurDateMnth, "ddMMMyyyy",
				"MM/dd/yyyy");

		log4j.info("Expiration Time for NST: " + strUpdTime);

		String strAdUpdTimeShift = dts.addTimetoExisting(strUpdTime, 1,
				"HH:mm");


				strMsgBodyMST = "Status Update for "
						+ strUsrFulName_A
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "+strUpdTime+" "
						+ strResource
						+ " "
						+ "changed "
						+ statrMultiTypeName
						+ " status from -- "
						+ "to "+strStatusName2+".\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName
						+ "s "
						+ "in the region report the following "
						+ statrMultiTypeName
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				
				strMsgBodyMST2 = "Status Update for "
					+ strUsrFulName_A
					+ ": "
					+ "\nOn "
					+ strCurrDate
					+  " "+strAdUpdTimeShift+" "
					+ strResource
					+ " "
					+ "changed "
					+ statrMultiTypeName
					+ " status from -- "
					+ "to "+strStatusName2+".\n\nComments: \n\nReasons: \n\nRegion: "
					+ ""
					+ strRegn
					+ "\n\nOther "
					+ strResrctTypName
					+ "s "
					+ "in the region report the following "
					+ statrMultiTypeName
					+ " status:\n\n\nPlease do not reply to this email message."
					+ " You must log into EMResource to take any action that may"
					+ " be required.";
			
				
				strMsgBodyMSTPager = statrMultiTypeName + " from -- to "+strStatusName2+"; "
				+ "Reasons: \nSummary at " + strCurrDate + " "+strUpdTime+ " \n"
				+ "Region: " + strRegn + "";
				
				

				strMsgBodyMSTPager2 = statrMultiTypeName + " from -- to "+strStatusName2+"; "
				+ "Reasons: \nSummary at " + strCurrDate + " "+strAdUpdTimeShift+ " \n"
				+ "Region: " + strRegn + "";
				
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);
		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			/*try {
				assertTrue(strFuncResult.equals(""));
				for (int i = 0; i < 2; i++) {

					strSubjName = "EMResource Expired Status: " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						String strMsg = objMail.seleniumGetText(selenium,
								"css=div.fixed.leftAlign", 160);

						if (strMsg.equals(strMsgBody2Expire)
								|| strMsg.equals(strMsgBody2ExpireAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Expire
									+ " is displayed for Pager Notification");
						} else if (strMsg.equals(strMsgBody2Shift)
								|| strMsg.equals(strMsgBody2ShiftAnother)) {
							intPagerRes++;
							log4j.info(strMsgBody2Shift
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

				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			for (int i = 0; i < 4; i++) {

				strSubjName = "EMResource Expired Status Notification: "
						+ strResource;
				strFuncResult = objMail.verifyEmail(selenium, strFrom, strTo,
						strSubjName);

				try {
					assertTrue(strFuncResult.equals(""));
					String strMsg = objMail.seleniumGetText(selenium,
							"css=div.fixed.leftAlign", 160);

					if (strMsg.equals(strMsgBody1Expire)
							|| strMsg.equals(strMsgBody1ExpireAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Expire
								+ " is displayed for Email Notification");
					} else if (strMsg.equals(strMsgBody1Shift)
							|| strMsg.equals(strMsgBody1ShiftAnother)) {
						intEMailRes++;
						log4j.info(strMsgBody1Shift
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
			}

			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();
			Thread.sleep(1000);

			selenium.selectWindow("");

			// check Email, pager notification
			if (intEMailRes == 4 && intPagerRes == 2) {
				gstrResult = "PASS";
			}*/

			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-102854";
			gstrTO = "Verify that user can receive the status change" +
					" notifications upon status change of a non-event" +
					" related status type.";
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
