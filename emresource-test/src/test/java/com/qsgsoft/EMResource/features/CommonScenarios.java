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

/*************************************************************************
' Description :This class includes Common Scenarios requirement test cases
' Date		  :13-June-2012
' Author	  :QSG
'-------------------------------------------------------------------------
' Modified Date                                               Modified By
' <Date>                           	                          <Name>
'*************************************************************************/

public class CommonScenarios  {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.CommonScenarios");
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

		try {
			selenium.close();
		} catch (Exception e) {

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
		
	/***************************************************************************************
	'Description	:Visibility of the status type can be changed only by system admin users.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-June-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                                                             Modified By
	'21-Aug-2012                                                               <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS48711() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();	
		try {
			gstrTCID = "BQS-48711 ";
			gstrTO = "Visibility of the status type can be changed only by system admin users.";
			gstrResult = "FAIL";
			gstrReason = "";
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			String strLoginUserName=rdExcel.readData("Login", 3, 1);
			String strLoginPassword=rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);			
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			
			String strUserName = "";
			String strInitPwd = "";
			String strConfirmPwd = "";
			String strUsrFulName = "";
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			//Number Status Type
			String strNumStatusTypeValue="Number";
			String statNumTypeName="AutoNST_"+strTimeText;
			String strNumStatTypDefn="Auto";
			
			
			//Text Status Type
			String strTextStatusTypeValue="Text";
			String statTextTypeName="AutoTST_"+strTimeText;
			String strTextStatTypDefn="Auto";
			
			
			//Multi Status Type
			String strMultiStatusTypeValue="Multi";
			String statMultiTypeName="AutoMST_"+strTimeText;
			String strMultiStatTypDefn="Auto";
			
			
			//Saturation Status Type
			String strSaturationStatusTypeValue="Saturation Score";
			String statSaturationTypeName="AutoSST_"+strTimeText;
			String strSaturationStatTypDefn="Auto";
						
		/*
		 * 1 Login as RegAdmin and navigate to Setup>>Status types No
		 * Expected Result
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
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}	
		/*
		 * 2 Select to create a new status type "Select Status Type" screen
		 * is displayed
		 */
		
		
		/*
		 * 3 Select "Number" from the "Status Type" drop-down and click on
		 * "Next" Options under "Status Type Visibility" are enabled in the
		 * "Create Number Status Type" screen.
		 * 
		 * By default option
		 * "Users with appropriate roles within the region may view or update this status type"
		 * is selected.
		 */
		
		/*
		 * 4 Provide mandatory data, select any option for the
		 * "Status Type Visibility" and save the status type NST NST is
		 * listed in the "Status Type List" screen
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strNumStatusTypeValue, statNumTypeName,
								strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
		/*
		 * 5 Select to create a new status type, select "Multi" from the
		 * "Status Type" drop-down and click on "Next" Options under
		 * "Status Type Visibility" are enabled in the
		 * "Create Multi Status Type" screen.
		 * 
		 * By default option
		 * "Users with appropriate roles within the region may view or update this status type"
		 * is selected.
		 */
		
		/*
		 * 6 Provide mandatory data, select any option for the
		 * "Status Type Visibility" and save the status type MST MST is
		 * listed in the "Status Type List" screen
		 */
			//Text, Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strTextStatusTypeValue, statTextTypeName,
								strTextStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * 7 Select to create a new status type, select "Text" from the
		 * "Status Type" drop-down and click on "Next" Options under
		 * "Status Type Visibility" are enabled in the
		 * "Create Text Status Type" screen.
		 * 
		 * By default option
		 * "Users with appropriate roles within the region may view or update this status type"
		 * is selected.
		 */
		
		/*
		 * 8 Provide mandatory data, select any option for the
		 * "Status Type Visibility" and save the status type TST TST is
		 * listed in the "Status Type List" screen
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectSTAndFilMandFldsWithSTVisibility(
						selenium, strMultiStatusTypeValue, statMultiTypeName,
						strMultiStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		/*
		 * 9 Select to create a new status type, select "Saturation Score"
		 * from the "Status Type" drop-down and click on "Next" Options
		 * under "Status Type Visibility" are enabled in the
		 * "Create Saturation Score Status Type" screen.
		 * 
		 * By default option
		 * "Users with appropriate roles within the region may view or update this status type"
		 * is selected.
		 */
		
		/*
		 * 10 Provide mandatory data, select any option for the
		 * "Status Type Visibility" and save the status type SST SST is
		 * listed in the "Status Type List" screen
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strSaturationStatusTypeValue,
								statSaturationTypeName,
								strSaturationStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Number Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statNumTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statNumTypeName, strNumStatusTypeValue, true,
						false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statNumTypeName, strNumStatusTypeValue, true,
						true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statNumTypeName, strNumStatusTypeValue, true,
						false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(selenium,
						statNumTypeName, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Text Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statTextTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statTextTypeName, strTextStatusTypeValue,
						true, false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statTextTypeName, strTextStatusTypeValue,
						true, true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statTextTypeName, strTextStatusTypeValue,
						true, false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(selenium,
						statTextTypeName, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Multi Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statMultiTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statMultiTypeName, strMultiStatusTypeValue,
						true, false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statMultiTypeName, strMultiStatusTypeValue,
						true, true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statMultiTypeName, strMultiStatusTypeValue,
						true, false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(selenium,
						statMultiTypeName, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Saturation Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statSaturationTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statSaturationTypeName,
						strSaturationStatusTypeValue, true, false,
						strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statSaturationTypeName,
						strSaturationStatusTypeValue, true, true,
						strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statSaturationTypeName,
						strSaturationStatusTypeValue, true, false,
						strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.selectVisibiltyValue(selenium,
						statSaturationTypeName, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}			
		/*
		 * STEP 3:Click on 'Create new user'.<-> 'Create New User' page is
		 * displayed.
		 */
			
			try {
				assertEquals("", strFuncResult);
				
				// Data for creating user
				strUserName = "auto" + System.currentTimeMillis();
				//String strTempUser1=strUserName;
				
				strInitPwd = rdExcel.readData("Login", 9, 2);
				strConfirmPwd = strInitPwd;
				strUsrFulName = strUserName + "fulName";

				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult
						+ "'Users List' page is NOT displayed. ";
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						selenium, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
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
		 * 18 Login as any user with "Setup Status Types" right and navigate
		 * to "Setup>>Status Types" No Expected Result
		 */
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		/*
		 * 19 Select to create a new status type, select "Number" from the
		 * "Status Type" drop-down and click on "Next" Options under
		 * "Status Type Visibility" are disabled in the
		 * "Create Number Status Type" screen.
		 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
				
				strFuncResult = objStatusTypes.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Number ,Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				
				strNumStatusTypeValue="Number";
				statNumTypeName="AutoNST_"+strTimeText;
				strNumStatTypDefn="Auto";
				
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strNumStatusTypeValue, statNumTypeName,
								strNumStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				strTextStatusTypeValue="Text";
				statTextTypeName="AutoTST_"+strTimeText;
				strTextStatTypDefn="Auto";
				
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strTextStatusTypeValue, statTextTypeName,
								strTextStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				strMultiStatusTypeValue="Multi";
				statMultiTypeName="AutoMST_"+strTimeText;
				strMultiStatTypDefn="Auto";
				
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strMultiStatusTypeValue, statMultiTypeName,
								strMultiStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Saturation ,Fill mandatory fields

			try {
				assertEquals("", strFuncResult);
				
				strSaturationStatusTypeValue="Saturation Score";
				statSaturationTypeName="AutoSST_"+strTimeText;
				strSaturationStatTypDefn="Auto";
				
				strFuncResult = objStatusTypes
						.selectSTAndFilMandFldsWithSTVisibility(selenium,
								strSaturationStatusTypeValue, statSaturationTypeName,
								strSaturationStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Number Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statNumTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statNumTypeName, strNumStatusTypeValue, false,
						false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statNumTypeName, strNumStatusTypeValue, false,
						true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statNumTypeName, strNumStatusTypeValue, false,
						false, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			//Text Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statTextTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statTextTypeName, strTextStatusTypeValue, false,
						false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statTextTypeName, strTextStatusTypeValue, false,
						true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statTextTypeName, strTextStatusTypeValue, false,
						false, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Multi Status types
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statMultiTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statMultiTypeName, strMultiStatusTypeValue, false,
						false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statMultiTypeName, strMultiStatusTypeValue, false,
						true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statMultiTypeName, strMultiStatusTypeValue, false,
						false, strVisiltyValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Saturation Status types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.editStatusTypesMandFlds(
						selenium, statSaturationTypeName, "", "", false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "SHARED";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statSaturationTypeName, strSaturationStatusTypeValue, false,
						false, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "REGION_ONLY";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statSaturationTypeName, strSaturationStatusTypeValue, false,
						true, strVisiltyValue, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String strVisiltyValue = "PRIVATE";
				strFuncResult = objStatusTypes.StatusTypesDefaultVisibility(
						selenium, statSaturationTypeName, strSaturationStatusTypeValue, false,
						false, strVisiltyValue, true);
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
			gstrTCID = "BQS-48711";
			gstrTO = "Visibility of the status type can"
					+ " be changed only by system admin users.";
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

	
