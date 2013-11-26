package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
import com.qsgsoft.EMResource.shared.*;

/**********************************************************************
' Description         :This class includes Test cases
' Requirement         :CreateRegion Interface Of Type PostResourceDetailStatus
' Requirement  Group  : Setting Region Interfaces 
' Date		          :20-Oct-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'*******************************************************************/
public class CreateRegionInterfaceOfTypePostResourceDetailStatus {

	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CreateRegionInterfaceOfTypePostResourceDetailStatus");
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

	String gstrTimeOut = "";

	Selenium selenium;
	
	
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
				4444, propEnvDetails.getProperty("Browser"), propEnvDetails
						.getProperty("urlEU"));

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

		boolean blnLogin = false;
		String strFuncResult = "";

		Login objLogin = new Login();// object of class Login
		Interface objInterface=new Interface();


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
				blnLogin=true;
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

		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

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
	

/***************************************************************
'Description		:Verify that a region interface of type 'Post Resource Detail Status' can be created.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:10/22/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testBQS94127() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Interface objInterface = new Interface();
		General objGeneral = new General();
		try {
			gstrTCID = "94127"; 
			gstrTO = " Verify that a region interface of type 'Post Resource Detail" +
					" Status' can be created.";
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			String strAction = "Edit";
			String strInterfaceName = "Inter" + strTimeText;
			String strWebServiceAction = "postResourceDetailStatus";
			String strActive = "Active";
			String strType = "Post Resource Detail Status";
			String strSchema = "ResourceDetailRequest 1.0";
			String strDesc = "Automation";
			String strContactInfo = "QSG";
			
			String interfaceNameExixting ="";
			String strActionExisting = "";
			String strActiveExisting = "";
			String strWebServiceActionExisting = "";
			String strTypeExisting = "";
			String strSchemaExisting = "";
			String strDescExisting = "";
			
			
			String[] strHeader = { "Action", "Name", "Active",
					"Web Services Action", "Type", "Schema", "Description" };
			/*
			 * STEP : Action:Preconditions: No other active interface is present
			 * in the region with the interface type 'Post Resource Detail
			 * Status' Expected Result:No Expected Result
			 */
			// 559461
			/*
			 * STEP : Action:Login as 'RegAdmin' to region 'RG1' Expected
			 * Result:'Region Default' screen is displayed.
			 */
			// 559462
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Setup >> Interfaces Expected
			 * Result:'Interface List' screen is displayed. 'Create New
			 * Interface' button is present. (All ready created Interfaces if
			 * any) Interfaces are listed appropriately under interface list.
			 */
			// 559463
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objInterface.navToInterfaceList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				interfaceNameExixting = selenium
						.getText("//div[@id='mainContainer']/table"
								+ "[@summary='Interfaces']/tbody/tr[1]/td[2]");

				strActionExisting = selenium
						.getText("//div[@id='mainContainer']/table"
								+ "[@summary='Interfaces']/tbody/tr[1]/td[1]/a");

				strActiveExisting = selenium
						.getText("//div[@id='mainContainer']/table[@summary='Interfaces']/tbody/tr[1]/td[3]");

				strWebServiceActionExisting = selenium
						.getText("//div[@id='mainContainer']/table[@summary='Interfaces']/tbody/tr[1]/td[4]");

				strTypeExisting = selenium
						.getText("//div[@id='mainContainer']/table[@summary='Interfaces']/tbody/tr[1]/td[5]");

				strSchemaExisting = selenium
						.getText("//div[@id='mainContainer']/table[@summary='Interfaces']/tbody/tr[1]/td[6]");

				strDescExisting = selenium
						.getText("//div[@id='mainContainer']/table[@summary='Interfaces']/tbody/tr[1]/td[7]");

				String strElementID = propElementDetails
						.getProperty("Interface.CreateNewInterface");
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("'Create New Interface' button is present. ");
				
				
			} catch (AssertionError Ae) {
				strFuncResult="'Create New Interface' button is NOT present. ";
				gstrReason = strFuncResult;
				log4j.info("'Create New Interface' button is NOT present. ");
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strDataExisting = { strActionExisting,
						interfaceNameExixting, strActiveExisting,
						strWebServiceActionExisting, strTypeExisting,
						strSchemaExisting, strDescExisting };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {

					strFuncResult = objInterface
							.checkDataInIntergaceListPageNew(selenium,
									strHeader[intRec], interfaceNameExixting,
									strDataExisting[intRec], String
											.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * STEP : Action:Click on 'Create New Interface' button,Select 'Post
			 * Resource Detail Status' from the drop down and click on next
			 * Expected Result:'Create Region Interface' screen is displayed.
			 * Under web service Action field 'postResourceDetailStatus' is
			 * displayed by default.
			 */
			// 559465
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface
						.navToSelInterfaceTypePageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navCreateNewInterfPage(selenium,
						strType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "css=input[name='actionName'][value='"
						+ strWebServiceAction + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("Under web service Action field '"+strWebServiceAction+"' " +
								"is displayed by default. ");
			} catch (AssertionError Ae) {
				strFuncResult = "Under web service Action field '"+strWebServiceAction+"'" +
						" is NOT displayed by default. ";
				gstrReason = strFuncResult;
				log4j
						.info("Under web service Action field '"+strWebServiceAction+"' " +
								"is NOT displayed by default. ");
			}
			/*
			 * STEP : Action:Fill the mandatory data and select Active check box
			 * click on save. Expected Result:'Interface' is created and listed
			 * in 'Interface List' screen with appropriate values as provided
			 * while creating interface under all the columns.
			 */
			// 559466
			try {
				assertEquals("", strFuncResult);
				String strResId = "Use the EMResource Resource ID";
				strFuncResult = objInterface.fillCreatRgnInterfaceFlds(
						selenium, strInterfaceName, strDesc, strContactInfo,
						strResId, false, true);
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
				String[] strData = { strAction, strInterfaceName, strActive,
						strWebServiceAction, strType, strSchema, strDesc };

				for (int intRec = 0; intRec < strHeader.length; intRec++) {

					strFuncResult = objInterface.checkDataInIntergaceListPageNew(
							selenium, strHeader[intRec], strInterfaceName,
							strData[intRec], String.valueOf(intRec + 1));
					try {
						assertTrue(strFuncResult.equals(""));
					} catch (AssertionError ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
		/*	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.VerifyInterfaceOtherFields(
						selenium, strInterfaceName, strActive,
						strWebServiceAction, strType, strSchema, strDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}*/

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
			gstrTCID = "BQS-94127";
			gstrTO = "Verify that a region interface of type 'Post Resource Detail Status' can be created.";
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
