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

/********************************************************************
' Description		:This class contains test cases from  requirement
' Requirement		:Resource Search
' Requirement Group	:Setting up resources
' Product		    :EMResource v3.21
' Date			    :7/12/2013
' Author		    :QSG
' -------------------------------------------------------------------
' Modified Date				Modified By
' Date					Name
'*******************************************************************/

public class ResourceSearch {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ResourceSearch");
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
	Selenium selenium, seleniumPrecondition;
	String gstrTimeOut;

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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		selenium.close();
		selenium.stop();

		seleniumPrecondition.close();
		seleniumPrecondition.stop();
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
//start//testBQS122243//
	/*******************************************************************************************
	'Description	:Verify that user can search for sub resources providing multiple characters
	                 (parent resource along with sub resource) in the search criteria.
	'Arguments		:None
	'Returns		:None
	'Date			:7/12/2013
	'Author			:QSG
	'-------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*********************************************************************************************/

	@Test
	public void testBQS122243() throws Exception {
		
		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Preferences objPreferences = new Preferences();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral=new General();

		try {
			gstrTCID = "122243"; // Test Case Id
			gstrTO = " Verify that user can search for sub resources providing multiple characters " +
					"(parent resource along with sub resource) in the search criteria.";//TO																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strTmText = dts.getCurrentDate("HHmm");	
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String strSSTValue = "Saturation Score";
			
			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statsNumTypeName = "sNST" + strTimeText;
			String statrSaturatTypeName1 = "rSST" + strTimeText;			
			String statpMultiTypeName = "pMST" + strTimeText;
			
			String strStatusTypeValues[] = new String[6];
			String strStatusName1 = "S1_" + strTimeTxt;
			String strStatusName2 = "S2_" + strTimeTxt;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
		
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strSubResource2 = "AutoSRs2_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "A" + strTmText;
			String strRSValue[] = new String[4];
			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";
			String strState = "(Any)";
			
			// user
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
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
	/*
	* STEP :
	  Action:Precondition:
			1. Created status types 'NST', 'MST', 'SST' , 'pMST'(private), sNST(shared),rSST (role based),
			statuses 'S1' & 'S2' are created under 'MST' and 'pMST'.
			2. Resource type 'RT' is created selecting status types 'NST', 'MST' and 'SST'.
			3. Sub Resource Type 'SRT' is created selecting status types 'sNST', 'pMST' and 'rSST.
			4. Resource 'RS' is created under 'RT'
			5. Sub-resource 'SRS1' , 'SRS2' are created under parent resource 'RS' selecting 'SRT' sub-resource type.
			6. User 'U1' is created selecting 'View Resource' and 'Update Resource' right on resource 'RS'.
	  Expected Result:No Expected Result
	*/
	//658013
			
		log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

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

			// NST
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
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
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
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
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
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
           //sNST
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
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//pMST
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
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
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
						seleniumPrecondition, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
           
			//rSST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName1);
				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
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
			// RS
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
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[3] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,
						strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,
						strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SRS1
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource1, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//SRS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource2, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
		/*
		* STEP :
		  Action:Login as user 'U1'
		  Expected Result:'Region Default' screen is displayed.
		*/
		//658014
				try {
					assertEquals("", strFuncResult);
					blnLogin = false;
					strFuncResult = objLogin.newUsrLogin(selenium,
							strUserName_1, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
		/*
		* STEP :
		  Action:Click on 'Search' button at top right corner of the screen.
		  Expected Result:'Find Resources' screen is displayed.
	      'Search' and 'Cancel' buttons are displayed.
		*/
		//658015
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objPreferences
							.navToFindResPageBySearchLink(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					String strElementID = "css=input[value='Search']";
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElementID);

					strElementID = "css=input[value='Cancel']";
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElementID);

					if (strFuncResult.equals("")) {
						log4j.info("'Search' and 'Cancel' buttons are displayed.");
					} else {
						strFuncResult = "'Search' and 'Cancel' buttons are NOT displayed.  ";
						log4j.info("'Search' and 'Cancel' buttons are NOT displayed.");
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
		/*
		* STEP :
		  Action:Provide Resource name (RS) in search criteria , click on search.
		  Expected Result:Resource 'RS',Sub-resource 'SRS1' and 'SRS2' are retrieved.
			1. Resource 'RS' is displayed.
			2. Sub-resource SRS1 is displayed in format < Resource name:Sub-resource name > 
			3.  Sub-resource SRS2 is displayed in format < Resource name:Sub-resource name >
		*/
		//658016
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.RetriveResourceInAddToCustomView(selenium,
								strResource1, strCategory, strRegion,
								strCityZipCd, strState, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strResource1, 
						strResource1, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide 'Resource name  sub-resource' name in search criteria , click on search.
		  Expected Result:Sub-resource 'SRS1' and 'SRS2' are retrieved.
			Sub resources 'SRS1' and 'SRS2' are displayed in the format below
			< Resource name:Sub-resource name >
		*/
		//658017
				
			try {
				assertEquals("", strFuncResult);
				String strResource[]={strResource1+" AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Provide 'RS SRS1' in search criteria , click on search.
		  Expected Result:Only Sub-resource 'SRS1' is retrieved.
	     < Resource name:Sub-resource name > is displayed under 'Resource Name' column.
		*/
		//658018
			try {
				assertEquals("", strFuncResult);
				String strResource[]={strResource1+" "+strSubResource1};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, false, false);
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
			gstrTCID = "BQS-122243";
			gstrTO = "Verify that user can search for sub resources providing multiple characters (parent resource along with sub resource) in the search criteria.";
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
//end//testBQS122243//

	//start//testBQS107722//
	/***************************************************************
	'Description	:Verify that user with 'Override Viewing Restrictions' right on the parent resource can search for sub resources from the 'Find Resources' screen.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS107722() throws Exception {
		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Preferences objPreferences = new Preferences();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral=new General();

		try {
			gstrTCID = "107722"; // Test Case Id
			gstrTO = " Verify that user with 'Override Viewing Restrictions' right on the parent resource can search for sub resources from the 'Find Resources' screen.";// Test
																																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strTmText = dts.getCurrentDate("HHmm");	
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String strSSTValue = "Saturation Score";
			
			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statsNumTypeName = "sNST" + strTimeText;
			String statrSaturatTypeName1 = "rSST" + strTimeText;			
			String statpMultiTypeName = "pMST" + strTimeText;
			
			String strStatusTypeValues[] = new String[6];
			String strStatusName1 = "S1_" + strTimeTxt;
			String strStatusName2 = "S2_" + strTimeTxt;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
		
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strSubResource2 = "AutoSRs2_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "A" + strTmText;
			String strRSValue[] = new String[4];
			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";
			String strState = "(Any)";
			
			// user
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUserName_4 = "AutoUsr_4" + System.currentTimeMillis();
			String strUserName_5 = "AutoUsr_5" + System.currentTimeMillis();
			
			String strUsrFulName_1 = strUserName_1;
			String strUsrFulName_2 = strUserName_2;
			String strUsrFulName_3 = strUserName_3;
			String strUsrFulName_4 = strUserName_4;
			String strUsrFulName_5 = strUserName_5;
			
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
		
			/*
			 * STEP : Action:Precondition:
			 * 1. Created status types 'NST', 'MST', 'SST' , 'pMST'(private),
			 * sNST(shared),rSST (role based),statuses 'S1' & 'S2' are created
			 * under 'MST' and 'pMST'.
			 * 2. Resource type 'RT' is created selecting status types 'NST',
			 * 'MST' and 'SST'.
			 * 3. Sub Resource Type 'SRT' is created selecting status types
			 * 'sNST', 'pMST' and 'rSST.
			 * 4. Resource 'RS' is created under 'RT'
			 * 5. Sub-resource 'SRS1' , 'SRS2' are created under parent resource
			 * 'RS' selecting 'SRT' sub-resource type.
			 * 6. User 'U1' is created selecting 'Override Viewing Restrictions'right.
			 * 7. User 'U2' is created selecting 'View Resource' and 'Update
			 * Resource' right on resource 'RS'.
			 * 8. User 'U3' is created selecting 'View Resource' and 'Run
			 * report' right on resource 'RS'
			 * 9. User 'U4' is created selecting 'View Resource' and 'Associate'
			 * right on resource 'RS'
			 * 10. User 'U5' is created without selecting any right on resource
			 * 'RS' Expected Result:No Expected Result
			 */
			// 609650
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

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

			// NST
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
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
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
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
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
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
           //sNST
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
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//pMST
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
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
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
						seleniumPrecondition, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
           
			//rSST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName1);
				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
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
			// RS
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
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[3] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,
						strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,
						strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SRS1
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource1, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//SRS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource2, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER1
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, false, true);
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
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER2
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
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// USER3
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
						seleniumPrecondition, strUserName_3, strInitPwd,
						strConfirmPwd, strUsrFulName_3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_3, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER4
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
						seleniumPrecondition, strUserName_4, strInitPwd,
						strConfirmPwd, strUsrFulName_4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						true, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_4, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER5
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
						seleniumPrecondition, strUserName_5, strInitPwd,
						strConfirmPwd, strUsrFulName_5);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_5, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 609664
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Search' button at top right corner of the
			 * screen. Expected Result:'Find Resources' screen is displayed.
			 * 
			 * 'Search' and 'Cancel' buttons are displayed.
			 */
			// 609673
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Search']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Search' and 'Cancel' buttons are displayed.");
				} else {
					strFuncResult = "'Search' and 'Cancel' buttons are NOT displayed.  ";
					log4j.info("'Search' and 'Cancel' buttons are NOT displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource name in search criteria ,
			 * click on search. Expected Result:Sub-resource 'SRS1' and 'SRS2'
			 * are retrieved.
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609682
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U2' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609684
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource (as SR) name in search
			 * criteria , click on search. Expected Result:Sub-resource 'SRS1'
			 * and 'SRS2' are retrieved.
			 * 
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609685
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U3' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609688
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_3, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource (as SR) name in search
			 * criteria , click on search. Expected Result:Sub-resource 'SRS1'
			 * and 'SRS2' are retrieved.
			 * 
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609689
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U4' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609691
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_4, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource (as SR) name in search
			 * criteria , click on search. Expected Result:Sub-resource 'SRS1'
			 * and 'SRS2' are retrieved.
			 * 
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609692
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U5' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609693
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_5, strInitPwd);
				blnLogin = true;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource name in search criteria ,
			 * click on search. Expected Result:Sub-Resource is not retrieved.
			 * 
			 * 'No resources match your criteria...' message is displayed.
			 */
			// 609711
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesVarErrMsg(selenium, strSubResource1, strCategory, strCityZipCd, strState);
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
			gstrTCID = "BQS-107722";
			gstrTO = "Verify that user with 'Override Viewing Restrictions' right on the parent resource can search for sub resources from the 'Find Resources' screen.";
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

	//end//testBQS107722//

	//start//testBQS107724//
	/***************************************************************
	'Description	:Verify that user with any of the affiliated right on the parent resource can search for sub resources from the 'Find Resources' screen.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/16/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS107724() throws Exception {
		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		Preferences objPreferences = new Preferences();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral=new General();

		try {
			gstrTCID = "107724"; // Test Case Id
			gstrTO = " Verify that user with any of the affiliated right on the parent resource can search for sub resources from the 'Find Resources' screen.";// Test
																																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strTmText = dts.getCurrentDate("HHmm");	
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			// login
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strStatTypeColor = "Black";
			String strSSTValue = "Saturation Score";
			
			String statrNumTypeName = "NST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statsNumTypeName = "sNST" + strTimeText;
			String statrSaturatTypeName1 = "rSST" + strTimeText;			
			String statpMultiTypeName = "pMST" + strTimeText;
			
			String strStatusTypeValues[] = new String[6];
			String strStatusName1 = "S1_" + strTimeTxt;
			String strStatusName2 = "S2_" + strTimeTxt;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
		
			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];
			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strSubResource1 = "AutoSRs1_" + strTimeText;
			String strSubResource2 = "AutoSRs2_" + strTimeText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strAbbrv = "A" + strTmText;
			String strRSValue[] = new String[4];
			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";
			String strState = "(Any)";
			
			// user
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserName_2 = "AutoUsr_2" + System.currentTimeMillis();
			String strUserName_3 = "AutoUsr_3" + System.currentTimeMillis();
			String strUserName_4 = "AutoUsr_4" + System.currentTimeMillis();
			
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
		

			/*
			 * STEP : Action:Precondition:
			 * 
			 * 1. Created status types 'NST', 'MST', 'SST' , 'pMST'(private),
			 * sNST(shared),rSST (role based),statuses 'S1' & 'S2' are created
			 * under 'MST' and 'pMST'.
			 * 2. Resource type 'RT' is created selecting status types 'NST',
			 * 'MST' and 'SST'.
			 * 3. Sub Resource Type 'SRT' is created selecting status types
			 * 'sNST', 'pMST' and 'rSST.
			 * 4. Resource 'RS' is created under 'RT'
			 * 5. Sub-resource 'SRS1' , 'SRS2' are created under parent resource
			 * 'RS' selecting 'SRT' sub-resource type.
			 * 6. User 'U1' is created selecting 'View Resource' and 'Update
			 * Resource' right on resource 'RS'.
			 * 
			 * 7. User 'U2' is created selecting 'View Resource' and 'Run
			 * report' right on resource 'RS'
			 * 
			 * 8. User 'U3' is created selecting 'View Resource' and 'Associate'
			 * right on resource 'RS'
			 * 
			 * 9. User 'U4' is created without selecting any right on resource
			 * 'RS' Expected Result:No Expected Result
			 */
			// 609713
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

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

			// NST
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
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
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
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (strStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, statrMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statrMultiTypeName,
								strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
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
				strStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (strStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
           //sNST
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
				strStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statsNumTypeName);
				if (strStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//pMST
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
				strStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
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
						seleniumPrecondition, statpMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition, statpMultiTypeName,
								strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
           
			//rSST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statrSaturatTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName1);
				if (strStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
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
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
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
			// RS
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
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[3] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,
						strStatusTypeValues[4], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSTInEditRT(seleniumPrecondition,
						strStatusTypeValues[5], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strSubResrctTypName);
				if (strFuncResult.compareTo("") != 0) {

					strRTValue[1] = strFuncResult;
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SRS1
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource1, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//SRS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(seleniumPrecondition,
						strSubResource2, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2,strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER1
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
						seleniumPrecondition, strUserName_1, strInitPwd,
						strConfirmPwd, strUsrFulName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER2
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
						seleniumPrecondition, strUserName_2, strInitPwd,
						strConfirmPwd, strUsrFulName_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_2, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// USER3
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
						seleniumPrecondition, strUserName_3, strInitPwd,
						strConfirmPwd, strUsrFulName_3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						true, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_3, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// USER4
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
						seleniumPrecondition, strUserName_4, strInitPwd,
						strConfirmPwd, strUsrFulName_4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_4, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
				blnLogin = false;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION" + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TESTCASE" + gstrTCID + " EXECUTION ENDS~~~~~");
			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 609714
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Search' button at top right corner of the
			 * screen. Expected Result:'Find Resources' screen is displayed.
			 * 
			 * 'Search' and 'Cancel' buttons are displayed.
			 */
			// 609715
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Search']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Search' and 'Cancel' buttons are displayed.");
				} else {
					strFuncResult = "'Search' and 'Cancel' buttons are NOT displayed.  ";
					log4j.info("'Search' and 'Cancel' buttons are NOT displayed.");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource name in search criteria ,
			 * click on search. Expected Result:Sub-resource 'SRS1' and 'SRS2'
			 * are retrieved.
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609716
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U2' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609717
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource (as SR) name in search
			 * criteria , click on search. Expected Result:Sub-resource 'SRS1'
			 * and 'SRS2' are retrieved.
			 * 
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609718
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U3' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609719
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_3, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource (as SR) name in search
			 * criteria , click on search. Expected Result:Sub-resource 'SRS1'
			 * and 'SRS2' are retrieved.
			 * 
			 * < Resource name:Sub-resource name > is displayed under 'Resource
			 * Name' column.
			 */
			// 609720
			try {
				assertEquals("", strFuncResult);
				String strResource[]={"AutoSRs"};
				strFuncResult = objPreferences.findResources(selenium,
						strResource, strCategory, strRegion, strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource1;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource1, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResource=strResource1+": "+strSubResource2;
				strFuncResult = objPreferences.verifyResourceInFindResPage(selenium, strSubResource2, 
						strResource, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as user 'U4' , Click on 'Search' button at
			 * top right corner of the screen. Expected Result:'Find Resources'
			 * screen is displayed.
			 */
			// 609723
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName_4, strInitPwd);
				blnLogin = true;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navToFindResPageBySearchLink(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Provide sub-resource name in search criteria ,
			 * click on search. Expected Result:Sub-Resource is not retrieved.
			 * 
			 * 'No resources match your criteria...' message is displayed.
			 */
			// 609724
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesVarErrMsg(selenium, strSubResource1, strCategory, strCityZipCd, strState);
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
			gstrTCID = "BQS-107724";
			gstrTO = "Verify that user with any of the affiliated right on the parent resource can search for sub resources from the 'Find Resources' screen.";
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

	//end//testBQS107724//
}

