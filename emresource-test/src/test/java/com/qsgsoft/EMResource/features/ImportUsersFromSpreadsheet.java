package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/**********************************************************************
' Description         :This class includes ImportUsersFromSpreadsheet test cases
' Requirement Group   :
' Requirement         :ImportUsersFromSpreadsheet
' Date		          :7-Nov-2012
' Author	          :QSG
'-------------------------------------------------------------------
' Modified Date                                    Modified By
' <Date>                           	                 <Name>
'*******************************************************************/


public class ImportUsersFromSpreadsheet {
	
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ImportUsersFromSpreadsheet");
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

	Selenium selenium,seleniumPrecondition;
	
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
		// kill browser
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
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

		gstrReason=gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
/********************************************************************************
'Description	:Verify that user can 'Test' prior to uploading users.
'Precondition	:None
'Arguments		:None
'Returns		:None
'Date	 		:7-Nov-2012
'Author			:QSG
'-------------------------------------------------------------------------------
'Modified Date                            Modified By
'<Date>                                     <Name>
**********************************************************************************/
	@Test
	public void testBQS103107() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objGeneral = new General();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "103107";
			gstrTO = "Verify that user can 'Test' prior to uploading users.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST" + strTimeText;
			String strStatTypDefn = "Automation";

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Crawford County";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();

			String strGenDate = "";
			String strGenDateFormatNew = "";
			String strUploadFilePath = "";
			boolean blnTest = false;

			String strRegGenTime = "";
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1. Role 'R' is created in region 'X' 2. Role ID for for role 'R'
			 * is noted.
			 */

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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

