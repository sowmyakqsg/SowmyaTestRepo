package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.Views;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/**********************************************************************
' Description :This class includes test cases to Create Saturation Score Status types
' Precondition:
' Functions: testBQS66950()
' Date		  :03-May-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/
public class CreateSaturationScoreStatType {

	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.CreateSaturationScoreStatType");
	static{
		BasicConfigurator.configure();
	}
	String gstrTCID, gstrTO, gstrResult, gstrReason;	
	Selenium selenium,seleniumPrecondition;	
	double gdbTimeTaken;
	OfficeCommonFunctions objOFC;
	ReadData objrdExcel;
	public static Date gdtStartDate;
	public Properties propElementDetails;
	public static String gstrBrowserName;
	public static String gstrTimetake, gstrdate, gstrtime, gstrBuild;
	public Properties propEnvDetails,propAutoItDetails;
	public static Properties browserProps = new Properties();
	
	private String browser="";
	Properties pathProps;
	private String json;
	public static long sysDateTime;	
	public static long gsysDateTime;
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId,StrSessionId1,StrSessionId2;
	public static String gstrTimeOut="";
	
	@Before
	public void setUp() throws Exception {
		
		
		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
					
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");		
		
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		
		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {
		         
	        
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.json, propEnvDetails.getProperty("urlEU"));
			
	       
		} else {
			selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
					4444, this.browser, propEnvDetails.getProperty("urlEU"));			
		
		}		
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");

		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();		
		
	}

	@After
	public void tearDown() throws Exception {	
		
		// kill browser
		selenium.close();
		selenium.stop();
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
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
				sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);
	
	}
	

	/***********************************************************************
	'Description	:Verify that a Saturation score status type can be created.
	'Precondition	: Precondition:
						1. User 'A' has 'Setup Status types' right.
	'Arguments		:void
	'Returns		:void
	'Date	 		:3-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	************************************************************************/
	
	@Test
	public void testBQS83387()throws Exception{
		try{
			gstrTCID = "83387";			
			gstrTO = "Verify that a Saturation score status type can be created";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			
			Login objLogin = new Login();// object of class Login
			CreateUsers objUser=new CreateUsers();	
			StatusTypes objStatTyp=new StatusTypes();
			ReadData objReadData = new ReadData (); 
								
			String strStatusTypeValue="Saturation Score";
						
			String statTypeName="AutoScST_"+System.currentTimeMillis();
			String strStatTypDefn="Auto Test Status Type";
						
			String strHeader[]={"Action","Name","Type","Event Only?","Standard Type","Section","Description","Statuses"};
			String strData[]={"edit",statTypeName,"Saturation Score","No","","Uncategorized",strStatTypDefn,""};
			
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);	
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strPassword="abc123";
			String strFullUserName="Full"+strUserName;
			
			String strFailMsg = objLogin.login(seleniumPrecondition,strAdmUserName,
					strAdmPassword);

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strPassword, strPassword, strFullUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objUser.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objUser.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
						strByRole, strByResourceType, strByUserInfo, strNameFormat);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * Step 2: Login as user 'A' and navigate to Setup>>Status types <-> 'Status Type List' page is displayed. 
			 *
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strPassword);	
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objStatTyp.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			/*
			 * Step 3: Click on 'Create new status type' <-> 'Select Status Type' screen is displayed
			 * Step 4: Select 'Saturation Score' and click on 'Next' <-> 'Create Saturation Score Status Type' screen is displayed.
			 * Step 5: Fill in all the mandatory fields and 'Save' <->	Status type is listed in the 'Status Type List' screen
			 */
			try {
				assertTrue(strFailMsg.equals(""));	
				strFailMsg=objStatTyp.selectStatusTypesAndFilMandFlds(selenium, strStatusTypeValue, statTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));
			
				for(int intRec=0;intRec<strHeader.length;intRec++){
					//call the function to check appropriate values are displayed in status type list
					strFailMsg=objStatTyp.checkDataInStatusTypeListNew(selenium, strHeader[intRec], statTypeName, strData[intRec],String.valueOf(intRec+1) );
					try {
						assertTrue(strFailMsg.equals(""));	
					} catch (AssertionError Ae) {
						gstrReason = gstrReason+" "+strFailMsg;
					}
				}
				
				if(gstrReason.equals(""))
					gstrResult="PASS";
				
								
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		}catch(Exception e){
		
			Exception excReason;
			gstrTCID = "83387";
			gstrTO = "Verify that a Saturation score status type can be created";
			gstrResult = "FAIL";
			excReason = null;
	
			log4j.info(e);
			log4j.info("========== Test Case '"+gstrTCID+"' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();	
		
		}			
	}
	 /***********************************************************************
	 'Description :Verify that the option to trace the user can be selected for a text status type.
	 'Precondition :1. 'Status Change Name Trace' option is selected for Test user's region.
	     2. Test user has created a text status type 'TST'
	     3. 'TST' is selected for resource type 'RT'
	     4. Resource 'RS' is created for 'RT'
	     5. Test user has update status right on RS and role to update TST
	     6. View 'V1' is created selecting 'TST' and 'RS' 
	 'Arguments  :void
	 'Returns  :void
	 'Date    :7-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Name>                                <Name>
	 ************************************************************************/
	 
