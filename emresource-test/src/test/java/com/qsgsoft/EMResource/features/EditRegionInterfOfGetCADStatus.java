package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;

/********************************************************************
' Description		:This class contains test cases from  requirement
' Requirement		:Edit region interface of type 'Get CAD Status'
' Requirement Group	:Setting Region Interfaces
' Product		    :EMResource Base
' Date			    :26-10-2012
' Author		    :QSG
'*******************************************************************/

public class EditRegionInterfOfGetCADStatus {

	// Log4j object to write log entries to the Log files
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditRegionInterfOfGetCADStatus");

	static {
		BasicConfigurator.configure();
	}
	// Objects to access the common functions
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;

	/*
	 * Global variables to store the test case details � TestCaseID, Test
	 * Objective,Result, Reason for failure
	 */
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	// Selenium Object
	Selenium selenium;

	// Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();

	public Properties propElementDetails; // Property variable for ElementID
											// file
	public Properties propEnvDetails;// Property variable for Environment data
	public Properties propPathDetails; // Property variable for Path information
	public Properties propAutoITDetails;// Property variable for AutoIT file
										// details
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;// Variable to store browser name
	public static String gstrTimetaken, gstrdate, gstrtime, gstrBuild;// Result
																		// Variables

	double gdbTimeTaken; // Variable to store the time taken

	public static Date gdtStartDate;// Date variable

	public static long sysDateTime;
	public static long gsysDateTime = 0;
	public static String gstrTimeOut = "";
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId = "", StrSessionId1, StrSessionId2;

	/**********************************************************************************
	* This function is called the setup() function which is executed before every test.
	*
	* The function will take care of creating a new selenium session for every test
	*
    ***********************************************************************************/

