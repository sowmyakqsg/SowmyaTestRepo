package com.qsgsoft.EMResource.features;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.EventSetup;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Preferences;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.ViewMap;
import com.qsgsoft.EMResource.shared.Views;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;


public class UserAssgnForViewResAndStatTypes {

	//Log4j object to write log entries to the Log files
	static Logger log4j = Logger.getLogger("com.qsgsoft.EMResource.features.UserAssgnForViewResAndStatTypes");
	
	static {
		BasicConfigurator.configure();
	}
	//Objects to access the common functions
	OfficeCommonFunctions objOFC;
		ReadData objrdExcel;

	/*Global variables to store the test case details � TestCaseID, Test Objective,Result,
	Reason for failure */
	String gstrTCID, gstrTO, gstrResult, gstrReason;

	//Selenium Object
	Selenium selenium,seleniumFirefox,seleniumPrecondition;

	//Object for date time settings
	Date_Time_settings dts = new Date_Time_settings();	

	public Properties propElementDetails; //Property variable for ElementID file
	public Properties propEnvDetails;//Property variable for Environment data
	public Properties propPathDetails; //Property variable for Path information
	public Properties propAutoITDetails;//Property variable for AutoIT file details
	public static Properties browserProps = new Properties();
	public static String gstrBrowserName;//Variable to store browser name
	public static String gstrTimetaken, gstrdate, gstrtime, gstrBuild;//Result Variables
	@SuppressWarnings("unused")
	private String browser;
	double gdbTimeTaken; //Variable to store the time taken

	public static Date gdtStartDate;// Date variable

	@SuppressWarnings("unused")
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime=0;
	public static String gstrTimeOut="";
	public static String sessionId_FF36,sessionId_FF20,sessionId_FF3,sessionId_FF35,sessionId_IE6,sessionId_IE7,
	    sessionId_IE8,session_SF3,session_SF4,session_op9,session_op10,session_GC,StrSessionId="",StrSessionId1,StrSessionId2;
	/****************************************************************************************************************
		* This function is called the setup() function which is executed before every test.
		*
		* The function will take care of creating a new selenium session for every test
		*
	****************************************************************************************************************/
	@Before
	public void setUp() throws Exception {

		//Create object to read environment properties file
		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();
		//Retrieve browser information
		browser=propEnvDetails.getProperty("Browser");
		gstrBrowserName=propEnvDetails.getProperty("Browser").substring(1)+" "+propEnvDetails.getProperty("BrowserVersion");
		//Retrieve the value of page load time limit
		gstrTimeOut=propEnvDetails.getProperty("TimeOut");
		//create an object to refer to Element ID properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();
		//Create a new selenium session
		selenium = new DefaultSelenium(propEnvDetails.getProperty("Server"),
			4444, propEnvDetails.getProperty("Browser"), propEnvDetails
			.getProperty("urlEU"));
		
		seleniumFirefox = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444,propEnvDetails.getProperty("BrowserFirefox"), propEnvDetails
				.getProperty("urlEU"));
		

