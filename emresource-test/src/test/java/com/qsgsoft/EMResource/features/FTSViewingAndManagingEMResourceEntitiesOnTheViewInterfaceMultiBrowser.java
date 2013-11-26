package com.qsgsoft.EMResource.features;
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
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.ViewMap;
import com.qsgsoft.EMResource.shared.Views;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import static org.junit.Assert.*;

/***********************************************************************************
' Description       :This class includes requirement testcases
' Requirement Group :Resource Hierarchies
' Requirement       :Viewing & managing EMResource entities on the 'View' interface
' Date		        :16-October-2013
' Author	        :QSG
'-----------------------------------------------------------------------------------
' Modified Date                                                    Modified By
' <Date>                           	                               <Name>
'***********************************************************************************/

public class FTSViewingAndManagingEMResourceEntitiesOnTheViewInterfaceMultiBrowser {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features."
			+ "ViewingAndManagingEMResourceEntitiesOnTheViewInterface");
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
	Selenium selenium,seleniumPrecondition,selenium1;

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
		
		selenium1 = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("Browser"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();
		
				

		selenium1.start();
		selenium1.windowMaximize();

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
		
		try {
			selenium1.close();
		} catch (Exception e) {

		}
		selenium1.stop();
		

		

		
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


	//start//testFTS107919//
	/***************************************************************
	'Description		:Edit the name of the sub-resource and verify that sub-resource name is updated
	 					 on view resource detail screen on refresh.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/16/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date				              Modified By
	'Date					                  Name
	***************************************************************/

	@Test
	public void testFTS107919() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "107919"; // Test Case Id
			gstrTO = " Edit the name of the sub-resource and verify that sub-resource name is"
					+ " updated on view resource detail screen on refresh.";// Test//
																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

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
			String statrStatypeName1 = "AutoST1_" + strTimeText;
			String statrStatypeName2 = "AutoST2_" + strTimeText;
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[7];
			// Status of MST
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strEditSubResName = "AutoSRs_Edit" + strTimeText;
			
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// User
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// Section data
			String strSection1 = "Sec_" + strTimeText;
			String strSectionValue = "";

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			 * 
			 * 2. NST , MST, SST, TST ,ST1,ST2 status types are under status
			 * type section SEC1.
			 * 
			 * 3. Resource type RT is created selecting NST,MST,TST,SST.
			 * 
			 * 4. Sub Resource Type 'RT1' is created selecting ST1,ST2.
			 * 
			 * 5. Resource 'RS' is created under 'RT'.
			 * 
			 * 6. Sub-Resource 'RS1' is created under parent resource 'RS'
			 * selecting sub-resource type 'RT1'
			 * 
			 * 7. User U1 is created selecting the following rights: a. 'View
			 * Resource' and 'Update resource' right on RS. b. Role R1 to view
			 * and update NST , MST, SST, TST,ST1,ST2 status types. c.
			 * 'Configure Region View' right.
			 * 
			 * 8. View 'V1' is created selecting status types NST,MST,TST,SST
			 * and resource type RT Expected Result:No Expected Result
			 */
			// 610454
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName2, strMSTValue, strStatTypeColor,
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

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
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
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName1);
				if (str_roleStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName2);
				if (str_roleStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[4], true);
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
				strRTvalue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/* Create sub resource type RT1 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[5] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			/*
			 * 5. Sub-resource 'SR1' is created under parent resource 'RS'
			 * selecting 'RT1' sub-resource type.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
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
				strRSValue[1] = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" },
						{ str_roleStatusTypeValues[5], "true" },
						{ str_roleStatusTypeValues[6], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
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
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

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
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				String[] strRSValues = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, str_roleStatusTypeValues, false, strRSValues,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.SaveAndVerifyView(
						seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrStatypeName1,
						statrStatypeName2, statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statrNedocTypeName };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1',Navigate to View >> V1 Expected
			 * Result:RS is displayed under RT along with status types
			 * NST,MST,SST,TST.
			 */
			// 610455
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			//Login as User in browser B1
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				String strResources[] = { strResource1 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResources, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Details provided while creating resource 'RS' are displayed
			 * appropriately.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is not displayed.
			 */
			// 610456

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
				String[][] strResouceData = { { "Type:", strResrctTypName },
						{ "Contact:", strContFName + " " + strContLName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource1, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section is displayed.";
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views,Click on 'Customize
			 * Resource Detail View' button Expected Result:'Edit Resource
			 * Detail View Sections' screen is displayed.
			 * 
			 * 'Save','Cancel' and 'Sub-resource' buttons are displayed on
			 * bottom left of the screen.
			 * 
			 * 'Sort all' button at bottom right of the screen.
			 */
			// 610457
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Save']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on 'Sub-resources' button Expected
			 * Result:'Edit Sub Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * Sub-Resoure Type 'RT1' is listed under it.
			 */
			// 610458

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select Check box associated with 'RT1' and select
			 * status types ST1,ST2 and click on 'Save' Expected Result:'Region
			 * Views List' screen is displayed.
			 */
			// 610459
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { str_roleStatusTypeValues[5],
						str_roleStatusTypeValues[6] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> V1, click on resource 'RS'
			 * Expected Result:'View Resource Detail' screen for resource 'RS'
			 * is displayed.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Sub-resource 'RS1' is displayed under 'RT1' along with status
			 * types ST1,ST2
			 */
			// 610460

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
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as 'RegAdmin' on browser B2 Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 610461
			
			//Login as RegAdmin in browser B2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium1,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium1,
						strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resources,Click on
			 * 'Sub-resource' link associated with resource 'RS1' Expected
			 * Result:'Sub-Resource List for < resource name >' screen is
			 * displayed.
			 */
			// 610462

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(selenium1,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePageWithEditLink(
						selenium1, strResource1, strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit name of sub-resource 'RS1' to 'RS1_edit' and
			 * click on 'Save' Expected Result:'Sub-Resource List for < resource
			 * name >' screen is displayed.
			 * 
			 * Sub-resource 'RS1_edit' is listed under it.
			 */
			// 610463

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.editSubResourceWitLookUPadres(
						selenium1, strEditSubResName, strAbbrv,
						strStandResType, false, strContFName, strContLName, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						selenium1, strEditSubResName, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			

			/*
			 * STEP : Action:On browser 'B1' click on refresh link at the top
			 * left corner of the screen. Expected Result:'View Resource Detail'
			 * screen for resource 'RS' is displayed.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Sub-resource 'RS1_edit' is displayed under 'RT1' along with
			 * status types ST1,ST2
			 */
			// 610464

			
			try {
				assertEquals("", strFuncResult);				
				strFuncResult = objGeneral.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strEditSubResName);
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-107919";
			gstrTO = "Edit the name of the sub-resource and verify that sub-resource name is updated on" +
					 " view resource detail screen on refresh.";
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

	// end//testFTS107919//
	

	//start//testFTS107920//
	/***************************************************************
	'Description		:Verify that deactivated status types associated with sub-resources are not
	 					 displayed on view resource detail screen on refresh.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/17/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS107920() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
	try{	
		gstrTCID = "107920";	//Test Case Id	
		gstrTO = " Verify that deactivated status types associated with sub-resources are not displayed on " +
				"view resource detail screen on refresh.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		Date_Time_settings dts = new Date_Time_settings();
		String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
		String strTimeTxt = dts.getCurrentDate("MMddyyyy");
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		gstrTimetake = dts.timeNow("HH:mm:ss");

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
		String statrStatypeName1 = "AutoST1_" + strTimeText;
		String statrStatypeName2 = "AutoST2_" + strTimeText;
		String statrNumTypeName = "AutoNST" + strTimeText;
		String statrTextTypeName = "AutoTST" + strTimeText;
		String statrMultiTypeName = "AutoMST" + strTimeText;
		String statrSaturatTypeName = "AutoSST" + strTimeText;
		String statrNedocTypeName = "AutoNEDOC" + strTimeText;
		String str_roleStatusTypeValues[] = new String[7];
		// Status of MST
		String strStatTypeColor = "Black";
		String str_roleStatusName1 = "rSa" + strTimeTxt;
		String str_roleStatusName2 = "rSb" + strTimeTxt;
		String str_roleStatusValue[] = new String[2];
		// RT
		String strResrctTypName = "AutoRt_1" + strTimeText;
		String strSubResrctTypName = "AutoSRt_" + strTimeText;
		String strRTvalue[] = new String[2];
		// RS
		String strResource1 = "AutoRs_1" + strTimeText;
		String strSubResource = "AutoSRs_" + strTimeText;		
		String strAbbrv = "Rs";
		String strStandResType = "Aeromedical";
		String strContFName = "auto";
		String strContLName = "qsg";
		String strState = "Alabama";
		String strCountry = "Autauga County";
		String strRSValue[] = new String[2];
		// Role
		String strRolesName_1 = "Role_1" + strTimeText;
		String strRoleValue = "";
		// User
		String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strConfirmPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = strUserName1;
		// Search user criteria
		String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		String strByResourceType = rdExcel.readInfoExcel("User_Template",
				7, 12, strFILE_PATH);
		String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
				13, strFILE_PATH);
		String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
				14, strFILE_PATH);
		// View Details
		String strViewName = "AutoV_" + strTimeText;
		String strVewDescription = "";
		String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
		// Section data
		String strSection1 = "Sec_" + strTimeText;
		String strSectionValue = "";		

		


		/*
		* STEP :
		  Action:Preconditions:
	
	1. Status types NST, MST, SST, TST,ST1,ST2 are created.
	
	2. NST , MST, SST, TST ,ST1,ST2 status types are under status type section SEC1.
	
	3. Resource type RT is created selecting NST,MST,TST,SST.
	
	4. Sub Resource Type 'RT1' is created selecting ST1,ST2.
	
	5. Resource 'RS' is created under 'RT'.
	
	6. Sub-Resource 'RS1' is created under parent resource 'RS' selecting sub-resource type 'RT1'
	
	7. User U1 is created selecting the following rights:
	a. 'View Resource' and 'Update resource' right on RS.
	b. Role R1 to view and update NST , MST, SST, TST,ST1,ST2 status types.
	c. 'Configure Region View' right.
	
	8. View 'V1' is created selecting status types NST,MST,TST,SST and resource type RT
		  Expected Result:No Expected Result
		*/
		//610494
		log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

		// Status Types
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes
					.navStatusTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		// Role based status type
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
			strFuncResult = objStatusTypes.savAndVerifySTNew(
					seleniumPrecondition, statrNumTypeName);
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
			strFuncResult = objStatusTypes.savAndVerifySTNew(
					seleniumPrecondition, statrTextTypeName);
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
			strFuncResult = objStatusTypes.savAndVerifySTNew(
					seleniumPrecondition, statrSaturatTypeName);
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
			strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
					seleniumPrecondition, statrMultiTypeName,
					str_roleStatusName2, strMSTValue, strStatTypeColor,
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

		try {
			assertEquals("", strFuncResult);
			str_roleStatusValue[1] = objStatusTypes
					.fetchStatValInStatusList(seleniumPrecondition,
							statrMultiTypeName, str_roleStatusName2);
			if (str_roleStatusValue[1].compareTo("") != 0) {
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
					seleniumPrecondition, strNDSTValue, statrNedocTypeName,
					strStatTypDefn, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.savAndVerifySTNew(
					seleniumPrecondition, statrNedocTypeName);
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
		// ST1
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strNSTValue, statrStatypeName1,
					strStatTypDefn, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.savAndVerifySTNew(
					seleniumPrecondition, statrStatypeName1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			str_roleStatusTypeValues[5] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statrStatypeName1);
			if (str_roleStatusTypeValues[5].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch status type  value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		// ST2
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strNSTValue, statrStatypeName2,
					strStatTypDefn, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.savAndVerifySTNew(
					seleniumPrecondition, statrStatypeName2);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			str_roleStatusTypeValues[6] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition,
							statrStatypeName2);
			if (str_roleStatusTypeValues[6].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch status type  value";
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
					seleniumPrecondition, strResrctTypName,
					"css=input[name='statusTypeID'][value='"
							+ str_roleStatusTypeValues[0] + "']");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.selAndDeselSTInEditRT(seleniumPrecondition,
							str_roleStatusTypeValues[1], true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.selAndDeselSTInEditRT(seleniumPrecondition,
							str_roleStatusTypeValues[2], true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.selAndDeselSTInEditRT(seleniumPrecondition,
							str_roleStatusTypeValues[3], true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.selAndDeselSTInEditRT(seleniumPrecondition,
							str_roleStatusTypeValues[4], true);
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
			strRTvalue[0] = objRT.fetchRTValueInRTList(
					seleniumPrecondition, strResrctTypName);
			if (strRTvalue[0].compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch Role value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		/* Create sub resource type RT1 */
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.resrcTypeMandatoryFlds(
					seleniumPrecondition, strSubResrctTypName,
					"css=input[name='statusTypeID'][value='"
							+ str_roleStatusTypeValues[5] + "']");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.selAndDeselSTInEditRT(seleniumPrecondition,
							str_roleStatusTypeValues[6], true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.selAndDeselSubResourceType(
					seleniumPrecondition, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT
					.saveAndNavToResTypeList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
					seleniumPrecondition, strSubResrctTypName);
			if (strRTvalue[1].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch RT value";
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
			strFuncResult = objRs.createResourceWitLookUPadres(
					seleniumPrecondition, strResource1, strAbbrv,
					strResrctTypName, strContFName, strContLName, strState,
					strCountry, strStandResType);
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

		/*
		 * 5. Sub-resource 'SR1' is created under parent resource 'RS'
		 * selecting 'RT1' sub-resource type.
		 */

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navToEditSubResourcePage(
					seleniumPrecondition, strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navToCreateSubResourcePage(
					seleniumPrecondition, strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.createSubResourceWitLookUPadres(
					seleniumPrecondition, strSubResource, strAbbrv,
					strSubResrctTypName, strStandResType, false,
					strContFName, strContLName, "", "");
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
			strRSValue[1] = objRs.fetchSubResValueInResList(
					seleniumPrecondition, strSubResource);
			if (strRSValue[1].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch Resource value";
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		// Role
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.rolesMandatoryFlds(
					seleniumPrecondition, strRolesName_1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strSTvalues[][] = {
					{ str_roleStatusTypeValues[0], "true" },
					{ str_roleStatusTypeValues[1], "true" },
					{ str_roleStatusTypeValues[2], "true" },
					{ str_roleStatusTypeValues[3], "true" },
					{ str_roleStatusTypeValues[4], "true" },
					{ str_roleStatusTypeValues[5], "true" },
					{ str_roleStatusTypeValues[6], "true" } };
			strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
					seleniumPrecondition, false, false, strSTvalues,
					strSTvalues, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.savAndVerifyRoles(
					seleniumPrecondition, strRolesName_1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strRoleValue = objRoles.fetchRoleValueInRoleList(
					seleniumPrecondition, strRolesName_1);
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
			strFuncResult = objCreateUsers
					.navUserListPge(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
					seleniumPrecondition, strUserName1, strInitPwd,
					strConfirmPwd, strUsrFulName);
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
			String strOptions = propElementDetails
					.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
			strFuncResult = objCreateUsers.advancedOptns(
					seleniumPrecondition, strOptions, true);

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
					seleniumPrecondition, strUserName1, strByRole,
					strByResourceType, strByUserInfo, strNameFormat);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		// View
		try {
			assertEquals("", strFuncResult);
			blnLogin = true;
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
			String[] strRSValues = {};
			strFuncResult = objViews.createViewNew(seleniumPrecondition,
					strViewName, strVewDescription, strViewType, true,
					false, str_roleStatusTypeValues, false, strRSValues,
					false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.selAndDeselRTInEditView(
					seleniumPrecondition, strRTvalue[0], true, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.SaveAndVerifyView(
					seleniumPrecondition, strViewName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		// Section
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews
					.navToEditResDetailViewSections(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			String[] strStatTypeArr = { statrStatypeName1,
					statrStatypeName2, statrNumTypeName, statrTextTypeName,
					statrMultiTypeName, statrSaturatTypeName,
					statrNedocTypeName };
			strFuncResult = objViews
					.dragAndDropSTtoSection(seleniumPrecondition,
							strStatTypeArr, strSection1, true);
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
			strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
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
			strFuncResult = objLogin.logout(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		log4j.info("~~~~~PRECONDITION - " + gstrTCID
				+ " EXECUTION ENDS~~~~~");



		/*
		* STEP :
		  Action:Login as User 'U1',Navigate to View >> V1
		  Expected Result:RS is displayed under RT along with status types NST,MST,SST,TST.
		*/
		//610495

		log4j.info("~~~~~TEST CASE - " + gstrTCID
				+ " EXECUTION STARTS~~~~~");
		//Login as User in browser B1
		try {
			assertEquals("", strFuncResult);
			blnLogin = false;
			strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
					strInitPwd);
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
			String[] strStatTypeArr = { statrMultiTypeName,
					statrNedocTypeName, statrNumTypeName,
					statrSaturatTypeName, statrTextTypeName };
			String strResources[] = { strResource1 };
			strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
					strResrctTypName, strResources, strStatTypeArr);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		* STEP :
		  Action:Click on resource 'RS'
		  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.

	Details provided while creating resource 'RS' are displayed appropriately.
	
	Status types NST,MST,SST,TST are displayed under section 'SEC1'
	
	'Sub-resources' section is not displayed.
		*/
		//610496

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
			String[][] strResouceData = { { "Type:", strResrctTypName },
					{ "Contact:", strContFName + " " + strContLName } };
			strFuncResult = objViews.verifyResDetailsInViewResDetail(
					selenium, strResource1, strResouceData);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			String[] strStatTypeArr = { statrMultiTypeName,
					statrNedocTypeName, statrSaturatTypeName,
					statrNumTypeName, statrTextTypeName };
			strFuncResult = objViews.verifySTInViewResDetailUnderSection(
					selenium, strSection1, strSectionValue, strStatTypeArr);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			assertFalse(selenium.isElementPresent("//table[@summary"
					+ "='Status for " + strSubResrctTypName + "']/"
					+ "preceding-sibling::div[1]/h1"));
			log4j.info("'Sub-resources' section is not displayed.");
		} catch (AssertionError Ae) {
			strFuncResult = "'Sub-resources' section is displayed.";
			gstrReason = strFuncResult;
		}



		/*
		* STEP :
		  Action:Navigate to Setup >> Views,Click on 'Customize Resource Detail View' button
		  Expected Result:'Edit Resource Detail View Sections' screen is displayed.
	
	'Save','Cancel' and 'Sub-resource' buttons are displayed on bottom left of the screen.
	
	'Sort all' button at bottom right of the screen.
		*/
		//610497

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews.navRegionViewsList(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews
					.navToEditResDetailViewSections(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strElementID = "css=input[value='Save']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);

			strElementID = "css=input[value='Cancel']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);

			strElementID = "css=input[value='Sub-resources']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);

			strElementID = "css=input[value='Sort All']";
			strFuncResult = objGeneral.checkForAnElements(selenium,
					strElementID);

			if (strFuncResult.equals("")) {
				log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
						+ " displayed on bottom left of the screen. ");
				log4j.info("'Sort all' button at bottom right of the screen. ");
			} else {
				strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
						+ "NOT displayed on bottom left of the screen. ";
				log4j.info("'Save','Cancel' and 'Sub-resource' buttons are "
						+ "NOT displayed on bottom left of the screen. ");
				log4j.info("'Sort all' button at bottom right of the screen NOT displayed ");
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		* STEP :
		  Action:Click on 'Sub-resources' button
		  Expected Result:'Edit Sub Resource Detail View Sections' screen is displayed.
	
	Sub-Resoure Type 'RT1' is listed under it.
		*/
		//610498

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objViews
					.navToEditSubResDetailViewSections(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}


		/*
		* STEP :
		  Action:Select Check box associated with 'RT1' and select status types ST1,ST2 and click on 'Save'
		  Expected Result:'Region Views List' screen is displayed.
		*/
		//610499

		try {
			assertEquals("", strFuncResult);
			String strstValues[] = { str_roleStatusTypeValues[5],
					str_roleStatusTypeValues[6] };
			strFuncResult = objViews
					.selectStatusTypesInEditSubResourceSectionsPge(
							selenium, strSubResrctTypName, strRTvalue[1],
							strstValues, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}


		/*
		* STEP :
		  Action:Navigate to View >> V1, click on resource 'RS'
		  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.
	
	Status types NST,MST,SST,TST are displayed under section 'SEC1'
	
	'Sub-resources' section is displayed.
	
	Sub-resource 'RS1' is displayed under 'RT1' along with status types ST1,ST2
		*/
		//610500

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
							selenium, strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String[] strStatTypeArr = { statrMultiTypeName,
					statrNedocTypeName, statrSaturatTypeName,
					statrNumTypeName, statrTextTypeName };
			strFuncResult = objViews.verifySTInViewResDetailUnderSection(
					selenium, strSection1, strSectionValue, strStatTypeArr);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
			strFuncResult = objViews
					.verifySTInViewResDetailUnderSubResource(selenium,
							strSubResrctTypName, strStatTypep,
							strSubResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		


		/*
		* STEP :
		  Action:Login as 'RegAdmin' on browser B2
		  Expected Result:'Region Default' screen is displayed.
		*/
		//610501


		//Login as RegAdmin in browser B2
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(selenium1,
					strLoginUserName, strLoginPassword);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(selenium1,
					strRegn);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		* STEP :
		  Action:Navigate to Setup >> Status types , Click on 'Edit' link associated with status type 'ST1'
		  Expected Result:'Edit Resource Detail View Sections' screen is displayed.
	
	Edit < Status type Type > screen is displayed.
	
	'Active' check box is selected.
		*/
		//610502

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes
					.navStatusTypList(selenium1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.editStatusTypePage(selenium1, statrStatypeName1, strNSTValue);
					
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}


		/*
		* STEP :
		  Action:Deselect active check box and click on 'Save'
		  Expected Result:'Status Type List' screen is displayed.
	Status type 'ST1' is not displayed.
		*/
		//610503

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.activateAndDeactivateST(selenium1, statrStatypeName1, false, false);
					
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.VerifySTInStatList(selenium1, statrStatypeName1, true, false);
					
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}		
		
		
		/*
		* STEP :
		  Action:On browser 'B1' click on refresh link at the top left corner of the screen.
		  Expected Result:'View Resource Detail' screen for resource 'RS' is displayed.

	Status Type 'ST1' is not displayed under sub-resource section.
	
	Resource 'RS1' is displayed under 'RT1' associated with status type 'ST2'.
		*/
		//610505
		
		try {
			assertEquals("", strFuncResult);				
			strFuncResult = objGeneral.refreshPageNew(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
				
		
		try {
			assertEquals("", strFuncResult);
			String[] strStatTypep = {statrStatypeName1, statrStatypeName2 };
			strFuncResult = objViews
					.verifySTInViewResDetailUnderSubResource(selenium,
							strSubResrctTypName, strStatTypep,
							strSubResource);
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
							selenium, strResource1);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			String[] strStatTypep = { statrStatypeName1 };
			strFuncResult = objViews
					.verifySTInViewResDetailUnderSubResourceFalse(selenium,
							strSubResrctTypName, strStatTypep, strSubResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			gstrResult = "PASS";
		}
		catch (AssertionError Ae)
		{
			gstrResult = "FAIL";
			gstrReason = gstrReason+" "+strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "FTS-107920";
		gstrTO = "Verify that deactivated status types associated with sub-resources are not displayed on " +
				 "view resource detail screen on refresh.";
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

	//end//testFTS107920//

	//start//testFTS107921//
	/***************************************************************
	'Description		:Verify that deactivated sub-resources are not displayed on view resource detail 
						 screen on refresh.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/18/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS107921() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "107921"; // Test Case Id
			gstrTO = " Verify that deactivated sub-resources are not displayed on view resource detail screen on refresh.";// Test
																															// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

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
			String statrStatypeName1 = "AutoST1_" + strTimeText;
			String statrStatypeName2 = "AutoST2_" + strTimeText;
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[7];
			// Status of MST
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strSubResrctTypName1 = "AutoSRt_" + strTimeText;
			String strSubResrctTypName2 = "AutoSRt_2" + strTimeText;
			String strRTvalue[] = new String[3];
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strSubResource1 = "AutoSRs_" + strTimeText;
			String strSubResource2 = "AutoSRs_2" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[3];
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// User
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// Section data
			String strSection1 = "Sec_" + strTimeText;
			String strSectionValue = "";

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Status types NST, MST, SST, TST,ST1,ST2 are created.
			 * 
			 * 2. NST , MST, SST, TST ,ST1,ST2 status types are under status
			 * type section SEC1.
			 * 
			 * 3. Resource type RT is created selecting NST,MST,TST,SST.
			 * 
			 * 4. Sub Resource Type 'SRT1' and 'SRT2' are created selecting
			 * ST1,ST2.
			 * 
			 * 5. Resource 'RS' is created under 'RT'.
			 * 
			 * 6. Sub-Resource 'SRS1' is created under parent resource 'RS'
			 * selecting sub-resource type 'SRT1'
			 * 
			 * 7. Sub-Resource 'SRS2' is created under parent resource 'RS'
			 * selecting sub-resource type 'SRT2'
			 * 
			 * 7. User U1 is created selecting the following rights: a. 'View
			 * Resource' and 'Update resource' right on RS. b. Role R1 to view
			 * and update NST , MST, SST, TST,ST1,ST2 status types. c.
			 * 'Configure Region View' right.
			 * 
			 * 8. View 'V1' is created selecting status types NST,MST,TST,SST
			 * and resource type RT Expected Result:No Expected Result
			 */
			// 610508

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName2, strMSTValue, strStatTypeColor,
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

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
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
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName1);
				if (str_roleStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName2);
				if (str_roleStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[4], true);
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
				strRTvalue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* Create sub resource type SRT1 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[5] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName1);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/* Create sub resource type SRT2 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[5] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[2] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName2);
				if (strRTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			/*
			 * 5. Sub-resource 'SR1' is created under parent resource 'RS'
			 * selecting 'SRT1' sub-resource type.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource1, strAbbrv,
						strSubResrctTypName1, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource1, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource1);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 5. Sub-resource 'SR2' is created under parent resource 'RS'
			 * selecting 'SRT2' sub-resource type.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource2, strAbbrv,
						strSubResrctTypName2, strStandResType, false,
						strContFName, strContLName, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndVerifySubResourceInRSList(
						seleniumPrecondition, strSubResource2, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[2] = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource2);
				if (strRSValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" },
						{ str_roleStatusTypeValues[5], "true" },
						{ str_roleStatusTypeValues[6], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Creating user
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
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

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
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				String[] strRSValues = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, str_roleStatusTypeValues, false, strRSValues,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.SaveAndVerifyView(
						seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrStatypeName1,
						statrStatypeName2, statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statrNedocTypeName };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1',Navigate to View >> V1 Expected
			 * Result:RS is displayed under RT along with status types
			 * NST,MST,SST,TST.
			 */
			// 610509

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login as User in browser B1
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				String strResources[] = { strResource1 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResources, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Details provided while creating resource 'RS' are displayed
			 * appropriately.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is not displayed.
			 */
			// 610510

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
				String[][] strResouceData = { { "Type:", strResrctTypName },
						{ "Contact:", strContFName + " " + strContLName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource1, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName1 + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName1 + " is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName1 + " is displayed.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName2 + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName2 + " is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName2 + " is displayed.";
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views,Click on 'Customize
			 * Resource Detail View' button Expected Result:'Edit Resource
			 * Detail View Sections' screen is displayed.
			 * 
			 * 'Save','Cancel' and 'Sub-resource' buttons are displayed on
			 * bottom left of the screen.
			 * 
			 * 'Sort all' button at bottom right of the screen.
			 */
			// 610511

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Save']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Sub-resources' button Expected
			 * Result:'Edit Sub Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * Sub-Resoure Type 'SRT1' and 'SRT2' are listed under it.
			 */
			// 610512
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select Check box associated with 'SRT1' and 'SRT2'
			 * select status types ST1,ST2 for both sub-resource types and click
			 * on 'Save' Expected Result:'Region Views List' screen is
			 * displayed.
			 */
			// 610513

			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { str_roleStatusTypeValues[5],
						str_roleStatusTypeValues[6] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName1, strRTvalue[1],
								strstValues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { str_roleStatusTypeValues[5],
						str_roleStatusTypeValues[6] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName2, strRTvalue[2],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> V1, click on resource 'RS'
			 * Expected Result:'View Resource Detail' screen for resource 'RS'
			 * is displayed.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Sub-resource 'SRS1' and 'SRS2' are displayed under 'SRT1' ,
			 * 'SRT2' respectively along with status types ST1,ST2
			 */
			// 610514

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
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName1, strStatTypep,
								strSubResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName2, strStatTypep,
								strSubResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as 'RegAdmin' on browser B2 Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 610515

			// Login as RegAdmin in browser B2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium1, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium1, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Resource Types ,Click on
			 * 'Edit' link associated with sub-resource type 'SRT1' Expected
			 * Result:'Edit Resource Type' screen is displayed.
			 */
			// 610516
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navToeditResrcTypepage(selenium1,
						strSubResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect 'Active' check box and click on 'Save'
			 * Expected Result:'Resource Type List' screen is displayed.
			 * 
			 * Sub-Resoure Type 'SRT1' is not listed under it.
			 */
			// 610517

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.activateAndDeactivateRTNew(selenium1,
						strSubResrctTypName1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:On browser 'B1' click on refresh link at the top
			 * left corner of the screen. Expected Result:'View Resource Detail'
			 * screen for resource 'RS' is displayed.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * Sub-resource type 'SRT1' is not displayed under Sub-resource
			 * section.
			 * 
			 * Sub-resource 'SRS2' is displayed under 'SRT2' associated with
			 * status types ST1 , ST2
			 */
			// 610519

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.refreshPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName1 + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section for Sub-resource Type"
						+ strSubResrctTypName1 + " is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName1 + " is  displayed.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName2, strStatTypep,
								strSubResource2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:On browser B2 Click on 'Edit' link associated with
			 * sub-resource type 'SRT2' Expected Result:'Edit Resource Type
			 * List' screen is displayed.
			 */
			// 610520

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navToeditResrcTypepage(selenium1,
						strSubResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Deselect 'Active' check box and click on 'Save'
			 * Expected Result:'Resource Type List' screen is displayed.
			 * 
			 * Sub-Resoure Type 'SRT2' is not listed under it.
			 */
			// 610535

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.activateAndDeactivateRTNew(selenium1,
						strSubResrctTypName2, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:On browser 'B1' click on refresh link Expected
			 * Result:'View Resource Detail' screen for resource 'RS' is
			 * displayed.
			 * 
			 * Status types NST,MST,SST,TST are displayed under section 'SEC1'
			 * 
			 * 'Sub-resources' section is not displayed.
			 */
			// 610521

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.refreshPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName1 + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section for Sub-resource Type"
						+ strSubResrctTypName1 + " is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName1 + " is  displayed.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName2 + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName2 + " is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section for Sub-resource Type "
						+ strSubResrctTypName2 + " is  displayed.";
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
			gstrTCID = "FTS-107921";
			gstrTO = "Verify that deactivated sub-resources are not displayed on view resource detail " +
					"screen on refresh.";
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

	// end//testFTS107921//
	

	//start//testFTS126270//
	/***************************************************************
	'Description		:Edit the name of status type associated with the sub-resource and verify that
	 					 status name is updated on view resource detail screen on refresh.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/18/2013
	'Author				:QSG
	'---------------------------------------------------------------
	'Modified Date							Modified By
	'Date									Name
	***************************************************************/

	@Test
	public void testFTS126270() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "126270"; // Test Case Id
			gstrTO = " Edit the name of status type associated with the sub-resource and verify that " +
					 "status name is updated on view resource detail screen on refresh.";// Test// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

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
			String statrStatypeName1 = "AutoST1_" + strTimeText;
			String statrStatypeEditName1 = "AutoST1_Edit" + strTimeText;
			String statrStatypeEditName2 = "AutoST2_Edit" + strTimeText;
			String statrStatypeName2 = "AutoST2_" + strTimeText;
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[7];
			// Status of MST
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// User
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// Section data
			String strSection1 = "Sec_" + strTimeText;
			String strSectionValue = "";

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Status types NST, MST, SST, TST,NEDOC, ST1,ST2 are created.
			 * 
			 * 2. NST , MST, SST, TST, NEDOC, ST1,ST2 status types are under
			 * status type section SEC1.
			 * 
			 * 3. Resource type RT is created selecting NST,MST,TST,SST, NEDOC.
			 * 
			 * 4. Sub Resource Type 'RT1' is created selecting ST1,ST2.
			 * 
			 * 5. Resource 'RS' is created under 'RT'.
			 * 
			 * 6. Sub-Resource 'SRS1' is created under parent resource 'RS'
			 * selecting sub-resource type 'RT1'
			 * 
			 * 7. User U1 is created selecting the following rights: a. 'View
			 * Resource' and 'Update resource' right on RS. b. Role R1 to view
			 * and update NST , MST, SST, TST, NEDOC, ST1,ST2 status types. c.
			 * 'Configure Region View' right.
			 * 
			 * 8. View 'V1' is created selecting status types NST,MST,TST,SST,
			 * NEDOC and resource type RT Expected Result:No Expected Result
			 */
			// 662763
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName2, strMSTValue, strStatTypeColor,
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

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
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
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName1);
				if (str_roleStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName2);
				if (str_roleStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[4], true);
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
				strRTvalue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/* Create sub resource type RT1 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[5] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Creating RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			/*
			 * 5. Sub-resource 'SR1' is created under parent resource 'RS'
			 * selecting 'RT1' sub-resource type.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
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
				strRSValue[1] = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" },
						{ str_roleStatusTypeValues[5], "true" },
						{ str_roleStatusTypeValues[6], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
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
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

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
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				String[] strRSValues = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, str_roleStatusTypeValues, false, strRSValues,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.SaveAndVerifyView(
						seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrStatypeName1,
						statrStatypeName2, statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statrNedocTypeName };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1',Navigate to View >> V1 Expected
			 * Result:RS is displayed under RT along with status types
			 * NST,MST,SST,TST, NEDOC.
			 */
			// 662764
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login as User in browser B1
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				String strResources[] = { strResource1 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResources, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Details provided while creating resource 'RS' are displayed
			 * appropriately.
			 * 
			 * Status types NST,MST,SST,TST, NEDOC are displayed under section
			 * 'SEC1'
			 * 
			 * 'Sub-resources' section is not displayed.
			 */
			// 662765
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
				String[][] strResouceData = { { "Type:", strResrctTypName },
						{ "Contact:", strContFName + " " + strContLName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource1, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section is displayed.";
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views,Click on 'Customize
			 * Resource Detail View' button Expected Result:'Edit Resource
			 * Detail View Sections' screen is displayed.
			 * 
			 * 'Save','Cancel' and 'Sub-resource' buttons are displayed on
			 * bottom left of the screen.
			 * 
			 * 'Sort all' button at bottom right of the screen.
			 */
			// 662766
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Save']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Sub-resources' button Expected
			 * Result:'Edit Sub Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * Sub-Resoure Type 'RT1' is listed under it.
			 */
			// 662767
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select Check box associated with 'RT1' and select
			 * status types ST1,ST2 and click on 'Save' Expected Result:'Region
			 * Views List' screen is displayed.
			 */
			// 662768
			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { str_roleStatusTypeValues[5],
						str_roleStatusTypeValues[6] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to View >> V1, click on resource 'RS'
			 * Expected Result:'View Resource Detail' screen for resource 'RS'
			 * is displayed.
			 * 
			 * Status types NST,MST,SST,TST, NEDOC are displayed under section
			 * 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Sub-resource 'SRS1' is displayed under 'RT1' along with status
			 * types ST1,ST2
			 */
			// 662769
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
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Login as 'RegAdmin' on browser B2 Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 662770
			// Login as RegAdmin in browser B2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium1, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium1, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Status types,Click on 'Edit'
			 * link associated with status type 'ST1' Expected Result:'Edit <
			 * Type of Status type type of ST1 >' screen is displayed.
			 */
			// 662771
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit name of the status type as ST1 to 'ST1_edit'
			 * and click on 'Save' Expected Result:'Status Type List' screen is
			 * displayed.
			 * 
			 * Status Type 'ST1_edit' is listed under it.
			 */
			// 662772
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium1, statrStatypeName1, statrStatypeEditName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Repeat the steps 9 and 10 for status type ST2
			 * Expected Result:'Status Type List' screen is displayed.
			 * 
			 * Status Type 'ST2_edit' is listed under it.
			 */
			// 662774
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium1, statrStatypeName2, statrStatypeEditName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:On browser 'B1' click on refresh link at the top
			 * left corner of the screen. Expected Result:'View Resource Detail'
			 * screen for resource 'RS' is displayed.
			 * 
			 * Status types NST,MST,SST,TST, NEDOC are displayed under section
			 * 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Status Type 'ST1_edit' and 'ST2_edit' are displayed under 'RT1'
			 * along with Sub-resource 'SRS1'
			 */
			// 662773
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.refreshPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeEditName1,
						statrStatypeEditName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
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
			gstrTCID = "FTS-126270";
			gstrTO = "Edit the name of status type associated with the sub-resource and verify that status" +
					"name is updated on view resource detail screen on refresh.";
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

	// end//testFTS126270//
	

	//start//testFTS126269//
	/***************************************************************
	'Description		:Verify that status types associated with sub-resources are displayed accordingly on view resource detail when added/removed on refresh.
	'Precondition		:
	'Arguments			:None
	'Returns			:None
	'Date				:10/21/2013
	'Author				:Suhas
	'---------------------------------------------------------------
	'Modified Date				               Modified By
	'Date									   Name
	***************************************************************/

	@Test
	public void testFTS126269() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Views objViews = new Views();
		General objGeneral = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "126269"; // Test Case Id
			gstrTO = " Verify that status types associated with sub-resources are displayed accordingly on view resource detail when added/removed on refresh.";// Test
																																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

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
			String statrStatypeName1 = "AutoST1_" + strTimeText;
			String statrStatypeName2 = "AutoST2_" + strTimeText;
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[7];
			// Status of MST
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strSubResrctTypName = "AutoSRt_" + strTimeText;
			String strRTvalue[] = new String[2];
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strSubResource = "AutoSRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// User
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// View Details
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			// Section data
			String strSection1 = "Sec_" + strTimeText;
			String strSectionValue = "";

			/*
			 * STEP : Action:Preconditions:
			 * 
			 * 1. Status types NST, MST, SST, TST, NEDOC, ST1,ST2 are created.
			 * 
			 * 2. NST , MST, SST, TST, NEDOC, ST1,ST2 status types are under
			 * status type section SEC1.
			 * 
			 * 3. Resource type RT is created selecting NST,MST,TST,SST, NEDOC.
			 * 
			 * 4. Sub Resource Type 'RT1' is created selecting ST1,ST2.
			 * 
			 * 5. Resource 'RS' is created under 'RT'.
			 * 
			 * 6. Sub-Resource 'SRS1' is created under parent resource 'RS'
			 * selecting sub-resource type 'RT1'
			 * 
			 * 7. User U1 is created selecting the following rights: a. 'View
			 * Resource' and 'Update resource' right on RS. b. Role R1 to view
			 * and update NST , MST, SST, TST, NEDOC, ST1,ST2 status types. c.
			 * 'Configure Region View' right.
			 * 
			 * 8. View 'V1' is created selecting status types NST,MST,TST,SST,
			 * NEDOC and resource type RT Expected Result:No Expected Result
			 */
			// 662746

			log4j.info("~~~~~PRECONDITION - " + gstrTCID
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

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNumTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrTextTypeName);
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
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrSaturatTypeName);
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
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName2, strMSTValue, strStatTypeColor,
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

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[1] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName2);
				if (str_roleStatusValue[1].compareTo("") != 0) {
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
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
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
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[5] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName1);
				if (str_roleStatusTypeValues[5].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrStatypeName2,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrStatypeName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[6] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrStatypeName2);
				if (str_roleStatusTypeValues[6].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
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
						seleniumPrecondition, strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[4], true);
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
				strRTvalue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/* Create sub resource type RT1 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strSubResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[5] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[6], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.selAndDeselSubResourceType(
						seleniumPrecondition, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.saveAndNavToResTypeList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTvalue[1] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strSubResrctTypName);
				if (strRTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch RT value";
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
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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

			/*
			 * 5. Sub-resource 'SR1' is created under parent resource 'RS'
			 * selecting 'RT1' sub-resource type.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToCreateSubResourcePage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createSubResourceWitLookUPadres(
						seleniumPrecondition, strSubResource, strAbbrv,
						strSubResrctTypName, strStandResType, false,
						strContFName, strContLName, "", "");
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
				strRSValue[1] = objRs.fetchSubResValueInResList(
						seleniumPrecondition, strSubResource);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" },
						{ str_roleStatusTypeValues[5], "true" },
						{ str_roleStatusTypeValues[6], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
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
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.ConfigRegionViews");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);

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
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				String[] strRSValues = {};
				strFuncResult = objViews.createViewNew(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, str_roleStatusTypeValues, false, strRSValues,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(
						seleniumPrecondition, strRTvalue[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.SaveAndVerifyView(
						seleniumPrecondition, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Section
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrStatypeName1,
						statrStatypeName2, statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statrNedocTypeName };
				strFuncResult = objViews
						.dragAndDropSTtoSection(seleniumPrecondition,
								strStatTypeArr, strSection1, true);
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
				strSectionValue = objViews.fetchSectionID(seleniumPrecondition,
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * STEP : Action:Login as User 'U1',Navigate to View >> V1 Expected
			 * Result:RS is displayed under RT along with status types
			 * NST,MST,SST,TST and NEDOC.
			 */
			// 662747

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			// Login as User in browser B1
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
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
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				String strResources[] = { strResource1 };
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResrctTypName, strResources, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on resource 'RS' Expected Result:'View
			 * Resource Detail' screen for resource 'RS' is displayed.
			 * 
			 * Details provided while creating resource 'RS' are displayed
			 * appropriately.
			 * 
			 * Status types NST,MST,SST,TST and NEDOC are displayed under
			 * section 'SEC1'
			 * 
			 * 'Sub-resources' section is not displayed.
			 */
			// 662748

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
				String[][] strResouceData = { { "Type:", strResrctTypName },
						{ "Contact:", strContFName + " " + strContLName } };
				strFuncResult = objViews.verifyResDetailsInViewResDetail(
						selenium, strResource1, strResouceData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info("'Sub-resources' section is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "'Sub-resources' section is displayed.";
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views,Click on 'Customize
			 * Resource Detail View' button Expected Result:'Edit Resource
			 * Detail View Sections' screen is displayed.
			 * 
			 * 'Save','Cancel' and 'Sub-resource' buttons are displayed on
			 * bottom left of the screen.
			 * 
			 * 'Sort all' button at bottom right of the screen.
			 */
			// 662749
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[value='Save']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Cancel']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sub-resources']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				strElementID = "css=input[value='Sort All']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

				if (strFuncResult.equals("")) {
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are"
							+ " displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen. ");
				} else {
					strFuncResult = "'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ";
					log4j.info("'Save','Cancel' and 'Sub-resource' buttons are "
							+ "NOT displayed on bottom left of the screen. ");
					log4j.info("'Sort all' button at bottom right of the screen NOT displayed ");
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Sub-resources' button Expected
			 * Result:'Edit Sub Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * Sub-Resoure Type 'RT1' is listed under it.
			 */
			// 662750

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select Check box associated with 'RT1' and select
			 * status types ST1,ST2 and click on 'Save' Expected Result:'Region
			 * Views List' screen is displayed.
			 */
			// 662751

			try {
				assertEquals("", strFuncResult);
				String strstValues[] = { str_roleStatusTypeValues[5],
						str_roleStatusTypeValues[6] };
				strFuncResult = objViews
						.selectStatusTypesInEditSubResourceSectionsPge(
								selenium, strSubResrctTypName, strRTvalue[1],
								strstValues, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to View >> V1, click on resource 'RS'
			 * Expected Result:'View Resource Detail' screen for resource 'RS'
			 * is displayed.
			 * 
			 * Status types NST,MST,SST,TST, NEDOC are displayed under section
			 * 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Sub-resource 'SRS1' is displayed under 'RT1' along with status
			 * types ST1,ST2
			 */
			// 662752

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
								selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as 'RegAdmin' on browser B2 Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 662753

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium1, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium1, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Navigate to Setup >> Views,Click on 'Customize
			 * Resource Detail View' button Expected Result:'Edit Resource
			 * Detail View Sections' screen is displayed.
			 */
			// 662754

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on 'Sub-resources' button Expected
			 * Result:'Edit Sub Resource Detail View Sections' screen is
			 * displayed.
			 * 
			 * Sub-Resoure Type 'RT1' is listed under it and is checked.
			 */
			// 662755

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on sub-resource type 'RT1' and deselect the
			 * check box corresponding to status type 'ST1' , 'ST2' and click on
			 * 'Save' Expected Result:'Region Views List' screen is displayed.
			 */
			// 662756

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.selAndDeselRTInEditSubResourceSectionsPge(selenium1,
								strSubResrctTypName, strRTvalue[1], false,
								false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.selOrDeselSTInEditSubResourceSectionsPge(selenium1,
								statrStatypeName1, str_roleStatusTypeValues[5],
								strRTvalue[1], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.selOrDeselSTInEditSubResourceSectionsPge(selenium1,
								statrStatypeName2, str_roleStatusTypeValues[6],
								strRTvalue[1], false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:On browser 'B1' click on refresh link at the top
			 * left corner of the screen. Expected Result:'View Resource Detail'
			 * screen for resource 'RS' is displayed.
			 * 
			 * Status types NST,MST,SST,TST, NEDOC are displayed under section
			 * 'SEC1'
			 * 
			 * 'Sub- Sub-resource 'SRS1' is not displayed Status types 'ST1' and
			 * 'ST2' are not displayed.
			 */
			// 662757
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.refreshPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium.isElementPresent("//table[@summary"
						+ "='Status for " + strSubResrctTypName + "']/"
						+ "preceding-sibling::div[1]/h1"));
				log4j.info(" Sub-resource section" + strSubResrctTypName
						+ " is not displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = " Sub-resource section " + strSubResrctTypName
						+ " is  displayed.";
				log4j.info(" Sub-resource section " + strSubResrctTypName
						+ " is  displayed.");
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName + "']/thead/"
						+ "tr/th/a[text()='" + strSubResrctTypName + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					strFuncResult = "";
					log4j.info(" Sub-resource type  " + strSubResrctTypName
							+ " is NOT displayed.");
				}
			} catch (AssertionError Ae) {
				strFuncResult = " Sub-resource Type " + strSubResrctTypName
						+ " is  displayed.";
				log4j.info(" Sub-resource type  " + strSubResrctTypName
						+ " is  displayed.");
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName + "']/thead/"
						+ "tr/th/a[text()='" + statrStatypeName1 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					strFuncResult = "";
					log4j.info(" Status Type   " + statrStatypeName1
							+ " is NOT displayed.");
				}

			} catch (AssertionError Ae) {
				strFuncResult = " Sub-resource Type " + strSubResrctTypName
						+ " is  displayed.";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@summary='Status for "
						+ strSubResrctTypName + "']/thead/"
						+ "tr/th/a[text()='" + statrStatypeName2 + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				if (strFuncResult.equals("Element NOT Present")) {
					strFuncResult = "";
					log4j.info(" Status Type   " + statrStatypeName2
							+ " is NOT displayed.");
				}

			} catch (AssertionError Ae) {
				strFuncResult = " Sub-resource Type " + statrStatypeName2
						+ " is  displayed.";
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:On browser B2 select the check box corresponding to
			 * sub-resource type 'RT1' and select status types ST1,ST2 click on
			 * 'Save' Expected Result:'Region Views List' screen is displayed.
			 */
			// 662758

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditResDetailViewSections(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navToEditSubResDetailViewSections(selenium1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.selAndDeselRTInEditSubResourceSectionsPge(selenium1,
								strSubResrctTypName, strRTvalue[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.selOrDeselSTInEditSubResourceSectionsPge(selenium1,
								statrStatypeName1, str_roleStatusTypeValues[5],
								strRTvalue[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.selOrDeselSTInEditSubResourceSectionsPge(selenium1,
								statrStatypeName2, str_roleStatusTypeValues[6],
								strRTvalue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:On browser 'B1' click on refresh link Expected
			 * Result:'View Resource Detail' screen for resource 'RS' is
			 * displayed.
			 * 
			 * Status types NST,MST,SST,TST, NEDOC are displayed under section
			 * 'SEC1'
			 * 
			 * 'Sub-resources' section is displayed.
			 * 
			 * Sub-resource 'SRS1' is displayed under 'RT1' along with status
			 * types ST1,ST2
			 */
			// 662759

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objGeneral.refreshPageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatTypeArr = { statrMultiTypeName,
						statrNedocTypeName, statrSaturatTypeName,
						statrNumTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatTypeArr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strStatTypep = { statrStatypeName1, statrStatypeName2 };
				strFuncResult = objViews
						.verifySTInViewResDetailUnderSubResource(selenium,
								strSubResrctTypName, strStatTypep,
								strSubResource);
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
			gstrTCID = "FTS-126269";
			gstrTO = "Verify that status types associated with sub-resources are displayed accordingly on view resource detail when added/removed on refresh.";
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

	// end//testFTS126269//
	
//start//testFTS119174//
	/************************************************************************************
	'Description	:Verify that NEDOC status types are updated appropriately when the  
	                 user encounters a validation message while updating the status types
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:10/24/2013
	'Author			:QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				                                             Modified By
	'Date					                                                 Name
	**************************************************************************************/

	@Test
	public void testFTS119174() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		ViewMap objViewMap = new ViewMap();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			gstrTCID = "119174"; // Test Case Id
			gstrTO = " Verify that NEDOC status types are updated appropriately when the user encounters "
					+ "a validation message while updating the status types";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Status types
			String strStatTypDefn = "Automation";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrRNedocTypeName = "rNEDOC" + strTimeText;
			String statrPNedocTypeName = "sNEDOC" + strTimeText;
			String statrSNedocTypeName = "pNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[3];

			// RT
			String strResrctTypName = "AutoRt_1" + strTimeText;
			String strRTvalue[] = new String[2];
			// RS
			String strResource = "AutoRs_1" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];

			// Role
			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			// User
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName1;
			// Search user criteria
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
		  Action:Verify that NEDOC status types are updated appropriately when the user 
		  encounters a validation message while updating the status types
		  Expected Result:No Expected Result
		*/
		//654978

			log4j.info("~~~~~~~~~~Precondtion" + gstrTCID + " starts~~~~~~~~~");

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

			// Rolebased status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue,
						statrRNedocTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrRNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrRNedocTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// private status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selStatusTypesAndCreatePrivateST(seleniumPrecondition,
								strNDSTValue, statrPNedocTypeName,
								strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrPNedocTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Shared status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue,
						statrSNedocTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisibilty = "SHARED";
				strFuncResult = objStatusTypes.selectVisibiltyValue(
						seleniumPrecondition, statrSNedocTypeName,
						strVisibilty, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSNedocTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Resource type
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
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT
						.selAndDeselSTInEditRT(seleniumPrecondition,
								str_roleStatusTypeValues[2], true);
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
				strRTvalue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResrctTypName);
				if (strRTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Resource
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
						strResrctTypName, strContFName, strContLName, strState,
						strCountry, strStandResType);
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
			// Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, true, true, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName_1);
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
				strFuncResult = objCreateUsers
						.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	

		log4j.info("~~~~~TEST-CASE" + gstrTCID + "PRECONDITION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		log4j.info("~~~~~TEST CASE" + gstrTCID + "EXECUTION STARTS~~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);
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

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strUpdateValue, str_roleStatusTypeValues[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strUpdateValue, str_roleStatusTypeValues[1], false);
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
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);
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

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue[] = { "1", "2", "30", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strUpdateValue, str_roleStatusTypeValues[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveNedocSTAndVerErrorMsg(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.cancelAndNavToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strUpdValue="241 - Disaster";
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, strUpdValue);
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

			try {
				assertEquals("", strFuncResult);
				String strUpdateValue[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strUpdateValue, str_roleStatusTypeValues[2], false);
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
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-119174";
			gstrTO = "Verify that NEDOC status types are updated appropriately when the user encounters"
					+ " a validation message while updating the status types";
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

	// end//testFTS119174//
}
