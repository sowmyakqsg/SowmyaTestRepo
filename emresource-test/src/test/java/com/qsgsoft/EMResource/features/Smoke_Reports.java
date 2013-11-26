package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.CSVFunctions;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.GetProcessList;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

public class Smoke_Reports {
  
	Date dtStartDate;      
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.Smoke_Reports");
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
		
		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		selenium.start();
		selenium.windowMaximize();

		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		

	
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}


	@After
	public void tearDown() throws Exception {
		
		try{
			selenium.close();
		}catch(Exception e){
			
		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
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
	
	
	
	
	/*******************************************************************************************
	'Description	:Verify that a 'Event Snapshot' report can be generated.
	'Precondition	:1. Status Type NST, MST, TST and SST are created.
					2.Resource Type 'RT' is associated with status types NST,MST,TST and SST.
					2. Resources RS is created with address under RT.
					3. Event template ET1 is created selecting resource type RT and status types NST, MST, TST, SST
					4. Event E1 is created under ET1 selecting resource RS on day D1.
					5. User U1 has following rights:
					a. Report - Event Snapshot
					b. Role to update status types NST,MST,TST and SST
					c. 'View Resource','Update' and 'Run Report' rights on resources RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke118735() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		General objGeneral=new General();
		EventSetup objEventSetup=new EventSetup();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "118735"; 
			gstrTO = "Verify that a 'Event Snapshot' report can be generated.";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
	
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strApplTime = "";

			String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			
			//Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
				
			String statNumTypeName="NST"+strTimeText;
			String statTextTypeName="TST"+strTimeText;
			String statMultiTypeName="MST"+strTimeText;
			String statSaturatTypeName="SST"+strTimeText;
			
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";
			
			String strStatTypeColor="Black";
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			
			//General variable 
			String strStatValue = "";
		
			String strSTvalue[]=new String[4];
			String strRSValue[]=new String[1];
			
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue="";

			String strResource ="AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserNameUpdate = "AutoUsr_Update" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strUsrFulNameAny = strUserNameUpdate;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			
			String strTempName="ET"+System.currentTimeMillis();
			String strTempDef="Automation";
			
			String strEveName="Event"+System.currentTimeMillis();
			String strInfo="Automation";
			String strETValue = "";
			
			String strHr = "";
			String strEventValue = "";
			String strMin = "";

			String strLastUpdArr[]={};
			String strUpdateDataHr="";
			String strUpdateDataMin="";
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xlsx";
			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventSnapshot_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "EventSnapshot_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strTimeReport="";
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Creating TST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Creating SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Status Type Multi status type is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName1);
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName2);
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
				
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Role-based Status Types NST1(number), MST1(multi), TST1(text)
			 * and SST1(saturation score) are created with a role R1 to view and
			 * update these status types.
			 */
			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = {strSTvalue[0],strSTvalue[1],strSTvalue[2],strSTvalue[3]};
				String[] updateRightValue =  {strSTvalue[0],strSTvalue[1],strSTvalue[2],strSTvalue[3]};
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, true);
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
			
			//1. Resource type RT
			
			
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
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[3] + "']");
				
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
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition, strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*2. Resources RS is created under RT. */
			
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
			
			/*4. Event template ET1 is created selecting resource type RT and status types NST, MST, TST, SST*/


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
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
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

			/*6. User U1 has following rights:

				a. Report - Event Snapshot
				b. No role is associated with User U1
				c. 'View Status' and 'Run Report' rights on resources RS. */
			
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventSnapShotReprt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Any User
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameUpdate,
								strInitPwd, strConfirmPwd, strUsrFulNameAny);

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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameUpdate, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				blnLogin=false;
				strFuncResult=objLogin.newUsrLogin(selenium, strUserNameUpdate, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*7. Status Type NST, MST, TST and SST are updated on day D1 time hour H1 for resource RS. 		No */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName, };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			// Update Number Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.updateNumStatusType(selenium, strResource,
								statNumTypeName, strSTvalue[0], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			// Update Text Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.updateNumStatusType(selenium, strResource,
								statTextTypeName, strSTvalue[1], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			
			// Update Saturation Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objStatusTypes.updateSatuScoreStatusType(
						selenium, strResource, statSaturatTypeName,
						strSTvalue[2], strUpdateValue1, strUpdateValue2, "393",
						"429");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			// Update Multi Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.updateMultiStatusType_ChangeVal(
						selenium, strResource, statMultiTypeName,
						strSTvalue[3], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objViewMap.navResPopupWindowNew(selenium, strResource);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strLastUpdArr =  selenium.getText(
						"//span[text()='"+strStatusName1+"']" +
						"/following-sibling::span[@class='time'][1]").split(" ");
				
				strLastUpdArr=strLastUpdArr[2].split(":");
			    strUpdateDataHr=strLastUpdArr[0];
			    String str[]=strLastUpdArr[1].split("\\)");
			    strLastUpdArr[1]=str[0]; 
			    strUpdateDataMin=strLastUpdArr[1];
			    
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				
				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				
				strFuncResult=objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
	
				strFuncResult=objRep.navToEventSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
	
				strFuncResult = objRep.selectHtmlOrExcelformatInEventSnapShotReport(
						selenium, strCSTApplTime, strCSTApplTime, strETValue,true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				
				strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "m", "mm");
				strLastUpdArr[0]=dts.converDateFormat(strLastUpdArr[0], "H", "HH");
				

				String strTime = selenium.getText("//div[@id='mainContainer']"
						+ "/form/table/tbody/tr/td/table/tbody/tr/td[2]");
				strHr = strTime.substring(11, 13);
				System.out.println(strHr);

				strMin = strTime.substring(14, 16);
				System.out.println(strMin);

				strTimeReport = dts.converDateFormat(strHr + ":" + strMin,
						"HH:mm", "H:mm");
				System.out.println(strTimeReport);
				
				String str=strLastUpdArr[0]+":"+strLastUpdArr[1];
				str=dts.addTimetoExisting(str, 1, "HH:mm");
				strLastUpdArr=str.split(":");
				
				
				strFuncResult = objRep.enterEvntSnapshotGenerateReport(
						selenium, strCSTApplTime, strEventValue, strLastUpdArr[0], strLastUpdArr[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				
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
				} while (strProcess.contains(strAutoFileName) && intCnt< Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			int intCount = 0;
			int intCount1=0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());
					Thread.sleep(10000);
					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);
			
			try {
				assertEquals("", strFuncResult);

				String strTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"dd MMM yyyy");
				String strFutureDate = dts.DaytoExistingDate(strTime, 1,
						"dd MMM yyyy");
				strApplTime = dts.converDateFormat(strApplTime, "M/d/yyyy",
						"M/d/yyyy");
				
				strLastUpdArr[0]=dts.converDateFormat(strLastUpdArr[0], "HH", "H");
				/*strLastUpdArr[1]=dts.converDateFormat(strLastUpdArr[1], "mm", "m");*/
				
				String str = "Event Snapshot for " + strEveName + " - "
						+ strApplTime + " " + strLastUpdArr[0] + ":"
						+ strLastUpdArr[1]
						+ " Central Standard Time. Event Start:" + " "
						+ strTime + " " + strHr + ":" + strMin
						+ ". Event End: " + strFutureDate + "" + " " + strHr
						+ ":" + strMin + ". ";

				String[][] strTestData = {
						{ str, "", "", "", "", "", "", "", "" },
						{ "Resource Type", "Resource", statMultiTypeName,
								statNumTypeName, statSaturatTypeName,
								statTextTypeName, "Comment", "Last Update",
								"By User" },
						{
								strResrctTypName,
								strResource,
								strStatusName1,
								"1",
								"393",
								"1",
								"",
								strTime + " " + strUpdateDataHr + ":"
										+ strUpdateDataMin, strUserNameUpdate } };

				strFuncResult = OFC.readAndVerifyDataExcel(strTestData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+ strFuncResult;

			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "118735";
			gstrTO = "Verify that a 'Event Snapshot' report can be generated.";
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

	/*******************************************************************************************
	'Description	:Verify that a 'Status Reason Detail' report can be generated
	'Precondition	:1. Resource type RT is associated with status type ST1.

					2. Private status type pMST(multi status type) is created selecting status reason SR1
					
					3. Resources RS are created under RT.
					
					4. Private status Type pMST is added for resource RS at the resource level.
					
					5. User U1 has following rights:
					
					a. Report - Status Reason Detail Summary.
					b. Role to View status type pMST.
					c.'View Status' and 'Run Report' rights on resources RS.
					
					6.Private status Type pMST is updated with status reason on day D1.  
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Feb-2013                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke91289() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();

		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();

		try {
			gstrTCID = "91289"; // Test Case Id
			gstrTO = "Verify that a 'Status Reason Detail' report can be generated";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTestData[] = new String[10];
			CSVFunctions objCSV = new CSVFunctions();

			General objGen = new General();
			StatusReason objStatRes = new StatusReason();
			StatusTypes objST = new StatusTypes();

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strUpdMulValue = "";
			String strUpdValCheck = "";
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);


			String strUpdUsrName = "AutoUpdUsr" + System.currentTimeMillis();
			;
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

			String strGenDate = "";
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatReasDetRep_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strReasonName = "AutoReas_" + System.currentTimeMillis();
			String strReasonVal = "";
			String strRegGenTime = "";
			String strCurYear = dts.getCurrentDate("yyyy");
			String strLastUpdDate = "";
			String strLastUpdTime = "";

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
						strAdmPassword);
				blnLogin = true;
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

			// private ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(seleniumPrecondition,
						false, false, true);
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

					// Update user
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
								seleniumPrecondition, strUpdUsrName, strUpdPwd, strUpdPwd,
								strUsrFulName);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.selectResourceRights(seleniumPrecondition, strResource,
										false, true, false, true);

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
						strFuncResult = objCreateUsers
								.selectResourceRights(seleniumPrecondition, strResource,
										false, false, true, true);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers
								.advancedOptns(
										seleniumPrecondition,
										propElementDetails
												.getProperty("CreateNewUsr.AdvOptn.RepStatReasonDetail"),
										true);

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
						String[] strArFunRes = new String[5];
						strArFunRes = objViewMap.getUpdTimeValue(selenium, "1");

						strLastUpdTime = strArFunRes[2] + ":" + strArFunRes[3];

						strLastUpdDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strFuncResult = strArFunRes[4];

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

					log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
							+ " EXECUTION ENDS~~~~~");

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					log4j.info("~~~~~TEST CASE - " + gstrTCID
							+ " EXECUTION STARTS~~~~~");

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
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
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
								&& intCnt <  Integer.parseInt(propEnvDetails
										.getProperty("WaitForReport")));

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
								.navToStatusReasonDetail(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);

						strFuncResult = objRep
								.enterRepDetailsAndGenStatusReasonDetRep(
										selenium, strRSValue[0], statTypeName,
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
								&& intCnt <  Integer.parseInt(propEnvDetails
										.getProperty("WaitForReport")));

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						selenium.selectWindow("");
						String[] strArFunRes = new String[5];
						strArFunRes = objGen.getSnapTime(selenium);
						strFuncResult = strArFunRes[4];
						strGenDate = dts.converDateFormat(strArFunRes[0]
								+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
								"dd-MMM-yyyy");
						strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}


					try {
						assertEquals("", strFuncResult);
						String[][] strReportData = {
								{ "Resource", "Status", "Status Reason",
										"Status Start Date", "Status End Date",
										"Duration ", "User", "IP", "Trace",
										"Comments" },

								{ strResource, strUpdMulValue, strReasonName,
										strLastUpdDate + " " + strLastUpdTime,
										strGenDate + " " + strRegGenTime,
										"\\d+\\.\\d+", strUpdUsrName,
										propEnvDetails.getProperty("ExternalIP"), "**", strReasonName + " " }, };

						strFuncResult = objCSV.ReadFromCSV(strCSVDownlPath,
								strReportData);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						gstrResult = "PASS";
						// Write result data
						// Write result data
						strTestData[0] = propEnvDetails.getProperty("Build");
						strTestData[1] = gstrTCID;
						strTestData[2] = strUserName + "/" + strUsrPassword;
						strTestData[3] = strResType;
						strTestData[4] = strResource;
						strTestData[5] = statTypeName;
						strTestData[6] = strUpdMulValue;
						strTestData[5] = "Check the Status Reason details in PDF file: "
								+ strPDFDownlPath
								+ "Start Date: "
								+ strLastUpdDate
								+ " "
								+ strLastUpdTime
								+ "End Date: "
								+ strGenDate
								+ " "
								+ strRegGenTime
								+ "Status Reason: "
								+ strReasonName + "IP: " + propEnvDetails.getProperty("ExternalIP");

						String strWriteFilePath = pathProps
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
				gstrReason = gstrReason+ strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91289";
			gstrTO = "Verify that a 'Status Reason Detail' report can be generated";
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
	/***************************************************************
	'Description		:Verify that a 'Status Snapshot' report can be generated
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10-09-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'26-Feb-2013					Name
	***************************************************************/
	@Test
	public void testSmoke91306() throws Exception {
		try {
			gstrTCID = "BQS-91306"; // Test Case Id
			gstrTO = "Verify that a 'Status Snapshot' report can be generated";
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			General objGen = new General();
			Paths_Properties objAP = new Paths_Properties();
			Properties pathProps = objAP.Read_FilePath();

			StatusTypes objST = new StatusTypes();
			ResourceTypes objRT = new ResourceTypes();
			Resources objRs = new Resources();
			CreateUsers objCreateUsers = new CreateUsers();
			Login objLogin = new Login();// object of class Login
			Roles objRole = new Roles();

			Reports objRep = new Reports();
			Views objViews = new Views();
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();

			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strAbbrv = "Rs";

			String strLastUpdMST = "";
			String strLastUpdNST = "";
			String strLastUpdSST = "";
			String strLastUpdTST = "";

			String strUpdMulValue = "";
			String strUpdNumValue = "";
			String strUpdSatuValue = "";
			String strUpdTxtValue = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strFuncResult = "";

			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strResType = "AutoRt_" + strTimeText;

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strUpdatNumValue1 = "2";

			String strUpdatTxtValue1 = "s";

			String strUpdSatuValue1 = "393";

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_" + gstrTCID + "_" + strTimeText + ".xlsX";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSnapRep_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatSnapRep_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCurYear = dts.getCurrentDate("yyyy");
			String strGenDate = "";
			String strRegGenTime = "";

			String strGenTimeHrs = "";
			String strGenTimeMin = "";

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			
			String strUsrFulName = "autouser";
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUsrPassword = "abc123";

			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strMulStatTypeValue = "Multi";
			String strMulTypeName = "AutoMSt_" + strTimeText;
			String strMulStatTypDefn = "Auto";
			String strMulStatTypeColor = "Black";

			String strNumStatTypeValue = "Number";
			String strNumTypeName = "AutoNSt_" + strTimeText;
			String strNumStatTypDefn = "Auto";

			String strTxtStatTypeValue = "Text";
			String strTxtTypeName = "AutoTSt_" + strTimeText;
			String strTxtStatTypDefn = "Auto";

			String strSatuStatTypeValue = "Saturation Score";
			String strSatuTypeName = "AutoSSt_" + strTimeText;
			String strSatuStatTypDefn = "Auto";

			String strViewName = "AutoV_" + strTimeText;

			String strVewDescription = "";
			String strViewType = "Resource (Resources and status types as rows. Status, comments and timestamps as columns.)";

			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

			// Multi ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strMulStatTypeValue, strMulTypeName, strMulStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strMulTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch multi status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMulTypeName, strStatusName1, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strMulTypeName, strStatusName2, strMulStatTypeValue,
						strMulStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMulTypeName, strStatusName1);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strMulTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strNumTypeName, strNumStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetchNumber status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Score ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strSatuStatTypeValue, strSatuTypeName,
						strSatuStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strSatuTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Saturation score status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strTxtStatTypeValue, strTxtTypeName, strTxtStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strTxtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch Text status type value";
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

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
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
										.getProperty("CreateNewUsr.AdvOptn.RepSnapShot"),
								true);

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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
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
				strFuncResult = objViews.navToUserView(selenium, strViewName);

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
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[0], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdatNumValue1, strSTvalue[1], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[2]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdatTxtValue1, strSTvalue[3], false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveAndNavToViewScreen(selenium,
						strViewName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateStatusType(selenium,
						strResType, strMulTypeName, strStatusName1);
				strUpdMulValue = strStatusName1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateStatusType(selenium,
						strResType, strNumTypeName, strUpdatNumValue1);
				strUpdNumValue = strUpdatNumValue1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateStatusType(selenium,
						strResType, strSatuTypeName, strUpdSatuValue1);
				strUpdSatuValue = strUpdSatuValue1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateStatusType(selenium,
						strResType, strTxtTypeName, strUpdatTxtValue1);
				strUpdTxtValue = strUpdatTxtValue1;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViews.getLastUpdTimeValue(selenium,
						strResType, strMulTypeName);
				strFuncResult = strArFunRes[4];
				strLastUpdMST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
				;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViews.getLastUpdTimeValue(selenium,
						strResType, strNumTypeName);
				strFuncResult = strArFunRes[4];
				strLastUpdNST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
				;
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViews.getLastUpdTimeValue(selenium,
						strResType, strSatuTypeName);
				strFuncResult = strArFunRes[4];
				strLastUpdSST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViews.getLastUpdTimeValue(selenium,
						strResType, strTxtTypeName);
				strFuncResult = strArFunRes[4];
				strLastUpdTST = strArFunRes[0] + " " + strArFunRes[1] + " "
						+ strCurYear + " " + strArFunRes[2] + ":"
						+ strArFunRes[3];
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
				// Wait for 2 minutes
				Thread.sleep(120000);
				strFuncResult = objRep.navToStatusSnapshotReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String[] strArFunRes = new String[5];
				strArFunRes = objGen.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];

				strGenTimeHrs = strArFunRes[2];
				strGenTimeMin = strArFunRes[3];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterStatSnapshotRepAndGenRep(selenium,
						strCSTApplTime, strGenTimeHrs, strGenTimeMin);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{
								"Status Snapshot - " + strGenDate + " "
										+ strRegGenTime + " "+propEnvDetails.getProperty("TimeZone"), "", "", "",
								"", "" },
						{ "Resource Type", "Resource", "Status Type", "Status",
								"Comments", "Last Update" },
						{ strResType, strResource, strMulTypeName,
								strUpdMulValue, "", strLastUpdMST },
						{ strResType, strResource, strNumTypeName,
								strUpdNumValue, "", strLastUpdNST },
						{ strResType, strResource, strSatuTypeName,
								strUpdSatuValue, "", strLastUpdSST },
						{ strResType, strResource, strTxtTypeName,
								strUpdTxtValue, "", strLastUpdTST } };
				strFuncResult = objOFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+ strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-91306";
			gstrTO = "Verify that a 'Status Snapshot' report can be generated";
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
	
	/***************************************************************
	'Description		:Verify that a 'Status Summary' report can be generated
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:11-09-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'26-Feb-2013					Name
	***************************************************************/
	@Test
	public void testSmoke91256() throws Exception {
		try {
			gstrTCID = "BQS-91256"; // Test Case Id
			gstrTO = "Verify that a 'Status Summary' report can be generated";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			String strFILE_PATH = pathProps.getProperty("TestData_path");
					
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();
			String strTestData[]=new String[10];
			CSVFunctions objCSV=new CSVFunctions();
	
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			Roles objRole=new Roles();
			
			String strFuncResult="";
		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
			String statTypeName="AutoMSt_"+strTimeText;
			String strPStatType="AutopMSt_"+strTimeText;
			
			String strStatTypDefn="Auto";
			String strStatTypeColor="Black";
			
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
						
			String strUsrFulName="autouser";
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
				
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strStatusName3="Stc"+strTimeText;
			String strStatusName4="Std"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[2];
			String strRSValue[]=new String[1];
		
			String strPStatusValue[]=new String[2];
			strPStatusValue[0]="";
			strPStatusValue[1]="";
			
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			strSTvalue[0]="";			
			String strAutoFilePath=propElementAutoItDetails.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatSummary_"+gstrTCID+"_"+strTimeText+".csv";
			String strPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatSummary_"+gstrTCID+"_"+strTimeText+".pdf";
			String strAutoFileName=propElementAutoItDetails.getProperty("Reports_DownloadFile_Name");
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
		
			String strPCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"PrStatSummary_"+gstrTCID+"_"+strTimeText+".csv";
			String strPrPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"PrStatSummary_"+gstrTCID+"_"+strTimeText+".pdf";
			
			String[][] strReportData={{"Status Summary","**","**","**","**"},
					  {"Status Type","Resource","Status","Total Hours","% of Total Hours"},
					  {statTypeName,strResource,strStatusName1,"\\d+\\.\\d+","\\d+"},
					  {statTypeName,strResource,strStatusName2,"\\d+\\.\\d+","\\d+"},
					  {"**","**","**","**","**"},
					  {"Aggregate Status Summary","**","**","**","**"},
					  {"Status Type","Resource Type","Status","Total Hours","% of Total Hours"},
					  {statTypeName,strResType,strStatusName1,"\\d+\\.\\d+","\\d+"},
					  {statTypeName,strResType,strStatusName2,"\\d+\\.\\d+","\\d+"}
					  };
	
					
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName1, strStatusTypeValue, strStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, statTypeName, strStatusName2, strStatusTypeValue, strStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName1);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strStatusValue[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName2);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strStatusValue[1]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status value";
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, strPStatType, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//private ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypeVisibility(seleniumPrecondition, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, strPStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strPStatType);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strSTvalue[1]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, strPStatType, strStatusName3, strStatusTypeValue, strStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition, strPStatType, strStatusName4, strStatusTypeValue, strStatTypeColor,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strPStatType, strStatusName3);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strPStatusValue[0]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,strPStatType, strStatusName4);
				if(strStatValue.compareTo("")!=0){
					strFuncResult="";
					strPStatusValue[1]=strStatValue;
				}else{
					strFuncResult="Failed to fetch status value";
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
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResType, "css=input[name='statusTypeID'][value='"+strSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for(int intST=0;intST<strSTvalue.length;intST++){
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(seleniumPrecondition,strSTvalue[intST], true);
																	
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
				String strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);
				
				if(strResVal.compareTo("")!=0){
					strFuncResult="";
					strRSValue[0]=strResVal;
				}else{
					strFuncResult="Failed to fetch Resource value";
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
			//create role to update
									
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strSTvalue, true, strSTvalue, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch Role value";
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
										.getProperty("CreateNewUsr.AdvOptn.RepStatSummary"),
								true);

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

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				String[] strRoleStatType = {statTypeName};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(selenium,
							strResource, statTypeName, strSTvalue[0],
							strStatusName1,strStatusValue[0],strStatusName2,strStatusValue[1]);
				
			
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportDetAndGenReport(selenium, strRSValue[0], strSTvalue[0],strCurrDate, strCurrDate, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strPDFDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt< Integer.parseInt(propEnvDetails
							.getProperty("WaitForReport")));
						 	
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportDetAndGenReport(selenium, strRSValue[0], strSTvalue[0],strCurrDate, strCurrDate, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt< Integer.parseInt(propEnvDetails
							.getProperty("WaitForReport")));
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objCSV.ReadFromCSV(strCSVDownlPath, strReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[][] strPReportData={{"Status Summary","**","**","**","**"},
					  {"Status Type","Resource","Status","Total Hours","% of Total Hours"},
					  {strPStatType,strResource,strStatusName3,"\\d+\\.\\d+","\\d+"},
					  {strPStatType,strResource,strStatusName4,"\\d+\\.\\d+","\\d+"},
					  {"**","**","**","**","**"},
					  {"Aggregate Status Summary","**","**","**","**"},
					  {"Status Type","Resource Type","Status","Total Hours","% of Total Hours"},
					  {strPStatType,strResType,strStatusName3,"\\d+\\.\\d+","\\d+"},
					  {strPStatType,strResType,strStatusName4,"\\d+\\.\\d+","\\d+"}
					  };
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				String[] strEventStatType = {};
				String[] strRoleStatType = {strPStatType};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
			
				strFuncResult = objViewMap.updateMultiStatusType_ChangeVal(selenium,
							strResource, strPStatType, strSTvalue[1],
							strStatusName3,strPStatusValue[0],strStatusName4,strPStatusValue[1]);
				
			
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportDetAndGenReport(selenium, strRSValue[0], strSTvalue[1],strCurrDate, strCurrDate, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strPrPDFDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt< Integer.parseInt(propEnvDetails
							.getProperty("WaitForReport")));
						 	
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
				strFuncResult = objRep.navToStatusSummReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportDetAndGenReport(selenium, strRSValue[0], strSTvalue[1],strCurrDate, strCurrDate, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strPCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt< Integer.parseInt(propEnvDetails
							.getProperty("WaitForReport")));
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objCSV.ReadFromCSV(strPCSVDownlPath, strPReportData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
				//Write result data
				strTestData[0]= propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strUserName+"/"+strUsrPassword;
				strTestData[3]=strResType;
				strTestData[4]=strResource;
				strTestData[5]=statTypeName+" "+strPStatType;
				strTestData[6]=strStatusName1+","+strStatusName2+";"+strStatusName1+","+strStatusName2;
				strTestData[5]="Check the Status Summary details in PDF file: "+strPDFDownlPath+" for private ST: "+strPDFDownlPath;
				
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Reports");
				
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+ strFuncResult;
			}				
													
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-91256";
			gstrTO = "Verify that a 'Status Summary' report can be generated";
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

	
	/***************************************************************
	'Description		: Verify that a 'Status Reason Summary' report can be generated
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:12-09-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	@Test
	public void testSmoke91304() throws Exception {
		try {
			gstrTCID = "91304"; // Test Case Id
			gstrTO = " Verify that a 'Status Reason Summary' report can be generated";
																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			Paths_Properties objAP = new Paths_Properties();
			pathProps = objAP.Read_FilePath();
			gstrTimeOut=propEnvDetails.getProperty("TimeOut");
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();
			String strTestData[]=new String[10];
			CSVFunctions objCSV=new CSVFunctions();
			int intResCnt=0;
			String strCSVResult="";
			StatusReason objStatRes=new StatusReason();
			StatusTypes objST=new StatusTypes();
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			CreateUsers objCreateUsers=new CreateUsers();
			
			Roles objRole=new Roles();
			
			String strFuncResult="";
		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			Login objLogin = new Login();// object of class Login
		
			ViewMap objViewMap=new ViewMap();
			Reports objRep=new Reports();
				
			String strUpdMulValue="";
			String strUpdValCheck="";
			
			String strPUpdMulValue="";
			String strPUpdValCheck="";
			// login details
			String strAdmUserName = rdExcel.readData("Login", 3, 1);
			String strAdmPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";
			String statTypeName="AutoMSt_"+strTimeText;
			String strStatTypDefn="Auto";
			String strStatTypeColor="Black";
			
			String strPStatType="AutopMSt_"+strTimeText;
			
			String strAbbrv="Rs";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strRoleValue="";
						
			String strUsrFulName="autouser";
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strUsrPassword="abc123";
			
					
			String strState="Alabama";
			String strCountry="Autauga County";
					
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strStatusName3="Stc"+strTimeText;
			String strStatusName4="Std"+strTimeText;
			
			String strResource="AutoRs_"+strTimeText;
			String strResType="AutoRt_"+strTimeText;	
							
			String strSTvalue[]=new String[2];
			String strRSValue[]=new String[1];
		
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			String strPStatusValue[]=new String[2];
			strPStatusValue[0]="";
			strPStatusValue[1]="";
			
			strSTvalue[0]="";	
			strSTvalue[1]="";	
			strRSValue[0]="";
			String strAutoFilePath=propElementAutoItDetails.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatReasSumRep_"+gstrTCID+"_"+strTimeText+".csv";
			String strPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"StatReasSumRep_"+gstrTCID+"_"+strTimeText+".pdf";
			String strAutoFileName=propElementAutoItDetails.getProperty("Reports_DownloadFile_Name");
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
					
			String strPCSVDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"PrStatReasSumRep_"+gstrTCID+"_"+strTimeText+".csv";
			String strPrPDFDownlPath=pathProps.getProperty("Reports_DownloadCSV_Path")+"PrStatReasSumRep_"+gstrTCID+"_"+strTimeText+".pdf";
			
			String strReasonName1="AutoReas1_"+System.currentTimeMillis();
			String strReasonVal1="";
			
			String strReasonName2="AutoReas2_"+System.currentTimeMillis();
			String strReasonVal2="";
			
			
			// Search user criteria
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
				
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,strAdmUserName,
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
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition, strReasonName1, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strReasonVal1=objStatRes.fetchStatReasonValue(seleniumPrecondition, strReasonName1);
				if(strReasonVal1.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch status value";
				}
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
				strFuncResult = objStatRes.createStatusReasn(seleniumPrecondition, strReasonName2, "", "", true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strReasonVal2=objStatRes.fetchStatReasonValue(seleniumPrecondition, strReasonName2);
				if(strReasonVal2.compareTo("")!=0){
					strFuncResult="";
					
				}else{
					strFuncResult="Failed to fetch status value";
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
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strStatusTypeValue, statTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition, strReasonVal1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeName);
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
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal1, true);
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal1, true);
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
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
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
				strFuncResult = objST
						.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
								strStatusTypeValue, strPStatType,
								strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectAndDeselectStatusReason(seleniumPrecondition,
						strReasonVal2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition,
						strPStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strPStatType);
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
						strPStatType, strStatusName3, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						strPStatType, strStatusName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						strPStatType, strStatusName4, strStatusTypeValue,
						strStatTypeColor, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatRes.selectAndDeselectStatusReasInStatus(
						seleniumPrecondition, strReasonVal2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.saveAndVerifyStatus(seleniumPrecondition,
						strPStatType, strStatusName4);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strPStatType, strStatusName3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						strPStatType, strStatusName4);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPStatusValue[1] = strStatValue;
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

			for (int intST = 0; intST < strSTvalue.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);

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
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusTypeWithReason(
						selenium, strResource, statTypeName, strSTvalue[0],
						strStatusName1, strStatusValue[0], strStatusName2,
						strStatusValue[1], strReasonVal1, strReasonVal1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "1");
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
				Thread.sleep(150000);
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
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(
						selenium, strRSValue[0], statTypeName, strCurrDate,
						strCurrDate, true, strUpdValCheck, strReasonVal1);
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
						&& intCnt < Integer.parseInt(propEnvDetails
								.getProperty("WaitForReport")));

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

				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(
						selenium, strRSValue[0], statTypeName, strCurrDate,
						strCurrDate, false, strUpdValCheck, strReasonVal1);
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
						&& intCnt < Integer.parseInt(propEnvDetails
								.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData = {
						{ "Status Reason Summary", "**", "**", "**", "**", "**" },
						{ "Resource", "Status", "Status Reason", "Time(hrs)",
								"% of all Hrs on this status", "% of Total Hrs" },

						{ strResource, strUpdMulValue, strReasonName1,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" }, };

				strCSVResult = objCSV.ReadFromCSV(strCSVDownlPath,
						strReportData);
				if (strCSVResult == "") {
					intResCnt++;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { strPStatType };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.updateMultiStatusTypeWithReason(
						selenium, strResource, strPStatType, strSTvalue[1],
						strStatusName3, strPStatusValue[0], strStatusName4,
						strPStatusValue[1], strReasonVal2, strReasonVal2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String[] strArFunRes = new String[5];
				strArFunRes = objViewMap.getUpdSTValueInMap(selenium, "3");
				strFuncResult = strArFunRes[1];
				strPUpdMulValue = strArFunRes[0];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (strPUpdMulValue.equals(strStatusName3)) {
					strPUpdValCheck = strPStatusValue[0];
				} else {
					strPUpdValCheck = strPStatusValue[1];
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				Thread.sleep(150000);
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
				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(
						selenium, strRSValue[0], strPStatType, strCurrDate,
						strCurrDate, true, strPUpdValCheck, strReasonVal2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPrPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName)
						&& intCnt < Integer.parseInt(propEnvDetails
								.getProperty("WaitForReport")));

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

				strFuncResult = objRep.enterStatReasSummaryRepAndGenRep(
						selenium, strRSValue[0], strPStatType, strCurrDate,
						strCurrDate, false, strPUpdValCheck, strReasonVal2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPCSVDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName)
						&& intCnt < Integer.parseInt(propEnvDetails
								.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strReportData1 = {
						{ "Status Reason Summary", "**", "**", "**", "**", "**" },
						{ "Resource", "Status", "Status Reason", "Time(hrs)",
								"% of all Hrs on this status", "% of Total Hrs" },

						{ strResource, strPUpdMulValue, strReasonName2,
								"\\d+\\.\\d+", "\\d+\\.\\d+", "\\d+\\.\\d+" }, };

				strCSVResult = objCSV.ReadFromCSV(strPCSVDownlPath,
						strReportData1);
				if (strCSVResult == "") {
					intResCnt++;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				if (intResCnt == 2) {
					gstrResult = "PASS";
					// Write result data
					strTestData[0] = propEnvDetails.getProperty("Build");
					strTestData[1] = gstrTCID;
					strTestData[2] = strUserName + "/" + strUsrPassword;
					strTestData[3] = strResType;
					strTestData[4] = strResource;
					strTestData[5] = statTypeName + " " + strPStatType;
					strTestData[6] = strUpdMulValue + " " + strPUpdMulValue;
					strTestData[5] = "Check the Status Summary details in PDF file: "
							+ strPDFDownlPath
							+ "status reason: "
							+ strReasonName1
							+ ";"
							+ " for Private ST: "
							+ strPrPDFDownlPath
							+ "status reason: "
							+ strReasonName2;

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Reports");
				}
			} catch (AssertionError Ae) {
				gstrReason = gstrReason+ strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91304";
			gstrTO = " Verify that a 'Status Reason Summary' report can be generated";
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

	/****************************************************************************************
	'Description	:Verify that a 'Status Detail' report can be generated for Saturation Score status type.
	'Precondition	:1.Role based or shared status type SST and private status type pSST are created.
					2..Resource type RT is associated with status type NST and pNST(number).
					3. Resources RS is created with address under RT.
					4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status typeS SST and pSST
					'Update Status' and 'Run Report' rights on resources RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke91143() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "91143"; // Test Case Id
			gstrTO = "Verify that a 'Status Detail' report can be generated"
					+ " for Saturation Score status type.";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");


			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "SST" + strTimeText;
			String statPrivtTypeName = "pSST" + strTimeText;
			String strStatusTypeValue = "Saturation Score";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.csv";

			String strCSVDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.csv";

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataCSV2 = null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
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
								strStatusTypeValue, statPrivtTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statPrivtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
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

			// 2. Resource type RT is associated with status type ST1.

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

				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);

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

			// 3. Resources RS is created under RT.

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

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

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/* 6.Status Type NST is updated with some value on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPrivtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				String strReportResult[] = objStatusTypes
						.updateSatuScoreStatusTypeWitTime(selenium,
								strResource, statPrivtTypeName, strSTvalue[1],
								strUpdateValue1, strUpdateValue2, "393", "429",
								"HH:mm:ss");
				strFuncResult = strReportResult[0];
				strTimeUpdateSystem = strReportResult[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource RS is displayed ");

				String strElementID = "//a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource RS is NOT displayed ");
				gstrReason = strFuncResult + " Resource RS is NOT displayed ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Resource info pop window of RS is displayed with 'View Info'"
								+ "links. ");
				String strElementID = "//a[text()='Update Status']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ");

				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource info pop window of RS is displayed with "
						+ "'Update status' links. ");

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed with"
						+ "  'Update status' links. ";
				log4j
						.info("Resource info pop window of RS is NOT displayed with"
								+ "  'Update status' links. ");

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifyCalndrWidInStatusDetailReport(
						selenium, statPrivtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[1], strApplTime,
								strApplTime, true, statPrivtTypeName,
								"HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserName_1, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath_1,
						"Status Detail Report need to be check in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[1], strApplTime,
								strApplTime, false, statPrivtTypeName,
								"HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strCurrDate, strDurationDiff,
						strUserName_1, propEnvDetails.getProperty("ExternalIP"), "", strCSVDownlPath_1,
						"Status Detail Report need to be check in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				String strReportResult[] = objStatusTypes
						.updateSatuScoreStatusTypeWitTime(selenium,
								strResource, statTypeName, strSTvalue[0],
								strUpdateValue1, strUpdateValue2, "393", "429",
								"HH:mm:ss");
				strFuncResult = strReportResult[0];
				strTimeUpdateSystem = strReportResult[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("//div[@class='emsText maxheight']/span[3]");

				strUpdatedDate = selenium
						.getText("//div[@class='emsText maxheight']"
								+ "/span[@class='time'][2]");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserName_1, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath_2,
						"Status Detail Report need to be check in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strCurrDate, strDurationDiff,
						strUserName_1, propEnvDetails.getProperty("ExternalIP"), "", strCSVDownlPath_2,
						"Status Detail Report need to be check in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			File Pf1 = new File(strCSVDownlPath_1);
			File Pf2 = new File(strCSVDownlPath_2);
			File Cf1 = new File(strPDFDownlPath_1);
			File Cf2 = new File(strPDFDownlPath_2);

			if (Pf1.exists() && Pf2.exists() && Cf1.exists() && Cf2.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"SmokeReports");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath,
							"SmokeReports");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"SmokeReports");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath,
							"SmokeReports");
				} catch (AssertionError Ae) {
					gstrReason = gstrReason + strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91143";
			gstrTO = "Verify that a 'Status Detail' report can be "
					+ "generated for Saturation Score status type.";
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
	
	/****************************************************************************************
	'Description	:Verify that a 'Status Detail' report can be generated for Multi status type.
	'Precondition	:1.Role based or shared status type MST and private status type pMST are created.
					2..Resource type RT is associated with status type NST and pNST(number).
					3. Resources RS is created with address under RT.
					4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status typeS MST and pMST
					'Update Status' and 'Run Report' rights on resources RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:14-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke91122() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "91122"; // Test Case Id
			gstrTO = "Verify that a 'Status Detail' report can be"
					+ " generated for Multi status type.";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "MST" + strTimeText;
			String statPrivtTypeName = "pMST" + strTimeText;
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strPrivtStatusName1 = "pSta" + strTimeText;
			String strPrivtStatusName2 = "pStb" + strTimeText;

			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			String strPrivtStatusValue[] = new String[2];
			strPrivtStatusValue[0] = "";
			strPrivtStatusValue[1] = "";

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.csv";

			String strCSVDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.csv";

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataCSV2 = null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName, strStatusName2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
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

				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strStatusTypeValue, statPrivtTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statPrivtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statPrivtTypeName, strPrivtStatusName1,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statPrivtTypeName, strPrivtStatusName2,
						strStatusTypeValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statPrivtTypeName, strPrivtStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPrivtStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statPrivtTypeName, strPrivtStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strPrivtStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
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

			// 2. Resource type RT is associated with status type ST1.

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

				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);

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

			// 3. Resources RS is created under RT.

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

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

			/* 6.Status Type NST is updated with some value on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
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

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPrivtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strUpdateResult[] = objStatusTypes
						.updateMultiStatusType_ChangeValWithTime(selenium,
								strResource, statTypeName, strSTvalue[0],
								strStatusName1, strStatusValue[0],
								strStatusName2, strStatusValue[1], "HH:mm:ss");
				strTimeUpdateSystem = strUpdateResult[1];
				strFuncResult = strUpdateResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource RS is displayed ");

				String strElementID = "//a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource RS is NOT displayed ");
				gstrReason = strFuncResult + " Resource RS is NOT displayed ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Resource info pop window of RS is displayed with 'View Info'"
								+ "links. ");
				String strElementID = "//a[text()='Update Status']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ");

				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource info pop window of RS is displayed with "
						+ "'Update status' links. ");

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed with"
						+ "  'Update status' links. ";
				log4j
						.info("Resource info pop window of RS is NOT displayed with"
								+ "  'Update status' links. ");

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifyCalndrWidInStatusDetailReport(
						selenium, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName,
								strStatusValue[0], "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("CSV generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserName_1, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath_1,
						"Status Detail Report need to be check in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName,
								strStatusValue[0], "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserName_1, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath_1,
						"Status Detail Report need to be check in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statPrivtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strUpdateResult[] = objStatusTypes
						.updateMultiStatusType_ChangeValWithTime(selenium,
								strResource, statPrivtTypeName, strSTvalue[1],
								strPrivtStatusName1, strPrivtStatusValue[0],
								strPrivtStatusName2, strPrivtStatusValue[1],
								"HH:mm:ss");
				strTimeUpdateSystem = strUpdateResult[1];
				strFuncResult = strUpdateResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("//div[@class='emsText maxheight']/span[3]");

				strUpdatedDate = selenium
						.getText("//div[@class='emsText maxheight']"
								+ "/span[@class='time'][2]");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[1], strApplTime,
								strApplTime, true, statPrivtTypeName,
								strPrivtStatusValue[0], "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserName_1, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath_2,
						"Status Detail Report need to be check in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[1], strApplTime,
								strApplTime, false, statPrivtTypeName,
								strPrivtStatusValue[0], "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserName_1, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath_2,
						"Status Detail Report need to be check in CSV file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1 = new File(strCSVDownlPath_1);
			File Pf2 = new File(strCSVDownlPath_2);
			File Cf1 = new File(strPDFDownlPath_1);
			File Cf2 = new File(strPDFDownlPath_2);

			if (Pf1.exists() && Pf2.exists() && Cf1.exists() && Cf2.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"SmokeReports");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath,
							"SmokeReports");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"SmokeReports");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath,
							"SmokeReports");
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+ strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91122";
			gstrTO = "Verify that a 'Status Detail' report can be generated for Multi status type.";
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
	

	/*******************************************************************************************
	'Description	:Verify that a 'Resource Detail' report can be generated.
	'Precondition	:1. Status types NST, MST, TST and SST are created.
					 2. Resource type RT selecting NST, MST, TST and SST.
					 3. Resource RS is created with Address selecting RT.
					 4. User 'U1' is created providing mandatory data and selecting 'May update region setup information' right under Advanced option. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:17-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke91753() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();

		try {
			gstrTCID = "91753"; // Test Case Id
			gstrTO = "Verify that a 'Resource Detail' report can be generated.";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName = "NST" + strTimeText;
			String statTextTypeName = "TST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String statSaturatTypeName = "SST" + strTimeText;

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

			// General variable
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strLongitude = "";
			String strLatitude = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "ResorceDetail_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "ResorceDetail_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "ResorceDetail_" + gstrTCID + "_"
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigating to status type list
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating TST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating SST
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status Type Multi status type is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2,
						strMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName1);
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
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Resource type RT

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
				seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[3] + "']");

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

			/* 2. Resources RS is created under RT. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

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

				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition,
						strResource);
				
				int intCnt=0;
				do{
					try {

						assertTrue(seleniumPrecondition.isElementPresent("id=longitude"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				strLongitude = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);

				char ch = '.';

				int intpos = strLongitude.indexOf(ch);

				String strLongitudeBefore = strLongitude.substring(0, intpos);
				strLongitude = strLongitude.substring(intpos, intpos + 4);

				strLongitude = strLongitudeBefore + strLongitude;
				log4j.info(strLongitude);

				strLatitude = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude.indexOf(ch);
				String strLatitudeBefore = strLatitude.substring(0, intpos);
				strLatitude = strLatitude.substring(intpos, intpos + 4);

				strLatitude = strLatitudeBefore + strLatitude;
				log4j.info(strLatitude);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4. User 'U1' is created providing mandatory data and selecting
			 * 'May update region setup information' right under Advanced
			 * option.
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
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("createUser.advancedoption.MayUpdateRegion");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
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

				strFuncResult = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.navResourceDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.verifyRSInRsDeatilReport(selenium,
						strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.generateResourceDetailReport(selenium,
						strRSValue);
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
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Resource Detail Report", "", "", "", "", "", "", "",
								"", "", "", "", "", "", "" },
						{ "Resource Name", "Type", "Address", "County",
								"Latitude", "Longitude", "EMResource ID",
								"AHA ID", "Website", "Contact", "Phone 1",
								"Phone 2", "Fax", "Email", "Notes" },
						{ strResource, strResrctTypName, "AL ", strCountry,
								strLongitude, strLatitude, strRSValue[0], "",
								"", strContFName + " " + strContLName, "", "",
								"", "", "" } };

				strFuncResult = OFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";

			} catch (AssertionError Ae) {
				gstrReason = gstrReason+ strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91753";
			gstrTO = "Verify that a 'Resource Detail' report can be generated.";
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
	/****************************************************************************************
	'Description	:Verify that a 'Status Detail' report can be generated for Number status 
	'Precondition	:1.Role based or shared status type NST and private status type pNST are created.
					2..Resource type RT is associated with status type NST and pNST(number).
					3. Resources RS is created with address under RT.
					4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status typeS NST and pNST
					'Update Status' and 'Run Report' rights on resources RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:11-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Feb-2013                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke91105() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral=new General();
		
		try {
			gstrTCID = "91105"; // Test Case Id
			gstrTO = "Verify that a 'Status Detail' report can be generated for Number status ";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strCSTApplTime = dts.getTimeOfParticularTimeZone("CST",
			"M/d/yyyy");
			
			String strApplTime="";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
			
			String statTypeName="NST"+strTimeText;
			String statPrivtTypeName="pNST"+strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
		
			String strSTvalue[]=new String[2];
			String strRSValue[]=new String[1];
			
			//Application time
			String strUpdatdVale="";
			String strUpdatedDate="";
			String strReportGenrtTime="";
			String strReportGenrtTimePDF="";
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
		
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			//System Time
			String strTimeGenerateSystem="";
			String strTimeUpdateSystem="";
			
			String strTimePDFGenerateSystem="";
			String strDurationDiffPDF="";
			
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
		
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			
			String strCSVDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.csv";

			String strCSVDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.csv";

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.pdf";
	
		
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String[] strTestDataCSV1= null;
			String[] strTestDataCSV2= null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//1. Status Type NST(numeric status type) is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, strStatusTypeValue, statPrivtTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statPrivtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Navigate to Role list
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
			

			//2. Resource type RT is associated with status type ST1. 
			
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
				
				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition, strSTvalue[1], true);
				
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
			
			//3. Resources RS is created under RT. 
			
			
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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */
			
			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);

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
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

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
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			

			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			

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

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName,statPrivtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			//Update Status
			try {
				assertEquals("", strFuncResult);

				String strSTValue[]={strSTvalue[0]};
				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						selenium, strResource, strSTValue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource RS is displayed ");

				String strElementID = "//a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource RS is NOT displayed ");
				gstrReason = strFuncResult + " Resource RS is NOT displayed ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Resource info pop window of RS is displayed with 'View Info'"
								+ "links. ");
				String strElementID = "//a[text()='Update Status']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ");

				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource info pop window of RS is displayed with "
						+ "'Update status' links. ");

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed with"
						+ "  'Update status' links. ";
				log4j
						.info("Resource info pop window of RS is NOT displayed with"
								+ "  'Update status' links. ");

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifyCalndrWidInStatusDetailReport(selenium, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);
				
				//Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
				
				
				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strCSTApplTime,
								strCSTApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiffPDF);

				//Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("CSV generation time "+strReportGenrtTimePDF);
				
				strTestDataPDF1 = new String[]{ propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_1,
						"Status Detail Report need to be check in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
			
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strCSTApplTime,
								strCSTApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];
				
				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time "+strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath_1};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt< Integer.parseInt(propEnvDetails
							.getProperty("WaitForReport")));
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strCurrDate=dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat =strUpdatedDate; 
				String strDurationGenerat =strReportGenrtTime;
				
				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);
				
				double fltDurationDiff=(double)intDurationDiff/3600;
				
				double dRounded=dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV1 =new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_1,
						"Status Detail Report need to be check in CSV file"

				};
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statPrivtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strSTValue[] = { strSTvalue[1] };
				String strReportResult[] = objViewMap
						.updateStatusTypeInAnyPositionWithTime(selenium,
								strResource, strSTValue, "HH:mm:ss", 3);
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("//div[@class='emsText maxheight']/span[3]");

				strUpdatedDate = selenium
						.getText("//div[@class='emsText maxheight']"
								+ "/span[@class='time'][2]");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);
				
				//Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
				
				
				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[1], strCSTApplTime,
								strCSTApplTime, true, statPrivtTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiffPDF);

				//Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("CSV generation time "+strReportGenrtTimePDF);
				
				strTestDataPDF2 = new String[]{ propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_2,
						"Status Detail Report need to be check in PDF file"

				};

				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[1], strCSTApplTime,
								strCSTApplTime, false, statPrivtTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];
				
				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time "+strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath_2};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt< Integer.parseInt(propEnvDetails
							.getProperty("WaitForReport")));
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strCurrDate=dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat =strUpdatedDate; 
				String strDurationGenerat =strReportGenrtTime;
				
				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);
				
				double fltDurationDiff=(double)intDurationDiff/3600;
				
				double dRounded=dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiff);
		
				strTestDataCSV2 =new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_2,
						"Status Detail Report need to be check in CSV file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1=new File(strCSVDownlPath_1);
			File Pf2=new File(strCSVDownlPath_2);
			File Cf1=new File(strPDFDownlPath_1);
			File Cf2=new File(strPDFDownlPath_2);
			
			if(Pf1.exists()&&Pf2.exists()&&Cf1.exists()&&Cf2.exists()){
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";
					
					
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath, "SmokeReports");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath, "SmokeReports");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath, "SmokeReports");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath, "SmokeReports");
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+ strFuncResult;
				}
			}
			

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91105";
			gstrTO = "Verify that a 'Status Detail' report can be generated for Number status ";
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
	
	/****************************************************************************************
	'Description	:Verify that a 'Status Detail' report can be generated for Text status type.
	'Precondition	:1.Role based or shared status type TST and private status type pTST are created.
					2..Resource type RT is associated with status type NST and pNST(number).
					3. Resources RS is created with address under RT.
					4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status typeS TST and pTST
					'Update Status' and 'Run Report' rights on resources RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-Sep-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'26-Feb-2012                                <Name>
	****************************************************************************************/
	
	@Test
	public void testSmoke91141() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "91141"; // Test Case Id
			gstrTO = "Verify that a 'Status Detail' report can be generated for Text status type.";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");


			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "TST" + strTimeText;
			String statPrivtTypeName = "pTST" + strTimeText;
			String strStatusTypeValue = "Text";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.csv";

			String strCSVDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.csv";

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + "2.pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String[] strTestDataCSV1= null;
			String[] strTestDataCSV2= null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;


			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
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
								strStatusTypeValue, statPrivtTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statPrivtTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
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

			// 2. Resource type RT is associated with status type ST1.

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

				strFuncResult = objRT.selAndDeselSTInEditRT(seleniumPrecondition,
						strSTvalue[1], true);

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

			// 3. Resources RS is created under RT.

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

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

			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/* 6.Status Type NST is updated with some value on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

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

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName, statPrivtTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strSTValue[] = { strSTvalue[1] };
				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						selenium, strResource, strSTValue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource RS is displayed ");

				String strElementID = "//a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource RS is NOT displayed ");
				gstrReason = strFuncResult + " Resource RS is NOT displayed ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Resource info pop window of RS is displayed with 'View Info'"
								+ "links. ");
				String strElementID = "//a[text()='Update Status']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				log4j.info("Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ");

				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed"
						+ " with 'View Info'links. ";
			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource info pop window of RS is displayed with "
						+ "'Update status' links. ");

				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ " Resource info pop window of RS is NOT displayed with"
						+ "  'Update status' links. ";
				log4j
						.info("Resource info pop window of RS is NOT displayed with"
								+ "  'Update status' links. ");

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.verifyCalndrWidInStatusDetailReport(
						selenium, statPrivtTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[1], strApplTime,
								strApplTime, true, statPrivtTypeName,
								"HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("CSV generation time "
						+ strReportGenrtTimePDF);
				
				strTestDataPDF1 = new String[]{ propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_1,
						"Status Detail Report need to be check in PDF file"

				};


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[1], strApplTime,
								strApplTime, false, statPrivtTypeName,
								"HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV1 =new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_1,
						"Status Detail Report need to be check in CSV file"

				};
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strSTValue[] = { strSTvalue[0] };
				String strReportResult[] = objViewMap
						.updateStatusTypeInAnyPositionWithTime(selenium,
								strResource, strSTValue, "HH:mm:ss", 3);
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("//div[@class='emsText maxheight']/span[3]");

				strUpdatedDate = selenium
						.getText("//div[@class='emsText maxheight']"
								+ "/span[@class='time'][2]");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];
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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("CSV generation time "
						+ strReportGenrtTimePDF);
				
				strTestDataPDF2 = new String[]{ propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_2,
						"Status Detail Report need to be check in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

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
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);
				
				strTestDataCSV2 =new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_2,
						"Status Detail Report need to be check in CSV file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			File Pf1=new File(strCSVDownlPath_1);
			File Pf2=new File(strCSVDownlPath_2);
			File Cf1=new File(strPDFDownlPath_1);
			File Cf2=new File(strPDFDownlPath_2);
			
			if(Pf1.exists()&&Pf2.exists()&&Cf1.exists()&&Cf2.exists()){
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";
					
					
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath, "SmokeReports");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath, "SmokeReports");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath, "SmokeReports");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath, "SmokeReports");
				} catch (AssertionError Ae) {
					gstrReason = gstrReason+ strFuncResult;
				}
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "91141";
			gstrTO = "Verify that a 'Status Detail' report can be generated for Text status type.";
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
	
	/***************************************************************
	'Description		:Verify that 'Statewide Resource Details' report can be 
	'					generated by RegAdmin in 'Statewide Florida' for all the 
	'					resources for the selected criteria while generating report.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:10/31/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testSmoke109178() throws Exception {
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Reports objRep = new Reports();

		try {
			gstrTCID = "109178"; // Test Case Id
			gstrTO = "Verify that 'Statewide Resource Details' report can be "
					+ "generated by RegAdmin in 'Statewide Florida' for all "
					+ "the resources for the selected criteria while generating report.";

			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);

			String strRegn1 = rdExcel.readData("Login", 22, 4);
			String strRegn2 = rdExcel.readData("Login", 26, 4);

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strNumStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strST = new String[2];

			String strResrcTypName1 = "AutoRt1_" + strTimeText;
			String strResrcTypName2 = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[2];

			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strRSValue[] = new String[2];
			String strStandResType = "Hospital";
			String strContFName = "FN";
			String strContLName = "LN";
			
			// Report
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatewideResDetRep1" + gstrTCID + strTimeText + ".xls";

			String strCSVFileNameRenamed = "StatewideResDetRep1" + gstrTCID
					+ strTimeText;

			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");

			/*
			 * STEP : Action:Precondition: All the below mentioned test data is
			 * created in 'Statewide Florida' region. 1. Number status type ST1
			 * is created with a standard status type 'Average wait time'. 2.
			 * Resource type RT1 is created selecting status type ST1. 3.
			 * Resource RS1 is created under resource type RT1 selecting
			 * standard resource type 'Hospital'. All the below mentioned test
			 * data is created in 'Central Florida' region. 1. Number status
			 * type ST2 is created with a standard status type 'Average wait
			 * time'. 2. Resource type RT2 is created selecting status type ST2.
			 * 3. Resource RS2 is created under resource type RT2 selecting
			 * standard resource type 'Hospital'. Expected Result:No Expected
			 * Result
			 */
			
			// 584154
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
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
						strNumStatTypeValue, strStatType1, strTxtStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStandardST = "Average Wait Time";
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
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
						strResrcTypName1, strST[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName1);

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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource1, "ab", strResrcTypName1, strStandResType,
						strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource1, "No", "", "ab", strResrcTypName1);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Region 2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
						strNumStatTypeValue, strStatType2, strTxtStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStandardST = "Average Wait Time";
				strFuncResult = objST.selectStandardSTInCreateST(seleniumPrecondition,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, strStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strST[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(seleniumPrecondition,
						strResrcTypName2, strST[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName2);

				if (strRsTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(seleniumPrecondition,
						strResource2, "ab", strResrcTypName2, strStandResType,
						strContFName, strContLName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(seleniumPrecondition,
						strResource2, "No", "", "ab", strResrcTypName2);
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");

			/*
			 * STEP : Action:Login as RegAdmin to 'Statewide Florida' region
			 * Expected Result:'Region Default' screen is displayed
			 */
			// 584084
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Reports >> Resource reports Expected
			 * Result:'Resource Reports Menu' screen is displayed
			 */

			// 584086
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Statewide resource details report' link
			 * Expected Result:Resources created across all the Florida regions
			 * under the resource type with standard resource types that are
			 * active in a region are listed under 'Standard Resources' on
			 * 'Statewide Resource Detail Report (Step 1 of 2) 'Hospital' is
			 * listed on the page.
			 */

			// 584124
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.navToStatewideResourceDetailReportPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select the check box corresponding to 'Hospital'
			 * under 'Standard Resources' and click on 'Next' button. Expected
			 * Result:'Statewide Resource Detail Report (Step 2 of 2)' page is
			 * displayed with 'Standard statuses' listed. 'Average Wait time' is
			 * listed on the page.
			 */

			// 584155
			try {
				assertEquals("", strFuncResult);
				String strStdResTypeVal = "106";
				String strStdRt = "Hospital";
				strFuncResult = objRep.selectStdResTypeForReport(selenium,
						strStdResTypeVal, strStdRt);
				log4j
						.info("Resources created across all the Florida regions under"
								+ " the resource type with standard resource types that are "
								+ "active in a region are listed under 'Standard Resources' "
								+ "on 'Statewide Resource Detail Report (Step 1 of 2) ");
			} catch (AssertionError Ae) {
				log4j
						.info("Resources created across all the Florida regions under"
								+ " the resource type with standard resource types that are "
								+ "active in a region are NOT listed under 'Standard Resources' "
								+ "on 'Statewide Resource Detail Report (Step 1 of 2) ");
				gstrReason = strFuncResult;
			}

			/* 
			 * * STEP : Action:Select the check box corresponding to 'Average
			 * wait time' under 'Standard statuses' and click on 'Generate
			 * Report' button. Expected Result:'File Download' pop up window is
			 * displayed with 'Name', 'Type' and 'From' details. 'Open', 'Save'
			 * and 'Cancel' buttons are available.
			 */
			// 584160
			try {
				assertEquals("", strFuncResult);
				String strStdStatusVal = "4004";
				String strStdStatusName = "Average Wait Time";
				strFuncResult = objRep.selectStdStatusesForReport(selenium,
						strStdStatusVal, strStdStatusName);
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
					Thread.sleep(1000);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			/*
			 * STEP : Action:Click on 'Open' button Expected Result:'Statewide
			 * Resource Detail Report' is generated in 'Excel' (xls format)
			 * displaying appropriate data in the following columns: 1. Region
			 * 2. Resource name 3. Type (Resource Type) 4. Address 5. County 6.
			 * Latitude 7. Longitude 8. EMResource ID 9. AHA ID 10. External ID
			 * 11. Website 12. Contact 13. Phone 1 14. Phone 2 15. Fax 16. Email
			 * 17. Notes 18. Standard status type name (Average Wait time)
			 * Resources created under the standard resource type (Hospital) and
			 * status types associated with standard status type (Average wait
			 * time) which are active across all the Florida regions are listed
			 * in the generated report. Resource 'RS1' details are displayed
			 * appropriately. 'Region' is displayed as 'Statewide Florida'
			 * Resource 'RS2' details are displayed appropriately. 'Region' is
			 * displayed as 'Central Florida'
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Statewide Resource Detail Report",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", "" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String[] strReportData = { "Region", "Resource Name", "Type",
						"Address", "County", "Latitude", "Longitude",
						"EMResource ID", "AHA ID", "External ID", "Website",
						"Contact", "Phone 1", "Phone 2", "Fax", "Email",
						"Notes", "Average Wait Time" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strReportData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn1, strResource1,
						strResrcTypName1, "", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResData[] = { strRegn2, strResource2,
						strResrcTypName2, "", "", "", "", "\\d+", "", "", "",
						strContFName + " " + strContLName, "", "", "", "", "",
						"0" };

				strFuncResult = objOFC.readAndVerifyParticularDataExcel(
						strResData, strCSVDownlRenamedPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				gstrResult = "PASS";

			} catch (AssertionError Ae) {
				gstrReason = gstrReason+ strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-109178";
			gstrTO = "Verify that 'Statewide Resource Details' report can"
					+ " be generated by RegAdmin in 'Statewide Florida' for"
					+ " all the resources for the selected criteria while"
					+ " generating report.";
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

	//start//testSmoke91308//
	/********************************************************************
	'Description	:Verify that a 'Event Detail' report can be generated.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/7/2013
	'Author			:QSG
	'--------------------------------------------------------------------
	'Modified Date				                         Modified By
	'Date					                             Name
	*********************************************************************/

	@Test
	public void testSmoke91308() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		General objGeneral = new General();
		EventSetup objEventSetup = new EventSetup();
		EventList objEventList = new EventList();
		Views objViews = new Views();

		try {
			gstrTCID = "91308"; // Test Case Id
			gstrTO = " Verify that a 'Event Detail' report can be generated.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = "";
			// Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statNumTypeName = "NST" + strTimeText;
			String statTextTypeName = "TST" + strTimeText;
			String statMultiTypeName = "MST" + strTimeText;
			String statSaturatTypeName = "SST" + strTimeText;

			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";

			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";

			// General variable
			String strStatValue = "";

			String strSTvalue[] = new String[4];
			String strRSValue[] = new String[1];
			String strResrctTypName = "AutoRt_" + strTimeText;
			String strRTValue = "";
			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
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

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strETValue = "";
			String strEventStartDate = "";
			String strEventEndDate = "";
			String Drill = "";
			String strEventValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "EventDetail_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status Type NST, MST,
			 * TST and SST are created. <br> <br>2.Resource Type 'RT' is
			 * associated with status types NST,MST,TST and SST. <br> <br>2.
			 * Resources RS is created with address under RT. <br> <br>3. Event
			 * template ET1 is created selecting resource type RT and status
			 * types NST, MST, TST, SST <br> <br>4. Event E1 is created under
			 * ET1 selecting resource RS on day D1. <br> <br>5. User U1 has
			 * following rights: <br> <br>a. Report - Event Detail <br>b. Role
			 * to update status types NST,MST,TST and SST <br>c. 'View
			 * Resource','Update' and 'Run Report' rights on resources RS.
			 * Expected Result:No Expected Result
			 */
			// 550629
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1. Status Type NST, MST, TST and SST are created.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
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

			// Creating NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status Type Multi status type is created.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName1);
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
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.rolesMandatoryFlds(
						seleniumPrecondition, strRoleName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, true);
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

			/*
			 * 2.Resource Type 'RT' is associated with status types NST,MST,TST
			 * and SST.
			 */

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
				seleniumPrecondition
						.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
				seleniumPrecondition
						.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[2] + "']");
				seleniumPrecondition
						.click("css=input[name='statusTypeID'][value='"
								+ strSTvalue[3] + "']");

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
			 * 2. Resources RS is created with address under RT.
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			/*
			 * 3. Event template ET1 is created selecting resource type RT and
			 * status types NST, MST, TST, SST
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup
						.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResTypeValue = { strRTValue };
				String[] strStatusTypeValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3] };
				strFuncResult = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strETValue = objEventSetup.fetchETInETList(
						seleniumPrecondition, strTempName);
				if (strETValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Event E1 is created under ET1 selecting resource RS on day D1.
			 */

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
				strFuncResult = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource, strEveName, strInfo, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strEventStartDate = seleniumPrecondition
						.getText("//div[@id='mainContainer']"
								+ "/table/tbody/tr/td[6][text()='" + strEveName
								+ "']/preceding-sibling::td[2]");

				strEventStartDate = dts.converDateFormat(strEventStartDate,
						"yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm");

				strEventEndDate = seleniumPrecondition
						.getText("//div[@id='mainContainer']/table/tbody/tr/td[6]"
								+ "[text()='"
								+ strEveName
								+ "']/preceding-sibling::td[1]");

				strEventEndDate = dts.converDateFormat(strEventEndDate,
						"yyyy-MM-dd HH:mm", "MM/dd/yyyy HH:mm");

				Drill = seleniumPrecondition
						.getText("//div[@id='mainContainer']/table/tbody/tr/td[6]"
								+ "[text()='"
								+ strEveName
								+ "']/following-sibling::td[1]");

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

		/*
		 * 5. User U1 has following rights:
		 * 
		 * a. Report - Event Detail b. Role to update status types
		 * NST,MST,TST and SST c. 'View Resource','Update' and 'Run Report'
		 * rights on resources RS.
		 */

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EventDetail");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, true, true);
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

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Login as user U1,Click on event banner of 'E1'
		 * Expected Result:Event status screen is displayed with:
		 * <br>Resource 'RS' is displayed on the 'Event Status' screen under
		 * RT along with NST, MST, TST and SST status types.
		 */
		// 550631

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
				String strArStatType2[] = { statMultiTypeName, statNumTypeName,
						statSaturatTypeName, statTextTypeName };
				strFuncResult = objEventList.checkInEventBannerNew(selenium,
						strEveName, strResrctTypName, strResource,
						strArStatType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on key icon associated with resource 'RS' and
		 * update the statuses NST,MST,TST and SST. Expected Result:Updated
		 * value is displayed in Event status screen.
		 */
		// 550633

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource);
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
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"Auto", strSTvalue[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strSTvalue[3], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statMultiTypeName, strStatusName1, "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statSaturatTypeName, "429", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statNumTypeName, "101", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statTextTypeName, "Auto", "6");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Reports>>Event Reports, click on 'Event
		 * Detail'. Expected Result:Event Detail Report (Step 1 of 2)'
		 * screen is displayed with: <br> <br>1. Start Date field with
		 * calender widget <br>2. End Date field with calender widget <br>3.
		 * List of Event Templates
		 */
		// 550634

		/*
		 * STEP : Action:Provide day D1 in start date and end date fields,
		 * select event template ET1 under 'Event Templates' section, then
		 * click on 'Next'. Expected Result:'Event Snapshot Report (Step 2
		 * of 2)' screen is displayed with: <br>1. Search Dates Entered
		 * <br>2. Report Dates <br>3. Select Events(check box) <br>4.
		 * Resources (check box)
		 */
		// 550635

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRep.navToEventReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRep.navToEventDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//td/input[@id='searchStartDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Start Date field with calender widget is displayed");
				String strElementID = "//td/input[@id='searchEndDate']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j.info("Start Date field with calender widget is NOT displayed");
				strFuncResult = "Start Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='ui-datepicker-div']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("End Date field with calender widget is displayed");
				String strElementID = "css=input[value='" + strETValue
						+ "'][name='eventTypeID']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				log4j.info("End Date field with calender widget is NOT displayed");
				strFuncResult = "End Date field with calender widget is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("List of Event Templates is displayed");
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterEvntReportStartDateNEndDate(
						selenium, strApplTime, strApplTime, strETValue);

			} catch (AssertionError Ae) {
				log4j.info("List of Event Templates is NOT displayed");
				strFuncResult = "List of Event Templates is NOT displayed";
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep
						.vrfyElementsInEvntDetalGenerateReport2Of2Pge(selenium,
								strEventValue, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterEvntDetalRepGenerate(selenium,
						strEventValue, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select day D1, select Event'E1', select resource RS
		 * and click on 'Generate Report'. Expected Result:Event Detail
		 * report is generated in PDF format with following details: <br>
		 * <br>The following columns are displayed under the selected event
		 * template: <br> <br>1. Event Title <br>2. Started by <br>3. Event
		 * description <br>4. Start date <br>5. End date <br>6. Drill <br>7.
		 * IP <br>8. Revision <br>9. Last modified by <br>10.Modification
		 * Time <br> <br>Details of resource RS is displayed for all 4
		 * status types appropriately in successive pages with following:
		 * <br> <br>1. Resource name <br>2. Status Type <br> <br>Columns:
		 * <br> <br>1. Status <br>2. Updated <br>3. User
		 */
		// 550636

			try {
				assertEquals("", strFuncResult);
				File f = new File(strPDFDownlPath);
				if (f.isFile()) {
					String[] strTestData = {
							propEnvDetails.getProperty("Build"), gstrTCID,
							strUserName_1 + "/" + strInitPwd, strTempName,
							strEveName, strPDFDownlPath,
							"verify data in PDF file", strEventStartDate,
							strEventEndDate,
							propEnvDetails.getProperty("ExternalIP"), Drill };

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");

					objOFC.writeResultData(strTestData, strWriteFilePath,
							"Eventdetailreport");
					gstrResult = "PASS";
					System.out
							.println("Generated PDF  is saved in the specified folder ");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-91308";
			gstrTO = "Verify that a 'Event Detail' report can be generated.";
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

	// end//testSmoke91308//
}