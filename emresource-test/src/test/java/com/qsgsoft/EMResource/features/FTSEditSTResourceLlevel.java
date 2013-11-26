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

/**********************************************************************
' Description         :This class includes test cases related to
' Requirement Group   :Setting up Resources 
' Requirement         :Edit status types at resource level
' Date		          :30-May-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class FTSEditSTResourceLlevel {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSEditSTResourceLlevel");
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
	Selenium selenium, seleniumFirefox, seleniumPrecondition;

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
		seleniumFirefox = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();

		seleniumFirefox.stop();

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

	/********************************************************************************
	'Description	:Verify that a user with only �Setup Resources� right cannot edit 
	'				 the status types at the resource level.
	'					Preconditions:
						1. Resource type RT is associated with status types ST1 and ST2
	'					2. Resource RS is created under resource type RT 
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testFTS36710() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT=new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		General objGeneral=new General();
		
		try {
			gstrTCID = "36710 ";
			gstrTO = "Verify that a user with only �Setup Resources� right "
					+ "cannot edit the status types at the resource level.";
			gstrResult = "FAIL";
			gstrReason = "";

			String strFILE_PATH = pathProps.getProperty("TestData_path");		
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3,4);
			
			String statTypeName_1="AutoNST_1" + strTimeText;
			String statTypeName_2="AutoNST_2" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[]=new String[2];
			strSTvalue[0]="";
			strSTvalue[1]="";
			
			String strResrctTypName="AutoRT" + strTimeText;		
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[]=new String[1];
			strRSValue[0]="";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserName_1 = "AutoUsr" +System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserName_1;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
		/*
		 * STEP 2: Login as user U1 and navigate to Setup>>Resources screen, click
		 * on 'Edit Status Types' link corresponding to resource RS1<-> 'Edit
		 * Resource-Level Status Types' screen is displayed.
		 * 
		 * 
		 */
			
		log4j.info("~~~~~PRE-CONDITION "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
				
		/*
		 * Precondition:
		 * 
		 * 1. Resource type RT is associated with status types ST1 and ST2
		 * 2. Resource RS is created under resource type RT
		 */
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;
				
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
			//ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName_1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName_1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName_2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName_2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

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
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				selenium.click("css=input[name='statusTypeID'][value='"
						+ strSTvalue[1] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
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
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITION "+gstrTCID+"  EXECUTION ENDS~~~~~");
					
			log4j.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
				
		 /* STEP 2: Login as RegAdmin to EMResource and create user U1 selecting
		 * only the right 'Setup Resources' <->No Expected Result*/
			 

			try {
				assertEquals("", strFuncResult);		
				strFuncResult=objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(selenium, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulNameA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 *STEP 3: Logout and login as user U1, and navigate to Setup>>Resources
		 * screen<-> Resource list screen is displayed with the list of all
		 * resources in the region. 'Create New Resource' button is
		 * available. 'Edit Status Types' link is NOT available for any of
		 * the resources listed . 'Edit' link is available corresponding to
		 * each resources. 'Users' link is NOT available corresponding to
		 * each resources.
		 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult=objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Resource list screen is displayed with the "
						+ "list of all resources in the region. ");
				String strElementID = propElementDetails
						.getProperty("CreateRes.CreateNewResource");			
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "Resource list screen is NOT displayed with the "
						+ "list of all resources in the region. ";
				log4j.info("Resource list screen is NOT displayed with "
						+ "the list of all resources in the region. ");
			}
			
			/* 'Create New Resource' button is available. */
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("'Create New Resource' button is available. ");

				String strElementID = "//div[@id='mainContainer']/table[2]/tbody/tr/td[5][text()='"
							+ strResource
							+ "']"
							+ "/parent::tr/td[1]/a[text()='Status Types']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Create New Resource' button is NOT available. ";
				log4j.info("'Create New Resource' button is NOT available. ");
			}
			
			/*'Edit Status Types' link is NOT available for any of the resources listed . */
			
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j.info("'Edit Status Types' link is NOT "
						+ "available for any of the resources listed .");

				String strElementID = "//div[@id='mainContainer']/table[2]/tbody/"
						+ "tr/td[5][text()='"
						+ strResource
						+ "']/parent::tr/td[1]/a[text()='Edit']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = "'Edit Status Types' link is available"
						+ " for any of the resources listed .";
				log4j.info("'Edit Status Types' link is available"
						+ " for any of the resources listed .");
			}

			/*'Edit' link is available corresponding to each resources. */
			
			try {
				assertEquals("", strFuncResult);
				log4j
						.info("'Edit' link is available corresponding to each resources.");

				String strElementID = "//div[@id='mainContainer']/table[2]/tbody/"
						+ "tr/td[5][text()='"
						+ strResource
						+ "']/parent::tr/td[1]/a[text()='Users']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = "'Edit' link is NOT available corresponding to each resources.";
				log4j
						.info("'Edit' link is NOT available corresponding to each resources.");
			}
			
			/*'Users' link is NOT available corresponding to each resources.*/
			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("'Users' link is NOT available corresponding to each resources.");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = "'Users' link is  available corresponding to each resources.";
				log4j
						.info("'Users' link is available corresponding to each resources.");
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
			.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "36710";
			gstrTO = "Verify that a user with only �Setup Resources� right cannot"
					+ " edit the status types at the resource level.";
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
	'Description	:Verify that number status types associated with same standard status type can be associated with a single resource.
	'					Preconditions:
						1. Resource type RT is associated with only status type ST.
	'				     2.Resources RS1 is created under RT. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-June-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	

	@Test
	public void testFTS38411() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objST=new StatusTypes();
		ResourceTypes objRT=new ResourceTypes();
		Resources objRs = new Resources();
		try {
			gstrTCID = "38411 ";
			gstrTO = "Verify that number status types associated with same standard"
					+ " status type can be associated with a single resource.";
			gstrResult = "FAIL";
			gstrReason = "";


			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");	
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login", 3, 4);
			
			String statTypeName_1="AutoNST_1" + strTimeText;
			String statTypeName_2="AutoNST_2" + strTimeText;
			String statTypeName_3="AutoNST_3" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Auto";
			String strStatValue = "";
			String strSTvalue[]=new String[3];
			strSTvalue[0]="";
			strSTvalue[1]="";
			strSTvalue[2]="";
			String strStandardST="";		
			String strResrctTypName="AutoRT" + strTimeText;		
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";
			String strRSValue[]=new String[1];
			strRSValue[0]="";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
					
		/*
		 * STEP 2: Login as user U1 and navigate to Setup>>Resources screen, click
		 * on 'Edit Status Types' link corresponding to resource RS1<-> 'Edit
		 * Resource-Level Status Types' screen is displayed.
		 * 
		 * 
		 */
			
			log4j
			.info("~~~~~PRE-CONDITION "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			
			
			/*
			 * Precondition:
			 * 
			 * 1. Resource type RT is associated with status types ST1 and ST2
			 * 2. Resource RS is created under resource type RT
			 */
			
			try{
				assertEquals("",strFuncResult);
				blnLogin=true;		
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			}catch(AssertionError Ae){
				gstrReason=strFuncResult;
			}
			
			
			//ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName_1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStandardST = "Bed Availability: Adult ICU";
				strFuncResult = objST.selectStandardSTInCreateST(selenium,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyST(selenium, statTypeName_1,
						strStatusTypeValue, strStandardST, strStatTypDefn, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName_1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
				//1. Resource type RT
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);		
				strFuncResult = objRT.resrcTypeMandatoryFlds(selenium,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(selenium,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*2. Resources RS is created under RT. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(selenium,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(selenium, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as RegAdmin and Navigate to >> Setup >> Status Types
			 * 'Status Type List' page is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.login(selenium, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult=objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Click on 'Create New Status Type' button 'Select Status Type'
			 * page is displayed
			 */
			/*
			 * 4 Select 'Number' in 'Select Type' drop down list, and click on
			 * 'next' button 'Create Number Status Type' page is displayed.
			 */
			/*
			 * 5 Fill the mandatory data, name the status type as ST1 and select
			 * the standard status type say 'X', and click on 'Save' button
			 * 'Status Type List' page is displayed and ST1 is displayed in the
			 * list
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName_2, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStandardST = "Bed Availability: Adult ICU";
				strFuncResult = objST.selectStandardSTInCreateST(selenium,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyST(selenium, statTypeName_2,
						strStatusTypeValue, strStandardST, strStatTypDefn, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName_2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Navigate to >> Setup >> Status Types 'Status Type List' page is
			 * displayed.
			 */
			/*
			 * 7 Click on 'Create New Status Type' button 'Select Status Type'
			 * page is displayed
			 */
			/*
			 * 8 Select 'Number' in 'Select Type' drop down list, and click on
			 * 'next' button 'Create Number Status Type' page is displayed.
			 */
			/*
			 * 9 Fill the mandatory data, name the status type as ST2 and select
			 * the standard status type say 'X', and click on 'Save' button
			 * 'Status Type List' page is displayed and ST2 is displayed in the
			 * list
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(selenium,
						strStatusTypeValue, statTypeName_3, strStatTypDefn,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStandardST = "Bed Availability: Adult ICU";
				strFuncResult = objST.selectStandardSTInCreateST(selenium,
						strStandardST);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.savAndVerifyST(selenium, statTypeName_3,
						strStatusTypeValue, strStandardST, strStatTypDefn, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objST.fetchSTValueInStatTypeList(selenium,
						statTypeName_3);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 10 Navigate to >> Setup >> Resources 'Resource List' is displayed */
			/*
			 * 11 Click on 'Edit Status Types' link associated with 'RS' 'Edit
			 * Resource-Level Status Types' screen is displayed
			 */
			/*
			 * 12 Select ST1 and ST2 from 'Additional Status Types' list and
			 * click on 'Save' button 'Resource List' screen is displayed
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						selenium, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strHavBed = "No";
				strFuncResult = objRs.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrctTypName);
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
			gstrTCID = "38411";
			gstrTO = "Verify that number status types associated with same standard" +
					" status type can be associated with a single resource.";
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
	/****************************************************************************************************
	'Description   :Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
                    1.ST1 is associated with resource types RT1 and RT2
                    2.RS2 is created under resource type RT2
                    3.Custom View V1 is created selecting RS1, RS2, status types ST1, ST2
                    Change the resource RS1's resource type and verify that the changes are 
                    reflected on the custom view screen of V1.
	'Arguments	   :None
	'Returns	   :None
	'Date		   :12/6/2012
	'Author		   :QSG
	'---------------------------------------------------------------------------------------------------
	'Modified Date				                                                         Modified By
	'Date					                                                             Name
	****************************************************************************************************/


	@Test
	public void testFTS40452() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();

		try {
			gstrTCID = "40452"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1.ST1 is associated with resource types RT1 and RT2  "
					+ "2.RS2 is created under resource type RT2"
					+ "3.Custom View V1 is created selecting RS1, RS2, status types ST1, ST2"
					+ "Change the resource RS1's resource type and verify that the changes are reflected on"
					+ " the custom view screen of V1.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String statrNumTypeName1 = "NST1_" + strTimeText;
			String statrNumTypeName2 = "NST2_" + strTimeText;
			String str_roleStatusTypeValues[] = new String[2];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";

			// RT
			String strResrctTypName1 = "AutoRT1_" + strTimeText;
			String strResrctTypName2 = "AutoRT2_" + strTimeText;
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strAbbrv = "AB";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strRSValue[] = new String[2];
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strCategory = "(Any)";
			String strCityZipCd = "";
			String strRegion = "(Any)";

			// Role
			String strRolesName = "AutoRol_1" + strTimeText;
			String strRoleValue = "";
			// user
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 = "FN" + strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			// Section
			String strSection1 = "AB_1" + strTimeText;
			String strSectionValue = "";
			String strArStatType[] = { statrNumTypeName1, statrNumTypeName2 };
			/*
			 * STEP : Action:Preconditions: 1. Resource type RT1 is associated
			 * with status type ST1. 2. Resource type RT2 is associated with
			 * status type ST1. 3. Resource RS1 is created under resource type
			 * RT1. 4. Resource RS2 is created under resource type RT2. 5.
			 * Create user U1 with the following rights, a. Setup Resource type
			 * b. Setup Resource c. View Custom view Expected Result:No Expected
			 * Result
			 */
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");
			try {
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName1);
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
						seleniumPrecondition, strNSTValue, statrNumTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName2);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrctTypName1,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrctTypName2,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Resources
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RSb
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv, strResrctTypName2,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// User
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName1,
								strInitPwd, strConfirmPwd, strUsrFulName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.CustomView");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
				blnLogin = false;
				strFuncResult = objLogin.login(seleniumFirefox,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumFirefox,
						strRegn);
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
				strFuncResult = objViews
						.navToEditResDetailViewSections(seleniumFirefox);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.dragAndDropSTtoSection(
						seleniumFirefox, strArStatType, strSection1, true);
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
			
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID+ " ends----------");
		/*
		 * STEP : Action:Login as user U1, Navigate to Setup >> Resources
		 * screen, click on 'Edit Status Types'link corresponding to
		 * resource RS1. Expected Result:'Edit Resource-Level Status Types'
		 * screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select check box corresponding to ST2 and save the
			 * page Expected Result: User is returned to the 'Resource List'
			 * screen
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, str_roleStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View Expected
			 * Result: 'Edit Custom View' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on button 'Add more Resources' Expected
			 * Result:'Find Resources' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navToFindResPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Enter resource RS in the 'Name' field and click on
			 * 'Search' Expected Result:Resource RS1 and RS2 are listed on the
			 * 'Find Resource' page
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strResourc = { "AutoRs_" };
				strFuncResult = objPreferences.findResources(selenium,
						strResourc, strCategory, strRegion, strCityZipCd,
						strState);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource1, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Select check box associated with resources RS1, RS2
			 * and click on button 'Add to custom View' Expected Result:'Edit
			 * Custom View' screen is displayed. Resource RS1 is displayed along
			 * with resource type RT1 under the section (created as the name
			 * given to the resource type RT1) Resource RS2 is displayed along
			 * with resource type RT2 under the section (created as the name
			 * given to the resource type RT2)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.AddResourceToCustomView(
						selenium, strResource2, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource1, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource2, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on button 'Option' Expected Result:'Edit
			 * Custom View Options (Columns)'screen is displayed. Status types
			 * ST1 and ST2 are displayed in the appropriate sections
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName1, statrNumTypeName2 };
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Select the check boxes ST1 and ST2, select among
			 * the four check boxes accordingly and click on Save Expected
			 * Result:'Custom View-Table' screen is displayed Resource RS1 is
			 * displayed under resource type RT1 along with status types ST1 and
			 * ST2 Resource RS2 is displayed under resource type RT2 along with
			 * status type ST1
			 */
			try {
				assertEquals("", strFuncResult);
				String[][] strSTValue = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" } };
				strFuncResult = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				String[] strStatType = { statrNumTypeName1, statrNumTypeName2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrctTypName1, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource2 };
				String[] strStatType = { statrNumTypeName1 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrctTypName2, strResource, strStatType);
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
			 * STEP : Action:Navigate to Setup >> Resource, click on the edit
			 * link associated with resource RS1, change the resource type RT1
			 * to RT2 and save Expected Result:'Resources List' page is
			 * displayed.
			 */
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
				strFuncResult = objResources.navResourcesList(selenium);
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
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrctTypName2,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> Custom Expected
			 * Result:Resource RS1 is displayed under Resource type RT1 along
			 * with Status type ST1 and ST2 Resource RS2 is displayed under
			 * Resource type RT2 along with Status type ST1
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				String[] strStatType = { statrNumTypeName1, statrNumTypeName2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrctTypName1, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource2 };
				String[] strStatType = { statrNumTypeName1 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrctTypName2, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View Expected
			 * Result:Section name (Resource Type name RT1) is retained.
			 * Resource name RS1 is displayed with the resource type RT2 Section
			 * name (Resource Type name RT2) is retained. Resource name RS2 is
			 * displayed with the resource type RT2
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPageNew(selenium,
						strResource1, strResrctTypName1, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPageNew(selenium,
						strResource2, strResrctTypName2, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on button 'Option' Expected Result:'Edit
			 * Custom View Options (Columns)'screen is displayed. Status type
			 * ST1 and ST2 are displayed in the appropriate section
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType);
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
			 * STEP : Action:Navigate to Setup >> Resource, click on the edit
			 * link associated with resource RS1, change the resource type RT2
			 * to RT1 and save Expected Result: 'Resources List' page is
			 * displayed.
			 */
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
				strFuncResult = objResources.navResourcesList(selenium);
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
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource1, strAbbrv, strResrctTypName1,
						strStandResType, strContFName, strContLName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Views >> Custom Expected Result:Custom
			 * View-Table' screen is displayed Resource RS1 is displayed under
			 * resource type RT1 along with status types ST1 and ST2 Resource
			 * RS2 is displayed under Resource type RT2 along with Status type
			 * ST1
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navViewCustomTable(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource1 };
				String[] strStatType = { statrNumTypeName1, statrNumTypeName2 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrctTypName1, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResource = { strResource2 };
				String[] strStatType = { statrNumTypeName1 };
				strFuncResult = objPreferences.verifyRTSTAndRSInCustView(
						selenium, strResrctTypName2, strResource, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Navigate to Preferences >> Customized View Expected
			 * Result:Section name (Resource Type name RT1) is retained.
			 * Resource name RS1 is displayed with the resource type RT1 Section
			 * name (Resource Type name RT2) is retained. Resource name RS2 is
			 * displayed with the resource type RT2
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.navEditCustomViewPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource1, strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences.verRSAndRTInEditCustomViewPage(
						selenium, strResource2, strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * STEP : Action:Click on button 'Option' Expected Result:'Edit
			 * Custom View Options (Columns)'screen is displayed. Status type
			 * ST1 and ST2 are displayed in the appropriate section
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditCustomViewOptionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.verifySTInSectionInEditCustomViwOptionPage(selenium,
								strRegn, strSectionValue, strSection1,
								strArStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "40452"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1.ST1 is associated with resource types RT1 and RT2  "
					+ "2.RS2 is created under resource type RT2"
					+ "3.Custom View V1 is created selecting RS1, RS2, status types ST1, ST2"
					+ "Change the resource RS1's resource type and verify that the changes are reflected on"
					+ " the custom view screen of V1.";// Test Objective
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
	
	/****************************************************************************************************
	'Description   :Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
					1. ST1 is associated with resource types RT1 and RT2
					2. RS2 is created under resource type RT2
					3. View V1 is created selecting RS1, ST1 and ST2
					4. Status Change Preferences notification are set for the Resource RS1 and Status types
					ST1 and ST2Change the resource RS1's resource type and verify that the notifications
					 are received for the resource RS1 when updat
	'Arguments	   :None
	'Returns	   :None
	'Date		   :26/12/2012
	'Author		   :QSG
	'-----------------------------------------------------------------------------------------------------
	'Modified Date				                       Modified By
	'Date					                              Name
	******************************************************************************************************/

	@Test
	public void testFTS40503() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();// object of class General

		try {
			gstrTCID = "40503"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource"
					+ " level under resource type RT1, where:1. ST1 is "
					+ "associated with resource types RT1 and RT2 2. RS2 is"
					+ " created under resource type RT2 3. View V1 is created "
					+ "selecting RS1, ST1 and ST2 4. Status Change Preferences "
					+ "notification are set for the Resource RS1 and Status types "
					+ "ST1 and ST2 Change the resource RS1's resource type and "
					+ "verify that the notifications are received for the resource RS1 when updat"
					+ " the custom view screen of V1.";
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String statrNumTypeName1 = "ST1_" + strTimeText;
			String statrNumTypeName2 = "ST2_" + strTimeText;
			String strSTvalue[] = new String[2];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";

			// RT
			String strResrctTypName1 = "AutoRT1_" + strTimeText;
			String strResrctTypName2 = "AutoRT2_" + strTimeText;
			String[] strRTValue=new String[2];
			
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String[] strRSValue=new String[2];
			
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			
			// user
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 =strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			
			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			int intEMailRes = 0;
			int intPagerRes = 0;
			
			int intEMailRes_1 = 0;
			int intPagerRes_1 = 0;
			
			int intEMailRes_2= 0;
			int intPagerRes_2 = 0;
			
			int intEMailRes_3= 0;
			int intPagerRes_3 = 0;
			
			int intResCnt = 0;
			
			String strCurrDate = "";
			String strCurrDate2="";
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody1_Desc="";
			String strMsgBody2 = "";
			String strMsgBody2_Desc = "";
			String strSubjName = "";
					
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			
			/*
			 * 1. Resource type RT1 is associated with status type ST1.
			 */
			try {
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
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrNumTypeName1);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrNumTypeName2);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			/*1. Resource type RT1 is associated with status type ST1.
			*/
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(selenium,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			
			/*2. Resource type RT2 is associated with status type ST1.
			*/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(selenium,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[1] = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName2);
				if (strRTValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			/*
			 * 3. Resource RS1 is created under resource type RT1.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);		
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources
						.fetchResValueInResList(selenium, strResource1);

				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Resource RS2 is created under resource type RT2.
			 */
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource2, strAbbrv, strResrctTypName2,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources
						.fetchResValueInResList(selenium, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Select check box corresponding to ST2 and save the page User is
			 * returned to the 'Resource List' screen
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 4 Navigate to Setup >> Views, Create a view V1 selecting resource
			 * RS1 status types ST1 and ST2 No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRSValues = { strRSValue[0] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 5. Create user U1 with the following rights, a. Setup Resource
			 * type b. Setup Resource c. Edit Status Change Notification
			 * Preferences d. Role R1 to update the status. e. Update right on
			 * resource RS1
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd,
						strUsrFulName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], false, true,
						false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as user U1, Navigate to Setup >> Resources screen, click
			 * on 'Edit Status Types' link corresponding to resource RS. 'Edit
			 * Resource-Level Status Types' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 5 Navigate to Views >> V1 Resource RS1 is displayed under
			 * resource type RT1 along with status types ST1 and ST2
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName1, strResources,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNumTypeName1,statrNumTypeName2};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Navigate to Preferences >> Status Change Prefs 'My Status
			 * Change Preferences' page is displayed
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Click on button 'Add' 'Find Resources' page is displayed
			 */
			/*
			 * 8 Provide resource RS1 in the Name field, click on button
			 * 'Search', select check box corresponding to RS1 and click on
			 * 'Notification' 'Edit My Status Change Preferences' screen is
			 * displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource1, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			
			/*
			 * 9 Select notification methods (web, e-mail, pager) for status
			 * types ST1 and ST2 and click on button 'Save'. 'My Status Change
			 * Preferences' screen is displayed with the preferences set.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statrNumTypeName1, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[1],
								statrNumTypeName1, true, true, true);
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
				blnLogin = false;
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
			 * 10 Navigate to Setup >> Resource, click on the edit link
			 * associated with resource RS1, change the resource type RT1 to RT2
			 * and save 'Resources List' page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objResources.navResourcesList(selenium);
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
				strFuncResult = objResources.selctRT(selenium,
						strResrctTypName2);
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
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 11 Navigate to Views >> V1 Update the status types St1 and ST2
			 * for the resource RS1 Notifications are received accordingly as
			 * set for the Status Change Preferences.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUpdateStatusByKey(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"101", strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName1, "101", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			try {
				assertEquals("", strFuncResult);
				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);			
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName1 + " status from 0 to 101.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName1 + " status from 0 to 101.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName1 + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate +" "+strAbbrv+"[0]"+ "\n"
						+ "Region: " + strRegn + "";

				strMsgBody2_Desc = statrNumTypeName1 + " from 0 to 101; "
						+ "Reasons: \nSummary at " + strCurrDate2 +" "+strAbbrv+"[0]"+ "\n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName1
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName1
						+ " status:\n"+strResource2+" = 0\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName1
						+ " status from 0 "
						+ "to 101.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName1
						+ " status:\n"+strResource2+" = 0\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes == 2 && intPagerRes == 1) {
						intResCnt++;
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//ST2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objViews.navToUpdateStatusByKey(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[1], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName1, "102", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName2 + " status from 0 to 102.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName2 + " status from 0 to 102.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName2 + " from 0 to 102; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody2_Desc = statrNumTypeName2 + " from 0 to 102; "
						+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 0 "
						+ "to 102.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 0 "
						+ "to 102.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_1 == 2 && intPagerRes_1 == 1) {
						intResCnt++;
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 12 Navigate to Setup >> Resource, click on the edit link
			 * associated with resource RS1, change the resource type RT2 to RT1
			 * and save 'Resources List' page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);

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
				blnLogin=true;
				strFuncResult = objResources.navResourcesList(selenium);
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
				strFuncResult = objResources.selctRT(selenium,
						strResrctTypName1);
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
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strUserName1, strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 13 Navigate to Views >> V1 Update the status types St1 and ST2
			 * for the resource RS1 Notifications are received accordingly as
			 * set for the Status Change Preferences.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"103", strSTvalue[0], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName1, "103", "3");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*
				 * String str = strDate[0].substring(1, 3); log4j.info(str);
				 */

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName1 + " status from 101 to 103.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName1 + " status from 101 to 103.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName1 + " from 101 to 103; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";
				
				strMsgBody2_Desc = statrNumTypeName1 + " from 101 to 103; "
				+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
				+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName1
						+ " status from 101 "
						+ "to 103.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName1
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName1
						+ " status from 101 "
						+ "to 103.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName1
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");


						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_2 == 2 && intPagerRes_2 == 1) {
						intResCnt++;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"104", strSTvalue[1], false, "", "");
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName1, "104", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*
				 * String str = strDate[0].substring(1, 3); log4j.info(str);
				 */

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName2 + " status from 102 to 104.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName2 + " status from 102 to 104.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName2 + " from 102 to 104; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";
				
				strMsgBody2_Desc = statrNumTypeName2 + " from 102 to 104; "
				+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
				+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 102 "
						+ "to 104.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 102 "
						+ "to 104.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");


						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_3 == 2 && intPagerRes_3 == 1) {
						intResCnt++;
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				assertEquals("", gstrReason);
				if(intResCnt==8){
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "40503"; 
			gstrTO = "Add status type ST2 to resource RS1 at resource"
					+ " level under resource type RT1, where:1. ST1 is "
					+ "associated with resource types RT1 and RT2 2. RS2 is"
					+ " created under resource type RT2 3. View V1 is created "
					+ "selecting RS1, ST1 and ST2 4. Status Change Preferences "
					+ "notification are set for the Resource RS1 and Status types "
					+ "ST1 and ST2 Change the resource RS1's resource type and "
					+ "verify that the notifications are received for the resource RS1 when updat"
					+ " the custom view screen of V1.";
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
	'Description   :Add status types ST2 and ST3 to resource RS1 at resource level under resource type RT1, where:
					1. ST1 is associated with RT1.
					2. RS3 is created under RT1.
					3. ST2 is associated with RT2.
					4. RS2 is created under RT2.
					5. View V1 is created selecting RT1, RT2, ST1, ST2 and ST3
					6. Status change preferences are set for ST1, ST2 and ST3 for RS1
					Change RS1's resource type and verify that user U1 still receives notifications for RS1
	'Arguments	   :None
	'Returns	   :None
	'Date		   :28/12/2012
	'Author		   :QSG
	'---------------------------------------------------------------
	'Modified Date				                       Modified By
	'Date					                              Name
	***************************************************************/

	@Test
	public void testFTS40548() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		Views objViews = new Views();
		EventNotification objEventNotification = new EventNotification();
		General objMail = new General();
		try {
			gstrTCID = "40548";
			gstrTO = "Add status types ST2 and ST3 to resource RS1 at resource"
					+ " level under resource type RT1, where:1. ST1 is associated"
					+ " with RT1.2. RS3 is created under RT1.3. ST2 is associated with "
					+ "RT2.4. RS2 is created under RT2.5. View V1 is created selecting"
					+ " RT1, RT2, ST1, ST2 and ST3 6. Status change preferences are set "
					+ "for ST1, ST2 and ST3 for RS1 Change RS1's resource type and "
					+ "verify that user U1 still receives notifications for RS1";
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// ST
			String statrNumTypeName1 = "ST1_" + strTimeText;
			String statrNumTypeName2 = "ST2_" + strTimeText;
			String statrNumTypeName3 = "ST3_" + strTimeText;
			String strSTvalue[] = new String[3];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";

			// RT
			String strResrctTypName1 = "AutoRT1_" + strTimeText;
			String strResrctTypName2 = "AutoRT2_" + strTimeText;
			String[] strRTValue=new String[2];
			
			// RS
			String strResource1 = "AutoRs_1" + strTimeText;
			String strResource2 = "AutoRs_2" + strTimeText;
			String strResource3 = "AutoRs_3" + strTimeText;
			
			String[] strRSValue=new String[3];
			
			String strAbbrv = "A" + strTmText;
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strRolesName_1 = "Role_1" + strTimeText;
			String strRoleValue = "";
			
			// user
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName1 =strUserName1;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strFirstName = "";
			String strMiddleName = "";
			String strLastName = "";
			String strOrganization = "";
			String strPhoneNo = "";
			String strPrimaryEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);
			String strPagerValue = rdExcel.readData("WebMailUser", 2, 1);
			String strAdminComments = "";
			
			String strViewName = "AutoV_" + strTimeText;
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
			
			String strStartDate = "";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			
			int intEMailRes_1 = 0;
			int intPagerRes_1 = 0;
			
			int intEMailRes_2= 0;
			int intPagerRes_2 = 0;
			
			int intEMailRes_3= 0;
			int intPagerRes_3 = 0;
			
			int intResCnt = 0;
			
			String strCurrDate = "";
			String strCurrDate2="";
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);

			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strMsgBody1 = "";
			String strMsgBody1_Desc="";
			String strMsgBody2 = "";
			String strMsgBody2_Desc = "";
			String strSubjName = "";
			
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			/* 1. Resource type RT1 is associated with status type ST1. */
			try {
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
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrNumTypeName1);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrNumTypeName2);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strNSTValue, statrNumTypeName3,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						selenium, statrNumTypeName3);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" },{ strSTvalue[2], "true" } };
				strFuncResult = objRoles
						.slectAndDeselectSTInCreateRole(selenium, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
						strRolesName_1);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/* 2. Resource type RT2 is associated with status type ST2. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(selenium,
						strResrctTypName1,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName1);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			
			/*2. Resource type RT2 is associated with status type ST1.
			*/
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(selenium,
						strResrctTypName2,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrctTypName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[1] = objResourceTypes.fetchRTValueInRTList(selenium,
						strResrctTypName2);
				if (strRTValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3. Resource RS1 and RS3 are created under resource type RT1.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource1, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objResources
						.fetchResValueInResList(selenium, strResource1);

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
				
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource3, strAbbrv, strResrctTypName1,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[2] = objResources
						.fetchResValueInResList(selenium, strResource3);

				if (strRSValue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4. Resource RS2 is created under resource type RT2.
			 */
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objResources.createResourceWitLookUPadres(
						selenium, strResource2, strAbbrv, strResrctTypName2,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objResources
						.fetchResValueInResList(selenium, strResource2);

				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Login as user U1, Navigate to Setup >> Resources screen, click
			 * on 'Edit Status Types' link corresponding to resource RS. 'Edit
			 * Resource-Level Status Types' screen is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources
						.navToEditResLevelSTPage_LinkChanged(selenium,
								strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 3 Select check box corresponding to ST2, ST3 and save the page
			 * User is returned to the 'Resource List' screen
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.selDeselctOnlySTInEditRSLevelPage(
						selenium, strSTvalue[1], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objResources.selDeselctSTInEditRSLevelPage(
						selenium, strSTvalue[2], true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Navigate to Setup >> Views, Create a view V1 is created
			 * selecting RT1, RT2, ST1, ST2 and ST3 No Expected Result
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strRSValues = { strRSValue[0],strRSValue[1],strRSValue[2] };
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, true, false,
						strSTvalue, false, strRSValues);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5. Create user U1 with the following rights, a. Setup Resource
			 * type b. Setup Resource c. Edit Status Change Notification
			 * Preferences d. Role R1 to update the status. e. Update right on
			 * resource RS1
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName1, strInitPwd, strConfirmPwd,
						strUsrFulName1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(selenium,
						strRoleValue, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						selenium, strResource1, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						selenium, strFirstName, strMiddleName, strLastName,
						strOrganization, strPhoneNo, strPrimaryEMail, strEMail,
						strPagerValue, strAdminComments);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			/*
			 * 2 Login as user U1, Navigate to Setup >> Resources screen, click
			 * on 'Edit Status Types' link corresponding to resource RS. 'Edit
			 * Resource-Level Status Types' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1, strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5 Navigate to Views >> V1 Resource RS1 is displayed under
			 * resource type RT1 along with status types ST1, ST2 and ST3
			 * Resource RS3 is displayed under resource type RT1 along with
			 * status type ST1
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strResource1 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName1, strResources,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strResources[] = { strResource3 };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName, strResrctTypName1, strResources,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNumTypeName1,statrNumTypeName2,statrNumTypeName3};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType={statrNumTypeName1};
				strFuncResult = objViews.checkStatusTypeInUserView(selenium, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Navigate to Preferences >> Status Change Prefs 'My Status
			 * Change Preferences' page is displayed
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 7 Click on button 'Add' 'Find Resources' page is displayed
			 */
			/*
			 * 8 Provide resource RS1 in the Name field, click on button
			 * 'Search', select check box corresponding to RS1 and click on
			 * 'Notification' 'Edit My Status Change Preferences' screen is
			 * displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(selenium,
								strResource1, strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			/*
			 * 9 Select notification methods (web, e-mail, pager) for status
			 * types ST1, ST2, ST3 and click on button 'Save'. 'My Status Change
			 * Preferences' screen is displayed with the preferences set.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[0],
								statrNumTypeName1, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[1],
								statrNumTypeName2, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(selenium,
								strResource1, strRSValue[0], strSTvalue[2],
								statrNumTypeName3, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Login as RegAdmin, Navigate to Setup >> Resource, click on the
			 * edit link associated with resource RS1, change the resource type
			 * RT1 to RT2 and save 'Resources List' page is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);

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
				blnLogin = true;
				strFuncResult = objResources.navResourcesList(selenium);
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
				strFuncResult = objResources.selctRT(selenium,
						strResrctTypName2);
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
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 11 Login as user U1, Navigate to Views >> V1 Update the status
			 * types ST1, ST2 and ST3 for resource RS1 Notifications are
			 * received as set for the Status Change Preferences for status
			 * types ST2 and ST3. ST1 does not receive the notification.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strElementID="css=#st_"+strSTvalue[0];
				strFuncResult = objMail.checkForAnElements(selenium, strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("Element NOT Present", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"102", strSTvalue[1], false, "", "");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName2 + " status from 0 to 102.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName2 + " status from 0 to 102.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				log4j.info(intResCnt);
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName2 + " from 0 to 102; "
						+ "Reasons: \nSummary at " + strCurrDate +" "+strAbbrv+"[0]"+ "\n"
						+ "Region: " + strRegn + "";

				strMsgBody2_Desc = statrNumTypeName2 + " from 0 to 102; "
						+ "Reasons: \nSummary at " + strCurrDate2 +" "+strAbbrv+"[0]"+ "\n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 0 "
						+ "to 102.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n"+strResource2+" = 0\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 0 "
						+ "to 102.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n"+strResource2+" = 0\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_2 == 2 && intPagerRes_2 == 1) {
						intResCnt++;
						log4j.info(intResCnt);
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//ST3
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"103", strSTvalue[2], false, "", "");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*String str = strDate[0].substring(1, 3);
				log4j.info(str);*/

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay =  strDate[0];
				strFndStYear=dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName3 + " status from 0 to 103.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName3 + " status from 0 to 103.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				log4j.info(intResCnt);
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName3 + " from 0 to 103; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody2_Desc = statrNumTypeName3 + " from 0 to 103; "
						+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
						+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName3
						+ " status from 0 "
						+ "to 103.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName3
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName3
						+ " status from 0 "
						+ "to 103.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName2
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName3
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_3 == 2 && intPagerRes_3 == 1) {
						intResCnt++;
						log4j.info(intResCnt);
					}

					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();

					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Login as RegAdmin, Navigate to Setup >> Resource, click on the
			 * edit link associated with resource RS1, change the resource type
			 * RT2 to RT1 and save 'Resources List' page is displayed.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);

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
				blnLogin = true;
				strFuncResult = objResources.navResourcesList(selenium);
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
				strFuncResult = objResources.selctRT(selenium,
						strResrctTypName1);
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
				blnLogin = false;
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			/*
			 * 13 Login as User U1, Navigate to Views >> V1 Update the status
			 * types ST1, ST2 and ST3 for the resource RS1 Notifications are
			 * received accordingly as set for the Status Change Preferences
			 * (ST1, ST2 and ST3 receive notifications)
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"104", strSTvalue[0], false, "", "");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*
				 * String str = strDate[0].substring(1, 3); log4j.info(str);
				 */

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName1 + " status from 0 to 104.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName1 + " status from 0 to 104.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				log4j.info(intResCnt);
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName1 + " from 0 to 104; "
						+ "Reasons: \nSummary at " + strCurrDate +" "+strAbbrv+"[0]"+ "\n"
						+ "Region: " + strRegn + "";
				
				strMsgBody2_Desc = statrNumTypeName1 + " from 0 to 104; "
				+ "Reasons: \nSummary at " + strCurrDate2 +" "+strAbbrv+"[0]"+ "\n"
				+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName1
						+ " status from 0 "
						+ "to 104.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName1
						+ " status:\n"+strResource3+" = 0\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";
				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName1
						+ " status from 0 "
						+ "to 104.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName1
						+ " status:\n"+strResource3+" = 0\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");


						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_1++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_1++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_1 == 2 && intPagerRes_1 == 1) {
						intResCnt++;
						log4j.info(intResCnt);
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"105", strSTvalue[1], false, "", "");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName2, "105", "4");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*
				 * String str = strDate[0].substring(1, 3); log4j.info(str);
				 */

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName2 + " status from 102 to 105.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName2 + " status from 102 to 105.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				log4j.info(intResCnt);
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName2 + " from 102 to 105; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";
				
				strMsgBody2_Desc = statrNumTypeName2 + " from 102 to 105; "
				+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
				+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 102 "
						+ "to 105.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName2
						+ " status from 102 "
						+ "to 105.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName2
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						
						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_2++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_2++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_2 == 4 && intPagerRes_2 == 2) {
						intResCnt++;
						log4j.info(intResCnt);
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"106", strSTvalue[2], false, "", "");
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifyUpdateSTValInViews(selenium,
						statrNumTypeName2, "106", "5");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strUpdatedDate = selenium.getText("css=#statusTime");
				log4j.info(strUpdatedDate);

				String strDate[] = strUpdatedDate.split(" ");

				for (String s : strDate) {
					log4j.info(s);
				}

				/*
				 * String str = strDate[0].substring(1, 3); log4j.info(str);
				 */

				String str1 = strDate[2].substring(0, 2);
				log4j.info(str1);

				String str2 = strDate[2].substring(3, 5);
				log4j.info(str2);

				// get Start Date
				strFndStMnth = strDate[1];
				strFndStDay = strDate[0];
				strFndStYear = dts.getFutureYear(0, "yyyy");

				// get Start Time
				strFndStHr = str1;
				strFndStMinu = str2;

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
				blnLogin = true;

				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");

				String strCurrentDate = strCurrDate;
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;
				String strTimeFormat = strFndStHr + ":" + strFndStMinu;

				String strFndStMinu2 = dts.AddTimeToExistingTimeHourAndMin(
						strTimeFormat, 0, 1, "HH:mm");

				strCurrDate2 = strCurrentDate + " " + strFndStMinu2;

				String strSTDateTime = strStartDate;

				String strDesc1 = "On " + strCurrDate + " changed "
						+ statrNumTypeName3 + " status from 103 to 106.";

				String strDesc2 = "On " + strCurrDate2 + " changed "
						+ statrNumTypeName3 + " status from 103 to 106.";

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationCorrect(selenium, strResource1,
								strSTDateTime, strAddedDtTime, strDesc1,
								strDesc2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			try {
				assertEquals("", strFuncResult);
				intResCnt++;
				log4j.info(intResCnt);
				Thread.sleep(60000);

				strSubjName = "Change for " + strAbbrv;

				strMsgBody2 = statrNumTypeName3 + " from 103 to 106; "
						+ "Reasons: \nSummary at " + strCurrDate + " \n"
						+ "Region: " + strRegn + "";
				
				strMsgBody2_Desc = statrNumTypeName3 + " from 103 to 106; "
				+ "Reasons: \nSummary at " + strCurrDate2 + " \n"
				+ "Region: " + strRegn + "";

				strMsgBody1 = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName3
						+ " status from 103 "
						+ "to 106.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName3
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				strMsgBody1_Desc = "Status Update for "
						+ strUsrFulName1
						+ ": "
						+ "\nOn "
						+ strCurrDate2
						+ " "
						+ strResource1
						+ " "
						+ "changed "
						+ statrNumTypeName3
						+ " status from 103 "
						+ "to 106.\n\nComments: \n\nReasons: \n\nRegion: "
						+ ""
						+ strRegn
						+ "\n\nOther "
						+ strResrctTypName1
						+ "s "
						+ "in the region report the following "
						+ statrNumTypeName3
						+ " status:\n\n\nPlease do not reply to this email message."
						+ " You must log into EMResource to take any action that may"
						+ " be required.";

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);


				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					
					

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

					

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strSubjName = "Status Change for " + strResource1;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");
						

						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}


					
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					selenium.selectFrame("relative=up");
					selenium.selectFrame("horde_main");
					// click on Back to Inbox
					selenium.click("link=Back to Inbox");
					selenium.waitForPageToLoad("90000");

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						// assertTrue(strFuncResult.equals(""));
						String strMsg = selenium
								.getText("css=div.fixed.leftAlign");


						if (strMsg.equals(strMsgBody1)||strMsg.equals(strMsgBody1_Desc)) {
							intEMailRes_3++;
							log4j.info("Email body is displayed");
						} else if (strMsg.equals(strMsgBody2)||strMsg.equals(strMsgBody2_Desc)) {
							intPagerRes_3++;
							log4j.info("Pager body is displayed");
						} else {
							log4j.info("Email and Pager body is NOT displayed");
						}

						

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}
					// check Email, pager notification
					if (intEMailRes_3 == 4 && intPagerRes_3 == 2) {
						intResCnt++;
						log4j.info(intResCnt);
					}
					
					selenium.selectWindow("Mail");
					selenium.selectFrame("horde_main");
					selenium.click("link=Log out");
					selenium.waitForPageToLoad("90000");
					selenium.close();
					
					
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					Thread.sleep(4000);

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*14 	Navigate to Preferences >> Status change Prefs 		'Edit My Status Change Preferences' screen is displayed.
  	  		Status types ST1, ST2 and ST3 are displayed along with the preferences set.
  	  		*/
  	  		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(selenium,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData={{"0","true","true","true"}};
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statrNumTypeName1, strNotifData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData={{"0","true","true","true"}};
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statrNumTypeName2, strNotifData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] strNotifData={{"0","true","true","true"}};
				strFuncResult = objPreferences
						.checkStatTypeInEditMyStatChangePrefs(selenium,
								statrNumTypeName3, strNotifData);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				log4j.info(gstrReason);
				assertEquals("", gstrReason);
				log4j.info(intResCnt);
				
				if(intResCnt==10){
					gstrResult = "PASS";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "40548";
			gstrTO = "Add status types ST2 and ST3 to resource RS1 at resource"
					+ " level under resource type RT1, where:1. ST1 is associated"
					+ " with RT1.2. RS3 is created under RT1.3. ST2 is associated with "
					+ "RT2.4. RS2 is created under RT2.5. View V1 is created selecting"
					+ " RT1, RT2, ST1, ST2 and ST3 6. Status change preferences are set "
					+ "for ST1, ST2 and ST3 for RS1 Change RS1's resource type and "
					+ "verify that user U1 still receives notifications for RS1";
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
	/*************************************************************************************************************
	'Description   :From resource RS, remove a status type ST that was added at the resource level and verify that the
	                status change notification preferences set for ST for resource RS are also removed for the user
	'Arguments	   :None
	'Returns	   :None
	'Date		   :12/19/2012
	'Author	       :QSG
	'------------------------------------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	**************************************************************************************************************/

	@Test
	public void testFTS36847() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		Preferences objPreferences = new Preferences();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRoles = new Roles();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "36847"; // Test Case Id
			gstrTO = "From resource RS, remove a status type ST that was added at the resource level and verify "
					+ "that the status change notification preferences set for ST for resource RS are also removed "
					+ "for the user";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			// Create ST1(NST)
			String strStatType1 = "Number";
			String statTypeName1 = "AutoNST1_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strSTvalue1[] = new String[1];
			strSTvalue1[0] = "";

			String strUpdatTxtValue1 = "1";
			String strUpdatTxtValue2 = "b";

			String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6", "7",
					"8" };
			String strScUpdValue2[] = { "1", "2", "3", "4", "5", "6", "7", "8",
					"9" };

			String strUpdateValue1 = "393";
			String strUpdateValue2 = "429";

			String strStatTypeNST = "Number";
			String strStatTypeMST = "Multi";
			String strStatTypeSST = "Saturation Score";
			String statTypeNameNST = "AutoNst_" + strTimeText;
			String statTypeNameMST = "AutoMst_" + strTimeText;
			String statTypeNameSST = "AutoSst_" + strTimeText;
			String strSTvalue[] = new String[3];
			String strStatusName = "Sta" + strTimeText;
			String strStatTypeColor = "Black";
			String strStatusNameValue = "";

			// RT data
			String strRsTypeValues[] = new String[1];
			String strResrcTypName = "AutoRT_" + strTimeText;

			// Resource
			String strRSValues[] = new String[2];
			String strResource1 = "AutoRS1_" + strTimeText;
			String strResource2 = "AutoRS2_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Alabama";
			String strCountry = "Coosa County";

			// Role
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleValue = "";

			// Data for creating user1 with update resource right
			String strUserName1 = "auto1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = strInitPwd;
			String strUsrFulName1 = strUserName1 + "fulName";

			// Data for creating user2 with update resource right
			String strUserName2 = "auto2" + System.currentTimeMillis();
			String strUsrFulName2 = strUserName2 + "fulName";

			/*
			* STEP :
			  Action:Preconditions:
			1. Resource type RT is associated with only status type ST1 
			2. Status Types NST (Number status type), MST (Multi status type) and SST (Saturation score status type)are created.
			3. Resources RS1 and RS2 are created under RT.
			4. Status types NST, MST and SST are added for resource RS1 at the resource level.
			5. User U1 has following rights: 'Setup Resource Types'
				                              'Edit Status Change Notification Preferences'
				 and has set preferences to receive status change notifications for resource RS1 for status types 
				 NST, MST and SST
			6. User U2 has the role to update status types of NST, MST and SST and the 'Update Status' right on resource RS1
			   Expected Result:No Expected Result
			*/
			//219496

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
						seleniumPrecondition, strStatType1, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue1[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strSTvalue1[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create NST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatTypeNST, statTypeNameNST,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeNameNST);
				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatTypeMST, statTypeNameMST,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeNameMST);
				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statTypeNameMST, strStatusName,
						strStatTypeMST, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusNameValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statTypeNameMST, strStatusName);
				if (strStatusNameValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatTypeSST, statTypeNameSST,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeNameSST);
				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
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
						seleniumPrecondition, strResrcTypName, strSTvalue1[0]);
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
			// RS1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName, "FN", "LN", strState, strCountry,
						"Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValues[0] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources
						.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName, "FN", "LN", strState, strCountry,
						"Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValues[1] = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status types NST, MST and SST are added for resource RS1 at the
			// resource level

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(
						seleniumPrecondition, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int intRes = 0; intRes < strSTvalue.length; intRes++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
							seleniumPrecondition, strSTvalue[intRes], true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs
						.savAndVerifyEditRSLevelPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create Role
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				String strRoleRights[][] = {};
				assertEquals("", strFuncResult);
				String[] strSTvalues={strSTvalue1[0],strSTvalue[0],strSTvalue[1],strSTvalue[2]};
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRoleName, strRoleRights,
						strSTvalues, true, strSTvalues, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			
			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRoleName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create U1
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
						strConfirmPwd, strUsrFulName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Create U2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(
						seleniumPrecondition, strUserName2, strInitPwd,
						strConfirmPwd, strUsrFulName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValues[0],
						false, true, false, true);
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
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Login using U1 credentials
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName1, strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSearchCategory = "(Any)";
				strFuncResult = objPreferences.addResourcesInST(
						seleniumPrecondition, strResource1, strSearchCategory,
						strRSValues[0], strSTvalue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								seleniumPrecondition, strResource1,
								strRSValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(
								seleniumPrecondition, strResource1,
								strRSValues[0], strSTvalue[1], statTypeNameMST,
								strStatusNameValue, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strSearchCategory = "(Any)";
				strFuncResult = objPreferences.addResourcesInST(
						seleniumPrecondition, strResource1, strSearchCategory,
						strRSValues[0], strSTvalue[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				log4j.info("................Precondition Execution ends............................");
			/*
			 * STEP 2: Action:Login as user U2 and update the status of status
			 * types NST, MST and SST Expected Result:No Expected Result
			 */
			// 219497
			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName2,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeNameNST, statTypeNameMST,
						statTypeNameSST };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateMultiStatusType(selenium,
						strResource1, statTypeNameMST, strSTvalue[1],
						strStatusName, strStatusNameValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateNumStatusType(selenium,
						strResource1, statTypeNameNST, strSTvalue[0],
						strUpdatTxtValue1, strUpdatTxtValue2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navResPopupWindowNew1(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.updateSatuScoreStatusType(selenium,
						strResource1, statTypeNameSST, strSTvalue[2],
						strScUpdValue1, strScUpdValue2, strUpdateValue1,
						strUpdateValue2);
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
			* STEP 3:
			  Action:Logout and login as user U1 and navigate to Setup>>Resource, click on 'Edit Status Types' 
			  link corresponding to resource RS1
			  Expected Result:'Edit Resource-Level Status Types' screen is displayed.
			*/

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strConfirmPwd);
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
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		   /*
			* STEP 4:
			  Action:Deselect status types NST, MST and SST and save
			  Expected Result:User is returned to the 'Resource List' page
			*/
			//219552
			for (int intRes = 0; intRes < strSTvalue.length; intRes++) {
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
							selenium, strSTvalue[intRes], false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		   /*
			* STEP 5:
			  Action:Navigate to 'Preferences>>Status Change Prefs'
			  Expected Result:RS1 is not listed on the page
			*/
			//219554
			 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/"
								+ "table/tbody/tr/td[text()='" + strResource1
								+ "']"));
				log4j.info(strResource1 + " is NOT listed on the page");
			} catch (AssertionError Ae) {
				strFuncResult = strResource1 + " is  listed on the page";
				log4j.info(strResource1 + " is listed on the page");
				gstrReason = strFuncResult;
			}

			 /* STEP 6:
			  Action:Click on 'Add' button, search for resource RS1 and click on 'Notifications'
			  Expected Result:'Edit My Status Change Preferences' page is displayed with RS1 and only ST1 is displayed in the page.
			                   NST, MST and SST are not displayed.
			*/
			//219555

			try {
				assertEquals("", strFuncResult);
				// add res data
				String strSearchCategory = "(Any)";
				String strRegion = "";
				String strSearchWhere = "";
				strFuncResult = objPreferences.navTOFindResourcesPage(selenium,
						strResource1, strSearchCategory, strRegion, 
						strSearchWhere, strState, strRSValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeName1
								+ "']"));
				log4j.info("only " + statTypeName1 + " is displayed with "
						+ strResource1 + "in the page");
			} catch (AssertionError Ae) {
				log4j.info("only " + statTypeName1 + " is NOT displayed with "
						+ strResource1 + "in the page");
				strFuncResult = "only " + statTypeName1
						+ " is NOT displayed with " + strResource1
						+ "in the page";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeNameNST
								+ "']"));
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeNameMST
								+ "']"));
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeNameSST
								+ "']"));
				log4j.info(statTypeNameNST + "," + statTypeNameMST + ","
						+ statTypeNameSST + " NOT displayed.");
			} catch (AssertionError Ae) {
				log4j.info(statTypeNameNST + "," + statTypeNameMST + ","
						+ statTypeNameSST + " displayed.");
				strFuncResult = statTypeNameNST + "," + statTypeNameMST + ","
						+ statTypeNameSST + " displayed.";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
		/*
		* STEP 7:
		  Action:Logout and login as any user with 'User - Setup User Accounts' right, navigate to Setup>>Users
		  Expected Result:Click on the link 'Edit' corresponding to user U1
		*/
		//223753
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserName1,
						strInitPwd);
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
				strFuncResult = objCreateUsers.navToEditUserPage(selenium,
						strUserName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP 8:
			  Action:Click on the 'Status Change Notification Preferences
			  ' link in the 'Edit User' page
			  Expected Result:RS1 is not listed on the page
			*/
			//223755
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMYStatusChangeNotiFrmEditUserPge(selenium,
								strUserName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//div[@id='mainContainer']/"
								+ "table/tbody/tr/td[text()='" + strResource1
								+ "']"));
				log4j.info(strResource1 + " is NOT listed on the page");
			} catch (AssertionError Ae) {
				strFuncResult = strResource1 + " is listed on the page";
				log4j.info(strResource1 + " is listed on the page");
				gstrReason = strFuncResult;
			}

			/*
			* STEP 9:
			  Action:Click on 'Add' button, search for resource RS1 and click on 'Notifications'
			  Expected Result:'Status Change Preferences for U1' page is displayed with RS1 and only ST1 is 
			  displayed in the page. 
		
			  NST, MST and SST are not displayed.
			*/
			//223754

			try {
				assertEquals("", strFuncResult);
				// add res data
				String strSearchCategory = "(Any)";
				String strRegion = "";
				String strSearchWhere = "";
				strFuncResult = objPreferences.navTOFindResourcesPage(selenium,
						strResource1, strSearchCategory, strRegion, 
						strSearchWhere, strState, strRSValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertTrue(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeName1
								+ "']"));
				log4j.info("only " + statTypeName1 + " is displayed with "
						+ strResource1 + "in the page");
			} catch (AssertionError Ae) {
				log4j.info("only " + statTypeName1 + " is NOT displayed with "
						+ strResource1 + "in the page");
				strFuncResult = "only " + statTypeName1
						+ " is NOT displayed with " + strResource1
						+ "in the page";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeNameNST
								+ "']"));
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeNameMST
								+ "']"));
				assertFalse(selenium
						.isElementPresent("//table[@class='displayTable']"
								+ "/tbody/tr/td/div[text()='" + statTypeNameSST
								+ "']"));
				log4j.info(statTypeNameNST + "," + statTypeNameMST + ","
						+ statTypeNameSST + " NOT displayed.");
			} catch (AssertionError Ae) {
				log4j.info(statTypeNameNST + "," + statTypeNameMST + ","
						+ statTypeNameSST + " displayed.");
				strFuncResult = statTypeNameNST + "," + statTypeNameMST + ","
						+ statTypeNameSST + " displayed.";
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrResult = "FAIL";
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "36847";
			gstrTO = "From resource RS, remove a status type ST that was added at the resource level and "
					+ "verify that the status change notification preferences set for ST for resource RS are "
					+ "also removed for the user";
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
	//start//testFTS40101//
	/*****************************************************************************************************
	'Description	:Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
	                 1. ST1 is associated with resource types RT1 and RT2
	                 2. RS2 is created under resource type RT2
	                 3. View V1 is created selecting RS1, ST1 and ST2Change the resource RS1's resource
	                  type and verify that the changes are reflected on the view screen of V1.
	'Arguments		:None
	'Returns		:None
	'Date			:8/16/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------------
	'Modified Date				                                                             Modified By
	'Date					                                                                 Name
	*****************************************************************************************************/

	@Test
	public void testFTS40101() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objST = new StatusTypes();		
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews=new Views();
		try {
			gstrTCID = "40101"; // Test Case Id
			gstrTO = " Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. View V1 is created selecting RS1, ST1 and ST2Change the resource RS1's resource"
					+ " type and verify that the changes are reflected on the view screen of V1.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strTxtStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strSTValue = new String[3];

			String strResType1 = "AutoRt1_" + strTimeText;
			String[] strRTValue = new String[2];
			String strResType2 = "AutoRt2_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];
			String strContFName = "auto";
			String strContLName = "qsg";

			//View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
	   /*
		* STEP :
		  Action:Preconditions:
			1. Resource type RT1 is associated with status type ST1.
			2. Resource type RT2 is associated with status type ST1.
			3. Resource RS is created under resource type RT1.
		  Expected Result:No Expected Result
		*/
		//234365
			
		 log4j.info("-----" +"Precondtion for test case "+gstrTCID+" starts------");
			
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTxtStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTValue[0] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strSTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTxtStatTypeValue,
						strStatType2, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTValue[1] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strSTValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType1,
						"css=input[name='statusTypeID'][value='"
								+ strSTValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResType1);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType2,
						"css=input[name='statusTypeID'][value='"
								+ strSTValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[1] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResType2);
				if (strRTValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
            //RS
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
						strResType1, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strResVal = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			
		/*
		* STEP :
		  Action:Login as RegAdmin, Navigate to Setup >> Resources screen, click on 'Edit Status Types'
		   link corresponding to resource RS.
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed.
		*/
		//234366

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select check box corresponding to ST2 and save the page
		  Expected Result:User is returned to the 'Resource List' screen
		*/
		//234367
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(selenium,
						strSTValue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Views, Create a view V1 selecting resource RS1 status types ST1 and ST2
		  Expected Result:No Expected Result
		*/
		//234400
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTValues[] = { strSTValue[0], strSTValue[1]};
				strFuncResult = objViews.createView(selenium, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Views >> V1
		  Expected Result:Resource RS1 is displayed under resource type RT1 along with status types ST1 and ST2
		*/
		//234370
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResType1, strResources,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1,strStatType2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Setup >> Resource, click on the edit link associated with resource RS1,
		   change the resource type RT1 to RT2 and save
		  Expected Result:'Resources List' page is displayed.
		*/
		//234371
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(
						selenium, strResource, strAbbrv, strResType2,
						strStandResType, false, false, "", "", false, "", "",
						strState, "", strCountry, "", "FN", "LN", "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Views >> V1
		  Expected Result:Resource RS1 is displayed under resource type RT2 along with status types ST1 and ST2.
		*/
		//234372
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResType2, strResources,
						strRTValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1,strStatType2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Setup >> Resource, click on the edit link associated with resource RS1,
		   change the resource type RT2 to RT1 and save
		  Expected Result:'Resources List' page is displayed.
		*/
		//234861
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(
						selenium, strResource, strAbbrv, strResType1,
						strStandResType, false, false, "", "", false, "", "",
						strState, "", strCountry, "", "FN", "LN", "", "", "",
						"", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Views >> V1
		  Expected Result:Resource RS1 is displayed under resource type RT1 along with status types ST1 and ST2
		*/
		//234862
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource };
				strFuncResult = objViews.checkResTypeAndResourceInUserView(
						selenium, strViewName_1, strResType1, strResources,
						strRTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strStatType = { strStatType1,strStatType2 };
				strFuncResult = objViews.checkStatusTypeInUserView(selenium,
						strStatType);
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
			gstrTCID = "FTS-40101";
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:"
					+ "1. ST1 is associated with resource types RT1 and RT2"
					+ "2. RS2 is created under resource type RT2"
					+ "3. View V1 is created selecting RS1, ST1 and ST2 Change the resource RS1's "
					+ "resource type and verify that the changes are reflected on the view screen of V1.";
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
   //end//testFTS40101//
	
	//start//testFTS40194//
	/*****************************************************************************************************
	'Description	:Add status type ST2 to resource RS1 at resource level under resource type RT1, where:
	                 1. ST1 is associated with resource types RT1 and RT2
	                 2. RS2 is created under resource type RT2
	                 3. View V1 is created selecting RS1, ST1 and ST2Change the resource RS1's resource
	                  type and verify that the changes are reflected on the view screen of V1.
	'Arguments		:None
	'Returns		:None
	'Date			:8/16/2013
	'Author			:QSG
	'----------------------------------------------------------------------------------------------------
	'Modified Date				                                                             Modified By
	'Date					                                                                 Name
	*****************************************************************************************************/

	@Test
	public void testFTS40194() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objST = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Views objViews = new Views();
		try {
			gstrTCID = "40194"; // Test Case Id
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:" +
					"1. ST1 is associated with resource types RT1 and RT2" +
					"2. RS2 is created under resource type RT2" +
					"3. View V1 is created selecting RS1, ST1 and RT2 (master check box)" +
					"Change the resource RS1's resource type and verify that the changes are reflected " +
					"on the view ";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");

			String strStatType1 = "AutoSt1_" + strTimeText;
			String strStatType2 = "AutoSt2_" + strTimeText;
			String strTxtStatTypeValue = "Number";
			String strTxtStatTypDefn = "Auto";
			String[] strSTValue = new String[3];

			String strResType1 = "AutoRt1_" + strTimeText;
			String[] strRTValue = new String[2];
			String strResType2 = "AutoRt2_" + strTimeText;
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			String strContFName = "auto";
			String strContLName = "qsg";

			// View
			String strViewName_1 = "autoView_1" + strTimeText;
			String strVewDescription = "Automation";
			String strViewType = "Summary Plus (Resources as rows."
					+ " Status types and comments as columns.)";
		   /*
			* STEP :
			  Action:Preconditions:
			1. Resource type RT1 is associated with status type ST1.
			2. Resource type RT2 is associated with status type ST1.
			3. Resource RS1 is created under resource type RT1.
			4. Resource RS2 is created under resource type RT2.
			5. View V1 is created, selecting status types ST1, resource RS1 and resource type RT2
			(master check box) 
			  Expected Result:No Expected Result
			*/
			//234365
				
			 log4j.info("-----" +"Precondtion for test case "+gstrTCID+" starts------");
				
			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTxtStatTypeValue,
						strStatType1, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTValue[0] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType1);
				if (strSTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ST2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTxtStatTypeValue,
						strStatType2, strTxtStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTValue[1] = objST.fetchSTValueInStatTypeList(
						seleniumPrecondition, strStatType2);
				if (strSTValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType1,
						"css=input[name='statusTypeID'][value='"
								+ strSTValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResType1);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RT2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType2,
						"css=input[name='statusTypeID'][value='"
								+ strSTValue[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[1] = objRT.fetchRTValueInRTList(
						seleniumPrecondition, strResType2);
				if (strRTValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS1
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
						strResType1, strContFName, strContLName, strState,
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
			// RS2		

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource2, strAbbrv,
						strResType2, strContFName, strContLName, strState,
						strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//View
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strSTValues[] = { strSTValue[0] };
				String[] strRSValues={strRSValue[0]};
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName_1,
						strVewDescription, strViewType, true, false,
						strSTValues, false, strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navEditViewPage(seleniumPrecondition, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.selAndDeselRTInEditView(seleniumPrecondition,
						strRTValue[1], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
							
			log4j.info("~~~~~TEST-CASE" + gstrTCID + " PRECONDITION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
				
		/*
		* STEP :
		  Action:Login as RegAdmin, Navigate to Setup >> Resources screen, click on 'Edit Status Types'
		   link corresponding to resource RS1.
		  Expected Result:'Edit Resource-Level Status Types' screen is displayed.
		*/
		//234366

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Select check box corresponding to ST2 and save the page
		  Expected Result:User is returned to the 'Resource List' screen
		*/
		//234367
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selAndDeselSTInEditResLevelST(selenium,
						strSTValue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Views >> V1
		  Expected Result:Resource RS1 is displayed under resource type RT1 along with status types ST1.
  	  		Resource RS2 is displayed under resource type RT2 along with status type ST1 
		*/
		//234370
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1 };
				String[] strStatType={strStatType1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResType1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource2 };
				String[] strStatType={strStatType1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResType2, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Navigate to Setup >> Resource, change the resource type RT1 to RT2 of resource RS1 and save 
		  Expected Result:'Resources List' page is displayed.
		*/
		//234371
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(selenium,
						strResource1, strAbbrv, strResType2, strStandResType,
						false, false, "", "", false, "", "", strState, "",
						strCountry, "", "FN", "LN", "", "", "", "", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Views >> V1
		  Expected Result:Resource RS1 is displayed under resource type RT2 along with status types ST1.
  	  		Resource RS2 is displayed under resource type RT2 along with status type ST1 
		*/
		//234372
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1 ,strResource2};
				String[] strStatType={strStatType1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResType2, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Navigate to Setup >> Resource, change the resource type RT2 to RT1 of resource RS1 and save 
		  Expected Result:'Resources List' page is displayed.
		*/
		//234861
				
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(selenium,
						strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResource_FillAllFields(selenium,
						strResource1, strAbbrv, strResType1, strStandResType,
						false, false, "", "", false, "", "", strState, "",
						strCountry, "", "FN", "LN", "", "", "", "", "", "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.saveAndNavRSList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to Views >> V1
		  Expected Result:Resource RS1 is displayed under resource type RT1 along with status types ST1.
  	  		Resource RS2 is displayed under resource type RT2 along with status type ST1 
		*/
		//234862
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource1 };
				String[] strStatType={strStatType1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResType1, strResources, strStatType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String[] strResources = { strResource2 };
				String[] strStatType={strStatType1};
				strFuncResult = objViews.checkAllSTRTAndRSInUserView(selenium,
						strResType2, strResources, strStatType);
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
			gstrTCID = "FTS-40194";
			gstrTO = "Add status type ST2 to resource RS1 at resource level under resource type RT1, where:" +
					"1. ST1 is associated with resource types RT1 and RT2" +
					"2. RS2 is created under resource type RT2" +
					"3. View V1 is created selecting RS1, ST1 and RT2 (master check box)Change the " +
					"resource RS1's resource type and verify that the changes are reflected on the view ";
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
	   //end//testFTS40194//
// start//testFTS40080//
	/*****************************************************************************************
	 * 'Description   : Associate multi status type MST to a resource at the resource level and
	 *                update the status of MST. Verify that the resource icon is displayed with
	 *                  the updated status color on the Map screen.
	 * 'Precondition  : 
	 * 'Arguments     :None 
	 * 'Returns       :None 
	 * 'Date          :9/10/2013
	 * 'Author        :QSG
	 * '--------------------------------------------------------------------------------------
	 * 'Modified Date                                                        Modified By 
	 * 'Date                                                                 Name
	 ****************************************************************************************/

	@Test
	public void testFTS40080() throws Exception {
		boolean blnLogin = false;
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Views objViews = new Views();
		CreateUsers objCreateUsers = new CreateUsers();
		String strFuncResult = "";
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		ViewMap objViewMap = new ViewMap();
		try {
			gstrTCID = "40080"; // Test Case Id
			gstrTO = " Associate multi status type MST to a resource at the resource level and update" +
					" the status of MST. Verify that the resource icon is displayed with the " +
					"updated status color on the Map screen.";//TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName1 = "AutoST1" + strTimeText;
			String statTypeName2 = "AutoMST" + strTimeText;
			String strStatTypDefn = "Automation";
			String strStatTypeColor = "Red";
			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;
			String strStatusValue[] = new String[2];
			String strStatusTypeValues[] = new String[2];

			String strResrcTypName = "RT" + strTimeText;
			String strRsTypeValues[] = new String[1];
			String strResourceA = "AutoRs1_A" + strTimeText;
			String strContLName = "qsg";
			String strState = "Texas";
			String strCountry = "Bell County";
			String strContFName = "auto";
			String strStandResType = "Aeromedical";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[1];
			String strRolesName = "AutoRol1" + strTimeText;
			
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strUsrFulName1 = "FN" + strUserName1;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Summary Plus (Resources as rows. Status types and comments as columns.)";
			
		/*
		 * STEP : Action:Preconditions: <br> <br>1. Resource type RT is
		 * associated with status type ST (other than multi status type
		 * MST). <br>2. Resource RS is created with address under resource
		 * type RT. <br>3. MST is not associated with any resource/resource
		 * type. <br>4. Create a view V1, selecting status types ST, MST and
		 * resource RS. <br>5. Create a Role R1 <br>5. Create user U1
		 * selecting only the following rights: <br>a. 'Setup Resource
		 * Types' <br>b. 'Setup Resources' <br>Update status on the resource
		 * RS and Role to update the status. Expected Result:No Expected
		 * Result
		 */
		// 234232

			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " starts----------");

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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ST1
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue,
						statTypeName1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			String strMSTValue = "Multi";
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statTypeName2);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strExpHr = "00";
			String strExpMn = "05";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statTypeName2, strStatusName1, strMSTValue,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.createSTWithinMultiTypeSTExpTime(seleniumPrecondition,
								statTypeName2, strStatusName2, strMSTValue,
								strStatTypeColor, strExpHr, strExpMn, "", "",
								true);
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

			// Resource Type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes
						.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResrcTypName,
						"css=input[name='statusTypeID'][value='"
								+ strStatusTypeValues[0] + "']");
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
			// Resources
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// /RSa
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResourceA, strAbbrv,
						strResrcTypName, true, strContFName, strContLName,
						strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
						strResourceA);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
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
				String[] strSTvalue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, true,
						false, strSTvalue, false, strRSValue);
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
				String[] updateRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				String[][] strRoleRightss = {};
				String[] strViewRightValue = { strStatusTypeValues[0],
						strStatusTypeValues[1] };
				strFuncResult = objRoles.CreateRoleWithAllFieldsCorrect(
						seleniumPrecondition, strRolesName, strRoleRightss,
						strViewRightValue, true, updateRightValue, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String strRoleValue = "";

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);
				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// User
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
						strConfirmPwd, strUsrFulName1);
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
						seleniumPrecondition, strResourceA, strRSValue[0],
						false, true, false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpResrcTyp"),
								true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.Advoptn.SetUPResources"),
								true);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j
					.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case " + gstrTCID
					+ " ends----------");
		/*
		 * STEP : Action:Login as user U1 and navigate to Setup >> Resources
		 * screen, click on 'Edit Status Types' link corresponding to
		 * resource RS1 Expected Result:'Edit Resource-Level Status Types'
		 * screen is displayed.
		 */
		// 234331
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditRTInResourcePage(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select check box corresponding to MST and save the
		 * page Expected Result:User is returned to the 'Resource List'
		 * screen
		 */
		// 234333
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strStatusTypeValues[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		// saveAndNavRSList
		/*
		 * STEP : Action:Navigate to Views >> V1 and update the status of
		 * MST (with the status value and color selected for the status)
		 * Expected Result:No Expected Result
		 */
		// 234335
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUpdateStatusByKey(selenium,
						strResourceA);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			String[] strStatusTypeNames = { statTypeName1, statTypeName2 };
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.verifySTInUpdateSTScreen(selenium,
						strStatusTypeNames);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[0], strStatusTypeValues[1], false, "",
						"");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Views >> Map Expected Result:No
		 * Expected Result
		 */
		// 234337
			try {
				assertEquals("", strFuncResult);
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName2 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResourceA, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
	/*
	 * STEP : Action:Select MST from the 'Status Type' drop down.
	 * Expected Result:Resource icon displayed on the Map screen is
	 * colored with the particular updated status color of MST
	 */
	// 234340
			String strTestData[] = new String[16];
			try {

				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName1 + "/" + strInitPwd + ""
						+ strUserName + "/" + strInitPwd;
				strTestData[3] = statTypeName1 + statTypeName2;
				strTestData[4] = strViewName;
				strTestData[5] = "Verify from 6th step";
				strTestData[6] = strResourceA;
				strTestData[13] = strStatTypeColor;
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");
			} catch (AssertionError ae) {
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
			gstrTCID = "FTS-40080";
			gstrTO = "Associate multi status type MST to a resource at the resource level and update the status of MST. Verify that the resource icon is displayed with the updated status color on the Map screen.";
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

	// end//testFTS40080//
}
