package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
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
� Requirement		:Edit region
' Date			    :24/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

public class FTSEditRegion {
	
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditRegion");
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
		
	/***************************************************************
	'Description		:Verify that 'Interface Key' can be provided while editing the region.
	'Precondition		:Region RG1 is created without providing the 'Interface key'. 
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/26/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/
	
	@Test
	public void testFTS66781() throws Exception {
	try{
		
		Login objLogin = new Login();// object of class Login
		Regions objRegions=new Regions();
		gstrTCID = "66781";	//Test Case Id	
		gstrTO = "Verify that 'Interface Key' can be provided while editing the region.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = "Z_Region"+strTimeText;
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
			String strInterfaceKey="I"+strTimeText;
		/*
		* Precondition:
		  Action:Region RG1 is created without providing the 'Interface key'. 
		  Expected Result:No Expected Result
		*/

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Login as RegAdmin to region RG1, navigate to Setup >> Regions.
			  Expected Result:'Region List' screen is displayed
			*/
			//395265

			try {
				assertEquals("", strFuncResult);
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
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			* STEP :
			  Action:Provide valid data in 'Interface key' field and save.
			  Expected Result:Region List' screen is displayed
			*/
			//395267

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
			  Action:Click on 'Edit' link next to region RG1.
			  Expected Result:Data provided for 'Interface key' is displayed on the 'Edit Region' screen and the field is disabled
			*/
			//395268

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForInterfaceKey(selenium,
						strInterfaceKey, true);
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
			gstrTCID = "66781";
			gstrTO = "Verify that 'Interface Key' can be provided while editing the region.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}
	
	/***************************************************************
	'Description		:Verify that 'Interface Key' can be provided while editing the region.
	'Precondition		:Region RG1 is created providing data in the 'Interface key' field. 
	'Arguments		    :None
	'Returns		    :None
	'Date			    :6/26/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66847() throws Exception {
		try {

			Login objLogin = new Login();// object of class Login
			Regions objRegions = new Regions();
			gstrTCID = "66847"; // Test Case Id
			gstrTO = "Create a region by providing an interface key, edit the region details by Regional Admin "
					+ "and save and verify that the interface key is retained when the region is opened for editing.";// Test
																														// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strRegionName1 = "Z_editRegion" + strTimeText;
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
			String strInterfaceKey = "I" + strTimeText;
		/*
		* Precondition:
		  Action:Region RG1 is created providing data in the 'Interface key' field. 
		  Expected Result:No Expected Result
		*/
	
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			* STEP :
			  Action:Login as RegAdmin, navigate to Setup >> regions.
			  Expected Result:'Region List' screen is displayed
			*/
			//395440

			try {
				assertEquals("", strFuncResult);
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
						strRegionName, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
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
			  Action:Click on 'Edit' link next to region RG1.
			  Expected Result:'Edit Region' screen is displayed
		      Data provided for the 'Interface Key' field is retained and is disabled.
			*/
			//395442
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForInterfaceKey(selenium,
						strInterfaceKey, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			* STEP :
			  Action:Edit the data in all the fields and save.
			  Expected Result:'Region List' screen is displayed
			*/
			//395446
			try {
				assertEquals("", strFuncResult);
				strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
				strContFN = "FN";
				strContLN = "LN";
				strOrg = "Editqsg";
				strAddr = "editqsgsoft";
				strContactPhone1 = "3456-678-565";
				strContactPhone2 = "2342-456-546";
				strContactFax = "676-575-5675";
				strContactEMail = "qsg@qsgsoft.com";
				strEmailFrequency = "Never";
				strAlertFrequency = "15";
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName1, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			* STEP :
			  Action:Click on 'Edit' link next to region RG1.
			  Expected Result:Changes made to the fields are retained.
			*/
			//395450

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
				strContFN = "FN";
				strContLN = "LN";
				strOrg = "Editqsg";
				strAddr = "editqsgsoft";
				strContactPhone1 = "3456-678-565";
				strContactPhone2 = "2342-456-546";
				strContactFax = "676-575-5675";
				strContactEMail = "qsg@qsgsoft.com";
				strEmailFrequency = "Never";
				strAlertFrequency = "15";
				strFuncResult = objRegions.verifyAllRegnFields(selenium,
						strRegionName1, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);

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
			gstrTCID = "66847";
			gstrTO = "Create a region by providing an interface key, edit the region details by Regional Admin"
					+ " and save and verify that the interface key is retained when the region is opened for editing.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description    :Create a region by providing an interface key, edit the
	                 region details by a user other than Regional Admin and verify 
	                 that the interface key is retained for RegAdmin when the region is opened for editing.
	'Arguments		:None
	'Returns		:None
	'Date			:6/26/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66843() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "66843"; // Test Case Id
			gstrTO = " Create a region by providing an interface key, edit the region details by a user other than Regional Admin and verify that the interface key is retained for RegAdmin when the region is opened for editing.";// Test
																																																										// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
			String strInterfaceKey = "I" + strTimeText;

			// USER
			String strUserName = "RegionUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

		/*
		* STEP :
		  Action:Precondition: 
	      1. Region RG1 is created providing data in the 'Interface key' field.
	      2. User U1 is created with right 'May update region setup information'
		  Expected Result:No Expected Result
		*/
		//395371

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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
		  Action:Login as user U1, navigate to Setup >> regions.
		  Expected Result:'Region List' screen is displayed
		*/
		//395384
			try {
				assertEquals("", strFuncResult);
				strLoginUserName = strUserName;
				strLoginPassword = strInitPwd;
				strFuncResult = objLogin.newUsrLogin(selenium,
						strLoginUserName, strLoginPassword);
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
		* STEP :
		  Action:Click on 'Edit' link next to region RG1.
		  Expected Result:'Edit Region' screen is displayed
	     'Interface Key' and 'Domain' fields are not displayed.
		*/
		//395385
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForDomainAndIntField(selenium,
						true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Click on save.
		  Expected Result:'Region List' screen is displayed
		*/
		//395386
		
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
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions.
		  Expected Result:'Region List' screen is displayed
		*/
		//395387

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
				strRegn = rdExcel.readData("Login", 3, 4);
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
		* STEP :
		  Action:Click on 'Edit' link next to region RG1.
		  Expected Result:'Edit Region' screen is displayed
	      Data provided for the field 'Interface key' while creating the region is retained and the field is disabled.
		*/
		//395388

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForInterfaceKey(selenium,
						strInterfaceKey, true);
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
			gstrTCID = "66843";
			gstrTO = "Create a region by providing an interface key, edit the region details by a" +
					" user other than Regional Admin and verify that the interface key is retained " +
					"for RegAdmin when the region is opened for editing.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/********************************************************************************
	'Description	:Edit a region and select the option Instant Messaging option and 
	verify that the option Instant Messaging is available for the Users in the region.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/27/2012
	'Author			:QSG
	'--------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	*********************************************************************************/

	@Test
	public void testFTS66853() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			gstrTCID = "66853"; // Test Case Id
			gstrTO = " Edit a region and select the option Instant Messaging option and verify" +
					" that the option Instant Messaging is available for the Users in the region.";//TO																	// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";	

		/*
		* STEP :
		  Action:Preconditions:
	      1. Region RG1 is created.
		  Expected Result:No Expected Result
		*/
		//395596
		
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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395597

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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:'Edit Region' screen is displayed.
		*/
		//395599
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select check box 'Instant Messaging' check box and save
		  Expected Result:'Region List' screen is displayed
		*/
		//395600
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.instancemsgKeyForRegion(selenium,
						true);
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
		  Action:Logout and Login as RegAdmin to region RG1.
		  Expected Result:'Region Default' screen is displayed.
	      'IM' tab is available in the Menu header.
		*/
		//395608
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

		/*
		* STEP :
		  Action:Click on 'IM'.
		  Expected Result:'Instant Messaging' page is displayed.
		*/
		//395612

			try {
				assertEquals("", strFuncResult);
				strRegn = strRegionName;
				strFuncResult = objRegions.navToInstantMsgPage(selenium);
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
			gstrTCID = "66853";
			gstrTO = "Edit a region and select the option Instant Messaging option and verify that " +
					"the option Instant Messaging is available for the Users in the region.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description	:Edit a region and deselect the option Instant Messaging option and 
	verify that the option Instant Messaging is not available for the Users in the region.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/27/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66867() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			gstrTCID = "66867"; // Test Case Id
			gstrTO = " Edit a region and deselect the option Instant Messaging option and verify " +
					"that the option Instant Messaging is not available for the Users in the region.";//TO																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";

		/*
		* STEP :
		  Action:Preconditions:
	     1. Region RG1 is created selecting the option 'Instant Messaging'.
		  Expected Result:No Expected Result
		*/
		//395628
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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.instancemsgKeyForRegion(selenium,
						true);
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
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395629
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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:'Edit Region' screen is displayed.
		*/
		//395631
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Deselect check box 'Instant Messaging' check box and save
		  Expected Result:'Region List' screen is displayed.
          'IM' tab is not displayed on the Menu header.
		*/
		//395632

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.instancemsgKeyForRegion(selenium,
						false);
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
				strFuncResult = objRegions.checkForIMLinkFOrRegion(selenium,
						true);
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
			gstrTCID = "66867";
			gstrTO = "Edit a region and deselect the option Instant Messaging option and verify that" +
					" the option Instant Messaging is not available for the Users in the region.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description	:Edit all the fields and cancel the process and verify that the  
	                 changes made are not retained when the region is opened for editing.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/27/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66857() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			gstrTCID = "66857"; // Test Case Id
			gstrTO = " Edit all the fields and cancel the process and verify that the changes made "
					+ "are not retained when the region is opened for editing.";// Test
																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strRegionName1 = "Z_editRegion" + strTimeText;
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
		  Action:Preconditions:
	      1. Region RG1 is created.
		  Expected Result:No Expected Result
		*/
		//395648
		
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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395649
			
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
				strRegn =strRegionName;
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
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:'Edit Region' screen is displayed.
		*/
		//395650
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Edit data in all the fields and click on 'Cancel'
		  Expected Result:Name of the region is not updated in the 'Region List' screen
		*/
		//395651
			try {
				assertEquals("", strFuncResult);
				strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
				strContFN = "FN";
				strContLN = "LN";
				strOrg = "Editqsg";
				strAddr = "editqsgsoft";
				strContactPhone1 = "3456-678-565";
				strContactPhone2 = "2342-456-546";
				strContactFax = "676-575-5675";
				strContactEMail = "qsg@qsgsoft.com";
				strEmailFrequency = "Never";
				strAlertFrequency = "15";
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						strRegionName1, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.checkForRegionNotCreation(selenium, strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:Changes made are not retained on the 'Edit Region' screen
		*/
		//395652

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
				strContFN = "FN";
				strContLN = "LN";
				strOrg = "Editqsg";
				strAddr = "editqsgsoft";
				strContactPhone1 = "3456-678-565";
				strContactPhone2 = "2342-456-546";
				strContactFax = "676-575-5675";
				strContactEMail = "qsg@qsgsoft.com";
				strEmailFrequency = "Never";
				strAlertFrequency = "15";
				strFuncResult = objRegions.verifyAllRegnFields(selenium,
						strRegionName1, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "66857";
			gstrTO = "Edit all the fields and cancel the process and verify that the changes made are "
					+ "not retained when the region is opened for editing.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description	:Edit a region and deselect the option IP filter option and verify that the option to enter IP range is not present while Creating/Editing a user.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/28/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66852() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "66852"; // Test Case Id
			gstrTO = " Edit a region and deselect the option IP filter option and verify that"
					+ " the option to enter IP range is not present while Creating/Editing a user.";// Test
																									// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			// USER
			String strUserName = "Rgnusr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// Region
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
			// Search user data
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
	
		/*
		* STEP :
		  Action:Preconditions:
	      1. Region RG1 is created selecting 'IP FILTER' option.
          2. User U1 is created in RG1.
		  Expected Result:No Expected Result
		*/
		//395569
		
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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.selectAndDeselectIPFILTER(selenium,
						true);
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
				selenium.click(propElementDetails
						.getProperty("CreateNewUsr.Save"));
				selenium.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
		/*
		* STEP :
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395570
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:'Edit Region' screen is displayed.
		*/
		//395572
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect check box 'IP FILTER' check box and save
		  Expected Result:'Region List' screen is displayed
		*/
		//395573

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.selectAndDeselectIPFILTER(selenium,
						false);
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'User List' screen is displayed
		*/
		//395574
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on 'Create New User' button.
		  Expected Result:'Create New User' screen is displayed.
		*/
		//395575
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Advanced Options section.
		  Expected Result:'IP range for Login' field is not displayed.
		*/
		//395579
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.CheckForIPRangeLogin(selenium, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Users List' screen is displayed.
		*/
		//395581
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to user U1.
		  Expected Result:'Edit User' screen is displayed.
		*/
		//395583
			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 3, 12,
						strFILE_PATH);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Advanced Options section.
		  Expected Result:'IP range for Login' field is not displayed.
		*/
		//395584

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.CheckForIPRangeLogin(selenium,
						false);
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
			gstrTCID = "66852";
			gstrTO = "Edit a region and deselect the option IP filter option and verify that the " +
					"option to enter IP range is not present while Creating/Editing a user.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description	:Edit a region and select the option IP filter option and verify that the 
	                 option to enter IP range is present while Creating/Editing a user.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/29/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66851() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			CreateUsers objCreateUsers = new CreateUsers();
			gstrTCID = "66851"; // Test Case Id
			gstrTO = " Edit a region and select the option IP filter option and verify that the "
					+ "option to enter IP range is present while Creating/Editing a user.";// Test
																							// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			// USER
			String strUserName = "Rgnusr1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// Region
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";
			// Search user data
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";
			String strFILE_PATH = pathProps.getProperty("TestData_path");
	
		/*
		* STEP :
		  Action:Preconditions:
	     1. Region RG1 is created.
         2. User U1 is created in RG1.
		  Expected Result:No Expected Result
		*/
		//395514

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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395515
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:'Edit Region' screen is displayed.
		*/
		//395517
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select check box 'IP FILTER' check box and save
		  Expected Result:'Region List' screen is displayed
		*/
		//395518
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.selectAndDeselectIPFILTER(selenium,
						true);
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'User List' screen is displayed
		*/
		//395520
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
		/*
		* STEP :
		  Action:Click on 'Create New User' button.
		  Expected Result:'Create New User' screen is displayed.
		*/
		//395562
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navToCreateUserPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Advanced Options section.
		  Expected Result:'IP range for Login' field is displayed.
		*/
		//395563
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.CheckForIPRangeLogin(selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Users List' screen is displayed.
		*/
		//395567
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.cancelAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to user U1.
		  Expected Result:'Edit User' screen is displayed.
		*/
		//395522

			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 3, 12,
						strFILE_PATH);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Advanced Options section.
		  Expected Result:'IP range for Login' field is displayed.
		*/
		//395521
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.CheckForIPRangeLogin(selenium,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Provide valid data in the 'IP range for Login' field and save.
		  Expected Result:'Users List' screen is displayed.
		*/
		//395532
			try {
				assertEquals("", strFuncResult);
				String strRangeOfIp="240.230.222.234";
				strFuncResult = objCreateUsers.validDataForIPRangeLogin(selenium, strRangeOfIp);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Users
		  Expected Result:'User List' screen is displayed
		*/
		//395560		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
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
			gstrTCID = "66851";
			gstrTO = "Edit a region and select the option IP filter option and verify that the option to"
					+ " enter IP range is present while Creating/Editing a user.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description	:Edit a region and select the option to trace name for status change and
	 verify that the option to trace name is present while creating a status type in this region.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:6/29/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66849() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			StatusTypes objStatusTypes = new StatusTypes();
			gstrTCID = "66849"; // Test Case Id
			gstrTO = " Edit a region and select the option to trace name for status change and verify that the option to trace name is present while creating a status type in this region.";// Test
																																																// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			// Region
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";

		/*
		* STEP :
		  Action:Precondition: Region RG1 is created.
		  Expected Result:No Expected Result
		*/
		//395455

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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395457

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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1.
		  Expected Result:Edit Region' screen is displayed.
		*/
		//395458

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Select check box 'STATUS CHANGE NAME TRACE' and save
		  Expected Result:'Region List' screen is displayed
		*/
		//395460
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.selectAndDeselectTraceName(selenium,
						true);
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
		  Action:Navigate to Setup >> Status types.
		  Expected Result:Status Type List' screen is displayed
		*/
		//395462
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select number in the 'Select Type' drop down and click on 'Next'
		  Expected Result:Create Number Status Type' screen is displayed.
	     'Trace User' check box is displayed.
		*/
		//395464
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395465
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.cancelAndNavToSTListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select multi in the 'Select Type' drop down and click on 'Next'
		  Expected Result:'Create Multi Status Type' screen is displayed.
	      'Trace User' check box is displayed.'
		*/
		//395467
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395468
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.cancelAndNavToSTListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select Text in the 'Select Type' drop down and click on 'Next'
		  Expected Result:"Create Text Status Type' screen is displayed.
	     Trace User' check box is displayed."
		*/
		//395469
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395471
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.cancelAndNavToSTListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select Saturation Score in the 'Select Type' drop down and click on 'Next'
		  Expected Result:Create Saturation Score Status Type' screen is displayed.
	      'Trace User' check box is displayed.
		*/
		//395472

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395473

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.cancelAndNavToSTListPage(selenium);
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
			gstrTCID = "66849";
			gstrTO = "Edit a region and select the option to trace name for status change and" +
					" verify that the option to trace name is present while creating a status type" +
					" in this region.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description		:Edit a region and deselect the option  to trace name for status change and verify that the option to trace name is not present while creating a status type in this region.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:6/29/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS66850() throws Exception {
		try {

			Login objLogin = new Login();
			Regions objRegions = new Regions();
			StatusTypes objStatusTypes = new StatusTypes();
			gstrTCID = "66850"; // Test Case Id
			gstrTO = " Edit a region and deselect the option  to trace name for status change and"
					+ " verify that the option to trace name is not present while creating a status"
					+ " type in this region.";// TO // Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = "";
			// Region
			String strRegionName = "Z_AutoTest" + strTimeText;
			String strTimeZone = "(GMT-05:00) Eastern Time (US, Canada)";
			String strContFN = "FN";
			String strContLN = "LN";
			String strEmailFrequency = "Never";
			String strAlertFrequency = "15";

		/*
		* STEP :
		  Action:Precondition: Region RG1 is created.
		  Expected Result:No Expected Result
		*/
		//395475

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
				strRegn = rdExcel.readData("Login", 3, 4);
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
				strFuncResult = objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.selectAndDeselectTraceName(selenium,
						true);
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
		  Action:Login as RegAdmin to RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		//395476
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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1.
		  Expected Result:Edit Region' screen is displayed.
		*/
		//395477
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Deselect check box 'STATUS CHANGE NAME TRACE' and save.
		  Expected Result:'Region List' screen is displayed
		*/
		//395499
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.selectAndDeselectTraceName(selenium,
						false);
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
		  Action:Navigate to Setup >> Status types.
		  Expected Result:Status Type List' screen is displayed
		*/
		//395500
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select number in the 'Select Type' drop down and click on 'Next'
		  Expected Result:Create Number Status Type' screen is displayed.
	      'Trace User' check box is not displayed.
		*/
		//395502
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395503
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.cancelAndNavToSTListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select multi in the 'Select Type' drop down and click on 'Next'
		  Expected Result:'Create Multi Status Type' screen is displayed.
	     'Trace User' check box is not displayed.'
		*/
		//395504
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Multi";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395505
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.cancelAndNavToSTListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select Text in the 'Select Type' drop down and click on 'Next'
		  Expected Result:'Create Text Status Type' screen is displayed.
	      'Trace User' check box is not displayed.
		*/
		//395508
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Text";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395509
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.cancelAndNavToSTListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Create New Status Type', select Saturation Score in the 'Select Type' drop down and click on 'Next'
		  Expected Result:Create Saturation Score Status Type' screen is displayed.
	      'Trace User' check box is not displayed.
		*/
		//395510
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Saturation Score";
				strFuncResult = objStatusTypes.selectSTAndChkForTraceName(
						selenium, strStatusTypeValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Cancel' button.
		  Expected Result:'Status Type List' screen is displayed.
		*/
		//395512
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.cancelAndNavToSTListPage(selenium);
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
			gstrTCID = "66850";
			gstrTO = "Edit a region and deselect the option  to trace name for status change and verify that the option to trace name is not present while creating a status type in this region.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
	}

	/***************************************************************
	'Description	:Verify that a region can be edited by editing all the fields.
	'Precondition	:
	'Arguments		:None
	'Returns		:None
	'Date			:11/6/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS98732() throws Exception {
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		String strFuncResult = "";
		try {
			gstrTCID = "98732"; // Test Case Id
			gstrTO = " Verify that a region can be edited by editing all the fields.";// TO
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
			String strRegionName = "Z_AutoTest" + strTimeText;
			String EditRegionName = "Z_EditRegion" + strTimeText;
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
		  Action:Precondition:
	      Region RG1 is created
		  Expected Result:No Expected Result
		*/
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.navCreateNewRegionPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.fillAllRegnFields(selenium, strRegionName, strTimeZone,
						strContFN, strContLN, strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency, strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult =objRegions.saveAndVerifyRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
		/*
		* STEP :
		  Action:Login as RegAdmin to region RG1, navigate to Setup >> Regions
		  Expected Result:'Region List' screen is displayed
		*/
		
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on 'Edit' link next to region RG1
		  Expected Result:'Edit Region' screen is displayed
		*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.editRegion(selenium, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Edit data in all the fields and save.
		  Expected Result:Name of the region is updated in the 'Region List' screen.
	      Edited region name is NOT displayed at the top left corner of the screen.
		*/

			try {
				assertEquals("", strFuncResult);
				strContFN = "FirstN";
				strContLN = "LastN";
				strContactPhone1 = "9999-111-222";
				strContactPhone2 = "2352-444-555";
				strContactFax = "666-555-5775";
				strContactEMail = "qsgtech@qsgsoft.com";
				strFuncResult = objRegions.fillAllRegnFields(selenium,
						EditRegionName, strTimeZone, strContFN, strContLN,
						strOrg, strAddr, strContactPhone1, strContactPhone2,
						strContactFax, strContactEMail, strEmailFrequency,
						strAlertFrequency);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.saveAndVerifyRegion(selenium,
						EditRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.verifyRegionName(selenium,
						EditRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on link 'Contact Us'.
		  Expected Result:'Contact Us' thick box is displayed.
          Contact name, Phone numbers, Fax number and email ID values edited for region RG1 are displayed.
		*/
			try {
				assertEquals("The Region name" + strRegionName
						+ "is NOT  displayed at the top left corner",
						strFuncResult);
				strContFN = "FirstN";
				strContLN = "LastN";
				strContactPhone1 = "9999-111-222";
				strContactPhone2 = "2352-444-555";
				strContactFax = "666-555-5775";
				strContactEMail = "qsgtech@qsgsoft.com";
				strFuncResult = objRegions.navToContactUsFrame(selenium,
						EditRegionName, strContFN, strContLN, strContactPhone1,
						strContactPhone2, strContactFax, strContactEMail);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Logout and login as RegAdmin to region RG1 (edited).
		  Expected Result:Edited region name is not updated at the top left corner of the application.
		*/
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
				blnLogin = true;
				strRegn = EditRegionName;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.verifyRegionName(selenium,
						EditRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Login as RegAdmin on Mobile device.
		  Expected Result:'Main Menu' screen is displayed.
		*/
			try {
				assertEquals("The Region name" + EditRegionName
						+ "is NOT  displayed at the top left corner",
						strFuncResult);
				strFuncResult = "";
				gstrResult = "PASS";
				// Write result data
				String strTestData[] = new String[10];
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strRegionName;
				strTestData[3] = strContFN;
				strTestData[3] = strContLN;
				strTestData[4] = strContactPhone1;
				strTestData[5] = strContactPhone2;
				strTestData[6] = strContactFax;
				strTestData[7] = strContactEMail;
				strTestData[9] = "Verify from 7th step";
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
			gstrTCID = "98732";
			gstrTO = "Verify that a region can be edited by editing all the fields.";
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
