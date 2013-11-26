package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*************************************************************************
' Description :This class includes test cases to Create Multi Status types
' Date		  :02-May-2012
' Author	  :QSG
'-------------------------------------------------------------------------
' Modified Date                                               Modified By
' <Date>                           	                          <Name>
'*************************************************************************/
public class CreateMultiStatusType {

	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.CreateMultiStatusType");
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
	public Properties propEnvDetails, propAutoItDetails;
	public static Properties browserProps = new Properties();

	private String browser = "";
	public Properties pathProps;
	private String json;
	public static long sysDateTime;
	public static long gsysDateTime;
	public static String sessionId_FF36, sessionId_FF20, sessionId_FF3,
			sessionId_FF35, sessionId_IE6, sessionId_IE7, sessionId_IE8,
			session_SF3, session_SF4, session_op9, session_op10, session_GC,
			StrSessionId, StrSessionId1, StrSessionId2;
	public static String gstrTimeOut = "";

	@Before
	public void setUp() throws Exception {

		ReadEnvironment objReadEnvironment = new ReadEnvironment();
		propEnvDetails = objReadEnvironment.readEnvironment();

		Paths_Properties objAP = new Paths_Properties();
		pathProps = objAP.Read_FilePath();
		gstrTimeOut = propEnvDetails.getProperty("TimeOut");

		browser = propEnvDetails.getProperty("Browser");
		gstrBrowserName = propEnvDetails.getProperty("Browser").substring(1)
				+ " " + propEnvDetails.getProperty("BrowserVersion");
		// create an object to refer to properties file
		ElementId_properties objelementProp = new ElementId_properties();
		propElementDetails = objelementProp.ElementId_FilePath();

		if (propEnvDetails.getProperty("Server").equals("saucelabs.com")) {

			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.json,
					propEnvDetails.getProperty("urlEU"));

		} else {
			selenium = new DefaultSelenium(
					propEnvDetails.getProperty("Server"), 4444, this.browser,
					propEnvDetails.getProperty("urlEU"));
		}

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");
		
		selenium.start();
		selenium.windowMaximize();
		selenium.setTimeout("");

