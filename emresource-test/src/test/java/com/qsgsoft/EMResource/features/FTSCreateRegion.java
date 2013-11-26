package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description		:This class contains test cases from   requirement
' Requirement Group	:Setting up Regions   
� Requirement		    :Create Region
' Date			    :24/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class FTSCreateRegion {
	
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSCreateRegion");
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
	Selenium selenium;
	String gstrTimeOut;



/****************************************************************************************************************
	* This function is called the setup() function which is executed before every test.
	*
	* The function will take care of creating a new selenium session for every test
	*
****************************************************************************************************************/

	@Before
	public void setUp() throws Exception {

		dtStartDate = new Date();
		gstrBrowserName = "IE 8";
		gstrBuild = "";

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

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


/****************************************************************************************************************	*
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and writing the execution
	* result of the test. 
	*
****************************************************************************************************************/

	@After
	public void tearDown() throws Exception {
		
		try {
			selenium.close();
		} catch (Exception e) {

		}
		selenium.stop();

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

		gdbTimeTaken = objOFC.TimeTaken(dtStartDate);// and execution time
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		Date_Time_settings dts = new Date_Time_settings();
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");

		// gstrBuild=PropEnvDetails.getProperty("Build");
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");
		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
/***************************************************************************************************
'Description	:Cancel the process of creating the region and verify that the region is not created.
'Arguments		:None
'Returns		:None
'Date			:6/25/2012
'Author			:QSG
'---------------------------------------------------------------------------------------------------
'Modified Date				                                                             Modified By
'Date					                                                                 Name
*****************************************************************************************************/
	@Test
	public void testFTS2112() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		try {
		
			gstrTCID = "2112"; // Test Case Id
			gstrTO = "Cancel the process of creating the region and verify that the region is not created.";// Test
																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = "Z_Autoqsg"+strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strOrg = "qsg";
			String strAddr = "qsgsoft";
			String strContactPhone1 = "3456-678-565";
			String strContactPhone2 = "2342-456-546";
			String strContactFax = "676-575-5675";
			String strContactEMail = "autoemr@qsgsoft.com";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
		/*
		* STEP :
		  Action:Login as RegAdmin
		  Expected Result:No Expected Result
		*/
		//4212	
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup>>Regions
		  Expected Result:No Expected Result
		*/
		//4213		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on ''Create New Region''
		  Expected Result:No Expected Result
		*/
		//4214		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Enter data in all fields and click on ''Cancel''
		  Expected Result:User is taken to ''Region List'' screen and the region is not created
		*/
		//4215
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForRegionNotCreation(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "2112";
			gstrTO = "Cancel the process of creating the region and verify that the region is not created.";
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
/***********************************************************************************************
'Description	:Verify that Interface key is not visible to any user other than Regional Admin.
'Arguments		:None
'Returns		:None
'Date			:6/25/2012
'Author			:QSG
'------------------------------------------------------------------------------------------------
'Modified Date				                                                       Modified By
'Date				                                                              	Name
*************************************************************************************************/

	@Test
	public void testFTS2109() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Regions objRegions = new Regions();
		CreateUsers objCreateUsers = new CreateUsers();
		try {
			
			gstrTCID = "2109"; // Test Case Id
			gstrTO = " Verify that Interface key is not visible to any user other than Regional Admin.";// Test
																										// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			String strRegionName = "Z_QsgReg" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
			String strInterfaceKey ="15";

			// USER
			String strUserName = "RegionUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
		/*
		* STEP :
		  Action:Login as RegAdmin
		  Expected Result:No Expected Result
		*/
		//4184

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);
				strRegn = rdExcel.readData("Login", 3, 4);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Setup>>Regions
		  Expected Result:No Expected Result
		*/
		//4185
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Click on ''Create New Region''
		  Expected Result:No Expected Result
		*/
		//4186
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Enter mandatory data and provide an interface key and save
		  Expected Result:Region R1 is listed in region list screen
		*/
		//4187
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN, "",
						"", "", "", "", "", strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.interfaceKeyForRegion(selenium,
						strInterfaceKey);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on ''Edit'' associated with R1
		  Expected Result:Interface key provided is retained and the field is disabled
		*/
		//4188

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertEquals(
						strInterfaceKey,
						(selenium.getValue(propElementDetails
								.getProperty("Regions.CreateNewRegn.InterfaceKey"))));
				assertFalse(selenium.isEditable(propElementDetails
						.getProperty("Regions.CreateNewRegn.InterfaceKey")));
				log4j.info("Interface key provided is retained and the field is disabled");
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
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegn = strRegionName;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
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
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("createUser.advancedoption.MayUpdateRegion"),
								true);
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

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
		* STEP :
		  Action:Login as any other user in R1 with the right to update region setup information
		  Expected Result:No Expected Result
		*/
		//4189
	
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = strUserName;
				strLoginPassword = strInitPwd;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup>>Regions and click on ''Edit'' next to R1
		  Expected Result:Interface key field is not available
		*/
		//4190
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForDomainAndIntField(selenium,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "2109";
			gstrTO = "Verify that Interface key is not visible to any user other than Regional Admin.";
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
/**********************************************************************************
'Description	:Verify that a region can be created by entering data in all fields.
'Arguments		:None
'Returns		:None
'Date			:6/25/2012
'Author			:QSG
'----------------------------------------------------------------------------------
'Modified Date				                                      Modified By
'Date					                                          Name
***********************************************************************************/

	@Test
	public void testFTS2108() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin=new Login();
		Regions objRegions=new Regions();
	try{	

			gstrTCID = "2108"; // Test Case Id
			gstrTO = " Verify that a region can be created by entering data in all fields.";// Test
																							// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = "Z_Autoqsg" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strOrg = "qsg";
			String strAddr = "qsgsoft";
			String strContactPhone1 = "3456-678-565";
			String strContactPhone2 = "2342-456-546";
			String strContactFax = "676-575-5675";
			String strContactEMail = "autoemr@qsgsoft.com";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";

		/*
		* STEP :
		  Action:Login as RegAdmin
		  Expected Result:No Expected Result
		*/
		//4176
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strRegn = rdExcel.readData("Login", 3, 4);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup>>Regions
		  Expected Result:No Expected Result
		*/
		//4177
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on ''Create New Region''
		  Expected Result:No Expected Result
		*/
		//4178	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Enter data in all fields and save
		  Expected Result:Region R1 is listed in region list screen
		*/
		//4179	
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						strRegionName);
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
		* STEP :
		  Action:Login to region R1
		  Expected Result:The Region name is displayed at the top right corner
		*/
		//4180

			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegn = strRegionName;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.verifyRegionName(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on ''Contact Us''
		  Expected Result:Contact name, Phone numbers, Fax number and email ID provided for region R1 are displayed.
		*/
		//4181

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToContactUsFrame(selenium,
						strRegionName, strContFN, strContLN, strContactPhone1,
						strContactPhone2, strContactFax, strContactEMail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
				// Write result data
				String strTestData[] = new String[10];
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strRegionName;
				strTestData[3] = strContFN;
				strTestData[4] = strContFN;
				strTestData[5] = strContactPhone1;
				strTestData[6] = strContactPhone2;
				strTestData[7] = strContactFax;
				strTestData[8] = strContactEMail;
				strTestData[9] = "Verify form 7th step ";
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Region");
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}
		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "2108";
			gstrTO = "Verify that a region can be created by entering data in all fields.";
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
/***********************************************************************************
'Description    :Verify that a region can be created by entering only mandatory data.
'Arguments		:None
'Returns		:None
'Date			:6/25/2012
'Author			:QSG
'------------------------------------------------------------------------------------
'Modified Date				                                           Modified By
'Date					                                               Name
*************************************************************************************/

	@Test
	public void testFTS2107() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Regions objRegions = new Regions();
		try {

			gstrTCID = "2107"; // Test Case Id
			gstrTO = " Verify that a region can be created by entering only mandatory data.";// Test
																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = "Z_Autoqsg" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
		/*
		* STEP :
		  Action:Login as RegAdmin
		  Expected Result:No Expected Result
		*/
		//4168
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);
			strFuncResult = objLogin.login(selenium, strLoginUserName,
					strLoginPassword);
			try {
				assertEquals("", strFuncResult);
				strRegn = rdExcel.readData("Login", 3, 4);
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup>>Regions
		  Expected Result:''Region List'' screen is displayed
		*/
		//4169
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on ''Create New Region''
		  Expected Result:''Create New Region'' screen is displayed
		*/
		//4170
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Enter mandatory data and save
		  Expected Result:Region R1 is listed in region list screen
		*/
		//4171
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName, strTimeZone, strContFN, strContLN, "",
						"", "", "", "", "", strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						strRegionName);
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
		* STEP :
		  Action:Login to region R1
		  Expected Result:The Region name is displayed at the top right corner
		*/
		//4172		
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegn = strRegionName;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.verifyRegionName(selenium,
						strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on ''Contact Us''
		  Expected Result:Contact name provided for R1 is displayed
		*/
		//4173
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navToContactUsFrame(selenium,
						strRegionName, strContFN, strContLN, "", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", gstrReason);
				gstrResult = "PASS";
				// Write result data
				String strTestData[] = new String[10];
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strRegionName;
				strTestData[3] = strContFN;
				strTestData[4] = strContFN;
				strTestData[9] = "Verify form 7th step ";
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Region");
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "2107";
			gstrTO = "Verify that a region can be created by entering only mandatory data.";
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

