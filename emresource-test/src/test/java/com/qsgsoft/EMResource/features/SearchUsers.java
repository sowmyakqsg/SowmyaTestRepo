package com.qsgsoft.EMResource.features;

import java.util.Date;

import java.util.Properties;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import com.qsgsoft.EMResource.shared.CreateUsers;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.SearchUserByDiffCrteria;
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

/**********************************************************************
' Description :This class includes Search Users requirement testcases
' Precondition:
' Date		  :4-April-2012
' Author	  :QSG
'-------------------------------------------------------------------
' Modified Date                            Modified By
' <Date>                           	         <Name>
'*******************************************************************/

public class SearchUsers  {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.SearchUsers");
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
	Selenium selenium,seleniumPrecondition;
	String gstrTimeOut;
	
	
	@Before
	public void setUp() throws Exception {

		dtStartDate = new Date();
		
		
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

		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {
		
		selenium.close();
		selenium.stop();
		try{
			seleniumPrecondition.close();
		}catch(Exception e){
			
		}
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
	'Description	:Verify that users can be searched by providing a 
	'				 search criteria in the user 
	'Precondition	:1. User A is created selecting role R1.
	'				 2. User B is created selecting role R2.
	'				 3. User C is created selecting role R3. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:13-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS69757() throws Exception {
		
		boolean blnLogin=false;//keep track of logout of application		
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles=new Roles();
		CreateUsers objCreateUsers = new CreateUsers();// object of class													// CreateUsers
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "BQS-69757 ";
			gstrTO = "Verify that users can be searched by providing a"
					+ " search criteria in the user  ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3, 4);
			
			// Role
			String strRolesName1 = "AutoRol1_" +strTimeText;
			String strRolesName2 = "AutoRol2_" +strTimeText;
			String strRolesName3 = "AutoRol3_" +strTimeText;
			String strRoleValue[] = new String[4];
			// USER
			String strUserName1 = "AutoUsr1" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr2" + System.currentTimeMillis();
			String strUserName3 = "AutoUsr3" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN"+strUserName1;			
			String strFILE_PATH= pathProps.getProperty("TestData_path");	
			//search user data
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			 log4j.info("~~~~~PRE-CONDITIION " + gstrTCID  + " EXECUTION STARTS~~~~~");
			 
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
			// ROLE1
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
						strRolesName1, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[0] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName1);
				if (strRoleValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// ROLE2

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName2, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[1] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName2);
				if (strRoleValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// ROLE3
			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(seleniumPrecondition,
						strRolesName3, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(seleniumPrecondition,
						strRolesName3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue[2] = objRoles.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName3);
				if (strRoleValue[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// User1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName1, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[0], true);
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
			// User2
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName2, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[1], true);
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
			// User1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName3, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRole(seleniumPrecondition,
						strRoleValue[2], true);
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
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			/*
			 * STEP 2: Login as RegAdmin and navigate to Setup>>Users.<-> 'Users
			 * List' page is displayed.
			 */
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 3: Search for user A or user B or user C by selecting 'Username'
			 * 'Contains'.<-> Appropriate users are displayed.
			 */ 
			
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
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName1,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUserNames[] = new String[] { strUserName1 };
				if (strUserNames.length == selenium.getXpathCount(
						"//table[@id='tblUsers']/tbody/tr").intValue()) {
					for (String s : strUserNames) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
											+ "[text()='" + s + "']"));
							log4j.info("Appropriate users are displayed. ");
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " User " + s
									+ " is NOT displayed";

						}
					}
				} else {
					strFuncResult = "User NOT found";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP 3: 	Search for user A or user B or user C by selecting 'Role' in 'Search' Drop down. 
		 * <-> Appropriate users are displayed.
		 */ 
			try {
				assertEquals("", strFuncResult);
				strByRole = strRolesName1;
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 3, 12,
						strFILE_PATH);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName1,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUserNames[] = new String[] { strUserName1 };
				if (strUserNames.length == selenium.getXpathCount(
						"//table[@id='tblUsers']/tbody/tr").intValue()) {
					for (String s : strUserNames) {
						try {
							assertTrue(selenium
									.isElementPresent("//table[@id='tblUsers']/tbody/tr/td[2]"
											+ "[text()='" + s + "']"));
							log4j.info("Appropriate users are displayed. ");
						} catch (AssertionError Ae) {
							gstrReason = gstrReason + " User " + s
									+ " is NOT displayed";
						}
					}
				} else {
					strFuncResult = "User NOT found";
				}

			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = gstrReason + " " + strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			
		} catch (Exception e) {
			gstrTCID = "BQS-69757";
			gstrTO = "Verify that users can be searched by providing a " +
					"search criteria in the user ";
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