			/*
			 * 3. Resource 'RS' is created under 'RT' which is associated to
			 * 'ST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N","", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						strUserID, strRoleValue, "", strInitPwd, strUsrFulName,
						"", "", "", "", "", "", "", "", "", "", "" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to region 'X' 'Region Default'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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
			/*
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select 'Upload Resources' 'New Upload' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select 'Browse' and add file 'X' File 'X' is populated in the
			 * 'Spreadsheet' field.
			 */

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strUploadFilePath = pathProps
						.getProperty("ImportDataExcelPath");
				blnTest = true;

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						blnTest, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArFunRes[] = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				String strCurYear = dts.getFutureYear(0, "yyyy");
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				strGenDateFormatNew = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				strGenDateFormatNew = strGenDateFormatNew + " " + strRegGenTime;
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*6 	Retain the selected value for the option 'Test?' and 'Save' 		'Upload Details' screen is displayed.
  	  		Following fields are displayed with appropriate value and is disabled.
				1. Spreadsheet: File path
				2. Test?: Is selected
				3. User: RegAdmin
				4. Date: Current Date
				  	  		Appropriate values are present under the following column headers:
				1. Row- 1
				2. Resource ID- None
				3. Resource Name- None
				4. Failed- No
				5. Geocode- None
				6. Resource Comments- No resource data provided
				7. User ID- As provided
				8. Full Name- As provided
				9. Failed- No
				10. User Comments- None
  	  		'Return' button is available. */
  	  		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload
						.verifyElmentsInUploadDetailsPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.SpreadSheet");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, strUploadFilePath);
					log4j
							.info("Following fields are displayed with appropriate value and"
									+ " is disabled.1. Spreadsheet: File path ");

				} catch (AssertionError Ae) {
					log4j
							.info("Following fields are displayed with appropriate value and "
									+ "is disabled.1. Spreadsheet: File path ");
					strFuncResult = "Following fields are displayed with appropriate value and"
							+ " is disabled.1. Spreadsheet: File path ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.Test");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = "";

					if (blnTest) {
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is selected ");

						} catch (AssertionError Ae) {
							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is NOT selected ");
							gstrReason = strFuncResult;
						}
					} else {
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is selected ");
							gstrReason = strFuncResult;
						}

						if (strFuncResult.compareTo("Element is NOT Checked ") == 0) {
							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is NOT selected ");
							strFuncResult = "";
						}
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.CreateUserName");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, "RegAdmin");
					log4j
							.info("Following fields are displayed with appropriate value and"
									+ " is disabled.2. 3. User: RegAdmin  ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.Date");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					
					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, strGenDate);

					log4j
							.info("Following fields are displayed with appropriate value and"
									+ " is disabled.2. 4. Date: Current Date ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select 'Return' 'Upload List' screen is displayed with
			 * appropriate values under following column headers. 1. Action:
			 * View Details 2. Test?: Yes 3. Date: Resource upload date and time
			 * 4. User: RegAdmin
			 */
			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", "", "", "No", "",
						"no resource data provided", strUserID, strUsrFulName,
						"No", "" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Action", "Test?", "Date", "User" };
				String[] pStrArrUplData = { "View Details", "Yes",
						strGenDateFormatNew, strLoginUserName };
				strFuncResult = objUpload.verifyUplDetailsInUpldListPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to 'Setup >> Users' Uploaded user is not present in
			 * the 'User List' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserID,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {

					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td"
									+ "[text()='" + strUserID + "']"));

					log4j.info("User " + strUserID
							+ " is NOT listed in the 'Users List' " + "screen");

				} catch (AssertionError Ae) {
					strFuncResult = "User " + strUserID
							+ " is listed in the 'Users List'" + "" + Ae;
					log4j.info("User " + strUserID
							+ " is  listed in the 'Users List'" + "" + Ae);
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Navigate to 'Setup >> Resources' and select 'Users' link for a
			 * resource 'RS' The uploaded user is not displayed in the 'Assign
			 * users to RS' screen.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strElementID = "//table[@id='tbl_association']/tbody/tr/td[6][text()='"
						+ strUserID + "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("The uploaded user is not displayed in the 'Assign users to RS' screen. ");
				strFuncResult="";
			} catch (AssertionError Ae) {
				strFuncResult = "The uploaded user is displayed in the 'Assign users to RS' screen. ";
				log4j
						.info("The uploaded user is displayed in the 'Assign users to RS' screen. ");
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
			gstrTCID = "103107";
			gstrTO = "Verify that user can 'Test' prior to uploading users.";
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
	'Description	:Verify that user is not imported when 'UserID' is not specified in the 
	'Precondition	:1. Role 'R' is created in region 'X'
					2. Role ID for for role 'R' is noted.
					3. Provide data in the mandatory fields for users except for UserID in the standard template 
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS103108() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		CreateUsers objCreateUsers = new CreateUsers();
		Upload objUpload = new Upload();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria =new SearchUserByDiffCrteria();


		try {
			gstrTCID = "103108";
			gstrTO = "Verify that user is not imported when 'UserID' is not specified in the ";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";


			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();

			
			String strUploadFilePath = "";
			boolean blnTest = false;
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);



			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			/*
			 * 1 Precondition: 1. Role 'R' is created in region 'X' 2. Role ID
			 * for for role 'R' is noted. 3. Provide data in the mandatory
			 * fields for users except for UserID in the standard template No
			 * Expected Result
			 */

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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

			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N","", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", strRoleValue, "", strInitPwd, strUsrFulName,
						"", "", "", "", "", "", "", "", "", "", "" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to region 'X' 'Region Default'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

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

				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select 'Upload Resources' 'New Upload' screen is displayed.
			 */

			/*
			 * 5 Select 'Browse' and add file 'X' File 'X' is populated in the
			 * 'Spreadsheet' field.
			 */

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strUploadFilePath = pathProps
						.getProperty("ImportDataExcelPath");
				blnTest = false;

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						blnTest, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Deselect the option 'Test?' and 'Save' 'Upload Details' screen
			 * is displayed. Appropriate values are present under the following
			 * column headers: 1. Row- 1 2. Resource ID- None 3. Resource Name-
			 * None 4. Failed- No 5. Geocode- None 6. Resource Comments- No
			 * resource data provided 7. User ID- None 8. Full Name- None 9.
			 * Failed- No 10. User Comments- No resource data provided
			 */

			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", "", "", "No", "",
						"no resource data provided", "", "",
						"No", "no user data provided" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to 'Setup >> Users' Uploaded user is not present in
			 * the 'User List' screen.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserID,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {

					assertFalse(selenium
							.isElementPresent("//table[@id='tblUsers']/tbody/tr/td"
									+ "[text()='" + strUserID + "']"));

					log4j.info("User " + strUserID
							+ " is NOT listed in the 'Users List' " + "screen");

				} catch (AssertionError Ae) {
					strFuncResult = "User " + strUserID
							+ " is listed in the 'Users List'" + "" + Ae;
					log4j.info("User " + strUserID
							+ " is  listed in the 'Users List'" + "" + Ae);
				}
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
			gstrTCID = "103108";
			gstrTO = "Verify that user is not imported when 'UserID' is not specified in the ";
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
	'Description	:Verify that users can be uploaded providing only the mandatory 
	'				data in the template.
	'Precondition	:None
	'Arguments		:None
	'Returns		:None
	'Date	 		:8-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS103114() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objGeneral = new General();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "103114";
			gstrTO = "Verify that users can be uploaded providing only "
					+ "the mandatory data in the template.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			// ST
			String strStatusTypeValues[] = new String[1];
			String statTypeName = "AutoST" + strTimeText;
			String strStatTypDefn = "Automation";

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[1];

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Crawford County";
			String strResVal = "";
			String strRSValue[] = new String[1];

			// USER
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();

			String strGenDate = "";
			String strGenDateFormatNew = "";
			String strDate="";
			String strUploadFilePath = "";
			boolean blnTest = false;

			String strRegGenTime = "";

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

				/*1 	Precondition:
					1. Role 'R' is created in region 'X'
					2. Role ID for for role 'R' is noted.
					3. Resource 'RS' is created under 'RT' which is associated to 'ST'
					3. Following data is provided in the standard template 'X':
					1. User ID
					2. Role ID
					3. Password
					4. Full Name */

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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

			/*
			 * 3. Resource 'RS' is created under 'RT' which is associated to
			 * 'ST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { "", "N","", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						strUserID, strRoleValue, "", strInitPwd, strUsrFulName,
						"", "", "", "", "", "", "", "", "", "", "" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to region 'X' 'Region Default'
			 * screen is displayed.
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
				
			/*
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				/*
				 * 4 Select 'Upload Resources' 'New Upload' screen is displayed.
				 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				/*
				 * 5 Select 'Browse' and add file 'X' File 'X' is populated in the
				 * 'Spreadsheet' field.
				 */

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strUploadFilePath = pathProps
						.getProperty("ImportDataExcelPath");
				blnTest = false;

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						blnTest, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strArFunRes[] = objGeneral.getSnapTime(selenium);
				strFuncResult = strArFunRes[4];
				String strCurYear = dts.getFutureYear(0, "yyyy");
				strGenDate = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"MM/dd/yyyy");
				log4j.info(strGenDate);
				strGenDateFormatNew = dts.converDateFormat(strArFunRes[0]
						+ strArFunRes[1] + strCurYear, "ddMMMyyyy",
						"yyyy-MM-dd");
				strDate=dts.getTimeOfParticularTimeZone("CST", "MM/dd/yyyy");
				strRegGenTime = strArFunRes[2] + ":" + strArFunRes[3];
				strGenDateFormatNew = strGenDateFormatNew + " " + strRegGenTime;

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				/*6 	Deselect the option 'Test?' and 'Save' 		'Upload Details' screen is displayed.
	  	  		Following fields are displayed with appropriate value and is disabled.
					1. Spreadsheet: File path
					2. Test?: Is deselected
					3. User: RegAdmin
					4. Date: Current Date
					  	  		Appropriate values are present under the following column headers:
					1. Row- 1
					2. Resource ID- None
					3. Resource Name- None
					4. Failed- No
					5. Geocode- None
					6. Resource Comments- No resource data provided
					7. User ID- As provided
					8. Full Name- As provided
					9. Failed- No
					10. User Comments- None
					  	  		'Return' button is available. 
	  	  		*/
	  	  		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload
						.verifyElmentsInUploadDetailsPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.SpreadSheet");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, strUploadFilePath);
					log4j
							.info("Following fields are displayed with appropriate value and"
									+ " is disabled.1. Spreadsheet: File path ");
					
					if(strFuncResult.compareTo("")!=0){
						strFuncResult = objGeneral.assertEQUALSValue(selenium,
								strElementID, "Import.xls");
						log4j
								.info("Following fields are displayed with appropriate value and"
										+ " is disabled.1. Spreadsheet: File path ");
					}

				} catch (AssertionError Ae) {
					log4j
							.info("Following fields are displayed with appropriate value and "
									+ "is disabled.1. Spreadsheet: File path ");
					strFuncResult = "Following fields are displayed with appropriate value and"
							+ " is disabled.1. Spreadsheet: File path ";
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.Test");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = "";

					if (blnTest) {
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is selected ");

						} catch (AssertionError Ae) {
							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is NOT selected ");
							gstrReason = strFuncResult;
						}
					} else {
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is selected ");
							gstrReason = strFuncResult;
						}

						if (strFuncResult.compareTo("Element is NOT Checked ") == 0) {
							log4j
									.info("Following fields are displayed with appropriate value and"
											+ " is disabled.2. Test?: Is NOT selected ");
							strFuncResult = "";
						}
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.CreateUserName");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, strLoginUserName);
					log4j
							.info("Following fields are displayed with appropriate value and"
									+ " is disabled.2. 3. User: RegAdmin  ");

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails
						.getProperty("Upload.Date");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Editable ", strFuncResult);

					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, strDate);

					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				/*7 	Select 'Return' 		'Upload List' screen is displayed with appropriate values under following column headers.
				1. Action: View Details
				2. Test?: No
				3. Date: Resource upload date and time
				4. User: RegAdmin */

			try {
				assertEquals("", strFuncResult);

				log4j
						.info("Following fields are displayed with appropriate value and"
								+ " is disabled.2. 4. Date: Current Date ");

				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", "", "", "No", "",
						"no resource data provided", strUserID, strUsrFulName,
						"No", "" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strColHeaders = { "Action", "Test?", "Date", "User" };
				String[] pStrArrUplData = { "View Details", "No",
						strGenDateFormatNew, strLoginUserName };
				strFuncResult = objUpload.verifyUplDetailsInUpldListPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to 'Setup >> Users' Uploaded user is present in the
			 * 'User List' screen under appropriate column headers with values
			 * provided while uploading resource.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteria(selenium, strUserID,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				try {

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/thead/tr/th[2]/"
									+ "a[text()='Username']/ancestor::table/tbody/tr/td[2]"
									+ "[text()='" + strUserID + "']"));

					assertTrue(selenium
							.isElementPresent("//table[@id='tblUsers']/thead/tr/th[3]"
									+ "/a[text()='Full Name']/ancestor::table/tbody/tr/"
									+ "td[3][text()='" + strUsrFulName + "']"));

					log4j
							.info("Uploaded "
									+ strUserID
									+ " is present in the 'User List' screen under "
									+ "appropriate column headers with values provided while uploading resource. ");

				} catch (AssertionError Ae) {
					strFuncResult = "Uploaded "
							+ strUserID
							+ " is NOT present in the 'User List' screen under "
							+ "appropriate column headers with values provided while uploading resource."
							+ "" + Ae;
					log4j
							.info("Uploaded "
									+ strUserID
									+ " is NOT present in the 'User List' screen under "
									+ "appropriate column headers with values provided while uploading resource."
									+ "" + Ae);
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Select to edit the user The values provided while uploading the
			 * user is retained. Checkbox for role 'R' is selected User has view
			 * right to all the resources in that region.
			 */
  	  		
  	  		
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserID, strByRole, strByResourceType, strByUserInfo,
						strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.chkRoleSelectedOrNot(selenium,
						true, strRoleValue, strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails.getProperty("CreateNewUsr.UserName");
				strFuncResult = objGeneral.assertEQUALSValue(selenium, strElementID,
						strUserID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = propElementDetails.getProperty("CreateNewUsr.FulUsrName");
				strFuncResult = objGeneral.assertEQUALSValue(selenium,
						strElementID, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[name='SELECT_ALL'][value='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Navigate to 'Setup >> Resources' and select 'Users' link for a
			 * resource 'RS' 'View Resource' right is selected for the uploaded
			 * user
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteriaInAssignUsersPge(selenium, strUserID,
								strByRole, strByResourceType, strByUserInfo,
								strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				String strElementID = "css=input[value='" + strUserID
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("'View Resource' right is selected for the uploaded user ");
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				strFuncResult = "'View Resource' right is NOT selected for the uploaded user ";
				log4j
						.info("'View Resource' right is NOT selected for the uploaded user ");
				gstrReason = strFuncResult;
			}
			

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "103114";
			gstrTO = "Verify that users can be uploaded providing only"
					+ " the mandatory data in the template.";
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
	'Description		:Verify that users can be imported providing the ResourceID.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:08-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS103123() throws Exception {
	try{	
		gstrTCID = "103123";	//Test Case Id	
		gstrTO = " Verify that users can be imported providing the ResourceID.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake=dts.timeNow("HH:mm:ss");	
		Login objLogin = new Login();// object of class Login
		
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
	
		String strFuncResult = "";
		String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
	
		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 3, 4);

		// Role
		String strRolesName = "AutoRol1" + strTimeText;
		String strRoleValue = "";

		// ST
		String strStatusTypeValues[] = new String[1];
		String statTypeName = "AutoST" + strTimeText;
		String strStatTypDefn = "Automation";

		// RT data
		String strResrcTypName = "AutoRT_" + strTimeText;
		String strRsTypeValues[] = new String[1];

		// Resource
		String strResource = "AutoRS_" + strTimeText;
		String strAbbrv = "abb";
		String strState = "Indiana";
		String strCountry = "Crawford County";
		String strResVal = "";
		String strRSValue[] = new String[1];

		// USER
		String strUserID = "Auto" + System.currentTimeMillis();
		String strInitPwd = rdExcel.readData("Login", 4, 2);
		String strUsrFulName = "UserFul" + System.currentTimeMillis();
		
		String strAutoFilePath=propElementAutoItDetails.getProperty("CreateEve_UploadFile_Path");
		String strUplPath=pathProps.getProperty("UploadResFile_Path");

		String strAutoFileName=propElementAutoItDetails.getProperty("CreateEve_UploadFile_FileName");
		/*
		* STEP :
		  Action:Precondition:
	<br>1. Role 'R' is created in region 'X'
	<br>2. Role ID for for role 'R' is noted.
	<br>3. Resource 'RS' is created under 'RT' which is associated to 'ST'
	<br>4. Resource ID for 'RS' is noted. 
	<br>5. Following data is provided in the standard template 'X': 
	<br>1. Resource ID
	<br>2. User ID
	<br>3. Role ID
	<br>4. Password
	<br>5. Full Name
		  Expected Result:No Expected Result
		*/
		//593403
		strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
				strLoginPassword);

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
			strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
					strRolesName);
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

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			String strStatusTypeValue = "Number";
			strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
					seleniumPrecondition, strStatusTypeValue, statTypeName,
					strStatTypDefn, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strStatusTypeValues[0] = objStatusTypes
					.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);

			if (strStatusTypeValues[0].compareTo("") != 0) {
				strFuncResult = "";
			} else {
				strFuncResult = "Failed to fetch status value";
			}
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
					seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
					strResrcTypName);
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

		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navResourcesList(seleniumPrecondition);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.createResourceWitLookUPadres(
					seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
					"LN", strState, strCountry, "Hospital");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			String[] strTestData = {strRSValue[0], "N","", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "",
					strUserID, strRoleValue, "", strInitPwd, strUsrFulName,
					"", "", "", "", "", "", "", "", "", "", "" };

			objOFC.writeResultDataToParticularRow(strTestData, strUplPath, "Resource", 1);

		} catch (Exception Ae) {
			strFuncResult = Ae.toString();
			gstrReason = strFuncResult;
		}

		
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to region 'X'
		  Expected Result:'Region Default' screen is displayed.
		*/
		//593404
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(seleniumPrecondition);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Navigate to 'Setup >> Upload'
		  Expected Result:'Upload List' screen is displayed.
		*/
		//593405
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToUploadListPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Select 'Upload Resources'
		  Expected Result:'New Upload' screen is displayed.
		*/
		//593406
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToNewUploadPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Select 'Browse' and add file 'X'
		  Expected Result:File 'X' is populated in the 'Spreadsheet' field.
		*/
		//593407
		boolean blnTest=false;
		boolean blnSave=true;
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.fillNewUploadFields(selenium, strAutoFilePath, strUplPath, strAutoFileName, blnTest, blnSave);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Deselect the option 'Test?' and 'Save'
		  Expected Result:'Upload Details' screen is displayed.
				  Appropriate values are present under the following column headers:
	<br>1. Row- 1
	<br>2. Resource ID- As provided
	<br>3. Resource Name- As provided
	<br>4. Failed- No
	<br>5. Geocode- None
	<br>6. Resource Comments- existing resource
	<br>7. User ID- As provided
	<br>8. Full Name- As provided
	<br>9. Failed- No
	<br>10. User Comments- None
				  'Return' button is available.
		*/
		//593408
		String[] strColHeaders={"Row","Resource ID","Resource Name","Failed","Geocode","Resource Comments","User ID","Full Name","Failed","User Comments"};
		String[] pStrArrUplData={"1",strRSValue[0],strResource,"No","","existing resource",strUserID,strUsrFulName,"No",""};
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(selenium, strColHeaders, pStrArrUplData);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Select 'Return'
		  Expected Result:'Upload List' screen is displayed
		*/
		//593409
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Navigate to 'Setup >> Users'
		  Expected Result:Uploaded user is present in the 'User List' screen under appropriate column headers with values provided while uploading resource.
		*/
		//593410
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objCreateUsers.navUserListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objCreateUsers.verifyUserDetailsInUserList(selenium, strUserID, strUsrFulName, "");
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Select to edit the user
		  Expected Result:The values provided while uploading the user is retained.
				  User has view right to all the resources in that region.
		*/
		//593411
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objCreateUsers.navToEditUserPage(selenium, strUserID);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
	
		try {
			assertEquals("", strFuncResult);

			strFuncResult = objCreateUsers.verifyUsrMandValuesWithRole(selenium, strUserID, strUsrFulName, strRoleValue, true);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Navigate to 'Setup >> Resources' and select 'Users' link for a resource 'RS'
		  Expected Result:'View Resource' right is selected for the uploaded user
		*/
		//593412
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navResourcesList(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objResources.navToAssignUsersOFRes(selenium, strResource);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.checkResourceRightsWitRSValues(selenium, strResource,strUserID, false, false, false, true);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		try{
			assertEquals("", strFuncResult);
			gstrResult = "PASS";
		}
		catch (AssertionError Ae)
		{
			gstrResult = "FAIL";
			gstrReason = gstrReason+" "+strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "BQS-103123";
		gstrTO = "Verify that users can be imported providing the ResourceID.";
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
	'Description	:Verify that users can be imported providing resource ID and any 
	'				of the affiliated rights.
	'Precondition	:1. Role 'R' is created in region 'X'
						2. Role ID for role 'R' is noted in region 'X'.
						3. Resource 'RS', 'RS1', 'RS2' and 'RS3' are created under 'RT' which is associated to 'ST'
						4. Resource ID for 'RS' is noted.
						5. Following data is provided in the standard template 'X':
						1. Resource ID
						2. User ID
						3. Role ID
						4. Password
						5. Full Name
						6. User U1 is provided with 'Associated' right
						7. User U2 is provided with 'Update' right
						8. User U3 is provided with 'Reports' right 
	'Arguments		:None
	'Returns		:None
	'Date	 		:9-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS103133() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "103133";
			gstrTO = "Verify that users can be imported providing resource ID and any " +
					"of the affiliated rights.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName = "AutoST" + strTimeText;
			String strStatTypDefn = "Automation";
			String statTypeNameOther = "AutoSTOthr" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];
			String strResrcTypNameOthr = "AutoRTOthr" + strTimeText;
			

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Crawford County";
			String strResVal = "";
			String strRSValue[] = new String[4];
			
			// Resources Other
			String strResource_1 = "AutoRS_1" + strTimeText;
			String strResource_2 = "AutoRS_2" + strTimeText;
			String strResource_3 = "AutoRS_3" + strTimeText;
			
	
			// USER
			String strUserID_1 = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = "UserFul_1" + System.currentTimeMillis();
			
			String strUserID_2 = "2" + System.currentTimeMillis();
			String strUsrFulName_2 = "UserFul_2" + System.currentTimeMillis();
	
			String strUserID_3 = "3" + System.currentTimeMillis();
			String strUsrFulName_3 = "UserFul_3" + System.currentTimeMillis();

			
			String strUploadFilePath = "";
			boolean blnTest = false;

			

			/*1 	Precondition:
				1. Role 'R' is created in region 'X'
				2. Role ID for role 'R' is noted in region 'X'.
				3. Resource 'RS', 'RS1', 'RS2' and 'RS3' are created under 'RT' which is associated to 'ST'
				4. Resource ID for 'RS' is noted.
				5. Following data is provided in the standard template 'X':
				1. Resource ID
				2. User ID
				3. Role ID
				4. Password
				5. Full Name
				6. User U1 is provided with 'Associated' right
				7. User U2 is provided with 'Update' right
				8. User U3 is provided with 'Reports' right 
				
				*/
				
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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

			/*
			 * 3. Resource 'RS' is created under 'RT' which is associated to
			 * 'ST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Other status type
			
			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameOther,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameOther);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
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
			
			//Resorce type other
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypNameOthr, strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypNameOthr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypNameOthr);

				if (strRsTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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
			
			//RS1
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource_1, strAbbrv, strResrcTypNameOthr, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource_1);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//RS2
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource_2, strAbbrv, strResrcTypNameOthr, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource_2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[2] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//RS3
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource_3, strAbbrv, strResrcTypNameOthr, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource_3);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[3] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { strRSValue[0],"N","", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						strUserID_1, strRoleValue, "", strInitPwd, strUsrFulName_1,
						"", "", "", "", "", "", "", "", "Y", "N", "N" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath_103133");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strTestData = {strRSValue[0], "N","", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						strUserID_2, strRoleValue, "", strInitPwd, strUsrFulName_2,
						"", "", "", "", "", "", "", "", "N", "Y", "N" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath_103133");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 2);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String[] strTestData = {strRSValue[0], "N","", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						strUserID_3, strRoleValue, "", strInitPwd, strUsrFulName_3,
						"", "", "", "", "", "", "", "", "N", "N", "Y" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath_103133");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 3);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}
			

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to region 'X' 'Region Default'
			 * screen is displayed.
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
				
			/*
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				/*
				 * 4 Select 'Upload Resources' 'New Upload' screen is displayed.
				 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

				/*
				 * 5 Select 'Browse' and add file 'X' File 'X' is populated in the
				 * 'Spreadsheet' field.
				 */

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strUploadFilePath = pathProps
						.getProperty("ImportDataExcelPath_103133");
				blnTest = false;

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						blnTest, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			6 	Deselect the option 'Test?' and 'Save' 		'Upload Details' screen is displayed.
  	  		Rows from 1 to 3 is displayed with appropriate values are present under the following column headers:
				1. Row-
				2. Resource ID
				3. Resource Name
				4. Failed
				5. Geocode
				6. Resource Comments
				7. User ID
				8. Full Name
				9. Failed
				10. User Comments
				  	  		'Return' button is available. 
  	  		*/
			

			try {
				assertEquals("", strFuncResult);


				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", strRSValue[0], strResource, "No", "",
						"existing resource", strUserID_1, strUsrFulName_1,
						"No", "" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				log4j
						.info("Following fields are displayed with appropriate value and"
								+ " is disabled.2. 4. Date: Current Date ");

				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "2", strRSValue[0], strResource, "No", "",
						"existing resource", strUserID_2, strUsrFulName_2,
						"No", "" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPgeNew(
						selenium, strColHeaders, pStrArrUplData,strUserID_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);

				log4j
						.info("Following fields are displayed with appropriate value and"
								+ " is disabled.2. 4. Date: Current Date ");

				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "3", strRSValue[0], strResource, "No", "",
						"existing resource", strUserID_3, strUsrFulName_3,
						"No", "" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPgeNew(
						selenium, strColHeaders, pStrArrUplData,strUserID_3);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select 'Return' 'Upload List' screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
 				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*8 	Navigate to 'Setup >> Users' 		User 'U1' is uploaded which has 'Associate' right on resource 'RS' and only 'View' right on other resources (RS1, RS2 and RS3).
  	  		User 'U2' is uploaded which has 'Update' right on resource 'RS' and only 'View' right on other resources (RS1, RS2 and RS3).
  	  		User 'U3' is uploaded which has 'Reports' right on resource 'RS' and only 'View' right on other resources (RS1, RS2 and RS3). 
  	  		*/
  	  		
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserID_1, strByRole, strByResourceType, strByUserInfo,
						strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//View Right

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='viewRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='viewRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[3]
									+ "'][name='viewRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			//Association right
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='association']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='association']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("Element is NOT Checked ", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='association']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("Element is NOT Checked ", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[3]
									+ "'][name='association']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//u2
		
			try {
				assertEquals("Element is NOT Checked ", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserID_2, strByRole, strByResourceType, strByUserInfo,
						strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//View Right

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='viewRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='viewRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[3]
									+ "'][name='viewRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			//Update right
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='updateRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);
				
				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='updateRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);
					
					try {
						assertEquals("Element is NOT Checked ", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='updateRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);
						
						try {
							assertEquals("Element is NOT Checked ", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[3]
									+ "'][name='updateRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);
							
							
						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}
					
					
					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//u2
			
			try {
				assertEquals("Element is NOT Checked ", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserID_3, strByRole, strByResourceType, strByUserInfo,
						strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//View Right

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='viewRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='viewRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[3]
									+ "'][name='viewRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}
						
					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			// Update right
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='reportRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='reportRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("Element is NOT Checked ", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='reportRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("Element is NOT Checked ", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[3]
									+ "'][name='reportRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*9 	Navigate to 'Setup >> Resources' and select 'Users' link for a resource 'RS' 		Users
			 *  have the following rights on RS along with 'View' right:
				U1-Associate right
				U2-Update right
				U3-Reports right
			 	*/
				
			
			try {
				assertEquals("Element is NOT Checked ", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteriaInAssignUsersPge(selenium,
								strUserID_1, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strUserID_1
						+ "'][name='association']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User2
			try {
				assertEquals("", strFuncResult);
				log4j
						.info("'Association' right is selected for the uploaded user "
								+ strUserID_1);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteriaInAssignUsersPge(selenium,
								strUserID_2, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				log4j
						.info("'Association' right is NOT selected for the uploaded user "
								+ strUserID_1);
				strFuncResult = "'Association' right is NOT selected for the uploaded user "
						+ strUserID_1;
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strUserID_2
						+ "'][name='updateRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User3
			try {
				assertEquals("", strFuncResult);
				log4j.info("'Update' right is selected for the uploaded user "
						+ strUserID_2);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteriaInAssignUsersPge(selenium,
								strUserID_3, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {

				log4j
						.info("'Update' right is NOT selected for the uploaded user "
								+ strUserID_2);
				strFuncResult = "'Update' right is NOT selected for the uploaded user "
						+ strUserID_2;

				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strUserID_3
						+ "'][name='reportRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("'Run Report' right is selected for the uploaded user "
								+ strUserID_3);

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				log4j
						.info("'Run Report' right is NOT selected for the uploaded user "
								+ strUserID_3);
				strFuncResult = "'Run Report' right is NOT selected for the uploaded user "
						+ strUserID_3;

				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "103133";
			gstrTO = "Verify that users can be imported providing resource ID and "
					+ "any of the affiliated rights.";
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
	'Description		:Verify that duplicate UserID provided in the standard template cannot be uploaded.
	'Precondition		:
	'Arguments		:None
	'Returns		:None
	'Date			:09-11-2012
	'Author			:QSG
	'---------------------------------------------------------------
	'Modified Date				Modified By
	'Date					Name
	***************************************************************/

	@Test
	public void testBQS101824() throws Exception {
	try{	
		gstrTCID = "101824";	//Test Case Id	
		gstrTO = " Verify that duplicate UserID provided in the standard template cannot be uploaded.";//Test Objective
		gstrReason = "";
		gstrResult = "FAIL";	

		log4j.info("~~~~~TEST CASE - "+gstrTCID+" EXECUTION STARTS~~~~~");
		Date_Time_settings dts = new Date_Time_settings();
		gstrTimetake=dts.timeNow("HH:mm:ss");	

		String strAutoFilePath=propElementAutoItDetails.getProperty("CreateEve_UploadFile_Path");
		String strUplPath=pathProps.getProperty("UploadResFile_Path");
		String strManyResUplPath=pathProps.getProperty("UploadManyResFile_Path");

		String strAutoFileName=propElementAutoItDetails.getProperty("CreateEve_UploadFile_FileName");
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		
		Roles objRoles = new Roles();
	
		CreateUsers objCreateUsers = new CreateUsers();
		
		Upload objUpload = new Upload();
	
	
		String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
	
	
		String strLoginUserName = rdExcel.readData("Login", 3, 1);
		String strLoginPassword = rdExcel.readData("Login", 3, 2);
		String strRegn = rdExcel.readData("Login", 3, 4);

		// Role
		String strRolesName = "AutoRol1" + strTimeText;
		String strRoleValue = "";

		
		String strUsrFulName2="Full2"+System.currentTimeMillis();
		String strUserName2="AutoUsr2"+System.currentTimeMillis();
		String strUsrPassword= rdExcel.readData("Login", 4, 2);
		
		String strFILE_PATH = pathProps.getProperty("TestData_path");
		
		String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
				strFILE_PATH);
		String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
				12, strFILE_PATH);
		String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
				strFILE_PATH);
		String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
				strFILE_PATH);
		
		String strUsrFulName1="Full1"+System.currentTimeMillis();
		String strUserName1="AutoUsr1"+System.currentTimeMillis();
		

		String strUsrFulName3="Full3"+System.currentTimeMillis();
		String strUserName3="AutoUsr3"+System.currentTimeMillis();
		
		/*
		* STEP :
		  Action:Precondition:
	<br>1. Role 'R' is created in region 'X'
	<br>2. Role ID for for role 'R' is noted.
	<br>3. USer 'U1' has been uploaded to region 'R' from the standard template 'X'
	<br>3. User 'U2' has been created in region 'R'
	<br>4. Standard template has the following values:
	<br>1. U1
	<br>2. U2
	<br>3. U3
	<br>4. U3 (Duplicates the above user)
		  Expected Result:No Expected Result
		*/
		//595573
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
			strFuncResult = objRoles.navRolesListPge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objRoles.navCreateRolePge(selenium);
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}

		try {
			assertEquals("", strFuncResult);

			strFuncResult = objRoles.roleMandtoryFlds(selenium,
					strRolesName);
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

			String[] strTestData = {"", "N","", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "",
					strUserName1, strRoleValue, "", strUsrPassword, strUsrFulName1,
					"", "", "", "", "", "", "", "", "", "", "" };

			objOFC.writeResultDataToParticularRow(strTestData, strUplPath, "Resource", 1);

		} catch (Exception Ae) {
			strFuncResult = Ae.toString();
			gstrReason = strFuncResult;
		}
		
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToUploadListPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToNewUploadPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		
		boolean blnTest=false;
		boolean blnSave=true;
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.fillNewUploadFields(selenium, strAutoFilePath, strUplPath, strAutoFileName, blnTest, blnSave);
		}
		catch (AssertionError Ae)
		{
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
			strFuncResult = objCreateUsers.fillUsrMandatoryFlds(selenium, strUserName2, strUsrPassword, strUsrPassword, strUsrFulName2);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
							
		try {
			assertEquals("", strFuncResult);
							
			strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
					selenium, strUserName2, strByRole, strByResourceType,
					strByUserInfo, strNameFormat);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);

			String[] strTestData = {"", "N","", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "",
					strUserName1, strRoleValue, "", strUsrPassword, strUsrFulName1,
					"", "", "", "", "", "", "", "", "", "", "" };

			objOFC.writeResultDataToParticularRow(strTestData, strManyResUplPath, "ResourceUsr", 1);

		} catch (Exception Ae) {
			strFuncResult = Ae.toString();
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);

			String[] strTestData = {"", "N","", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "",
					strUserName2, strRoleValue, "", strUsrPassword, strUsrFulName2,
					"", "", "", "", "", "", "", "", "", "", "" };

			objOFC.writeResultDataToParticularRow(strTestData, strManyResUplPath, "ResourceUsr", 2);

		} catch (Exception Ae) {
			strFuncResult = Ae.toString();
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);

			String[] strTestData = {"", "N","", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "",
					strUserName3, strRoleValue, "", strUsrPassword, strUsrFulName3,
					"", "", "", "", "", "", "", "", "", "", "" };

			objOFC.writeResultDataToParticularRow(strTestData, strManyResUplPath, "ResourceUsr", 3);

		} catch (Exception Ae) {
			strFuncResult = Ae.toString();
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);

			String[] strTestData = {"", "N","", "", "", "", "", "", "", "",
					"", "", "", "", "", "", "", "", "", "", "", "", "", "",
					strUserName3, strRoleValue, "", strUsrPassword, strUsrFulName3,
					"", "", "", "", "", "", "", "", "", "", "" };

			objOFC.writeResultDataToParticularRow(strTestData, strManyResUplPath, "ResourceUsr",4);

		} catch (Exception Ae) {
			strFuncResult = Ae.toString();
			gstrReason = strFuncResult;
		}
		
		/*
		* STEP :
		  Action:Login as RegAdmin and navigate to region 'X'
		  Expected Result:No Expected Result
		*/
		//595574
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.logout(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}

		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.login(selenium, strLoginUserName, strLoginPassword);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}



		try{
			assertEquals("", strFuncResult);
			strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Navigate to 'Setup >> Upload'
		  Expected Result:'Upload List' screen is displayed.
		*/
		//595575
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToUploadListPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Once again select to upload users U1, U2, U3 and U3 from template X
		  Expected Result:'Upload Details' screen is displayed with the following user comments for each row.
				  Row1: duplicate UserID and 'Yes' is displayed for column 'Failed'
	<br>Row2: duplicate UserID and 'Yes' is displayed for column 'Failed'
	<br>Row3: No user comment and 'No' is displayed for column 'Failed'
	<br>Row4: duplicates user in row 3 and 'Yes' is displayed for column 'Failed'
		*/
		//595576
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.navToNewUploadPage(selenium);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
	
		blnTest=false;
		blnSave=true;
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.fillNewUploadFields(selenium, strAutoFilePath, strManyResUplPath, strAutoFileName, blnTest, blnSave);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		
		String[] strColHeaders={"Row","Resource ID","Resource Name","Failed","Geocode","Resource Comments","User ID","Full Name","Failed","User Comments"};
		String[] pStrArrUplData={"1","","","No","","no resource data provided",strUserName1,strUsrFulName1,"Yes","duplicate UserID"};
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(selenium, strColHeaders, pStrArrUplData);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		String[][] pStrArrUplData1={{"1","","","No","","no resource data provided",strUserName1,strUsrFulName1,"Yes","duplicate UserID"},
				{"2","","","No","","no resource data provided",strUserName2,strUsrFulName2,"Yes","duplicate UserID"},
				{"3","","","No","","no resource data provided",strUserName3,strUsrFulName3,"No",""},
				{"4","","","No","","no resource data provided",strUserName3,strUsrFulName3,"Yes","duplicates user in row 3"},};
		try{
			assertEquals("", strFuncResult);
			strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge_MultipleData(selenium, pStrArrUplData1);
		}
		catch (AssertionError Ae)
		{
			gstrReason = strFuncResult;
		}
		/*
		* STEP :
		  Action:Navigate to 'Setup >> Users'
		  Expected Result:Only U3 is uploaded.
				  Duplicates of U1, U2 and U3 is not uploaded
		*/
		//595577
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.navUserListPge(selenium);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.verifyUsrInUserListPage(selenium, strUserName3, true, false);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.checkDuplicateOfUserNotPresent(selenium, strUserName1);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.checkDuplicateOfUserNotPresent(selenium, strUserName2);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		try {
			assertEquals("", strFuncResult);
			strFuncResult = objCreateUsers.checkDuplicateOfUserNotPresent(selenium, strUserName3);
			
		} catch (AssertionError Ae) {
			gstrReason = strFuncResult;
		}
		
		try{
			assertEquals("", strFuncResult);
			gstrResult = "PASS";
		}
		catch (AssertionError Ae)
		{
			gstrResult = "FAIL";
			gstrReason = gstrReason+" "+strFuncResult;
		}

		log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");	
		log4j.info("----------------------------------------------------------");

	   } catch (Exception e) {
		gstrTCID = "BQS-101824";
		gstrTO = "Verify that duplicate UserID provided in the standard template cannot be uploaded.";
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
	'Description	:Verify that users can be imported providing resource ID and all
	'				 the affiliated rights.
	'Precondition	:1 	Precondition:
						1. Role 'R' is created in region 'X'
						2. Role ID for for role 'R' is noted.
						3. Resource 'RS', 'RS1', 'RS2' and 'RS3' are created under 'RT' which is associated to 'ST'
						4. Resource ID for 'RS' is noted.
						5. Following data is provided in the standard template 'X':
						1. Resource ID
						2. User ID
						3. Role ID
						4. Password
						5. Full Name
						6. User U1 is provided with 'Associated', 'Update' and 'Reports' right 	
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-Nov-2012
	'Author			:QSG
	'-------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	**********************************************************************************/
	@Test
	public void testBQS103478() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		CreateUsers objCreateUsers = new CreateUsers();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		Views objViews = new Views();
		SearchUserByDiffCrteria objSearchUserByDiffCrteria = new SearchUserByDiffCrteria();

		try {
			gstrTCID = "103478";
			gstrTO = "Verify that users can be imported providing resource "
					+ "ID and all the affiliated rights.";
			gstrResult = "FAIL";
			gstrReason = "";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Role
			String strRolesName = "AutoRol1" + strTimeText;
			String strRoleValue = "";

			// ST
			String strStatusTypeValues[] = new String[2];
			String statTypeName = "AutoST" + strTimeText;
			String strStatTypDefn = "Automation";
			String statTypeNameOther = "AutoSTOthr" + strTimeText;

			// RT data
			String strResrcTypName = "AutoRT_" + strTimeText;
			String strRsTypeValues[] = new String[2];
			String strResrcTypNameOthr = "AutoRTOthr" + strTimeText;

			// Resource
			String strResource = "AutoRS_" + strTimeText;
			String strAbbrv = "abb";
			String strState = "Indiana";
			String strCountry = "Crawford County";
			String strResVal = "";
			String strRSValue[] = new String[4];

			// Resources Other
			String strResource_1 = "AutoRS_1" + strTimeText;
			String strResource_2 = "AutoRS_2" + strTimeText;
			String strResource_3 = "AutoRS_3" + strTimeText;

			// USER
			String strUserID_1 = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = "UserFul_1"+ strUserID_1;

			String strUploadFilePath = "";
			boolean blnTest = false;

			/*
			 * 1 Precondition: 1. Role 'R' is created in region 'X' 2. Role ID
			 * for role 'R' is noted in region 'X'. 3. Resource 'RS', 'RS1',
			 * 'RS2' and 'RS3' are created under 'RT' which is associated to
			 * 'ST' 4. Resource ID for 'RS' is noted. 5. Following data is
			 * provided in the standard template 'X': 1. Resource ID 2. User ID
			 * 3. Role ID 4. Password 5. Full Name 6. User U1 is provided with
			 * 'Associated' right 7. User U2 is provided with 'Update' right 8.
			 * User U3 is provided with 'Reports' right
			 */

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STATRTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
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
				strFuncResult = objRoles.navCreateRolePge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRoles.roleMandtoryFlds(seleniumPrecondition,
						strRolesName);
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

			/*
			 * 3. Resource 'RS' is created under 'RT' which is associated to
			 * 'ST'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[0] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeName);

				if (strStatusTypeValues[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Other status type

			try {
				assertEquals("", strFuncResult);
				String strStatusTypeValue = "Number";
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeNameOther,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusTypeValues[1] = objStatusTypes
						.fetchSTValueInStatTypeList(seleniumPrecondition, statTypeNameOther);

				if (strStatusTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strStatusTypeValues[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
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

			// Resorce type other

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypNameOthr, strStatusTypeValues[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResourceTypes.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypNameOthr);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRsTypeValues[1] = objResourceTypes
						.fetchResTypeValueInResTypeList(seleniumPrecondition,
								strResrcTypNameOthr);

				if (strRsTypeValues[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrcTypName, "FN",
						"LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
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

			// RS1

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource_1, strAbbrv, strResrcTypNameOthr,
						"FN", "LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource_1);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS2

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource_2, strAbbrv, strResrcTypNameOthr,
						"FN", "LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource_2);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[2] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// RS3

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.createResourceWitLookUPadres(
						seleniumPrecondition, strResource_3, strAbbrv, strResrcTypNameOthr,
						"FN", "LN", strState, strCountry, "Hospital");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strResVal = objResources.fetchResValueInResList(seleniumPrecondition,
						strResource_3);
				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[3] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
			 */

			try {
				assertEquals("", strFuncResult);

				String[] strTestData = { strRSValue[0], "N","", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", "", strUserID_1, strRoleValue, "", strInitPwd,
						strUsrFulName_1, "", "", "", "", "", "", "", "", "Y",
						"Y", "Y" };

				String strWriteFilePath = pathProps
						.getProperty("ImportDataExcelPath");

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j
					.info("~~~~~TEST CASE " + gstrTCID
							+ " EXECUTION STATRTS~~~~~");

			/*
			 * 2 Login as RegAdmin and navigate to region 'X' 'Region Default'
			 * screen is displayed.
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

			/*
			 * 3 Navigate to 'Setup >> Upload' 'Upload List' screen is
			 * displayed.
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 4 Select 'Upload Resources' 'New Upload' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Select 'Browse' and add file 'X' File 'X' is populated in the
			 * 'Spreadsheet' field.
			 */

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				strUploadFilePath = pathProps
						.getProperty("ImportDataExcelPath");
				blnTest = false;

				strFuncResult = objUpload.fillNewUploadFields(selenium,
						strAutoFilePath, strUploadFilePath, strAutoFileName,
						blnTest, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Deselect the option 'Test?' and 'Save' 'Upload Details' screen
			 * is displayed. Rows from 1 to 3 is displayed with appropriate
			 * values are present under the following column headers: 1. Row- 2.
			 * Resource ID 3. Resource Name 4. Failed 5. Geocode 6. Resource
			 * Comments 7. User ID 8. Full Name 9. Failed 10. User Comments
			 * 'Return' button is available.
			 */
			try {
				assertEquals("", strFuncResult);

				String[] strColHeaders = { "Row", "Resource ID",
						"Resource Name", "Failed", "Geocode",
						"Resource Comments", "User ID", "Full Name", "Failed",
						"User Comments" };
				String[] pStrArrUplData = { "1", strRSValue[0], strResource,
						"No", "", "existing resource", strUserID_1,
						strUsrFulName_1, "No", "" };
				strFuncResult = objUpload.verifyUplDetailsInUpldDetailsPge(
						selenium, strColHeaders, pStrArrUplData);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select 'Return' 'Upload List' screen is displayed
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPageByReturn(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 8 Navigate to 'Setup >> Users' User 'U1' is uploaded which has
			 * 'Associate', 'Update' and 'Reports' right on resource 'RS'
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserID_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// All Rights

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[0]
							+ "'][name='association']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[0]
								+ "'][name='updateRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("", strFuncResult);

							strElementID = "css=input[value='" + strRSValue[0]
									+ "'][name='reportRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			// View Right for all resources

			try {
				assertEquals("", strFuncResult);

				log4j
						.info("User 'U1' is uploaded which has 'Associate', 'Update' "
								+ "and 'Reports' right on resource 'RS' ");

				String strElementID = "css=input[value='" + strRSValue[1]
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[2]
							+ "'][name='viewRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[3]
								+ "'][name='viewRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			
			//RS1
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[1]
						+ "'][name='association']";
				
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Checked ", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[1]
							+ "'][name='updateRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("Element is NOT Checked ", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[1]
								+ "'][name='reportRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			
			
			//RS2
			try {
				assertEquals("Element is NOT Checked ", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[2]
						+ "'][name='association']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Checked ", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[2]
							+ "'][name='updateRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("Element is NOT Checked ", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[2]
								+ "'][name='reportRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//RS3
			try {
				assertEquals("Element is NOT Checked ", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[3]
						+ "'][name='association']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("Element is NOT Checked ", strFuncResult);

					strElementID = "css=input[value='" + strRSValue[3]
							+ "'][name='updateRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("Element is NOT Checked ", strFuncResult);

						strElementID = "css=input[value='" + strRSValue[3]
								+ "'][name='reportRight']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("Element is NOT Checked ", strFuncResult);
				strFuncResult="";
				log4j
						.info("User U1 has only '"
								+ "View' right on other resources " +
										"(RS1, RS2 and RS3). ");
			} catch (AssertionError Ae) {

				log4j
						.info("User U1 NOT has only 'View' right on " +
								"other resources (RS1, RS2 and RS3). ");
				strFuncResult = "User U1 NOT has only 'View' right on" +
						" other resources (RS1, RS2 and RS3). ";
				gstrReason = strFuncResult;
			}

			/*
			 * 9 Navigate to 'Setup >> Resources' and select 'Users' link for a
			 * resource 'RS' User 'U1' has 'Associate', 'Update', 'Reports' and
			 * 'View' right on 'RS'
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objResources.navToAssignUsersOFRes(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// User1
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objSearchUserByDiffCrteria
						.searchUserByDifCriteriaInAssignUsersPge(selenium,
								strUserID_1, strByRole, strByResourceType,
								strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strUserID_1
						+ "'][name='viewRight']";
				strFuncResult = objViews.isCheckedElement(selenium,
						strElementID);

				try {
					assertEquals("", strFuncResult);

					strElementID = "css=input[value='" + strUserID_1
							+ "'][name='updateRight']";
					strFuncResult = objViews.isCheckedElement(selenium,
							strElementID);

					try {
						assertEquals("", strFuncResult);

						strElementID = "css=input[value='" + strUserID_1
								+ "'][name='association']";
						strFuncResult = objViews.isCheckedElement(selenium,
								strElementID);

						try {
							assertEquals("", strFuncResult);

							strElementID = "css=input[value='" + strUserID_1
									+ "'][name='reportRight']";
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							gstrReason = strFuncResult;
						}

					} catch (AssertionError Ae) {
						gstrReason = strFuncResult;
					}

				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				log4j
						.info("	User '"
								+ strUserID_1
								+ "' has 'Associate', 'Update', 'Reports' and 'View' right on 'RS' ");

				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				log4j
						.info("	User '"
								+ strUserID_1
								+ "' NOT has 'Associate', 'Update', 'Reports' and 'View' right on 'RS' ");
				strFuncResult = "	User '"
						+ strUserID_1
						+ "'NOT has 'Associate', 'Update', 'Reports' and 'View' right on 'RS' ";

				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "103478";
			gstrTO = "Verify that users can be imported providing "
					+ "resource ID and all the affiliated rights.";
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
