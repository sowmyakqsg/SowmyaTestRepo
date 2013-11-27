package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.support.*;
import com.qsgsoft.EMResource.shared.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/*****************************************************************
' Description         : This class includes test cases related to
' Requirement Group   : Reports 
' Requirement         : Resource Detail Report
' Date		          : 5th-March-2013
' Author	          : QSG
'-----------------------------------------------------------------
' Modified Date                                      Modified By
' <Date>                           	                    <Name>
'*****************************************************************/
public class ResourceDetailReportTest {
	Date gdtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.ResourceDetailReport");
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
	Selenium selenium, seleniumFirefox, seleniumPrecondition;

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
		seleniumFirefox = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserFirefox"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

		seleniumFirefox.start();
		seleniumFirefox.windowMaximize();
		seleniumFirefox.setTimeout("");

		objOFC = new OfficeCommonFunctions();
		rdExcel = new ReadData();

	}

	@After
	public void tearDown() throws Exception {

		try {
			seleniumFirefox.close();
		} catch (Exception e) {

		}
		try {
			selenium.close();
		} catch (Exception e) {

		}
		try {
			seleniumPrecondition.close();
		} catch (Exception e) {

		}
		seleniumPrecondition.stop();

		seleniumFirefox.stop();

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
	//start//testBQS124624//
	/************************************************************************************************
	 * 'Description  :Verify that user can generate 'Resource Detail report' providing mandatory data. 
	 * 'Precondition :
	 * 'Arguments    :None
	 * 'Returns      :None
	 * 'Date         :8/16/2013 
	 * 'Author       :QSG
	 * '----------------------------------------------------------------------------------------------
	 * 'Modified Date                                                                  Modified By 
	 * 'Date                                                                             Name
	 ************************************************************************************************/

	@Test
	public void testBQS124624() throws Exception {
		String strFuncResult = "";
		boolean blnLogin = false;
		Login objLogin = new Login();
		CreateUsers objCreateUsers = new CreateUsers();// object of class
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		OfficeCommonFunctions OFC = new OfficeCommonFunctions();
		Reports objReports = new Reports();
		General objGeneral = new General();
		try {
			gstrTCID = "124624"; // Test Case Id
			gstrTO = " Verify that user can generate '" +
					"Resource Detail report' providing mandatory data.";// TO
			gstrReason = "";
			gstrResult = "FAIL";

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			selenium.open(propEnvDetails.getProperty("urlRel"));
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);
			String strTimeText = dts.getCurrentDate("MMddyyyy_hhmmss");

			String statrNedocTypeName = "AutoNEDOC" + strTimeText;
			String strNDSTValue = "NEDOCS Calculation";
			String strTxtStatTypDefn = "Auto";
			String[] strSTValue = new String[1];

			String strResrcTypName = "AutoRt1_" + strTimeText;
			String[] strRTValue = new String[2];
			String strResource1 = "AutoRs1_" + strTimeText;
			String strResource2 = "AutoRs2_" + strTimeText;
			String strAbbrv = "Rs";
			String strStandResType = "Aeromedical";
			String strState = "Alabama";
			String strCountry = "Autauga County";
			String strRSValue[] = new String[2];
			String strContFName = "auto";
			String strContLName = "qsg";
			String strLongitude = "";
			String strLatitude = "";
			String strLongitude1 = "";
			String strLatitude1 = "";

			String strUserName1 = "AutoUsr1_" + System.currentTimeMillis();
			String strUserName2 = "AutoUsr2_" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName = strUserName1;
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String strCSVDownlPathRenamed = pathProps
					.getProperty("Reports_DownloadCSV_Path");
			
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "ResorceDetail_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "ResorceDetail_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed = "ResorceDetail_" + gstrTCID + "_"
					+ strTimeText;
			
			String strCSVDownlPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "ResorceDetail1_" + gstrTCID + "_" + strTimeText + ".xlsx";

			String strCSVDownlRenamedPath1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "ResorceDetail1_" + gstrTCID + "_" + strTimeText + ".xls";

			String strCSVFileNameRenamed1 = "ResorceDetail1_" + gstrTCID + "_"
					+ strTimeText;
			
		/*
		* STEP :
		  Action:Preconditions:
		1. Create Status type NEDOC
		2. Create Resource type RT selecting NEDOC
		3. Resource RS1 and RS2 are created under RT.
		4. User 'U1' is created providing mandatory data and selecting 'May update region setup information' right under Advanced option. 
		5. User U2 is created selecting 'Edit Resources Only' right and run report right on resource 'RS1'
		6. User U3 is created selecting 'Setup Resources' right and run report right on resources RS1 ,RS2
	    Expected Result:No Expected Result
		*/
		//660781
			
		log4j.info("----Precondtion for test case " + gstrTCID+ " starts-----");

			strFuncResult = objLogin.login(seleniumPrecondition,
					strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult = objLogin.navUserDefaultRgn(
						seleniumPrecondition, strRegn);
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
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNDSTValue, statrNedocTypeName,
						strTxtStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.savAndVerifySTNew(
						seleniumPrecondition, statrNedocTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strSTValue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNedocTypeName);
				if (strSTValue[0].compareTo("") != 0) {
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
				strFuncResult = objRT.resrcTypeMandatoryFldsNew(
						seleniumPrecondition, strResrcTypName, strSTValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrcTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue[0] = objRT.fetchResTypeValueInResTypeList(
						seleniumPrecondition, strResrcTypName);
				if (strRTValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch status value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// RS
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResource1, strAbbrv,
						strResrcTypName, true, strContFName, strContLName,
						strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[0] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource1);
				if (strRSValue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition,
						strResource1);
				
				int intCnt=0;
				do{
					try {

						assertTrue(seleniumPrecondition.isElementPresent("id=longitude"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				strLongitude = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude);

				char ch = '.';

				int intpos = strLongitude.indexOf(ch);

				String strLongitudeBefore = strLongitude.substring(0, intpos);
				strLongitude = strLongitude.substring(intpos, intpos + 4);

				strLongitude = strLongitudeBefore + strLongitude;
				log4j.info(strLongitude);

				strLatitude = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude.indexOf(ch);
				String strLatitudeBefore = strLatitude.substring(0, intpos);
				strLatitude = strLatitude.substring(intpos, intpos + 4);

				strLatitude = strLatitudeBefore + strLatitude;
				log4j.info(strLatitude);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.createResourceWitLookUPadresSharWitRgn(
						seleniumPrecondition, strResource2, strAbbrv,
						strResrcTypName, true, strContFName, strContLName,
						strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRSValue[1] = objRs.fetchResValueInResList(
						seleniumPrecondition, strResource2);
				if (strRSValue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navToEditResourcePage(seleniumPrecondition,
						strResource2);
				
				int intCnt=0;
				do{
					try {

						assertTrue(seleniumPrecondition.isElementPresent("id=longitude"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);
				
				strLongitude1 = seleniumPrecondition.getValue("id=longitude");
				log4j.info(strLongitude1);

				char ch = '.';

				int intpos = strLongitude1.indexOf(ch);

				String strLongitudeBefore = strLongitude1.substring(0, intpos);
				strLongitude1 = strLongitude1.substring(intpos, intpos + 4);

				strLongitude1 = strLongitudeBefore + strLongitude1;
				log4j.info(strLongitude1);

				strLatitude1 = seleniumPrecondition.getValue("id=latitude");

				intpos = strLatitude1.indexOf(ch);
				String strLatitudeBefore = strLatitude1.substring(0, intpos);
				strLatitude1 = strLatitude1.substring(intpos, intpos + 4);

				strLatitude1 = strLatitudeBefore + strLatitude1;
				log4j.info(strLatitude1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.cancelAndNavToRSListPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//User1
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
						seleniumPrecondition, strUserName1, strInitPwd,
						strConfirmPwd, strUsrFulName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("createUser.advancedoption.MayUpdateRegion");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			// User2
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
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource1, strRSValue[0],
						false, false, true, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.EditResourceOnly");
				strFuncResult = objCreateUsers.advancedOptns(
						seleniumPrecondition, strOptions, true);
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

		/*
		* STEP :
		  Action:Login as U1 and navigate to Report>>Resource reports.
		  Expected Result:'Resource Reports Menu' screen is displayed.
          'Resource Details' option is available.
		*/
		//660782
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				String strElementID = "link=Resource Details";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
				log4j.info("'Resource Details' option is available.");
			} catch (AssertionError Ae) {
				log4j.info("'Resource Details' option is NOT available.");
				strFuncResult = "'Resource Details' option is NOT available.";
				gstrReason = strFuncResult;
			}

		/*
		* STEP :
		  Action:Click on 'Resource Details'
		  Expected Result:'Resource Detail Report' screen is displayed with:
			Resources:Select All(Check box)
			(List of all the resources in region with check box)
			Resources RS1 and RS2 are listed under it.
		*/
		//660783
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.verifyRSInRsDeatilReport(selenium,
						strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.verifyRSInRsDeatilReport(selenium,
						strRSValue[1]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


		/*
		* STEP :
		  Action:Select resources RS1 and RS2,Click on Generate Report
		  Expected Result:Resource Detail Report is Displayed in XLSX Format.
			Title as 'Resource Detail Report'
			Following columns are displayed with appropriate data (Data entered while creating resources RS1 
			and RS2):
			1. Resources Name
			2. Type (Resource Type associated with RS1 and RS2)
			3. Address
			4. County
			5. Latitude
			6. Longitude
			7. EMResource ID
			8. AHA ID
			9. Website
			10. Contact
			11. Phone 1
			12. Phone 2
			13. Fax
			14. Email
			15. Notes
		*/
		//660784
			try {
				assertEquals("", strFuncResult);
				String[] strRSValues={strRSValue[0],strRSValue[1]};
				strFuncResult = objReports.generateResourceDetailReport(selenium,
						strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			int intCount = 0;
			int intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Resource Detail Report", "", "", "", "", "", "", "",
								"", "", "", "", "", "", "" },
						{ "Resource Name", "Type", "Address", "County",
								"Latitude", "Longitude", "EMResource ID",
								"AHA ID", "Website", "Contact", "Phone 1",
								"Phone 2", "Fax", "Email", "Notes" },
						{ strResource1, strResrcTypName, "AL ", strCountry,
								strLongitude, strLatitude, strRSValue[0], "",
								"", strContFName + " " + strContLName, "", "",
								"", "", "" },
						{ strResource2, strResrcTypName, "AL ", strCountry,
								strLongitude1, strLatitude1, strRSValue[1], "",
								"", strContFName + " " + strContLName, "", "",
								"", "", "" } };

				strFuncResult = OFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		/*
		* STEP :
		  Action:Login as U2 and navigate to Report>>Resource reports ,Click on 'Resource Details'
		  Expected Result:'Resource Detail Report' screen is displayed with:
			Resources:Select All(Check box) 
			Only Resource RS1 is listed under it.
		*/
		//660786
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName2,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceReportsMenu(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.navResourceDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objReports.verifyRSInRsDeatilReport(selenium,
						strRSValue[0]);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

		/*
		* STEP :
		  Action:Select resource RS1 and Click on Generate Report
		  Expected Result:Resource Detail Report is Displayed in XLSX Format with appropriate data 
		  of resource RS1
		*/
		//660787
			try {
				assertEquals("", strFuncResult);
				String[] strRSValues={strRSValue[0]};
				strFuncResult = objReports.generateResourceDetailReport(selenium,
						strRSValues);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt <  Integer.parseInt(propEnvDetails
						.getProperty("WaitForReport")));

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			 intCount = 0;
			 intCount1 = 0;
			do {

				try {
					assertTrue(new File(strCSVDownlPath).exists());

					Runtime.getRuntime().exec(
							"wscript D:\\eclipse\\com.qsgsoft.EMResource\\SupportFiles\\"
									+ "AutoItFiles\\ConvertxlsxToxls.vbs "
									+ strCSVDownlPathRenamed + " "
									+ strCSVFileNameRenamed1);

					intCount1 = 0;
					do {
						try {
							assertTrue(new File(strCSVDownlRenamedPath1)
									.exists());
							break;
						} catch (AssertionError Ae) {
							Thread.sleep(1000);
							intCount1++;
						}

					} while (intCount1 < 60);

					break;
				} catch (AssertionError Ae) {
					Thread.sleep(1000);
					intCount++;
				}
			} while (intCount < 60);

			try {
				assertEquals("", strFuncResult);

				String[][] strReportData = {
						{ "Resource Detail Report", "", "", "", "", "", "", "",
								"", "", "", "", "", "", "" },
						{ "Resource Name", "Type", "Address", "County",
								"Latitude", "Longitude", "EMResource ID",
								"AHA ID", "Website", "Contact", "Phone 1",
								"Phone 2", "Fax", "Email", "Notes" },
						{ strResource1, strResrcTypName, "AL ", strCountry,
								strLongitude, strLatitude, strRSValue[0], "",
								"", strContFName + " " + strContLName, "", "",
								"", "", "" } };

				strFuncResult = OFC.readAndVerifyDataExcel(strReportData,
						strCSVDownlRenamedPath1);
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
			gstrTCID = "BQS-124624";
			gstrTO = "Verify that user can generate 'Resource Detail report' providing mandatory data.";
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
  //end//testBQS124624//
}