@Test
public void testBQS68665() throws Exception {

	String strFuncResult = "";
	boolean blnLogin = false;

	Login objLogin = new Login();// object of class Login
	Views objViews = new Views();
	StatusTypes objST = new StatusTypes();
	ResourceTypes objRT = new ResourceTypes();
	Resources objRs = new Resources();
	CreateUsers objCreateUsers = new CreateUsers();
	Roles objRole = new Roles();
	Views objView=new Views();
	try {
		gstrTCID = "68665";
		gstrTO = "Verify that the option to trace the user can be selected for a saturation score status type.";
		gstrReason = "";
		gstrResult = "FAIL";

		log4j.info("~~~~~TEST CASE - " + gstrTCID
				+ " EXECUTION STARTS~~~~~");

		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake = dts.timeNow("HH:mm:ss");
		String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
		
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		// Search user criteria
		String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		String strByResourceType = objrdExcel.readInfoExcel("User_Template",
				7, 12, strFILE_PATH);
		String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
				13, strFILE_PATH);
		String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
				14, strFILE_PATH);
		
		// login details
		String strUserName = "AutoUsr"+System.currentTimeMillis();
		String strPassword = "abc123";

		String strViewName = "AutoV_"+strTimeText;

		String[] strUpdatSatuValue1 ={"1","2","3","4","5","6","7","8","9"};
		String[] strUpdatSatuValue2 ={"0","1","2","3","4","5","6","7","8"};
		
		String[]strUpdatSatuValue=new String[9];
		String strUpdatdSatuValue="429";
		String[] strErrorMsg = new String[2];
	
		// login details
		String strAdmUserName = objrdExcel.readData("Login", 3, 1);
		String strAdmPassword = objrdExcel.readData("Login", 3, 2);
		String strRegn = objrdExcel.readData("Login", 3, 4);

		String strStatusTypeValue = "Saturation Score";
		String statTypeName = "AutoTSt_" + strTimeText;
		String strStatTypDefn = "Auto";
				
		String strSTvalue[] = new String[1];
		
		String strState = "Alabama";
		String strCountry = "Autauga County";

				
		String strResource = "AutoRs_" + strTimeText;
		String strResType = "AutoRt_" + strTimeText;
		
		String strStatusValues[] = new String[2];
		strStatusValues[0] = "";
		strStatusValues[1] = "";
		

		String strAbbrv = "Rs";

		String strStandResType = "Aeromedical";
		String strContFName = "auto";
		String strContLName = "qsg";
		
		String strRSValue[] = new String[1];
		

		String strRoleName = "AutoR_" + strTimeText;
		String strRoleRights[][] = {};
		String strRoleValue = "";
		String strUsrFulName = "autouser";
		
		String strVewDescription="";
		String strViewType="Summary Plus (Resources as rows. Status types and comments as columns.)";
		
	
		log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(seleniumPrecondition, strAdmUserName,
					strAdmPassword);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.navStatusTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition,
					strStatusTypeValue, statTypeName, strStatTypDefn, false);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			//select trace option
			seleniumPrecondition.click("css=input[name=trace]");
			strFuncResult = objST.savAndVerifySTNew(seleniumPrecondition, statTypeName);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
	

		try {
			assertEquals("", strFuncResult);
			String strStatValue = objST.fetchSTValueInStatTypeList(
					seleniumPrecondition, statTypeName);
			if (strStatValue.compareTo("") != 0) {
				strFuncResult = "";
				strSTvalue[0] = strStatValue;
			} else {
				strFuncResult = "Failed to fetch status type value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
					strResType, "css=input[name='statusTypeID'][value='"
							+ strSTvalue[0] + "']");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition, strResType);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.navResourcesList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
					strResource, strAbbrv, strResType, strContFName,
					strContLName, strState, strCountry, strStandResType);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strResVal = objRs.fetchResValueInResList(seleniumPrecondition,
					strResource);

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
			strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
					strRoleName, strRoleRights, strSTvalue, true,
					strSTvalue, true, true);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
					strRoleName);

			if (strRoleValue.compareTo("") != 0) {
				strFuncResult = "";

			} else {
				strFuncResult = "Failed to fetch Role value";
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		//  user
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
					strUserName, strPassword, strPassword, strUsrFulName);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.selectResourceRights(seleniumPrecondition,
					strResource, false, true, false, true);

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

			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(seleniumPrecondition, strUserName,
					strByRole, strByResourceType, strByUserInfo, strNameFormat);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
						
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objView.navRegionViewsList(seleniumPrecondition);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objView.createView(seleniumPrecondition, strViewName, strVewDescription, strViewType, true, false, strSTvalue, false, strRSValue);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
	
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
		log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
		
		/*
		 * STEP 2: Login as Test user and navigate to 'View >> V1'<-> 'V1'
		 * page is displayed.
		 */
		strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strPassword);

		try {
			assertEquals("", strFuncResult);
			blnLogin = true;

			strFuncResult = objViews.navToUserView(selenium, strViewName);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strErrorMsg = objViews.fetchUpdateStatusValue(selenium, strResType,
					statTypeName);

			strUpdatdSatuValue = strErrorMsg[0];

			if (strUpdatdSatuValue.equals("--")||strUpdatdSatuValue.equals("429")) {
				strUpdatdSatuValue = "393";
				for(int i=0;i<strUpdatSatuValue.length;i++)
					strUpdatSatuValue[i]=strUpdatSatuValue2[i];
			} else {
				strUpdatdSatuValue = "429";
				for(int i=0;i<strUpdatSatuValue.length;i++)
					strUpdatSatuValue[i]=strUpdatSatuValue1[i];
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		 * STEP 3: Click on the status cell of NST 'Update status' screen is
		 * displayed An instruction <->'Please note: You must enter your
		 * name and password below, when changing this status' is displayed
		 * below 'NST' 'User Verification' is displayed as the header below
		 * the link 'Show All Statuses' along with the instruction
		 * '*required to complete edits' corresponding to it. Fields 'Your
		 * Name' and 'Password' are available with 'Save' and 'Cancel'
		 * buttons.
		 */
		try {
			assertEquals("", strErrorMsg[1]);

			strFuncResult = objViews.navUpdateStatusByStatusCell(selenium,
					strResType,
					statTypeName);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.verifyUpdateStatusPageForElmnts(
					selenium, true, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		 * STEP 4: Enter the status value, do NOT provide name and password
		 * and click on 'Save'<-> An error message stating 'The following
		 * error occurred on this page: Name and password are required.' is
		 * displayed and the page is not saved. Provided status value is
		 * retained.
		 */

		try {
			assertEquals("", strFuncResult);
		
			strFuncResult = objViews.fillAndSavUpdateStatusSSTWithTrace(selenium,
					strUpdatSatuValue, strSTvalue[0],true,"","");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.savAndVerifyUpdateST(selenium,
					strViewName,strResType,
					statTypeName, strUpdatdSatuValue);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals(strViewName + " page is NOT displayed",
					strFuncResult);
		

			try {
				assertEquals(selenium
						.getValue("css=input[name='status_value_"
								+ strSTvalue[0] + "']"), strUpdatdSatuValue);
				log4j.info("Provided status value is retained. ");

			} catch (AssertionError Ae) {
				log4j.info("Provided status value is NOT retained. " + Ae);
				gstrReason = "Provided status value is NOT retained. " + Ae;
				strFuncResult = gstrReason;
			}

			String strErrorMeassage = "Name and password are required.";
			strFuncResult = objViews.verifyErrorMsgUpdateStatus(selenium,
					strErrorMeassage);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		 * STEP 5: Provide name and wrong password and click on 'Save' An
		 * error message stating <->'The following error occurred on this
		 * page: Incorrect password.' is displayed and the page is not
		 * saved.
		 */

		try {
			assertEquals("", strFuncResult);
		
			String strNwPassword = "abc321";

			strFuncResult = objViews.fillAndSavUpdateStatusSSTWithTrace(selenium,
					strUpdatSatuValue, strSTvalue[0],true, strUserName,
					strNwPassword);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.savAndVerifyUpdateST(selenium,
					strViewName,strResType,
					statTypeName, strUpdatdSatuValue);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals(strViewName + " page is NOT displayed",
					strFuncResult);

		
			try {
				assertEquals(selenium
						.getValue("css=input[name='status_value_"
								+ strSTvalue[0] + "']"), strUpdatdSatuValue);
				log4j.info("Provided status value is retained. ");

			} catch (AssertionError Ae) {
				log4j.info("Provided status value is NOT retained. " + Ae);
				gstrReason = "Provided status value is NOT retained. " + Ae;
				strFuncResult = gstrReason;
			}

			String strErrorMeassage = "Incorrect password.";
			strFuncResult = objViews.verifyErrorMsgUpdateStatus(selenium,
					strErrorMeassage);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		 * STEP 6: Provide incorrect name and correct password and click on
		 * 'Save' <->Status value is updated and displayed appropriately.
		 */

		try {
			assertEquals("", strFuncResult);
		
			// Invalid username and correct password
			String strIncUserName = "User"+System.currentTimeMillis();
			
			strFuncResult = objViews.fillAndSavUpdateStatusSSTWithTrace(selenium,
					strUpdatSatuValue, strSTvalue[0],true, strIncUserName,
					strPassword);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.savAndVerifyUpdateST(selenium,
					strViewName, strResType,
					statTypeName, strUpdatdSatuValue);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		 * STEP 7: Once again update the status of NST, provide appropriate
		 * name and password and click on 'Save'<-> The status value is
		 * updated and displayed on the view screen.
		 */
		try {
			assertEquals("", strFuncResult);

			strErrorMsg = objViews.fetchUpdateStatusValue(selenium, strResType,
					statTypeName);

			strUpdatdSatuValue = strErrorMsg[0];

			if (strUpdatdSatuValue.equals("429")
					|| strUpdatdSatuValue.equals("--")) {
				strUpdatdSatuValue = "393";
				for(int i=0;i<strUpdatSatuValue.length;i++)
					strUpdatSatuValue[i]=strUpdatSatuValue2[i];
			} else {
				strUpdatdSatuValue = "429";
				for(int i=0;i<strUpdatSatuValue.length;i++)
					strUpdatSatuValue[i]=strUpdatSatuValue1[i];
			}

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.navUpdateStatusByStatusCell(selenium,
					strResType,
					statTypeName);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			
			
			strFuncResult = objViews.fillAndSavUpdateStatusSSTWithTrace(selenium,
					strUpdatSatuValue, strSTvalue[0],true, strUserName,
					strPassword);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objViews.savAndVerifyUpdateST(selenium,
					strViewName,strResType,
					statTypeName, strUpdatdSatuValue);

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			gstrResult = "PASS";

		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j
				.info("----------------------------------------------------------");
	} catch (Exception e) {

		Exception excReason;
		gstrTCID = "68665";
		gstrTO = "Verify that the option to trace the user can be selected for a saturation score status type.";
				
		gstrResult = "FAIL";
		excReason = null;

		log4j.info(e);
		log4j.info("========== Test Case '" + gstrTCID
				+ "' has FAILED ==========");
		log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
		log4j
				.info("----------------------------------------------------------");
		excReason = e;
		gstrReason = excReason.toString();

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
