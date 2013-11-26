package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
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
import com.qsgsoft.EMResource.shared.Regions;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.SearchUserByDiffCrteria;
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
' Description		:This class contains test cases from   requirement
' Requirement		:Activate System notice
' Requirement Group	:Setting up Regions 
� Product		    :EMResource v3.19
' Date			    :2/June/2012
' Author		    :QSG
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/

@SuppressWarnings("unused")
public class RemoveUserFromRegion {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.RemoveUserFromRegion");
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

		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}

		seleniumPrecondition.stop();
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
	'Description	:Verify that RegAdmin can remove access from regions for a user 
	                 who currently have access to multiple regions.
	'Precondition	:User U1 has access to the regions 'R1' and 'R2'
	           		 No Expected Result 
	'Arguments		:None
	'Returns		:None
	'Date	 		:4-April-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	***************************************************************/
	
	@Test
	public void testBQS32727() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();
		Regions objRegions = new Regions();

		try {
			gstrTCID = "BQS-32727 ";
			gstrTO = "Verify that RegAdmin can remove access from regions for a user"
					+ "who currently have access to multiple regions.";
			gstrResult = "FAIL";
			gstrReason = "";
			selenium.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strLoginUserName = "";
			String strLoginPassword = "";
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strRegionName = rdExcel.readData("Regions", 3, 5);
			String strRegionValues[] = new String[2];

			// User Data
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			String[] strRegionNames = { rdExcel.readData("Login", 4, 4) };
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
			String strUsrFulName = "FN" + strUserName;

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Region

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRegions.navRegionList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRegionValues[0] = objRegions.fetchRegionValue(seleniumPrecondition,
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
				strRegionValues[1] = objRegions.fetchRegionValue(seleniumPrecondition,
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
				strFuncResult = objCreateUsers
						.saveAndNavToUsrListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// searching user by different criteria
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(seleniumPrecondition, strUserName,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError ae) {
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
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITIION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			/*
			 * STEP 1:Login as RegAdmin to region 'R1'. No Expected Result
			 */

			/*
			 * STEP 3:Navigate to Setup >> Users 'Users List' screen is
			 * displayed
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
				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// Region

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * STEP 4:Click on the link 'Regions' associated with the user U1
			 * 'Edit User Regions' screen is displayed
			 */

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
			/*
			 * STEP 5:Remove the access from the region 'R2' by deselecting the
			 * check box and Click on button 'Save' User is taken back to 'Users
			 * List' screen.
			 */

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
			 * STEP 6:Click on the link 'Regions' associated with the user U1
			 * 'Edit User Regions' screen is displayed Check box corresponding
			 * to region 'R2' is deselected. Check box corresponding to region
			 * 'R1' remains checked.
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
						false, strRegionValues[0], strRegionName);
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
			 * STEP 7:Login as user U1. Region default view of region 'R1' is
			 * displayed.
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
				assertEquals(strRegn, selenium.getText("id=regionName"));
				log4j.info("Region default view of region" + strRegn
						+ "is displayed.");
			} catch (AssertionError Ae) {
				strFuncResult = "Region default view of region" + strRegn
						+ "is NOT displayed.";
				log4j.info("Region default view of region" + strRegn
						+ "is NOT displayed.");
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
				// Write result data
				String strTestData[] = new String[10];
				strTestData[0] = propEnvDetails.getProperty("Build");
				strTestData[1] = gstrTCID;
				strTestData[2] = strUserName + "/" + strInitPwd;
				strTestData[3] = "Login as a:" + strUserName
						+ "Launch EMR application on mobile device";
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strTestData, strWriteFilePath, "User");
			} catch (AssertionError ae) {
				gstrReason = strFuncResult;
			}

		} catch (Exception e) {
			gstrTCID = "BQS-32727";
			gstrTO = "Verify that RegAdmin can remove access from regions for a user"
					+ "who currently have access to multiple regions.";
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


