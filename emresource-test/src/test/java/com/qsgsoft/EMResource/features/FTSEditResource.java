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

/*******************************************************************
' Description :This class includes FTSEditResource requirement 
'				testcases
' Date		  :16-Jan-2013
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSEditResource {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditResource");
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
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	//start//testFTS124521//
	/***************************************************************
	'Description	:Verify that field 'Contact Title' checkbox can
	                 be selected while creating resource type.
	'Arguments		:None
	'Returns		:None
	'Date			:8/7/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				                       Modified By
	'Date					                           Name
	***************************************************************/

	@Test
	public void testFTS124521() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		MobileView objMobileView = new MobileView();

		try {
			gstrTCID = "124521"; // Test Case Id
			gstrTO = "Verify that data can be edited in the 'Title' field.";//TO
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
		  Action:Precondition:1. Resource type is edited and 'Contact Title' check box is selected.
			2. Resource RS is created under RT providing value for 'Contact Title' field.
			3. Edit the 'Contact Title' provided for resource 'RS'
			4. Edited value is displayed on 'View Resource Detail' screen
			5. It is also displayed on Mobile View screen. 
		*/
		//492816
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

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

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-124521"; // Test Case Id
			gstrTO = "Verify that data can be edited in the 'Title' field.";// TO
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

		//end//testFTS124521//
	
// start//testFTS112474//
	/***************************************************************
	 * 'Description 	:Verify that when a resource is opened for editing all the
	 *                   previously saved values are retained.
	 * 'Precondition 	: 
	 * 'Arguments       :None
	 * 'Returns 		:None 	
	 * 'Date 			:8/30/2013 
	 * 'Author 			:QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date                        Modified By 
	 * 'Date                                 Name
	 ***************************************************************/

	@Test
	public void testFTS112474() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		General objGeneral = new General();
		try {
			gstrTCID = "112474"; // Test Case Id
			gstrTO = " Verify that when a resource is opened for editing all the previously saved values are retained.";// Test
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
			String ambulanceImg = rdExcel.readInfoExcel("ResourceIcon", 4, 2,
					strFILE_PATH);

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

			String strSTvalue[] = new String[4];
			String strRoleValue[] = new String[1];
			String strRTvalue[] = new String[1];
			String strRSValue[] = new String[1];
			String strStatusValue[] = new String[2];

			String strStreetAddr = "Street Adderss";
			String strCity = "Florida";
			String strZipCode = "020-40-30";
			String strWebSite = "www.qsgsoft.com";
			String strTitle = "Res_Title";
			String strContAddr = "Res_Adderss";
			String strContPh1 = "900300400";
			String strContPh2 = "900300401";
			String strContFax = "401-301-56";
			String strContEmail = "autoemr@qsgsoft.com";
			String strNotes = "This is Automation notes";
			String strHavBed = "Yes";

			/*
			 * STEP : Action:Preconditions: <br> <br>1. Status types 'NST',
			 * 'MST', 'TST' and 'SST' are created selecting role 'R1'. <br>
			 * <br>2. Resource Type 'RT' is created selecting status types
			 * 'NST', 'MST', 'TST' and 'SST' <br> <br>3. Resource 'RS' is
			 * created under 'RT' providing data in all the fields. Expected
			 * Result:No Expected Result
			 */
			// 625453

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

			// Creating RS
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
				strFuncResult = objResources.createResource_FillAllFields(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strStandResType, true, true, "", "",
						true, strStreetAddr, strCity, strState, strZipCode,
						strCounty, strWebSite, strContFName, strContLName,
						strTitle, strContAddr, strContPh1, strContPh2,
						strContFax, strContEmail, strNotes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Logout

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRECONDITION  - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as 'RegAdmin' Expected Result:'Region
			 * Default' screen is displayed.
			 */
			// 625454

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login
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
			 * STEP : Action:Navigaet to Setup >> Resources Expected
			 * Result:Resource is listed under 'Resource List' screen with
			 * appropriate data under following column headers : i. Action
			 * (Edit,Status Types,Users,Sub-Resources,Demote links are
			 * displayed) ii. Icon (Selected resource icon while creating
			 * resource) iii. Active (Active) iv. HAvBED (yes) v. AHA ID
			 * (provided ID) vi. Name (Name of the resource)vii. Abbreviation
			 * (Abbreviation provided while creating resource) viii. Resource
			 * Type (Selected resource type)
			 */
			// 625456

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
				String strElementID = "//table[@class='displayTable striped border sortable']/tbody/tr/td[6][text()='"
						+ strResource
						+ "']"
						+ "/parent::tr/td[3][text()='Active']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("ACTIVE is listed under resource " + strResource
							+ "In resource list page");
				} else {
					strFuncResult = "ACTIVE is NOT listed under resource "
							+ strResource + "In resource list page";
					log4j.info("ACTIVE is NOT listed under resource "
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
				strFuncResult = objResources.verifyLinksUndrActionInRSListPge(
						selenium, strResource);
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
				strFuncResult = objResources.verifyResourceInfoInResList(
						selenium, strResource, strHavBed, "", strAbbrv,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Edit' link associated with resource 'RS'
			 * Expected Result:'Edit Resource' screen is displayed. All the data
			 * provided while creating resource is displayed appropriately.
			 */
			// 625470

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.verifyResMandValuesWithCityAndStInEditRes(selenium,
								strResource, strAbbrv, strCity,
								strResrcTypName, strStandResType, strState,
								strContFName, strContLName);
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
			gstrTCID = "FTS-112474";
			gstrTO = "Verify that when a resource is opened for editing all the previously saved values are retained.";
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

	// end//testFTS112474//

	//start//testFTS112475//
		/***************************************************************
		'Description		:Verify that all the fields in a resource can be edited
		'Precondition		:
		'Arguments		:None
		'Returns		:None
		'Date			:9/5/2013
		'Author			:QSG
		'---------------------------------------------------------------
		'Modified Date				Modified By
		'Date					Name
		***************************************************************/

	@Test
	public void testFTS112475() throws Exception {
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
		try {
			gstrTCID = "112475"; // Test Case Id
			gstrTO = " Verify that all the fields in a resource can be edited";// Test
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
			String strStreetAddr = "AL";
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

			String strResource1 = "Res_2" + strTimeText;
			String strAbbrv1 = "Ab";
			String strStandResType1 = "Ambulance";
			String strStreetAddr1 = "HI";
			String strCity1 = "Mexico";
			String strState1 = "Hawaii";
			String strZipCode1 = "122-333";
			String strCounty1 = "Hawaii County";
			String strWebSite1 = "www.qsgsoft.com";
			String strContFName1 = "FN";
			String strContLName1 = "LN";
			String strTitle1 = "Title1";
			String strContAddr1 = "Address1";

			String strContPhNew1 = "888-555-1212";
			String strContPhNew2 = "888-555-1212";
			String strContFax1 = "888-555-1212";
			String strContEmail1 = "autoemr@qsgsoft.com";
			String strNotes1 = "Automation new notes";

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
			 * 'MST', 'TST' and 'SST'
			 * 
			 * 3. Resource 'RS' is created under 'RT' providing data in all the
			 * fields.
			 * 
			 * 4. View 'V1' is created selecting status types 'NST', 'MST',
			 * 'TST','SST' and resource type 'RT' Expected Result:No Expected
			 * Result
			 */
			// 625471

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
				strFuncResult = objResources.createResource_FillAllFieldsNew(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strStandResType, true, false, "", "",
						false, strStreetAddr, strCity, strState, strZipCode,
						strCounty, strWebSite, strContFName, strContLName,
						strTitle, strContAddr, strContPh1, strContPh2,
						strContFax, strContEmail, strNotes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
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
				strFuncResult = objResources.navToEditResourcePage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strLTValue = objResources
						.fetchLatitudeAndLongitudeValue(seleniumPrecondition);
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
				strFuncResult = objResources
						.saveAndNavRSList(seleniumPrecondition);
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
				strFuncResult = objViews
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~ORECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			/*
			 * STEP : Action:Login as 'RegAdmin' Expected Result:'Region
			 * Default' screen is displayed.
			 */
			// 625472

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
			 * STEP : Action:Navigaet to Setup >> Resources Expected
			 * Result:Resource is listed under 'Resource List' screen with
			 * appropriate data under following column headers :
			 * 
			 * i. Action (Edit,Status Types,Users,Sub-Resources,Demote links are
			 * displayed) ii. Icon (Selected resource icon while creating
			 * resource) iii. Active (Active) iv. HAvBED (yes) v. AHA ID
			 * (provided ID) vi. Name (Name of the resource) vii. Abbreviation
			 * (Abbreviation provided while creating resource) viii. Resource
			 * Type (Selected resource type)
			 */
			// 625473

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
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
			 * STEP : Action:Click on 'Edit' link associated with resource 'RS'
			 * Expected Result:'Edit Resource' screen is displayed.
			 * 
			 * All the data provided while creating resource is displayed
			 * appropriately.
			 */
			// 625474
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyAllDataRetainedInEditRSPage(
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
				strFuncResult = objResources.chkValueForLatitudeAndLongitude(
						selenium, strLTValue[0], strLTValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit data in all the fields and click on 'Save'
			 * Expected Result:Resource is listed under 'Resource List' screen
			 * with updated data under following column headers :
			 * 
			 * i. Action ii. Icon iii. Active iv. HAvBED v. AHA ID vi. Name vii.
			 * Abbreviation viii. Resource Type
			 */
			// 625475

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFieldsNew(
						selenium, strResource1, strAbbrv1, strResrcTypName,
						strStandResType1, true, false, "", "", false,
						strStreetAddr1, strCity1, strState1, strZipCode1,
						strCounty1, strWebSite1, strContFName1, strContLName1,
						strTitle1, strContAddr1, strContPhNew1, strContPhNew2,
						strContFax1, strContEmail1, strNotes1);
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
						selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//div[@id='mainContainer']/table/tbody/tr/td[2]/img[@src='"+ambulanceImg+"']/ancestor::tr/"
						+ "td[text()='" + strResource1 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Icon is listed under resource " + strResource1
							+ "In resource list page");
				} else {
					strFuncResult = "Icon is NOT listed under resource "
							+ strResource1 + "In resource list page";
					log4j.info("Icon is NOT listed under resource "
							+ strResource1 + "In resource list page ");
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
						+ strResource1
						+ "']"
						+ "/parent::tr/td[3][text()='Active']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("")) {
					log4j.info("Active is listed under resource "
							+ strResource1 + "In resource list page");
				} else {
					strFuncResult = "Active is NOT listed under resource "
							+ strResource1 + "In resource list page";
					log4j.info("Active is NOT listed under resource "
							+ strResource1 + "In resource list page ");
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
						selenium, strResource1, strHavBed, "", strAbbrv1,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource1);
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

			/*
			 * STEP : Action:Navigate to View >> 'V1' Expected Result:View V1
			 * screen is displayed with resource RS under RT along with status
			 * types 'NST', 'MST', 'TST' and 'SST'
			 */
			// 625478

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				String strRS[] = { strResource1 };
				String strST[] = { statTypeName2, statTypeName1, statTypeName4,
						statTypeName3 };
				assertEquals("", strFuncResult);
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrcTypName, strRS, strST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:Updated
			 * details of resource 'RS' is displayed on 'Resource Detail View':
			 * 
			 * i. Type: ii. Address: iii. County: iv. Lat/Longitude: v.
			 * EMResource/AHA ID: vi. Website: vii. Contact: viii. Contact
			 * Title: ix. Phone 1: x. Phone 2: xi. Fax: xii. Email: xiii. Notes:
			 */
			// 625479

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String[][] strResouceData = {
					{ "Type:", strResrcTypName },
					{ "Address:", strStreetAddr1 },
					{ "County:", strCounty1 },
					{ "Lat/Longitude:",
							strLTValue[0] + " " + "/" + " " + strLTValue[1] },
					{ "Contact:", strContFName1 + " " + strContLName1 },
					{ "Contact Title:", strTitle1 },
					{ "Phone 1:", strContPhNew1 },
					{ "Phone 2:", strContPhNew2 }, { "Email:", strContEmail1 },
					{ "Notes:", strNotes1 }

			};
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource1, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map ,Select resource 'RS' from
			 * 'Find Resource' dropdown Expected Result:Resource RS is displayed
			 * on popup window on map with status types 'NST', 'MST', 'TST',
			 * 'SST' and 'View Info', 'Edit Info' links.
			 */
			// 625480

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2, statTypeName1,
						statTypeName4, statTypeName3 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on View Info link on resource popup Expected
			 * Result:Updated values of resource 'RS' are displayed on 'View
			 * Resource Detail' screen.
			 */
			// 625481

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
						selenium, strResource1, strResouceData);
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
			gstrTCID = "FTS-112475";
			gstrTO = "Verify that all the fields in a resource can be edited";
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

	// end//testFTS112475//
	


	//start//testFTS112477//
	/***************************************************************
	'Description		:Edit data in all the fields and cancel the process of editing the resource and verify that the data is not updated.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:9/6/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				           Modified By
	'Date					               Name
	***************************************************************/

	@Test
	public void testFTS112477() throws Exception {
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
		try {
			gstrTCID = "112477"; // Test Case Id
			gstrTO = " Edit data in all the fields and cancel the process of editing the resource and verify that the data is not updated.";// Test
																																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String ambulanceImg = rdExcel.readInfoExcel("ResourceIcon", 4,
					2, strFILE_PATH);
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

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
			String strStreetAddr = "AL";
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

			String strResource1 = "Res_2" + strTimeText;
			String strAbbrv1 = "Ab";
			String strStandResType1 = "Aeromedical";
			String strStreetAddr1 = "HI";
			String strCity1 = "Mexico";
			String strState1 = "Hawaii";
			String strZipCode1 = "122-333";
			String strCounty1 = "Hawaii County";
			String strWebSite1 = "www.qsgsoft.com";
			String strContFName1 = "FN";
			String strContLName1 = "LN";
			String strTitle1 = "Title1";
			String strContAddr1 = "Address1";

			String strContPhNew1 = "888-555-1212";
			String strContPhNew2 = "888-555-1212";
			String strContFax1 = "888-555-1212";
			String strContEmail1 = "autoemr@qsgsoft.com";
			String strNotes1 = "Automation new notes";

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
			 * 'MST', 'TST' and 'SST'
			 * 
			 * 3. Resource 'RS' is created under 'RT' providing data in all the
			 * fields.
			 * 
			 * 4. View 'V1' is created selecting status types 'NST', 'MST',
			 * 'TST','SST' and resource type 'RT' Expected Result:No Expected
			 * Result
			 */
			// 625483
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

			//Creating RS
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
				strFuncResult = objResources.createResource_FillAllFieldsNew(
						seleniumPrecondition, strResource, strAbbrv,
						strResrcTypName, strStandResType, true, false, "", "",
						false, strStreetAddr, strCity, strState, strZipCode,
						strCounty, strWebSite, strContFName, strContLName,
						strTitle, strContAddr, strContPh1, strContPh2,
						strContFax, strContEmail, strNotes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);
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
				strFuncResult = objResources.navToEditResourcePage(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strLTValue = objResources
						.fetchLatitudeAndLongitudeValue(seleniumPrecondition);
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
				strFuncResult = objResources
						.saveAndNavRSList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Creating V1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToCreateViewPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, true);
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

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as 'RegAdmin' Expected Result:'Region
			 * Default' screen is displayed.
			 */
			// 625484

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
			 * STEP : Action:Navigaet to Setup >> Resources Expected
			 * Result:Resource is listed under 'Resource List' screen with
			 * appropriate data under following column headers :
			 * 
			 * i. Action (Edit,Status Types,Users,Sub-Resources,Demote links are
			 * displayed) ii. Icon (Selected resource icon while creating
			 * resource) iii. Active (Active) iv. HAvBED (yes) v. AHA ID
			 * (provided ID) vi. Name (Name of the resource) vii. Abbreviation
			 * (Abbreviation provided while creating resource) viii. Resource
			 * Type (Selected resource type)
			 */
			// 625485

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
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
			 * STEP : Action:Click on 'Edit' link associated with resource 'RS'
			 * Expected Result:'Edit Resource' screen is displayed.
			 * 
			 * All the data provided while creating resource is displayed
			 * appropriately.
			 */
			// 625486

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToEditResourcePage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.verifyAllDataRetainedInEditRSPage(
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
				strFuncResult = objResources.chkValueForLatitudeAndLongitude(
						selenium, strLTValue[0], strLTValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit data in all the fields and click on 'Cancel'
			 * Expected Result:Resource 'RS' is listed under 'Resource List'
			 * screen with the data provided while creating resource.
			 */
			// 625487

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFieldsNew(
						selenium, strResource1, strAbbrv1, strResrcTypName,
						strStandResType1, true, false, "", "", false,
						strStreetAddr1, strCity1, strState1, strZipCode1,
						strCounty1, strWebSite1, strContFName1, strContLName1,
						strTitle1, strContAddr1, strContPhNew1, strContPhNew2,
						strContFax1, strContEmail1, strNotes1);
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
			 * STEP : Action:Navigate to View >> 'V1' Expected Result:View V1
			 * screen is displayed with resource RS under RT along with status
			 * types 'NST', 'MST', 'TST' and 'SST'
			 */
			// 625488

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToViewResourceDetailPageWitoutWaitForPgeLoad(
								selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:Data
			 * provided while creating resource 'RS' is displayed on 'Resource
			 * Detail View':
			 * 
			 * i. Type: ii. Address: iii. County: iv. Lat/Longitude: v.
			 * EMResource/AHA ID: vi. Website: vii. Contact: viii. Contact
			 * Title: ix. Phone 1: x. Phone 2: xi. Fax: xii. Email: xiii. Notes:
			 */
			// 625489

			String[][] strResouceData = {
					{ "Type:", strResrcTypName },
					{ "Address:", strStreetAddr },
					{ "County:", strCounty },
					{ "Lat/Longitude:",
							strLTValue[0] + " " + "/" + " " + strLTValue[1] },
					{ "Contact:", strContFName + " " + strContLName },
					{ "Contact Title:", strTitle }, { "Phone 1:", strContPh1 },
					{ "Phone 2:", strContPh2 }, { "Email:", strContEmail },
					{ "Notes:", strNotes }

			};

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> Map ,Select resource 'RS' from
			 * 'Find Resource' dropdown Expected Result:Resource RS is displayed
			 * on popup window on map with status types 'NST', 'MST', 'TST',
			 * 'SST' and 'View Info', 'Edit Info' links.
			 */
			// 625490

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
			 * STEP : Action:Click on 'View Info' link on resource popup
			 * Expected Result:Data provided while creating resource 'RS' are
			 * displayed on 'View Resource Detail' screen.
			 */
			// 625491
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
			gstrTCID = "FTS-112477";
			gstrTO = "Edit data in all the fields and cancel the process of editing the resource and verify that the data is not updated.";
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

	// end//testFTS112477//

}