		selenium.start();
		selenium.windowMaximize();
		
		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
						.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		
		//Define object to call support functions to read excel, date etc
		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	
	@After
	public void tearDown() throws Exception {
		
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		
		seleniumPrecondition.stop();
		
		try{
			selenium.close();
		}catch(Exception e){
			
		}
		
		selenium.stop();
		
		try{
			seleniumFirefox.close();
		}catch(Exception e){
			
		}
		
		seleniumFirefox.stop();
		
	
		// determine log message
		if (gstrResult.toUpperCase().equals("PASS"))
		{
			log4j.info("-------------------Test Case Execution " + gstrTCID
			+ " has PASSED------------------");
		} else if (gstrResult.toUpperCase().equals("SKIP"))
		{
			log4j.info("-------------------Test Case Execution " + gstrTCID
			+ " was SKIPPED------------------");
		} else
		{
			log4j.info("-------------------Test Case Execution " + gstrTCID
							+ " has FAILED------------------");
		}

		//Retrieve the path of the Result file
		String FILE_PATH = "";
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		FILE_PATH = pathProps.getProperty("Resultpath");
		// Retrieve the total execution time
		gdbTimeTaken = objOFC.TimeTaken(gdtStartDate);
		Date_Time_settings dts = new Date_Time_settings();
		//Get the current date
		gstrdate = dts.getCurrentDate(selenium, "yyyy-MM-dd");
		//Get the Build ID of the application
		gstrBuild = propEnvDetails.getProperty("Build");
		//Check if result should be written to Excel or Test Management Tool
		String blnresult = propEnvDetails.getProperty("writeresulttoexcel/DB");
		boolean blnwriteres = blnresult.equals("true");

		gstrReason=gstrReason.replaceAll("'", " ");
		//Write Result of the test.
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
						gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetaken, gstrdate,
						sysDateTime, gstrBrowserName, gstrBuild,StrSessionId);

	}
	/********************************************************************************
	'Description	:For a resource, verify that status types can be refined for a user.
	
	'Precondition	:1. Role-based status types ST1 and ST2 are created selecting role R1 under "Roles with view rights" section.
				2. ST1 and ST2 are associated with resource type RT.
				3. Resources RS1 and RS2 are created under resource type RT with address.
				
				4. View V1 has status type ST1, ST2 and resource RS1 and RS2.
				5. Event Template ET is created with ST1, ST2 and RT.
				6. Event E1 is created under ET selecting RS1 and RS2.
				7. Status types ST1 and ST2 are under status type section S1.
				
				8. User U1 has the following rights:
				a) Role R1
				b) "View Resource" right on resource RS
				c) View Custom View
				d) Edit Status Change Notification Preferences
				
				9. User U1 has added resources RS1, RS2 and status types ST1 and ST2 to his/her custom view. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:29-10-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	
	@Test
	public void testBQS102572() throws Exception {

		boolean blnLogin = false;
		String strFailMsg ="";
	
		Login objLogin = new Login();// object of class Login
		Views objViews=new Views();
		Preferences objPreferences = new Preferences();	
		EventSetup objEventSetup = new EventSetup();
		ViewMap objViewMap=new ViewMap();
		Paths_Properties objAP = new Paths_Properties();
		Properties propPathDetails = objAP.Read_FilePath();
		try {
			gstrTCID = "BQS-102572";
			gstrTO = "For a resource, verify that status types can be refined for a user.";
					
			gstrResult = "FAIL";
			gstrReason = "";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL

			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j
					.info("~~~~~TEST CASE "+gstrTCID+" EXECUTION STATRTS~~~~~");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetaken = dts.timeNow("HH:mm:ss");
			
			String strTmText = dts.getCurrentDate("HHmm");
			ResourceTypes objRT=new ResourceTypes();
			Resources objRs=new Resources();
			ReadData objReadData = new ReadData (); 
		
			StatusTypes objST=new StatusTypes();
		
			CreateUsers objCreateUsers=new CreateUsers();
		
			String strTimeText=dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			Roles objRole=new Roles();
		
			String strResource1="AutoRs1_"+strTimeText;
			String strResource2="AutoRs2_"+strTimeText;
					
			String strNSTValue="Number";
		
			String strStatType1="Aa1_"+strTimeText;
			String strStatType2="Aa2_"+strTimeText;
				
			String strUserName_Any = "AutoUsr_Any" + System.currentTimeMillis();
			String strUsrFulName_Any = strUserName_Any;
		
			String strTempName = "ET" + System.currentTimeMillis();
			String strTempDef = "Automation";
			String strETValue = "";

			String strEveName = "Event" + System.currentTimeMillis();
			String strInfo = "Automation";
			String strEventValue = "";
			
			String []strStatTypeArr={strStatType1,strStatType2};
			String strLoginUserName = objReadData.readData("Login", 3, 1);
			String strLoginPassword =objReadData.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);
			String strStatTypDefn="Auto";
			
			String strRSTvalue[]=new String[2];
		
			String strStatValue="";
		
			String strResrctTypName="AutoRt_"+strTimeText;
			String strRTValue[]=new String[1];
			String strRSValue[]=new String[2];
			String strAbbrv = "A" + strTmText;
			String strResVal="";
			
			String strStandResType="Aeromedical";
			String strContFName="auto";
			String strContLName="qsg";
			
			String strUserName="AutoUsr"+System.currentTimeMillis();
			String strInitPwd=objrdExcel.readData("Login", 4, 2);
			String strConfirmPwd=objrdExcel.readData("Login", 4, 2);
			String strUsrFulName="Full"+strUserName;
			String[] strRoleValue=new String[1];
			
			String strRoleName="AutoR_"+strTimeText;
			String strRoleRights[][]={};
			String strSection="AB_"+strTimeText;
			
			String strViewName="AutoV_"+strTimeText;
			
			String strVewDescription="";
			String strViewType="Summary Plus (Resources as rows. Status types and comments as columns.)";
			String strSectionValue = "";
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRole.navRolesListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			String strUpdRts[]={};
			String strViewRts[]={};		
			try {
				assertEquals("", strFailMsg);
			
				strFailMsg = objRole.CreateRoleWithAllFields(seleniumPrecondition, strRoleName, strRoleRights, strViewRts, true, strUpdRts, true, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);
				strRoleValue[0] = objRole.fetchRoleValueInRoleList(seleniumPrecondition, strRoleName);
				
				if(strRoleValue[0].compareTo("")!=0){
					strFailMsg="";
					
				}else{
					strFailMsg="Failed to fetch Role value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNSTValue, strStatType1, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String[][] strRoleViewRts={{strRoleValue[0],"true"}};
				String[][]strRlUpdRts={};
				strFailMsg = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition, false, false, strRoleViewRts, strRlUpdRts, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strStatType1);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[0]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objST.selectStatusTypesAndFilMandFlds(seleniumPrecondition, strNSTValue, strStatType2, strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String[][] strRoleViewRts={{strRoleValue[0],"true"}};
				String[][]strRlUpdRts={};
				strFailMsg = objST.slectAndDeselectRoleInSTNew(seleniumPrecondition, false, false, strRoleViewRts, strRlUpdRts, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strStatValue = objST.fetchSTValueInStatTypeList(seleniumPrecondition, strStatType2);
				if(strStatValue.compareTo("")!=0){
					strFailMsg="";
					strRSTvalue[1]=strStatValue;
				}else{
					strFailMsg="Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}	
						
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.resrcTypeMandatoryFlds(seleniumPrecondition, strResrctTypName, "css=input[name='statusTypeID'][value='"+strRSTvalue[0]+"']");
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			for(int intST=0;intST<strRSTvalue.length;intST++){
				try {
					assertEquals("", strFailMsg);
				
					strFailMsg = objRs.selAndDeselSTInEditResLevelST(seleniumPrecondition, strRSTvalue[intST], true);			
	
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}		
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.saveAndvrfyResType(seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRT.fetchResTypeValueInResTypeList(seleniumPrecondition, strResrctTypName);
				if (strFailMsg.compareTo("") != 0) {
					
					strRTValue[0] = strFailMsg;
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch resource type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource1, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource1);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[0]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFailMsg = objRs.createResourceWitLookUPadres(seleniumPrecondition, strResource2, strAbbrv, strResrctTypName, strContFName, strContLName, strState, strCountry, strStandResType);
					

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			try {
				assertEquals("", strFailMsg);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource2);
				
				if(strResVal.compareTo("")!=0){
					strFailMsg="";
					strRSValue[1]=strResVal;
				}else{
					strFailMsg="Failed to fetch Resource value";
				}
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.navUserListPge(seleniumPrecondition);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
				
				
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition, strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue[0], true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource1, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.selectResourceRights(seleniumPrecondition, strResource2, false, false, false, true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.CustomView"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition, propElementDetails.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer"), true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}			
			
			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUser(seleniumPrecondition, strUserName);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			//User with user set up account right
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_Any, strInitPwd, strConfirmPwd, strUsrFulName_Any);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.Advoptn.SetUPResources");
				strFailMsg = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
								
				strFailMsg = objCreateUsers.savVrfyUser(seleniumPrecondition, strUserName_Any);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navRegionViewsList(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				
				strFailMsg = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType,
						true, false, strRSTvalue, false, strRSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumFirefox, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumFirefox, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try{
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navRegionViewsList(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFailMsg;
			}

			try{
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navToEditResDetailViewSections(seleniumFirefox);
			}
			catch (AssertionError Ae)
			{
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.dragAndDropSTtoSection(seleniumFirefox, strStatTypeArr, strSection, true);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}		
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews
						.navToEditResDetailViewSections(seleniumFirefox);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strSectionValue = objViews
						.fetchSectionID(seleniumFirefox, strSection);
				if (strSectionValue.compareTo("") != 0) {
					strFailMsg = "";
				} else {
					strFailMsg = "Failed to fetch section value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(seleniumFirefox);
				
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			/* 5. Event Template ET is created with ST1, ST2 and RT. */
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				String[] strResTypeValue = { strRTValue[0] };
				String[] strStatusTypeVal = { strRSTvalue[0], strRSTvalue[1] };
				strFailMsg = objEventSetup.CreateETBySelctngRTAndST(
						seleniumPrecondition, strTempName, strTempDef, true,
						strResTypeValue, strStatusTypeVal);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);

				strETValue = objEventSetup.fetchETInETList(seleniumPrecondition,
						strTempName);
				if (strETValue.compareTo("") != 0) {
					strFailMsg = "";
				} else {
					strFailMsg = "Function failes to fetch ETY Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			/*6. Event E1 is created under ET selecting RS. */
			

			try {
				assertEquals("", strFailMsg);
				strFailMsg=objEventSetup.navToEventManagementNew(seleniumPrecondition);
			} catch (AssertionError Ae) {

				gstrReason = strFailMsg;
			}
			try {

				assertEquals("", strFailMsg);

				strFailMsg = objEventSetup.createEvent(seleniumPrecondition,
						strTempName, strResource1, strEveName, strInfo, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {

				assertEquals("", strFailMsg);

				strFailMsg = objEventSetup.slectAndDeselectRSEditEventPage(seleniumPrecondition, strResource2, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strEventValue = objEventSetup.fetchEventValueInEventList(
						seleniumPrecondition, strEveName);
				if (strEventValue.compareTo("") != 0) {
					strFailMsg = "";
				} else {
					strFailMsg = "Function failes to fetch Event Value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				blnLogin = true;
				strFailMsg = objPreferences.navEditCustomViewPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			

			String strRS[] = { strResource1,strResource2 };
			for(int intR=0;intR<strRS.length;intR++){
				try {
					assertEquals("", strFailMsg);		
					
					strFailMsg = objPreferences.findResourcesAndAddToCustomView(selenium, strRS[intR], "(Any)", "", "",  "(Any)");
				} catch (AssertionError Ae) {
					gstrReason = strFailMsg;
				}
			}
			

			try {
				assertEquals("", strFailMsg);
		
				strFailMsg = objPreferences.navEditCustomViewOptionPage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			try {
				assertEquals("", strFailMsg);
	
				String strSTValue[][] = {{ strRSTvalue[0], "true" }, 
						{ strRSTvalue[1], "true" } };
				strFailMsg = objPreferences.addSTInEditCustViewOptionPage(
						selenium, strSTValue, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertEquals("", strFailMsg);
				strFailMsg = objLogin.logout(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
		
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName_Any,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objRs.navToAssignUsersOFRes(selenium, strResource1);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
	
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.navToRefineVisibleST(selenium,
						strUsrFulName);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
						
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strRSTvalue[0], false);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objCreateUsers.saveChangesInRefineSTAndVerifyAssignUsr(selenium,strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);
			
				strFailMsg = objRs.savAndVerifyEditRSLevelPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertTrue(strFailMsg.equals(""));	
			
				strFailMsg = objLogin.login(selenium, strUserName,
						strConfirmPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
					
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						strStatType1, false, strResource1,1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						strStatType1, true, strResource2,1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 6 Navigate to View>>Custom N/A is displayed under status type ST1
			 * column for resource RS1 and not for resource RS2.
			 */
			

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.navViewCustomTableOrCustmViewMap(
						selenium, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.chkSTAssoOrNotInCustomViewScreen(selenium,
						strStatType1, false, strResource1,1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.chkSTAssoOrNotInCustomViewScreen(selenium,
						strStatType1, true, strResource2,1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			

			/*
			 * 7 Navigate to the map view of custom view Only ST2 is displayed
			 * in the resource pop up window of RS1.
			 * 
			 * Both ST1 and ST2 are displayed in the resource pop up window of
			 * RS2.
			 */
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.navViewCustomTableOrCustmViewMap(
						selenium, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				String[] strEventStatType = {};
				String[] strRoleStatType = {strStatType1};
				strFailMsg = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				String[] strEventStatType = {};
				String[] strRoleStatType = {strStatType2};
				strFailMsg = objViewMap
						.verifyStatTypesInResourcePopup_ShowMap(selenium,
								strResource1, strEventStatType,
								strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				String[] strEventStatType = {};
				String[] strRoleStatType = { strStatType1 };
				strFailMsg = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				String[] strEventStatType = {};
				String[] strRoleStatType = { strStatType2 };
				strFailMsg = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						strStatType1, false, strResource1,1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.chkSTAssoOrNotInViewScreenNew(selenium,
						strStatType1, true, strResource2,1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 10 Click on the name of resource RS1 Only ST2 is displayed on the
			 * 'View Resource Detail' screen under section S1.
			 */

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.navToViewResourceDetailPage(selenium,
						strResource1);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertEquals("", strFailMsg);

				String[] strStatTypep = { strStatType1 };
				strFailMsg = objViews.verifySTInViewResDetailNew(selenium,
						strSection, strSectionValue, strStatTypep);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("The Status Type is NOT displayed on "
								+ "the view resource detail screen. ", strFailMsg);

				String[] strStatTypep = { strStatType2 };
				strFailMsg = objViews.verifySTInViewResDetailNew(selenium,
						strSection, strSectionValue, strStatTypep);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			/*
			 * 11 Click on the event banner of E1, click on the name of resource
			 * RS2 Both ST1 and ST2 are displayed on the 'View Resource Detail'
			 * screen under section S1.
			 */
			
			
			try {
				assertEquals("", strFailMsg);
				strFailMsg = objViewMap.navEventStatusPage(selenium, strEveName);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			

			try {
				assertEquals("", strFailMsg);

				strFailMsg = objViews.navToViewResourceDetailPage(selenium,
						strResource2);

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			try {
				assertEquals("", strFailMsg);

				String[] strStatType = { strStatType1,strStatType2 };
				strFailMsg = objViews.verifySTInViewResDetailNew(selenium,
						strSection, strSectionValue, strStatType);
				
				if(strFailMsg.equals("The Status Type is NOT displayed on "
								+ "the view resource detail screen. ")){
					strFailMsg="";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objPreferences
						.navMyStatusTypeChangePreference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			// add res data
			String strSearchCategory = "(Any)";
			String strRegion = "";
			String strSearchWhere = "";
			String strSearchState = "(Any)";
			try {
				assertEquals("", strFailMsg);

				strFailMsg = objPreferences.navTOFindResourcesPage(selenium,
						strResource1, strSearchCategory, strRegion,
						strSearchWhere, strSearchState, strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			
			
			
			try {
				assertEquals("", strFailMsg);
				String strArST[] = { strStatType2, strStatType1 };
				strFailMsg = objPreferences.verifySTInSectionInEditMySTPrfPageNew(
						selenium, strSectionValue, strSection, strArST);

				if (strFailMsg.equals(strStatType1
						+ " is NOT displayed under section " + strSection + " "
						+ "in the 'Edit My Status Change Preferences' screen.")) {
					strFailMsg = "";

				}
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			try {
				assertEquals("", strFailMsg);

				gstrResult = "PASS";

				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, strUserName + "/" + strInitPwd,
						strStatType1 + "," + strStatType2, strViewName,
						"From 14th Step to 18",
						strResource1 + "," + strResource2, strSection,
						strEveName };

				String strWriteFilePath = propPathDetails
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Views");

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-102572";
			gstrTO = "For a resource, verify that status types can be refined for a user.";
					
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
			strFailMsg = objLogin.logout(selenium);

			try {
				assertEquals("", strFailMsg);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		}
	}
}
