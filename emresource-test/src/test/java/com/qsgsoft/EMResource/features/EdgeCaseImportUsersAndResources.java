package com.qsgsoft.EMResource.features;

import static org.junit.Assert.*;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;

import com.qsgsoft.EMResource.shared.General;
import com.qsgsoft.EMResource.shared.Login;
import com.qsgsoft.EMResource.shared.ResourceTypes;
import com.qsgsoft.EMResource.shared.Resources;
import com.qsgsoft.EMResource.shared.Roles;
import com.qsgsoft.EMResource.shared.StatusTypes;
import com.qsgsoft.EMResource.shared.Upload;
import com.qsgsoft.EMResource.shared.Views;
import com.qsgsoft.EMResource.support.Date_Time_settings;
import com.qsgsoft.EMResource.support.ElementId_properties;
import com.qsgsoft.EMResource.support.OfficeCommonFunctions;
import com.qsgsoft.EMResource.support.Paths_Properties;
import com.qsgsoft.EMResource.support.ReadData;
import com.qsgsoft.EMResource.support.ReadEnvironment;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;
/*******************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Setting up Resources 
' Requirement Group	:Import Users and Resources
� Product		    :EMResource v3.23
' Date			    :16/July/2013
' Author		    :QSG
--------------------------------------------------------------------
' Modified Date			                               Modified By
' Date					                               Name
'*******************************************************************/
public class EdgeCaseImportUsersAndResources {
	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger.getLogger("com.qsgsoft." +
			"EMResource.features.EdgeCase_ImportUsersAndResources");
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

   /***********************************************************************************
	* This function is called the setup() function which is executed before every test.
	* 
	* The function will take care of creating a new selenium session for every test
	* 
    ************************************************************************************/

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
		
		seleniumPrecondition = new DefaultSelenium(propEnvDetails
				.getProperty("Server"), 4444, propEnvDetails
				.getProperty("BrowserPrecondition"), propEnvDetails
				.getProperty("urlEU"));
		
		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();
		selenium.start();
		selenium.windowMaximize();

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

    /************************************************************************************
    * This function is called the teardown() function which is executed after every test.
	* The function will take care of stopping the selenium session for every test and 
	* writing the execution result of the test. 
    *************************************************************************************/

