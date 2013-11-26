package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;
import org.junit.*;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
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

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement Group	:Setting up Status types 
' Requirement 	    :Associating section to a status type
� Product		    :EMResource v3.24
' Date			    :26/July/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class HappyDayAssociatingSectionToAstatusType {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.HappyDay_UpdateStatusScreen");
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
	Selenium selenium,seleniumPrecondition,seleniumFirefox;

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
		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
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
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		seleniumFirefox.stop();
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
	
	/*****************************************************************************
	'Description :Verify that section can be selected while creating a status type
	'Arguments	 :None
	'Returns	 :None
	'Date		 :26/July/2013
	'Author		 :QSG
	'-----------------------------------------------------------------------------
	'Modified Date				                                       Modified By
	'Date					                                           Name
	*******************************************************************************/
	@Test
	public void testHapyDay124083() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ResourceTypes objResourceTypes = new ResourceTypes(); // object of class ResourceTypes
		StatusTypes objStatusTypes = new StatusTypes();// object of class  StatusTypes
		Resources objResources = new Resources();// object of class  Resources
		Views objViews = new Views();// object of class  Views
		ViewMap objViewMap = new ViewMap();// object of class  ViewMap
		try {
			gstrTCID = "124083"; // Test Case Id
			gstrTO = "Verify that section can be selected while creating a status type";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
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
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";
			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// sec data
			String strSection1 = "SEC_" + strTimeText;
			String strSectionValue = "";

		/*Step1:Action:Check 'Select All', 'Clear All', 'Show All Statuses' links are left aligned  
		 *             on the update status screen from Regional view, custom view, map screen.
		 *             (associate more than one status type) 		
		 * Expected Result: No Expected Result 		 */
			
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			strFuncResult = objLogin.login(seleniumFirefox,
					strLoginUserName, strLoginPassword);
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumFirefox, strRegn);
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
				String[] strStatTypeArr = {};
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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strNSTValue, "Create");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrNumTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
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
						selenium, strTSTValue, statrTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strTSTValue, "Create");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrTextTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
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
						selenium, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strSSTValue, "Create");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrSaturatTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
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
						selenium, strMSTValue, statrMultiTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strSSTValue, "Create");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifyMultiST(selenium,
						statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrMultiTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
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
						selenium, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						selenium, statrMultiTypeName,
						str_roleStatusName2, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(selenium,
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
						.fetchStatValInStatusList(selenium,
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
						selenium, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strSSTValue, "Create");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrNedocTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
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
						.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			for (int intST = 1; intST < str_roleStatusTypeValues.length; intST++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objResourceTypes.selAndDeselSTInEditRT(
							selenium,
							str_roleStatusTypeValues[intST], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName);
				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.createResourceWitLookUPadresSharWitRgn(
								selenium, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources.fetchResValueInResList(
						selenium, strResource);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			// Edit Resource Detail View Sections
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				strFuncResult = objViews.verifyStatusTypesInNewSection(
						selenium, strStatType, strSection1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// View Map
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "124083"; // Test Case Id
			gstrTO = "Verify that section can be selected while creating a status type";// TO
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
	
	/*****************************************************************************
	'Description :Verify that Section can be selected while editing a status type
	'Arguments	 :None
	'Returns	 :None
	'Date		 :29/July/2013
	'Author		 :QSG
	'-----------------------------------------------------------------------------
	'Modified Date				                                       Modified By
	'Date					                                           Name
	*******************************************************************************/
	@Test
	public void testHapyDay124084() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		ResourceTypes objResourceTypes = new ResourceTypes();// object of class  ResourceTypes
		StatusTypes objStatusTypes = new StatusTypes();// object of class  StatusTypes
		Resources objResources = new Resources();// object of class Resources
		Views objViews = new Views();// object of class  Views
		ViewMap objViewMap = new ViewMap();// object of class  ViewMap
		try {
			gstrTCID = "124084"; // Test Case Id
			gstrTO = "Verify that section can be selected while editing a status type";// TO
			gstrReason = "";
			gstrResult = "FAIL";
			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");
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
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];
			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusName2 = "rSb" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];
			str_roleStatusValue[0] = "";
			str_roleStatusValue[1] = "";
			// RT
			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRsTypeValues = new String[1];
			// RS
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			// sec data
			String strSection1 = "SEC_" + strTimeText;
			String strSectionValue = "";

		/*Step1:Action:Check 'Select All', 'Clear All', 'Show All Statuses' links are left aligned  
		 *             on the update status screen from Regional view, custom view, map screen.
		 *             (associate more than one status type) 		
		 * Expected Result: No Expected Result 
		 */
			
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
			//Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type NST
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
			// Role based status type TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, true);
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
			// Role based status type SST
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
			// Role based status type MST
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
			// Role based status type NDST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, true);
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
			for (int intST = 1; intST < str_roleStatusTypeValues.length; intST++) {
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

			// RS
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
						.createResourceWitLookUPadresSharWitRgn(
								seleniumPrecondition, strResource, strAbbrv,
								strResrcTypName, true, strContFName,
								strContLName, strState, strCountry,
								strStandResType);
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumFirefox, strRegn);
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
				String[] strStatTypeArr = {};
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
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium, statrNumTypeName, strNSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strNSTValue, "Edit");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrNumTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrNumTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrTextTypeName, strTSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strTSTValue, "Edit");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrTextTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrTextTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			//SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrSaturatTypeName, strSSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strSSTValue, "Edit");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrSaturatTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrSaturatTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrMultiTypeName, strMSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strMSTValue, "Edit");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrMultiTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrMultiTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//NDST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypePage(selenium,
						statrNedocTypeName, strNDSTValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.verSectionInStatusTypeTypeDropDown(selenium,
								strSection1, strSectionValue, true,
								strNDSTValue, "Edit");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selSectionForStatusType(
						selenium, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySectionInStatPage(selenium,
						statrNedocTypeName, strSection1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Edit Resource Detail View Sections
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToEditResDetailViewSections(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				strFuncResult = objViews.verifyStatusTypesInNewSection(
						selenium, strStatType, strSection1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// View Map
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToViewResourceDetailPageFrmRSPopUp(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { statrMultiTypeName,
						statrNedocTypeName, statrNumTypeName,
						statrSaturatTypeName, statrTextTypeName };
				strFuncResult = objViews.verifySTInViewResDetailUnderSection(
						selenium, strSection1, strSectionValue, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "124084"; // Test Case Id
			gstrTO = "Verify that section can be selected while editing a status type";// TO
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
