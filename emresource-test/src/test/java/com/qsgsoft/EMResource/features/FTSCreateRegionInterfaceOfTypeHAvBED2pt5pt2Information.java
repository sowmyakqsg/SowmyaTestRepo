package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.Interface;
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


/*****************************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Setting Region Interfaces 
' Requirement Group	:Create region interface of type 'HAvBED 2.5.2 Information'
� Product		    :EMResource v3.19
' Date			    :4/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*****************************************************************************/

public class FTSCreateRegionInterfaceOfTypeHAvBED2pt5pt2Information {
	
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateRegionInterfaceOfTypeHAvBED2pt5pt2Information");
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
					String strType = "Post HAvBED 2.5.2 Information";
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
	

	//start//testFTS67105
	/***************************************************************
	 * 'Description		:Verify that all the resources in the region are displayed under 
	 *                   'Allowed Resources' section in the 'Create Interface' screen of 
	 *                   'Post Havbed 2.5.2 Information' interface
	 * 'Precondition	:
	 * 'Arguments		:None
	 * 'Returns			:None
	 * 'Date			:8/13/2013 
	 * 'Author			:QSG 
	 * ---------------------------------------------------------------
	 * 'Modified Date                            Modified By  
	 * 'Date                                     Name
	 ***************************************************************/
	@Test
	public void testFTS67105() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Interface objInterface = new Interface();
		try {

			gstrTCID = "67105"; // Test Case Id
			gstrTO = " Verify that all the resources in the region are displayed under 'Allowed Resources'" +
					" section in the 'Create Interface' screen of 'Post Havbed 2.5.2 Information' interface.";// Test
			// Objective
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
			String strStatusTypeValue = "Number";
			String statTypeName = "Status_Ty" + strTimeText;
			String strStatTypDefn = "Stdef";
			String strResrcTypName = "Res_Ty1" + strTimeText;
			String strResource = "Res1" + strTimeText;
			String strAbbrv = "Abb1";
			String strStandResType = "Aeromedical";
			String strExternalId = "Exterid";
			String strStreetAddr = "Saddrs";
			String strCity = "Scity";
			String strState = "Alabama";
			String strCounty = "Barbour County";
			String strContFName = "FN";
			String strContLName = "LN";
			String strZipCode = "11122";
			String strWebSite = "";
			String strTitle = "Title";
			String strContAddr = "Cadd";
			String AHAID = "";
			String strContPh1 = "";
			String strContPh2 = "";
			String strContFax = "";
			String strContEmail = "autoemr@qsgsoft.com";
			String strNotes = "Notes";
			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[2];
			String strResName = "Res_Ty2" + strTimeText;
			String strRSAbbr = "Abb2";
			String strStdRT = "Aeromedical";
			String strFName = "FN1";
			String strLName = "LN1";
			String strCountry = "Barbour County";
			String strIntLable = "Post HAvBED 2.5.2 Information";
			String strInterfaceName = "Inte" + strTimeText;
			String strDesc = "Indes";
			String strContactInfo = "Info";
			String strResId = "Use the EMResource Resource ID";
			/*
			 * STEP : Action:Precondition: <br>1. RS1 is created selecting the
			 * checkbox 'Reports HAvBED data' <br>2. RS2 is created without
			 * selecting the checkbox 'Reports HAvBED data' Expected Result:RS1
			 * and RS2 are displayed under 'Allowed Resources' section in
			 * 'Create Interface' screen of 'Post Havbed 2.5.2 Information'
			 * interface.
			 */
			// 397515
			
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
           //Login
			try {
				assertEquals("", strFuncResult);
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
           //Creating ST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
           //Fetching ST
			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statTypeName);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Creating RT
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
						selenium, strResrcTypName, strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						selenium, strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
           //Creating RS1
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
						.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResource_FillAllFields(
						selenium, strResource, strAbbrv,
						strResrcTypName, strStandResType, true, false, AHAID,
						strExternalId, false, strStreetAddr, strCity, strState,
						strZipCode, strCounty, strWebSite, strContFName,
						strContLName, strTitle, strContAddr, strContPh1,
						strContPh2, strContFax, strContEmail, strNotes);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResourceInRSList(
						selenium, strResource);
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
           //Creating RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResName, strRSAbbr,
						strResrcTypName, strFName, strLName, strState,
						strCountry, strStdRT);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources.fetchResValueInResList(
						selenium, strResName);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRECONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
           //Creating Interface
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
						selenium, strIntLable);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.fillCreatRgnInterfaceFlds(
						selenium, strInterfaceName, strDesc,
						strContactInfo, strResId, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Verifying Allowed USer
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface
						.verifyResrceDispUnderAllowedResourec(
								selenium, strRSValue[0],
								strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface
						.verifyResrceDispUnderAllowedResourec(
								selenium, strRSValue[1], strResName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.selectAndDeselectResInInterface(
						selenium, strResource, strRSValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.selectAndDeselectResInInterface(
						selenium, strResName, strRSValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInterface.savAndVerifyInterface(
						selenium, strInterfaceName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strActive = "Active";
				String strAction = "postHAvBEDStatus";
				String strType = "Post HAvBED 2.5.2 Information";
				String strSchema = "HHS_HAvBED 2.5.2";
				strFuncResult = objInterface.VerifyInterfaceOtherFields(
						selenium, strInterfaceName, strActive,
						strAction, strType, strSchema, strDesc);
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

		}

		catch (Exception e) {
			gstrTCID = "FTS-67105";
			gstrTO = "Verify that all the resources in the region are displayed under 'Allowed Resources' section in the 'Create Interface' screen of 'Post Havbed 2.5.2 Information' interface.";
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
	
	//end//testFTS67105

}
