package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Status Reason Summary Report
' Requirement Group	:Reports
' Product		    :EMResource v3.17
' Date			    :06-07-2012
' Author		    :QSG
' ------------------------------------------------------------------
' Modified Date				                            Modified By
' Date					                                Name
'*******************************************************************/
@SuppressWarnings("unused")
public class StatusReasonSummaryRep {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.StatusReasonSummaryRep");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	Selenium selenium,seleniumPrecondition;
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails, propPathDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails, propAutoItDetails, pathProps;
	public static Properties browserProps = new Properties();

	private String browser = "";

	private String json;
	public static long sysDateTime;
	public static long gsysDateTime;
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId, StrSessionId1, StrSessionId2;
	public static String gstrTimeOut = "";

	@Before
	public void setUp() throws Exception {

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {

			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.json,
					propEnvDetails.getProperty("urlEU"));

		} else {
			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.browser,
					propEnvDetails.getProperty("urlEU"));

		}

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");
		
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		// kill browser
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
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}
	
	/*******************************************************************************
	'Description	:Add a multi status type MST back to resource RS with the status 
	                 reason and update the status value of RS, generate the �Status 
	                 Reason Summary Report� and verify that the data is displayed 
	                 appropriately in the report
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date				                                        Modified By
	'Date					                                            Name
	********************************************************************************/
	@Test
	public void testBQS37559() throws Exception {
		CSVFunctions objCSV = new CSVFunctions();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "BQS-37559"; // Test Case Id
			gstrTO = "Add a multi status type MST back to resource"
					+ " RS with the status reason and update the status"
					+ " value of RS, generate the �Status Reason Summary"
					+ " Report� and verify that the data is displayed "
					+ "appropriately in the report";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strTestData[] = new String[10];
			
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String strStatTypeAsgnVal = "Number";
			String strStatTypeAsgn = "AutoNst_" + strTimeText;

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strSTvalue[] = new String[1];
			strSTvalue[0] = "";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strSTAsnVal[] = new String[1];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUpdMulValueBef = "";
			String strUpdValCheckBef = "";
			String strUpdMulValueAft = "";
			String strUpdValCheckAft = "";

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

		 log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			
			strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

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
						strStatTypeAsgnVal, strStatTypeAsgn, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeAsgn);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTAsnVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName + "']"));

					log4j.info("Status type " + statTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName, strStatusName1,
								strStatusTypeValue, strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
										strReasonVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
								statTypeName, strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName, strStatusName2,
								strStatusTypeValue, strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
										strReasonVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
								statTypeName, strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName, strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName, strStatusName2);
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
						strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
								strResType,
								"css=input[name='statusTypeID'][value='"
										+ strSTAsnVal[0] + "']");
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
						strFuncResult = objRs.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv, strResType,
								strContFName, strContLName, strState,
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
						strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
								strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								seleniumPrecondition, strSTvalue[0], true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

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
						strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUserName, strUsrPassword,
								strUsrPassword, strUsrFulName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, true, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
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
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly"),
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
												.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
										true);
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
								.savVrfyUserWithSearchUser(seleniumPrecondition,
										strUserName, strByRole,
										strByResourceType, strByUserInfo,
										strNameFormat);
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
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValueBef = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValueBef.equals(strStatusName1)) {
							strUpdValCheckBef = strStatusValue[0];
						} else {
							strUpdValCheckBef = strStatusValue[1];
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
					// deselect
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navToEditResLevelSTPage(selenium,
								strResource);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								selenium, strSTvalue[0], false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// select
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.navToEditResLevelSTPage(selenium,
								strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								selenium, strSTvalue[0], true);
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
						strFuncResult = objLogin.login(selenium, strUserName,
								strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);

						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValueAft = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValueAft.equals(strStatusName1)) {
							strUpdValCheckAft = strStatusValue[0];
						} else {
							strUpdValCheckAft = strStatusValue[1];
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, true,
										strUpdValCheckAft, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheckAft, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Status Reason Summary", "**", "**", "**",
										"**", "**" },
								{ "Resource", "Status", "Status Reason",
										"Time(hrs)",
										"% of all Hrs on this status",
										"% of Total Hrs" },

								{ strResource, strUpdMulValueAft,
										strReasonName, "\\d+\\.\\d+",
										"\\d+\\.\\d+", "\\d+\\.\\d+" } };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";
						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strUsrPassword;
						strTestData[3] = strResType;
						strTestData[4] = strResource;
						strTestData[5] = statTypeName;
						strTestData[6] = "Status Before: " + strUpdMulValueBef
								+ "Status After: " + strUpdMulValueAft;
						strTestData[5] = "Check the Status Summary details in PDF file: "
								+ strPDFDownlPath
								+ "status reason: "
								+ strReasonName;

						String strWriteFilePath = propPathDetails
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Reports");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-37559";
			gstrTO = "Add a multi status type MST back to resource RS with "
					+ "the status reason and update the status value of RS, "
					+ "generate the �Status Reason Summary Report� and verify"
					+ " that the data is displayed appropriately in the report";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************************************
	'Description	:Update status of a multi status type MST with a status reason where MST 
	                 is added at the resource level for resource RS. Verify that a user with 
	                 �Run Report� and 'View Resource' rights right on RS and with a role with
	                  only VIEW right for MST can view the details of selected reason in the 
	                  generated 'Status Reason Summary Report'.
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	******************************************************************************************/
	@Test
	public void testBQS42739() throws Exception {
		String strFuncResult = "";
		CSVFunctions objCSV = new CSVFunctions();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "BQS-42739"; // Test Case Id
			gstrTO = "Update status of a multi status type MST with a status"
					+ " reason where MST is added at the resource level for"
					+ "resource RS. Verify that a user with �Run Report� and "
					+ "'View Resource' rights right on RS and with a role with"
					+ " only VIEW right for MST can view the details of selected "
					+ "reason in the generated 'Status Reason Summary Report'.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strTestData[] = new String[10];

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String strStatTypeAsgnVal = "Number";
			String strStatTypeAsgn = "AutoNst_" + strTimeText;

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strSTvalue[] = new String[1];
			strSTvalue[0] = "";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strSTAsnVal[] = new String[1];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			
		log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.createStatusReasn(seleniumPrecondition, strReasonName,
								"", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

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
						seleniumPrecondition, strStatTypeAsgnVal,
						strStatTypeAsgn, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeAsgn);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTAsnVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName + "']"));

					log4j.info("Status type " + statTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName1, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName2, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName2);
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
						strFuncResult = objRT
								.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(
								seleniumPrecondition, strResType,
								"css=input[name='statusTypeID'][value='"
										+ strSTAsnVal[0] + "']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(
								seleniumPrecondition, strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs
								.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv,
								strResType, strContFName, strContLName,
								strState, strCountry, strStandResType);

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
						strFuncResult = objRs.navToEditResLevelSTPage(
								seleniumPrecondition, strResource);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								seleniumPrecondition, strSTvalue[0], true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole
								.navRolesListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName,
								strRoleRights, strSTvalue, true, strSTvalue,
								true, true);
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

					// create role for user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleForUser,
								strRoleRitsForUsr, strSTvalue, true,
								strRlUpdRtForUsr, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strRoleUsrVal = objRole.fetchRoleValueInRoleList(
								seleniumPrecondition, strRoleForUser);
						if (strRoleUsrVal.compareTo("") != 0) {
							strFuncResult = "";

						} else {
							strFuncResult = "Failed to fetch Role value";
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Update user
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
								seleniumPrecondition, strUpdUsrName, strUpdPwd,
								strUpdPwd, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, true,
								false, true);
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
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUpdUsrName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
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
								seleniumPrecondition, strUserName,
								strUsrPassword, strUsrPassword, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false,
								false, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleUsrVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUserName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUpdUsrName, strUpdPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValue = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValue.equals(strStatusName1)) {
							strUpdValCheck = strStatusValue[0];
						} else {
							strUpdValCheck = strStatusValue[1];
						}

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
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, true,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Status Reason Summary", "**", "**", "**",
										"**", "**" },
								{ "Resource", "Status", "Status Reason",
										"Time(hrs)",
										"% of all Hrs on this status",
										"% of Total Hrs" },

								{ strResource, strUpdMulValue, strReasonName,
										"\\d+\\.\\d+", "\\d+\\.\\d+",
										"\\d+\\.\\d+" }, };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";
						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strUsrPassword;
						strTestData[3] = strResType;
						strTestData[4] = strResource;
						strTestData[5] = statTypeName;
						strTestData[6] = strUpdMulValue;
						strTestData[5] = "Check the Status Summary details in PDF file: "
								+ strPDFDownlPath
								+ "status reason: "
								+ strReasonName;

						String strWriteFilePath = propPathDetails
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Reports");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-42739";
			gstrTO = "Update status of a multi status type MST with a status"
					+ "reason where MST is added at the resource level for "
					+ "resource RS. Verify that a user with �Run Report� and "
					+ "'View Resource' rights right on RS and with a role with "
					+ "only VIEW right for MST can view the details of selected"
					+ " reason in the generated 'Status Reason Summary Report'.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************************************
	'Description	:Update status of a multi status type MST with a status reason where MST 
	                 is added at the resource level for resource RS. Verify that a user with 
	                 �Run Report� and 'View Resource' rights right on RS and with a role with 
	                 only VIEW right for MST can view the details of selected reason in the 
	                 generated 'Status Reason Summary Report'.
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testBQS63426() throws Exception {

		String strTestData[] = new String[10];
		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		try {
			gstrTCID = "BQS-63426"; // Test Case Id
			gstrTO = " Verify that, status reason details of a status type"
					+ " is not displayed in the 'Status Reason Summary' "
					+ "report for a particular resource for which it is refined.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName1 = "AutoMSt1_" + strTimeText;
			String statTypeName2 = "AutoMSt2_" + strTimeText;
			String statTypeName3 = "AutoMSt3_" + strTimeText;
			String strSTvalue[] = new String[3];
			strSTvalue[0] = "";

			String strArStatType[] = { statTypeName1, statTypeName2,
					statTypeName3 };
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusName3 = "Stc" + strTimeText;

			String strArStatus[] = { strStatusName1, strStatusName2,
					strStatusName3 };
			String strStatusValue[] = new String[3];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			strStatusValue[2] = "";

			String strResType = "AutoRt_" + strTimeText;
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];
			strRSValue[0] = "";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName1 = "AutoReas1_" + System.currentTimeMillis();
			String strReasonName2 = "AutoReas2_" + System.currentTimeMillis();
			String[] strReasonVal = new String[2];

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";
			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strPDFDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

			String strCSVDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strPDFDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = "";

			strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SR1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName1, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[0] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName1);
				if (strReasonVal[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// SR2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName2, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[1] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName2);
				if (strReasonVal[1].compareTo("") != 0) {
					strFuncResult = "";

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

			// MST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName1, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName1, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intVal = 0; intVal < strReasonVal.length; intVal++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intVal], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName1, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName1, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
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
						statTypeName2, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intVal = 0; intVal < strReasonVal.length; intVal++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intVal], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName2, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName2, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST3
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statTypeName3, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName3, strStatusName3, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intVal = 0; intVal < strReasonVal.length; intVal++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intVal], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName3, strStatusName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName3, strStatusName3);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType, "css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < strSTvalue.length - 1; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selAndDeselSTInEditResLevelST(
							seleniumPrecondition, strSTvalue[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
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
						strResource1, strAbbrv, strResType, strContFName,
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
						strResource2, strAbbrv, strResType, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResource2);
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
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

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

			// create role for user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleForUser, strRoleRitsForUsr, strSTvalue, true,
						strRlUpdRtForUsr, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleUsrVal = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRoleForUser);
				if (strRoleUsrVal.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Update user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUpdUsrName, strUpdPwd, strUpdPwd, strUsrFulName);
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
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource2, false, true, false, true);
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
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUpdUsrName, strByRole, strByResourceType,
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource1, false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
						strResource2, false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleUsrVal, true);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUpdUsrName,
						strUpdPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int intST = 0; intST < strArStatType.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViewMap.updateMultiSTWithReason(
							selenium, strResource1, strArStatType[intST],
							strSTvalue[intST], strArStatus[intST],
							strStatusValue[intST], strReasonVal);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource1, strArStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1, statTypeName2,
						statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource2, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int intST = 0; intST < strArStatType.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViewMap.updateMultiSTWithReason(
							selenium, strResource2, strArStatType[intST],
							strSTvalue[intST], strArStatus[intST],
							strStatusValue[intST], strReasonVal);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource2, strArStatus);
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
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			for (int intST = 1; intST < 3; intST++) {
				try {
					assertEquals("", strFuncResult);

					strFuncResult = objCreateUsers
							.selAndDeselSTInRefineVisibleST(selenium,
									strSTvalue[intST], false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;

				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.saveChangesInRefineSTAndVerifyEditUser(selenium);
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
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strDateAr[] = objGen.getSnapTime(selenium);
			String strYear = dts.getCurrentDate("yyyy");
			strCurrDate = dts.converDateFormat(strDateAr[1] + strDateAr[0]
					+ strYear, "MMMddyyyy", "MM/dd/yyyy");

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName2, strCSTApplTime,
						strCSTApplTime, true, strStatusValue[1], strReasonVal,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName2, strCSTApplTime,
						strCSTApplTime, false, strStatusValue[1], strReasonVal,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Status Reason Summary", "**", "**", "**", "**", "**" },
						{ "Resource", "Status", "Status Reason", "Time(hrs)",
								"% of all Hrs on this status", "% of Total Hrs" },

						{ strResource2, strStatusName2, strReasonName1,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" },
						{ strResource2, strStatusName2, strReasonName2,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" },
						{ "**", "**", "**", "**", "**", "**" },
						{ "Aggregate Status Reason Summary", "**", "**", "**",
								"**", "**" },
						{ "Resource Type", "Status", "Status Reason",
								"Time(hrs)", "% of all Hrs on this status",
								"% of Total Hrs" },
						{ strResType, strStatusName2, strReasonName1,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" },
						{ strResType, strStatusName2, strReasonName2,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" } };

				strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath1,
						strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName3, strCSTApplTime,
						strCSTApplTime, true, strStatusValue[2], strReasonVal,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName3, strCSTApplTime,
						strCSTApplTime, false, strStatusValue[2], strReasonVal,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Status Reason Summary", "**", "**", "**", "**", "**" },
						{ "Resource", "Status", "Status Reason", "Time(hrs)",
								"% of all Hrs on this status", "% of Total Hrs" },

						{ strResource2, strStatusName3, strReasonName1,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" },
						{ strResource2, strStatusName3, strReasonName2,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" },
						{ "**", "**", "**", "**", "**", "**" },
						{ "Aggregate Status Reason Summary", "**", "**", "**",
								"**", "**" },
						{ "Resource Type", "Status", "Status Reason",
								"Time(hrs)", "% of all Hrs on this status",
								"% of Total Hrs" },
						{ strResType, strStatusName3, strReasonName1,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" },
						{ strResType, strStatusName3, strReasonName2,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" } };

				strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath2,
						strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource2;
				strTestData[5] = statTypeName2 + "," + statTypeName3;
				strTestData[6] = strStatusName2 + "," + strStatusName3;
				strTestData[7] = "Check the Status Reason Summary details in PDF file: "
						+ strPDFDownlPath1
						+ " for MST2 and "
						+ strPDFDownlPath2
						+ " for MST3 status reasons: "
						+ strReasonName1 + "," + strReasonName2;

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63426";
			gstrTO = " Verify that, status reason details of a status"
					+ " type is not displayed in the 'Status Reason Summary'"
					+ " report for a particular resource for which it is refined.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	/****************************************************************************************
	'Description	:Update status of a multi status type MST with a status reason where MST
	                  is added at the resource type level for resource RS. Verify that a user 
	                  with �Run Report� & 'View Resource' rights on RS and with a role with 
	                  only VIEW right for MST can view the details of selected reason in the
	                   generated �Status Reason Summary Report�.
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date				                                                 Modified By
	'Date					                                                     Name
	*****************************************************************************************/
	@Test
	public void testBQS42742() throws Exception {
		try {
			gstrTCID = "BQS-42742"; // Test Case Id
			gstrTO = "Update status of a multi status type MST with a status"
					+ " reason where MST is added at the resource type level "
					+ "for resource RS. Verify that a user with �Run Report� "
					+ "& 'View Resource' rights on RS and with a role with "
					+ "only VIEW right for MST can view the details of selected"
					+ " reason in the generated �Status Reason Summary Report�.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();

			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();

			StatusReason objStatRes = new StatusReason();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = "abc123";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			strSTvalue[0] = "";
			strRSValue[0] = "";
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};

			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strAdmUserName, strAdmPassword);
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
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.createStatusReasn(seleniumPrecondition, strReasonName,
								"", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

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
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName + "']"));

					log4j.info("Status type " + statTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName1, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName2, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName2);
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
						strFuncResult = objRT
								.navResourceTypList(seleniumPrecondition);
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
						strFuncResult = objRT.saveAndvrfyResType(
								seleniumPrecondition, strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs
								.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv,
								strResType, strContFName, strContLName,
								strState, strCountry, strStandResType);

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
						strFuncResult = objRole
								.navRolesListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName,
								strRoleRights, strSTvalue, true, strSTvalue,
								true, true);

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

					// create role for user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleForUser,
								strRoleRitsForUsr, strSTvalue, true,
								strRlUpdRtForUsr, true, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strRoleUsrVal = objRole.fetchRoleValueInRoleList(
								seleniumPrecondition, strRoleForUser);

						if (strRoleUsrVal.compareTo("") != 0) {
							strFuncResult = "";

						} else {
							strFuncResult = "Failed to fetch Role value";
						}
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					// Update user
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
								seleniumPrecondition, strUpdUsrName, strUpdPwd,
								strUpdPwd, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, true,
								false, true);
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
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUpdUsrName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
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
								seleniumPrecondition, strUserName,
								strUsrPassword, strUsrPassword, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false,
								false, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(
								seleniumPrecondition, strRoleUsrVal, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUserName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID+ " EXECUTION ENDS~~~~~");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
				
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
		
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUpdUsrName, strUpdPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValue = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValue.equals(strStatusName1)) {
							strUpdValCheck = strStatusValue[0];
						} else {
							strUpdValCheck = strStatusValue[1];
						}

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
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, true,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Status Reason Summary", "**", "**", "**",
										"**", "**" },
								{ "Resource", "Status", "Status Reason",
										"Time(hrs)",
										"% of all Hrs on this status",
										"% of Total Hrs" },

								{ strResource, strUpdMulValue, strReasonName,
										"\\d+\\.\\d+", "\\d+\\.\\d+",
										"\\d+\\.\\d+" }, };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";
						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strUsrPassword;
						strTestData[3] = strResType;
						strTestData[4] = strResource;
						strTestData[5] = statTypeName;
						strTestData[6] = strUpdMulValue;
						strTestData[5] = "Check the Status Summary details in PDF file: "
								+ strPDFDownlPath
								+ "status reason: "
								+ strReasonName;

						String strWriteFilePath = propPathDetails
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Reports");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-42742";
			gstrTO = "Update status of a multi status type MST with a "
					+ "status reason where MST is added at the resource "
					+ "type level for resource RS. Verify that a user with "
					+ "�Run Report� & 'View Resource' rights on RS and with "
					+ "a role with only VIEW right for MST can view the "
					+ "details of selected reason in the generated �Status"
					+ " Reason Summary Report�.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	/********************************************************************************************
	'Description	:Update status of a multi private status type pMST with a status reason where 
	                 pMST is added at the resource level for resource RS. Verify that a user with 
	                 �Run Report� and 'View Resource' rights right on RS and without any role can 
	          view the details of selected reason in the generated 'Status Reason Summary Report'.
	'Arguments		:None
	'Returns		:None
	'Date			:06-07-2012
	'Author			:QSG
	'--------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*********************************************************************************************/
	@Test
	public void testBQS48857() throws Exception {
		try {
			gstrTCID = "BQS-48857"; // Test Case Id
			gstrTO = "Update status of a multi private status type pMST "
					+ "with a status reason where pMST is added at the "
					+ "resource level for resource RS. Verify that a user"
					+ " with �Run Report� and 'View Resource' rights right"
					+ " on RS and without any role can view the details of"
					+ " selected reason in the generated 'Status Reason "
					+ "Summary Report'.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();

			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();

			StatusReason objStatRes = new StatusReason();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String strStatTypeAsgnVal = "Number";
			String strStatTypeAsgn = "AutoNst_" + strTimeText;

			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();

			String strUpdPwd = "abc123";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			String strSTAsnVal[] = new String[1];
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			strSTvalue[0] = "";
			strRSValue[0] = "";
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";

		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " Prcondition STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.createStatusReasn(seleniumPrecondition, strReasonName,
								"", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName);
				if (strReasonVal.compareTo("") != 0) {
					strFuncResult = "";

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
						seleniumPrecondition, strStatTypeAsgnVal,
						strStatTypeAsgn, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatTypeAsgn);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTAsnVal[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// private ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(
						seleniumPrecondition, strReasonVal, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				seleniumPrecondition.click(propElementDetails
						.getProperty("Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

				try {
					assertTrue(seleniumPrecondition
							.isElementPresent("//div[@id='mainContainer']/"
									+ "table/tbody/tr/td[2][text()='"
									+ statTypeName + "']"));

					log4j.info("Status type " + statTypeName
							+ " is created and is listed in the "
							+ "'Status Type List' screen. ");

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
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName1, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName1);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.createSTWithinMultiTypeST(
								seleniumPrecondition, statTypeName,
								strStatusName2, strStatusTypeValue,
								strStatTypeColor, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objStatRes
								.selectAndDeselectStatusReasInStatus(
										seleniumPrecondition, strReasonVal,
										true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objST.saveAndVerifyStatus(
								seleniumPrecondition, statTypeName,
								strStatusName2);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName1);
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
						String strStatValue = objST.fetchStatValInStatusList(
								seleniumPrecondition, statTypeName,
								strStatusName2);
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
						strFuncResult = objRT
								.navResourceTypList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.resrcTypeMandatoryFlds(
								seleniumPrecondition, strResType,
								"css=input[name='statusTypeID'][value='"
										+ strSTAsnVal[0] + "']");
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRT.saveAndvrfyResType(
								seleniumPrecondition, strResType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs
								.navResourcesList(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.createResourceWitLookUPadres(
								seleniumPrecondition, strResource, strAbbrv,
								strResType, strContFName, strContLName,
								strState, strCountry, strStandResType);

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
						strFuncResult = objRs.navToEditResLevelSTPage(
								seleniumPrecondition, strResource);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRs.selDeselctSTInEditRSLevelPage(
								seleniumPrecondition, strSTvalue[0], true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole
								.navRolesListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					// create role to update

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRole.CreateRoleWithAllFields(
								seleniumPrecondition, strRoleName,
								strRoleRights, strSTvalue, true, strSTvalue,
								true, true);

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

					// Update user
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
								seleniumPrecondition, strUpdUsrName, strUpdPwd,
								strUpdPwd, strUsrFulName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false, true,
								false, true);

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
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUpdUsrName,
										strByRole, strByResourceType,
										strByUserInfo, strNameFormat);

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
								seleniumPrecondition, strUserName,
								strUsrPassword, strUsrPassword, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.selectResourceRights(
								seleniumPrecondition, strResource, false,
								false, true, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
									true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.savVrfyUserWithSearchUser(
										seleniumPrecondition, strUserName,
										strByRole, strByResourceType,
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
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUpdUsrName, strUpdPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);

						String[] strEventStatType = {};
						String[] strRoleStatType = { statTypeName };
						strFuncResult = objViewMap
								.verifyStatTypesInResourcePopup(selenium,
										strResource, strEventStatType,
										strRoleStatType, false, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objViewMap
								.updateMultiStatusTypeWithReason(selenium,
										strResource, statTypeName,
										strSTvalue[0], strStatusName1,
										strStatusValue[0], strStatusName2,
										strStatusValue[1], strReasonVal,
										strReasonVal);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;

					}

					try {
						assertEquals("", strFuncResult);
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdSTValueInMap(selenium,
								"1");
						strFuncResult = strArFunRes[1];
						strUpdMulValue = strArFunRes[0];

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						if (strUpdMulValue.equals(strStatusName1)) {
							strUpdValCheck = strStatusValue[0];
						} else {
							strUpdValCheck = strStatusValue[1];
						}

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
						strFuncResult = objLogin.newUsrLogin(selenium,
								strUserName, strUsrPassword);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, true,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strPDFDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						strFuncResult = objRep.navToStatusReports(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.navToStatusReasonSummReport(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						String strCSTApplTime = dts
								.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

						strFuncResult = objRep
								.enterStatReasSummaryRepAndGenRep(selenium,
										strRSValue[0], statTypeName,
										strCSTApplTime, strCSTApplTime, false,
										strUpdValCheck, strReasonVal);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						// Thread.sleep(20000);
						String strProcess = "";
						String strArgs[] = { strAutoFilePath, strCSVDownlPath };

						// AutoIt
						Runtime.getRuntime().exec(strArgs);

						int intCnt = 0;
						do {
							GetProcessList objGPL = new GetProcessList();
							strProcess = objGPL.GetProcessName();
							intCnt++;
							Thread.sleep(500);
						} while (strProcess.contains(strAutoFileName)
								&& intCnt < 180);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Status Reason Summary", "**", "**", "**",
										"**", "**" },
								{ "Resource", "Status", "Status Reason",
										"Time(hrs)",
										"% of all Hrs on this status",
										"% of Total Hrs" },

								{ strResource, strUpdMulValue, strReasonName,
										"\\d+\\.\\d+", "\\d+\\.\\d+",
										"\\d+\\.\\d+" }, };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";
						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strUsrPassword;
						strTestData[3] = strResType;
						strTestData[4] = strResource;
						strTestData[5] = statTypeName;
						strTestData[6] = strUpdMulValue;
						strTestData[5] = "Check the Status Summary details in PDF file: "
								+ strPDFDownlPath
								+ "status reason: "
								+ strReasonName;

						String strWriteFilePath = propPathDetails
								.getProperty("WriteResultPath");
						objOFC.writeResultData(strTestData, strWriteFilePath,
								"Reports");

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {

					log4j.info("Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. ");

					gstrReason = "Status type " + statTypeName
							+ " is created and is NOT listed in the "
							+ "'Status Type List' screen. " + Ae;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-48857";
			gstrTO = "Update status of a multi private status type"
					+ " pMST with a status reason where pMST is added "
					+ "at the resource level for resource RS. Verify "
					+ "that a user with �Run Report� and 'View Resource'"
					+ " rights right on RS and without any role can view"
					+ " the details of selected reason in the generated "
					+ "'Status Reason Summary Report'.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/*******************************************************************
	'Description  :Generate 'Status Reason Summary report' in PDF format  
	               and verify that the report displays correct data
	'Arguments	  :None
	'Returns	  :None
	'Date		  :29-11-2012
	'Author		  :QSG
	'-------------------------------------------------------------------
	'Modified Date				                            Modified By
	'Date					                                Name
	********************************************************************/
	@Test
	public void testBQS103964() throws Exception {

		String strFuncResult = "";
		CSVFunctions objCSV = new CSVFunctions();
		General objGen = new General();
		StatusReason objStatRes = new StatusReason();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		Roles objRole = new Roles();
		try {
			gstrTCID = "BQS-103964"; // Test Case Id
			gstrTO = "Generate 'Status Reason Summary report' in PDF"
					+ " format and verify that the report displays correct data";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();
			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strTestData[] = new String[10];

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutopMSt_" + strTimeText;
			String statShTypeName = "AutosMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			String strSTvalue[] = new String[2];
			strSTvalue[0] = "";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			strRSValue[0] = "";
			// ROLE
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};
			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName1 = "AutoReas1_" + System.currentTimeMillis();
			String strReasonName2 = "AutoReas2_" + System.currentTimeMillis();
			String strReasonName3 = "AutoReas3_" + System.currentTimeMillis();
			String[] strReasonVal = new String[3];
			// User
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrFulName = "autouser";
			String strUsrPassword = objrdExcel.readData("Login", 4, 2);
			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			String strUpdPwd = objrdExcel.readData("Login", 4, 2);

			String strUpdMulValue = "";
			String strUpdValCheck = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strPDFDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";
			String strPDFDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

			String strPDFDownlPath3 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep3_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";
			String strPDFDownlPath4 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep4_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".pdf";

		/** Preconditions:1. Status Reasons 'SR1','SR2' and 'SR3' are created.
			2. Status types pMST and sMST are created selecting Status Reasons 'SR1','SR2' and 'SR3'.
			3. Statuses 'S1' and 'S2' are created for pMST and sMST selecting Status Reasons 
			'SR1','SR2' and 'SR3'.
			4. Resources Type RT is created selecting status types pMST and sMST.
			5. Resource 'RS' is created under 'RT' providing address.
			6. User U1 has following rights:
					a. Report - Status Reason Summary.
					b. Role to view and update status types.
					c.'View Status','Update Status' and 'Run Report' rights on resources RS. 
		 * 
		 */

			strFuncResult = objLogin.login(seleniumPrecondition,
					strAdmUserName, strAdmPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes
						.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(
						seleniumPrecondition, strReasonName1, "", "", true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[0] = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName1);
				if (strReasonVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(
						seleniumPrecondition, strReasonName2, "", "", true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[1] = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName2);
				if (strReasonVal[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(
						seleniumPrecondition, strReasonName3, "", "", true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[2] = objStatRes.fetchStatReasonValue(
						seleniumPrecondition, strReasonName3);
				if (strReasonVal[2].compareTo("") != 0) {
					strFuncResult = "";

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
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName, strStatusName2);
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statShTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(
						seleniumPrecondition, true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statShTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statShTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(
						seleniumPrecondition, statShTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(
									seleniumPrecondition, strReasonVal[intSR],
									true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statShTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(
						seleniumPrecondition, statShTypeName, strStatusName1);
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

			for (int intST = 1; intST < strSTvalue.length; intST++) {
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
						strResType);
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName, strUsrPassword,
						strUsrPassword, strUsrFulName);
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
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, true, true,
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
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndCheckErrorMsg(
						selenium, strRSValue, strCSTApplTime, strCSTApplTime,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statShTypeName };
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
			String[] strPtStReasVal1 = { strReasonVal[0], strReasonVal[1] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName2, strStatusValue[1], strPtStReasVal1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strShStReasVal1 = { strReasonVal[1], strReasonVal[2] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[1],
						strStatusName1, strStatusValue[0], strShStReasVal1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus = { strStatusName1, strStatusName2 };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strUpdTime1 = "";
			String[] strArFunRes = new String[5];
			String strCurrYear = dts.getCurrentDate("yyyy");
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes[4];
				strCurrDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strUpdTime1 = strArFunRes[2] + ":" + strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Wait for 3 mins
				Thread.sleep(180000);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statShTypeName, strCSTApplTime,
						strCSTApplTime, true, strStatusValue[0],
						strShStReasVal1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes1 = new String[5];
			String strRepGenTime1 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];

				strRepGenTime1 = strArFunRes1[2] + ":" + strArFunRes1[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName, strCSTApplTime,
						strCSTApplTime, true, strStatusValue[1],
						strPtStReasVal1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes2 = new String[5];
			String strRepGenTime2 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes2 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes2[4];

				strRepGenTime2 = strArFunRes2[2] + ":" + strArFunRes2[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statShTypeName };
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
			String[] strPtStReasVal2 = { strReasonVal[1], strReasonVal[2] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName2, strStatusValue[1], strPtStReasVal2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String[] strShStReasVal2 = { strReasonVal[0], strReasonVal[1] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[1],
						strStatusName1, strStatusValue[0], strShStReasVal2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus1 = { strStatusName1, strStatusName2 };
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strUpdTime2 = "";
			String[] strArFunRes3 = new String[5];

			try {
				assertEquals("", strFuncResult);
				strArFunRes3 = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes3[4];
				strCurrDate = dts.converDateFormat(strArFunRes3[0]
						+ strArFunRes3[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strUpdTime2 = strArFunRes3[2] + ":" + strArFunRes3[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statShTypeName, strCSTApplTime,
						strCSTApplTime, true, strStatusValue[0],
						strShStReasVal2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath3 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes4 = new String[5];
			String strRepGenTime3 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes4 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes4[4];

				strRepGenTime3 = strArFunRes4[2] + ":" + strArFunRes4[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName, strCSTApplTime,
						strCSTApplTime, true, strStatusValue[1],
						strPtStReasVal2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath4 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[] strArFunRes5 = new String[5];
			String strRepGenTime4 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes5 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes5[4];

				strRepGenTime4 = strArFunRes5[2] + ":" + strArFunRes5[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource;
				strTestData[5] = statTypeName + "" + statShTypeName;
				strTestData[6] = strStatusName1 + "," + strStatusName2;
				strTestData[7] = strTestData[7] = "Check the Status Summary details in PDF file(Step 9): "
						+ strPDFDownlPath1
						+ ",Status Type: "
						+ statShTypeName
						+ ", Status updated: "
						+ strStatusName1
						+ ",Stauts Reason selected: "
						+ strReasonName2
						+ ","
						+ strReasonName3
						+ ", Status Update time: "
						+ strUpdTime1
						+ "Report Generation Time: "
						+ strRepGenTime1
						+ "Check the Status Summary details in PDF file(Step 12): "
						+ strPDFDownlPath2
						+ ",Status Type: "
						+ statTypeName
						+ ", Status updated: "
						+ strStatusName2
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ", Status Update time: "
						+ strUpdTime1
						+ "Report Generation Time: "
						+ strRepGenTime2
						+ "Check the Status Summary details in PDF file(Step 17): "
						+ strPDFDownlPath3
						+ ",Status Type: "
						+ statShTypeName
						+ ", Status updated: "
						+ strStatusName1
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: "
						+ strRepGenTime3
						+ "Check the Status Summary details in PDF file(Step 19): "
						+ strPDFDownlPath4
						+ ",Status Type: "
						+ statTypeName
						+ ", Status updated: "
						+ strStatusName2
						+ ",Stauts Reason selected: "
						+ strReasonName2
						+ ","
						+ strReasonName3
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: "
						+ strRepGenTime3;

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103964";
			gstrTO = "Generate 'Status Reason Summary report' in PDF format"
					+ " and verify that the report displays correct data";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************
	'Description	:Generate 'Status Reason Summary report' in CSV format and 
	                 verify that the report displays correct data.
	'Arguments		:None
	'Returns		:None
	'Date			:30-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS103965() throws Exception {
		try {
			gstrTCID = "BQS-103965"; // Test Case Id
			gstrTO = "Generate 'Status Reason Summary report' in CSV"
					+ " format and verify that the report displays correct data.";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			propPathDetails = objAP.Read_FilePath();
			pathProps = objAP.Read_FilePath();

			propAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();
			General objGen = new General();
			StatusReason objStatRes = new StatusReason();
			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();

			Roles objRole = new Roles();

			String strFuncResult = "";

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login

			ViewMap objViewMap = new ViewMap();
			Reports objRep = new Reports();

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutopMSt_" + strTimeText;
			String statShTypeName = "AutosMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";

			String strAbbrv = "Rs";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			;
			String strUpdPwd = "abc123";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			strSTvalue[0] = "";
			strRSValue[0] = "";
			String strAutoFilePath = propAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath1 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep1_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strCSVDownlPath2 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep2_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";

			String strCSVDownlPath3 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep3_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";
			String strCSVDownlPath4 = propPathDetails
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasSumRep4_"
					+ gstrTCID
					+ "_"
					+ strTimeText
					+ ".csv";

			String strAutoFileName = propAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleForUser = "AutoRL" + strTimeText;
			String strRoleRitsForUsr[][] = {};

			String[] strRlUpdRtForUsr = {};
			String strRoleUsrVal = "";

			String strReasonName1 = "AutoReas1_" + System.currentTimeMillis();
			String strReasonName2 = "AutoReas2_" + System.currentTimeMillis();
			String strReasonName3 = "AutoReas3_" + System.currentTimeMillis();

			String[] strReasonVal = new String[3];

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
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
				strFuncResult = objStatRes.navStatusReasonList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName1, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[0] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName1);
				if (strReasonVal[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName2, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[1] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName2);
				if (strReasonVal[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition,
						strReasonName3, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strReasonVal[2] = objStatRes.fetchStatReasonValue(seleniumPrecondition,
						strReasonName3);
				if (strReasonVal[2].compareTo("") != 0) {
					strFuncResult = "";

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
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, statTypeName,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(seleniumPrecondition,
						false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statTypeName);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statTypeName, strStatusName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strStatusTypeValue, statShTypeName, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(seleniumPrecondition,
						true, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objST.selectAndDeselectStatusReason(
							seleniumPrecondition, strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						statShTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, statShTypeName);
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
						statShTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intSR = 0; intSR < strReasonVal.length; intSR++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatRes
							.selectAndDeselectStatusReasInStatus(seleniumPrecondition,
									strReasonVal[intSR], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						statShTypeName, strStatusName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statShTypeName, strStatusName1);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResType, "css=input[name='statusTypeID'][value='"
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
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// create role to update

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
						strUserName, strUsrPassword, strUsrPassword,
						strUsrFulName);

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
						strResource, false, true, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.RepStatReasonSummary"),
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
		log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strUsrPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statShTypeName };
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
			String[] strPtStReasVal1 = { strReasonVal[0], strReasonVal[1] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName2, strStatusValue[1], strPtStReasVal1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strShStReasVal1 = { strReasonVal[1], strReasonVal[2] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[1],
						strStatusName1, strStatusValue[0], strShStReasVal1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus = { strStatusName1, strStatusName2 };
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strUpdTime1 = "";
			String[] strArFunRes = new String[5];
			String strCurrYear = dts.getCurrentDate("yyyy");
			try {
				assertEquals("", strFuncResult);
				strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes[4];
				strCurrDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strUpdTime1 = strArFunRes[2] + ":" + strArFunRes[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Wait for 3 mins
				Thread.sleep(180000);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statShTypeName, strCSTApplTime,
						strCSTApplTime, false, strStatusValue[0],
						strShStReasVal1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes1 = new String[5];
			String strRepGenTime1 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes1 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes1[4];

				strRepGenTime1 = strArFunRes1[2] + ":" + strArFunRes1[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statTypeName, strCSTApplTime,
						strCSTApplTime, false, strStatusValue[1],
						strPtStReasVal1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes2 = new String[5];
			String strRepGenTime2 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes2 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes2[4];

				strRepGenTime2 = strArFunRes2[2] + ":" + strArFunRes2[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statShTypeName };
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
			String[] strPtStReasVal2 = { strReasonVal[1], strReasonVal[2] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statTypeName, strSTvalue[0],
						strStatusName2, strStatusValue[1], strPtStReasVal2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			String[] strShStReasVal2 = { strReasonVal[0], strReasonVal[1] };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiSTWithReason(selenium,
						strResource, statShTypeName, strSTvalue[1],
						strStatusName1, strStatusValue[0], strShStReasVal2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String[] strArStatus1 = { strStatusName1, strStatusName2 };
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.saveAndVerifyUpdtST(selenium,
						strResource, strArStatus1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			String strUpdTime2 = "";
			String[] strArFunRes3 = new String[5];

			try {
				assertEquals("", strFuncResult);
				strArFunRes3 = objViewMap.getUpdTimeValue(selenium, "1");
				strFuncResult = strArFunRes3[4];
				strCurrDate = dts.converDateFormat(strArFunRes3[0]
						+ strArFunRes3[1] + strCurrYear, "ddMMMyyyy",
						"MM/dd/yyyy");

				strUpdTime2 = strArFunRes3[2] + ":" + strArFunRes3[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReasonSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
						"M/d/yyyy");

				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep_SelAll(
						selenium, strRSValue, statShTypeName, strCSTApplTime,
						strCSTApplTime, false, strStatusValue[0],
						strShStReasVal2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath3 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strArFunRes4 = new String[5];
			String strRepGenTime3 = "";
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strArFunRes4 = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes4[4];

				strRepGenTime3 = strArFunRes4[2] + ":" + strArFunRes4[3];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strUsrPassword;
				strTestData[3] = strResType;
				strTestData[4] = strResource;
				strTestData[5] = statTypeName + "" + statShTypeName;
				strTestData[6] = strStatusName1 + "," + strStatusName2;
				strTestData[7] = "Check the Status Summary details in CSV file(Step 7): "
						+ strCSVDownlPath1
						+ ",Status Type: "
						+ statShTypeName
						+ ", Status updated: "
						+ strStatusName1
						+ ",Stauts Reason selected: "
						+ strReasonName2
						+ ","
						+ strReasonName3
						+ ", Status Update time: "
						+ strUpdTime1
						+ "Report Generation Time: "
						+ strRepGenTime1
						+ "Check the Status Summary details in CSV file(Step 11): "
						+ strCSVDownlPath2
						+ ",Status Type: "
						+ statTypeName
						+ ", Status updated: "
						+ strStatusName2
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ", Status Update time: "
						+ strUpdTime1
						+ "Report Generation Time: "
						+ strRepGenTime2
						+ "Check the Status Summary details in CSV file(Step 16): "
						+ strCSVDownlPath3
						+ ",Status Type: "
						+ statShTypeName
						+ ", Status updated: "
						+ strStatusName1
						+ ",Stauts Reason selected: "
						+ strReasonName1
						+ ","
						+ strReasonName2
						+ ", Status Update time: "
						+ strUpdTime2
						+ "Report Generation Time: "
						+ strRepGenTime3;

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103965";
			gstrTO = "Generate 'Status Reason Summary report' in CSV "
					+ "format and verify that the report displays"
					+ " correct data.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
}
