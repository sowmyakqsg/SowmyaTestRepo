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

/*********************************************************************
' Description		:This class contains test cases from   requirement
' Requirement		:Activate System notice
' Requirement Group	:Setting up Regions 
� Product		    :EMResource v3.19
' Date			    :2/June/2012
' Author		    :QSG
' ---------------------------------------------------------------------
' Modified Date			                                   Modified By
' Date					                                   Name
'**********************************************************************/

public class FTSRemoveUserFromRegion {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.FTSRemoveUserFromRegion");
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
				4444, propEnvDetails.getProperty("Browser"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(propEnvDetails.getProperty("Server"),
				4444, propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));
		
		selenium.start();
		selenium.windowMaximize();
		

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			selenium.close();
		} catch (Exception e) {

		}
		selenium.stop();
		
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/*************************************************************
	'Description	:Verify that user cannot be saved by removing 
	                  access to all the regions.
	'Arguments		:None
	'Returns		:None
	'Date			:7/6/2012
	'Author			:QSG
	'-------------------------------------------------------------
	'Modified Date				                        Modified By
	'Date					                            Name
	**************************************************************/

	@Test
	public void testFTS32752() throws Exception {
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions = new Regions();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		try {

			gstrTCID = "32752"; // Test Case Id
			gstrTO = " Verify that user cannot be saved by removing access to all the regions.";// Test
																								// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			// user search data
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";
			// Region data
			String strRegionName = rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

		/*
		* STEP :
		  Action:Precondition:
		  User U1 has access to the regions 'R1' and 'R2'
		  Expected Result:No Expected Result
		*/
		//192128
		log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
			     + " EXECUTION STATRTS~~~~~");
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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// user
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

			// searching user by different criteria
			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
						strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID+ " EXECUTION STARTS~~~~~");

		/*
		* STEP :
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//192129

		/*
		* STEP :
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//192130

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//192131
		
		
		// searching user by different criteria
		try {
			assertEquals("", strFuncResult);
			strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			strFuncResult = objSearchUserByDiffCrteria
					.searchUserByDifCriteria(selenium, strUserName,
							strByRole, strByResourceType, strByUserInfo,
							strNameFormat);

		} catch (AssertionError ae) {
			gstrReason = strFuncResult;
		}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Remove the access from the regions 'R1' and 'R2' by deselecting the respective check boxes
		   and click on button 'Save'
		  Expected Result:Error message "You must select at least one Region."
	      is displayed.
				  User is not saved.
				  Check box corresponding to region 'R2' remains checked.
				  Check box corresponding to region 'R1' remains checked.
		*/
		//192132

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkerrorMsgEditusrRegionPage(
						selenium, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						false, strRegionValues[0], strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						false, strRegionValues[1], strRegn);
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
			gstrTCID = "32752";
			gstrTO = "Verify that user cannot be saved by removing access to all the regions.";
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

	/******************************************************************************
	'Description  :Verify that when the RegAdmin adds the user back to the region,
	               previously selected 'Roles' for the user within that region are reset.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :7/7/2012
	'Author		  :QSG
	'-------------------------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS32806() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions=new Regions();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Roles objRoles = new Roles();
	try{

			gstrTCID = "32806"; // Test Case Id
			gstrTO = " Verify that when the RegAdmin adds the user back to the region,"
					+ " previously selected 'Roles' for the user within that region are reset.";// Test																							// Objective
			gstrReason = "";
			gstrResult = "FAIL";	
		
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
		
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Region data
			String strRegionName= rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			
			//Search user data
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			// Role
			String strRolesName = "Rol299" + System.currentTimeMillis();
			String strRoleValue = "";

		/*
		* STEP :
		  Action:Preconditions:
        1. User U1 has access to the regions 'R1' and 'R2'.
	    2. Role 'Role1' is assigned to user U1 in the region 'R2'.
		  Expected Result:No Expected Result
		*/
		//193221
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
				     + " EXECUTION STATRTS~~~~~");
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
			
			//Region
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//user
			
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

			// searching user by different criteria
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// ROLE
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strRoleRights = {};
				String[] updateRightValue = {};
				String[] strViewRightValue = {};
				strFuncResult = objRoles.CreateRoleWithAllFields(selenium,
						strRolesName, strRoleRights, strViewRightValue, false,
						updateRightValue, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(selenium,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRoleValue = objRoles.fetchRoleValueInRoleList(selenium,
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
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
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
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
	
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
		/*
		* STEP :
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//193222
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

		/*
		* STEP :
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//193223
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//193224
			// searching user by different criteria
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Remove the access from the region 'R2' by
			 * deselecting the check box and Click on button 'Save' Expected
			 * Result:User is taken back to 'Users List' screen.
			 */
	      	//193676
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
							strRegionValues[1], false, false);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				

		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
		*/
		//193680
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Provide back access to the region 'R2' by selecting the check box and click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//193225

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], true, false);
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
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
				  Check box corresponding to region 'R2' is selected.
				  Check box corresponding to region 'R1' remains checked.
		*/
		//193226
					
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[0], strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[1],strRegionName);
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
		  Action:Login as RegAdmin to region 'R2'.
		  Expected Result:'Region Default' screen is displayed.
		*/
		//193386
			
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed.
		*/
		//193388
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the link 'Edit' associated with the user U1
		  Expected Result:'Edit User' screen is displayed.
		*/
		//193240
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to 'Roles' section in the 'Edit User' screen.
		  Expected Result:Check box associated with the 'Role1' is deselected.
		*/
		//193242
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRoleSelectedOrNot(selenium,
						false, strRoleValue, strRolesName);
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
		gstrTCID = "32806";
		gstrTO = "Verify that when the RegAdmin adds the user back to the region, previously selected 'Roles' for the user within that region are reset.";
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
/***************************************************************
'Description		:Verify that RegAdmin can provide access back to the 
                    region from which the user had been removed.
'Precondition		:
'Arguments		:None
'Returns		:None
'Date			:7/7/2012
'Author			:QSG
'---------------------------------------------------------------
'Modified Date				Modified By
'Date					Name
***************************************************************/

	@Test
	public void testFTS32800() throws Exception {
		
		String strFunResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions=new Regions();
	try{
		
			gstrTCID = "32800"; // Test Case Id
			gstrTO = " Verify that RegAdmin can provide access back to the region from " +
					 "which the user had been removed.";// Test																												// Objective
			gstrReason = "";
			gstrResult = "FAIL";	

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strFuncResult = "";
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Region data
			String strRegionName= rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];
			// /User data
			String strByRole = "";
			String strByResourceType = "";
			String strByUserInfo = "";
			String strNameFormat = "";
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
		/*
		* STEP :
		  Action:Precondition:
         User U1 has access to the regions 'R1' and 'R2'
		  Expected Result:No Expected Result
		*/
		//192776
		
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
			
			//Region 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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

			// searching user by different criteria
			try {
				assertEquals("", strFunResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
						strFILE_PATH);
				strFunResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFunResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//192777
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
		/*
		* STEP :
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//192778
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//192779
		// searching user by different criteria
		try {
			assertEquals("", strFunResult);
			strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			strFunResult = objSearchUserByDiffCrteria
					.searchUserByDifCriteria(selenium, strUserName,
							strByRole, strByResourceType, strByUserInfo,
							strNameFormat);

		} catch (AssertionError ae) {
			gstrReason = strFunResult;
		}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Remove the access from the region 'R2' by deselecting the check box and Click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//192780
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFunResult);
				gstrResult="PASS";
				//Write result data
				String strTestData[]=new String[10];
				strTestData[0]=propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strUserName+"/"+strInitPwd;
				strTestData[3]="Login as a:"+strUserName+"Launch EMR application on mobile device";
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "User");
			} catch (AssertionError ae) {
				gstrReason = strFunResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "32800";
			gstrTO = "Verify that RegAdmin can provide access back to the region from which the user had been removed.";
			gstrResult = "FAIL";
			log4j.info(e);
			log4j.info("========== Test Case '" + gstrTCID
					+ "' has FAILED ==========");
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");
			gstrReason = e.toString();
		}
		if (blnLogin) {
			strFunResult = objLogin.logout(selenium);

			try {
				assertEquals("", strFunResult);
			} catch (AssertionError Ae) {
				gstrReason = strFunResult;
			}
		}
	}
	/***************************************************************
	'Description		:Verify that when the RegAdmin adds the user back to the region,
	                      previously selected 'Resource Rights' for the user within that region are reset.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/9/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS32808() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();
	try{
			gstrTCID = "32808"; // Test Case Id
			gstrTO = " Verify that when RegAdmin adds the user back to the region, previously selected 'Rights'" +
					" for the user within that region are reset.";// Test objective
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			// Region data
			String strRegionName= rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];
				
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
			strLoginUserName = rdExcel.readData("Login", 3, 1);
			strLoginPassword = rdExcel.readData("Login", 3, 2);

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
		
		/*
		* STEP :
		  Action:Preconditions:
	     1. User U1 has access to the regions 'R1' and 'R2'.
	     2. Additional User rights are assigned to user U1 by in the region 'R2'.
		  Expected Result:No Expected Result
		*/
		//193842
		
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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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

			// searching user by different criteria
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, true);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName );
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.advancedOptns(
								selenium,
								propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp"),
								true);
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			/*
			* STEP :
			  Action:Login as RegAdmin to region 'R1'.
			  Expected Result:No Expected Result
			*/
			//193843
		
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//193844
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//193845
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Remove the access from the region 'R2' by deselecting the check box and Click on button 'Save'
			  Expected Result:User is taken back to 'Users List' screen.
			*/
			//193846
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Click on the link 'Regions' associated with the user U1
			  Expected Result:'Edit User Regions' screen is displayed.
			*/
			//193847
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Provide back access to the region 'R2' by selecting the check box and click on button 'Save'
			  Expected Result:User is taken back to 'Users List' screen.
			*/
			//193848


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Click on the link 'Regions' associated with the user U1
			  Expected Result:'Edit User Regions' screen is displayed.
					  Check box corresponding to region 'R2' is selected.
					  Check box corresponding to region 'R1' remains checked.
			*/
			//193849
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[1], strRegn);
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
			  Action:Login as RegAdmin to region 'R2'.
			  Expected Result:'Region Default' screen is displayed.
			*/
			//193850

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName );
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			* STEP :
			  Action:Navigate to Setup >> Users
			  Expected Result:'Users List' screen is displayed.
			*/
			//193851

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			* STEP :
			  Action:Click on the link 'Edit' associated with the user U1
			  Expected Result:'Edit User' screen is displayed.
			*/
			//193852

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			/*
			* STEP :
			  Action:Navigate to 'Advanced Options' section in the 'Edit User' screen.
			  Expected Result:Check boxes associated with the rights are deselected.
			*/
			//193853
			try {
				assertEquals("", strFuncResult);
				String strOptions=propElementDetails
										.getProperty("CreateNewUsr.AdvOptn.SetUpStatTyp");
				String strRight="Setup Status Types";
				strFuncResult = objCreateUsers.chkRightAdvancedOptnsSelectedOrNot(selenium, strOptions, false,strRight);
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
			gstrTCID = "32808";
			gstrTO = "Verify that when RegAdmin adds the user back to the region, previously selected 'Rights' for the user within that region are reset.";
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

	/***************************************************************
	'Description		:Verify that when the RegAdmin adds the user back to the region, previously selected 'Resource Rights' for the user within that region are reset.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/9/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS32807() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Regions objRegions=new Regions();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Resources objResources = new Resources();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
	try{
	
			gstrTCID = "32807"; // Test Case Id
			gstrTO = " Verify that when the RegAdmin adds the user back to the region, previously selected 'Resource Rights' for the user within that region are reset.";// Test
																																											// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
		
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// Region data
			String strRegionName = rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

			// RT data
			String strResrcTypName = "RT" + System.currentTimeMillis();

			// Resource
			String strResource = "RS23" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strReSValue[] = new String[1];
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName1 = "ST1" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";
		
		/*
		* STEP :
		  Action:Preconditions:
		  1. User U1 has access to the regions 'R1' and 'R2'.
		  2. Resource 'RS' is assigned to user U1 in the region 'R2'.
		  Expected Result:No Expected Result
		*/
		//193689
		
		
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
			//Region
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//user
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

			// searching user by different criteria
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0] , true, true);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName);
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
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName1,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName1);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.assignUsrToResource(selenium, true, false, false, true, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				// assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strReSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

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
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//193690
		
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//193691
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}


		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//193692
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Remove the access from the region 'R2' by deselecting the check box and Click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//193693
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0] , false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
		*/
		//193694
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide back access to the region 'R2' by selecting the check box and click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//193695
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.saveAndNavToUsrListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
				  Check box corresponding to region 'R2' is selected.
				  Check box corresponding to region 'R1' remains checked.
		*/
		//193696
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegn);
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
		  Action:Login as RegAdmin to region 'R2'.
		  Expected Result:'Region Default' screen is displayed.
		*/
		//193697
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

		/*
		* STEP :
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed.
		*/
		//193698
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the link 'Edit' associated with the user U1
		  Expected Result:'Edit User' screen is displayed.
		*/
		//193699
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Navigate to 'Resource Rights' section in the 'Edit User' screen.
		  Expected Result:Check box associated with the resource 'RS' is deselected.
		*/
		//193700

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRSSelectedOrNotForUsr(
						selenium, false, strResVal, strResource);
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
			gstrTCID = "32807";
			gstrTO = "Verify that when the RegAdmin adds the user back to the region, previously selected 'Resource Rights' for the user within that region are reset.";
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
	/***************************************************************
	'Description		:Remove the access to a region for a user who has access to more than two regions
	                     and verify that the region is not listed in the 'Select Region' screen.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/9/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS32750() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions=new Regions();
	try{
		
			gstrTCID = "32750"; // Test Case Id
			gstrTO = " Remove the access to a region for a user who has access to more than two regions" +
					" and verify that the region is not listed in the 'Select Region' screen.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn =rdExcel.readData("Login", 3, 4);
			//Region data 
			String strRegionName1 = rdExcel.readData("Regions", 3, 5);
			String strRegionName2 = rdExcel.readData("Regions", 4, 5);
			String strRegionValues[] = new String[3];
			
			//User data
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

		/*
		* STEP :
		  Action:Precondition:
	      User U1 has access to the regions 'R1','R2' and 'R3'
		  Expected Result:No Expected Result
		*/
		//192084
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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Region 1 Value
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegionName1);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			try {
				assertEquals("", strFuncResult);
				strRegionValues[2] = objRegions.fetchRegionValue(selenium,
						strRegionName2);
				if (strRegionValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[2], true, true);
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
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//192085
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//192086
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//192087
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium,
								strUserName, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Remove the access from the region 'R2' by deselecting the check box and Click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//192088
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, false);
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
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
				  Check box corresponding to region 'R2' is deselected.
				  Check box corresponding to region 'R1' remains checked.
				  Check box corresponding to region 'R3' remains checked.
		*/
		//192089
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, false, strRegionValues[1], strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[2], strRegionName2);
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
		  Action:Login as user U1.
		  Expected Result:'Select Region' screen is displayed.
		*/
		//192097

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		* STEP :
		  Action:Click on the 'Region' drop down.
		  Expected Result:Region 'R2' is not displayed.
				  Region 'R1' is displayed.
				  Region 'R3' is displayed.
		*/
		//192099
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navToSelectRegionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.chkRegionListForUser(selenium, strRegionValues[2], true, strRegionName2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.chkRegionListForUser(selenium, strRegionValues[0], true, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.chkRegionListForUser(selenium, strRegionValues[1], false, strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
				//Write result data
				String strTestData[]=new String[10];
				strTestData[0]=propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strUserName+"/"+strInitPwd;
				strTestData[3]="Login as a:"+strUserName+"Launch EMR application on mobile device";
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "User");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Launch EMR application on mobile device (Ex: ipod touch) and login as user U1
		  Expected Result:'Main Menu' screen is displayed.
		*/
		//192090

		/*
		* STEP :
		  Action:Click on 'Select Region' on the 'Main Menu'.
		  Expected Result:'Pick Region' screen is displayed.
				  Region 'R2' is not listed.
				  Region 'R1' is listed.
				  Region 'R3' is listed.
		*/
		//192091

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "32750";
		gstrTO = "Remove the access to a region for a user who has access to more than two regions and verify that the region is not listed in the 'Select Region' screen.";
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
	
	/***************************************************************
	'Description		:Verify that when the user is added back to the region, the region should be listed in the 'Region' drop down in the 'Select Region' screen.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:7/9/2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS32812() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();
	try{	

			gstrTCID = "32812"; // Test Case Id
			gstrTO = " Verify that when the user is added back to the region, the region should be listed in the" +
					" 'Region' drop down in the 'Select Region' screen.";// Test Objective
			gstrReason = "";
			gstrResult = "FAIL";

			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			//Region data 
			String strRegionName = rdExcel.readData("Regions", 3, 5);
			String strRegionName1 = rdExcel.readData("Regions", 4, 5);
			String strRegionValues[] = new String[3];

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";

		/*
		* STEP :
		  Action:Precondition:
	     User U1 has access to the regions 'R1','R2' and 'R3'
		  Expected Result:No Expected Result
		*/
		//194269
		
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[2] = objRegions.fetchRegionValue(selenium,
						strRegionName1);
				if (strRegionValues[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[2], true, true);
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
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//194270
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//194271

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//194272
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium,
								strUserName, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Remove the access from the region 'R2' by deselecting the check box and Click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//194273
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], false, false);
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
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
		*/
		//194363
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Provide back access to the region 'R2' by selecting the check box and click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//194364
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
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
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
				  Check box corresponding to region 'R2' is selected.
				  Check box corresponding to region 'R1' remains checked.
				  Check box corresponding to region 'R3' remains checked.
		*/
		//194274
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[1], strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[2], strRegionName1);
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
		  Action:Login as user U1.
		  Expected Result:'Select Region' screen is displayed.
		*/
		//194275

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.navToSelectRegionPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

		/*
		* STEP :
		  Action:Click on the 'Region' drop down.
		  Expected Result:Region 'R2' is displayed.
				  Region 'R1' is displayed.
				  Region 'R3' is displayed.
		*/
		//194276
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.chkRegionListForUser(selenium, strRegionValues[1], true, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.chkRegionListForUser(selenium, strRegionValues[2], true, strRegionName1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult =objCreateUsers.chkRegionListForUser(selenium, strRegionValues[2], true, strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
				//Write result data
				String strTestData[]=new String[10];
				strTestData[0]=propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strUserName+"/"+strInitPwd;
				strTestData[3]="Login as a:"+strUserName+"Launch EMR application on mobile device";
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "User");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}


		/*
		* STEP :
		  Action:Launch EMR application on mobile device (Ex: ipod touch) and login as user U1
		  Expected Result:'Main Menu' screen is displayed.
		*/
		//194277

		/*
		* STEP :
		  Action:Click on 'Select Region' on the 'Main Menu'.
		  Expected Result:'Pick Region' screen is displayed.
				  Region 'R2' is listed.
				  Region 'R1' is listed.
				  Region 'R3' is listed.
		*/
		//194278
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "32812";
		gstrTO = "Verify that when the user is added back to the region, the region should be listed in the 'Region' drop down in the 'Select Region' screen.";
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
	
	/***************************************************************
	'Description		:Verify that when RegAdmin adds the user back to the region, 
	                     previously selected 'Views Access' for the user within that region are reset.
	'Precondition		:
	'Arguments		    :None
	'Returns		    :None
	'Date			    :7/9/2012
	'Author			    :QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testFTS32809() throws Exception {
		
		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Views objViews = new Views();
		Regions objRegions = new Regions();
	try{	

			gstrTCID = "32809"; // Test Case Id
			gstrTO = " Verify that when RegAdmin adds the user back to the region, previously selected 'Views "
					+ "Access' for the user within that region are reset.";// Test
																			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			
			//Region data 
			String strRegionName= rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];
			
			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST1" + System.currentTimeMillis();
			String strStatTypDefn = "Automation";

			// RT data
			String strResrcTypName = "AutoRT1" + System.currentTimeMillis();
			String strRsTypeValues[]=new String[1];
			// Resource
			String strResource = "AutoRS1" + System.currentTimeMillis();
			String strHavBed = "No";
			String strAbbrv = "abb";
			String strResVal = "";
			String strRSValue[] = new String[2];
			
			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
			String strViewValue[] = new String[1];
		/*
		* STEP :
		  Action:Preconditions:
	    1. User U1 has access to the regions 'R1' and 'R2'.
	    2. User U1 is assigned to view the region R2's view 'V1'.
		  Expected Result:No Expected Result
		*/
		//193952

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
				strFuncResult = objRegions.navRegionList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(selenium,
						strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(selenium,
						strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, true);
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName);
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
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						selenium, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(selenium,
						statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(selenium, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// RT1
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						selenium, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(selenium,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(selenium,
								strResrcTypName);

				if (strRsTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
				strFuncResult = objResources.navToCreateResourcePage(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						selenium, strResource, strAbbrv, strResrcTypName,
						"Hospital", "FN", "LN");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				 assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(selenium,
						strResource, strHavBed, "", strAbbrv, strResrcTypName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(selenium,
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
			
			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = {strStatusTypeValues[0]};
				String[] strReSValue={strResVal};
				strFuncResult = objViews.createView(selenium, strViewName,
						strVewDescription, strViewType, false, false, strSTvalue,
						false, strReSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strViewValue[0] = objViews.fetchViewValueInViewList(selenium, strViewName);
				if (strViewValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch View value";
				}
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
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selAndDeselViewInEditUserPage(selenium, strViewValue[0], true);
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
		  Action:Login as RegAdmin to region 'R1'.
		  Expected Result:No Expected Result
		*/
		//193953				
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
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed
		*/
		//193954
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed
		*/
		//193955
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Remove the access from the region 'R2' by deselecting the check box and Click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//193956
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], false, false);
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
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
		*/
		//193957
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Provide back access to the region 'R2' by selecting the check box and click on button 'Save'
		  Expected Result:User is taken back to 'Users List' screen.
		*/
		//193958
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
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
		  Action:Click on the link 'Regions' associated with the user U1
		  Expected Result:'Edit User Regions' screen is displayed.
				  Check box corresponding to region 'R2' is selected.
				  Check box corresponding to region 'R1' remains checked.
		*/
		//193959
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[0], strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium, true, strRegionValues[1], strRegn);
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
		  Action:Login as RegAdmin to region 'R2'.
		  Expected Result:'Region Default' screen is displayed.
		*/
		//193960
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName );
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to Setup >> Users
		  Expected Result:'Users List' screen is displayed.
		*/
		//193961
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Click on the link 'Edit' associated with the user U1
		  Expected Result:'Edit User' screen is displayed.
		*/
		//193962
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserPge(selenium, strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Navigate to 'Views' section in the 'Edit User' screen.
		  Expected Result:Check boxes associated with the view 'V1' is deselected.
		*/
		//193963
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkViewSelOrNotInEditUsrpage(selenium, strViewName, false, strViewValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				gstrResult="PASS";
				//Write result data
				String strTestData[]=new String[10];
				strTestData[0]=propEnvDetails.getProperty("Build");
				strTestData[1]=gstrTCID;
				strTestData[2]=strUserName+"/"+strInitPwd;
				strTestData[3]="verify from 13th step.";
				String strWriteFilePath=pathProps.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "User");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}
			
		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "32809";
		gstrTO = "Verify that when RegAdmin adds the user back to the region, previously selected 'Views Access' for the user within that region are reset.";
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
	
	/**********************************************************************
	'Description	:Verify that when the user is added back to the region,
	                 other users when searched for the added user in that
	                  region, the user should be listed.
	'Arguments		:None
	'Returns		:None
	'Date			:12/19/2012
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date				                               Modified By
	'Date					                                   Name
	***********************************************************************/

	@Test
	public void testFTS32813() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();
		InstantMessaging objInstant = new InstantMessaging();
		RegionalInfo objRegionalInfo = new RegionalInfo();
		General objGeneral = new General();
		try {
			gstrTCID = "32813"; // Test Case Id
			gstrTO = " Verify that when the user is added back to the region, other users when"
					+ " searched for the added user in that region, the user should be listed.";// TO
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
			String strRegn1 = rdExcel.readData("Regions", 3, 5);
			String strRegn2 = rdExcel.readData("Regions", 4, 5);
			String strRegionValues[] = new String[2];

			// ST
			String statrNumTypeName = "NST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[1];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			// RT
			String strResrctTypName = "AutoRT" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strChatUser = "AutoChatusr" + System.currentTimeMillis();
			String strChatUserSearch = "autochatusr"
					+ System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strChatUsrFN = "FN" + strChatUser;
			String strEMail = rdExcel.readData("WebMailUser", 3, 1);

			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
			String strViewValue[] = new String[1];

		/*
		 * STEP : Action:Preconditions: 1. Instant Messaging is enabled for
		 * regions 'R1' and 'R2' 2. User U1 is created with primary email,
		 * email and pager address in region 'R1'. 3. User U1 has role
		 * 'ROL1' in region 'R1'. 4. User U1 has 'Update Status', 'Run
		 * Report' and 'Associated with' right on the resource 'RS' in
		 * region 'R1'. 5. A region view V1 is created selecting 'RS' and
		 * associated status types in region 'R1'. 6. User U1 has access to
		 * the regions 'R1' and 'R2'. Expected Result:No Expected Result
		 */

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + " EXECUTION STATRTS~~~~~");

		
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn2);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn1);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
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
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
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
				strFuncResult = objResourceTypes
						.resrcTypeMandatoryFldsNew(seleniumPrecondition, strResrctTypName,
								str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
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
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						"Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(seleniumPrecondition,
						strResource, strHavBed, "", strAbbrv, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
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
			// user
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strResVal, true, true, true,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(seleniumPrecondition,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						strRegionValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strChatUser, strInitPwd, strConfirmPwd, strChatUsrFN);
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
			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_roleStatusTypeValues[0] };
				String[] strReSValue = { strResVal };
				strFuncResult = objViews.createView(seleniumPrecondition, strViewName,
						strVewDescription, strViewType, false, false,
						strSTvalue, false, strReSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strViewValue[0] = objViews.fetchViewValueInViewList(seleniumPrecondition,
						strViewName);
				if (strViewValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch View value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + "  EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		 * STEP : Action:Login as RegAdmin to region 'R1'. Expected
		 * Result:No Expected Result
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium,
						strLoginUserName, strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Setup >> Users Expected Result:'Users
		 * List' screen is displayed
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1 Expected Result:'Edit User Regions' screen is displayed
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Remove the access from the region 'R1' by
		 * deselecting the check box and click on button 'Save' Expected
		 * Result:User is taken back to 'Users List' screen. User U1 is not
		 * listed in the 'Users List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName, false, false);
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
		 * STEP : Action:Provide the access back to the region 'R1' by
		 * selecting the check box and click on button 'Save' Expected
		 * Result:User is taken back to 'Users List' screen. User U1 is
		 * listed in the 'User List' screen.
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
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
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
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
		 * STEP : Action:Provide the user name U1 in the search field and
		 * click on button 'Search'. Expected Result:User U1 is retrieved.
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
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
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.InstantMessagingInitiateChatSession");
				strFuncResult = objCreateUsers.advancedOptns(selenium,
						strOptions, true);
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
		 * STEP : Action:Select role 'ROL1' from 'Any Role' dropdown and
		 * click on button 'Search'. Expected Result:User U1 is not
		 * retrieved.
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = strRolesName;
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				Thread.sleep(10000);
				try{
					assertEquals("No users found in this region",
							selenium.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is not retrieved.");
				} catch (AssertionError Ae) {
					log4j.info("No users found in this region Message is NOT displayed.");
					strFuncResult = "No users found in this region Message is NOT displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select 'Resource Name' in the search drop down,
		 * provide the resource name 'RS' in the search field and click on
		 * the button 'Search'. Expected Result:User U1 is not retrieved.
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 8,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strResource,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try{
					assertEquals("No users found in this region",
							selenium.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is not retrieved.");
				} catch (AssertionError Ae) {
					log4j.info("No users found in this region Message is NOT displayed.");
					strFuncResult = "No users found in this region Message is NOT displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Regional Info >> Users. Expected
		 * Result:User U1 is listed in the 'User List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.vrfyUsrNameAndFNInRegInfo(
						selenium, strUserName, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select 'Resource Type' RT in the search drop down,
		 * click on the button 'Search'. Expected Result:User U1 is not
		 * retrieved.
		 */
			try {
				assertEquals("", strFuncResult);
				String strByResourceType = strResrctTypName;
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try{
					assertEquals("No users found in this region",
							selenium.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is not retrieved.");
				} catch (AssertionError Ae) {
					log4j.info("No users found in this region Message is NOT displayed.");
					strFuncResult = "No users found in this region Message is NOT displayed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select 'Email Address' in the search drop down,
		 * provide e-mail address of user U1 in the search field and click
		 * on the button 'Search' Expected Result:User U1 is retrieved.
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 6,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strEMail, strByRole,
								strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName, true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Setup >> Resources Expected
		 * Result:'Resource List' screen is displayed. Resource 'RS' is
		 * listed in the 'Resource List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the link 'users' associated with the
		 * resource 'RS'. Expected Result:'Assign Users to Resource' screen
		 * is displayed. User U1 is listed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tbl_association']/tbody/tr/td[text()='"
						+ strUserName + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				log4j.info("User " + strUserName + "is listed.");
			} catch (AssertionError Ae) {
				log4j.info("User " + strUserName + "is NOT listed.");
				strFuncResult = "User " + strUserName + "is NOT listed.";
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to View, Click on view 'V1'. Expected
		 * Result:'Region Views List' screen is displayed. View 'V1' screen
		 * is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on resource 'RS' link. Expected Result:'View
		 * Resource Detail' screen is displayed. User U1 is not listed under
		 * the section 'Contacts'.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				if(strFuncResult.equals(""))
				{
				assertFalse(selenium
						.isElementPresent("//table[@id='contacts']/tbody/tr/td[text"
								+ "()='" + strUsrFulName + "']"));
				log4j.info("User " + strUserName
						+ "is NOT listed under the section 'Contacts'.");
				}else{
					log4j.info("User " + strUserName
							+ "is listed under the section 'Contacts'.");
					strFuncResult = "User " + strUserName
							+ "is listed under the section 'Contacts'.";
				}
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
		 * STEP : Action:Click on IM. Expected Result:'Instant Messaging'
		 * screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant.navInstantMesgingPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on button 'New Private Chat'. Expected
		 * Result:'Create New Chat (1 of 2)' window is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant.navCreateNewPrivateChatPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select 'Username' in the find user(s) drop down,
		 * provide user U1 and click on button 'Next'. Expected
		 * Result:'Create New Chat (2 of 2)' window is displayed. User U1 is
		 * listed.
		 */
			try {
				assertEquals("", strFuncResult);
				String strLabel = "Username";
				String strSearchText = strChatUser;
				strFuncResult = objInstant.navCreateNewPrivateChat2Of2Pge(
						selenium, strLabel, strSearchText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				if (strFuncResult.equals("")) {
					String strElementID="//div[@id='createNewPrivateChatDialog_"
									+ "step2_withResults']/select/option[@value='"
									+ strChatUserSearch + "']";
					strFuncResult = objGeneral.CheckForElements(selenium,
							strElementID);
					log4j.info("User " + strChatUserSearch + "  is listed.");
					selenium.click("id=createNewPrivateChatDialog_closeButton");
				} else {
					log4j.info("User " + strChatUserSearch + " is NOT listed.");
					strFuncResult = "User " + strChatUserSearch + " is NOT listed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on button 'New Conference', enter mandatory
		 * data and Click on button 'Create Conference'. Expected Result:New
		 * Conference is created.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant.navNewConference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRoomName = "Confhall";
				strFuncResult = objInstant.CreateConference(selenium,
						strRoomName, strStatTypDefn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on button 'Invite'. Expected Result:'Invite
		 * to Conference (1 of 2)' window is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant
						.navInviteToConference1Of2Page(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select 'Username' in the find user(s) drop down,
		 * provide user U1 and click on button 'Next'. Expected
		 * Result:'Create New Chat (2 of 2)' window is displayed. User U1 is
		 * retrieved.
		 */
			try {
				assertEquals("", strFuncResult);
				String strLabel = "Username";
				String strSearchText = strChatUser;
				strFuncResult = objInstant.navToInviteConference2Of2Pge(
						selenium, strLabel, strSearchText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				if (strFuncResult.equals("")) {
					String strElementID = "//div[@id='inviteMemberDialog_step2_withResults']" +
							"/div/div/input[@value='"+strChatUserSearch+"']";
					strFuncResult = objGeneral.CheckForElements(selenium,
							strElementID);
					log4j.info("User " + strChatUserSearch + "  is listed.");
					selenium.click("id=inviteMemberDialog_closeButton");
					Thread.sleep(5000);
				} else {
					log4j.info("User " + strChatUserSearch + " is NOT listed.");
					strFuncResult = "User " + strChatUserSearch
							+ " is NOT listed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP : Action:Login as RegAdmin to region 'R2'. Expected
		 * Result:No Expected Result
		 */

			try {
				assertEquals("", strFuncResult);
				try {
					selenium.open(propEnvDetails.getProperty("urlEU"));
				} catch (Exception e) {

				}
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Setup >> Users. Expected Result:'Users
		 * List' screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1. Expected Result:'Edit User Regions' screen is displayed.
		 * Check box associated with the region 'R1' is selected. Check box
		 * associated with the region 'R2' is selected.
		 */
			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[0], strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[1], strRegn1);
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
			gstrTCID = "32813";
			gstrTO = "Verify that when the user is added back to the region, other users when searched" +
					" for the added user in that region, the user should be listed.";
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
	/***************************************************************
	'Description  :Verify that after removing the user from a region,
	               when other users searches for the removed user in 
	               that region, the user should not be listed.
	'Arguments	  :None
	'Returns	  :None
	'Date		  :12/19/2012
	'Author		  :QSG
	'---------------------------------------------------------------
	'Modified Date				                         Modified By
	'Date					                             Name
	***************************************************************/

	@Test
	public void testFTS32801() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objResourceTypes = new ResourceTypes();
		Resources objResources = new Resources();
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();
		InstantMessaging objInstant = new InstantMessaging();
		RegionalInfo objRegionalInfo = new RegionalInfo();
		General objGeneral = new General();
		try {
			gstrTCID = "32801"; // Test Case Id
			gstrTO = " Verify that after removing the user from a region, when other users searches for the"
					+ " removed user in that region, the user should not be listed.";// Test
																						// Objective
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
			String strRegn1 = rdExcel.readData("Login", 3, 4);
			String strRegn2 = rdExcel.readData("Regions", 4, 5);
			String strRegionValues[] = new String[2];

			// ST
			String statrNumTypeName = "NST" + strTimeText;
			String str_roleStatusTypeValues[] = new String[1];
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			// RT
			String strResrctTypName = "AutoRT" + strTimeText;
			String strRsTypeValues[] = new String[1];
			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strHavBed = "No";
			String strResVal = "";
			String strRSValues[] = new String[1];
			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strChatUser = "AutoChatusr" + System.currentTimeMillis();
			String strChatUserSearch = "autochatusr"
					+ System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "FN" + strUserName;
			String strChatUsrFN = "FN" + strChatUser;
			String strEMail = rdExcel.readData("WebMailUser", 3, 1);

			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
			String strViewValue[] = new String[1];

		/*
		 * STEP : Action:Preconditions: 1. Instant Messaging is enabled for
		 * regions 'R1' and 'R2' 2. User U1 is created with primary email,
		 * email and pager address in region 'R1'. 3. User U1 has role
		 * 'ROL1' in region 'R1'. 4. User U1 has 'Update Status', 'Run
		 * Report' and 'Associated with' right on the resource 'RS' in
		 * region 'R1'. 5. A region view V1 is created selecting 'RS' and
		 * associated status types in region 'R1'. 6. User U1 has access to
		 * the regions 'R1' and 'R2'. Expected Result:No Expected Result
		 */

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(
						seleniumPrecondition, strRegn2);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(
						seleniumPrecondition, strRegn1);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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

			// status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
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
						seleniumPrecondition, strResrctTypName,
						str_roleStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(
						seleniumPrecondition, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[0] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrctTypName);
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
				strFuncResult = objResources
						.navToCreateResourcePage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWithMandFields(
						seleniumPrecondition, strResource, strAbbrv,
						strResrctTypName, "Hospital", "FN", "LN");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndNavToAssignUsr(
						seleniumPrecondition, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.saveAndVerifyResource(
						seleniumPrecondition, strResource, strHavBed, "",
						strAbbrv, strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(
						seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValues[0] = strResVal;
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
				strFuncResult = objRoles.CreateRoleWithAllFields(
						seleniumPrecondition, strRolesName, strRoleRights,
						strViewRightValue, false, updateRightValue, false,
						false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			// user
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
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail,
						strEMail, strEMail, "");
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
						seleniumPrecondition, strResource, strResVal, true,
						true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(
						seleniumPrecondition, strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(
						seleniumPrecondition, strRegionValues[0], true, false);
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

			// view1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_roleStatusTypeValues[0] };
				String[] strReSValue = { strResVal };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, false,
						false, strSTvalue, false, strReSValue);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strViewValue[0] = objViews.fetchViewValueInViewList(
						seleniumPrecondition, strViewName);
				if (strViewValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch View value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + "  EXECUTION ENDS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strChatUser, strInitPwd, strConfirmPwd, strChatUsrFN);
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
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objCreateUsers.navEditUserPge(seleniumPrecondition,
						strUserName, strByRole, strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn." +
								"InstantMessagingInitiateChatSession");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STATRTS~~~~~");
		/*
		 * STEP : Action:Login as RegAdmin to region 'R1'. Expected
		 * Result:No Expected Result
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Setup >> Users Expected Result:'Users
		 * List' screen is displayed
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1 Expected Result:'Edit User Regions' screen is displayed
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Remove the access from the region 'R1' by
		 * deselecting the check box and click on button 'Save' Expected
		 * Result:User is taken back to 'Users List' screen. User U1 is not
		 * listed in the 'Users List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
		}
		/*
		 * STEP : Action:Provide the user name U1 in the search field and
		 * click on button 'Search'. Expected Result:User U1 is not
		 * retrieved.
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals("No users found in this region", selenium
							.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is NOT retrieved.");
				} catch (AssertionError Ae) {
					log4j.info("User " + strUserName + " is retrieved.");
					strFuncResult = "User " + strUserName + " is  retrieved.";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select role 'ROL1' from 'Any Role' dropdown and
		 * click on button 'Search'. Expected Result:User U1 is not
		 * retrieved.
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = strRolesName;
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				Thread.sleep(10000);
				assertEquals("", strFuncResult);
				try {
					assertEquals("No users found in this region", selenium
							.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is NOT retrieved.");
				} catch (AssertionError Ae) {
					log4j
							.info("User " + strUserName + " is retrieved.");
					strFuncResult = "User " + strUserName + " is  retrieved.";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select 'Resource Name' in the search drop down,
		 * provide the resource name 'RS' in the search field and click on
		 * the button 'Search'. Expected Result:User U1 is not retrieved.
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 8,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strResource,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals("No users found in this region", selenium
							.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is NOT retrieved.");
				} catch (AssertionError Ae) {
					log4j
							.info("User " + strUserName + " is retrieved.");
					strFuncResult = "User " + strUserName + " is  retrieved.";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Regional Info >> Users. Expected
		 * Result:User U1 is listed in the 'User List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.navRegionalInfoUsrPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegionalInfo.vrfyUsrNameAndFNInRegInfo(
						selenium, strUserName, strUsrFulName);
				if (strFuncResult
						.equals("Updated data "
								+ strUsrFulName
								+ "is NOT displayed for the user in the 'Users List'screen.")) {
					strFuncResult = "";
				} else {
					strFuncResult = "Updated data " + strUsrFulName
							+ " is  displayed "
							+ "for the user in the 'Users List'screen.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select 'Resource Type' RT in the search drop down,
		 * click on the button 'Search'. Expected Result:User U1 is not
		 * retrieved.
		 */
			try {
				assertEquals("", strFuncResult);
				String strByResourceType = strResrctTypName;
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				try {
					assertEquals("No users found in this region", selenium
							.getText("id=noUsers"));
					log4j.info("User " + strUserName + " is NOT retrieved.");
				} catch (AssertionError Ae) {
					log4j
							.info("User " + strUserName + " is retrieved.");
					strFuncResult = "User " + strUserName + " is  retrieved.";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Select 'Email Address' in the search drop down,
		 * provide e-mail address of user U1 in the search field and click
		 * on the button 'Search' Expected Result:User U1 is not retrieved. 
		 */

			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 6,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strEMail, strByRole,
								strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.verifyUsrInUserListPage(
						selenium, strUserName, false, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Setup >> Resources Expected
		 * Result:'Resource List' screen is displayed. Resource 'RS' is
		 * listed in the 'Resource List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.VerifyResource(selenium,
						strResource, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the link 'users' associated with the
		 * resource 'RS'. Expected Result:'Assign Users to Resource' screen
		 * is displayed. User U1 is listed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tbl_association']/tbody/tr/td[text()='"
						+ strUserName + "']";
				strFuncResult = objGeneral.CheckForElements(selenium,
						strElementID);
				if(strFuncResult.equals("Element NOT Present")){
					log4j.info("User " + strUserName + "is NOT listed.");
					strFuncResult="";
				}else{
					log4j.info("User " + strUserName + "is listed.");
					strFuncResult="User " + strUserName + "is listed.";
				}
				
			} catch (AssertionError Ae) {
			
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to View, Click on view 'V1'. Expected
		 * Result:'Region Views List' screen is displayed. View 'V1' screen
		 * is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navRegionViewsList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToUserView(selenium, strViewName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on resource 'RS' link. Expected Result:'View
		 * Resource Detail' screen is displayed. User U1 is not listed under
		 * the section 'Contacts'.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.navToViewResourceDetailPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				assertFalse(selenium
						.isElementPresent("//table[@id='contacts']/tbody/tr/td[text"
								+ "()='" + strUsrFulName + "']"));
				log4j.info("User " + strUserName
						+ "is NOT listed under the section 'Contacts'.");
			} catch (AssertionError Ae) {
				log4j.info("User " + strUserName
						+ "is listed under the section 'Contacts'.");
				strFuncResult = "User " + strUserName
						+ "is listed under the section 'Contacts'.";
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on IM. Expected Result:'Instant Messaging'
		 * screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant.navInstantMesgingPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on button 'New Private Chat'. Expected
		 * Result:'Create New Chat (1 of 2)' window is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant.navCreateNewPrivateChatPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Select 'Username' in the find user(s) drop down,
		 * provide user U1 and click on button 'Next'. Expected
		 * Result:'Create New Chat (2 of 2)' window is displayed. User U1 is
		 * listed.
		 */
			try {
				assertEquals("", strFuncResult);
				String strLabel = "Username";
				String strSearchText = strChatUser;
				strFuncResult = objInstant.navCreateNewPrivateChat2Of2Pge(
						selenium, strLabel, strSearchText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				if (strFuncResult.equals("")) {
					String strElementID="//div[@id='createNewPrivateChatDialog_"
									+ "step2_withResults']/select/option[@value='"
									+ strChatUserSearch + "']";
					strFuncResult = objGeneral.CheckForElements(selenium,
							strElementID);
					log4j.info("User " + strChatUserSearch + "  is listed.");
					selenium.click("id=createNewPrivateChatDialog_closeButton");
				} else {
					log4j.info("User " + strChatUserSearch + " is NOT listed.");
					strFuncResult = "User " + strChatUserSearch + " is NOT listed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Click on button 'New Conference', enter mandatory
		 * data and Click on button 'Create Conference'. Expected Result:New
		 * Conference is created.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objInstant.navNewConference(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strRoomName = "Confhall";
				strFuncResult = objInstant.CreateConference(selenium,
						strRoomName, strStatTypDefn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on button 'Invite'. Expected Result:'Invite
		 * to Conference (1 of 2)' window is displayed.
		 */
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objInstant
							.navInviteToConference1Of2Page(selenium);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

		/*
		 * STEP : Action:Select 'Username' in the find user(s) drop down,
		 * provide user U1 and click on button 'Next'. Expected
		 * Result:'Create New Chat (2 of 2)' window is displayed. User U1 is
		 * retrieved.
		 */
			try {
				assertEquals("", strFuncResult);
				String strLabel = "Username";
				String strSearchText = strChatUser;
				strFuncResult = objInstant.navToInviteConference2Of2Pge(
						selenium, strLabel, strSearchText);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				if (strFuncResult.equals("")) {
					String strElementID = "//div[@id='inviteMemberDialog_step2_withResults']/"
							+ "div/div/input[@Value='"
							+ strChatUserSearch
							+ "']";
					strFuncResult = objGeneral.CheckForElements(selenium,
							strElementID);
					log4j.info("User " + strChatUserSearch + "  is listed.");
					selenium.click("id=inviteMemberDialog_closeButton");
					Thread.sleep(5000);
				} else {
					log4j.info("User " + strChatUserSearch + " is NOT listed.");
					strFuncResult = "User " + strChatUserSearch
							+ " is NOT listed.";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
		/*
		 * STEP : Action:Login as RegAdmin to region 'R2'. Expected
		 * Result:No Expected Result
		 */

			try {
				assertEquals("", strFuncResult);
				try {
					selenium.open(propEnvDetails.getProperty("urlEU"));
				} catch (Exception e) {

				}
				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		/*
		 * STEP : Action:Navigate to Setup >> Users. Expected Result:'Users
		 * List' screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1. Expected Result:'Edit User Regions' screen is displayed.
		 * Check box associated with the region 'R1' is deselected. Check
		 * box associated with the region 'R2' is selected.
		 */
			try {
				assertEquals("", strFuncResult);
				String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				String strByResourceType = rdExcel.readInfoExcel(
						"Precondition", 3, 10, strFILE_PATH);
				String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3,
						11, strFILE_PATH);
				String strNameFormat = rdExcel.readInfoExcel("Precondition", 4,
						12, strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[0], strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						false, strRegionValues[1], strRegn1);
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
			gstrTCID = "32801"; // Test Case Id
			gstrTO = " Verify that after removing the user from a region, when other users searches for the"
					+ " removed user in that region, the user should not be listed.";
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
	/*********************************************************************
	'Description	:Verify that when the user is added back to the region,
	                 the previously set (before removing the region access) 
	    'Event notifications' are NOT retained for the user in that region.
	'Arguments		:None
	'Returns		:None
	'Date			:12/18/2012
	'Author			:QSG
	'----------------------------------------------------------------------
	'Modified Date				                               Modified By
	'Date					                                   Name
	***********************************************************************/

	@Test
	public void testFTS32810() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		General objMail = new General();
		CreateUsers objCreateUsers = new CreateUsers();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();
		EventSetup objEventSetup = new EventSetup();
		EventNotification objEventNotification = new EventNotification();
		try {
			gstrTCID = "32810"; // Test Case Id
			gstrTO = "Verify that when the user is added back to the region, the previously set (before "
					+ "removing the region access) 'Event notifications' are NOT retained for the user in that region.";																													// Objective
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
			String strRegn1 = rdExcel.readData("Regions", 3, 5);
			String strRegn2 = rdExcel.readData("Regions", 4, 5);
			String strRegionValues[] = new String[2];
			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strEMail = rdExcel.readData("WebMailUser", 2, 1);

			String strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
					10, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
					strFILE_PATH);
            //Template
			String strTempName = "Autotmp1" + strTimeText;
			String strTempDef = "Automation";
			// Event
			String strEveName = "AutoEve_1" + strTimeText;
			
			// Web mail user details
			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strCurrDate = "";
			String strStartDate = "";
			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";
			String strSubjName = "";
		/*
		 * STEP : Action:Preconditions: 1. User U1 has access to the regions
		 * 'R1' and 'R2'. 2. User U1 is created with primary email, email
		 * and pager address. 3. Event template ET is created in region
		 * 'R2'. 4. For user U1 select to receive Email, pager and web
		 * notifications for event template ET. Expected Result:No Expected
		 * Result
		 */
		// 193952
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID+ " EXECUTION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn1);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(seleniumPrecondition,
						strRegn2);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName, strInitPwd, strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.nonMandatoryUsrProfileFlds(
						seleniumPrecondition, "", "", "", "", "", strEMail, strEMail,
						strEMail, "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(seleniumPrecondition,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(seleniumPrecondition,
						strRegionValues[0], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventSetupPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventTemplate(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.CreateETByMandFieldsByAdminAndUser(
						seleniumPrecondition, strTempName, strTempDef, false,true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventNotification.selectEventNofifForUser(
						seleniumPrecondition, strUsrFulName, strTempName,
						true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID + "  EXECUTION ENDS~~~~~");
			log4j.info("~~~~~TEST CASE " + gstrTCID+ " EXECUTION STATRTS~~~~~");
		/*
		 * STEP : Action:Login as RegAdmin to region 'R1'. Expected
		 * Result:No Expected Result
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to Setup >> Users Expected Result:'Users
		 * List' screen is displayed
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1 Expected Result:'Edit User Regions' screen is displayed
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Remove the access from the region 'R2' by
		 * deselecting the check box and Click on button 'Save' Expected
		 * Result:User is taken back to 'Users List' screen.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, false);
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
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1 Expected Result:'Edit User Regions' screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Provide back access to the region 'R2' by selecting
		 * the check box and click on button 'Save' Expected Result:User is
		 * taken back to 'Users List' screen.
		 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], true, false);
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
		 * STEP : Action:Click on the link 'Regions' associated with the
		 * user U1 Expected Result:'Edit User Regions' screen is displayed.
		 * Check box corresponding to region 'R2' is selected. Check box
		 * corresponding to region 'R1' remains checked.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[0], strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[1], strRegn2);
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
		 * STEP : Action:Login as RegAdmin to region 'R2'. Expected Result:
		 * No Expected Result
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Navigate to 'Event >> Event Management'. Expected
		 * Result: 'Event Management' screen is displayed.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.navToEventManagementNew(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.createEventMandFlds(selenium,
						strTempName, strEveName, strTempDef, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		 * STEP : Action:Provide mandatory data and click on 'Save' Expected
		 * Result:Event EV1 is created and listed in 'Event Management'
		 * screen. E-mail and pager Event notifications are not received.
		 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objEventSetup.saveAndvrfyEvent(selenium,
						strEveName, true);
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

				Thread.sleep(30000);

				strSubjName = strEveName;

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strSubjName = strEveName;

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));

						strFuncResult = "";
					} catch (AssertionError Ae) {
						gstrReason = gstrReason + " " + strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			selenium.selectWindow("Mail");
			selenium.selectFrame("horde_main");
			selenium.click("link=Log out");
			selenium.waitForPageToLoad("90000");
			selenium.close();

			selenium.selectWindow("");
			selenium.selectWindow("");
			selenium.selectFrame("Data");
			
		/*
		 * STEP : Action:Login as user U1 to region 'R1' Expected
		 * Result:Event Web notifications are not received
		 */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				
				strStartDate = dts.converDateFormat(strFndStYear + strFndStMnth
						+ strFndStDay, "yyyyMMMdd", "M/d/yyyy");

				strCurrDate = dts.converDateFormat(strStartDate, "M/d/yyyy",
						"dd MMM yyyy");
				strStartDate = strStartDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				strCurrDate = strCurrDate + " " + strFndStHr + ":"
						+ strFndStMinu;

				String strSTDateTime = strStartDate;

				String strDesc = strEveName + "\n" + strTempDef;
						
				strFuncResult = objEventNotification.ackSTWebNotificationNew(
						selenium, strEveName, strSTDateTime, strDesc);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*
		 * STEP : Action:Launch EMR application on mobile device (Ex: ipod
		 * touch) and login as user U1 Expected Result:'Main Menu' screen is
		 * displayed. 'Select Region' on the 'Main Menu' screen is not
		 * displayed.
		 */
			try {
				assertEquals(
						"Web Notification is NOT displayed",
						strFuncResult);
				gstrResult = "PASS";
				// Write result data
				String strTestData[] = new String[10];
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strInitPwd;
				strTestData[3] = "Verify 14th Step.";
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "User");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "32810"; // Test Case Id
			gstrTO = "Verify that when the user is added back to the region, the previously set (before "
					+ "removing the region access) 'Event notifications' are NOT retained for the user in that region."; // Objective
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
	//start//testFTS32811//
	/***************************************************************
	'Description		:Verify that when the user is added back to the region, the previously set (before removing the region access) 'Status Change Preferences' are NOT retained for the user in that region.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:9/13/2013
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@SuppressWarnings("unused")
	@Test
	public void testFTS32811() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		Roles objRoles = new Roles();
		Preferences objPreferences = new Preferences();
		General objMail = new General();
		Regions objRegions = new Regions();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		ViewMap objViewMap = new ViewMap();
		EventNotification objEventNotification = new EventNotification();

		try {
			gstrTCID = "32811"; // Test Case Id
			gstrTO = " Verify that when the user is added back to the region, the previously set (before removing the region access) 'Status Change Preferences' are NOT retained for the user in that region.";// Test
																																																				// Objective
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MM/dd/yyyy-hhmmss");
			String strTimeTxt = dts.getCurrentDate("MMddyyyy");

			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Region data
			String strRegionName = rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];

			// Status types
			String strStatTypDefn = "Automation";
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strNDSTValue = "NEDOCS Calculation";

			// role based
			String statrNumTypeName = "AutoNST" + strTimeText;
			String statrTextTypeName = "AutoTST" + strTimeText;
			String statrMultiTypeName = "AutoMST" + strTimeText;
			String statrSaturatTypeName = "AutoSST" + strTimeText;
			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String str_roleStatusTypeValues[] = new String[5];

			String strStatTypeColor = "Black";
			String str_roleStatusName1 = "rSa" + strTimeTxt;
			String str_roleStatusValue[] = new String[2];

			// USER
			String strUserName = "AutoUsr" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "autouser";
			String strUserName2="AutoUsr2" + System.currentTimeMillis();
			

			// RT
			String strResType = "AutoRt_" + strTimeText;
			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[1];

			// Role
			String strRolesName = "AutoRol1" + System.currentTimeMillis();
			String strRoleValue = "";

			// View
			String strViewName = "AutoV_" + System.currentTimeMillis();
			String strVewDescription = "";
			String strViewType = "Resource (Resources and status "
					+ "types as rows. Status, comments and timestamps as columns.)";
			String strViewValue[] = new String[1];

			// Search criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strAbove = "100";
			String strBelow = "50";

			String strSaturAbove = "400";
			String strSaturBelow = "200";

			String strFndStMnth = "";
			String strFndStYear = "";
			String strFndStDay = "";
			String strFndStHr = "";
			String strFndStMinu = "";

			String strStartDate = "";
			String strCurrDate = "";
			String strCurrDate2 = "";

			int intResCnt = 0;

			String strLoginName = rdExcel.readData("WebMailUser", 2, 1);
			String strPassword = rdExcel.readData("WebMailUser", 2, 2);
			String strFrom = "notification@emsystems.com";
			String strTo = strLoginName;
			String strSubjName = "";
			String RSandSRS = "";

			/*
			 * STEP : Action:Preconditions: 1. User U1 has access to the regions
			 * 'R1' and 'R2'. 2. Resource RS is created selecting RT which is
			 * associated with all 4 status types NST, MST, TST and SST. 3. A
			 * region view V1 is created selecting RS and above mentioned 4
			 * status types. 4. User U1 is created with 'Update Status' right on
			 * resource RS, role to update all 4 status types. 5. 'Status Change
			 * Preferences' for all the 4 status types are set and enabled to
			 * receive e-mail, web and pager notifications. Expected Result:No
			 * Expected Result
			 */
			// 194258

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				strLoginUserName = rdExcel.readData("Login", 3, 1);
				strLoginPassword = rdExcel.readData("Login", 3, 2);
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
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(
						seleniumPrecondition, strRegionName);
				if (strRegionValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValues[1] = objRegions.fetchRegionValue(
						seleniumPrecondition, strRegn);
				if (strRegionValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Status Types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes
						.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Role based status type
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statrNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NST
			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNumTypeName);
				if (str_roleStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statrTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrTextTypeName);
				if (str_roleStatusTypeValues[1].compareTo("") != 0) {
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
						seleniumPrecondition, strSSTValue,
						statrSaturatTypeName, strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[2] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrSaturatTypeName);
				if (str_roleStatusTypeValues[2].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statrMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[3] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrMultiTypeName);
				if (str_roleStatusTypeValues[3].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName,
						str_roleStatusName1, strMSTValue, strStatTypeColor,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusValue[0] = objStatusTypes
						.fetchStatValInStatusList(seleniumPrecondition,
								statrMultiTypeName, str_roleStatusName1);
				if (str_roleStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				str_roleStatusTypeValues[4] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition,
								statrNedocTypeName);
				if (str_roleStatusTypeValues[4].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status type  value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RT

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.resrcTypeMandatoryFlds(
						seleniumPrecondition, strResType,
						"css=input[name='statusTypeID'][value='"
								+ str_roleStatusTypeValues[0] + "']");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			for (int i = 1; i < str_roleStatusTypeValues.length; i++) {

				try {
					assertEquals("", strFuncResult);
					strFuncResult = objRT.selAndDeselSTInEditRT(
							seleniumPrecondition, str_roleStatusTypeValues[i],
							true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Resource
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
						strResType, strContFName, strContLName, strState,
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

			// Role

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRoles.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.rolesMandatoryFlds(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = {
						{ str_roleStatusTypeValues[0], "true" },
						{ str_roleStatusTypeValues[1], "true" },
						{ str_roleStatusTypeValues[2], "true" },
						{ str_roleStatusTypeValues[3], "true" },
						{ str_roleStatusTypeValues[4], "true" } };
				strFuncResult = objRoles.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTvalues,
						strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.savAndVerifyRoles(
						seleniumPrecondition, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRoles.fetchRoleValueInRoleList(
						seleniumPrecondition, strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// View1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews
						.navRegionViewsList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTvalue = { str_roleStatusTypeValues[0],
						str_roleStatusTypeValues[1],
						str_roleStatusTypeValues[2],
						str_roleStatusTypeValues[3],
						str_roleStatusTypeValues[4] };
				String[] strReSValue = { strRSValue[0] };
				strFuncResult = objViews.createView(seleniumPrecondition,
						strViewName, strVewDescription, strViewType, false,
						false, strSTvalue, false, strReSValue);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strViewValue[0] = objViews.fetchViewValueInViewList(
						seleniumPrecondition, strViewName);
				if (strViewValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch View value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// user1
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
						seleniumPrecondition, strUserName, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditStatusChangNotiPrefer");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//USER2
			
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
						seleniumPrecondition, strUserName2, strInitPwd,
						strConfirmPwd, strUsrFulName);
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
						seleniumPrecondition, strResource, strRSValue[0],
						false, true, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName2, strByRole,
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition,
						strUserName, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// My status change preferences
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navMyStatusTypeChangePreference(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.addResourcesAndNavToEditSTNotfPreferences(
								seleniumPrecondition, strResource,
								strRSValue[0]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strSTName = { statrNumTypeName, statrTextTypeName,
						statrMultiTypeName, statrSaturatTypeName,
						statrNedocTypeName };
				strFuncResult = objPreferences
						.verifySTInInEditMySTPrfPageInUncategorizedSection(
								seleniumPrecondition, strSTName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Above Below values

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[0],
								statrNumTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(
								seleniumPrecondition, strAbove, strBelow,
								strRSValue[0], str_roleStatusTypeValues[0],
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// SST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPage(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[2],
								statrSaturatTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(
								seleniumPrecondition, strSaturAbove,
								strSaturBelow, strRSValue[0],
								str_roleStatusTypeValues[2], true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// MST

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageOfMST(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[3],
								statrMultiTypeName, str_roleStatusValue[0],
								true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// NEDOC

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifInEditMySTNotifPageBelow(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[4],
								statrNedocTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.provideSTAboveBelowRangeInEditMySTNotifPage(
								seleniumPrecondition, strAbove, strBelow,
								strRSValue[0], str_roleStatusTypeValues[4],
								true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// TST

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.navEditMySatPrefPgeOfPartcularRS(seleniumPrecondition,
								strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objPreferences
						.selectSTChangeNotifforTxtStatusType(
								seleniumPrecondition, strResource,
								strRSValue[0], str_roleStatusTypeValues[1],
								statrTextTypeName, true, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");

			/*
			 * STEP : Action:Login as RegAdmin to region 'R1'. Expected
			 * Result:No Expected Result
			 */
			// 194259

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
			 * STEP : Action:Navigate to Setup >> Users Expected Result:'Users
			 * List' screen is displayed
			 */
			// 194260

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Click on the link 'Regions' associated with the
			 * user U1 Expected Result:'Edit User Regions' screen is displayed
			 */
			// 194261

			try {
				assertEquals("", strFuncResult);
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
						strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Remove the access from the region 'R2' by
			 * deselecting the check box and Click on button 'Save' Expected
			 * Result:User is taken back to 'Users List' screen.
			 */
			// 194262

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[0], true, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], false, false);
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
			 * STEP : Action:Click on the link 'Regions' associated with the
			 * user U1 Expected Result:'Edit User Regions' screen is displayed.
			 */
			// 194267

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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegionName);
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
				strByRole = rdExcel.readInfoExcel("Precondition", 3, 9,
						strFILE_PATH);
				strByResourceType = rdExcel.readInfoExcel("Precondition", 3,
						10, strFILE_PATH);
				strByUserInfo = rdExcel.readInfoExcel("Precondition", 3, 11,
						strFILE_PATH);
				strNameFormat = rdExcel.readInfoExcel("Precondition", 4, 12,
						strFILE_PATH);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);

			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			
			/*
			 * STEP : Action:Provide back access to the region 'R2' by selecting
			 * the check box and click on button 'Save' Expected Result:User is
			 * taken back to 'Users List' screen.
			 */
			// 194268

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.slectAndDeselectRegion(selenium,
						strRegionValues[1], true, false);
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
			 * STEP : Action:Click on the link 'Regions' associated with the
			 * user U1 Expected Result:'Edit User Regions' screen is displayed.
			 * Check box corresponding to region 'R2' is selected. Check box
			 * corresponding to region 'R1' remains checked.
			 */
			// 194263

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navEditUserRegions(selenium,
						strUserName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[0], strRegionName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.chkRegionSelectedOrNot(selenium,
						true, strRegionValues[1], strRegn);
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
			 * STEP : Action:Login as user who has update status rights on
			 * resource RS and role to update all four status types NST, MST,
			 * TST and SST. Expected Result:No Expected Result
			 */
			// 194264

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium,
						strUserName2, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			/*
			 * STEP : Action:From the view screen, update the status of NST,
			 * MST, TST and SST for the first time with a value greater than the
			 * specified value for 'Above' field. Expected Result:Pager and Web
			 * notifications are not received.
			 */
			// 194265

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navResPopupWindow(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.navToUpdateStatusPrompt(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"500", str_roleStatusTypeValues[0], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						"500", str_roleStatusTypeValues[1], false, "", "");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, str_roleStatusTypeValues[2]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};
				strFuncResult = objViews.fillUpdateNEDOCSTAndSave(selenium,
						strScUpdValue1, str_roleStatusTypeValues[4], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						str_roleStatusValue[0], str_roleStatusTypeValues[3],
						false, "", "");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			String strUpdatedDate = "";
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.saveUpdateStatusValue(selenium);
				strUpdatedDate = selenium.getText("css=#statusTime");
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
				
				strFuncResult = objLogin.login(selenium,
						strUserName, strInitPwd);
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
				
			
			// MAIL
			String strUpdate1 = "500";
			String strUpdate2 = "393";
			String strUpdate3 = str_roleStatusName1;
			String strUpdate4 = "241";

			try {
				assertEquals("", strFuncResult);
				log4j.info(strUpdatedDate);
				String strDate[] = strUpdatedDate.split(" ");
				for (String s : strDate) {
					log4j.info(s);
				}

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

				RSandSRS = strResource;

				String strDesc1[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrMultiTypeName + " status from -- to "
								+ strUpdate3 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ strTSTValue + " status from -- to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrMultiTypeName + " status from -- to "
								+ strUpdate4 + "." };

				String strDesc2[] = {
						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrNumTypeName + " status from 0 to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrSaturatTypeName + " status from 0 to "
								+ strUpdate2 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrMultiTypeName + " status from -- to "
								+ strUpdate3 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ strTSTValue + " status from -- to "
								+ strUpdate1 + ".",

						"On " + strCurrDate + " " + RSandSRS + " changed "
								+ statrMultiTypeName + " status from -- to "
								+ strUpdate4 + "." };

				String[] strTime = strSTDateTime.split(" ");
				String strAddedDtTime = dts.addTimetoExisting(strTime[1], 1,
						"HH:mm");

				log4j.info(strAddedDtTime);

				strAddedDtTime = strTime[0] + " " + strAddedDtTime;
				log4j.info(strAddedDtTime);

				strFuncResult = objEventNotification
						.ackSTWebNotificationForManyStatusTypes(selenium,
								strResource, strSTDateTime, strAddedDtTime,
								strDesc1, strDesc2);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Web Notification is NOT displayed", strFuncResult);
				intResCnt++;
				Thread.sleep(30000);

				selenium.selectWindow("");
				strFuncResult = objMail.loginAndnavToInboxInWebMail(selenium,
						strLoginName, strPassword);

				try {
					assertTrue(strFuncResult.equals(""));
					strSubjName = "Status Change for " + RSandSRS;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);
					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						strFuncResult = "";

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
						log4j.info("The mail with subject " + strSubjName
								+ " is present in the inbox");

					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
					log4j.info("The mail with subject " + strSubjName
							+ " is present in the inbox");
				}

				try {
					strSubjName = "Change for " + strAbbrv;
					strFuncResult = objMail.verifyEmail(selenium, strFrom,
							strTo, strSubjName);

					try {
						assertTrue(strFuncResult
								.equals("The mail with subject " + strSubjName
										+ " is NOT present in the inbox"));
						strFuncResult = "";

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
						log4j.info("The mail with subject " + strSubjName
								+ " is present in the inbox");

					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

				selenium.selectWindow("Mail");
				selenium.selectFrame("horde_main");
				selenium.click("link=Log out");
				selenium.waitForPageToLoad("90000");

				selenium.selectWindow("");
				selenium.selectFrame("Data");
				Thread.sleep(4000);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP : Action:Login as user U1 to region 'R1' Expected
			 * Result:Status change Web notifications are not received
			 */
			// 194266

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

		} catch (Exception e) {
			gstrTCID = "FTS-32811";
			gstrTO = "Verify that when the user is added back to the region, the previously set (before removing the region access) 'Status Change Preferences' are NOT retained for the user in that region.";
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

	// end//testFTS32811//
}
