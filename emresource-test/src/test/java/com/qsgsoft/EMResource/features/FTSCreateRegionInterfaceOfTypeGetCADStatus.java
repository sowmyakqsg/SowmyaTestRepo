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
import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Interface;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Regions;
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
' Requirement Group   :Create region interface of type 'Get CAD Status'
' Requirement         :Add a document
' Date		          :22-Oct-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'*******************************************************************/

public class FTSCreateRegionInterfaceOfTypeGetCADStatus {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features."
			+ "FTSCreateRegionInterfaceOfTypeGetCADStatus");
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
	public static String interfaceNameExixting = "";
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
					String strType = "Get Hospital Status";
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
	
	//start//testFTS109266//
	/*************************************************************************
	'Description	:Create an interface selecting 'Get CAD status' and verify 
	                 that the interface is listed in the Interface list screen.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:7/24/2013
	'Author			:QSG
	'------------------------------------------------------------------------
	'Modified Date				                              Modified By
	'Date					                                  Name
	*************************************************************************/

	@Test
	public void testFTS109266() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Interface objInterface = new Interface();
		General objGeneral = new General();
		Regions objRegions=new Regions();
		try {
			gstrTCID = "109266"; // Test Case Id
			gstrTO = " Create an interface selecting 'Get CAD status' and verify that the interface is " +
					"listed in the Interface list screen.";//TO																				// Objective
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
			String strInterFaceKey="";

			String strAction = "Edit";
			String strInterfaceName = "Inter" + strTimeText;
			String strActive = "Active";
			String strWebServiceAction = "getCadStatus";
			String strType = "Get CAD Status";
			String strSchema = "CADStatus 1.0";
			String strDesc = "Automation";
			String strContactInfo = "QSG";

			String interfaceNameExixting = "";
			String strActionExisting = "";
			String strActiveExisting = "";
			String strWebServiceActionExisting = "";
			String strTypeExisting = "";
			String strSchemaExisting = "";
			String strDescExisting = "";

			String[] strHeader = { "Action", "Name", "Active",
					"Web Services Action", "Type", "Schema", "Description" };

		/*
		* STEP :
		  Action:Precondition: 
		  No other active interface is present in the region with the interface type 'Get CAD Status'.
		  Expected Result:No Expected Result
		*/
		//614594
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
			try {
				assertEquals("", strFuncResult);
				strInterFaceKey = objRegions.fetchInterFaceKey(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as 'RegAdmin' to region 'RG1'
		  Expected Result:'Region Default' screen is displayed.
		*/
		//614595
		
		/*
		* STEP :
		  Action:Navigate to Setup >> Interfaces
		  Expected Result:'Interface List' screen is displayed.
			'Create New Interface' button is present.
			Already created Interfaces if any are listed.
		*/
		//614597
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
		* STEP :
		  Action:Click on 'Create New Interface' button, select 'Get CAD Status' 
		  from the drop down and click on next
		 Expected Result:'Create Region Interface' screen is displayed.
			The following fields are present:
			1. Name
			2. Description
			3. Contact Information
			4. Interface key has the same value as that provided for the region and is disabled.
			5. Webservice action: Filled with 'getCadStatus' by default
			6. Resource Identification Method: 'Use the EMResource Resource ID' is the only option under this.
			7. 'Active' checkbox
		'Optional Status Type mappings' section is displayed with the following columns:
			1. Include? - Check box
			2. Type
			3. Standard Status type
			4. Regional status type
			5. Description
		The following Standard status types are available under the 'Optional Status type Mappings' section:
			1. ALS 
			2. Average Wait Time
			3. BLS
			4. CCT
			5. Maximum Wait Time
			6. Units Available
			7. Units at Destination
			8. Units en Route
		*/
		//614598
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navToSelInterfaceTypePageNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.navCreateNewInterfPage(selenium,strType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRSOption="Use the EMResource Resource ID";
				strFuncResult = objInterface.chkFldsInEditRegionInterfaceOfGETCADStatus(selenium,
						strWebServiceAction, strInterFaceKey, strRSOption);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.chkFldsOptionalStatusTypeMappings(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Provide data in 'Name', 'Description' and 'Contact Information' fields, select 'ALS',
		   'BLS' and 'CCT' under 'Optional Status Type Mappings' section and click on 'Save'
		   Expected Result:Created interface is listed on 'Interface List' >screen with appropriate
		   data in the following fields:
				1. Name
				2. Active is displayed under 'Active' column
				3. 'getCadStatus' is displayed under 'Web Services Action'
				4. 'GET CAD Status' under 'Type' column
				5. 'CADStatus 1.0' under 'Schema' column
				6. Description provided.
		*/
		//614599

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
				String strOptionName = "ALS";
				strFuncResult = objInterface.selDeselctOptionalStatMappings(
						selenium, strOptionName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptionName = "BLS";
				strFuncResult = objInterface.selDeselctOptionalStatMappings(
						selenium, strOptionName, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptionName = "CCT";
				strFuncResult = objInterface.selDeselctOptionalStatMappings(
						selenium, strOptionName, true);
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

					strFuncResult = objInterface
							.checkDataInIntergaceListPageNew(selenium,
									strHeader[intRec], strInterfaceName,
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

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				strFuncResult = gstrReason;
			}		

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "FTS-109266";
			gstrTO = "Create an interface selecting 'Get CAD status' and verify that the interface " +
					"is listed in the Interface list screen.";
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

	//end//testFTS109266//
}
