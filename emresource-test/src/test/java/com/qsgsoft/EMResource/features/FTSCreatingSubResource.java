package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*******************************************************************
' Description	    :This class contains test cases from requirement
' Requirement Group :Resource Hierarchies
' Requirement       :Creating Sub-Resource 
' Product		    :EMResource v3.23
' Date			    :22/July/2013
' Author		    :QSG
-------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class FTSCreatingSubResource {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features."
			+ "FTSCreatingSubResource");
	static {
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild,
			gstrBrowserName, strSessionId,gstrTimetaken;
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

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		// kill browser
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
	
	//start//testFTS122254//
	/****************************************************************
	'Description	:Verify that sub-resources are not listed if a  
	               sub-resource typeassociated with it is deactivated.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/22/2013
	'Author			:QSG
	'----------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*****************************************************************/

	@Test
	public void testFTS122254() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		General objGeneral = new General();
		CreateUsers objCreateUsers=new CreateUsers();
		Resources objResources = new Resources();
		try {
			gstrTCID = "122254"; // Test Case Id
			gstrTO = " Verify that sub-resources are not listed if a sub-resource type associated "
					+ "with it is deactivated.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];

			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strSubResource1 = "ASubRs1_" + strTimeText;
			String strSubResource2 = "ASubRs2_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[4];
			
			//User
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

		/*
		* STEP :
		  Action:Preconditions:
			1. Status type ST1 and ST2 are created in region RG1.
			2. Resource Type (Normal) RT1 is created selecting status type ST1.
			3. Resource 'RS1' and 'RS2' are created selecting RT1.
			4. Sub-Resource 'SRT1' is created selecting status type ST2.
			5. Sub-resource 'SRS1' and 'SRS2' are created selecting sub-resource type under parent resource 'RS1'
			6. User 'U1' is created selecting 'Setup Resource Types' right and 'View' and 'Update'
			 right on resource 'RS1' and 'RS2'
		  Expected Result:No Expected Result
		*/
		//658095
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
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
								+ strSTvalue[0] + "']");
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

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
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
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0]  = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0] .compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            
			//SRS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[2] = objResources.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource1);
				if (strRSValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SRS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource2, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[3] = objResources.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource2);
				if (strRSValue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
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
		//658096
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:'Resource List' screen is displayed.
		  Resource 'RS1' and 'RS2' are listed under it.
		  'Edit' , 'Status Types' , 'Sub-Resources' links are displayed next to resources.
		  'Sub-resources(2(number of sub-resources))'  is displayed for resource 'RS1'
		   Sub-resource link is displayed for resource 'RS2' 
		  'Create New Resource' button is present at top left corner.
		*/
		//658097
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPgeNew(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPgeNew(
						selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (2)";
				strFuncResult = objResources.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateRes.CreateNewResource");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New Resource ' button is present at top left corner.");
				} else {
					log4j.info("'Create New Resource ' button is  NOT present at top left corner.");
					strFuncResult = "'Create New Resource ' button is NOT present at top left corner.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resource Types
		  Expected Result:'Resource Type List' screen is displayed.
          'Edit' link is displayed next to all 'Resource Types'.
		*/
		//658098
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.savRTandVerifyDataInRTListPage(selenium,
						strResrctTypName, "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link associated with Sub-resource type 'SRT1'
		  Expected Result:'Edit Resource Type' screen is displayed.
          'Active' check box is selected.
		*/
		//658099

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						selenium, strSubResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.vrfyActiveChkBoxSelOrNotForRT(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect 'Active' check-box and click on 'Save'
		  Expected Result:'Resource Type List' screen is displayed. 
          Resource Type 'SRT1' is not displayed. (Displayed only if 'include inactive 
          resource types' check box is selected.)
		*/
		//658100
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.activateAndDeactivateRTNew(selenium,
						strSubResrctTypName, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:'Resource List' screen is displayed.
		  Resource 'RS1' and 'RS2' are listed under it.
		  'Sub-resources count corresponding to resource 'RS1' is decremented by 2.
		*/
		//658101
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources";
				strFuncResult = objResources.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the 'Sub-resource' link corresponding to the resource 'RS1'
		  Expected Result:Sub resources are not listed on 'Sub-Resource List for < RS1 >' screen
		*/
		//658102
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource2, false);
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
			gstrTCID = "FTS-122254";
			gstrTO = "Verify that sub-resources are not listed if a sub-resource type associated with it is deactivated.";
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

	//end//testFTS122254//
	
 //start//testFTS107904//
	/******************************************************************************
	'Description	:Verify that appropriate message is displayed when attempted to 
	                 create duplicate sub-resources under the same parent resource.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/22/2013
	'Author			:QSG
	'------------------------------------------------------------------------------
	'Modified Date				                                       Modified By
	'Date					                                           Name
	*******************************************************************************/

	@Test
	public void testFTS107904() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		General objGeneral = new General();
		CreateUsers objCreateUsers=new CreateUsers();
		Resources objResources = new Resources();
		try {
			gstrTCID = "107904"; // Test Case Id
			gstrTO = " Verify that appropriate message is displayed when attempted to create " +
					"duplicate sub-resources under the same parent resource.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTmText = dts.getCurrentDate("HHmm");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strStatType1 = "AutoNSt1_" + strTimeText;
			String strStatType2 = "AutoNSt2_" + strTimeText;
			String strNSTValue = "Number";
			String strStatTypDefn = "Auto";
			String strSTvalue[] = new String[2];

			// RT
			String strResrctTypName = "AutoRt1_" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTValue[] = new String[2];

			// RS
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strSubResource1 = "ASubRs1_" + strTimeText;
			String strSubResource2 = "ASubRs2_" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strRSValue[] = new String[4];

			//User
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel(
					"User_Template", 7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

		/*
		* STEP :
		  Action:Preconditions:
			1. Status type ST1 and ST2 are created in region RG1.
			2. Resource Type (Normal) RT1 is created selecting status type ST1.
			3. Resource 'RS1' and 'RS2' are created selecting RT1.
			4. Sub-Resource 'SRT1' is created selecting status type ST2.
			5. Sub-resource 'SRS1' and 'SRS2' are created selecting sub-resource type under parent resource 'RS1'
			6. User 'U1' is created selecting 'Setup Resource Types' right and 'View' and 'Update'
			 right on resource 'RS1' and 'RS2'
		  Expected Result:No Expected Result
		*/
		//658095
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				blnLogin = true;
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
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
						seleniumPrecondition, strNSTValue, strStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strStatType2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
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
								+ strSTvalue[0] + "']");
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

			// SRT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
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
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0]  = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0] .compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrctTypName, strStandResType, strContFName,
						strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            
			//SRS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[2] = objResources.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource1);
				if (strRSValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//SRS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource2, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[3] = objResources.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource2);
				if (strRSValue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource2, strRSValue[1],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
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
		//658096
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources
		  Expected Result:'Resource List' screen is displayed.
		  Resource 'RS1' and 'RS2' are listed under it.
		  'Edit' , 'Status Types' , 'Sub-Resources' links are displayed next to resources.
		  'Sub-resources(2(number of sub-resources))'  is displayed for resource 'RS1'
		   Sub-resource link is displayed for resource 'RS2' 
		  'Create New Resource' button is present at top left corner.
		*/
		//658097
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPgeNew(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyLinksUndrActionInRSListPgeNew(
						selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSubRSCount = "Sub-Resources (2)";
				strFuncResult = objResources.VerifySubResourceCountInRSList(selenium,
						strSubRSCount, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateRes.CreateNewResource");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New Resource ' button is present at top left corner.");
				} else {
					log4j.info("'Create New Resource ' button is  NOT present at top left corner.");
					strFuncResult = "'Create New Resource ' button is NOT present at top left corner.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Sub-Resource' link associated with resource 'RS1' 
		  Expected Result:	'Sub-resource List for < Resource Name >' screen is displayed.
		Sub-Resources 'SRS1' and 'SRS2' are listed under it.
		'Create New Sub-Resource' button is present at top left corner. 
		*/
		//658098
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource1, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifySubResource(selenium,
						strSubResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = propElementDetails
						.getProperty("CreateResource.ValuesubResource");
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("'Create New Sub-Resource' button is present at top left corner.");
				} else {
					log4j.info("'Create New Sub-Resource' button is  NOT present at top left corner.");
					strFuncResult = "'Create New Sub-Resource' button is NOT present at top left corner.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on Create New Sub-Resource button 
		  Expected Result:'Create New Sub-Resource for < Resource name>' screen is displayed. 
		*/
		//658099
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide 'Name' of sub-resource as 'SRS1' and fill other mandatory fields and click on 'Save' 
		  Expected Result:User is retained on 'Create New Sub-Resource for < Resource name>' screen .
			Sub-resource is not created.
			Following error message is displayed
			'The following error occurred on this page:
			�Duplicate sub-resource names are not allowed within a resource.' 
		*/
		//658100
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						selenium, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.savAndVrfyErrMsgForRSWithSubRS(
						selenium,strResource1, strSubResource1, strUserName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
		/*
		* STEP :
		  Action:Navigate to Setup >> Resources,Click on 'Sub-Resource' link associated with resource 'RS2' 
		  Expected Result:'Sub-resource List for < Resource Name >' screen is displayed. 
		*/
		//658101
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditSubResourcePage(
						selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Sub-resource' button ,Provide 'Name' of sub-resource as
		   'SRS1' and fill other mandatory fields and click on 'Save' 
		  Expected Result:'Sub-resource List for < Resource Name >' screen is displayed.
          Sub-Resources 'SRS1' is listed under it. 
		*/
		//658102
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateSubResourcePage(
						selenium, strResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createSubResourceWitLookUPadres(
						selenium, strSubResource1, strAbbrv,
						strSubResrctTypName, strStandResType, true,
						strContFName, strContLName, strState, strCountry);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifySubResourceInRSList(
						selenium, strSubResource1, strResource2);
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
			gstrTCID = "FTS-107904";
			gstrTO = " Verify that appropriate message is displayed when attempted to create " +
					"duplicate sub-resources under the same parent resource.";// TO
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

	//end//testFTS107904//
}
