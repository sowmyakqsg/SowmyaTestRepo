package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Arrays;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************************
' Description        :This class contains test cases from requirement			
' Requirement Group  :Creating & managing Events
' Requirement        :Create private event
' Date               : 17-Apr-2012
' Author             :QSG
'---------------------------------------------------------------------
' Modified Date                                           Modified By
' <Date>                           	                      <Name>
'**********************************************************************
*/
public class CreatePrivateEvent {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.CreatePrivateEvent");
	static{
		BasicConfigurator.configure();
	}
	
	String gstrTCID, gstrTO, gstrResult, gstrReason;	
	Selenium selenium,seleniumFirefox;	
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails;
	public static Properties browserProps = new Properties();
	
	private String browser="";
		
	private String json;
	public static long sysDateTime;	
	public static long gsysDateTime;
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId,StrSessionId1,StrSessionId2;
	public static String gstrTimeOut="";
	
	

	@Before
	public void setUp() throws Exception {
		
		
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
					
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");		
		
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {		         
	        
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.json, propEnvDetails.getProperty("urlEU"));
			
	       
		} else {
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));			
		
		}		
					
		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
	
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");
		
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();		
		
	}

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
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	
	/**
	'*************************************************************
	' Description: Verify that a user with 'View all resources in private event' right can view the event related status types of resources associated with private event.
	' Precondition: 
				1. Role based status types MST, NST, TST and SST are created.
				2. Event related role based status types ENST, EMST, ESST and ETST are created.
				3. All status types(role based and event related) are under status type section SEC1.
				4. Resource type RT is created selecting MST, NST, TST, SST, ENST, EMST, ESST and ETST status types.
				5. Resource RS is created under RT providing address.
				6. User A is created selecting;
				
				a. 'View Resource' right on RS.
				b. Role R1 to view and update status types NST, MST, TST, SST, ENST, EMST, ESST and ETST.
				c.'Maintain Event Templates' right
				d 'Maintain Events' right.
				e. 'User - Setup User Accounts' right
				
				7. Event Template ET is created selecting RT, NST, MST, TST, SST, ENST, EMST, ESST and ETST. 
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'************************************************************* 
*/
	
	@Test
	public void testBQS68347() throws Exception {
		try {
			gstrTCID = "68347";
			gstrTO = "Verify that a user with 'View all resources in private event' right can view the event related status types of resources associated with private event.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			ReadData objReadData = new ReadData();
			MobileView objMob = new MobileView();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			EventList objList = new EventList();
			CreateUsers objUser = new CreateUsers();
			ViewMap objMap = new ViewMap();
			String strTestData[] = new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			Views objViews = new Views();
			CreateUsers objCreateUsers = new CreateUsers();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Roles objRole = new Roles();
			String strEventName = "AutoEv" + System.currentTimeMillis();
			String strEveTempName = "AutoET_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strInfo = "auto event";

			String strIconSrc = objReadData.readInfoExcel("Event Temp Data", 2,
					6, strFILE_PATH);
			String strResType = "AutoRt_" + strTimeText;

			String strMSTValue = "Multi";
			String strNSTValue = "Number";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strEveTemp = "AutoET_" + strTimeText;
			String strTempDef = objrdExcel.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objrdExcel.readInfoExcel("Event Temp Data", 2,
					4, strFILE_PATH);
			String strAsscIcon = objrdExcel.readInfoExcel("Event Temp Data", 2,
					5, strFILE_PATH);

			String strNumStat = "Aa1_" + strTimeText;
			String strMulStat = "Aa2_" + strTimeText;
			String strTextStat = "Aa3_" + strTimeText;
			String strSatuStat = "Aa4_" + strTimeText;

			String strENumStat = "A11_" + strTimeText;
			String strEMulStat = "A12_" + strTimeText;
			String strETextStat = "A13_" + strTimeText;
			String strESatuStat = "A14_" + strTimeText;
			// String strEveSection="AutoEveSec1";
			String[] strEveStatType = { strEMulStat, strENumStat, strESatuStat,
					strETextStat };
			String[] strRoleStatType = { strMulStat, strNumStat, strSatuStat,
					strTextStat };
			String[] strStatTypeArr = { strEMulStat, strESatuStat, strMulStat,
					strETextStat, strENumStat, strNumStat, strSatuStat,
					strTextStat };
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn = "Auto";
			String strFailMsg = "";
			String strRSTvalue[] = new String[4];
			String strERSTvalue[] = new String[4];
			String strStatValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strRoleValue = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strSection = "AB_" + strTimeText;
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strEMulStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(selenium, strEMulStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strEMulStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strENumStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strENumStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strENumStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTSTValue, strETextStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strETextStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strETextStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSSTValue, strESatuStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strESatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strESatuStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (int intST = 0; intST < strRSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium,
							strRSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}
			for (int intST = 0; intST < strERSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium,
							strERSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(selenium,
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
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				strFailMsg = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strRSTvalue, true,
						strRSTvalue, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String[][] strViewRts = { { strRSTvalue[0], "true" },
						{ strRSTvalue[1], "true" }, { strRSTvalue[2], "true" },
						{ strRSTvalue[3], "true" },
						{ strERSTvalue[0], "true" },
						{ strERSTvalue[1], "true" },
						{ strERSTvalue[2], "true" },
						{ strERSTvalue[3], "true" } };
				String[][] strUpdRts = { { strRSTvalue[0], "true" },
						{ strRSTvalue[1], "true" }, { strRSTvalue[2], "true" },
						{ strRSTvalue[3], "true" },
						{ strERSTvalue[0], "true" },
						{ strERSTvalue[1], "true" },
						{ strERSTvalue[2], "true" },
						{ strERSTvalue[3], "true" } };

				strFailMsg = objRole.slectAndDeselectSTInCreateRole(selenium,
						false, false, strViewRts, strUpdRts, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
				strFailMsg = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium,
						strResource, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUser(selenium, strUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				// navigate to Event Template
				strFailMsg = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strArRoleRights[] = { strRSTvalue[0], strRSTvalue[1],
					strRSTvalue[2], strRSTvalue[3], strERSTvalue[0],
					strERSTvalue[1], strERSTvalue[2], strERSTvalue[3] };

			try {
				assertTrue(strFailMsg.equals(""));

				// fill the required fields in Create Event Template and save
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strArRoleRights, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
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

				strFailMsg = objLogin.logout(selenium);
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

			/*
			 * Step 2: Login as user A, navigate to Events >> Management. <->
			 * 'Event Management' screen is displayed.
			 */

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 3: Click on 'Create New Event' button, select ET, provide
			 * mandatory data (EVE as event name), select Private check box,
			 * select resource RS and click on 'Save'. <-> EVE is listed in the
			 * 'Event management' screen.
			 */
			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.createEvent(selenium, strEveTempName,
						strResource, strEventName, strInfo, false);
				log4j.info("Event Name: " + strEventName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				// select private? check box
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Private"));

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEventName + "']"));
					log4j.info("Event '" + strEventName
							+ "' is listed on 'Event Management' screen.");
					/*
					 * Step 4: Navigate to Setup >> Users <-> 'User List' screen
					 * is displayed.
					 */
					strFailMsg = objUser.navUserListPge(selenium);
					try {
						assertTrue(strFailMsg.equals(""));
						/*
						 * Step 5: Click on 'Edit' link next to User A, navigate
						 * to 'Additional User Rights' section, select 'View All
						 * Resources in Private Events' check box and click on
						 * 'Save'. <-> 'User List' screen is displayed.
						 */
						// Click on Edit user link
						selenium
								.click("//table[@id='tblUsers']/tbody/tr/td[text()='"
										+ strUserName
										+ "']/parent::tr/td[1]/a[text()='Edit']");
						selenium.waitForPageToLoad(gstrTimeOut);

						strFailMsg = objUser.advancedOptns(selenium,
								"//input[@value='55']", true);

					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objUser.savVrfyUser(selenium, strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 6: Logout and login as user A, click on event banner
					 * EVE. <-> RS is displayed under RT along with NST, MST,
					 * TST, SST, ENST, EMST, ESST and ETST status types.
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.login(selenium, strUserName,
								strConfirmPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objList.checkInEventBanner(selenium,
								strEventName, strResType, strResource,
								strStatTypeArr);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 7: Navigate to View>>Map and click on the resource
					 * icon of RS <-> NST, MST, TST, SST, ENST, EMST, ESST and
					 * ETST status types are displayed in the resource RS pop up
					 * window
					 */
					try {
						assertTrue(strFailMsg.equals(""));

						strFailMsg = objMap.verifyStatTypesInResourcePopup(
								selenium, strResource, strEveStatType,
								strRoleStatType, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 8: Click on 'View Info' link on the resource pop up
					 * window of RS <-> NST, MST, TST, SST, ENST, EMST, ESST and
					 * ETST status types are displayed under status type section
					 * SEC1 on 'View Resource Detail' screen.
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMap.verifyStatTypeInViewResDetail(
								selenium, strSection, strEveStatType,
								strRoleStatType, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 9: Click on 'Mobile View' link on footer of the
					 * application. <-> 'Main Menu' screen is displayed Step 10:
					 * Click on Events >> EVE >> Resources. <-> 'Event
					 * Resources' screen is displayed. <-> Resource RS is
					 * displayed.
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMob.navToEventDetailInMob(selenium,
								strEventName, strIconSrc, strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 11: Click on Resource RS. <-> 'Resource Detail'
					 * screen is displayed.
					 * 
					 * <-> NST, MST, TST, SST, ENST, EMST, ESST and ETST status
					 * types are displayed under section SEC1.
					 */

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMob.checkResouceDetail(selenium,
								strResource, strSection, strStatTypeArr);
					} catch (AssertionError ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						selenium.close();
						selenium.selectWindow("");
						gstrResult = "PASS";

						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strConfirmPwd;
						strTestData[3] = strEveTempName;
						strTestData[4] = strEventName;
						strTestData[5] = "Resource Name: " + strResource
								+ ", Section Name: " + strSection
								+ ", Status Types: "
								+ Arrays.toString(strStatTypeArr)
								+ ", Step:Check in mobile";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Events");

					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;

					}
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEventName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEventName
							+ "' is NOT listed on 'Event Management' screen.";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "68347";
			gstrTO = "Verify that a user with 'View all resources in private event' right can view the event related status types of resources associated with private event.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}

	}
	
	/**
	'*************************************************************
	' Description: Verify that a user with 'Update status' right on the resource can view the event related status types of the resource associated with private event.
	' Precondition: 
			1. Role based status types MST, NST, TST and SST are created.
			2. Event related role based status types ENST, EMST, ESST and ETST are created.
			3. All status types(role based and event related) are under status type section SEC1.
			4. Resource type RT is created selecting MST, NST, TST, SST, ENST, EMST, ESST and ETST status types.
			5. Resource RS is created under RT providing address.
			6. User A is created selecting;
			
			a. View Resource right on RS.
			b. Role R1 to view and update status types NST, MST, TST, SST, ENST, EMST, ESST and ETST.
			c.'Maintain Event Templates' right
			d 'Maintain Events' right.
			e. 'User - Setup User Accounts' right
			
			7. Event Template ET is created selecting RT, NST, MST, TST, SST, ENST, EMST, ESST and ETST.
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'************************************************************* 
*/
	
	@Test
	public void testBQS68367() throws Exception {
		try {
			gstrTCID = "68367";
			gstrTO = "Verify that a user with 'Update status' right on the resource can view the event related status types of the resource associated with private event.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			ReadData objReadData = new ReadData();
			MobileView objMob = new MobileView();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve = new EventSetup();
			EventList objList = new EventList();
			CreateUsers objUser = new CreateUsers();
			ViewMap objMap = new ViewMap();
			String strTestData[] = new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST = new StatusTypes();
			Views objViews = new Views();
			CreateUsers objCreateUsers = new CreateUsers();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Roles objRole = new Roles();
			String strEventName = "AutoEv" + System.currentTimeMillis();
			String strEveTempName = "AutoET_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strInfo = "auto event";

			String strIconSrc = objReadData.readInfoExcel("Event Temp Data", 2,
					6, strFILE_PATH);
			String strResType = "AutoRt_" + strTimeText;

			String strMSTValue = "Multi";
			String strNSTValue = "Number";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";

			String strEveTemp = "AutoET_" + strTimeText;
			String strTempDef = objrdExcel.readInfoExcel("Event Temp Data", 2,
					3, strFILE_PATH);
			String strEveColor = objrdExcel.readInfoExcel("Event Temp Data", 2,
					4, strFILE_PATH);
			String strAsscIcon = objrdExcel.readInfoExcel("Event Temp Data", 2,
					5, strFILE_PATH);

			String strNumStat = "Aa1_" + strTimeText;
			String strMulStat = "Aa2_" + strTimeText;
			String strTextStat = "Aa3_" + strTimeText;
			String strSatuStat = "Aa4_" + strTimeText;

			String strENumStat = "A11_" + strTimeText;
			String strEMulStat = "A12_" + strTimeText;
			String strETextStat = "A13_" + strTimeText;
			String strESatuStat = "A14_" + strTimeText;
			// String strEveSection="AutoEveSec1";
			String[] strEveStatType = { strEMulStat, strENumStat, strESatuStat,
					strETextStat };
			String[] strRoleStatType = { strMulStat, strNumStat, strSatuStat,
					strTextStat };
			String[] strStatTypeArr = { strEMulStat, strESatuStat, strMulStat,
					strETextStat, strENumStat, strNumStat, strSatuStat,
					strTextStat };
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword = objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn = "Auto";
			String strFailMsg = "";
			String strRSTvalue[] = new String[4];
			String strERSTvalue[] = new String[4];
			String strStatValue = "";

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue[] = new String[1];
			String strRSValue[] = new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd = objrdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strRoleValue = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strSection = "AB_" + strTimeText;
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
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
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strMSTValue, strEMulStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(selenium, strEMulStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strEMulStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[0] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strNSTValue, strENumStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strENumStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strENumStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[1] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strTSTValue, strETextStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strETextStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strETextStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[2] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium,
						strSSTValue, strESatuStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strESatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						strESatuStat);
				if (strStatValue.compareTo("") != 0) {
					strFailMsg = "";
					strERSTvalue[3] = strStatValue;
				} else {
					strFailMsg = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strRSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			for (int intST = 0; intST < strRSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium,
							strRSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}
			for (int intST = 0; intST < strERSTvalue.length; intST++) {
				try {
					assertEquals("", strFailMsg);

					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium,
							strERSTvalue[intST], true);

				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(selenium,
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
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

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
				strFailMsg = objRole.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objRole.CreateRoleWithAllFields(selenium,
						strRoleName, strRoleRights, strRSTvalue, true,
						strRSTvalue, true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String[][] strViewRts = { { strRSTvalue[0], "true" },
						{ strRSTvalue[1], "true" }, { strRSTvalue[2], "true" },
						{ strRSTvalue[3], "true" },
						{ strERSTvalue[0], "true" },
						{ strERSTvalue[1], "true" },
						{ strERSTvalue[2], "true" },
						{ strERSTvalue[3], "true" } };
				String[][] strUpdRts = { { strRSTvalue[0], "true" },
						{ strRSTvalue[1], "true" }, { strRSTvalue[2], "true" },
						{ strRSTvalue[3], "true" },
						{ strERSTvalue[0], "true" },
						{ strERSTvalue[1], "true" },
						{ strERSTvalue[2], "true" },
						{ strERSTvalue[3], "true" } };

				strFailMsg = objRole.slectAndDeselectSTInCreateRole(selenium,
						false, false, strViewRts, strUpdRts, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium,
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
				strFailMsg = objCreateUsers.navUserListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium,
						strResource, false, false, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.savVrfyUser(selenium, strUserName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));
				// navigate to Event Template
				strFailMsg = objEve.createEventTemplate(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strArRoleRights[] = { strRSTvalue[0], strRSTvalue[1],
					strRSTvalue[2], strRSTvalue[3], strERSTvalue[0],
					strERSTvalue[1], strERSTvalue[2], strERSTvalue[3] };

			try {
				assertTrue(strFailMsg.equals(""));

				// fill the required fields in Create Event Template and save
				strFailMsg = objEve.fillMandfieldsAndSaveEveTemp(selenium,
						strEveTemp, strTempDef, strEveColor, strAsscIcon,
						strIconSrc, "", "", "", "", true, strRTValue,
						strArRoleRights, true, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
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

				strFailMsg = objLogin.logout(selenium);
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

			 
			/* * * Step 2: Login as user A, navigate to Events >> Management. <->
			 * 'Event Management' screen is displayed.
			 */

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg = "";
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

				 
			/* * * Step 3: Click on 'Create New Event' button, select ET, provide
			 * mandatory data (EVE as event name), select Private check box,
			 * select resource RS and click on 'Save'. <-> EVE is listed in the
			 * 'Event management' screen.
			 */

			try {
				assertTrue(strFailMsg.equals(""));

				strFailMsg = objEve.createEvent(selenium, strEveTempName,
						strResource, strEventName, strInfo, false);
				log4j.info("Event Name: " + strEventName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));

				// select private? check box
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Private"));

				// click on save
				selenium.click(propElementDetails
						.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"
									+ strEventName + "']"));
					log4j.info("Event '" + strEventName
							+ "' is listed on 'Event Management' screen.");

					 
					/* * * Step 4: Navigate to Setup >> Users <-> 'User List'
					 * screen is displayed.
					 
*/
					strFailMsg = objUser.navUserListPge(selenium);
					try {
						assertTrue(strFailMsg.equals(""));

						 
						/* * * Step 5: Click on 'Edit' link next to User A,
						 * navigate to 'Resource Rights' section, select 'Update
						 * Status' right check box next to resource RS and click
						 * on 'Save'. <-> 'User List' screen is displayed
						 */

						// Click on Edit user link
						selenium
								.click("//table[@id='tblUsers']/tbody/tr/td[text()='"
										+ strUserName
										+ "']/parent::tr/td[1]/a[text()='Edit']");
						selenium.waitForPageToLoad(gstrTimeOut);

						strFailMsg = objUser.selectResourceRights(selenium,
								strResource, false, true, false, true);

					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objUser.savVrfyUser(selenium, strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					 
					/* * * Step 6: Logout and login as user A, click on event
					 * banner EVE. <-> RS is displayed under RT along with NST,
					 * MST, TST, SST, ENST, EMST, ESST and ETST status types.
					*/ 

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.login(selenium, strUserName,
								strConfirmPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objList.checkInEventBanner(selenium,
								strEventName, strResType, strResource,
								strStatTypeArr);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					 
			/*		 * * Step 7: Navigate to View>>Map and click on the resource
					 * icon of RS <-> NST, MST, TST, SST, ENST, EMST, ESST and
					 * ETST status types are displayed in the resource RS pop up
					 * window
					 */
					try {
						assertTrue(strFailMsg.equals(""));

						strFailMsg = objMap.verifyStatTypesInResourcePopup(
								selenium, strResource, strEveStatType,
								strRoleStatType, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					/* 
					 * * Step 8: Click on 'View Info' link on the resource pop
					 * up window of RS <-> NST, MST, TST, SST, ENST, EMST, ESST
					 * and ETST status types are displayed under status type
					 * section SEC1 on 'View Resource Detail' screen.
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMap.verifyStatTypeInViewResDetail(
								selenium, strSection, strEveStatType,
								strRoleStatType, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

				/*	 
					 * * Step 9: Click on 'Mobile View' link on footer of the
					 * application. <-> 'Main Menu' screen is displayed. Step
					 * 10: Click on Events >> EVE >> Resources. <-> 'Event
					 * Resources' screen is displayed.
					 * 
					 * <-> Resource RS is displayed.*/
					 
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMob.navToEventDetailInMob(selenium,
								strEventName, strIconSrc, strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}

					 
					/* * * Step 11: Click on Resource RS. <-> 'Resource Detail'
					 * screen is displayed.
					 * 
					 * <-> NST, MST, TST, SST, ENST, EMST, ESST and ETST status
					 * types are displayed under section SEC1.
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objMob.checkResouceDetail(selenium,
								strResource, strSection, strStatTypeArr);
					} catch (AssertionError ae) {
						gstrReason = strFailMsg;
					}

					try {
						assertTrue(strFailMsg.equals(""));
						selenium.close();
						selenium.selectWindow("");
						gstrResult = "PASS";

						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strConfirmPwd;
						strTestData[3] = strEveTempName;
						strTestData[4] = strEventName;
						strTestData[5] = "Resource Name: " + strResource
								+ ", Section Name: " + strSection
								+ ", Status Types: "
								+ Arrays.toString(strStatTypeArr)
								+ ", Step:Check in mobile";

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Events");

					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;

					}
				} catch (AssertionError ae) {
					log4j.info("Event '" + strEventName
							+ "' is NOT listed on 'Event Management' screen.");
					gstrReason = "Event '" + strEventName
							+ "' is NOT listed on 'Event Management' screen.";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "68367";
			gstrTO = "Verify that a user with 'Update status' right on the resource can view the event related status types of the resource associated with private event.";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}

	}
	/**
	'*************************************************************
	' Description: Verify that a user with 'Run report' right on the resource can view the event related status types of the resource associated with private event.
	' Precondition: 
			1. Role based status types MST, NST, TST and SST are created.
			2. Event related role based status types ENST, EMST, ESST and ETST are created.
			3. All status types(role based and event related) are under status type section SEC1.
			4. Resource type RT is created selecting MST, NST, TST, SST, ENST, EMST, ESST and ETST status types.
			5. Resource RS is created under RT providing address.
			6. User A is created selecting;
			
			a. View Resource right on RS.
			b. Role R1 to view and update status types NST, MST, TST, SST, ENST, EMST, ESST and ETST.
			c.'Maintain Event Templates' right
			d 'Maintain Events' right.
			e. 'User - Setup User Accounts' right
			
			7. Event Template ET is created selecting RT, NST, MST, TST, SST, ENST, EMST, ESST and ETST.
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'************************************************************* 
*/
	@Test
	public void testBQS68368()throws Exception{
		try{
			gstrTCID = "68368";			
			gstrTO = "Verify that a user with 'Run report' right on the resource can view the event related status types of the resource associated with private event.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			ReadData objReadData = new ReadData (); 
			MobileView objMob=new MobileView();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
			EventList objList=new EventList();
			CreateUsers objUser=new CreateUsers();
			ViewMap objMap=new ViewMap();
			String strTestData[]=new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST=new StatusTypes();
			Views objViews=new Views();
			CreateUsers objCreateUsers=new CreateUsers();
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Roles objRole=new Roles();
			String strEventName="AutoEv"+System.currentTimeMillis();
			String strEveTempName="AutoET_"+strTimeText;
			String strResource="AutoRs_"+strTimeText;
			String strInfo="auto event";	
			
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
			String strResType="AutoRt_"+strTimeText;
			
			String strMSTValue="Multi";
			String strNSTValue="Number";
			String strTSTValue="Text";
			String strSSTValue="Saturation Score";
						
			String strEveTemp="AutoET_"+strTimeText;
			String strTempDef=objrdExcel.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objrdExcel.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objrdExcel.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
					
			String strNumStat="Aa1_"+strTimeText;
			String strMulStat="Aa2_"+strTimeText;
			String strTextStat="Aa3_"+strTimeText;
			String strSatuStat="Aa4_"+strTimeText;
			
			String strENumStat="A11_"+strTimeText;
			String strEMulStat="A12_"+strTimeText;
			String strETextStat="A13_"+strTimeText;
			String strESatuStat="A14_"+strTimeText;
			//String strEveSection="AutoEveSec1";
			String[] strEveStatType={strEMulStat,strENumStat,strESatuStat,strETextStat};
			String[] strRoleStatType={strMulStat,strNumStat,strSatuStat,strTextStat};
			String []strStatTypeArr={strEMulStat,strESatuStat,strMulStat,strETextStat,strENumStat,strNumStat,strSatuStat,strTextStat};
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn="Auto";
			String strFailMsg ="";
			String strRSTvalue[]=new String[4];
			String strERSTvalue[]=new String[4];
			String strStatValue="";
		
			String strResrctTypName="AutoRt_"+strTimeText;
			String strRTValue[]=new String[1];
			String strRSValue[]=new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal="";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			String strRoleValue="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strSection="AB_"+strTimeText;
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strMulStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strNumStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strTextStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strSatuStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strEMulStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(selenium, strEMulStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strEMulStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strENumStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strENumStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strENumStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strETextStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strETextStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strETextStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strESatuStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strESatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strESatuStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium, strResrctTypName, "css=input[name='statusTypeID'][value='"+strRSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			for(int intST=0;intST<strRSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium, strRSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
			for(int intST=0;intST<strERSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium, strERSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(selenium, strResrctTypName);
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
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(selenium, strResource, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
									
			try {
				assertEquals("", strFailMsg);
				
				strFailMsg = objRole.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strRSTvalue, true, strRSTvalue, true, false);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
					String[][] strViewRts={{strRSTvalue[0],"true"},
											{strRSTvalue[1],"true"},
											{strRSTvalue[2],"true"},
											{strRSTvalue[3],"true"},
										{strERSTvalue[0],"true"},
										{strERSTvalue[1],"true"},
										{strERSTvalue[2],"true"},
										{strERSTvalue[3],"true"}};
				String[][] strUpdRts={{strRSTvalue[0],"true"},
						{strRSTvalue[1],"true"},
						{strRSTvalue[2],"true"},
						{strRSTvalue[3],"true"},
					{strERSTvalue[0],"true"},
					{strERSTvalue[1],"true"},
					{strERSTvalue[2],"true"},
					{strERSTvalue[3],"true"}};
				
				strFailMsg = objRole.slectAndDeselectSTInCreateRole(selenium, false, false, strViewRts, strUpdRts, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUser(selenium, strUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(selenium);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strArRoleRights[]={strRSTvalue[0],strRSTvalue[1],strRSTvalue[2],strRSTvalue[3],
					strERSTvalue[0],strERSTvalue[1],strERSTvalue[2],strERSTvalue[3]};
			
			try {
				assertTrue(strFailMsg.equals(""));			
			
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strRTValue,strArRoleRights,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
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
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox, strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.logout(selenium);
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
			
			/* * Step 2: 	Login as user A, navigate to Events >> Management. <->	'Event Management' screen is displayed.*/
			 
					
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/* * Step 3: 	Click on 'Create New Event' button, select ET, provide mandatory data (EVE as event name), select Private check box, select resource RS and click on 'Save'. <-> EVE is listed in the 'Event management' screen.*/
			 
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(selenium, strEveTempName, strResource, strEventName, strInfo,false);
				log4j.info("Event Name: "+strEventName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				//select private? check box
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Private"));
				
				//click on save
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"+strEventName+"']"));
					log4j.info("Event '"+strEventName+"' is listed on 'Event Management' screen.");
					
					/* * Step 4: Navigate to Setup >> Users <-> 'User List' screen is displayed*/
					 
					strFailMsg=objUser.navUserListPge(selenium);
					try {
						assertTrue(strFailMsg.equals(""));
						
						/* * Step 5: Click on 'Edit' link next to User A, navigate to 'Resource Rights' section, select 'Run Report' right check box next to resource RS and click on 'Save'. <->	'User List' screen is displayed*/
						 
						//Click on Edit user link
						selenium.click("//table[@id='tblUsers']/tbody/tr/td[text()='"+strUserName+"']/parent::tr/td[1]/a[text()='Edit']");
						selenium.waitForPageToLoad(gstrTimeOut);
						
						strFailMsg=objUser.selectResourceRights(selenium, strResource, false, false, true, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objUser.savVrfyUser(selenium, strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					 /** Step 6: 	Logout and login as user A, click on event banner EVE. 	<-> RS is displayed under RT along with NST, MST, TST, SST, ENST, EMST, ESST and ETST status types.*/ 
					 
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.login(selenium,  strUserName,
								strConfirmPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objList.checkInEventBanner(selenium, strEventName, strResType, strResource, strStatTypeArr);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					/* * Step 7: Navigate to View>>Map and click on the resource icon of RS. 	<->	NST, MST, TST, SST, ENST, EMST, ESST and ETST status types are displayed in the resource RS pop up window.*/
					 
					try {
						assertTrue(strFailMsg.equals(""));		
						
						strFailMsg=objMap.verifyStatTypesInResourcePopup(selenium, strResource,strEveStatType,strRoleStatType,true,true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					/* * Step 8: Click on 'View Info' link on the resource pop up window of RS <-> NST, MST, TST, SST, ENST, EMST, ESST and ETST status types are displayed under status type section SEC1 on 'View Resource Detail' screen
					 */
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objMap.verifyStatTypeInViewResDetail(selenium, strSection,strEveStatType,strRoleStatType,true,true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					/* * Step 9: Click on 'Mobile View' link on footer of the application. <-> 'Main Menu' screen is displayed.
					 * Step 10: Click on Events >> EVE >> Resources. <-> 'Event Resources' screen is displayed.

						<-> Resource RS is displayed. */
					 
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objMob.navToEventDetailInMob(selenium, strEventName, strIconSrc, strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					
					/* * Step 11: Click on Resource RS. <-> 'Resource Detail' screen is displayed.

						<-> NST, MST, TST, SST, ENST, EMST, ESST and ETST status types are displayed under section SEC1. */
					 
					try{
						assertTrue(strFailMsg.equals(""));	
						strFailMsg=objMob.checkResouceDetail(selenium,strResource, strSection, strStatTypeArr);
					}catch(AssertionError ae){
						gstrReason = strFailMsg;
					}		
										
					
					try {
						assertTrue(strFailMsg.equals(""));					
						selenium.close();
						selenium.selectWindow("");				
						gstrResult="PASS";
						
						//Write result data
						strTestData[0]= propEnvDetails.getProperty("Build");
						strTestData[1]=gstrTCID;
						strTestData[2]=strUserName+"/"+strConfirmPwd;
						strTestData[3]=strEveTempName;
						strTestData[4]=strEventName;
						strTestData[5]="Resource Name: "+strResource+", Section Name: "+strSection+", Status Types: "+Arrays.toString(strStatTypeArr)+", Step:Check in mobile";
						
						String strWriteFilePath=pathProps.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
						
					}
				}catch(AssertionError ae){
					log4j.info("Event '"+strEventName+"' is NOT listed on 'Event Management' screen.");
					gstrReason="Event '"+strEventName+"' is NOT listed on 'Event Management' screen.";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68368";
			gstrTO = "Verify that a user with 'Run report' right on the resource can view the event related status types of the resource associated with private event.";
			gstrResult = "FAIL";
			excReason = null;
	
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}			
	
	}
	/**
	'*************************************************************
	' Description: Verify that a user with 'Associated with' right on the resource cannot view the event related status types of the resource associated with private event. 
	' Precondition: 
			1. Role based status types MST, NST, TST and SST are created.
			2. Event related role based status types ENST, EMST, ESST and ETST are created.
			3. All status types(role based and event related) are under status type section SEC1.
			4. Resource type RT is created selecting MST, NST, TST, SST, ENST, EMST, ESST and ETST status types.
			5. Resource RS is created under RT providing address.
			6. User A is created selecting;
			
			a. View Resource right on RS.
			b. Role R1 to view and update status types NST, MST, TST, SST, ENST, EMST, ESST and ETST.
			c.'Maintain Event Templates' right
			d 'Maintain Events' right.
			e. 'User - Setup User Accounts' right
			
			7. Event Template ET is created selecting RT, NST, MST, TST, SST, ENST, EMST, ESST and ETST.
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'************************************************************* 
*/
	@Test
	public void testBQS68369()throws Exception{
		try{
			gstrTCID = "68369";			
			gstrTO = "Verify that a user with 'Associated with' right on the resource cannot view the event related status types of the resource associated with private event. ";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			int intResCnt=0;
			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			ReadData objReadData = new ReadData (); 
			MobileView objMob=new MobileView();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
		
			CreateUsers objUser=new CreateUsers();
			ViewMap objMap=new ViewMap();
			String strTestData[]=new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST=new StatusTypes();
			Views objViews=new Views();
			CreateUsers objCreateUsers=new CreateUsers();
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Roles objRole=new Roles();
			String strEventName="AutoEv"+System.currentTimeMillis();
			String strEveTempName="AutoET_"+strTimeText;
			String strResource="AutoRs_"+strTimeText;
			String strInfo="auto event";	
			
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
					
			String strMSTValue="Multi";
			String strNSTValue="Number";
			String strTSTValue="Text";
			String strSSTValue="Saturation Score";
						
			String strEveTemp="AutoET_"+strTimeText;
			String strTempDef=objrdExcel.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objrdExcel.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objrdExcel.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
					
			String strNumStat="Aa1_"+strTimeText;
			String strMulStat="Aa2_"+strTimeText;
			String strTextStat="Aa3_"+strTimeText;
			String strSatuStat="Aa4_"+strTimeText;
			
			String strENumStat="A11_"+strTimeText;
			String strEMulStat="A12_"+strTimeText;
			String strETextStat="A13_"+strTimeText;
			String strESatuStat="A14_"+strTimeText;
			//String strEveSection="AutoEveSec1";
			String[] strEveStatType={strEMulStat,strENumStat,strESatuStat,strETextStat};
			String[] strRoleStatType={strMulStat,strNumStat,strSatuStat,strTextStat};
			String []strStatTypeArr={strEMulStat,strESatuStat,strMulStat,strETextStat,strENumStat,strNumStat,strSatuStat,strTextStat};
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn="Auto";
			String strFailMsg ="";
			String strRSTvalue[]=new String[4];
			String strERSTvalue[]=new String[4];
			String strStatValue="";
		
			String strResrctTypName="AutoRt_"+strTimeText;
			String strRTValue[]=new String[1];
			String strRSValue[]=new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal="";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			String strRoleValue="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strSection="AB_"+strTimeText;
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strMulStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strNumStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strTextStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strSatuStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strEMulStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(selenium, strEMulStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strEMulStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strENumStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strENumStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strENumStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strETextStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strETextStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strETextStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strESatuStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strESatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strESatuStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium, strResrctTypName, "css=input[name='statusTypeID'][value='"+strRSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			for(int intST=0;intST<strRSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium, strRSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
			for(int intST=0;intST<strERSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium, strERSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(selenium, strResrctTypName);
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
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(selenium, strResource, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
									
			try {
				assertEquals("", strFailMsg);
				
				strFailMsg = objRole.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strRSTvalue, true, strRSTvalue, true, false);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
					String[][] strViewRts={{strRSTvalue[0],"true"},
											{strRSTvalue[1],"true"},
											{strRSTvalue[2],"true"},
											{strRSTvalue[3],"true"},
										{strERSTvalue[0],"true"},
										{strERSTvalue[1],"true"},
										{strERSTvalue[2],"true"},
										{strERSTvalue[3],"true"}};
				String[][] strUpdRts={{strRSTvalue[0],"true"},
						{strRSTvalue[1],"true"},
						{strRSTvalue[2],"true"},
						{strRSTvalue[3],"true"},
					{strERSTvalue[0],"true"},
					{strERSTvalue[1],"true"},
					{strERSTvalue[2],"true"},
					{strERSTvalue[3],"true"}};
				
				strFailMsg = objRole.slectAndDeselectSTInCreateRole(selenium, false, false, strViewRts, strUpdRts, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUser(selenium, strUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(selenium);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strArRoleRights[]={strRSTvalue[0],strRSTvalue[1],strRSTvalue[2],strRSTvalue[3],
					strERSTvalue[0],strERSTvalue[1],strERSTvalue[2],strERSTvalue[3]};
			
			try {
				assertTrue(strFailMsg.equals(""));			
			
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strRTValue,strArRoleRights,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
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
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox, strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.logout(selenium);
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
			/*
			 * Step 2: 	Login as user A, navigate to Events >> Management. 	<->	'Event Management' screen is displayed.
			 */
					
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 3: Click on 'Create New Event' button, select ET, provide mandatory data (EVE as event name), select Private check box, select resource RS and click on 'Save'. <->	EVE is listed in the 'Event management' screen
			 */
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(selenium, strEveTempName, strResource, strEventName, strInfo,false);
				log4j.info("Event Name: "+strEventName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				//select private? check box
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Private"));
				
				//click on save
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"+strEventName+"']"));
					log4j.info("Event '"+strEventName+"' is listed on 'Event Management' screen.");
					/*
					 * Step 4: Navigate to Setup >> Users <-> 'User List' screen is displayed. 
					 */
					strFailMsg=objUser.navUserListPge(selenium);
					try {
						assertTrue(strFailMsg.equals(""));
						/*
						 * Step 5: Click on 'Edit' link next to User A, navigate to 'Resource Rights' section, select 'Associated With' right check box next to resource RS and click on 'Save'. <-> 'User List' screen is displayed. 
						 */
						//Click on Edit user link
						selenium.click("//table[@id='tblUsers']/tbody/tr/td[text()='"+strUserName+"']/parent::tr/td[1]/a[text()='Edit']");
						selenium.waitForPageToLoad(gstrTimeOut);
						
						strFailMsg=objUser.selectResourceRights(selenium, strResource, true, false, false, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objUser.savVrfyUser(selenium, strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 6: Logout and login as user A, click on event banner EVE. <-> Appropriate message 'You do not have viewing rights to any resources participating in this event.' is displayed. 
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.login(selenium, strUserName,
								strConfirmPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						selenium.selectFrame("Data");
						//click on Event banner
						selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEventName+"']");
						selenium.waitForPageToLoad(gstrTimeOut);
						try{
							assertEquals("You do not have viewing rights to any resources participating in this event.", selenium.getText("css=#viewContainer"));
							log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is displayed.");
							intResCnt++;
						} catch (AssertionError Ae) {
							log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.");
							gstrReason="Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.";
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 7: Navigate to View>>Map and click on the resource icon of RS. 	<->	NST, MST, TST and SST status types are displayed in the resource RS pop up window.

						<-> ENST, EMST, ESST and ETST status types are not displayed in the resource RS pop up window. 
					 */
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objMap.verifyStatTypesInResourcePopup(selenium, strResource,strEveStatType,strRoleStatType,false,true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					/*
					 * Step 8: Click on 'View Info' link on the resource pop up window of RS <-> NST, MST, TST and SST status types are displayed under the status type section SEC1 on 'View Resource Detail' screen.

						<-> ENST, EMST, ESST and ETST status types are not displayed under the status type section SEC1 on 'View Resource Detail' screen. 
					 */
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objMap.verifyStatTypeInViewResDetail(selenium, strSection,strEveStatType,strRoleStatType,false,true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						/*
						 * Step 9: Click on 'Mobile View' link on footer of the application. <-> 'Main Menu' screen is displayed.
						 * Step 10: Click on Events >> EVE >> Resources. <-> 'Event Resources' screen is displayed.

							<-> Appropriate message 'You do not have viewing rights to any resources participating in this event or the event does not involve resources.' is displayed. 
						 */
						strFailMsg=objMob.navToEventDetailInMob(selenium, strEventName, strIconSrc, strResource);
						if(strFailMsg.compareToIgnoreCase(strResource+" is NOT listed in the 'Event Resources' screen.")==0){
							try{
								assertTrue(selenium.isTextPresent("You do not have viewing rights to any resources participating in this event or the event does not involve resources."));
								log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is displayed.");
								intResCnt++;
							} catch (AssertionError Ae) {
								log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.");
								gstrReason=gstrReason+" Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.";
							}
						}else{
							gstrReason=gstrReason+" "+strResource+" is listed in the 'Event Resources' screen. "+strFailMsg;
						}			
						
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
												
					
					if(intResCnt==2)	{			
						selenium.close();
						selenium.selectWindow("");				
						gstrResult="PASS";
						
						//Write result data
						strTestData[0]= propEnvDetails.getProperty("Build");
						strTestData[1]=gstrTCID;
						strTestData[2]=strUserName+"/"+strConfirmPwd;
						strTestData[3]=strEveTempName;
						strTestData[4]=strEventName;
						strTestData[5]="Resource Name: "+strResource+", Section Name: "+strSection+", Status Types: "+Arrays.toString(strStatTypeArr)+", Step:Check in mobile";
						
						String strWriteFilePath=pathProps.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
						
					}
				}catch(AssertionError ae){
					log4j.info("Event '"+strEventName+"' is NOT listed on 'Event Management' screen.");
					gstrReason="Event '"+strEventName+"' is NOT listed on 'Event Management' screen.";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68369";
			gstrTO = "Verify that a user with 'Associated with' right on the resource cannot view the event related status types of the resource associated with private event. ";
			gstrResult = "FAIL";
			excReason = null;
	
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}			
	
	}
	/**
	'*************************************************************
	' Description: Verify that a user with 'View Resource' right cannot view the event related status types of the resource associated with private event.
	' Precondition: 
			1. Role based status types MST, NST, TST and SST are created.
			2. Event related role based status types ENST, EMST, ESST and ETST are created.
			3. All status types(role based and event related) are under status type section SEC1.
			4. Resource type RT is created selecting MST, NST, TST, SST, ENST, EMST, ESST and ETST status types.
			5. Resource RS is created under RT providing address.
			6. User A is created selecting;
			
			a. View Resource right on RS.
			b. Role R1 to view and update status types NST, MST, TST, SST, ENST, EMST, ESST and ETST.
			c.'Maintain Event Templates' right
			d 'Maintain Events' right.
			e. 'User - Setup User Accounts' right
			
			7. Event Template ET is created selecting RT, NST, MST, TST, SST, ENST, EMST, ESST and ETST.
	' Arguments:
	' Returns:
	' Date: 17-Apr-2012
	' Author:QSG
	'----------------------------------------------------------------------------------
	' Modified Date                            Modified By
	' <Date>                                     <Name>
	'************************************************************* 
*/
	@Test
	public void testBQS68370()throws Exception{
		try{
			gstrTCID = "68370";			
			gstrTO = "Verify that a user with 'View Resource' right cannot view the event related status types of the resource associated with private event.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			int intResCnt=0;
			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			ReadData objReadData = new ReadData (); 
			MobileView objMob=new MobileView();
			Login objLogin = new Login();// object of class Login
			EventSetup objEve=new EventSetup();
		
			CreateUsers objUser=new CreateUsers();
			ViewMap objMap=new ViewMap();
			String strTestData[]=new String[10];
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();
			StatusTypes objST=new StatusTypes();
			Views objViews=new Views();
			CreateUsers objCreateUsers=new CreateUsers();
			String strFILE_PATH=pathProps.getProperty("TestData_path");
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Roles objRole=new Roles();
			String strEventName="AutoEv"+System.currentTimeMillis();
			String strEveTempName="AutoET_"+strTimeText;
			String strResource="AutoRs_"+strTimeText;
			String strInfo="auto event";	
			
			String strIconSrc=objReadData.readInfoExcel("Event Temp Data", 2, 6, strFILE_PATH);
					
			String strMSTValue="Multi";
			String strNSTValue="Number";
			String strTSTValue="Text";
			String strSSTValue="Saturation Score";
						
			String strEveTemp="AutoET_"+strTimeText;
			String strTempDef=objrdExcel.readInfoExcel("Event Temp Data", 2, 3, strFILE_PATH);
			String strEveColor=objrdExcel.readInfoExcel("Event Temp Data", 2, 4, strFILE_PATH);
			String strAsscIcon=objrdExcel.readInfoExcel("Event Temp Data", 2, 5, strFILE_PATH);
					
			String strNumStat="Aa1_"+strTimeText;
			String strMulStat="Aa2_"+strTimeText;
			String strTextStat="Aa3_"+strTimeText;
			String strSatuStat="Aa4_"+strTimeText;
			
			String strENumStat="A11_"+strTimeText;
			String strEMulStat="A12_"+strTimeText;
			String strETextStat="A13_"+strTimeText;
			String strESatuStat="A14_"+strTimeText;
			//String strEveSection="AutoEveSec1";
			String[] strEveStatType={strEMulStat,strENumStat,strESatuStat,strETextStat};
			String[] strRoleStatType={strMulStat,strNumStat,strSatuStat,strTextStat};
			String []strStatTypeArr={strEMulStat,strESatuStat,strMulStat,strETextStat,strENumStat,strNumStat,strSatuStat,strTextStat};
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn="Auto";
			String strFailMsg ="";
			String strRSTvalue[]=new String[4];
			String strERSTvalue[]=new String[4];
			String strStatValue="";
		
			String strResrctTypName="AutoRt_"+strTimeText;
			String strRTValue[]=new String[1];
			String strRSValue[]=new String[1];
			String strAbbrv = "A" + strTmText;
			String strResVal="";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="autouser";
			String strRoleValue="";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strSection="AB_"+strTimeText;
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strMulStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strMulStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strNumStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strNumStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strTextStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strTextStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strSatuStat, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strSatuStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strMSTValue, strEMulStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifyMultiST(selenium, strEMulStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strEMulStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strNSTValue, strENumStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strENumStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strENumStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strTSTValue, strETextStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strETextStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strETextStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[2]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(selenium, strSSTValue, strESatuStat, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectDeSelEventOnly(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.savAndVerifySTNew(selenium, strESatuStat);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium, strESatuStat);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strERSTvalue[3]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(selenium, strResrctTypName, "css=input[name='statusTypeID'][value='"+strRSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			for(int intST=0;intST<strRSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium, strRSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
			for(int intST=0;intST<strERSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(selenium, strERSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(selenium, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(selenium, strResrctTypName);
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
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(selenium, strResource, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
									
			try {
				assertEquals("", strFailMsg);
				
				strFailMsg = objRole.CreateRoleWithAllFields(selenium, strRoleName, strRoleRights, strRSTvalue, true, strRSTvalue, true, false);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
					String[][] strViewRts={{strRSTvalue[0],"true"},
											{strRSTvalue[1],"true"},
											{strRSTvalue[2],"true"},
											{strRSTvalue[3],"true"},
										{strERSTvalue[0],"true"},
										{strERSTvalue[1],"true"},
										{strERSTvalue[2],"true"},
										{strERSTvalue[3],"true"}};
				String[][] strUpdRts={{strRSTvalue[0],"true"},
						{strRSTvalue[1],"true"},
						{strRSTvalue[2],"true"},
						{strRSTvalue[3],"true"},
					{strERSTvalue[0],"true"},
					{strERSTvalue[1],"true"},
					{strERSTvalue[2],"true"},
					{strERSTvalue[3],"true"}};
				
				strFailMsg = objRole.slectAndDeselectSTInCreateRole(selenium, false, false, strViewRts, strUpdRts, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strRoleValue = objRole.fetchRoleValueInRoleList(selenium, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(selenium);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(selenium, strRoleValue, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(selenium, strResource, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvnts"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.MaintEvntTemps"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(selenium, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUser(selenium, strUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg= "Event Setup screen is NOT displayed";
				assertTrue(objEve.navToEventSetup(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertTrue(strFailMsg.equals(""));	
				//navigate to Event Template
				strFailMsg=objEve.createEventTemplate(selenium);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			String strArRoleRights[]={strRSTvalue[0],strRSTvalue[1],strRSTvalue[2],strRSTvalue[3],
					strERSTvalue[0],strERSTvalue[1],strERSTvalue[2],strERSTvalue[3]};
			
			try {
				assertTrue(strFailMsg.equals(""));			
			
				//fill the required fields in Create Event Template and save
				strFailMsg=objEve.fillMandfieldsAndSaveEveTemp(selenium, strEveTemp, strTempDef, strEveColor, strAsscIcon, strIconSrc,"","","","",true,strRTValue,strArRoleRights,true,false,false);
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);
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
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox, strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.logout(selenium);
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
			
			
	/*		 * Step 2: 	Login as user A, navigate to Events >> Management. <->	'Event Management' screen is displayed.*/
			 
									
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg= "Event Management screen is NOT displayed";
				assertTrue(objEve.navToEventManagement(selenium));
				strFailMsg="";	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/* * Step 3: Click on 'Create New Event' button, select ET, provide mandatory data (EVE as event name), select Private check box, select resource RS and click on 'Save'. <->	EVE is listed in the 'Event management' screen.
			 * */
			 
			try {
				assertTrue(strFailMsg.equals(""));
								
				strFailMsg=objEve.createEvent(selenium, strEveTempName, strResource, strEventName, strInfo,false);
				log4j.info("Event Name: "+strEventName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
				
				//select private? check box
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Private"));
				
				//click on save
				selenium.click(propElementDetails.getProperty("Event.CreateEve.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
				
				try{
					assertTrue(selenium.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/td[6][text()='"+strEventName+"']"));
					log4j.info("Event '"+strEventName+"' is listed on 'Event Management' screen.");
					
					/* * Step 4: 	Navigate to Setup >> Users <->	'User List' screen is displayed.*/
					 
					strFailMsg=objUser.navUserListPge(selenium);
					try {
						assertTrue(strFailMsg.equals(""));
						
						 /** Step 5: Click on 'Edit' link next to User A, navigate to 'Resource Rights' section, select 'View Resource' right check box next to resource RS and click on 'Save'. <->	'User List' screen is displayed. */
						 
						//Click on Edit user link
						selenium.click("//table[@id='tblUsers']/tbody/tr/td[text()='"+strUserName+"']/parent::tr/td[1]/a[text()='Edit']");
						selenium.waitForPageToLoad(gstrTimeOut);
						
						strFailMsg=objUser.selectResourceRights(selenium, strResource, false, false, false, true);
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objUser.savVrfyUser(selenium, strUserName);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					/* * Step 6: Logout and login as user A, click on event banner EVE. <->	Appropriate message 'You do not have viewing rights to any resources participating in this event.' is displayed. 
					 */
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg=objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						strFailMsg = objLogin.login(selenium, strUserName,
								strConfirmPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						selenium.selectFrame("Data");
						//click on Event banner
						selenium.click("//div[@id='eventsBanner']/table/tbody/tr/td/a[text()='"+strEventName+"']");
						selenium.waitForPageToLoad(gstrTimeOut);
						
						
						try{
							assertEquals("You do not have viewing rights to any resources participating in this event.", selenium.getText("css=#viewContainer"));
							log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is displayed.");
							intResCnt++;
						} catch (AssertionError Ae) {
							log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.");
							gstrReason="Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.";
						}
						
					
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					/* * Step 7: 	Navigate to View>>Map and click on the resource icon of RS. <->	NST, MST, TST and SST status types are displayed in the resource RS pop up window.
					 * <-> ENST, EMST, ESST and ETST status types are not displayed in the resource RS pop up window. 
					 */
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objMap.verifyStatTypesInResourcePopup(selenium, strResource,strEveStatType,strRoleStatType,false,true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					/* * Step 8: Click on 'View Info' link on the resource pop up window of RS <-> NST, MST, TST and SST status types are displayed under the status type section SEC1 on 'View Resource Detail' screen.
					 * <-> ENST, EMST, ESST and ETST status types are not displayed under the status type section SEC1 on 'View Resource Detail' screen. 
					 */
					try {
						assertTrue(strFailMsg.equals(""));					
						strFailMsg=objMap.verifyStatTypeInViewResDetail(selenium, strSection,strEveStatType,strRoleStatType,false,true);
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
					
					try {
						assertTrue(strFailMsg.equals(""));
						
						/* * Step 9: Click on 'Mobile View' link on footer of the application. <-> 'Main Menu' screen is displayed.
						 * Step 10: Click on Events >> EVE >> Resources. <-> 'Event Resources' screen is displayed.
						 * <-> Appropriate message 'You do not have viewing rights to any resources participating in this event or the event does not involve resources.' is displayed.  
						 */
						strFailMsg=objMob.navToEventDetailInMob(selenium, strEventName, strIconSrc, strResource);
						if(strFailMsg.compareToIgnoreCase(strResource+" is NOT listed in the 'Event Resources' screen.")==0){
							try{
								assertTrue(selenium.isTextPresent("You do not have viewing rights to any resources participating in this event or the event does not involve resources."));
								log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is displayed.");
								intResCnt++;
							} catch (AssertionError Ae) {
								log4j.info("Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.");
								gstrReason=gstrReason+" Appropriate message 'You do not have viewing rights to any resources participating in this event.' is NOT displayed.";
							}
						}else{
							gstrReason=gstrReason+" "+strResource+" is listed in the 'Event Resources' screen. "+strFailMsg;
						}			
						
						
					} catch (AssertionError Ae) {
						gstrReason = strFailMsg;
					}
												
					
					if(intResCnt==2)	{			
						selenium.close();
						selenium.selectWindow("");				
						gstrResult="PASS";
						
						//Write result data
						strTestData[0]= propEnvDetails.getProperty("Build");
						strTestData[1]=gstrTCID;
						strTestData[2]=strUserName+"/"+strConfirmPwd;
						strTestData[3]=strEveTempName;
						strTestData[4]=strEventName;
						strTestData[5]="Resource Name: "+strResource+", Section Name: "+strSection+", Status Types: "+Arrays.toString(strStatTypeArr)+", Step:Check in mobile";
						
						String strWriteFilePath=pathProps.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath, "Events");
						
					}
				}catch(AssertionError ae){
					log4j.info("Event '"+strEventName+"' is NOT listed on 'Event Management' screen.");
					gstrReason="Event '"+strEventName+"' is NOT listed on 'Event Management' screen.";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "68370";
			gstrTO = "Verify that a user with 'View Resource' right cannot view the event related status types of the resource associated with private event.";
			gstrResult = "FAIL";
			excReason = null;
	
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}			
	
	}
	
	
}