		gdtStartDate = new Date();
		objOFC = new OfficeCommonFunctions();
		objrdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
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
				sysDateTime, gstrBrowserName, gstrBuild, StrSessionId);

	}
	
	/***********************************************************************
	'Description	:Verify that a Multi status type can be created.
	'Arguments		:void
	'Returns		:void
	'Date	 		:2-May-2012
	'Author			:QSG
	'-----------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Name>                              		<Name>
	************************************************************************/	
	@Test
	public void testBQS83386()throws Exception{
		
		Login objLogin = new Login();// object of class Login
		CreateUsers objUser=new CreateUsers();
		StatusTypes objStatTyp=new StatusTypes();
		ReadData objReadData = new ReadData (); 
		
		try{
			gstrTCID = "83386";			
			gstrTO = "Verify that a Multi status type can be created";
			gstrReason = "";
			gstrResult = "FAIL";		

			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			// login details
			String strAdmUserName = objReadData.readData("Login", 3, 1);
			String strAdmPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);	
			
			String strStatusTypeValue="Multi";						
			String statTypeName="AutoMST_"+System.currentTimeMillis();
			String strStatTypDefn="Auto Test Status Type";
			String strHeader[] = { "Action", "Name", "Type", "Event Only?",
					"Standard Type","Section", "Description", "Statuses" };
			String strData[] = { "edit | statuses | sort", statTypeName,
					"Multi", "No", "","Uncategorized", strStatTypDefn, "" };
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
		
			log4j.info("---------------Precondtion for test case "+gstrTCID+" starts----------");
			
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
				strFailMsg = objUser
						.advancedOptns(
								seleniumPrecondition,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
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
			log4j.info("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			log4j.info("---------------Precondtion for test case "+gstrTCID+" ends----------");
		/*
		 * Step 2: Login as user 'A' and navigate to Setup>>Status types <-> 'Status Type List' page is displayed. 
		 *
		 */
			
           log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objLogin.newUsrLogin(selenium, strUserName,
						strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objStatTyp.navStatusTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
		/*
		 * Step 3: Click on 'Create new status type' <-> 'Select Status Type' screen is displayed
		 * Step 4: Select 'Multi' and click on 'Next' <-> 'Create Multi Status Type' screen is displayed.
		 * Step 5: Fill in all the mandatory fields and 'Save' <->	Status List for <Status Type Name>' page is displayed
		 */
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objStatTyp.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// click on save
				selenium.click(propElementDetails.getProperty("Save"));
				selenium.waitForPageToLoad(gstrTimeOut);
		/*
		 * Step 6: Select 'Return To Status Type List' screen <-> Status
		 * type is listed in the 'Status Type List' screen.
		 */
				try {
					assertEquals("Status List for " + statTypeName,
							selenium.getText(propElementDetails
									.getProperty("Header.Text")));
					log4j.info("Status List for " + statTypeName
							+ " page is displayed");

					// click on Return to Status Type List link
					selenium.click("link=Return to Status Type List");
					selenium.waitForPageToLoad(gstrTimeOut);

					for (int intRec = 0; intRec < strHeader.length; intRec++) {
						// call the function to check appropriate values are
						// displayed in status type list
						strFailMsg = objStatTyp.checkDataInStatusTypeListNew(
								selenium, strHeader[intRec], statTypeName,
								strData[intRec], String.valueOf(intRec + 1));
						try {
							assertTrue(strFailMsg.equals(""));
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " " + strFailMsg;
						}
					}

					if (gstrReason.equals(""))
						gstrResult = "PASS";
				} catch (AssertionError Ae) {
					log4j.info("Status List for " + statTypeName
							+ " page is NOT displayed");
					gstrReason = "Status List for " + statTypeName
							+ " page is NOT displayed";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "83386";
			gstrTO = "Verify that a Multi status type can be created";
			gstrResult = "FAIL";
			excReason = null;

			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			excReason = e;
			gstrReason = excReason.toString();

		}
	}	
	/*********************************************************************************************
	'Description	:Verify that a multi status type can be associated with a standard status type.
	'Arguments		:void
	'Returns		:void
	'Date	 		:02-May-2012
	'Author			:QSG
	'----------------------------------------------------------------------------------------------
	'Modified Date                                                                     Modified By
	'<Name>                              		                                        <Name>
	***********************************************************************************************/
	
	@Test
	public void testBQS66921()throws Exception{
		
		Login objLogin = new Login();			
		StatusTypes objStatTyp=new StatusTypes();
		ReadData objReadData = new ReadData (); 
		General objGeneral=new General();
		try{
			gstrTCID = "66921";			
			gstrTO = "Verify that a multi status type can be associated with a standard status type.";
			gstrReason = "";
			gstrResult = "FAIL";		
						
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
			Date_Time_settings dts = new Date_Time_settings();	
			gstrTimetake=dts.timeNow("HH:mm:ss");

			//login details
			String strUserName = objReadData.readData("Login", 3, 1);
			String strPassword = objReadData.readData("Login", 3, 2);
			String strRegn=objReadData.readData("Login", 3, 4);
			
			String strStatusTypeValue="Multi";			
			String strStandType="Ambulance/Aeromedical Availability";
			String statTypeName="AutoMST_"+System.currentTimeMillis();
			String strStatTypDefn="Auto Test Status Type";
			
			/*
			 * Step 1: 	Login as RegAdmin and navigate to 'Setup >> Status Type' 
			 * Select to 'Create New Status Type >> Multi
			 * <-> 	'Create Multi Status Type' screen is displayed. 
			 */
			selenium.open(propEnvDetails.getProperty("urlRel"));
			String strFailMsg = objLogin.login(selenium, strUserName,
					strPassword);	
			
			try {
				assertTrue(strFailMsg.equals(""));	
				//nav to default region
				strFailMsg = objLogin.navUserDefaultRgn(selenium, strRegn);
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
			 * Step 2: 	Select a Standard Status Type, fill in the mandatory fields and save. 	<->	Status type is listed in the status type list screen where appropriate value is displayed under the 'Standard Type' column header.
			 */
			try {
				assertTrue(strFailMsg.equals(""));
				strFailMsg = objStatTyp.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			try {
				assertTrue(strFailMsg.equals(""));
				// check standard status type field is enabled
				try {
					assertTrue(selenium.isEditable(propElementDetails
							.getProperty("CreateStatusType.StandStatType")));
					log4j.info("'Standard Status Type' field is enabled.");

					// select the Standard status type
					selenium.select(propElementDetails
							.getProperty("CreateStatusType.StandStatType"),
							"label=" + strStandType);
					// save details
					selenium.click(propElementDetails.getProperty("Save"));
					selenium.waitForPageToLoad(gstrTimeOut);

					// click on Return to Status Type List link
					selenium.click("link=Return to Status Type List");
					selenium.waitForPageToLoad(gstrTimeOut);

					try {
						assertEquals("Status Type List", selenium
								.getText("css=h1"));
						log4j.info("Status Type List screen is displayed");
						// check status type displayed in Status Type List
						try {
							assertTrue(selenium
									.isElementPresent("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
											+ statTypeName + "']"));
							log4j
									.info(selenium
											.getText("//div[@id='mainContainer']/table[2]/thead/tr/th[5]/a"));

							
							
							/*assertEquals(
									"Standard Type",
									selenium
											.getText("//div[@id='mainContainer']/table[2]/thead/tr/th[5]/a"));
*/
							assertEquals(
									"Standard Type",
									objGeneral
											.seleniumGetText(
													selenium,
													"//div[@id='mainContainer']/table[2]/thead/tr/th[5]/a",
													160));

							assertEquals(
									strStandType,
									selenium
											.getText("//div[@id='mainContainer']/table[2]/tbody/tr/td[2][text()='"
													+ statTypeName
													+ "']/parent::tr/td[5]"));
							log4j
									.info("Status type is listed in the status type list screen where " +
											"appropriate value is displayed under the 'Standard Type'" +
											" column header.");
							gstrResult = "PASS";
						} catch (AssertionError Ae) {
							log4j
									.info("Status type is listed in the status type list screen"
											+ " where appropriate value is NOT displayed under the 'Standard Type' column header.");
							gstrReason = "Status type is listed in the status type list screen"
									+ " where appropriate value is NOT displayed under the 'Standard Type' column header.";
						}

					} catch (AssertionError Ae) {
						log4j.info("Status Type List screen is NOT displayed");
						gstrReason = "Status Type List screen is NOT displayed";
					}

				} catch (AssertionError Ae) {
					log4j.info("'Standard Status Type' field is NOT enabled.");
					gstrReason = "'Standard Status Type' field is NOT enabled.";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFailMsg;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");
		} catch (Exception e) {

			Exception excReason;
			gstrTCID = "66921";
			gstrTO = "Verify that a multi status type can be associated with a standard status type.";
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
	}
	
	/***********************************************************************
	 'Description :Verify that the option to trace the user can be selected for a multi status type.
	 'Precondition :1. 'Status Change Name Trace' option is selected for Test user's region.
	     2. Test user has created a multi status type 'MST'
	     3. Statuses 'S1' and 'S2' are created under 'MST'
	     4. 'MST' is selected for resource type 'RT'
	     4. Resource 'RS' is created for 'RT'
	     5. Test user has update status right on RS and role to update MST
	     6. View 'V1' is created selecting 'MST' and 'RS'   
	 'Arguments  :void
	 'Returns  :void
	 'Date    :7-June-2012
	 'Author   :QSG
	 '-----------------------------------------------------------------------
	 'Modified Date                            Modified By
	 '<Name>                                <Name>
	 ************************************************************************/
	 
	@Test
	public void testBQS66920() throws Exception {

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
			gstrTCID = "66920";
			gstrTO = "Verify that the option to trace the "
					+ "user can be selected for a multi status type.";
			gstrReason = "";
			gstrResult = "FAIL";

			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			// login details
			String strUserName = "AutoUsr"+System.currentTimeMillis();
			String strPassword = "abc123";

			// Search user criteria
			String strByRole = objrdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = objrdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = objrdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = objrdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			String strViewName = "AutoV_"+strTimeText;

			String strUpdateName = "";
			String strStatusValue = "";
			String strIncUsrName="User_"+System.currentTimeMillis();
		
			String[] strErrorMsg = new String[2];

			// login details
			String strAdmUserName = objrdExcel.readData("Login", 3, 1);
			String strAdmPassword = objrdExcel.readData("Login", 3, 2);
			String strRegn = objrdExcel.readData("Login", 3, 4);

			String strStatusTypeValue = "Multi";
			String statTypeName = "AutoMSt_" + strTimeText;
			String strStatTypDefn = "Auto";
			String strStatTypeColor = "Black";
			
			String strSTvalue[] = new String[1];
			
			String strState = "Alabama";
			String strCountry = "Autauga County";

			String strStatusName1 = "Sta" + strTimeText;
			String strStatusName2 = "Stb" + strTimeText;

			String[] strStatusNames = { strStatusName1, strStatusName2};
			
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
				strFuncResult = objST.savAndVerifyMultiST(seleniumPrecondition, statTypeName);
				
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
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName1, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objST.createSTWithinMultiTypeST(seleniumPrecondition,
						statTypeName, strStatusName2, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValues[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strStatValue = objST.fetchStatValInStatusList(seleniumPrecondition,
						statTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValues[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
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

				strUpdateName = strErrorMsg[0];

				if (strUpdateName.equals(strStatusNames[0])
						|| strUpdateName.equals("--")) {
					strUpdateName = strStatusNames[1];
					strStatusValue = strStatusValues[1];
				} else {
					strUpdateName = strStatusNames[0];
					strStatusValue = strStatusValues[0];
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3: Click on the status cell of MST 'Update status' screen is
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
						strResType, statTypeName);

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
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue, strSTvalue[0], true, "","");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.savAndVerifyUpdateST(selenium,
						strViewName, strResType, statTypeName, strUpdateName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(strViewName + " page is NOT displayed",
						strFuncResult);
				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strStatusValue + "']"));
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
				
				String strNewPassword = "abc321";

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue, strSTvalue[0], true, strUserName,
						strNewPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.savAndVerifyUpdateST(selenium,
						strViewName, strResType, statTypeName, strUpdateName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals(strViewName + " page is NOT displayed",
						strFuncResult);

				try {
					assertTrue(selenium.isChecked("css=input[value='"
							+ strStatusValue + "']"));
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
			
				strFuncResult = objViews.fillAndSavUpdateStatus(selenium,
						strUpdateName, strSTvalue[0], true, strIncUsrName,
						strPassword);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.savAndVerifyUpdateST(selenium,
						strViewName, strResType, statTypeName, strUpdateName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 7: Once again update the status of MST, provide appropriate
			 * name and password and click on 'Save'<-> The status value is
			 * updated and displayed on the view screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strErrorMsg = objViews.fetchUpdateStatusValue(selenium, strResType,
						statTypeName);
				strUpdateName = strErrorMsg[0];

				if (strUpdateName.equals(strStatusNames[0])
						|| strUpdateName.equals("--")) {
					strUpdateName = strStatusNames[1];
					strStatusValue = strStatusValues[1];
				} else {
					strUpdateName = strStatusNames[0];
					strStatusValue = strStatusValues[0];
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navUpdateStatusByStatusCell(selenium,
						strResType, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);							
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue, strSTvalue[0], true, strUserName,
						strPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.savAndVerifyUpdateST(selenium,
						strViewName, strResType, statTypeName, strUpdateName);
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
			gstrTCID = "66920";
			gstrTO = "Verify that the option to trace the"
					+ " user can be selected for a multi status type.";
			gstrResult = "FAIL";
			excReason = null;
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE - " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
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
