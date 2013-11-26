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

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement Group :Setting up Status types
' Requirement       :Inactive NEDOCS status type
� Product		    :EMResource v3.23
' Date			    :6/June/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class EdgeCaseInactiveNEDOCSStatusType {


	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EdgeCase_CreateEvent");
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
	Selenium selenium,seleniumPrecondition,seleniumFirefox;
	String gstrTimeOut;

   /***********************************************************************************
	* This function is called the setup() function which is executed before every test.
	* 
	* The function will take care of creating a new selenium session for every test
	* 
    ************************************************************************************/

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

		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
		
		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");
		
		

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

    /************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and 
	* writing the execution result of the test. 
    *************************************************************************************/

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		seleniumPrecondition.stop();
		selenium.stop();
	
		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		seleniumFirefox.stop();
		
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


	/*****************************************************************
	'Description :Verify that a NEDOCS status type can be deactivated.	              
	'Arguments	 :None
	'Returns	 :None
	'Date		 :6/June/2013
	'Author		 :QSG
	'-----------------------------------------------------------------
	'Modified Date				                          Modified By
	'Date					                               Name
	******************************************************************/

	@Test
	public void testEdgeCase119789() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		EventList objEventlist = new EventList();
		EventSetup objEventSetup = new EventSetup();
		EventBanner objEventBanner = new EventBanner();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "119789"; // Test Case Id
			gstrTO = "Verify that a NEDOCS status type can be deactivated.";// TO
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
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statrNumTypeName = "NST" + strTimeText;
			String statrNedHocTypeName = "NEDOC" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTvalue[] = new String[2];
			String strNDSTValue = "NEDOCS Calculation";
			String strNSTValue = "Number";

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strResource1 = "AutoRs_1" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
     		String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[2];
			// Search Resource criteria
			String strCategory = rdExcel.readInfoExcel("Find Res", 4, 3,
					strFILE_PATH);
			String strRegion = rdExcel.readInfoExcel("Find Res", 4, 4,
					strFILE_PATH);
			String strCityZipCd = rdExcel.readInfoExcel("Find Res", 4, 5,
					strFILE_PATH);
			String strState = rdExcel.readInfoExcel("Find Res", 4, 6,
					strFILE_PATH);
			
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			
			//Sub resource
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			//View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// ET
			String strTempName1 = "ET1_" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			
			// sec data
			String strSection1 = "Sec_" + strTimeText;
			String strSectionValue = "";
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

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
			// NEDOCST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedHocTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNedHocTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 3. Resource type 'RT' is created and is associated with the NEDHOC status types.
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

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*Create sub resource type SRT*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strSubResrctTypName,
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
				strFuncResult = objRT
						.selAndDeselSubResourceType(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
			strState = "Alabama";
		    strCountry = "Barbour County";
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5. Sub-resource 'SRS' is created under parent resource 'RS'
			 * selecting 'SRT' sub-resource type.
			 */

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
						strSubResource, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
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
				String strSTvalues[][] = {{ strSTvalue[0], "true" },{ strSTvalue[1], "true" }};
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
			    assertEquals("", strFuncResult);
			    String strOptions = propElementDetails
			      .getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
			    strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
			      strOptions, true);

			   } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			  }
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, true,
						false, true);
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
			
			// Create View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				String strRSValues[]={strRSValue[0]};
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template Creation
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
				String[] strResTypeVal={strRTvalue[0]};
				String[] strStatusTypeval={strSTvalue[0],strSTvalue[1]};
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(seleniumPrecondition,
						strTempName1, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Event Creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName1, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(seleniumPrecondition,
						strResource1, true, false);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Section
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
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNedHocTypeName ,statrNumTypeName};
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);
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
			//Login as user A
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditSubResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[0],strSTvalue[1] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								seleniumPrecondition, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //Add Custom View
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						seleniumPrecondition, strResource1, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
			    assertEquals("", strFuncResult);
			    String[] strStatusType = {  statrNedHocTypeName, statrNumTypeName };
			    strFuncResult = objPreferences.editCustomViewOptions(seleniumPrecondition,
			      strStatusType);
			   } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			   }
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Login as user A
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// 4 Check Status types displayed in View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String[] strResource={strResource1};
				strFuncResult = objViews.checkResTypeAndResourceInUserView(selenium,
						strViewName, strResrctTypName, strResource, strRTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName, statrNumTypeName};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//6 Check Status types displayed in Resource detail view
			try {
			    assertEquals("", strFuncResult);

			    strFuncResult = objViews
			      .navToViewResourceDetailPageWitoutWaitForPgeLoad(
			        selenium, strResource1);
			   } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			   }
			   
			   try {
			    assertEquals("", strFuncResult);

			    String[] strStatType={statrNedHocTypeName, statrNumTypeName};
			    strFuncResult = objViews.verifySTInViewResDetailUnderSection(
			      selenium, strSection1, strSectionValue, strStatType);
			   } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			   }
			   
			   
			// Check Status types displayed in Sub resource detail
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName, statrNumTypeName};
				strFuncResult = objViews.verifySTInViewResDetailUnderSubResource(selenium, strSubResrctTypName, strStatType, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// 1 Check Status types displayed in View Map
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statrNumTypeName,statrNedHocTypeName  };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			//2 Check Status types displayed in Custom view map
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
				String[] strRoleStatType = { statrNumTypeName,statrNedHocTypeName  };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			//3 Check Status types displayed in Custom view table
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strStatTypeArr = { statrNedHocTypeName,statrNumTypeName };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(selenium, strResrctTypName, strResource1, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//5 Check Status types displayed in Event detail
			try {
				String[] strStatusTypeArr = {statrNedHocTypeName,statrNumTypeName  };
				assertEquals("", strFuncResult);
				strFuncResult = objEventlist.checkInEventBanner(selenium, strEveName, strResrctTypName, strResource1, strStatusTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Deactivate Nedoc ST
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrNedHocTypeName, strNDSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(
						selenium, statrNedHocTypeName, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			// Check Status types not displayed in 4 View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium, statrNedHocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//6Check Status types not displayed in  Resource detail view
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName};
				strFuncResult = objViews.verifySTInViewResDetailFalse(selenium, strSection1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Check Status types not displayed in  Sub resource detail
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName};
				strFuncResult = objViews.verifySTInViewResDetailUnderSubResourceFalse(selenium, strSubResrctTypName, strStatType, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Check Status types not displayed in  1 View Map
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
			    assertEquals("", strFuncResult);

			    String[] strEventStatType = {};
			    String[] strRoleStatType = { statrNedHocTypeName };
			    strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
			      selenium, strResource1, strEventStatType,
			      strRoleStatType, false, false);
			 } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			 }

			//2 Check Status types not displayed in  Custom view map
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
			    String[] strRoleStatType = {statrNedHocTypeName};
			    strFuncResult = objViewMap
			      .verifyStatTypesInResourcePopup_ShowMap(selenium,
			        strResource1, strEventStatType,
			        strRoleStatType, false, false);
			 } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			 }
			//3 Check Status types not displayed in  Custom view table
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] strRoleStatType = {statrNedHocTypeName};
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSTInViewCustTablFalse(selenium, strResrctTypName, strResource1, strRoleStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//5Check Status types not displayed in  Event detail
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] strStatTypeArr2 = { statrNedHocTypeName };
				assertEquals("", strFuncResult);
				strFuncResult = objEventlist.checkSTypesEvntBanner(selenium,
						strEveName, strResrctTypName, strStatTypeArr2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason +strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "119789"; // Test Case Id
			gstrTO = "Verify that a NEDOCS status type can be deactivated.";// TO
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
	
	/*****************************************************************
	'Description :Verify that a NEDOCS status type can be deactivated.	              
	'Arguments	 :None
	'Returns	 :None
	'Date		 :6/June/2013
	'Author		 :QSG
	'-----------------------------------------------------------------
	'Modified Date				                          Modified By
	'Date					                               Name
	******************************************************************/

	@Test
	public void testEdgeCase119790() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		Roles objRoles = new Roles();
		EventList objEventlist = new EventList();
		EventSetup objEventSetup = new EventSetup();
		EventBanner objEventBanner = new EventBanner();
		Preferences objPreferences = new Preferences();

		try {
			gstrTCID = "119790"; // Test Case Id
			gstrTO = "Verify that a NEDOCS status type can be reactivated.";// TO
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
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statrNumTypeName = "NST" + strTimeText;
			String statrNedHocTypeName = "NEDOC" + strTimeText;
			String strStatTypDefn = "Automation";
			String strSTvalue[] = new String[2];
			String strNDSTValue = "NEDOCS Calculation";
			String strNSTValue = "Number";

			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strResource1 = "AutoRs_1" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
     		String strAbbrv = "A" + strTmText;
			String strResVal = "";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[2];
			// Search Resource criteria
			String strCategory = rdExcel.readInfoExcel("Find Res", 4, 3,
					strFILE_PATH);
			String strRegion = rdExcel.readInfoExcel("Find Res", 4, 4,
					strFILE_PATH);
			String strCityZipCd = rdExcel.readInfoExcel("Find Res", 4, 5,
					strFILE_PATH);
			String strState = rdExcel.readInfoExcel("Find Res", 4, 6,
					strFILE_PATH);
			
			String strUserName_A = "AutoUsr_A" + System.currentTimeMillis();
			String strUsrFulName_A = strUserName_A;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			
			//Sub resource
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			//View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// ET
			String strTempName1 = "ET1_" + strTimeText;
			String strTempDef = "Automation";
			String strEveColor = "Black";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			
			// sec data
			String strSection1 = "Sec_" + strTimeText;
			String strSectionValue = "";
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " starts----------");

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
			// NEDOCST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedHocTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNedHocTypeName);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * 3. Resource type 'RT' is created and is associated with the NEDHOC status types.
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

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*Create sub resource type SRT*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strSubResrctTypName,
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
				strFuncResult = objRT
						.selAndDeselSubResourceType(seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition,
						strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
			strState = "Alabama";
		    strCountry = "Barbour County";
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5. Sub-resource 'SRS' is created under parent resource 'RS'
			 * selecting 'SRT' sub-resource type.
			 */

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
						strSubResource, strAbbrv, strSubResrctTypName,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchSubResValueInResList(seleniumPrecondition, strSubResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
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
				String strSTvalues[][] = {{ strSTvalue[0], "true" },{ strSTvalue[1], "true" }};
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
			    assertEquals("", strFuncResult);
			    String strOptions = propElementDetails
			      .getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
			    strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
			      strOptions, true);

			   } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			  }
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0], false, true,
						false, true);
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
			
			// Create View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				String strRSValues[]={strRSValue[0]};
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Event template Creation
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
				String[] strResTypeVal={strRTvalue[0]};
				String[] strStatusTypeval={strSTvalue[0],strSTvalue[1]};
				strFuncResult = objEventSetup.fillMandfieldsEveTempNew(seleniumPrecondition,
						strTempName1, strTempDef, strEveColor, true,
						strResTypeVal, strStatusTypeval, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Event Creation
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(seleniumPrecondition,
						strTempName1, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.slectAndDeselectRSEditEventPage(seleniumPrecondition,
						strResource1, true, false);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Section
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
				strFuncResult = objViews.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNedHocTypeName ,statrNumTypeName};
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strStatTypeArr, strSection1, true);
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
			//Login as user A
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditSubResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { strSTvalue[0],strSTvalue[1] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								seleniumPrecondition, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //Add Custom View
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objPreferences.navEditCustomViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.findResourcesAndAddToCustomView(
						seleniumPrecondition, strResource1, strCategory, strRegion,
						strCityZipCd, strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatusType = { statrNedHocTypeName,
						statrNumTypeName };
				strFuncResult = objPreferences.editCustomViewOptions(
						seleniumPrecondition, strStatusType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
					
			//Login as user A
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName_A,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			//Deactivate Nedoc ST
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrNedHocTypeName, strNDSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(
						selenium, statrNedHocTypeName, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			// Check Status types not displayed in 4 View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkStatusTypeInViewScreen(selenium, statrNedHocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//6Check Status types not displayed in  Resource detail view
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {

				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName};
				strFuncResult = objViews.verifySTInViewResDetailFalse(selenium, strSection1, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Check Status types not displayed in  Sub resource detail
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName};
				strFuncResult = objViews.verifySTInViewResDetailUnderSubResourceFalse(selenium, strSubResrctTypName, strStatType, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Check Status types not displayed in  1 View Map
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
			    assertEquals("", strFuncResult);
			    String[] strEventStatType = {};
			    String[] strRoleStatType = { statrNedHocTypeName };
			    strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
			      selenium, strResource1, strEventStatType,
			      strRoleStatType, false, false);
			 } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			 }

			//2 Check Status types not displayed in  Custom view map
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
			    String[] strRoleStatType = {statrNedHocTypeName};
			    strFuncResult = objViewMap
			      .verifyStatTypesInResourcePopup_ShowMap(selenium,
			        strResource1, strEventStatType,
			        strRoleStatType, false, false);
			 } catch (AssertionError Ae) {
			    gstrReason = strFuncResult;
			 }
			//3 Check Status types not displayed in  Custom view table
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] strRoleStatType = {statrNedHocTypeName};
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkSTInViewCustTablFalse(selenium, strResrctTypName, strResource1, strRoleStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//5Check Status types not displayed in  Event detail
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventBanner.clickEventWithFocusOnEveBanner(
						selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String[] strStatTypeArr2 = { statrNedHocTypeName };
				assertEquals("", strFuncResult);
				strFuncResult = objEventlist.checkSTypesEvntBanner(selenium,
						strEveName, strResrctTypName, strStatTypeArr2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	   //////////////////////////////////////////////////////////////////////////
					
             //Reactivate Nedoc ST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrNedHocTypeName, strNDSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.activateAndDeactivateST(
						selenium, statrNedHocTypeName, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectDesectIncludeInactiveST(
						selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// 4 Check Status types displayed in View
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName, strResource,
						strRTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrNedHocTypeName, statrNumTypeName };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//6 Check Status types displayed in Resource detail view
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String[] strStatType = { statrNedHocTypeName, statrNumTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			   
			   
			// Check Status types displayed in Sub resource detail
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNedHocTypeName, statrNumTypeName};
				strFuncResult = objViews.verifySTInViewResDetailUnderSubResource(selenium, strSubResrctTypName, strStatType, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// 1 Check Status types displayed in View Map
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strEventStatType = {};
				String[] strRoleStatType = { statrNumTypeName,statrNedHocTypeName  };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			//2 Check Status types displayed in Custom view map
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
				String[] strRoleStatType = { statrNumTypeName,statrNedHocTypeName  };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			//3 Check Status types displayed in Custom view table
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrNedHocTypeName,
						statrNumTypeName };
				strFuncResult = objViews.checkResTypeRSAndSTInViewCustTabl(
						selenium, strResrctTypName, strResource1,
						strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//5 Check Status types displayed in Event detail
			try {
				String[] strStatusTypeArr = { statrNedHocTypeName,
						statrNumTypeName };
				assertEquals("", strFuncResult);
				strFuncResult = objEventlist.checkInEventBanner(selenium,
						strEveName, strResrctTypName, strResource1,
						strStatusTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "119790"; // Test Case Id
			gstrTO = "Verify that a NEDOCS status type can be reactivated.";// TO
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
