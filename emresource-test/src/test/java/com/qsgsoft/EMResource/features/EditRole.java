package com.qsgsoft.EMResource.features;

import java.util.Date;
import java.util.Properties;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import static org.junit.Assert.*;

/*****************************************************************
' Description :This class includes Edit Role requirement testcases
' Date		  :2-May-2012
' Author	  :QSG
'-----------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*****************************************************************/

public class EditRole  {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.EditRole");
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
	String gstrTimeOut="";
	
	Selenium selenium,seleniumPrecondition;

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


		seleniumPrecondition=new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		selenium.start();
		selenium.windowMaximize();
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		seleniumPrecondition.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}

		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
		selenium.stop();	
		seleniumPrecondition.stop();
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
	
	
	

	/*******************************************************************
	'Description	:Verify that a role can be edited by RegAdmin.
	'Precondition	:1. Role R1 is created selecting some right (eg. 
					 User - Setup User Accounts).
					 2. User U1 is created selecting role R1. 

	'Arguments		:None
	'Returns		:None
	'Date	 		:2-May-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS69278() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application		
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class														// CreateUsers
		Roles objRoles=new Roles();
		

		try {
			gstrTCID = "BQS-69278 ";
			gstrTO = "Verify that a role can be edited by RegAdmin.";
			gstrResult = "FAIL";
			gstrReason = "";
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
		    // Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";
			// USER
			String strUserName = "AutoUsr_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			
			/*Precondition:
			 * 1. Role R1 is created selecting some right (eg. User - Setup User Accounts).
			 * 2. User U1 is created selecting role R1. 	 */
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");
				
				   try {
						assertEquals("", strFuncResult);
						strLoginUserName = rdExcel.readData("Login", 3, 1);
						strLoginPassword = rdExcel.readData("Login", 3, 2);
						strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
								strLoginPassword);
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
						strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}			
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objRoles.rolesMandatoryFlds(seleniumPrecondition,
								strRolesName);
					} catch (AssertionError ae) {
						gstrReason = strFuncResult;
					}

					try {
						assertEquals("", strFuncResult);
						String strOptions = propElementDetails
								.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
						strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
								strOptions, true);
					} catch (AssertionError ae) {
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
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					//user1
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
								strUserName, strInitPwd, strInitPwd, strUsrFulName);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition, strRoleValue, true);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objCreateUsers.saveAndNavToUsrListPage(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.logout(seleniumPrecondition);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}	
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");	
						
				/*
				 * STEP 2:Login as User U1. <->The role is available to the user
				 * (i.e. 'Setup' tab is available).
				 */
					try {
						assertEquals("", strFuncResult);
						strFuncResult = objLogin.newUsrLogin(selenium,strUserName, strInitPwd);
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
				try {
					assertEquals("", strFuncResult);
					try {
						assertTrue(selenium.isElementPresent(propElementDetails
								.getProperty("SetUP.SetUpLink")));
						log4j.info("The role is  available to the user "
								+ "(i.e. 'Setup' tab is available). ");
					} catch (AssertionError Ae) {
						strFuncResult = "The role is NOT available to the user "
								+ "(i.e. 'Setup' tab is available). " + Ae;
						gstrReason = strFuncResult + Ae;

						log4j.info("The role is NOT available to the user "
								+ "(i.e. 'Setup' tab is available). " + Ae);
					}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}			
				
				/*
				 *STEP 3: Logout and login as RegAdmin and navigate to Setup>>Roles.
				 * <->'Roles List' screen is displayed.
				 */			
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objLogin.logout(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
				    strFuncResult = objLogin.login(selenium, strLoginUserName,strLoginPassword);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);				
					strFuncResult = objLogin.navUserDefaultRgn(selenium,strRegn);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.navRolesListPge(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*STEP 4:	Click on the 'Edit' link associated with role R1. 
				 * 		'Edit Role' screen is displayed. 
				 */
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRoles.navEditRolesPge(selenium, strRolesName);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*STEP 5:Deselect the right (eg. User - Setup User Accounts) and save the page.
				 *  		'Roles List' screen is displayed. 
				 */
				try {
					assertEquals("", strFuncResult);
					String strOptions = propElementDetails
							.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
					strFuncResult = objCreateUsers.advancedOptns(selenium,
							strOptions, false);
				} catch (AssertionError ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					selenium.selectWindow("");
					selenium.selectFrame("Data");
					selenium.click("css=input[value='Save']");
					selenium.waitForPageToLoad(gstrTimeOut);
					try {
						assertEquals("Roles List", selenium.getText(propElementDetails
								.getProperty("Header.Text")));
						log4j.info("Roles List page is displayed");
						} catch (AssertionError Ae) {
							log4j.info("Roles List page is NOT displayed");					
							gstrReason = strFuncResult;
						}
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				/*STEP 6:Logout and login as user U1. 
				 * 		The role is no longer available to the user (i.e. 'Setup' tab is not available). 
				 */
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult=objLogin.logout(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult=objLogin.login(selenium, strUserName, strInitPwd);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					blnLogin=true;				
					try {
						assertFalse(selenium.isElementPresent(propElementDetails
								.getProperty("SetUP.SetUpLink")));
						log4j.info("The role is no longer available to the user "
								+ "(i.e. 'Setup' tab is NOT available). ");
					} catch (AssertionError Ae) {
						strFuncResult = "The role is no longer available to the user "
								+ "(i.e. 'Setup' tab is available). " + Ae;
						gstrReason = strFuncResult + Ae;
						log4j.info("The role is  available to the user "
								+ "(i.e. 'Setup' tab is available). " + Ae);
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
								
				try {
					assertEquals("", strFuncResult);
					gstrResult="PASS";
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-69278";
			gstrTO = "Verify that a role can be edited by RegAdmin.";
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