	@BeforeClass
	public static void setUpInterface() throws Exception {

		Date gdtStartDate;
		ReadData rdExcel;

		String gstrTCID, gstrTO, gstrResult, gstrReason;
		String gstrTimetake = "", gstrdate, gstrBuild, gstrBrowserName, strSessionId = "";
		double gdbTimeTaken;
		long gslsysDateTime = 0;
		OfficeCommonFunctions objOFC;
		Properties propEnvDetails;

		Properties pathProps;

		Selenium selenium;

		gdtStartDate = new Date();
		gstrBrowserName = "IE 8";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();

		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		Interface objInterface = new Interface();

		try {

			gstrTCID = "Inactivate Interface";
			gstrTO = "Inactivate Interface";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objInterface.navToInterfaceList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				if (selenium
						.isElementPresent("//div[@id='mainContainer']/table"
								+ "[@summary='Interfaces']/tbody/tr")) {
					int intInterfaceCnt = selenium.getXpathCount(
							"//div[@id='mainContainer']/table"
									+ "[@summary='Interfaces']/tbody/tr")
							.intValue();
					log4j.info(intInterfaceCnt);

					for (int i = intInterfaceCnt; i >= 1; i--) {
						log4j.info(i);

						if (i % 5 == 0) {

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.logout(selenium);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								blnLogin = false;
								strFuncResult = objLogin.login(selenium,
										strLoginUserName, strLoginPassword);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

							try {
								assertEquals("", strFuncResult);
								strFuncResult = objLogin.navUserDefaultRgn(
										selenium, strRegn);

							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}
							try {
								assertEquals("", strFuncResult);
								blnLogin = true;
								strFuncResult = objInterface
										.navToInterfaceList(selenium);
							} catch (AssertionError Ae) {
								gstrReason = strFuncResult;
							}

						}
						try {

							String strInterfaceName = selenium
									.getText("//div[@id='mainContainer']/table"
											+ "[@summary='Interfaces']/tbody/tr["
											+ i + "]/td[2]");

							if (selenium
									.isElementPresent("//div[@id='mainContainer']/table"
											+ "[@summary='Interfaces']/tbody/tr/td[2][text()='"
											+ strInterfaceName
											+ "']"
											+ "/following-sibling::td[1][text()='Active']")) {
								try {
									assertEquals("", strFuncResult);

									strFuncResult = objInterface
											.navEditRegionInterfacePage(
													selenium, strInterfaceName);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}
								try {
									assertEquals("", strFuncResult);

									strFuncResult = objInterface
											.inactivateInterface(selenium,
													strInterfaceName, false);
								} catch (AssertionError Ae) {
									gstrReason = strFuncResult;
								}

							} else {
								log4j.info("Interface is already disabled");
							}

						} catch (Exception e) {
							strFuncResult = e.toString();
							gstrReason = strFuncResult;

						}
					}

				} else {
					log4j.info("No Interface found in Interface list");

					String interfaceNameExisting = "Intrf" + strTimeText;
					String strType = "Get CAD Status";
					String strDesc = "Automation";
					String strContactInfo = "QSG";

					try {
						assertEquals("", strFuncResult);

						strFuncResult = objInterface
								.navToInterfaceList(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objInterface
								.navToSelInterfaceTypePageNew(selenium);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objInterface.navCreateNewInterfPage(
								selenium, strType);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strResId = "Use the EMResource Resource ID";
						strFuncResult = objInterface.fillCreatRgnInterfaceFlds(
								selenium, interfaceNameExisting, strDesc,
								strContactInfo, strResId, false, false);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						strFuncResult = objInterface.savAndVerifyInterface(
								selenium, interfaceNameExisting);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

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
		} catch (Exception e) {
			gstrTCID = "Inactivate Interface";
			gstrTO = "Inactivate Interface";
			gstrResult = "FAIL";
			gstrReason = "";

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
				gstrResult = "FAIL";
			}
		}

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

		FILE_PATH = pathProps.getProperty("Resultpath");
		// and execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		gstrBuild = propEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}

	@Before
	public void setUp() throws Exception {

		// Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		// Retrieve browser information
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// Retrieve the value of page load time limit
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		// create an object to refer to Element ID properties file
		@SuppressWarnings("unused")
		ElementId_properties objelementProp = new ElementId_properties();
		// Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));
		// Start Selenium RC
		selenium.start();
		// Maximize the window
		selenium.windowMaximize();
		selenium.setTimeout(propEnvDetails.getProperty("TimeOut"));
		// Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}
   /***************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and 
	* writing the execution result of the test. 
	*
    ****************************************************************************************/
	@After
	public void tearDown() throws Exception {
		// kill browser
		selenium.close();
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

		// Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		// Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		// Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		// Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason = gstrReason.replaceAll("'", " ");
		// Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);
	}

	/**************************************************************************************
	'Description	:Verify that a region interface of type 'Get CAD Status' can be edited.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:26-10-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	**************************************************************************************/

	@Test
	public void testBQS101822() throws Exception {
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Interface objInterface = new Interface();
		String strFuncResult = "";
		try {
			gstrTCID = "101822"; // Test Case Id
			gstrTO = " Verify that a region interface of type 'Get CAD Status' can be edited.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			gstrTimetaken = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strLoginUserName, strLoginPassword, strRegn, strIntLable, strInterfaceName, 
			strDesc, strContactInfo, strResId, strWebServiceAction, strActive, strType, strSchema;	

			/*
			 * STEP : Action:Preconditions: <br> <br>Interface 'Test Get CAD
			 * Status' of type 'Get CAD Status' is created in region RG1
			 * Expected Result:No Expected Result
			 */
			// 588971
			strLoginUserName = objrdExcel.readData("Login", 3, 1);
			strLoginPassword = objrdExcel.readData("Login", 3, 2);
			strRegn = objrdExcel.readData("Login", 3, 4);

			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navToInterfaceList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface
						.navToSelInterfaceTypePage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			strIntLable = "Get CAD Status";

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navCreateNewInterfPage(selenium,
						strIntLable);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Interface
			strInterfaceName = "AutoIntr_" + strTimeText;
			strActive = "Disabled";
			strWebServiceAction = "getCadStatus";
			strType = strIntLable;
			strSchema = "CADStatus 1.0";
			strDesc = "auto";
			strContactInfo = "";
			strResId = "Use the EMResource Resource ID";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.fillCreatRgnInterfaceFlds(
						selenium, strInterfaceName, strDesc, strContactInfo,
						strResId, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.savAndVerifyInterface(selenium,
						strInterfaceName);
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
			 * STEP : Action:Login as 'RegAdmin' to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 588972
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
			 * STEP : Action:Navigate to Setup >> Interfaces,Click on edit link
			 * associated with 'Test Get CAD Status' interface. Expected
			 * Result:'Edit region Interface' screen is displayed. <br> <br>Data
			 * provided while creating the interface is retained in all the
			 * fields.
			 */
			// 588973

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navToInterfaceList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navEditRegionInterfacePage(
						selenium, strInterfaceName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface
						.chkDataRetainedInEditRgnInterfaceFlds(selenium,
								strInterfaceName, strDesc, strContactInfo,
								strResId, strWebServiceAction, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Edit the mandatory data of interface along with
			 * description and click on 'Save' Expected Result:'Interface List'
			 * screen is displayed. <br> <br>Interface 'Test Get CAD Status' is
			 * displayed under it with the updated values under appropriate
			 * columns.
			 */
			// 588974

			strInterfaceName = "AutoEdInter" + strTimeText;
			strDesc = "autoq";
			strContactInfo = "";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.fillCreatRgnInterfaceFlds(
						selenium, strInterfaceName, strDesc, strContactInfo,
						strResId, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.savAndVerifyInterface(selenium,
						strInterfaceName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.checkIntergaceDetailsInIntListPage(
						selenium, strInterfaceName, strActive,
						strWebServiceAction, strType, strSchema, strDesc);
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
			gstrTCID = "BQS-101822";
			gstrTO = "Verify that a region interface of type 'Get CAD Status' can be edited.";
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
			selenium.selectWindow("");
			strFuncResult = objLogin.logout(selenium);
			try {
				assertEquals("", strFuncResult);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		}
	}
}