	@After
	public void tearDown() throws Exception {

		// kill browser
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();
		
		try {
			selenium.close();
		} catch (Exception e) {

		}
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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	/**********************************************************************
	'Description :Verify that additional rows are not displayed on 'Upload 
	              Details' screen when users are uploaded
	'Arguments	 :None
	'Returns	 :None
	'Date		 :08/Aug/2013
	'Author		 :QSG
	'----------------------------------------------------------------------
	'Modified Date				                               Modified By
	'Date					                                   Name
	***********************************************************************/

	@Test
	public void testEdgeCase123507() throws Exception {

		boolean blnLogin = false;
		String strFuncResult = "";
		Login objLogin = new Login();// object of class Login
		General objGeneral = new General();
		Roles objRoles = new Roles();
		ResourceTypes objResourceTypes = new ResourceTypes();
		StatusTypes objStatusTypes = new StatusTypes();
		Resources objResources = new Resources();
		Upload objUpload = new Upload();
		Views objViews = new Views();
		
		try {
			gstrTCID = "123507"; // Test Case Id
			gstrTO = "Verify that additional rows are not displayed on 'Upload Details'" +
					" screen when users are uploaded";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			seleniumPrecondition.open(propEnvDetails.getProperty("urlRel"));// relative URL
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
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
			String strUserID = "1" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = "UserFul" + System.currentTimeMillis();
						
			String strGenDate = "";
			String strGenDateFormatNew = "";
			boolean blnTest = false;
			String strRegGenTime = "";

		  log4j.info("~~~~~Precondition" + gstrTCID + " STATRTS~~~~~");

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
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


		 log4j.info("~~~~~Precondition" + gstrTCID + " ENDS~~~~~");
		 log4j.info("~~~~~TEST-CASE" + gstrTCID + " EXECUTION STATRTS~~~~~");
		  
			/*
			 * 3. Following data is provided in the standard template 'X': 1.
			 * User ID 2. Role ID 3. Password 4. Full Name
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strAutoFilePath = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Path");

				String strDownloadPath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";

				String strAutoFileName = propElementAutoItDetails
						.getProperty("Reports_DownloadFile_Name");

				strFuncResult = objUpload.downloadUploadTemplate(selenium,
						strAutoFilePath, strAutoFileName, strDownloadPath);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[] strTestData = { "", "N", "", "", "", "", "", "", "",
						"", "", "", "", "", "", "", "", "", "", "", "", "", "",
						"", strUserID, strRoleValue, "", strInitPwd,
						strUsrFulName, "", "", "", "", "", "", "", "", "", "",
						"" };

				String strWriteFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";

				objOFC.writeResultDataToParticularRow(strTestData,
						strWriteFilePath, "Sheet1", 1);

			} catch (Exception Ae) {
				strFuncResult = Ae.toString();
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToUploadListPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload.navToNewUploadPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				String strAutoFilePath = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_Path");
				String strAutoFileName = propElementAutoItDetails
						.getProperty("CreateEve_UploadFile_FileName");
				String strUploadFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";
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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objUpload
						.verifyElmentsInUploadDetailsPage(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strUploadFilePath = pathProps
						.getProperty("Reports_DownloadCSV_Path")
						+ "Up_Temp"
						+ gstrTCID + strTimeText + ".xls";
				String strElementID = propElementDetails
						.getProperty("Upload.SpreadSheet");
				strFuncResult = objViews.isEditableElement(selenium,
						strElementID);
				try {
					assertEquals("Element is NOT Editable ", strFuncResult);
					strFuncResult = objGeneral.assertEQUALSValue(selenium,
							strElementID, strUploadFilePath);
					log4j.info("Following fields are displayed with appropriate value and"
							+ " is disabled.1. Spreadsheet: File path ");
				} catch (AssertionError Ae) {
					log4j.info("Following fields are NOT displayed with appropriate value and "
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

							log4j.info("Following fields are displayed with appropriate value and"
									+ " is disabled.2. Test?: Is selected ");

						} catch (AssertionError Ae) {
							log4j.info("Following fields are NOT displayed with appropriate value and"
									+ " is disabled.2. Test?: Is NOT selected ");
							gstrReason = strFuncResult;
						}
					} else {
						try {
							assertEquals("", strFuncResult);
							strFuncResult = objViews.isCheckedElement(selenium,
									strElementID);

						} catch (AssertionError Ae) {
							log4j.info("Following fields are displayed with appropriate value and"
									+ " is disabled.2. Test?: Is selected ");
							gstrReason = strFuncResult;
						}

						if (strFuncResult.compareTo("Element is NOT Checked ") == 0) {
							log4j.info("Following fields are NOT displayed with appropriate value and"
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
					log4j.info("Following fields are displayed with appropriate value and"
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
					log4j.info("Following fields are displayed with appropriate value and"
							+ " is disabled.2. 4. Date: Current Date ");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				int intRowCount = selenium.getXpathCount(
						"//div[@id='mainContainer']/form/table[2]/tbody/tr")
						.intValue();
				assertEquals(1, intRowCount);
				log4j.info("Additional entries are NOT displayed when the users are uploaded for real.");
			} catch (AssertionError Ae) {
				strFuncResult = "Additional entries are displayed when the users are uploaded for real.";
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
					
			try {
				assertEquals("", strFuncResult);
				gstrResult = "PASS";
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
				
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "123507"; // Test Case Id
			gstrTO = "Verify that additional rows are not displayed on 'Upload Details'" +
					" screen when users are uploaded";// TO
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
}
