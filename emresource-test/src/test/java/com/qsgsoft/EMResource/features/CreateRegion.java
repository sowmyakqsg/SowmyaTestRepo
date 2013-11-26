package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description         :This class includes Test cases
' Requirement Group   :Setting up Regions
' Requirement         :Create Region
' Date		          :4th-Sep-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'*******************************************************************/


public class CreateRegion {
	
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSAddADocument");
	static{
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
	Selenium selenium;
	static Date_Time_settings dts = new Date_Time_settings();
	static String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
	
	// Region data
	public static String gstrRegionName = "Z_Auto" + strTimeText;
	public static String gstrTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
	public static String gstrContFN = "FN";
	public static String gstrContLN = "LN";
	public static String gstrOrg = "qsg";
	public static String gstrAddr = "qsgsoft";
	public static String gstrContactPhone1 = "3456-678-565";
	public static String gstrContactPhone2 = "2342-456-546";
	public static String gstrContactFax = "676-575-5675";
	public static String gstrContactEMail = "autoemr@qsgsoft.com";
	public static String gstrEmailFrequency = "Never";
	public static String gstrAlertFrequency = "15";
	public static String gstrInterfaceKey=dts.getCurrentDate("HHmm");
	
	public static String gstrRegionName1 ="Z_Auto" + strTimeText;
	public static String gstrTimeZone1 = "(GMT-05:00) Indiana (East)";
	public static String gstrContFN1 =  "FN";
	public static String gstrContLN1 = "LN";
	public static String gstrOrg1 ="";
	public static String gstrAddr1 = "";
	public static String gstrContactPhone1_1 =  "";
	public static String gstrContactPhone2_1 = "";
	public static String gstrContactFax1 = "";
	public static String gstrContactEMail1 = "";
	public static String gstrEmailFrequency1 = "Never";
	public static String gstrAlertFrequency1 = "15";
	public static String gstrInterfaceKey1=dts.getCurrentDate("HHmmss");
	public static String gstrDomain1="Test1";
	
	
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

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		selenium.close();

		// kill browser
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
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	
	/********************************************************************************
	'Description	:Verify that a region can be created.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4th-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testBQS64786() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();

		try {
			gstrTCID = "64786";
			gstrTO = "Verify that a region can be created.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			/*
			 * Login as RegAdmin to any region and navigate to Setup>>Regions
			 * 'Region List' screen is displayed.
			 */

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Click on 'Create New Region' 'Create New Region' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Enter mandatory fields and save Region is listed in the 'Region
			 * list' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						gstrRegionName, gstrTimeZone, gstrContFN, gstrContLN,
						gstrOrg, gstrAddr, gstrContactPhone1,
						gstrContactPhone2, gstrContactFax, gstrContactEMail,
						gstrEmailFrequency, gstrAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						gstrRegionName);
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
			 * Login as RegAdmin to the newly created region RegAdmin can
			 * successfully login to the region 'Region Default' screen is
			 * displayed
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium,
						gstrRegionName);
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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "64786";
			gstrTO = "Verify that a region can be created.";
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
	'Description		:Verify that Interface key can be provided while editing a region.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :11-Jan-2013
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS108503() throws Exception {
		Login objLogin = new Login();
		Regions objRegions = new Regions();
		String strFuncResult = "";
		boolean blnLogin = false;
		try {
			gstrTCID = "108503";
			gstrTO = " Verify that Interface key can be provided while editing a region.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			if (selenium
					.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/"
							+ "td[2][text()='" + gstrRegionName + "']") == false) {
				try {
					assertEquals("", strFuncResult);
					blnLogin = true;
					strFuncResult = objRegions.navRegionList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRegions.navCreateNewRegionPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRegions.fillAllRegnFields(selenium,
							gstrRegionName, gstrTimeZone, gstrContFN,
							gstrContLN, gstrOrg, gstrAddr, gstrContactPhone1,
							gstrContactPhone2, gstrContactFax,
							gstrContactEMail, gstrEmailFrequency,
							gstrAlertFrequency);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRegions.saveAndVerifyRegion(selenium,
							gstrRegionName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			/*
			 * 3 Click on 'Edit' next to the region RG1. 'Edit Region' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navEditRegionPge(selenium,
						gstrRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Provide an Interface key and save 'Region List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.interfaceKeyForRegion(selenium,
						gstrInterfaceKey);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						gstrRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Click on 'Edit' next to the region RG1. Interface key is
			 * displayed correctly and the field is disabled.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navEditRegionPge(selenium,
						gstrRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForInterfaceKey(selenium,
						gstrInterfaceKey, true);
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
			gstrTCID = "BQS-108503";
			gstrTO = "Verify that Interface key can be provided while editing a region.";
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
	
	/********************************************************************************
	'Description	:Verify that a region can be created providing Interface key.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:4th-Sep-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testBQS64788() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();

		try {
			gstrTCID = "64788";
			gstrTO = "Verify that a region can be created providing Interface key.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			/*
			 * Login as RegAdmin to any region and navigate to Setup>>Regions
			 * 'Region List' screen is displayed.
			 */

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Click on 'Create New Region' 'Create New Region' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Enter mandatory fields and save Region is listed in the 'Region
			 * list' screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions
						.fillAllRegnFieldsWitInterFaceKeyAndDomain(selenium,
								gstrRegionName1, gstrTimeZone1, gstrContFN1,
								gstrContLN1, gstrOrg1, gstrAddr1,
								gstrContactPhone1_1, gstrContactPhone2_1,
								gstrContactFax1, gstrContactEMail1,
								gstrEmailFrequency1, gstrAlertFrequency1,
								gstrInterfaceKey1, gstrDomain1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						gstrRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navEditRegionPge(selenium,
						gstrRegionName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String s = selenium
						.getAttribute("//input[@name='interfaceKey']/@disabled");

				if (s.compareTo("true") == 0) {
					log4j
							.info("Interface key is displayed correctly and the field is disabled. ");
				} else {
					strFuncResult = "Interface key is NOT displayed correctly and the field is NOT disabled. ";
					log4j
							.info("Interface key is NOT displayed correctly and the field is NOT disabled. ");
					gstrReason = strFuncResult;
				}

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
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "64788";
			gstrTO = "Verify that a region can be created providing Interface key.";
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
	 * 'Description :Verify that a region can be edited. 'Precondition :
	 * 'Arguments :	None 
	 * 'Returns :	None
	 * 'Date :	9/8/2012 
	 * 'Author 	:QSG
	 * '---------------------------------------------------------------
	 * 'Modified Date 								Modified By 
	 * 'Date 											Name
	 ***************************************************************/

	@Test
	public void testBQS64787() throws Exception {
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		String strFuncResult = "";
		boolean blnLogin = false;
		try {
			gstrTCID = "64787"; // Test Case Id
			gstrTO = " Verify that a region can be edited.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			if (selenium
					.isElementPresent("//div[@id='mainContainer']/table/tbody/tr/"
							+ "td[2][text()='" + gstrRegionName1 + "']") == false) {
				try {
					assertEquals("", strFuncResult);
					blnLogin = true;
					strFuncResult = objRegions.navRegionList(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRegions.navCreateNewRegionPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRegions
							.fillAllRegnFieldsWitInterFaceKeyAndDomain(
									selenium, gstrRegionName1, gstrTimeZone1,
									gstrContFN1, gstrContLN1, gstrOrg1,
									gstrAddr1, gstrContactPhone1_1,
									gstrContactPhone2_1, gstrContactFax1,
									gstrContactEMail1, gstrEmailFrequency1,
									gstrAlertFrequency1, gstrInterfaceKey1,
									gstrDomain1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRegions.saveAndVerifyRegion(selenium,
							gstrRegionName1);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navEditRegionPge(selenium,
						gstrRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.verifyAllRegnFieldsValues(selenium,
						gstrRegionName1, gstrTimeZone1, gstrContFN1,
						gstrContLN1, gstrOrg1, "", "", "", "", "",
						gstrEmailFrequency1, gstrAlertFrequency1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Edit mandatory data and save Expected
			 * Result:Changes are updated on the 'Region List' screen
			 */
			// 382091

			try {
				assertEquals("", strFuncResult);
				// Region data
				gstrRegionName1 = "Z_EAuto" + System.currentTimeMillis();
				gstrTimeZone1 = "(GMT-05:00) Eastern Time (US, Canada)";
				gstrContFN1 = "FN" + gstrRegionName1;
				gstrContLN1 = "LN" + gstrRegionName1;
				gstrEmailFrequency1 = "Never";
				gstrAlertFrequency1 = "30";
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						gstrRegionName1, gstrTimeZone1, gstrContFN1,
						gstrContLN1, "", "", "", "", "", "",
						gstrEmailFrequency1, gstrAlertFrequency1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						gstrRegionName1);
				log4j.info("Changes are updated on the 'Region List' screen");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions
						.verifyDomainInterfaceInRegionListPge(selenium,
								gstrRegionName1, gstrInterfaceKey1,
								gstrDomain1, true, true);

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
			gstrTCID = "BQS-64787";
			gstrTO = "Verify that a region can be edited.";
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

}
