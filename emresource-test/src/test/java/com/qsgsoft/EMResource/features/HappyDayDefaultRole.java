package com.qsgsoft.EMResource.features;
import static org.junit.Assert.*;

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

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement Group	:Creating & manage roles  
' Requirement 	    :Forgot User ID / Password
� Product		    :EMResource v3.24
' Date			    :15/Nov/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class HappyDayDefaultRole {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappyDayDefaultRole");
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
	String gstrTimeOut = "";
	Selenium selenium, seleniumPrecondition;

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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));
		selenium.start();
		selenium.windowMaximize();
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
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {
		}
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
	
	//start//testBQS127945//
	/***************************************************************
	'Description		:Verify that when a user is provided access to multiple regions, user will be provided with the default role set in that region and has the default viewing rights for the resources added in region's default view when the user is opened for editing
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11/15/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testBQS127945() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		General objMail = new General();// object of class General.
		CreateUsers objCreateUsers = new CreateUsers();// object of class CreateUsers.
		Regions objRegion = new Regions();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		try {
			gstrTCID = "127945"; // Test Case Id
			gstrTO = " Verify that when a user is provided access to multiple regions, "
					+ "user will be provided with the default role set in that region and has "
					+ "the default viewing rights for the resources added in region's default"
					+ " view when the user is opened for editing";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 3, 5);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// User
			String strUserName = "AutoUsr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName;
			// Status Types for region 2
			String strStatTypDefn = "Automation";
			String strStatTypeColor = "Black";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNEDOCValue = "NEDOCS Calculation";
			String statsNumTypeName = "NST" + strTimeText;
			String ststNumaType = "NSTa" + strTimeText;
			String ststNumbType = "NSTb" + strTimeText;
			String statsMultiTypeName = "MST" + strTimeText;
			String statsTextTypeName = "TST" + strTimeText;
			String statsSaturtnTypeName = "SST" + strTimeText;
			String statsNedocTypeName = "NED" + strTimeText;
			String strStatusName1 = "sSa" + strTimeText;
			String strStatusName2 = "sSb" + strTimeText;
			String strSTvalue[] = new String[7];
			String strStstusValue[] = new String[2];
			// Resource for Region 2
			String strResourceA = "AutoRs_A" + strTimeText;
			String strResourceC = "AutoRs_C" + strTimeText;
			String strResourceD = "AutoRs_D" + strTimeText;
			String strResourceB = "AutoRs_B" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[4];
			String strRightName = "viewRight";
			// ResourceType for Region 2
			String strRestyA = "AutoRstA" + strTimeText;
			String strRestyB = "AutoRstB" + strTimeText;
			String StrRstyValue[] = new String[2];
			// Role for Region 2
			String strRolA = "Rol2" + strTimeText;
			String strRolValue = "";
			String strRegionValue2 = "";
			// Status Type for Region 1
			String statsNumName = "AutoNst" + strTimeText;
			String strSTvalue1 = "";
			// ResourceType for Region 1
			String strRestyC = "AutoRstC" + strTimeText;
			String StrRstyValue1 = "";
			// Resource for Region 1
			String strResource = "AutoRs" + strTimeText;
			String strRSValue1 = "";
			// Role for Region 1
			String strRolB = "Rol1" + strTimeText;
			String strRolValue1 = "";

			/*
			 * STEP : Action:User U1 has access to RG1
			 * 
			 * in region RG2, default role is set. Region default is configured
			 * Expected Result:No Expected Result
			 */
			// 664523

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetch region values
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegion.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValue2 = objRegion.fetchRegionValue(
						seleniumPrecondition, strRegn2);
				if (strRegionValue2.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch region value";
				}
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
						seleniumPrecondition, strNSTValue, statsNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsNumTypeName);
				if (strSTvalue[0].compareTo("") != 0) {
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
						seleniumPrecondition, strMSTValue, statsMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsMultiTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
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
						seleniumPrecondition, statsMultiTypeName,
						strStatusName1, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statsMultiTypeName,
						strStatusName2, strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStstusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statsMultiTypeName,
						strStatusName1);
				if (strStstusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStstusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statsMultiTypeName,
						strStatusName2);
				if (strStstusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statsTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsTextTypeName);
				if (strSTvalue[2].compareTo("") != 0) {
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
						seleniumPrecondition, strSSTValue,
						statsSaturtnTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsSaturtnTypeName);
				if (strSTvalue[3].compareTo("") != 0) {
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
						seleniumPrecondition, strNEDOCValue,
						statsNedocTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[4] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsNedocTypeName);
				if (strSTvalue[4].compareTo("") != 0) {
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
						seleniumPrecondition, strNSTValue, ststNumaType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[5] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, ststNumaType);
				if (strSTvalue[5].compareTo("") != 0) {
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
						seleniumPrecondition, strNSTValue, ststNumbType,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[6] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, ststNumbType);
				if (strSTvalue[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strRestyA, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
						seleniumPrecondition, strSTvalue[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strRestyA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				StrRstyValue[0] = objResourceTypes.fetchRTValueInRTList(
						seleniumPrecondition, strRestyA);

				if (StrRstyValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strRestyB, strSTvalue[5]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strRestyB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				StrRstyValue[1] = objResourceTypes.fetchRTValueInRTList(
						seleniumPrecondition, strRestyB);

				if (StrRstyValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
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
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" }, { strSTvalue[4], "true" },
						{ strSTvalue[6], "true" } };

				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTUpdateValue,
						strSTUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRolValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolA);
				if (strRolValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.setRoleAsDefaultRole(
						seleniumPrecondition, strRolA);
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
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResourceA, strAbbrv,
						strRestyA, strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResourceA);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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
						seleniumPrecondition, strResourceB, strAbbrv,
						strRestyA, strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResourceB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResourceB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResourceB);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResourceB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(seleniumPrecondition);
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
						seleniumPrecondition, strResourceC, strAbbrv,
						strRestyB, strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResourceC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResourceC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue[2] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResourceC);
				if (strRSValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResourceC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(
						seleniumPrecondition, strSTvalue[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(seleniumPrecondition);
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
						seleniumPrecondition, strResourceD, strAbbrv,
						strRestyB, strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResourceD);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResourceD);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[3] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResourceD);
				if (strRSValue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition,
						strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[0], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[3], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[4], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[5], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue[6], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, StrRstyValue[0], true, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, StrRstyValue[1], true, true);

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
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn1);
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
						seleniumPrecondition, strNSTValue, statsNumName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue1 = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsNumName);
				if (strSTvalue1.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strRestyC, strSTvalue1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strRestyC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				StrRstyValue1 = objResourceTypes.fetchRTValueInRTList(
						seleniumPrecondition, strRestyC);

				if (StrRstyValue1.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Resource type value";
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
				strFuncResult = objRs
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv, strRestyC,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRSValue1 = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strRSValue1.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
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
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTUpdateValue = { { strSTvalue1, "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTUpdateValue,
						strSTUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRolValue1 = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolB);
				if (strRolValue1.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User
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
						.navToCreateUserPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillOnlyCreateUsrMandatFlds(
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRolValue1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRights(
						seleniumPrecondition, strResource, false, false, false,
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
				String[] strRegionValues = { strRegionValue2 };
				strFuncResult = objRegion.acessToRegnForUserWithRegionValue(
						seleniumPrecondition, strUserName, strRegionValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strViewName = "Region Default (default)";
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition,
						strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselStatTypeInEditView(
						seleniumPrecondition, strSTvalue1, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, StrRstyValue1, true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Provide access to region RG2. Expected Result:User
			 * will be provided with the default role set in RG2
			 * 
			 * Resources added in the region default view can be viewed
			 */
			// 664524

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
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
				String[] strStatType = { statsMultiTypeName,
						statsNedocTypeName, statsNumTypeName, ststNumbType,
						statsSaturtnTypeName, statsTextTypeName };
				String[] strResource1 = { strResourceA, strResourceB };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strRestyA, strResource1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceA + "']/parent::td/following::td[1]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated  with "
						+ statsMultiTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceA + "']/parent::td/following::td[2]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsNedocTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceA + "']/parent::td/following::td[3]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsNumTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "N/A";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceA + "']/parent::td/following::td[4]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with " + ststNumbType
						+ " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceA + "']/parent::td/following::td[5]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsSaturtnTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceA + "']/parent::td/following::td[6]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsTextTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceB + "']/parent::td/following::td[1]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated  with "
						+ statsMultiTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceB + "']/parent::td/following::td[2]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsNedocTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceB + "']/parent::td/following::td[3]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsNumTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceB + "']/parent::td/following::td[4]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with " + ststNumbType
						+ " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceB + "']/parent::td/following::td[5]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsSaturtnTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceB + "']/parent::td/following::td[6]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsTextTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statsMultiTypeName,
						statsNedocTypeName, statsNumTypeName,
						statsSaturtnTypeName, statsTextTypeName };
				String[] strResource1 = { strResourceC};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strRestyB, strResource1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceC + "']/parent::td/following::td[1]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated  with "
						+ statsMultiTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceC + "']/parent::td/following::td[2]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsNedocTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceC + "']/parent::td/following::td[3]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsNumTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceC + "']/parent::td/following::td[4]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsSaturtnTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strText = "--";
				String strElementID = "//table/tbody/tr/td[2]/a[text()='"
						+ strResourceC + "']/parent::td/following::td[5]";
				strFuncResult = objMail.assertEQUALS(selenium, strElementID,
						strText);
				log4j.info(strResourceA + " associated with "
						+ statsTextTypeName + " displays " + strText);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRS = {strResourceD};
				String strUserView = "";
				strFuncResult = objViews.checkResTypeAndResInUserViewFalseCond(
						selenium, strUserView, strRestyB, strRS,
						StrRstyValue[1]);
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
			 * STEP : Action:Login as system admin to RG2, navigate to setup >
			 * users and open user for editing Expected Result:Default role
			 * checkbox set in RG2 is selected.
			 * 
			 * Checkbox corresponding to resources added in the region's RG2
			 * default view are selected.
			 */
			// 664525

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User
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
				strFuncResult = objCreateUsers.chkRoleSelectedOrNot(selenium,
						true, strRolValue, strRolA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyViewResourceRight(
						selenium, strRightName, strRSValue[0], true);
				log4j.info("Check box corresponding to region " + strResourceA
						+ "is checked");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyViewResourceRight(
						selenium, strRightName, strRSValue[1], true);
				log4j.info("Check box corresponding to region " + strResourceB
						+ "is checked");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyViewResourceRight(
						selenium, strRightName, strRSValue[2], true);
				log4j.info("Check box corresponding to region " + strResourceC
						+ "is checked");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyViewResourceRight(
						selenium, strRightName, strRSValue[3], true);
				log4j.info("Check box corresponding to region " + strResourceD
						+ "is checked");

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
			 * STEP : Action:Navigate to Setup > Resources, click on 'User' link
			 * corresponding to the resource added in Region default view
			 * Expected Result:User U1 checkbox will be selected
			 */
			// 664526

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkUserRightsInAssignUserPage(selenium,
						strUserName, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResourceB);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkUserRightsInAssignUserPage(selenium,
						strUserName, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResourceC);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkUserRightsInAssignUserPage(selenium,
						strUserName, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToAssignUsersOFRes(selenium,
						strResourceD);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.checkUserRightsInAssignUserPage(selenium,
						strUserName, false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(selenium);
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
			gstrTCID = "BQS-127945";
			gstrTO = "Verify that when a user is provided access to multiple regions, user will be provided with the default role set in that region and has the default viewing rights for the resources added in region's default view when the user is opened for editing";
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

	// end//testBQS127945//

}
