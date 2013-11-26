package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/**********************************************************************
' Description :This class includes Delete Folder requirement testcases
' Requirement :CreateResourceType
' Date		  :16-Jan-2013
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                                       Modified By
' <Date>                           	                  <Name>
'*******************************************************************/

public class FTSCreateResource  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateResource");
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

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
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

		  gstrReason=gstrReason.replaceAll("'", " ");
		  objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
		    gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
		    gslsysDateTime, gstrBrowserName, gstrBuild,strSessionId);
	}
	
	//start//testFTS78711//
	/***********************************************************************************************
	'Description	:Verify that, while creating a resource, values can be provided in 'Title' field
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:8/8/2013
	'Author			:QSG
	'-----------------------------------------------------------------------------------------------
	'Modified Date				                                            Modified By
	'Date					                                                Name
	************************************************************************************************/

	@Test
	public void testFTS78711() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		MobileView objMobileView = new MobileView();
		try {
			gstrTCID = "78711"; // Test Case Id
			gstrTO = " Verify that, while creating a resource, values can be provided in 'Title' field";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			// ST
			String strNumStatType1 = "AutoNSt1_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strNSTValue = "Number";
			String strSTvalue[] = new String[2];
			// RT
			String strResrctTypName = "AutoRt_" + strTimeText;
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strTmText = dts.getCurrentDate("HHmm");
			String strState = "Alabama";
			String strCountry = "Barbour County";
			String strTitle = "Resourece_Creation";
			String strEditTitle = "Resourece_Edition";
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[1];
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			/*
			* STEP :
			  Action:1. Resource type is created selectign 'Contact Title' check box.
				2. Resource RS is created under RT providing value for 'Contact Title' field.
				3. Provided value is displayed on 'View Resource Detail' screen 
				4. It is also displayed on Mobile View screen.
							  Expected Result:No Expected Result
			*/
			//474636

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION ENDS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Role based ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, strNumStatType1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, strNumStatType1);
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
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrctTypName, strSTvalue[0]);
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
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresNew(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
				seleniumPrecondition.type(
						propElementDetails.getProperty("CreateResource.Title"),
						strTitle);
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
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);
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
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatusTypeVal = { strSTvalue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strStatusTypeVal, false, strRSValue);
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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
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
				strFuncResult = objRT.navToeditResrcTypepage(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.chkContactFieldsChkOrNot(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.cancelAndNavToResourceTypeList(selenium);
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
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource);
				selenium.type(
						propElementDetails.getProperty("CreateResource.Title"),
						strEditTitle);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResourceInRSList(selenium,
						strResource);
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
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = { { "Contact Title:",
						"Resourece_Edition" } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToViewsInMobileView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objMobileView.navToUserViewsInMob(selenium,
						strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				String[][] strResouceData = { { "Title:", "Resourece_Edition" } };
				strFuncResult = objMobileView.checkResouceDetailInMobileview(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError ae) {
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
			gstrTCID = "FTS-78711";
			gstrTO = "Verify that, while creating a resource, values can be provided in 'Title' field";
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

			// end//testFTS78711//
		}
	}

	//start//testFTS112460//
	/***************************************************************
	'Description		:Enter data in all the fields and cancel the process of creating the resource and verify that the resource is not created.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/2/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS112460() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		Date_Time_settings dts = new Date_Time_settings();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();

		try {
			gstrTCID = "112460"; // Test Case Id
			gstrTO = " Enter data in all the fields and cancel the process of creating the resource and verify that the resource is not created.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "NST" + strTimeText;
			String statrTextTypeName = "TST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statrNedocTypeName = "NDST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];
			String str_roleStatusValue[] = new String[2];

			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String strStatTypeColor = "Black";

			// ROLE
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];

			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'NST',
			 * 'MST', 'TST' and 'SST' are created selecting role 'R1'. <br>
			 * <br>2. Resource Type 'RT' is created selecting status types
			 * 'NST', 'MST', 'TST' and 'SST' Expected Result:No Expected Result
			 */
			// 625379
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Roles
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < str_roleStatusTypeValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							str_roleStatusTypeValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * STEP : Action:Login as 'RegAdmin' to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 625380

			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

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
			 * STEP : Action:Navigaet to Setup >> Resources ,Select 'include
			 * inactive resources' checkbox. Expected Result:'Resource List'
			 * screen is displayed with the following column headers : <br>
			 * <br>Action <br>Icon <br>Active <br>HAvBED <br>AHA ID <br>Name
			 * <br>Abbreviation <br>Resource Type <br> <br>'Create New Resource'
			 * button is displayed at top left of the screen.
			 */
			// 625381

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselIncludInactiveResrc(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.verifyResourceHeaderInResrcListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Resource' button Expected
			 * Result:'Create New Resource' screen is displayed.
			 */
			// 625382

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Enter data in all the fields of 'Create New
			 * Resource' screen and click on 'Cancel' Expected Result:'Resource
			 * List' screen is displayed. <br> <br>Resource is not created.
			 */
			// 625383

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.cancelAndNavToRSListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.verifyResourceNotListedInResrcLstPge(selenium,
								strResource);
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-112460";
			gstrTO = "Enter data in all the fields and cancel the process of creating the resource and verify that the resource is not created.";
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

	// end//testFTS112460//
	//start//testFTS112457//
	/***************************************************************
	'Description		:Verify that an appropriate message is displayed when the option 'Reports HAvBED Data' is selected without selecting a State.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/3/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS112457() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		Date_Time_settings dts = new Date_Time_settings();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();

		try {
			gstrTCID = "112457"; // Test Case Id
			gstrTO = " Verify that an appropriate message is displayed when the option 'Reports HAvBED Data' is selected without selecting a State.";// Test
																																						// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "NST" + strTimeText;
			String statrTextTypeName = "TST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statrNedocTypeName = "NDST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];
			String str_roleStatusValue[] = new String[2];

			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String strStatTypeColor = "Black";

			// ROLE
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];

			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strOptions = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'NST',
			 * 'MST', 'TST' and 'SST' are created selecting role 'R1'. <br>
			 * <br>2. Resource Type 'RT' is created selecting status types
			 * 'NST', 'MST', 'TST' and 'SST' <br> <br>3. User 'U1' is created
			 * selecting 'Setup Resource' and 'Configure region Views' right.
			 * Expected Result:No Expected Result
			 */
			// 625241

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Roles
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < str_roleStatusTypeValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							str_roleStatusTypeValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 625242

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigaet to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed. <br> <br>'Create New
			 * Resource' button is displayed at top left of the screen.
			 */
			// 625243

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				assertTrue(selenium
						.isElementPresent("css=input[value='Create New Resource']"));
				log4j.info("'Create New Resource' button is present. ");
			} catch (AssertionError Ae) {
				strFuncResult = "'Create New Resource' button is NOT present. ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Resource' button Expected
			 * Result:'Create New Resource' screen is displayed.
			 */
			// 625244

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			/*
			 * STEP : Action:Enter data in mandatory fields, do not select any
			 * state, select check box corresponding to 'Reports HAvBED data'
			 * and Click on 'Save' Expected Result:Following error message is
			 * displayed on 'Create New Resource' screen <br> <br>'�The 'State'
			 * is required for resources that report HAvBED data.'
			 */
			// 625245

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource, strAbbrv, strResrcTypName, "Hospital",
						"FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.selAndDeselRepHavBedChkBox(selenium, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVrfyErrMsgRepHavBedData(selenium);

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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-112457";
			gstrTO = "Verify that an appropriate message is displayed when the option 'Reports HAvBED Data' is selected without selecting a State.";
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

	// end//testFTS112457//
	// start//testFTS112458//
	/***************************************************************
	 * 'Description :Verify that a resource can be saved by selecting the option
	 * 'Reports HAvBED Data' after selecting a State. 'Precondition : 'Arguments
	 * :None 'Returns :None 'Date :9/4/2013 'Author :QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date Modified By 'Date Name
	 ***************************************************************/

	@Test
	public void testFTS112458() throws Exception {

		boolean blnLogin = false;// keep track of logout of application
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login

		CreateUsers objCreateUsers = new CreateUsers();
		Date_Time_settings dts = new Date_Time_settings();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		Resources objRs = new Resources();
		try {
			gstrTCID = "112458"; // Test Case Id
			gstrTO = " Verify that a resource can be saved by selecting the option 'Reports HAvBED Data' after selecting a State.";// Test
			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "NST" + strTimeText;
			String statrTextTypeName = "TST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statrNedocTypeName = "NDST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];
			String str_roleStatusValue[] = new String[2];

			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String strStatTypeColor = "Black";

			// ROLE
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];

			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strOptions = "";

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strState = "Alabama";
			String strCountry = "Barbour County";

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'NST',
			 * 'MST', 'TST' and 'SST' are created selecting role 'R1'. <br>
			 * <br>2. Resource Type 'RT' is created selecting status types
			 * 'NST', 'MST', 'TST' and 'SST' <br> <br>3. User 'U1' is created
			 * selecting 'Setup Resource' and 'Configure region Views' right.
			 * Expected Result:No Expected Result
			 */
			// 625280

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Roles
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < str_roleStatusTypeValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							str_roleStatusTypeValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 625281

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigaet to Setup >> Resources Expected
			 * Result:'Resource List' screen is displayed. <br> <br>'Create New
			 * Resource' button is displayed at top left of the screen.
			 */
			// 625282

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				assertTrue(selenium
						.isElementPresent("css=input[value='Create New Resource']"));
				log4j.info("'Create New Resource' button is present. ");
			} catch (AssertionError Ae) {
				strFuncResult = "'Create New Resource' button is NOT present. ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New Resource' button Expected
			 * Result:'Create New Resource' screen is displayed.
			 */
			// 625283

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresNew(selenium,
						strResource, strAbbrv, strResrcTypName, "FN", "LN",
						strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Enter data in mandatory fields, select state from
			 * state dropdown , select check box corresponding to 'Reports
			 * HAvBED data' and Click on 'Save' Expected Result:Resource 'RS' is
			 * listed under 'Resource List' screen. <br> <br>'Yes' is displayed
			 * under HAvBED column for resource 'RS'.
			 */
			// 625284

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.selAndDeselRepHavBedChkBox(selenium, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(selenium,
						strResource, "Yes", "", strAbbrv, strResrcTypName);
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-112458";
			gstrTO = "Verify that a resource can be saved by selecting the option 'Reports HAvBED Data' after selecting a State.";
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

	// end//testFTS112458//
	
	//start//testFTS112451//
			/***************************************************************
			'Description		:Verify that resource can be created providing data in all the fields
			'Precondition		:
			'Arguments			:None
			'Returns			:None
			'Date				:9/2/2013
			'Author				:QSG
			'---------------------------------------------------------------
			'Modified Date				            Modified By
			'Date					                Name
			***************************************************************/

		@Test
		public void testFTS112451() throws Exception {

			String strFuncResult = "";
			boolean blnLogin = false;
			Login objLogin = new Login();
			StatusTypes objStatusTypes = new StatusTypes();
			Roles objRoles = new Roles();
			ResourceTypes objResourceTypes = new ResourceTypes();
			Resources objResources = new Resources();
			Views objViews = new Views();
			ViewMap objViewMap = new ViewMap();
			General objGeneral = new General();
			CreateUsers objCreateUsers = new CreateUsers();
			try {
				gstrTCID = "112451"; // Test Case Id
				gstrTO = " Verify that resource can be created providing data in all the fields";// Test
																									// Objective
				gstrReason = "";
				gstrResult = "FAIL";

				Date_Time_settings dts = new Date_Time_settings();
				gstrTimetake = dts.timeNow("HH:mm:ss");
				String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
				String strLoginUserName = rdExcel.readData("Login", 3, 1);
				String strLoginPassword = rdExcel.readData("Login", 3, 2);
				String strRegn = rdExcel.readData("Login", 3, 4);
				String strFILE_PATH = pathProps.getProperty("TestData_path");
				String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"User_Template", 7, 12, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
						13, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
						14, strFILE_PATH);
				String strInitPwd = rdExcel.readData("Login", 4, 2);
				String strConfirmPwd = rdExcel.readData("Login", 4, 2);
				String ambulanceImg = rdExcel.readInfoExcel("ResourceIcon", 4,
						2, strFILE_PATH);

				String strStatusNumValue = "Number";
				String strStatusMulValue = "Multi";
				String strStatusTextValue = "Text";
				String strStatusSSValue = "Saturation Score";

				String statTypeName1 = "Nst" + strTimeText;
				String statTypeName2 = "Mst" + strTimeText;
				String statTypeName3 = "Tst" + strTimeText;
				String statTypeName4 = "Sst" + strTimeText;

				String strStatTypDefn = "Def";
				String strStatTypeColor = "Black";
				String strStatusName1 = "Sta" + strTimeText;
				String strStatusName2 = "Stb" + strTimeText;

				String strResrcTypName = "Resty_1" + strTimeText;
				String strContFName = "FirstName";
				String strContLName = "LastName";
				String strResource = "Res" + strTimeText;
				String strRoleName = "Rol_1" + strTimeText;
				String strAbbrv = "Abb";
				String strStandResType = "Ambulance";
				String strState = "Alabama";
				String strCounty = "Barbour County";
				
				String strUsrFulName = "UserName";
				String strUserName = "User_1" + System.currentTimeMillis();

				String strStreetAddr = "Street Adderss";
				String strCity = "Florida";
				String strZipCode = "020-40-30";
				String strWebSite = "www.qsgsoft.com";
				String strTitle = "Res_Title";
				String strContAddr = "Res_Address";
				String strContPh1 = "900300400";
				String strContPh2 = "900300401";
				String strContFax = "401-301-56";
				String strContEmail = "autoemr@qsgsoft.com";
				String strNotes = "This is Automation notes";
				String strHavBed = "Yes";

				String strViewName = "View_1" + strTimeText;
				String strVewDescription = "ViewDes" + strTimeText;
				String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

				String strSTvalue[] = new String[4];
				String strRoleValue[] = new String[1];
				String strRTvalue[] = new String[1];
				String strRSValue[] = new String[1];
				String strStatusValue[] = new String[2];
				String strLTValue[] = new String[2];

				/*
				 * STEP : Action:Preconditions:
				 * 
				 * 1. Status types 'NST', 'MST', 'TST' and 'SST' are created
				 * selecting role 'R1'.
				 * 
				 * 2. Resource Type 'RT' is created selecting status types 'NST',
				 * 'MST', 'TST' and 'SST' Expected Result:No Expected Result
				 */
				// 624921

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION STARTS~~~~~");
				// Login
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
							seleniumPrecondition, strRegn);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating NST
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
							seleniumPrecondition, strStatusNumValue, statTypeName1,
							strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
							seleniumPrecondition, statTypeName1);
					if (strSTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating MST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusMulValue, statTypeName2,
							strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
							seleniumPrecondition, statTypeName2);
					if (strSTvalue[1].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating STATUSES for MST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
							seleniumPrecondition, statTypeName2, strStatusName1,
							strStatusMulValue, strStatTypeColor, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
							seleniumPrecondition, statTypeName2, strStatusName2,
							strStatusMulValue, strStatTypeColor, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
							seleniumPrecondition, statTypeName2, strStatusName1);
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
							seleniumPrecondition, statTypeName2, strStatusName2);
					if (strStatusValue[1].compareTo("") != 0) {
						strFuncResult = "";

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
							seleniumPrecondition, strStatusTextValue,
							statTypeName3, strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
							seleniumPrecondition, statTypeName3);
					if (strSTvalue[2].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating SST
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
							seleniumPrecondition, strStatusSSValue, statTypeName4,
							strStatTypDefn, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
							seleniumPrecondition, statTypeName4);
					if (strSTvalue[3].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch status type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating Role

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
							strSTvalue[2], strSTvalue[3] };
					String[][] strRoleRightss = {};
					strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
							seleniumPrecondition, strRoleName, strRoleRightss,
							updateRightValue, true, updateRightValue, true, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
							seleniumPrecondition, strRoleName);
					if (strRoleValue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch role value";
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Creating RT
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
							seleniumPrecondition, strResrcTypName, strSTvalue[0]);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.saveAndvrfyResType(
							seleniumPrecondition, strResrcTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRTvalue[0] = objResourceTypes
							.fetchResTypeValueInResTypeList(seleniumPrecondition,
									strResrcTypName);
					if (strRTvalue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch resource type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				// Selecting MST,TST,SST in RT

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.navToeditResrcTypepage(
							seleniumPrecondition, strResrcTypName);
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
					strFuncResult = objResourceTypes.saveAndvrfyResType(
							seleniumPrecondition, strResrcTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				//Creating U1
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
					strFuncResult = objCreateUsers.slectAndDeselectRole(
							seleniumPrecondition, strRoleValue[0], true);
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

				log4j.info("~~~~~PRECONDITION - " + gstrTCID
						+ " EXECUTION ENDS~~~~~");
				/*
				 * STEP : Action:Login as 'RegAdmin' Expected Result:'Region
				 * Default' screen is displayed.
				 */
				// 624922
				log4j.info("~~~~~TEST CASE - " + gstrTCID
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
					strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Navigaet to Setup >> Resources ,Select 'include
				 * inactive resources' checkbox. Expected Result:'Resource List'
				 * screen is displayed with the following column headers :
				 * 
				 * Action Icon Active HAvBED AHA ID Name Abbreviation Resource Type
				 * 
				 * 'Create New Resource' button is displayed at top left of the
				 * screen.
				 */
				// 624990

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navResourcesList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.selAndDeselIncludInactiveResrc(
							selenium, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources
							.verifyResourceHeaderInResrcListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.selAndDeselIncludInactiveResrc(
							selenium, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Click on 'Create New Resource' button Expected
				 * Result:'Create New Resource' screen is displayed.
				 */
				// 624992

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navToCreateResourcePage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Create resource 'RS',entering data in all the
				 * fields of 'Create New Resource' screen and click on 'Save'
				 * Expected Result:Assign Users to < Resource name > screen is
				 * displayed. Associated With,Update Status,Run Reports and View
				 * Resource check boxes are associated with each user. None of the
				 * check boxes corresponding to any users is selected.
				 */
				// 624994

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.createResource_FillAllFieldsNew(
							selenium, strResource, strAbbrv, strResrcTypName,
							strStandResType, true, false, "", "", false,
							strStreetAddr, strCity, strState, strZipCode,
							strCounty, strWebSite, strContFName, strContLName,
							strTitle, strContAddr, strContPh1, strContPh2,
							strContFax, strContEmail, strNotes);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
							strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String strElementID = "//table[@id='tbl_association']/tbody/tr/td[text()='"
							+ strUserName + "']";
					strFuncResult = objGeneral
							.CheckForElements(selenium, strElementID);
					if (strFuncResult.equals("")) {
						log4j.info("User '"
								+ strUserName
								+ "' is listed under 'Assign Users to < resource name > ' screen.");
					} else {
						log4j.info("User '"
								+ strUserName
								+ "' is NOT listed under 'Assign Users to < resource name > ' screen.");
						gstrReason = strFuncResult;
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.assignUserRights(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.checkUserRightsInAssignUserPage(
							selenium, strUserName, false, false, false, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Click on 'Save' Expected Result:Resource is listed
				 * under 'Resource List' screen with appropriate data under
				 * following column headers :
				 * 
				 * i. Action (Edit,Status Types,Users,Sub-Resources,Demote links are
				 * displayed) ii. Icon (Selected resource icon while creating
				 * resource) iii. Active (Active) iv. HAvBED (yes) v. AHA ID
				 * (provided ID) vi. Name (Name of the resource) vii. Abbreviation
				 * (Abbreviation provided while creating resource) viii. Resource
				 * Type (Selected resource type)
				 */
				// 624995

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndVerifyResourceInRSList(
							selenium, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strRSValue[0] = objResources.fetchResValueInResList(selenium,
							strResource);
					if (strRSValue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch resource type value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.navToEditResourcePage(selenium,
							strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strLTValue = objResources
							.fetchLatitudeAndLongitudeValue(selenium);
					if (strLTValue[0].compareTo("") != 0) {
						strFuncResult = "";
					} else {
						strFuncResult = "Failed to fetch Latitude and Longitude  value";
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.saveAndNavRSList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.verifyLinksUndrActionInRSListPge(
							selenium, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2]/img[@src='"+ambulanceImg+"']/ancestor::tr/"
							+ "td[text()='" + strResource + "']";
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElementID);
					if (strFuncResult.equals("")) {
						log4j.info("Icon is listed under resource " + strResource
								+ "In resource list page");
					} else {
						strFuncResult = "Icon is NOT listed under resource "
								+ strResource + "In resource list page";
						log4j.info("Icon is NOT listed under resource "
								+ strResource + "In resource list page ");
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.selAndDeselIncludInactiveResrc(
							selenium, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					String strElementID = "//table[@class='displayTable striped border sortable']/tbody/tr/td[6][text()='"
							+ strResource
							+ "']"
							+ "/parent::tr/td[3][text()='Active']";
					strFuncResult = objGeneral.checkForAnElements(selenium,
							strElementID);
					if (strFuncResult.equals("")) {
						log4j.info("Active is listed under resource " + strResource
								+ "In resource list page");
					} else {
						strFuncResult = "Active is NOT listed under resource "
								+ strResource + "In resource list page";
						log4j.info("Active is NOT listed under resource "
								+ strResource + "In resource list page ");
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.selAndDeselIncludInactiveResrc(
							selenium, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResources.verifyResourceInfoInResList(
							selenium, strResource, strHavBed, "", strAbbrv,
							strResrcTypName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Navigate to Setup >> Views, Create view 'V1'
				 * selecting status types 'NST', 'MST', 'TST', 'SST', resource RT
				 * and click on 'Save' Expected Result:View V1 is displayed under
				 * 'Region Views List' screen.
				 */
				// 625006

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.navRegionViewsList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.navToCreateViewPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.createViewNew(selenium, strViewName,
							strVewDescription, strViewType, true, false,
							strSTvalue, false, strRSValue, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.selAndDeselRTInEditView(selenium,
							strRTvalue[0], true, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Navigate to View >> 'V1' Expected Result:View V1
				 * screen is displayed with resource RS under RT along with status
				 * types 'NST', 'MST', 'TST' and 'SST' iTriage icon is displayed
				 * next to resource RS
				 */
				// 625012

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.navToUserView(selenium, strViewName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					String strRS[] = { strResource };
					String strST[] = { statTypeName2, statTypeName1, statTypeName4,
							statTypeName3 };
					assertEquals("", strFuncResult);
					strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
							strResrcTypName, strRS, strST);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Click on resource 'RS' Expected Result:Details of
				 * resource RS is displayed appropriately on 'View Resource Detail'
				 * screen.
				 * 
				 * Following details are displayed appropriately as provided while
				 * creating resource:
				 * 
				 * i. Type: (Resource type) ii. Address: (Provided resource address)
				 * iii. County: (selected county) iv. Lat/Longitude: (populated
				 * lat/lang values) v. EMResource/AHA ID: (Provided ID) vi. Website:
				 * (Provided website) vii. Contact: (Contact first name and last
				 * name) viii. Contact Title: (Contact title provided) ix. Phone 1:
				 * (provided phone no) x. Phone 2: (provided phone no) xi. Fax:
				 * (provided fax no) xii. Email: (Provided mail ID) xiii. Notes:
				 * (provided notes for resource)
				 */
				// 625013

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews
							.navToViewResourceDetailPageWitoutWaitForPgeLoad(
									selenium, strResource);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				String[][] strResouceData = {
						{ "Address:", strStreetAddr },
						{ "Contact Title:", strTitle },
						{ "Type:", strResrcTypName },
						{ "County:", strCounty },
						{ "Phone 1:", strContPh1 },
						{ "Phone 2:", strContPh2 },
						{ "Email:", strContEmail },
						{ "Notes:", strNotes },
						{ "Contact:", strContFName + " " + strContLName },
						{ "Lat/Longitude:",
								strLTValue[0] + " " + "/" + " " + strLTValue[1] } };
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.verifyResDetailsInViewResDetail(
							selenium, strResource, strResouceData);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Navigate to View >> Map Expected Result:'Regional
				 * Map View' screen is displayed.
				 */
				// 625014							
				/*
				 * STEP : Action:Select resource 'RS' from 'Find Resource' dropdown
				 * Expected Result:Resource RS is displayed on popup window on map
				 * with status types 'NST', 'MST', 'TST', 'SST' and 'View Info',
				 * 'Edit Info' links.
				 */
				// 625015
				
				

				try {
					assertEquals("", strFuncResult);
					String[] strEventStatType = {};
					String[] strRoleStatType = { statTypeName2, statTypeName1,
							statTypeName4, statTypeName3 };
					strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
							selenium, strResource, strEventStatType,
							strRoleStatType, false, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				/*
				 * STEP : Action:Click on View Info link on resource popup Expected
				 * Result:Following details are displayed appropriately as provided
				 * while creating resource on 'View Resource Detail' screen:
				 * 
				 * i. Type: (Resource type) ii. Address: (Provided resource address)
				 * iii. County: (selected county) iv. Lat/Longitude: (populated
				 * lat/lang values) v. EMResource/AHA ID: (Provided ID) vi. Website:
				 * (Provided website) vii. Contact: (Contact first name and last
				 * name) viii. Contact Title: (Contact title provided) ix. Phone 1:
				 * (provided phone no) x. Phone 2: (provided phone no) xi. Fax:
				 * (provided fax no) xii. Email: (Provided mail ID) xiii. Notes:
				 * (provided notes for resource)
				 */
				// 625016

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViewMap
							.navToViewResourceDetailPageFrmRSPopUp(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objViews.verifyResDetailsInViewResDetail(
							selenium, strResource, strResouceData);
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
				gstrTCID = "FTS-112451";
				gstrTO = "Verify that resource can be created providing data in all the fields";
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

		// end//testFTS112451//
		
     //start//testFTS112459//
	/**********************************************************************************************
	'Description	:Provide a valid website address and verify that the website address is displayed
	                 appropriately on the 'View Resource Detail' screen.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:9/10/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------
	'Modified Date				                                                      Modified By
	'Date					                                                          Name
	***********************************************************************************************/

	@Test
	public void testFTS112459() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		General objGeneral = new General();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRole = new Roles();
		Date_Time_settings dts = new Date_Time_settings();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		ViewMap objViewMap=new ViewMap();
		try {
			gstrTCID = "112459"; // Test Case Id
			gstrTO = "Provide a valid website address and verify that the website address is displayed " +
					"appropriately on the 'View Resource Detail' screen.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "NST" + strTimeText;
			String statrTextTypeName = "TST" + strTimeText;
			String statrMultiTypeName = "MST" + strTimeText;
			String statrSaturatTypeName = "SST" + strTimeText;
			String statrNedocTypeName = "NDST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];
			String str_roleStatusValue[] = new String[2];

			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String strStatTypeColor = "Black";

			// ROLE
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];

			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strRSValue[]=new String[1];
			String strURL="www.google.com";

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strOptions = "";

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
		  Action:Preconditions:
			1. Status types 'NST', 'MST', 'TST' and 'SST' are created selecting role 'R1'.
		    2. Resource Type 'RT' is created selecting status types 'NST', 'MST', 'TST' and 'SST'
		    3. User 'U1' is created selecting 'Setup Resource' and 'Configure region Views' right.
		 'RS1'and Setup-Resource Type right.
		  Expected Result:No Expected Result
		*/
		//611565
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Roles
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRole.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRights,
						strViewRightValue, true, updateRightValue, true, true);
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
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			// NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleUpdateValue = { { strRoleValue, "true" } };
				String[][] strRoleViewValue = { { strRoleValue, "true" } };
				strFuncResult = objStatusTypes.slectAndDeselectRoleInSTNew(
						seleniumPrecondition, false, false, strRoleViewValue,
						strRoleUpdateValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(
						seleniumPrecondition, statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
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
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intST = 0; intST < str_roleStatusTypeValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							seleniumPrecondition,
							str_roleStatusTypeValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Users
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, 
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				 strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
		
		/*
		* STEP :
		  Action:Login as user 'U1'
		  Expected Result:'Region Default' screen is displayed.
		*/
		//611566

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigaet to Setup >> Resources. 
		  Expected Result:'Resource List' screen is displayed.
		  'Create New Resource' button is displayed at top left of the screen. 
		*/
		//611567

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("css=input[value='Create New Resource']"));
				log4j.info("'Create New Resource' button is present. ");
			} catch (AssertionError Ae) {
				strFuncResult = "'Create New Resource' button is NOT present. ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Resource' button 		'
		  Expected Result:'Create New Resource' screen is displayed. 
		*/
		//611568
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateResourcePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Create resource 'RS' entering data in mandatory fields, entering appropriate
		   data under 'Website' and click on 'Save' button 
          Expected Result:Resource 'RS' is displayed under 'Resource List' screen 
          with 'Edit' and 'Sub-Resources' links. 
		*/
		//611572

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWithMandFields(selenium,
						strResource, strAbbrv, strResrcTypName, "Hospital",
						"FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.provideLookUpAddressToResource(selenium,
						strState, strCountry);
				selenium.type(propElementDetails
						.getProperty("CreateResource.Website"), strURL);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavToAssignUsr(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifyResource(selenium,
						strResource, "No", "", strAbbrv, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(selenium,
						strResource);
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
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/thead"
								+ "/tr/th[1][text()='Action']"));
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[1][text()='Edit']"));
				log4j.info("Edit link is present under Action coloumn");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult+"Edit link is Not present under Action coloumn";
			}
			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//div[@id='mainContainer']/table[2]/"
								+ "tbody/tr/td[5][text()='"
								+ strResource
								+ "']/parent::tr/td[1]/a[3][text()='Sub-Resources']"));
				log4j.info("SubResources link is present under Action coloumn");

			} catch (AssertionError Ae) {
				gstrReason = "SubResources link is NOT present under Action coloumn";
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(selenium,
						strResource, strRSValue[0], false, false, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to View >> Map 
		  Expected Result:'Regional Map View' screen is displayed. 
		*/
		//611573

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statrNumTypeName,
						statrTextTypeName, statrMultiTypeName,
						statrSaturatTypeName, statrNedocTypeName };
				strFuncResult = objViewMap
						.verifyStatTypesInResourcePopupWaitForPopUp(selenium,
								strResource, strEventStatType, strRoleStatType,
								false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select resource 'RS' from 'Find Resource' dropdown 
		  Expected Result:Resource RS is displayed on popup window on map with status types 'NST',
		   'MST', 'TST', 'SST' and 'View Info', 'Edit Info' links. 
		*/
		//611569
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {

					log4j.info("Resource info pop window is displayed with 'View Info'"
									+ "link. ");
				} else {
					log4j.info("Resource info pop window is NOT displayed with 'View Info'"
									+ "links. ");
					strFuncResult = "Resource info pop window is NOT displayed with 'View Info'"
							+ "links. ";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult + " Resource RS is NOT displayed ";
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.varEditInfolink(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
		/*
		* STEP :
		  Action:Click on View Info link on resource popup 
		  Expected Result:Following details are displayed appropriately as provided while creating resource
		   on 'View Resource Detail' screen:
			i. Type: (Resource type)
			ii. Address:
			iii. County:
			iv. Lat/Longitude:
			v. EMResource/AHA ID:
			vi. Website: (Provided website address hyper linked )
			vii. Contact:
			viii. Contact Title:
			ix. Phone 1:
			x. Phone 2:
			xi. Fax:
			xii. Email:
			xiii. Notes: 
		*/
		//611570
			String strTestData[] = new String[10];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName_1 + "/" + strInitPwd;
				strTestData[3] = statrNumTypeName +"/"+ statrTextTypeName
				+"/"+statrMultiTypeName +"/"+ statrSaturatTypeName+"/"+statrNedocTypeName;
				strTestData[5] = "Verify from 8th step";
				strTestData[6] = strResource;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "112459"; // Test Case Id
			gstrTO = "Provide a valid website address and verify that the website address is displayed " +
					"appropriately on the 'View Resource Detail' screen.";// Test Objective
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
	//end//testFTS112459/
	
//start//testFTS112456//
	/***************************************************************
	'Description		:Verify that user can select look up address when address, city,
	 					 state and zip code are provided
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/25/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date						Modified By
	'Date								Name
	***************************************************************/

	@Test
	public void testFTS112456() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		ViewMap objViewMap = new ViewMap();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "112456"; // Test Case Id
			gstrTO = " Verify that user can select look up address when address, city,"
					+ " state and zip code are provided";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String ambulanceImg = rdExcel.readInfoExcel("ResourceIcon", 4, 2,
					strFILE_PATH);

			String strStatusNumValue = "Number";
			String strStatusMulValue = "Multi";
			String strStatusTextValue = "Text";
			String strStatusSSValue = "Saturation Score";
			String strNESTValue = "NEDOCS Calculation";

			String statTypeName1 = "Nst" + strTimeText;
			String statTypeName2 = "Mst" + strTimeText;
			String statTypeName3 = "Tst" + strTimeText;
			String statTypeName4 = "Sst" + strTimeText;
			String statTypeName5 = "Ned" + strTimeText;

			String strStatTypDefn = "Def";
			String strStatTypeColor = "Black";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String strResrcTypName = "Resty_1" + strTimeText;
			String strContFName = "FirstName";
			String strContLName = "LastName";
			String strResource = "Res" + strTimeText;
			String strRoleName = "Rol_1" + strTimeText;
			String strAbbrv = "Abb";
			String strStandResType = "Ambulance";
			String strState = "Alabama";
			String strCounty = "Barbour County";

			String strUsrFulName = "UserName";
			String strUserName = "User_1" + System.currentTimeMillis();

			String strStreetAddr = "Street Adderss";
			String strCity = "Florida";
			String strZipCode = "020-40-30";
			String strWebSite = "www.qsgsoft.com";
			String strTitle = "Res_Title";
			String strContAddr = "Res_Address";
			String strContPh1 = "900300400";
			String strContPh2 = "900300401";
			String strContFax = "401-301-56";
			String strContEmail = "autoemr@qsgsoft.com";
			String strNotes = "This is Automation notes";
			String strHavBed = "No";

			String strViewName = "View_1" + strTimeText;
			String strVewDescription = "ViewDes" + strTimeText;
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";

			String strSTvalue[] = new String[5];
			String strRoleValue[] = new String[1];
			String strRTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strStatusValue[] = new String[2];
			String strLTValue[] = new String[2];

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Status types 'NST', 'MST', 'TST' and 'SST' are created
			 * selecting role 'R1'.
			 * 
			 * 2. Resource Type 'RT' is created selecting status types 'NST',
			 * 'MST', 'TST' and 'SST'
			 * 
			 * 3. User 'U1' is created selecting 'Setup Resource' and 'Configure
			 * region Views' right. Expected Result:No Expected Result
			 */
			// 625152
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
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
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating NST
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
						seleniumPrecondition, strStatusNumValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusMulValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating STATUSES for MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName2, strStatusName1,
						strStatusMulValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeName2, strStatusName2,
						strStatusMulValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statTypeName2, strStatusName1);
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
						seleniumPrecondition, statTypeName2, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

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
						seleniumPrecondition, strStatusTextValue,
						statTypeName3, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName3);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusSSValue, statTypeName4,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName4);
				if (strSTvalue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNESTValue, statTypeName5,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[4] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName5);
				if (strSTvalue[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] updateRightValue = { strSTvalue[0], strSTvalue[1],
						strSTvalue[2], strSTvalue[3], strSTvalue[4] };
				String[][] strRoleRightss = {};
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRoleName, strRoleRightss,
						updateRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating RT
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
						seleniumPrecondition, strResrcTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Selecting MST,TST,SST in RT

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navToeditResrcTypepage(
						seleniumPrecondition, strResrcTypName);
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
						seleniumPrecondition, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating U1
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
				strFuncResult = objCreateUsers.slectAndDeselectRole(
						seleniumPrecondition, strRoleValue[0], true);
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

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

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

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as user 'U1' Expected Result:'Region Default'
			 * screen is displayed.
			 */
			// 625153
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigaet to Setup >> Resources ,Select 'include
			 * inactive resources' checkbox. Expected Result:'Resource List'
			 * screen is displayed with the following column headers :
			 * 
			 * Action Icon Active HAvBED AHA ID Name Abbreviation Resource Type
			 * 
			 * 'Create New Resource' button is displayed at top left of the
			 * screen.
			 */
			// 625154

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselIncludInactiveResrc(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.verifyResourceHeaderInResrcListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselIncludInactiveResrc(
						selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("css=input[value='Create New Resource']"));
				log4j.info("'Create New Resource' button is present. ");
			} catch (AssertionError Ae) {
				strFuncResult = "'Create New Resource' button is NOT present. ";
				log4j.info(strFuncResult);
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Create New Resource' button Expected
			 * Result:'Create New Resource' screen is displayed.
			 */
			// 625155

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Enter data in mandatory fields and enter
			 * appropriate address (address, city, State and zip) and click on
			 * 'Lookup Address' button Expected Result:Red push pin is displayed
			 * on the map at the street provided.
			 * 
			 * Latitude and longitude values are displayed automatically in the
			 * respective fields
			 */
			// 625156

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFieldsNew(
						selenium, strResource, strAbbrv, strResrcTypName,
						strStandResType, false, false, "", "", false,
						strStreetAddr, strCity, strState, strZipCode,
						strCounty, strWebSite, strContFName, strContLName,
						strTitle, strContAddr, strContPh1, strContPh2,
						strContFax, strContEmail, strNotes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);
				selenium
						.click("css=input[name='SELECT_ALL'][value='viewRight']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Save' Expected Result:Resource is listed
			 * under 'Resource List' screen with appropriate data under
			 * following column headers :
			 * 
			 * i. Action (Edit and Sub-Resources links are displayed) ii. Icon
			 * (Selected resource icon while creating resource) iii. Active
			 * (Active) iv. HAvBED (No) v. AHA ID (provided ID) vi. Name (Name
			 * of the resource) vii. Abbreviation (Abbreviation provided while
			 * creating resource) viii. Resource Type (Selected resource type)
			 */
			// 625157
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(selenium,
						strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strLTValue = objResources
						.fetchLatitudeAndLongitudeValue(selenium);
				if (strLTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Latitude and Longitude  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.verifyLinksUndrActionInRSListPgeAsUserLogin(selenium,
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2]/img[@src='"
						+ ambulanceImg
						+ "']/ancestor::tr/"
						+ "td[text()='"
						+ strResource + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Icon is listed under resource " + strResource
							+ "In resource list page");
				} else {
					strFuncResult = "Icon is NOT listed under resource "
							+ strResource + "In resource list page";
					log4j.info("Icon is NOT listed under resource "
							+ strResource + "In resource list page ");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselIncludInactiveResrc(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@class='displayTable striped border sortable']/tbody/tr/td[6][text()='"
						+ strResource
						+ "']"
						+ "/parent::tr/td[3][text()='Active']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Active is listed under resource " + strResource
							+ "In resource list page");
				} else {
					strFuncResult = "Active is NOT listed under resource "
							+ strResource + "In resource list page";
					log4j.info("Active is NOT listed under resource "
							+ strResource + "In resource list page ");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selAndDeselIncludInactiveResrc(
						selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyResourceInfoInResList(
						selenium, strResource, strHavBed, "", strAbbrv,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views Expected Result:'Region
			 * Views List' screen is displayed.
			 * 
			 * 'Create New View','Re-order Views' and 'Customize resource Detail
			 * View' buttons are available at top left of the screen.
			 */
			// 625158
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkButtonsInRegionViewList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Create New View' button Expected
			 * Result:'Create New View' screen is displayed.
			 */
			// 625159
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToCreateViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Create view 'V1' selecting status types 'NST',
			 * 'MST', 'TST', 'SST', resource RT and click on 'Save' Expected
			 * Result:View V1 is displayed under 'Region Views List' screen.
			 * 
			 * Copy,Edit,Delete and Users links are available next to view.
			 */
			// 625160
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createViewNew(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(selenium,
						strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> 'V1' Expected Result:View V1
			 * screen is displayed with resource RS under RT along with status
			 * types 'NST', 'MST', 'TST' and 'SST'
			 */
			// 625161

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strRS[] = { strResource };
				String strST[] = { statTypeName2, statTypeName5, statTypeName1,
						statTypeName4, statTypeName3 };
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName, strRS, strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:Details of
			 * resource RS is displayed appropriately on 'View Resource Detail'
			 * screen.
			 * 
			 * Following details are displayed appropriately as provided while
			 * creating resource:
			 * 
			 * i. Type: (Resource type) ii. Address: (Provided resource address)
			 * iii. County: (selected county) iv. Lat/Longitude: (populated
			 * lat/lang values) v. EMResource/AHA ID: (Provided ID) vi. Website:
			 * (Provided website) vii. Contact: (Contact first name and last
			 * name) viii. Contact Title: (Contact title provided) ix. Phone 1:
			 * (provided phone no) x. Phone 2: (provided phone no) xi. Fax:
			 * (provided fax no) xii. Email: (Provided mail ID) xiii. Notes:
			 * (provided notes for resource) Resource icon is displayed at the
			 * provided address on the map on Resource Detail screen
			 */
			// 625162

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[][] strResouceData = {
					{ "Address:", strStreetAddr },
					{ "Contact Title:", strTitle },
					{ "Type:", strResrcTypName },
					{ "County:", strCounty },
					{ "Phone 1:", strContPh1 },
					{ "Phone 2:", strContPh2 },
					{ "Email:", strContEmail },
					{ "Notes:", strNotes },
					{ "Contact:", strContFName + " " + strContLName },
					{ "Lat/Longitude:",
							strLTValue[0] + " " + "/" + " " + strLTValue[1] } };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map Expected Result:'Regional
			 * Map View' screen is displayed.
			 */
			// 625163
			/*
			 * STEP : Action:Select resource 'RS' from 'Find Resource' dropdown
			 * Expected Result:Resource RS is displayed on popup window on map
			 * with status types 'NST', 'MST', 'TST', 'SST' and 'View Info',
			 * 'Edit Info' links.
			 */
			// 625164
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2, statTypeName5,
						statTypeName1, statTypeName4, statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@class='smallLink']/a[text()='View Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("View Info link is present");
				} else {
					strFuncResult = "View Info link is NOT present";
					log4j.info(" View Info link is NOT present");
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				String strElementID1 = "//div[@class='smallLink']/a[text()='Edit Info']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID1);
				if (strFuncResult.equals("")) {
					log4j.info("Edit Info link is present");
				} else {
					strFuncResult = "Edit Info link is  NOT present";
					log4j.info("Edit Info link is NOT  present");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on View Info link on resource popup Expected
			 * Result:Following details are displayed appropriately as provided
			 * while creating resource on 'View Resource Detail' screen:
			 * 
			 * i. Type: (Resource type) ii. Address: (Provided resource address)
			 * iii. County: (selected county) iv. Lat/Longitude: (populated
			 * lat/lang values) v. EMResource/AHA ID: (Provided ID) vi. Website:
			 * (Provided website) vii. Contact: (Contact first name and last
			 * name) viii. Contact Title: (Contact title provided) ix. Phone 1:
			 * (provided phone no) x. Phone 2: (provided phone no) xi. Fax:
			 * (provided fax no) xii. Email: (Provided mail ID) xiii. Notes:
			 * (provided notes for resource) Resource icon is displayed at the
			 * provided address on the map on Resource Detail screen
			 */
			// 625165

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap
						.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-112456";
			gstrTO = "Verify that user can select look up address when address, city, state" +
					" and zip code are provided";
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

	// end//testFTS112456//
}
