package com.qsgsoft.EMResource.features;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.*;
import com.qsgsoft.EMResource.shared.*;
import com.qsgsoft.EMResource.support.*;
import com.thoughtworks.selenium.DefaultSelenium;
import com.thoughtworks.selenium.Selenium;

/********************************************************************
' Description		:This class contains test cases from requirement
' Requirement		:Status Detail Report
' Requirement Group	:Reports
' Product		    :EMResource v3.17
' Date			    :06-07-2012
' Author		    :QSG
---------------------------------------------------------------------
' Modified Date				                            Modified By
' Date					                                Name
'*******************************************************************/

public class StatusDetailReport {

	Date dtStartDate;
	ReadData rdExcel;
	static Logger log4j = Logger
			.getLogger("com.qsgsoft.EMResource.features.StatusDetailReport");
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
	Selenium selenium, seleniumPrecondition;
	String gstrTimeOut;

	/*@BeforeClass
	public static void setUpfetchExternalIP() throws Exception {

		//Fetch external IP
		int intCnt=0;
		OfficeCommonFunctions objOFC1;
		objOFC1=new OfficeCommonFunctions();
		String IP = objOFC1.externalIP();
		
		
		while(IP.contains("java.net.SocketTimeoutException: connect timed out")&&intCnt<20){
			IP = objOFC1.externalIP();
			intCnt++;
			log4j.info(intCnt);
		}
	}

*/
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

		seleniumPrecondition = new DefaultSelenium(
				propEnvDetails.getProperty("Server"), 4444,
				propEnvDetails.getProperty("BrowserPrecondition"),
				propEnvDetails.getProperty("urlEU"));

		seleniumPrecondition.start();
		seleniumPrecondition.windowMaximize();

		selenium.start();
		selenium.windowMaximize();

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

		gstrReason = gstrReason.replaceAll("'", " ");
		objOFC.WriteResultdb_Excel(gstrTCID, gstrTO, gstrResult, gstrReason,
				gdbTimeTaken, FILE_PATH, blnwriteres, gstrTimetake, gstrdate,
				gslsysDateTime, gstrBrowserName, gstrBuild, strSessionId);
	}
	
	/****************************************************************************************
	'Description	:Generate 'Status Detail report' in PDF format and verify that the report displays correct data.
	'Precondition	:1.Role based,shared and private status types NST,MST,sSST,sTST,pMST and pSST are created.
						(Multi status types created with statuses)
						2.Resource type RT is associated with all the status types.
						3. Resources RS is created with address under RT.
						4. User U1 has following rights:
						Role to view and update all status types.
						'Update Status' and 'Run Report' rights on resource RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:29-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS103962() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral=new General();
		Views objViews=new Views();
		
		try {
			gstrTCID = "BQS-103962"; 
			gstrTO = "Generate 'Status Detail report' in PDF format and " +
					"verify that the report displays correct data.";
			gstrReason = "";
			gstrResult = "FAIL";
		
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			//Role based
			String statrNumTypeName = "rNST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			
			//Shared 
			String statsSaturatTypeName = "sSST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			
			//Private
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			
			String strStatTypDefn = "Automation";
			String strStatTypeColor = "Black";
			String stNSTValue = "Number";
			String stMSTValue = "Multi";
			String stTSTValue = "Text";
			String stSSTValue = "Saturation Score";
			
			String strStatusName1 = "rSa" + strTimeText;
			String strStatusName2 = "rSb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			
			String stpStatusName1 = "pSa" + strTimeText;
			String stpStatusName2 = "pSb" + strTimeText;
			String stpStatusValue[] = new String[2];
			stpStatusValue[0] = "";
			stpStatusValue[1] = "";

			String strSTvalue[] = new String[6];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";

			String strApplTime="";
			
			String strStatusUpdateValue1="101";
			String strStatusUpdateValue2=strStatusName2;
			String strStatusUpdateValue3="429";
			String strStatusUpdateValue4="Auto";
			String strStatusUpdateValue5=stpStatusName2;
			String strStatusUpdateValue6="393";
			
			
			String strStatusUpdateDate1="";
			String strStatusUpdateDate2="";
			String strStatusUpdateDate3="";
			String strStatusUpdateDate4="";
			String strStatusUpdateDate5="";
			String strStatusUpdateDate6="";
			
			String strStatusUpdateDateEST1="";
			String strStatusUpdateDateEST2="";
			String strStatusUpdateDateEST3="";
			String strStatusUpdateDateEST4="";
			String strStatusUpdateDateEST6="";
			
			String strStatusGenerateDate="";
			
			String strStatusGenerateDateEST1="";
			String strStatusGenerateDateEST2="";
			String strStatusGenerateDateEST3="";
			String strStatusGenerateDateEST4="";
			String strStatusGenerateDateEST6="";
			
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strPDFDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "1.pdf";

			String strPDFDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "2.pdf";

			String strPDFDownlPath_3 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "3.pdf";

			String strPDFDownlPath_4 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "4.pdf";

			String strPDFDownlPath_5 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "5.pdf";

			log4j.info("~~~~~PRE CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1.Role based,shared and private status types
			 * NST,MST,sSST,sTST,pMST and pSST are created.
			 * 
			 * (Multi status types created with statuses)
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Role based
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stNSTValue, statrNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stMSTValue, statrMultiTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrMultiTypeName);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, strStatusName1,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, strStatusName2,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Shared
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stSSTValue, statsSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, "SHARED", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsSaturatTypeName);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stTSTValue, statsTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, "SHARED", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsTextTypeName);

				if (strSTvalue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Private
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, stMSTValue, statpMultiTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[4] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpMultiTypeName);

				if (strSTvalue[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, stpStatusName1,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, stpStatusName2,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				stpStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName, stpStatusName1);
				if (stpStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				stpStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName, stpStatusName2);
				if (stpStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, stSSTValue, statpSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[5] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpSaturatTypeName);

				if (strSTvalue[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2.Resource type RT is associated with all the status types.
			 */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				for(int i=1;i<strSTvalue.length;i++){
					seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
							+ strSTvalue[i] + "']");
				}
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resources RS is created with address under RT. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. Role R1 is created selecting status type ST under view and
			 * update.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" }, { strSTvalue[4], "true" },
						{ strSTvalue[5], "true" } };
				strFuncResult = objRole
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*4. User U1 has following rights:

				'Report - Status Detail'
				Role to view and update all status types.
				'Update Status' and 'Run Report' rights on resource RS. 
			*/

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			

			/*
			 * 2 Login as user U1,Navigate to View>>Map Regional Map View screen
			 * is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Select resource 'RS' from 'Find resource' drop down. Resource
			 * info pop window of RS is displayed with 'View Info' and 'Update
			 * status' links.
			 * 
			 * Resource RS is displayed with Status types NST,MST,sSST,sTST,pMST
			 * and pSST
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Update all the status types with some statuses on day D1 for
			 * resource RS. Updated status value is displayed on resource pop up
			 * window.
			 */
			
			/*******************************************************************************/	
			
			//Role based
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue1, strSTvalue[0], false, "", "");
				strStatusUpdateDateEST1 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[1], false, "", "");
				strStatusUpdateDateEST2 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Private

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[2]);
				
				strStatusUpdateDateEST3 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue4, strSTvalue[3], false, "", "");
				
				strStatusUpdateDateEST4 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Private
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						stpStatusValue[1], strSTvalue[4], false, "", "");
				//strStatusUpdateDateEST5 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[5]);
				
				strStatusUpdateDateEST6 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
				
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*******************************************************************************/	
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue1);

				strStatusUpdateDate1 = selenium.getText("//span[text()='"+strStatusUpdateValue1+"" +
						"']/following-sibling::span[1]");
				strStatusUpdateDate1 = strStatusUpdateDate1.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate1.split(" ");
				String strSplitTime[]=strLastUpdArr[2].split(":");
				strStatusUpdateDate1 = strSplitTime[0]+":"+strSplitTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue2);

				strStatusUpdateDate2 = selenium.getText("//span[text()='"+strStatusUpdateValue2+"" +
						"']/following-sibling::span[1]");
				strStatusUpdateDate2 = strStatusUpdateDate2.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate2.split(" ");
				String strSplitTime[]=strLastUpdArr[2].split(":");
				strStatusUpdateDate2 = strSplitTime[0]+":"+strSplitTime[1];
				
	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusUpdateValue3);
				strStatusUpdateDate3 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue3 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate3 = strStatusUpdateDate3.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate3.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate3= strSplitTime[0] + ":" + strSplitTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue4);
				
				strStatusUpdateDate4 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue4 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate4 = strStatusUpdateDate4.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate4.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate4= strSplitTime[0] + ":" + strSplitTime[1];

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue5);
				
				strStatusUpdateDate5 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue5 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate5 = strStatusUpdateDate5.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate5.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate5= strSplitTime[0] + ":" + strSplitTime[1];
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue6);
				
				strStatusUpdateDate6 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue6 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate6 = strStatusUpdateDate6.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate6.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate6= strSplitTime[0] + ":" + strSplitTime[1];
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*******************************************************************************/	
			/*
			 * 5 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dro pdown
			 * with status types (all the status types are listed)
			 */
				
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				String[] statTypeName = { statrNumTypeName, statrMultiTypeName,
						statsSaturatTypeName, statsTextTypeName,
						statpSaturatTypeName, statpMultiTypeName };
				strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
						selenium, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select MST and click on 'Next'
			 * 'Status Detail Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type MST 2. Statuses ST1,ST2 3. Resources RS.
			 */
				
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, true,
						statrMultiTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statrMultiTypeName, strResource, true,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, true, strResVal);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				strStatusGenerateDateEST2 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss.SSS");
				
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				
				//strStatusGenerateDate = strCurentDat[2] +":"+ strCurentDat[3];
				strStatusGenerateDate=strStatusGenerateDateEST2.substring(0, 5);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			
			/*
			 * 7 Select the statuses S1,S2 and resource RS and click on
			 * 'Generate Report'. Status Detail Report is generated in the CSV
			 * (Comma Separated Values) format with sections 'Status Detail' and
			 * 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date (day D1 date & time)
			 * (DD-MM-YYYY HH:MM) 4. End Date (day D1 date & time) (DD-MM-YYYY
			 * HH:MM) 5. Duration ((total number of minutes on that
			 * status/60)*100) 6. User 7. IP 8. Trace 9. Comments
			 * 
			 * 'Status Summary' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource :(Name of resource) 2. Status :(Updated status name)
			 * 3. Total Hours :(Total number of hours in the reporting period
			 * for that particular status i.e (End date & time - Start date &
			 * time) / 60 4. % of Total Hours :(Status reason report Time (Hrs)/
			 * Total Hours in report period for all the statuses reporting)
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate2;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;

				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST2, strStatusUpdateDateEST2,
						"HH:mm:ss.SSS");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue2,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_1,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			/*
			 * 8 Navigate to Reports>>Status Reports, click on 'Status
			 * Detail',select CSV option from 'Report Format' Provide D1 as the
			 * date for 'Start Date' and 'End Date' fields, select NST on
			 * 'Status Detail Report (Step 1 of 2)' screen and click on Next
			 * 'Status Detail Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type NST 2. Resources RS.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statrNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statrNumTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				strStatusGenerateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");
				
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate=strStatusGenerateDateEST1.substring(0, 5);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate1;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST1, strStatusUpdateDateEST1,
						"HH:mm:ss.SSS");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue1,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_2,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * ,Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select sTST on 'Status Detail
			 * Report (Step 1 of 2)' screen and click on Next 'Status Detail
			 * Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type sTST 2. Resources RS.
			 */
			/*
			 * 11 Select RS and click on 'Generate Report' Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * Details of resource RS are displayed appropriately with following
			 * columns:
			 * 
			 * 1. Status Value 2. Status Start Date (day D1 date & time)
			 * (DD-MM-YYYY HH:MM) 3. Status End Date (day D1 date & time)
			 * (DD-MM-YYYY HH:MM) 4. Duration (Hrs) 5. User 6. IP 7. Trace 8.
			 * Comments
			 * 
			 * 'Status Summary' section for the resource displays following
			 * details: 1. Resource 2. Status (Updated status name) 3. Total
			 * Hours :(Total number of hours in the reporting period for that
			 * particular status i.e (End date & time - Start date & time) / 60
			 * 4. % of Total Hours :(Status reason report Time (Hrs)/ Total
			 * Hours in report period for all the statuses reporting)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statsTextTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statsTextTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				


				strStatusGenerateDateEST4 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss.SSS");
				
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate=strStatusGenerateDateEST4.substring(0, 5);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_3 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate4;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST4, strStatusUpdateDateEST4,
						"HH:mm:ss.SSS");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue4,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_3,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*12 	Navigate to Reports>>Status Reports, click on 'Status Detail' 		Status Detail Report (Step 1 of 2)' screen is displayed with:

			1. 'Start Date' field (with calender widget)
			2. 'End Date' field (with calender widget)
			3. 'Report Format' (with PDF and CSV options, PDF is selected by default)
			4. Status Types dro pdown with status types (all the status types are listed)
			13 	Provide D1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format, select pSST and click on 'Next' 		'Status Detail Report (Step 2 of 2)' screen is displayed with:

			1. Status type pSST
			2. Resources RS.
			14 	Select RS and click on 'Generate Report' 		ED Saturation Report is generated in the CSV (Comma Separated Values) format with report period.
			ED Saturation Report displays following columns with appropriate data provided while updating status type:

			1. Date Time
			2. Resource
			3. ED Beds Occupied
			4. Pts in Lobby
			5. Amb wait
			6. General Admits
			7. ICU Admits
			8. 1 on 1 Pts
			9. Excess Lobby Wait
			10. RNs short-staffed
			11. Assigned ED Beds
			12. Lobby Capacity
			13. Sat Score
			14. Charge RN/Mgr
			15. Physician
			16. Comments 
			
			*/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statpSaturatTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statpSaturatTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				strStatusGenerateDateEST6 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss");

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate=strStatusGenerateDateEST6.substring(0, 5);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_4 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate6;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST6, strStatusUpdateDateEST6,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue6,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_4,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 15 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dropdown
			 * with status types (all the status types are listed) 16 Provide D1
			 * as the date for 'Start Date' and 'End Date' fields, select CSV
			 * for report format, select sSST and click on 'Next' 'Status Detail
			 * Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type sSST 2. Resources RS. 17 Select RS and click on
			 * 'Generate Report' ED Saturation Report is generated in the CSV
			 * (Comma Separated Values) format with report period. ED Saturation
			 * Report displays following columns with appropriate data provided
			 * while updating status type:
			 * 
			 * 1. Date Time 2. Resource 3. ED Beds Occupied 4. Pts in Lobby 5.
			 * Amb wait 6. General Admits 7. ICU Admits 8. 1 on 1 Pts 9. Excess
			 * Lobby Wait 10. RNs short-staffed 11. Assigned ED Beds 12. Lobby
			 * Capacity 13. Sat Score 14. Charge RN/Mgr 15. Physician 16.
			 * Comments
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statsSaturatTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statsSaturatTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");

				
				strStatusGenerateDateEST3 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss");
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate = strStatusGenerateDateEST3.substring(0, 5);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			
			try {
				assertEquals("", strFuncResult);

				
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath_5 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate3;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST3, strStatusUpdateDateEST3,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue3,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath_5,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				File Pdf1 = new File(strPDFDownlPath_1);
				File Pdf2 = new File(strPDFDownlPath_2);
				File Pdf3 = new File(strPDFDownlPath_3);
				File Pdf4 = new File(strPDFDownlPath_4);
				File Pdf5 = new File(strPDFDownlPath_5);

				if (Pdf1.isFile() && Pdf2.isFile() && Pdf3.isFile()
						&& Pdf4.isFile() && Pdf5.isFile()) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103962";
			gstrTO = "Generate 'Status Detail report' in PDF format and verify that the report displays correct data.";
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
	
	
	
	@Test
	public void testBQS63390() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-63390"; // Test Case Id
			gstrTO = "Update status of a numeric status type NST added at the "
					+ "resource level for a resource RS. Verify that a user with"
					+ " �Run Report� and 'View Resource' rights on RS and with a"
					+ " role with view right for NST can view the status of NST "
					+ "in the generated status detail report.";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
	
		
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strApplTime="";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
				
			String statTypeName="NST"+strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
		
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			//Application time
			String strUpdatdVale="";
			String strUpdatedDate="";
			String strReportGenrtTime="";
			String strReportGenrtTimePDF="";
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
			
			//System Time
			String strTimeGenerateSystem="";
			String strTimeUpdateSystem="";
			
			String strTimePDFGenerateSystem="";
			String strDurationDiffPDF="";
			
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
			//String strStatusValue[]={"35474","35475"};
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			
		
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			
			String PDFName="StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String[] strTestDataCSV= null;
			String[] strTestDataPDF = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//1. Status Type NST(numeric status type) is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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
			

			//2. Resource type RT is associated with status type ST1. 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//3. Resources RS is created under RT. 
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */
			
			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

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
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			log4j.info("~~~~~PRE-CONDITION- " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			//Update Status
			try {
				assertEquals("", strFuncResult);

				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						seleniumPrecondition, strResource, strSTvalue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);
				
				//Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
				
				
				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiffPDF);

				//Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "+strReportGenrtTimePDF);

				strTestDataPDF = new String[]{ propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF,
						strUserNameA,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						PDFName,
						"Status Detail Report need to be checked in PDF file"

				};
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];
				
				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time "+strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt<180);
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strCurrDate=dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat =strUpdatedDate; 
				String strDurationGenerat =strReportGenrtTime;
				
				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);
				
				double fltDurationDiff=(double)intDurationDiff/3600;
				
				double dRounded=dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiff);
				
				strTestDataCSV =new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff,
						strUserNameA,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath,
						"Status Detail Report need to be check in CSV file"

				};
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			File Pf1=new File(strCSVDownlPath);
			File Cf1=new File(strPDFDownlPath);
			
			
			if(Pf1.exists()&&Cf1.exists()){
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";
					
					
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataCSV, strWriteFilePath, "Status_Detail_Report");
					objOFC.writeResultData(strTestDataPDF, strWriteFilePath, "Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

		
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63390";
			gstrTO = "Update status of a numeric status type NST added at the"
					+ " resource level for a resource RS. Verify that a user with"
					+ " �Run Report� and 'View Resource' rights on RS and with a"
					+ " role with view right for NST can view the status of NST "
					+ "in the generated status detail report.";
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
		
	/****************************************************************************************
	'Description	:Add a number status type NST back to resource RS and update the status 
	'				value of NST for RS, generate the �Status Detail Report� and verify that
	'				 the data is displayed appropriately in the report 
	'Precondition	:1. Resource type RT1 is associated with status type ST1 and NOT with
	'				 status type NST (number)
	'				 2. Resources RS1 is created under RT1.
	'				 3. Status Type NST is added for resource RS1 at the resource level
	'				 4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status type NST
					'Update Status' and 'Run Report' rights on resources RS1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:27-June-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	
	@Test
	public void testBQS63336() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();

		try {
			gstrTCID = "BQS-63336"; // Test Case Id
			gstrTO = "Add a number status type NST back to resource RS and"
					+ " update the status value of NST for RS, generate the"
					+ "�Status Detail Report� and verify that the data is "
					+ "displayed appropriately in the report";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "ST" + strTimeText;
			String statNumTypeName = "NST" + strTimeText;

			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strUpdatdVale="";
			String strUpdatdVale_1 = "";

			// System Time
			String strUpdatdValeSystem_1 = "";
			String strUpdatdGenrtdValeSystem_1 = "";

			String strUpdatdValeSystem_2 = "";
			String strUpdatdGenrtdValeSystem_2 = "";

			// Application time
			String strUpdatedDate = "";
			String strUpdatedDate_1 = "";

			String strReportGenrtTime = "";
			String strReportGenrtTime_1 = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + strTimeText;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			String[] strTestDataCSV1 = null;
			String[] strTestDataCSV2 = null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			// admin login
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create Number status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type ST1 is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 2. Resource type RT is associated with status type ST1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Precondition 4. User U1 has following rights:
			 * 
			 * 'Report - Status Detail' Role to update status type NST 'Update
			 * Status' and 'Run Report' rights on resources RS1
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST-CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statNumTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdate[] = { strSTvalue[1] };
				String strUpdateST[] = objViewMap.updateStatusTypeWithTime(
						selenium, strResource, strUpdate, "HH:mm:ss");
				strFuncResult = strUpdateST[0];

				strUpdatdValeSystem_1 = strUpdateST[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale_1 = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate_1 = selenium.getText("//div/span"
						+ "[text()='" + strUpdatdVale_1 + "']/parent::"
						+ "div/span[@class='time']");

				strUpdatedDate_1 = strUpdatedDate_1.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate_1.split(" ");
				strUpdatedDate_1 = strLastUpdArr[2];
				log4j.info(strUpdatedDate_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
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

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Deselect the status type NST and click on 'Save'. 'Resource
			 * List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String strResult[] = objRs
						.selDeselctSTInEditRSLevelPageWithTime(selenium,
								strSTvalue[1], false, "HH:mm:ss");
				strUpdatdGenrtdValeSystem_1 = strResult[1];
				strFuncResult = strResult[0];
				// Fetch System time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime_1 = strStatusTime[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select back status type 'NST' and click on Save 'Resource List'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[1], true);

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
			 * 8 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statNumTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdate[] = { strSTvalue[1] };

				String strUpdateST[] = objViewMap.updateStatusTypeWithTime(
						selenium, strResource, strUpdate, "HH:mm:ss");
				strUpdatdValeSystem_2 = strUpdateST[1];
				strFuncResult = strUpdateST[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span"
						+ "[text()='"+strUpdatdVale+"']/parent::" + "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 9 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * 'Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dropdown
			 * with status types (ST1, NST)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statTypeName + "']"));
					log4j.info("Status Types dropdown with status types (ST1 ="
							+ statTypeName + ") ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (ST1 ="
									+ statTypeName + ") ");
					strFuncResult = "Status Types dropdown NOT with status types (NST ="
							+ statTypeName + ")";
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statNumTypeName
									+ "']"));
					log4j.info("Status Types dropdown with status types (NST ="
							+ statNumTypeName + ") ");

				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (NST ="
									+ statNumTypeName + ") ");
					strFuncResult = "Status Types dropdown NOT with status types (NST ="
							+ statNumTypeName + ")";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 10 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select NST and click on Next 'Status Detail Report (Step 2 of 2)'
			 * screen is displayed with:
			 * 
			 * 1. Status type NST 2. Resources RS1. 11 Select RS1 and click on
			 * 'Generate Report' Status Detail Report is generated in the PDF
			 * format.
			 * 
			 * Header and Footer is with following details.
			 * 
			 * Header: 1. Start Date: dd-mm-yyyy 2. End Date: dd-mm-yyyy 3.
			 * Status Type
			 * 
			 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
			 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
			 * Emsystems logo 5. Page number
			 * 
			 * Details of resource RS1 are displayed appropriately with
			 * following columns:
			 * 
			 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
			 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
			 */

			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statNumTypeName, "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "2",
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

				strTestDataPDF2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "1",
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * No Expected Result
			 */
			/*
			 * 13 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select NST and click on Next No
			 * Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Select RS1 and click on 'Generate Report' Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
			 * User 7. IP 8. Trace 9. Comments
			 */
			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statNumTypeName, "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);
			

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "2",
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

				strTestDataCSV2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "1",
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63336";
			gstrTO = "Add a number status type NST back to resource RS and "
					+ "update the status value of NST for RS, generate the "
					+ "�Status Detail Report� and verify that the data is"
					+ " displayed appropriately in the report";
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
	
	/******************************************************************************************
	'Description	:Add a text status type TST back to resource RS and update the status value
	'				  of TST for RS, generate the �Status Detail Report� and verify that the data 
	'				 is displayed appropriately in the report
	'Precondition	:1. Resource type RT1 is associated with status type ST1 and NOT with
	'				 status type TST (number)
	'				 2. Resources RS1 is created under RT1.
	'				 3. Status Type TST is added for resource RS1 at the resource level
	'				 4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status type TST
					'Update Status' and 'Run Report' rights on resources RS1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:28-June-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	
	@Test
	public void testBQS63369() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();

		try {
			gstrTCID = "BQS-63369"; // Test Case Id
			gstrTO = "Add a text status type TST back to resource RS and update"
					+ " the status value of TST for RS, generate the �Status Detail"
					+ " Report� and verify that the data is displayed appropriately "
					+ "in the report";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strFILE_PATH = pathProps.getProperty("TestData_path");


			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
			"M/d/yyyy");
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName1 = "TST_1" + strTimeText;
			String statTypeName2 = "TST_2" + strTimeText;

			String strStatusTypeValue = "Text";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];
			String strRSValue[] = new String[1];

			String strUpdatdVale = "";
			String strUpdatdVale_1 = "";

			// System Time
			String strUpdatdValeSystem_1 = "";
			String strUpdatdGenrtdValeSystem_1 = "";

			String strUpdatdValeSystem_2 = "";
			String strUpdatdGenrtdValeSystem_2 = "";

			// Application time
			String strUpdatedDate = "";
			String strUpdatedDate_1 = "";

			String strReportGenrtTime = "";
			String strReportGenrtTime_1 = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + strTimeText;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataCSV2 = null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// admin login
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create Number status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type ST1 is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 2. Resource type RT is associated with status type ST1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Precondition 4. User U1 has following rights:
			 * 
			 * 'Report - Status Detail' Role to update status type NST 'Update
			 * Status' and 'Run Report' rights on resources RS1
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/*
			 * 2 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdate[] = { strSTvalue[0] };
				String strUpdateST[] = objViewMap.updateStatusTypeWithTime(
						selenium, strResource, strUpdate, "HH:mm:ss");
				strFuncResult = strUpdateST[0];

				strUpdatdValeSystem_1 = strUpdateST[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale_1 = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate_1 = selenium.getText("//div/span"
						+ "[text()='" + strUpdatdVale_1 + "']/parent::"
						+ "div/span[@class='time']");

				strUpdatedDate_1 = strUpdatedDate_1.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate_1.split(" ");
				strUpdatedDate_1 = strLastUpdArr[2];
				log4j.info(strUpdatedDate_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
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

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Deselect the status type NST and click on 'Save'. 'Resource
			 * List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String strResult[] = objRs
						.selDeselctSTInEditRSLevelPageWithTime(selenium,
								strSTvalue[1], false, "HH:mm:ss");
				strUpdatdGenrtdValeSystem_1 = strResult[1];
				strFuncResult = strResult[0];
				// Fetch System time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime_1 = strStatusTime[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select back status type 'NST' and click on Save 'Resource List'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[0], true);
			
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
			 * 8 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				String strUpdate[] = { strSTvalue[0] };

				String strUpdateST[] = objViewMap.updateStatusTypeWithTime(
						selenium, strResource, strUpdate, "HH:mm:ss");
				strUpdatdValeSystem_2 = strUpdateST[1];
				strFuncResult = strUpdateST[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 9 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * 'Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dropdown
			 * with status types (ST1, NST)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statTypeName2 + "']"));
					log4j.info("Status Types dropdown with status types (ST1 ="
							+ statTypeName2 + ") ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (ST1 ="
									+ statTypeName2 + ") ");
					strFuncResult = "Status Types dropdown NOT with status types (NST ="
							+ statTypeName2 + ")";
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statTypeName1 + "']"));
					log4j.info("Status Types dropdown with status types (NST ="
							+ statTypeName1 + ") ");

				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (NST ="
									+ statTypeName1 + ") ");
					strFuncResult = "Status Types dropdown NOT with status types (NST ="
							+ statTypeName1 + ")";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 10 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select NST and click on Next 'Status Detail Report (Step 2 of 2)'
			 * screen is displayed with:
			 * 
			 * 1. Status type NST 2. Resources RS1. 11 Select RS1 and click on
			 * 'Generate Report' Status Detail Report is generated in the PDF
			 * format.
			 * 
			 * Header and Footer is with following details.
			 * 
			 * Header: 1. Start Date: dd-mm-yyyy 2. End Date: dd-mm-yyyy 3.
			 * Status Type
			 * 
			 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
			 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
			 * Emsystems logo 5. Page number
			 * 
			 * Details of resource RS1 are displayed appropriately with
			 * following columns:
			 * 
			 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
			 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
			 */
		
			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName1, "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

				strTestDataPDF2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/*
			 * 12 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * No Expected Result
			 */
			/*
			 * 13 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select NST and click on Next No
			 * Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Select RS1 and click on 'Generate Report' Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
			 * User 7. IP 8. Trace 9. Comments
			 */
			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName1, "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);
			

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

				strTestDataCSV2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63369";
			gstrTO = "Add a text status type TST back to resource RS "
					+ "and update the status value of TST for RS, generate"
					+ " the �Status Detail Report� and verify that the data "
					+ "is displayed appropriately in the report";
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
	
	
	
	/****************************************************************************************
	'Description	:Add a number status type SST back to resource RS and update the status 
	'				value of SST for RS, generate the �Status Detail Report� and verify that
	'				 the data is displayed appropriately in the report 
	'Precondition	:1. Resource type RT1 is associated with status type ST1 and NOT with
	'				 status type SST (number)
	'				 2. Resources RS1 is created under RT1.
	'				 3. Status Type SST is added for resource RS1 at the resource level
	'				 4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status type SST
					'Update Status' and 'Run Report' rights on resources RS1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:3-July-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	
	@Test
	public void testBQS63379() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		propElementAutoItDetails = objAP.ReadAutoit_FilePath();

		try {
			gstrTCID = "BQS-63379"; // Test Case Id
			gstrTO = "Add a saturation score status type SST back to resource RS and"
					+ " update the status value of SST for RS, generate the"
					+ "�Status Detail Report� and verify that the data is "
					+ "displayed appropriately in the report";
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

			String strFILE_PATH = pathProps.getProperty("TestData_path");

	

			// Current System date and time
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");
			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
					"M/d/yyyy");

			// Admin login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName1 = "SST_1" + strTimeText;
			String statTypeName2 = "SST_2" + strTimeText;

			// Status Type Category
			String strStatusTypeValue = "Saturation Score";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[2];// Status Type value
			String strRSValue[] = new String[1];// Resource value

			String strUpdatdVale = "";
			String strUpdatdVale_1 = "";

			// System Time

			String strUpdatdValeSystem_1 = "";
			String strUpdatdGenrtdValeSystem_1 = "";

			String strUpdatdValeSystem_2 = "";
			String strUpdatdGenrtdValeSystem_2 = "";

			// Application time
			String strUpdatedDate = "";
			String strUpdatedDate_1 = "";

			String strReportGenrtTime = "";
			String strReportGenrtTime_1 = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + strTimeText;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;

			// Search Criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");

			// Roel Name and Role Rights
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataCSV2 = null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			// Admin login
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

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

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

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type ST1 is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 2. Resource type RT is associated with status type ST1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT along witj lookup address

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Fetch Resource value

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Add Status type in Edit Resource Level Page

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * Precondition 4. User U1 has following rights:
			 * 
			 * 'Report - Status Detail' Role to update status type NST 'Update
			 * Status' and 'Run Report' rights on resources RS1
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 2 Login as user U1, update the status of SST on day D1 for
			 * resource RS1. No Expected Result
			 */

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Status updated for the first time
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				String strUpdateST[] = objStatusTypes
						.updateSatuScoreStatusTypeWitTime(selenium,
								strResource, statTypeName1, strSTvalue[0],
								strUpdateValue1, strUpdateValue2, "393", "429",
								"HH:mm:ss");
				strFuncResult = strUpdateST[0];

				strUpdatdValeSystem_1 = strUpdateST[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale_1 = selenium
						.getText("css=div.emsText.maxheight > span");
				System.out.println(strUpdatdVale_1);

				strUpdatedDate_1 = selenium.getText("//div/span"
						+ "[text()='" + strUpdatdVale_1 + "']/parent::"
						+ "div/span[@class='time']");

				strUpdatedDate_1 = strUpdatedDate_1.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate_1.split(" ");
				strUpdatedDate_1 = strLastUpdArr[2];
				log4j.info(strUpdatedDate_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
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

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Deselect the status type NST and click on 'Save'. 'Resource
			 * List' screen is displayed.
			 */

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String strEditST[] = objRs
						.selDeselctSTInEditRSLevelPageWithTime(selenium,
								strSTvalue[0], false, "HH:mm:ss");

				strFuncResult = strEditST[0];
				strUpdatdGenrtdValeSystem_1 = strEditST[1];

				// Fetch System time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime_1 = strStatusTime[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 6 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select back status type 'NST' and click on Save 'Resource List'
			 * screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[0], true);

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
			 * 8 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Status updated for the second time
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				String strUpdateResult[] = objStatusTypes
						.updateSatuScoreStatusTypeWitTime(selenium,
								strResource, statTypeName1, strSTvalue[0],
								strUpdateValue1, strUpdateValue2, "393", "429",
								"HH:mm:ss");
				strFuncResult = strUpdateResult[0];
				strUpdatdValeSystem_2 = strUpdateResult[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");
				System.out.println(strUpdatdVale);

				strUpdatedDate = selenium.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 9 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * 'Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dropdown
			 * with status types (ST1, NST)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statTypeName2 + "']"));
					log4j.info("Status Types dropdown with status types (ST1 ="
							+ statTypeName2 + ") ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (ST1 ="
									+ statTypeName2 + ") ");
					strFuncResult = "Status Types dropdown NOT with status types (NST ="
							+ statTypeName2 + ")";
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statTypeName1 + "']"));
					log4j.info("Status Types dropdown with status types (NST ="
							+ statTypeName1 + ") ");

				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (NST ="
									+ statTypeName1 + ") ");
					strFuncResult = "Status Types dropdown NOT with status types (NST ="
							+ statTypeName1 + ")";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 10 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select NST and click on Next 'Status Detail Report (Step 2 of 2)'
			 * screen is displayed with:
			 * 
			 * 1. Status type NST 2. Resources RS1. 11 Select RS1 and click on
			 * 'Generate Report' Status Detail Report is generated in the PDF
			 * format.
			 * 
			 * Header and Footer is with following details.
			 * 
			 * Header: 1. Start Date: dd-mm-yyyy 2. End Date: dd-mm-yyyy 3.
			 * Status Type
			 * 
			 * Footer: 1. Report Run By: (name of the user) 2. From: (Name of
			 * the Region) 3. On: MM/DD/YYYY HH:MM:SS (Time Zone) 4. Intermedix
			 * Emsystems logo 5. Page number
			 * 
			 * Details of resource RS1 are displayed appropriately with
			 * following columns:
			 * 
			 * 1. Status Value 2. Status Start Date 3. Status End Date 4.
			 * Duration (Hrs) 5. User 6. IP 7. Trace 8. Comments
			 */

			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName1, "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "393",
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

				strTestDataPDF2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "429",
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 12 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * No Expected Result
			 */
			/*
			 * 13 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select NST and click on Next No
			 * Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 14 Select RS1 and click on 'Generate Report' Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
			 * User 7. IP 8. Trace 9. Comments
			 */

			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName1, "HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "393",
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

				strTestDataCSV2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, "429",
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63379";
			gstrTO = "Add a number status type NST back to resource RS and "
					+ "update the status value of SST for RS, generate the "
					+ "�Status Detail Report� and verify that the data is"
					+ " displayed appropriately in the report";
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
	
	/****************************************************************************************
	'Description	:Update the status of a numeric private status type pNST added at
	'				 the resource level for a resource RS. Verify that a user with �Run Report�
	'				 and 'View Resource' rights on RS and without any role can view the status 
	'				of pNST for RS in the generated status detail report. 
	'Precondition	:1. Resource type RT1 is associated with status type ST1 and NOT with
	'				 status type NST (number)
	'				 2. Resources RS1 is created under RT1.
	'				 3. Status Type NST is added for resource RS1 at the resource level
	'				 4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status type NST
					'Update Status' and 'Run Report' rights on resources RS1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:27-June-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS63392() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "BQS-63392"; // Test Case Id
			gstrTO = "Update status of a numeric status type pNST added at the "
					+ "resource level for a resource RS. Verify that a user with"
					+ " �Run Report� and 'View Resource' rights on RS and with a"
					+ " role with view right for pNST can view the status of pNST "
					+ "in the generated status detail report.";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

	

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "pNST" + strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			// String strStatusValue[]={"35474","35475"};

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataPDF1 = null;

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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

			// 2. Resource type RT is associated with status type ST1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 6.Status Type NST is updated with some value on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						seleniumPrecondition, strResource, strSTvalue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "", strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63392";
			gstrTO = "Update status of a numeric status type pNST added at the"
					+ " resource level for a resource RS. Verify that a user with"
					+ " �Run Report� and 'View Resource' rights on RS and with a"
					+ " role with view right for pNST can view the status of pNST "
					+ "in the generated status detail report.";
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
	
	/****************************************************************************************
	'Description	:Update the status of a text private status type pTST added at the resource
	'				 level for a resource RS. Verify that a user with �Run Report� and 'View 
	'				Resource' rights on RS and without any role can view the status of pTST 
	'				for RS in the generated status detail report.
	'Precondition	:1. Private status Type pTST(Text status type) is created.
					2. Resource type RT is associated with status type ST1.
					3. Resources RS is created under RT.
					4. Private status Type pTST(Text status type) is added for resource RS at the resource level
					5. User U1 has following rights:
					a. Report - Status Detail.
					b. No role associated to User U1.
					c.'View Resource' and 'Run Report' rights on resources RS.
					6.Private status Type pTST is updated with some value on day D1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-July-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS63395() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "BQS-63395"; // Test Case Id
			gstrTO = "Update the status of a text private status type pTST "
					+ "added at the resource level for a resource RS. Verify "
					+ "that a user with �Run Report� and 'View Resource' rights "
					+ "on RS and without any role can view the status of pTST for "
					+ "RS in the generated status detail report.";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

	

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "pTST" + strTimeText;
			String strStatusTypeValue = "Text";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);



			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
	
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";

			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataPDF1 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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

			// 2. Resource type RT is associated with status type ST1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			// Update user

			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/* 6.Status Type NST is updated with some value on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						seleniumPrecondition, strResource, strSTvalue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "", strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		

			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63395";
			gstrTO = "Update the status of a text private status type pTST added"
					+ " at the resource level for a resource RS. Verify that"
					+ "a user with �Run Report� and 'View Resource' rights on"
					+ " RS and without any role can view the status of pTST"
					+ " for RS in the generated status detail report.";
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
	
	/****************************************************************************************
	'Description	:Update the status of a saturation score private status type pSST added 
	'				 at the resource level for a resource RS. Verify that a user with �Run 
	'				Report� and 'View Resource' rights on RS and without any role can view 
	'				the status of pSST for RS in the generated status detail report.
	'Precondition	:1. Private status Type pSST(saturation score status type) is created.
	'				2. Resource type RT is associated with status type ST1.
	'				3. Resources RS is created under RT.
	'				4. Private status Type pSST(saturation score status type) is added for resource RS at the resource level
	'				5. User U1 has following rights:
	'				a. Report - Status Detail.
	'				b. No role is associated to User U1.
	'				c.'View Resource' and 'Run Report' rights on resources RS.
	'				6.Private status Type pSST is updated with some value on day D1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:5-July-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/

	@Test
	public void testBQS63396() throws Exception {

		String strFuncResult = "";
		boolean blnLogin = false;

		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap = new ViewMap();
		Reports objRep = new Reports();
		StatusTypes objStatusTypes = new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral = new General();

		try {
			gstrTCID = "BQS-63396"; // Test Case Id
			gstrTO = "Update the status of a saturation score private status type pSST "
					+ "added at the resource level for a resource RS. Verify that a "
					+ "user with �Run Report� and 'View Resource' rights on RS and "
					+ "without any role can view the status of pSST for RS in the "
					+ "generated status detail report.";

			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");

	

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails = objAP.ReadAutoit_FilePath();
			String strTimeText = dts.getCurrentDate("MMddyyyy_HHmmss");

			String strApplTime = "";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			String statTypeName = "pSST" + strTimeText;
			String strStatusTypeValue = "Saturation Score";
			String strStatTypDefn = "Automation";
			String strStatValue = "";

			String strSTvalue[] = new String[1];
			String strRSValue[] = new String[1];

			// Application time
			String strUpdatdVale = "";
			String strUpdatedDate = "";
			String strReportGenrtTime = "";
			String strReportGenrtTimePDF = "";

			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;

			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);

			// System Time
			String strTimeGenerateSystem = "";
			String strTimeUpdateSystem = "";

			String strTimePDFGenerateSystem = "";
			String strDurationDiffPDF = "";

			String strCurrDate = dts.getCurrentDate("MM/dd/yyyy");
			// String strStatusValue[]={"35474","35475"};

			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";

			String PDFName = "StatSummary_" + gstrTCID + "_" + strTimeText
					+ ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			String[] strTestDataCSV1 = null;
			String[] strTestDataPDF1 = null;

			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 1. Status Type NST(numeric status type) is created.
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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

			// 2. Resource type RT is associated with status type ST1.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// 3. Resources RS is created under RT.

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */

			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			// Update user

			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			/* 6.Status Type NST is updated with some value on day D1. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				String strUpdateResult[] = objStatusTypes
						.updateSatuScoreStatusTypeWitTime(seleniumPrecondition,
								strResource, statTypeName, strSTvalue[0],
								strUpdateValue1, strUpdateValue2, "393", "429",
								"HH:mm:ss");
				strTimeUpdateSystem = strUpdateResult[1];
				strFuncResult = strUpdateResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span"
						+ "[text()='" + strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "", PDFName,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);
				String[] strReportData1 = { "ED Saturation Report", "**", "**",
						"**", "**", "**", "**", "**", "**", "**", "**", "**",
						"**", "**", "**", "**" };

				String[] strReportData2 = { "Report Period:  ",
						strCurrDate + " to " + strCurrDate, "**", "**", "**",
						"**", "**", "**", "**", "**", "**", "**", "**", "**",
						"**", "**" };

				String[] strReportData3 = { "Date Time", "Resource",
						"ED Beds Occupied", "Pts in Lobby", "Amb wait",
						"General Admits", "ICU Admits", "1 on 1 Pts",
						"Excess Lobby Wait", "RNs short-staffed",
						"Assigned ED Beds", "Lobby Capacity", "Sat Score",
						"Charge RN/Mgr", "Physician", "Comments" };

				String[] strReportData4 = {
						strCurrDate + " " + strDurationUpdat, strResource, "0",
						"1", "2", "3", "4", "5", "N", "6", "7", "8", "393",
						"**", "**", "**" };

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");
				objOFC.writeResultData(strReportData1, strWriteFilePath,
						"Status_Detail_Report");
				objOFC.writeResultData(strReportData2, strWriteFilePath,
						"Status_Detail_Report");
				objOFC.writeResultData(strReportData3, strWriteFilePath,
						"Status_Detail_Report");
				objOFC.writeResultData(strReportData4, strWriteFilePath,
						"Status_Detail_Report");

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "", strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63396";
			gstrTO = "Update the status of a saturation score private"
					+ " status type pSST added at the resource level for a"
					+ " resource RS. Verify that a user with �Run Report�"
					+ " and 'View Resource' rights on RS and without any role"
					+ " can view the status of pSST for RS in the generated "
					+ "status detail report.";
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
	
	/*******************************************************************************************
	'Description	:Update the status of a multi private status type pMST added at the resource
	'				 level for a resource RS. Verify that a user with �Run Report� and 'View Resource'
	'				 rights on RS and without any role can view the status of pMST for RS in the
	'				 generated status detail report.
	'Precondition	:1. Private status Type pMST(saturation score status type) is created.
	'				2. Resource type RT is associated with status type ST1.
	'				3. Resources RS is created under RT.
	'				4. Private status Type pSST(saturation score status type) is added for resource RS at the resource level
	'				5. User U1 has following rights:
	'				a. Report - Status Detail.
	'				b. No role is associated to User U1.
	'				c.'View Resource' and 'Run Report' rights on resources RS.
	'				6.Private status Type pMST is updated with some value on day D1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:7-July-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS63394() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-63394"; // Test Case Id
			gstrTO = "Update the status of a multi private status type "
					+ "pMST added at the resource level for a resource RS."
					+ " Verify that a user with �Run Report� and 'View Resource'"
					+ " rights on RS and without any role can view the status of"
					+ " pMST for RS in the generated status detail report.";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
				
			String strFILE_PATH = pathProps.getProperty("TestData_path");

		
			propElementAutoItDetails=objAP.ReadAutoit_FilePath();		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strApplTime="";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
			
			
				
			String statTypeName="pMST"+strTimeText;
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
			
			String strStatTypeColor="Black";
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
		
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			//Application time
			String strUpdatdVale="";
			String strUpdatedDate="";
			String strReportGenrtTime="";
			String strReportGenrtTimePDF="";
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			//System Time
			String strTimeGenerateSystem="";
			String strTimeUpdateSystem="";
			
			String strTimePDFGenerateSystem="";
			String strDurationDiffPDF="";
			
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			
		
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			String[] strTestDataCSV1 = null;
			String[] strTestDataPDF1 = null;
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//1. Status Type NST(numeric status type) is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypeVisibility(
						seleniumPrecondition, false, false, true);
				seleniumPrecondition.click(propElementDetails
						.getProperty("CreateStatusType.Save"));
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);
				
				int intCnt=0;
				do{
					try {

						assertTrue(seleniumPrecondition.isElementPresent("link=Return to Status Type List"));
						break;
					}catch(AssertionError Ae){
						Thread.sleep(1000);
						intCnt++;
					
					} catch (Exception Ae) {
						Thread.sleep(1000);
						intCnt++;
					}
				}while(intCnt<60);

				
				seleniumPrecondition.click("link=Return to Status Type List");
				seleniumPrecondition.waitForPageToLoad(gstrTimeOut);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertTrue(seleniumPrecondition
						.isElementPresent("//div[@id='mainContainer']/"
								+ "table/tbody/tr/td[2][text()='"
								+ statTypeName + "']"));

				log4j.info("Status type " + statTypeName
						+ " is created and is listed in the "
						+ "'Status Type List' screen. ");

				try {
					assertEquals("", strFuncResult);
					strStatValue = objStatusTypes.fetchSTValueInStatTypeList(
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
					strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
							seleniumPrecondition, statTypeName, strStatusName1, strStatusTypeValue,
							strStatTypeColor, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
	
				try {
					assertEquals("", strFuncResult);
					strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
							seleniumPrecondition, statTypeName, strStatusName2, strStatusTypeValue,
							strStatTypeColor, true);
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				
				try {
					assertEquals("", strFuncResult);
					strStatValue = objStatusTypes.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName1);
					if(strStatValue.compareTo("")!=0){
						strFuncResult="";
						strStatusValue[0]=strStatValue;
					}else{
						strFuncResult="Failed to fetch status value";
					}
					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
				try {
					assertEquals("", strFuncResult);
					strStatValue = objStatusTypes.fetchStatValInStatusList(seleniumPrecondition,statTypeName, strStatusName2);
					if(strStatValue.compareTo("")!=0){
						strFuncResult="";
						strStatusValue[1]=strStatValue;
					}else{
						strFuncResult="Failed to fetch status value";
					}
					
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
				
			} catch (AssertionError Ae) {

				log4j
						.info("Status type "
								+ statTypeName
								+ " is created and is NOT listed in the "
								+ "'Status Type List' screen. ");

				gstrReason = "Status type "
						+ statTypeName
						+ " is created and is NOT listed in the "
						+ "'Status Type List' screen. " + Ae;
			}
			
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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
			

			//2. Resource type RT is associated with status type ST1. 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//3. Resources RS is created under RT. 
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */
			
			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Update user
			
			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			// Update Status
			try {
				assertEquals("", strFuncResult);

				String strUpdateResult[] = objStatusTypes
						.updateMultiStatusType_ChangeValWithTime(seleniumPrecondition,
								strResource, statTypeName, strSTvalue[0],
								strStatusName1, strStatusValue[0],
								strStatusName2, strStatusValue[1], "HH:mm:ss");
				strTimeUpdateSystem = strUpdateResult[1];
				strFuncResult = strUpdateResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}



			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statTypeName,strStatusValue[0], "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration "
						+ strDurationDiffPDF);

				// Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "
						+ strReportGenrtTimePDF);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, false, statTypeName,strStatusValue[0], "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];

				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time " + strReportGenrtTime);

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
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("PDF generation duration " + strDurationDiff);

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "", strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1 = new File(strCSVDownlPath);
			File Cf1 = new File(strPDFDownlPath);

			if (Pf1.exists() && Cf1.exists()) {
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";

					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath,
							"Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath,
							"Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63394";
			gstrTO = "Update the status of a multi private status type"
					+ " pMST added at the resource level for a resource RS"
					+ ". Verify that a user with �Run Report� and 'View Resource'"
					+ " rights on RS and without any role can view the status of "
					+ "pMST for RS in the generated status detail report.";
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
	
	
	/****************************************************************************************
	'Description	:Update status of a numeric status type NST added at the resource type 
	'				level for a resource RS. Verify that a user with �Run Report� and 'View 
	'				Resource' rights on RS and with a role with view right for NST can view 
	'				the status of NST in the generated status detail report.
	'Precondition	:1. Status Type NST(numeric status type) is created.
	'				 2. Resource type RT is associated with status type NST.
	'				 3. Resources RS is created under RT.
	'				 4. User U1 has following rights:
		'				  a. Report - Status Detail.
		'			 	  b. Role to View status type NST.
		'				  c.'View Status' and 'Run Report' rights on resources RS.
	'				 5. Status Type NST is updated with some value on day D1. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:10-July-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS42729() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral=new General();
		
		try {
			gstrTCID = "BQS-42729"; // Test Case Id
			gstrTO = "Update status of a numeric status type NST added at the "
					+ "resource type level for a resource RS. Verify that a user"
					+ " with �Run Report� and 'View Resource' rights on RS and "
					+ "with a role with view right for NST can view the status "
					+ "of NST in the generated status detail report.";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
		
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails=objAP.ReadAutoit_FilePath();		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strApplTime="";
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
			
			String statTypeName="NST"+strTimeText;
			String strStatusTypeValue = "Number";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
		
			String strSTvalue[]=new String[1];
			String strRSValue[]=new String[1];
			
			//Application time
			String strUpdatdVale="";
			String strUpdatedDate="";
			String strReportGenrtTime="";
			String strReportGenrtTimePDF="";
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserNameA = "AutoUsr_A" + System.currentTimeMillis();
			String strUserNameB = "AutoUsr_B" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			String strUsrFulNameB = strUserNameB;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			//System Time
			String strTimeGenerateSystem="";
			String strTimeUpdateSystem="";
			
			String strTimePDFGenerateSystem="";
			String strDurationDiffPDF="";
			
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			
		
			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");
			
			
			String[] strTestDataCSV1= null;
			String[] strTestDataPDF1 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
				
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //Create status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//1. Status Type NST(numeric status type) is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, true);

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
			

			//2. Resource type RT is associated with status type ST1. 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//3. Resources RS is created under RT. 
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role to View status type NST.
			 * c.'View Resource' and 'Run Report' rights on resources RS.
			 */
			
			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameB,
								strInitPwd, strConfirmPwd, strUsrFulNameB);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameB, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Update user
			
			// Create User A

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}


			
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
				

			/* 6.Status Type NST is updated with some value on day D1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;
				strFuncResult = objLogin.newUsrLogin(seleniumPrecondition, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						seleniumPrecondition, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			//Update Status
			try {
				assertEquals("", strFuncResult);

				String strReportResult[] = objViewMap.updateStatusTypeWithTime(
						seleniumPrecondition, strResource, strSTvalue, "HH:mm:ss");
				strTimeUpdateSystem = strReportResult[1];
				strFuncResult = strReportResult[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = seleniumPrecondition
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = seleniumPrecondition.getText("//div/span" + "[text()='"
						+ strUpdatdVale + "']/parent::"
						+ "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}



			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameB,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Generate PDF File and fetch time
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");
				
				//Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);
				
				
				String strPdfGeneratd[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strCSTApplTime,
								strCSTApplTime, true, statTypeName, "HH:mm:ss");

				strFuncResult = strPdfGeneratd[0];
				strTimePDFGenerateSystem = strPdfGeneratd[1];

				int intDurationDiff = dts.getTimeDiff1(
						strTimePDFGenerateSystem, strTimeUpdateSystem);

				double fltDurationDiff = (double) intDurationDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				strDurationDiffPDF = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiffPDF);

				//Application Time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTimePDF = strStatusTime[2];
				System.out.println("PDF generation time "+strReportGenrtTimePDF);
				
				strTestDataPDF1 = new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strUpdatedDate,
						strCurrDate + " " + strReportGenrtTimePDF,
						strDurationDiffPDF,
						strUserNameA,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};


			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Generate report along with time
			try {
				assertEquals("", strFuncResult);
				String strCSTApplTime=dts.getTimeOfParticularTimeZone("CST", "M/d/yyyy");

				String strReportResult[] = objRep
						.enterReportSumDetailAndGenReportWitTime(selenium,
								strRSValue[0], strSTvalue[0], strCSTApplTime,
								strCSTApplTime, false, statTypeName, "HH:mm:ss");
				strTimeGenerateSystem = strReportResult[1];
				
				strFuncResult = strReportResult[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];
				System.out.println("CSV generation time "+strReportGenrtTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt<180);
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strCurrDate=dts.getCurrentDate("dd-MMM-yyyy");
				String strDurationUpdat =strUpdatedDate; 
				String strDurationGenerat =strReportGenrtTime;
				
				int intDurationDiff = dts.getTimeDiff1(strTimeGenerateSystem,
						strTimeUpdateSystem);
				
				double fltDurationDiff=(double)intDurationDiff/3600;
				
				double dRounded=dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out.println("PDF generation duration " + strDurationDiff);
			
				strTestDataCSV1 =new String[] { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff,
						strUserNameA,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1=new File(strCSVDownlPath);
			File Cf1=new File(strPDFDownlPath);
		
			if(Pf1.exists()&&Cf1.exists()){
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";
					
					
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath, "Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath, "Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}

			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-42729";
			gstrTO = "Update status of a numeric status type NST added at the "
					+ "resource type level for a resource RS. Verify that a user"
					+ " with �Run Report� and 'View Resource' rights on RS and "
					+ "with a role with view right for NST can view the status "
					+ "of NST in the generated status detail report.";
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
	
	
	/*******************************************************************************************
	'Description	:Verify that resource is not listed in the 'Step Detail Report (Step 2 of 2)
	'				 screen, if the status type selected in the previous screen is refined for 
	'				that particular resource.
	'Precondition	:1. Role-based Status Types NST1(number), MST1(multi), TST1(text) and SST1(saturation score) are created with a role R1 to view and update these status types.
	'				 2. Resource type RT is associated with status types NST1.
	'			     3. Resources RS1 and RS2 are created under resource type RT.
	'				 4. Status types MST1, TST1 and SST1 are associated with resource RS1 and RS2
	'				 at the resource level.
	'				 5. User U1 has following rights:
	'				   a. Report - Status Detail.
	'				   b. Role R1.
	'				   c.'Run Report' right on resources RS1 and RS2.
	'Arguments		:None
	'Returns		:None
	'Date	 		:12-July-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS63368() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		General objGeneral=new General();

		try {
			gstrTCID = "63368"; // Test Case Id
			gstrTO = "Verify that resource is not listed in the 'Step Detail Report"
					+ " (Step 2 of 2) screen, if the status type selected in the "
					+ "previous screen is refined for that particular resource.";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			

			String strFILE_PATH = pathProps.getProperty("TestData_path");

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
		
			String strApplTime="";
			//Admin Login credentials
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			
			//Region
			String strRegn=rdExcel.readData("Login",3, 4);
				
			String statNumTypeName="NST"+strTimeText;
			String statTextTypeName="TST"+strTimeText;
			String statMultiTypeName="MST"+strTimeText;
			String statSaturatTypeName="SST"+strTimeText;
			
			String strNSTValue = "Number";
			String strMSTValue = "Multi";
			String strTSTValue = "Text";
			String strSSTValue = "Saturation Score";
			String strStatTypDefn = "Automation";
			
			String strStatTypeColor="Black";
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			
			//General variable 
			String strStatValue = "";
		
			String strSTvalue[]=new String[4];
			String strRSValue[]=new String[2];
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource_1 ="AutoRs_1" + strTimeText;
			String strResource_2 ="AutoRs_2" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			
			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUserNameAny = "AutoUsr_any" + System.currentTimeMillis();
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulName_1 = strUserName_1;
			String strUsrFulNameAny = strUserNameAny;
			
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template", 7,
					12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7, 13,
					strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7, 14,
					strFILE_PATH);
			
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Create All 4 status types
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//1. Status Type NST(numeric status type) is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strNSTValue, statNumTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statNumTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//1. Status Type Text status type is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strTSTValue, statTextTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statTextTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			//1. Status Type Saturation score status type is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strSSTValue, statSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statSaturatTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[2]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//1. Status Type Multi status type is created. 
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strMSTValue, statMultiTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[3]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName1, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName, strStatusName2, strMSTValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
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

			/*
			 * 1. Role-based Status Types NST1(number), MST1(multi), TST1(text)
			 * and SST1(saturation score) are created with a role R1 to view and
			 * update these status types.
			 */
			try {
				assertEquals("", strFuncResult);

				String[] strViewRightValue = strSTvalue;
				String[] updateRightValue = strSTvalue;
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strViewRightValue, true,
						updateRightValue, true, true);
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
			
			/* 2. Resource type RT is associated with status types NST1. */
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			 /*3. Resources RS1 and RS2 are created under resource type RT. */

			//3. Resources RS1 is created under RT. 
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource_1, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource_1);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[0] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//3. Resources RS2 is created under RT. 
			
			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource_2, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource_2);

				if (strResVal.compareTo("") != 0) {
					strFuncResult = "";
					strRSValue[1] = strResVal;
				} else {
					strFuncResult = "Failed to fetch Resource value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			 /* 4. Status types MST1, TST1 and SST1 are associated with resource
			 * RS1 at the resource level.*/
			  

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.savAndVerifyEditRSLevelPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 /* 4. Status types MST1, TST1 and SST1 are associated with resource
			 * RS2 at the resource level.*/
			  
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource_2);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[1], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[2], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.selDeselctOnlySTInEditRSLevelPage(
						seleniumPrecondition, strSTvalue[3], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRs.savAndVerifyEditRSLevelPage(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 5. User U1 has following rights:
			 * 
			 * a. Report - Status Detail. b. Role R1. c.'Run Report' right on
			 * resources RS1 and RS2.
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult =objCreateUsers.navUserListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserName_1,
								strInitPwd, strConfirmPwd, strUsrFulName_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
						seleniumPrecondition, strResource_1, strRSValue[0], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource_2, strRSValue[1], false, false,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

		
			//Any User
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameAny,
								strInitPwd, strConfirmPwd, strUsrFulNameAny);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.SetUpUsrAcnt");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
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
						seleniumPrecondition, strResource_1, strRSValue[0], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource_2, strRSValue[1], false, true,
						false, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameAny, strByRole,
						strByResourceType, strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			log4j.info("~~~~~PRE-CONDITION " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID
					+ " EXECUTION ENDS~~~~~");

			try {
				assertEquals("", strFuncResult);
				
				blnLogin=false;
				strFuncResult=objLogin.newUsrLogin(selenium, strUserNameAny, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2 Login as a user with 'Update Status' right on resources RS1 and
			 * RS2 and a role to update status types NST1, MST1, TST1 and SST1.
			 * 
			 * Update the statuses of NST1, MST1, TST1 and SST1 of resources RS1
			 * and RS2 from View>>Map screen. Updated status values are
			 * displayed appropriately in the resource pop up window of RS1 and
			 * RS2 on Map screen.
			 */	
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName,
						statNumTypeName, statSaturatTypeName, statTextTypeName, };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource_1, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			// Update Number Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.updateNumStatusType(selenium, strResource_1,
								statNumTypeName, strSTvalue[0], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			// Update Text Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.updateNumStatusType(selenium, strResource_1,
								statTextTypeName, strSTvalue[1], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			
			// Update Saturation Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objStatusTypes.updateSatuScoreStatusType(
						selenium, strResource_1, statSaturatTypeName,
						strSTvalue[2], strUpdateValue1, strUpdateValue2, "393",
						"429");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			// Update Multi Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.updateMultiStatusType_ChangeVal(
						selenium, strResource_1, statMultiTypeName,
						strSTvalue[3], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statNumTypeName, statTextTypeName,
						statMultiTypeName, statSaturatTypeName };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource_2, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}	
			
			
			// Update Number Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.updateNumStatusType(selenium, strResource_2,
								statNumTypeName, strSTvalue[0], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			// Update Text Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap
						.updateNumStatusType(selenium, strResource_2,
								statTextTypeName, strSTvalue[1], "1", "2");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			
			
			// Update Saturation Status
			try {
				assertEquals("", strFuncResult);

				String[] strUpdateValue1 = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8" };
				String[] strUpdateValue2 = { "1", "2", "3", "4", "5", "6", "7",
						"8", "9" };
				strFuncResult = objStatusTypes.updateSatuScoreStatusType(
						selenium, strResource_2, statSaturatTypeName,
						strSTvalue[2], strUpdateValue1, strUpdateValue2, "393",
						"429");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			// Update Multi Status
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.updateMultiStatusType_ChangeVal(
						selenium, strResource_2, statMultiTypeName,
						strSTvalue[3], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1]);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
		
			/*
			 * 3 Login as a user with 'Setup- User Accounts' right, edit user U1
			 * and refine status types NST1, MST1, TST1 and SST1 for only
			 * resource RS1 and save the user. User is returned to Users List
			 * screen.
			 */ 
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objCreateUsers.navUserListPge(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}	
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navEditUserPge(selenium,
						strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navToRefineVisibleST(selenium, strResource_1);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[0], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[1], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[2], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.selAndDeselSTInRefineVisibleST(
						selenium, strSTvalue[3], false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.saveChangesInRefineSTAndVerifyEditUser(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 4 Login as User U1, navigate to Reports >> Status Reports, click
			 * on 'Status Detail'. 'Status Detail Report (Step 1 of 2)' screen
			 * is displayed.
			 * 
			 * Adobe Acrobat (PDF) is selected by default.
			 * 
			 * Status types NST1, MST1, TST1 and SST1 are listed in the 'Status Type' field. 
			 */	
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				
				strFuncResult=objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			/*
			 * 4 Login as User U1, navigate to Reports >> Status Reports, click
			 * on 'Status Detail'. 'Status Detail Report (Step 1 of 2)' screen
			 * is displayed.
			 * 
			 * Adobe Acrobat (PDF) is selected by default.
			 * 
			 * Status types NST1, MST1, TST1 and SST1 are listed in the 'Status
			 * Type' field.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportSTSumDetailDate(selenium,
						strApplTime, strApplTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "//select[@name='statusTypeID']/option[text()='"
						+ statNumTypeName + "']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Status types " + statNumTypeName
						+ " is listed in the 'Status Type' field. ");


				String strElementID = "//select[@name='statusTypeID']/option[text()='"
						+ statTextTypeName + "']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = "Status types " + statNumTypeName
						+ " is NOT listed in the 'Status Type' field. ";
				log4j.info("Status types " + statNumTypeName
						+ " is  NOT listed in the 'Status Type' field. ");

			}

			try {
				assertEquals("", strFuncResult);
				log4j.info("Status types " + statTextTypeName
						+ " is listed in the 'Status Type' field. ");

				String strElementID = "//select[@name='statusTypeID']/option[text()='"
						+ statMultiTypeName + "']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = "Status types " + statTextTypeName
						+ " is NOT listed in the 'Status Type' field. ";
				log4j.info("Status types " + statTextTypeName
						+ " is NOT listed in the 'Status Type' field. ");

			}
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status types " + statMultiTypeName
						+ " is listed in the 'Status Type' field. ");

				String strElementID = "//select[@name='statusTypeID']/option[text()='"
						+ statSaturatTypeName + "']";

				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = "Status types " + statMultiTypeName
						+ " is NOT listed  in the 'Status Type' field. ";
				log4j.info("Status types " + statMultiTypeName
						+ " is NOT listed in the 'Status Type' field. ");

			}
			
			
			try {
				assertEquals("", strFuncResult);
				log4j.info("Status types " + statSaturatTypeName
						+ " is listed in the 'Status Type' field. ");


			} catch (AssertionError Ae) {
				gstrReason = "Status types " + statSaturatTypeName
						+ " is NOT listed in the 'Status Type' field. ";
				log4j.info("Status types " + statSaturatTypeName
						+ " is NOT listed in the 'Status Type' field. ");

			}
			

			/*
			 * 5 Enter valid dates, select NST1 and click on Next. Resource RS1
			 * is not listed for 'Resources' field in the 'Step Detail Report
			 * (Step 2 of 2) screen.
			 * 
			 * Only RS2 is listed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportSTSumDetailDate(selenium,
						strApplTime, strApplTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportSTSumDetailSTAndNavigate(
						selenium, statNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("Resource "+strResource_1+" is not listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

				String strElementID = "css=input[value='" + strRSValue[1]
						+ "']";
				
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult + "Resource "+strResource_1+" is listed "
						+ "for 'Resources' field in the"
						+ " 'Step Detail Report (Step 2 of 2) screen. ";
				log4j
						.info("Resource "+strResource_1+" is listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

			}

			/*
			 * 6 Navigate to Reports >> Status Reports, click on 'Status
			 * Detail', enter valid dates, select MST1 and click on Next.
			 * Resource RS1 is not listed for 'Resources' field in the 'Step
			 * Detail Report (Step 2 of 2) screen.
			 * 
			 * Only RS2 is listed.
			 */
			try {
				assertEquals("", strFuncResult);
				log4j.info(" Only "+strResource_2+" is listed.");

				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult+"Only "+strResource_2+" is listed.";
				log4j.info(""+strResource_2+" is NOT listed.");

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportSTSumDetailDate(selenium,
						strApplTime, strApplTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportSTSumDetailSTAndNavigate(
						selenium, statTextTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("Resource "+strResource_1+" is not listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

				String strElementID = "css=input[value='" + strRSValue[1]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult + "Resource "+strResource_1+" is listed "
						+ "for 'Resources' field in the"
						+ " 'Step Detail Report (Step 2 of 2) screen. ";
				log4j
						.info("Resource "+strResource_1+" is listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

			}

			
			
			/*
			 * 7 Navigate to Reports >> Status Reports, click on 'Status
			 * Detail', enter valid dates, select TST1 and click on Next.
			 * Resource RS1 is not listed for 'Resources' field in the 'Step
			 * Detail Report (Step 2 of 2) screen.
			 * 
			 * Only RS2 is listed.
			 */
			try {
				assertEquals("", strFuncResult);
				log4j.info(" Only "+strResource_2+" is listed.");

				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult+"Only "+strResource_2+" is listed.";
				log4j.info(""+strResource_2+" is NOT listed.");

			}


			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportSTSumDetailDate(selenium,
						strApplTime, strApplTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportSTSumDetailSTAndNavigate(
						selenium, statSaturatTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("Resource "+strResource_1+" is not listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

				String strElementID = "css=input[value='" + strRSValue[1]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult + "Resource "+strResource_1+" is listed "
						+ "for 'Resources' field in the"
						+ " 'Step Detail Report (Step 2 of 2) screen. ";
				log4j
						.info("Resource "+strResource_1+" is listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

			}

			
			/*
			 * 8 Navigate to Reports >> Status Reports, click on 'Status
			 * Detail', enter valid dates, select SST1 and click on Next.
			 * Resource RS1 is not listed for 'Resources' field in the 'Step
			 * Detail Report (Step 2 of 2) screen.
			 * 
			 * Only RS2 is listed.
			 */
			try {
				assertEquals("", strFuncResult);
				log4j.info(" Only "+strResource_2+" is listed.");

				strFuncResult = objRep.navToStatusReports(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult+"Only "+strResource_2+" is listed.";
				log4j.info(""+strResource_2+" is NOT listed.");

			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportSTSumDetailDate(selenium,
						strApplTime, strApplTime);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportSTSumDetailSTAndNavigate(
						selenium, statMultiTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strElementID = "css=input[value='" + strRSValue[0]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("Element NOT Present", strFuncResult);
				log4j
						.info("Resource "+strResource_1+" is not listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

				String strElementID = "css=input[value='" + strRSValue[1]
						+ "']";
				strFuncResult = objGeneral.checkForAnElements(selenium,
						strElementID);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult + "Resource "+strResource_1+" is listed "
						+ "for 'Resources' field in the"
						+ " 'Step Detail Report (Step 2 of 2) screen. ";
				log4j
						.info("Resource "+strResource_1+" is listed for 'Resources' field in the"
								+ " 'Step Detail Report (Step 2 of 2) screen. ");

			}

			
			try {
				assertEquals("", strFuncResult);
				log4j.info(" Only "+strResource_2+" is listed.");

		
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult+"Only "+strResource_2+" is listed.";
				log4j.info(""+strResource_2+" is NOT listed.");

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
			gstrTCID = "63368";
			gstrTO = "Verify that resource is not listed in the 'Step Detail Report"
					+ " (Step 2 of 2) screen, if the status type selected in the "
					+ "previous screen is refined for that particular resource.";
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
	
	/****************************************************************************************
	'Description	:Add a multi status type MST back to resource RS and update the status value of MST for RS, generate the �Status Detail Report� and verify that the data is displayed appropriately in the report
	'Precondition	:1. Resource type RT1 is associated with status type ST1 and NOT with
	'				 status type NST (number)
	'				 2. Resources RS1 is created under RT1.
	'				 3. Status Type NST is added for resource RS1 at the resource level
	'				 4. User U1 has following rights:
					'Report - Status Detail'
					Role to update status type NST
					'Update Status' and 'Run Report' rights on resources RS1 
	'Arguments		:None
	'Returns		:None
	'Date	 		:27-June-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	
	@Test
	public void testBQS63347() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();

		
		try {
			gstrTCID = "BQS-63347"; // Test Case Id
			gstrTO = "Add a multi status type MST back to resource RS and update"
					+ " the status value of MST for RS, generate the �Status Detail"
					+ " Report� and verify that the data is displayed appropriately"
					+ " in the report";

			// Objective
			gstrReason = "";
			gstrResult = "FAIL";
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");

			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			
			String strFILE_PATH = pathProps.getProperty("TestData_path");

			propElementAutoItDetails=objAP.ReadAutoit_FilePath();		
			String strTimeText=dts.getCurrentDate("MMddyyyy_HHmmss");
			
			String strApplTime = dts.getTimeOfParticularTimeZone("CST",
			"M/d/yyyy");
			// login details
			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn=rdExcel.readData("Login",3, 4);
		
			String statMultiTypeName1="MST_1"+strTimeText;
			String statMultiTypeName2="MST_2"+strTimeText;
			
			String strStatusTypeValue = "Multi";
			String strStatTypDefn = "Automation";
			String strStatValue = "";
		
			String strSTvalue[]=new String[2];
			String strRSValue[]=new String[1];
			
			String strUpdatdVale="";
			String strUpdatdVale_1="";
			
			String strStatTypeColor="Black";
			String strStatusName1="Sta"+strTimeText;
			String strStatusName2="Stb"+strTimeText;
			
			String strStatusValue[]=new String[2];
			strStatusValue[0]="";
			strStatusValue[1]="";
			
			String strStatusName1_2="Sta"+strTimeText;
			String strStatusName2_2="Stb"+strTimeText;
			String strStatusValue_2[]=new String[2];
			strStatusValue_2[0]="";
			strStatusValue_2[1]="";
			
			//System Time
			String strUpdatdValeSystem_1="";
			String strUpdatdGenrtdValeSystem_1="";
			
			String strUpdatdValeSystem_2="";
			String strUpdatdGenrtdValeSystem_2="";
			
			//Application time
			String strUpdatedDate="";
			String strUpdatedDate_1="";
			
			String strReportGenrtTime="";
			String strReportGenrtTime_1="";
			
			String strResrctTypName = "AutoRt_" + strTimeText;

			String strResource = "AutoRs_" + strTimeText;
			String strAbbrv = "AB";
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";
			
			String strUserNameA = "AutoUsr_A" +strTimeText;
			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);
			String strUsrFulNameA = strUserNameA;
			
			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);
		
		
			String strCurrDate=dts.getCurrentDate("MM/dd/yyyy");
		
			String strRoleName = "AutoR_" + strTimeText;
			String strRoleRights[][] = {};
			String strRoleValue = "";

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strCSVDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".csv";
			String strPDFDownlPath = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatSummary_" + gstrTCID + "_" + strTimeText + ".pdf";
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String[] strTestDataCSV1 = null;
			String[] strTestDataCSV2 = null;
			String[] strTestDataPDF1 = null;
			String[] strTestDataPDF2 = null;

			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
				
			
			// admin login
			strFuncResult=objLogin.login(seleniumPrecondition, strLoginUserName, strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			 //Create Number status type

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statMultiTypeName1,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[0]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName1, strStatusName1, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName1, strStatusName2, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName1, strStatusName1);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName1, strStatusName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			// Navigate to Role list
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {

				gstrReason = strFuncResult;

			}
		
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.CreateRoleWithAllFields(seleniumPrecondition,
						strRoleName, strRoleRights, strSTvalue, false,
						strSTvalue, false, false);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String[][] strSTViewValue = { { strSTvalue[0], "true" } };
				String[][] strSTUpdateValue = { { strSTvalue[0], "true" } };
				strFuncResult = objRole.slectAndDeselectSTInCreateRole(
						seleniumPrecondition, false, false, strSTViewValue,
						strSTUpdateValue, true);

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
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, strStatusTypeValue, statMultiTypeName2,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchSTValueInStatTypeList(seleniumPrecondition,
						statMultiTypeName2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strSTvalue[1]=strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName2, strStatusName1_2, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statMultiTypeName2, strStatusName2_2, strStatusTypeValue,
						strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName2, strStatusName1_2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue_2[0] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatValue = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statMultiTypeName2, strStatusName2_2);
				if (strStatValue.compareTo("") != 0) {
					strFuncResult = "";
					strStatusValue_2[1] = strStatValue;
				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//2. Resource type RT is associated with status type ST1. 
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				
				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[1] + "']");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//3. Resources RS is created under RT. 
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(seleniumPrecondition,
						strResource, strAbbrv, strResrctTypName, strContFName,
						strContLName, strState, strCountry, strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs.fetchResValueInResList(seleniumPrecondition, strResource);

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
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(seleniumPrecondition,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(seleniumPrecondition,
						strSTvalue[0], true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * Precondition 4. User U1 has following rights:
			 * 
			 * 'Report - Status Detail' Role to update status type NST 'Update
			 * Status' and 'Run Report' rights on resources RS1
			 */
			
			// Create User B

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers
						.fillUsrMandatoryFlds(seleniumPrecondition, strUserNameA,
								strInitPwd, strConfirmPwd, strUsrFulNameA);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserNameA, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~PRE-CONDITION - " + gstrTCID
					+ " EXECUTION ENDS~~~~~");
			
			/*
			 * 2 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~TEST CASE - " + gstrTCID
					+ " EXECUTION STARTS~~~~~");
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.newUsrLogin(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName1 };
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			try {
				assertEquals("", strFuncResult);

				String strUpdateST[] = objViewMap.updateMultiStatusTypeWithTime(
						selenium, strResource, statMultiTypeName1,
						strSTvalue[0], strStatusName1, strStatusValue[0],
						strStatusName2, strStatusValue[1], "HH:mm:ss");

				strFuncResult = strUpdateST[0];

				strUpdatdValeSystem_1 = strUpdateST[1];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}


			try {
				assertEquals("", strFuncResult);

				strUpdatdVale_1 = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate_1 = selenium.getText("//div/span" + "[text()='"
						+ strUpdatdVale_1 + "']/parent::"
						+ "div/span[@class='time']");

				strUpdatedDate_1 = strUpdatedDate_1.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate_1.split(" ");
				strUpdatedDate_1 = strLastUpdArr[2];
				log4j.info(strUpdatedDate_1);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
	
			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
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

				strFuncResult = objLogin.login(selenium, strLoginUserName,
						strLoginPassword);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				blnLogin = false;

				strFuncResult = objLogin.navUserDefaultRgn(selenium, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 3 Navigate to Setup >> Resources 'Resource List' screen is
			 * displayed.
			 */ 
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navResourcesList(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 4 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */ 
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 5 Deselect the status type NST and click on 'Save'. 'Resource
			 * List' screen is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				String strResult[] = objRs
						.selDeselctSTInEditRSLevelPageWithTime(selenium,
								strSTvalue[0], false, "HH:mm:ss");
				strUpdatdGenrtdValeSystem_1 = strResult[1];
				strFuncResult = strResult[0];
				// Fetch System time
				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime_1 = strStatusTime[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Click on link 'Edit Status Types' associated with resource RS1.
			 * 'Edit Resource-Level Status Types' screen is displayed.
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.navToEditResLevelSTPage(selenium,
						strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 7 Select back status type 'NST' and click on Save 'Resource List'
			 * screen is displayed.
			 */ 
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objRs.selDeselctSTInEditRSLevelPage(selenium,
						strSTvalue[0], true);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult=objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			/*
			 * 8 Login as user U1, update the status of NST on day D1 for
			 * resource RS1. No Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				
				strFuncResult=objLogin.login(selenium, strUserNameA, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=true;
			
				String[] strEventStatType = {};
				String[] strRoleStatType = { statMultiTypeName1};
				strFuncResult = objViewMap.verifyStatTypesInResourcePopup(
						selenium, strResource, strEventStatType,
						strRoleStatType, false, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}
			
			
			try {
				assertEquals("", strFuncResult);

				String strUpdateST[] = objViewMap
						.updateMultiStatusTypeWithTime(selenium, strResource,
								statMultiTypeName1, strSTvalue[0],
								strStatusName1, strStatusValue[0],
								strStatusName2, strStatusValue[1], "HH:mm:ss");
				strUpdatdValeSystem_2 = strUpdateST[1];
				strFuncResult = strUpdateST[0];
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;

			}

			try {
				assertEquals("", strFuncResult);

				strUpdatdVale = selenium
						.getText("css=div.emsText.maxheight > span");

				strUpdatedDate = selenium.getText("//div/span"
						+ "[text()='"+strUpdatdVale+"']/parent::" + "div/span[@class='time']");
				strUpdatedDate = strUpdatedDate.substring(1, 13);

				String strLastUpdArr[] = strUpdatedDate.split(" ");
				strUpdatedDate = strLastUpdArr[2];

				strFuncResult = objLogin.logout(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.login(selenium, strUserNameA,
						strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			 * 9 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * 'Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dropdown
			 * with status types (ST1, NST)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statMultiTypeName2 + "']"));
					log4j.info("Status Types dropdown with status types (ST1 ="
							+ statMultiTypeName2 + ") ");
				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (ST1 ="
									+ statMultiTypeName2 + ") ");
					strFuncResult="Status Types dropdown NOT with status types (NST ="
						+ statMultiTypeName2 + ")";
				}

				try {
					assertTrue(selenium
							.isElementPresent("//div[@id='mainContainer']"
									+ "/form/table/tbody/tr[4]/td[2]/select/"
									+ "option[text()='" + statMultiTypeName1 + "']"));
					log4j.info("Status Types dropdown with status types (NST ="
							+ statMultiTypeName1 + ") ");
					
				} catch (AssertionError Ae) {
					log4j
							.info("Status Types dropdown NOT with status types (NST ="
									+ statMultiTypeName1 + ") ");
					strFuncResult="Status Types dropdown NOT with status types (NST ="
									+ statMultiTypeName1 + ")";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*
			10 	Provide D1 as the date for 'Start Date' and 'End Date' fields, select NST and click on Next 		'Status Detail Report (Step 2 of 2)' screen is displayed with:

				1. Status type NST
				2. Resources RS1.
				11 	Select RS1 and click on 'Generate Report' 		Status Detail Report is generated in the PDF format.

				Header and Footer is with following details.

				Header:
				1. Start Date: dd-mm-yyyy
				2. End Date: dd-mm-yyyy
				3. Status Type

				Footer:
				1. Report Run By: (name of the user)
				2. From: (Name of the Region)
				3. On: MM/DD/YYYY HH:MM:SS (Time Zone)
				4. Intermedix Emsystems logo
				5. Page number

				Details of resource RS1 are displayed appropriately with following columns:

				1. Status Value
				2. Status Start Date
				3. Status End Date
				4. Duration (Hrs)
				5. User
				6. IP
				7. Trace
				8. Comments */
			
			
			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
						.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
								strRSValue[0], strSTvalue[0], strApplTime,
								strApplTime, true, statMultiTypeName1, strStatusValue[0],"HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);

				strTestDataPDF1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale_1,
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};

				strTestDataPDF2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strPDFDownlPath,
						"Status Detail Report need to be checked in PDF file"

				};
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			try {
				assertEquals("", strFuncResult);
				// Thread.sleep(20000);
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strPDFDownlPath };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 12 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * No Expected Result
			 */
			/*
			 * 13 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select NST and click on Next No
			 * Expected Result
			 */
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 14 Select RS1 and click on 'Generate Report' Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date 4. End Date 5. Duration 6.
			 * User 7. IP 8. Trace 9. Comments
			 */
			try {
				assertEquals("", strFuncResult);

				String strReport[] = objRep
				.enterReportSumDetailAndGenReportWitTimeofMST(selenium,
						strRSValue[0], strSTvalue[0], strApplTime,
						strApplTime, false, statMultiTypeName1, strStatusValue[0],"HH:mm:ss");

				strUpdatdGenrtdValeSystem_2 = strReport[1];

				strFuncResult = strReport[0];

				String StatusTime = selenium.getText("css=#statusTime");

				String strStatusTime[] = StatusTime.split(" ");
				strReportGenrtTime = strStatusTime[2];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				//Thread.sleep(20000);
				String strProcess="";				
				String strArgs[]={strAutoFilePath,strCSVDownlPath};
				
				//AutoIt
				Runtime.getRuntime().exec(strArgs);
				
				int intCnt=0; 
				do{
				   GetProcessList objGPL = new GetProcessList();
				   strProcess = objGPL.GetProcessName();
				   intCnt++;
				   Thread.sleep(500);
				 } while (strProcess.contains(strAutoFileName)&&intCnt<180);
						 	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
	
			
			try {
				assertEquals("", strFuncResult);
				strCurrDate = dts.getCurrentDate("dd-MMM-yyyy");

				String strDurationUpdat = strUpdatedDate;
				String strDurationGenerat = strReportGenrtTime;

				// Updating
				int intDurationDiff_1 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_1, strUpdatdValeSystem_1);

				int intDurationDiff_2 = dts.getTimeDiff1(
						strUpdatdGenrtdValeSystem_2, strUpdatdValeSystem_2);

				double fltDurationDiff_1 = (double) intDurationDiff_1 / 3600;
				double fltDurationDiff_2 = (double) intDurationDiff_2 / 3600;

				double dRounded_1 = dts.roundTwoDecimals(fltDurationDiff_1);
				String strDurationDiff_1 = Double.toString(dRounded_1);

				double dRounded_2 = dts.roundTwoDecimals(fltDurationDiff_2);
				String strDurationDiff_2 = Double.toString(dRounded_2);
			

				strTestDataCSV1 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale_1,
						strCurrDate + " " + strUpdatedDate_1,
						strCurrDate + " " + strReportGenrtTime_1,
						strDurationDiff_1, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

				strTestDataCSV2 = new String[] {
						propEnvDetails.getProperty("Build"), gstrTCID,
						strResource, strUpdatdVale,
						strCurrDate + " " + strDurationUpdat,
						strCurrDate + " " + strDurationGenerat,
						strDurationDiff_2, strUserNameA, propEnvDetails.getProperty("ExternalIP"), "",
						strCSVDownlPath,
						"Status Detail Report need to be checked in CSV file"

				};

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			File Pf1=new File(strCSVDownlPath);
			File Cf1=new File(strPDFDownlPath);
		
			if(Pf1.exists()&&Cf1.exists()){
				try {
					assertEquals("", strFuncResult);
					gstrResult = "PASS";
					
					
					String strWriteFilePath = pathProps
							.getProperty("WriteResultPath");
					objOFC.writeResultData(strTestDataPDF1, strWriteFilePath, "Status_Detail_Report");
					objOFC.writeResultData(strTestDataPDF2, strWriteFilePath, "Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV1, strWriteFilePath, "Status_Detail_Report");
					objOFC.writeResultData(strTestDataCSV2, strWriteFilePath, "Status_Detail_Report");
				} catch (AssertionError Ae) {
					gstrReason = strFuncResult;
				}
			}
					
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-63347";
			gstrTO = "Add a multi status type MST back to resource RS "
					+ "and update the status value of MST for RS, generate "
					+ "the �Status Detail Report� and verify that the data "
					+ "is displayed appropriately in the report";
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
	
	

	
	/****************************************************************************************
	'Description	:Generate 'Status Detail report' in CSV format and verify that the report displays correct data.
	'Precondition	:1.Role based,shared and private status types NST,MST,sSST,sTST,pMST and pSST are created.
						(Multi status types created with statuses)
						2.Resource type RT is associated with all the status types.
						3. Resources RS is created with address under RT.
						4. User U1 has following rights:
						Role to view and update all status types.
						'Update Status' and 'Run Report' rights on resource RS. 
	'Arguments		:None
	'Returns		:None
	'Date	 		:28-Nov-2012
	'Author			:QSG
	'---------------------------------------------------------------------------------------
	'Modified Date                            Modified By
	'<Date>                                     <Name>
	****************************************************************************************/
	
	@Test
	public void testBQS103963() throws Exception {
		
		String strFuncResult="";
		boolean blnLogin=false;
		
		Paths_Properties objAP = new Paths_Properties();
		Properties pathProps = objAP.Read_FilePath();
		Login objLogin = new Login();// object of class Login
		ViewMap objViewMap=new ViewMap();
		Reports objRep=new Reports();
		StatusTypes objStatusTypes=new StatusTypes();
		ResourceTypes objRT = new ResourceTypes();
		Resources objRs = new Resources();
		CreateUsers objCreateUsers = new CreateUsers();
		Roles objRole = new Roles();
		OfficeCommonFunctions objOFC = new OfficeCommonFunctions();
		General objGeneral=new General();
		Views objViews=new Views();
		
		try {
			gstrTCID = "BQS-103963"; 
			gstrTO = "Generate 'Status Detail report' in CSV format and verify that the report displays correct data.";
			gstrReason = "";
			gstrResult = "FAIL";
			
	
			String strFILE_PATH = pathProps.getProperty("TestData_path");
			gstrTimeOut = propEnvDetails.getProperty("TimeOut");
			Date_Time_settings dts = new Date_Time_settings();
			gstrTimetake = dts.timeNow("HH:mm:ss");
			String strTimeText = dts.getCurrentDate("MMddyyyyhhmmss");
			String strTmText = dts.getCurrentDate("HHmm");

			String strLoginUserName = rdExcel.readData("Login", 3, 1);
			String strLoginPassword = rdExcel.readData("Login", 3, 2);
			String strRegn = rdExcel.readData("Login", 3, 4);

			// Search user criteria
			String strByRole = rdExcel.readInfoExcel("User_Template", 7, 11,
					strFILE_PATH);
			String strByResourceType = rdExcel.readInfoExcel("User_Template",
					7, 12, strFILE_PATH);
			String strByUserInfo = rdExcel.readInfoExcel("User_Template", 7,
					13, strFILE_PATH);
			String strNameFormat = rdExcel.readInfoExcel("User_Template", 7,
					14, strFILE_PATH);

			String strUserName_1 = "AutoUsr_1" + System.currentTimeMillis();
			String strUsrFulName_1 = strUserName_1;

			String strInitPwd = rdExcel.readData("Login", 4, 2);
			String strConfirmPwd = rdExcel.readData("Login", 4, 2);

			//Role based
			String statrNumTypeName = "rNST" + strTimeText;
			String statrMultiTypeName = "rMST" + strTimeText;
			
			//Shared 
			String statsSaturatTypeName = "sSST" + strTimeText;
			String statsTextTypeName = "sTST" + strTimeText;
			
			//Private
			String statpMultiTypeName = "pMST" + strTimeText;
			String statpSaturatTypeName = "pSST" + strTimeText;
			
			
			
			String strStatTypDefn = "Automation";
			String strStatTypeColor = "Black";
			String stNSTValue = "Number";
			String stMSTValue = "Multi";
			String stTSTValue = "Text";
			String stSSTValue = "Saturation Score";
			
			String strStatusName1 = "rSa" + strTimeText;
			String strStatusName2 = "rSb" + strTimeText;
			String strStatusValue[] = new String[2];
			strStatusValue[0] = "";
			strStatusValue[1] = "";
			
			String stpStatusName1 = "pSa" + strTimeText;
			String stpStatusName2 = "pSb" + strTimeText;
			String stpStatusValue[] = new String[2];
			stpStatusValue[0] = "";
			stpStatusValue[1] = "";

			String strSTvalue[] = new String[6];
			String strRSValue[] = new String[1];

			String strResrctTypName = "AutoRt" + strTimeText;
			String strRTValue = "";

			String strResource = "AutoRs" + strTimeText;
			String strAbbrv = "A" + strTmText;
			String strResVal = "";

			String strStandResType = "Aeromedical";
			String strContFName = "auto";
			String strContLName = "qsg";

			String strRolesName = "Role" + strTimeText;
			String strRoleValue = "";

			String strApplTime="";
			
			String strStatusUpdateValue1="101";
			String strStatusUpdateValue2=strStatusName2;
			String strStatusUpdateValue3="429";
			String strStatusUpdateValue4="Auto";
			String strStatusUpdateValue5=stpStatusName2;
			String strStatusUpdateValue6="393";
			
			
			String strStatusUpdateDate1="";
			String strStatusUpdateDate2="";
			String strStatusUpdateDate3="";
			String strStatusUpdateDate4="";
			String strStatusUpdateDate5="";
			String strStatusUpdateDate6="";
			
			String strStatusUpdateDateEST1="";
			String strStatusUpdateDateEST2="";
			String strStatusUpdateDateEST3="";
			String strStatusUpdateDateEST4="";
			//String strStatusUpdateDateEST5="";
			String strStatusUpdateDateEST6="";
			
			
			//String strESTimeUpdate="";	
			
			String strStatusGenerateDate="";
			
			String strStatusGenerateDateEST1="";
			String strStatusGenerateDateEST2="";
			String strStatusGenerateDateEST3="";
			String strStatusGenerateDateEST4="";
			String strStatusGenerateDateEST6="";
			
			

			String strAutoFilePath = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Path");
			String strAutoFileName = propElementAutoItDetails
					.getProperty("Reports_DownloadFile_Name");

			String strCSVDownlPath_1 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "1.csv";

			String strCSVDownlPath_2 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "2.csv";

			String strCSVDownlPath_3 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "3.csv";

			String strCSVDownlPath_4 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "4.csv";

			String strCSVDownlPath_5 = pathProps
					.getProperty("Reports_DownloadCSV_Path")
					+ "StatDetail_" + gstrTCID + "_" + strTimeText + "5.csv";

			log4j.info("~~~~~PRE CONDITION " + gstrTCID + " EXECUTION STARTS~~~~~");
			
			strFuncResult = objLogin.login(seleniumPrecondition, strLoginUserName,
					strLoginPassword);

			try {
				assertEquals("", strFuncResult);
				blnLogin = true;

				strFuncResult = objLogin.navUserDefaultRgn(seleniumPrecondition, strRegn);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*
			 * 1.Role based,shared and private status types
			 * NST,MST,sSST,sTST,pMST and pSST are created.
			 * 
			 * (Multi status types created with statuses)
			 */
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.navStatusTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			//Role based
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stNSTValue, statrNumTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[0] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrNumTypeName);

				if (strSTvalue[0].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stMSTValue, statrMultiTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[1] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statrMultiTypeName);

				if (strSTvalue[1].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, strStatusName1,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statrMultiTypeName, strStatusName2,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName1);
				if (strStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statrMultiTypeName, strStatusName2);
				if (strStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			//Shared
			
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stSSTValue, statsSaturatTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsSaturatTypeName, "SHARED", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[2] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsSaturatTypeName);

				if (strSTvalue[2].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selectStatusTypesAndFilMandFlds(
						seleniumPrecondition, stTSTValue, statsTextTypeName,
						strStatTypDefn, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.selectVisibiltyValue(seleniumPrecondition,
						statsTextTypeName, "SHARED", true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[3] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statsTextTypeName);

				if (strSTvalue[3].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			//Private
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, stMSTValue, statpMultiTypeName, strStatTypDefn,
						true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[4] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpMultiTypeName);

				if (strSTvalue[4].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, stpStatusName1,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objStatusTypes.createSTWithinMultiTypeST(
						seleniumPrecondition, statpMultiTypeName, stpStatusName2,
						stMSTValue, strStatTypeColor, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				stpStatusValue[0] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName, stpStatusName1);
				if (stpStatusValue[0].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				stpStatusValue[1] = objStatusTypes.fetchStatValInStatusList(
						seleniumPrecondition, statpMultiTypeName, stpStatusName2);
				if (stpStatusValue[1].compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch status value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objStatusTypes.selStatusTypesAndCreatePrivateST(
						seleniumPrecondition, stSSTValue, statpSaturatTypeName,
						strStatTypDefn, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strSTvalue[5] = objStatusTypes.fetchSTValueInStatTypeList(
						seleniumPrecondition, statpSaturatTypeName);

				if (strSTvalue[5].compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch Status tpe value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 2.Resource type RT is associated with all the status types.
			 */
			

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.navResourceTypList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRT.resrcTypeMandatoryFlds(seleniumPrecondition,
						strResrctTypName,
						"css=input[name='statusTypeID'][value='"
								+ strSTvalue[0] + "']");
				for(int i=1;i<strSTvalue.length;i++){
					seleniumPrecondition.click("css=input[name='statusTypeID'][value='"
							+ strSTvalue[i] + "']");
				}
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRT.saveAndvrfyResType(seleniumPrecondition,
						strResrctTypName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strRTValue = objRT.fetchRTValueInRTList(seleniumPrecondition,
						strResrctTypName);
				if (strRTValue.compareTo("") != 0) {
					strFuncResult = "";

				} else {
					strFuncResult = "Failed to fetch Role value";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/* 3. Resources RS is created with address under RT. */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRs.navResourcesList(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				String strState = "Alabama";
				String strCountry = "Barbour County";
				strFuncResult = objRs.createResourceWitLookUPadres(
						seleniumPrecondition, strResource, strAbbrv, strResrctTypName,
						strContFName, strContLName, strState, strCountry,
						strStandResType);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strResVal = objRs
						.fetchResValueInResList(seleniumPrecondition, strResource);

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
			 * 5. Role R1 is created selecting status type ST under view and
			 * update.
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRole.navRolesListPge(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.rolesMandatoryFlds(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				String strSTvalues[][] = { { strSTvalue[0], "true" },
						{ strSTvalue[1], "true" }, { strSTvalue[2], "true" },
						{ strSTvalue[3], "true" }, { strSTvalue[4], "true" },
						{ strSTvalue[5], "true" } };
				strFuncResult = objRole
						.slectAndDeselectSTInCreateRole(seleniumPrecondition, false, false,
								strSTvalues, strSTvalues, false);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRole.savAndVerifyRoles(seleniumPrecondition,
						strRolesName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);

				strRoleValue = objRole.fetchRoleValueInRoleList(seleniumPrecondition,
						strRolesName);

				if (strRoleValue.compareTo("") != 0) {
					strFuncResult = "";
				} else {
					strFuncResult = "Failed to fetch role value";
				}
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			/*4. User U1 has following rights:

				'Report - Status Detail'
				Role to view and update all status types.
				'Update Status' and 'Run Report' rights on resource RS. 
			*/

			try {
				assertEquals("", strFuncResult);

				strFuncResult = objCreateUsers.navUserListPge(seleniumPrecondition);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.fillUsrMandatoryFlds(seleniumPrecondition,
						strUserName_1, strInitPwd, strConfirmPwd,
						strUsrFulName_1);

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
				String strOptions = propElementDetails
						.getProperty("CreateNewUsr.AdvOptn.StatusDetail");
				strFuncResult = objCreateUsers.advancedOptns(seleniumPrecondition,
						strOptions, true);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
 
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.selectResourceRightsWitRSValues(
						seleniumPrecondition, strResource, strRSValue[0], false, true,
						true, true);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objCreateUsers.savVrfyUserWithSearchUser(
						seleniumPrecondition, strUserName_1, strByRole, strByResourceType,
						strByUserInfo, strNameFormat);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			log4j.info("~~~~~PRE CONDITION " + gstrTCID + " EXECUTION ENDS~~~~~");
			
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objLogin.logout(seleniumPrecondition);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION STARTS~~~~~");
			

			/*
			 * 2 Login as user U1,Navigate to View>>Map Regional Map View screen
			 * is displayed.
			 */
			
			try {
				assertEquals("", strFuncResult);
				blnLogin=false;
				strFuncResult = objLogin.newUsrLogin(selenium, strUserName_1, strInitPwd);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
				
			try {
				assertEquals("", strFuncResult);
				blnLogin = true;
				strFuncResult =objViewMap.navToRegionalMapView(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 3 Select resource 'RS' from 'Find resource' drop down. Resource
			 * info pop window of RS is displayed with 'View Info' and 'Update
			 * status' links.
			 * 
			 * Resource RS is displayed with Status types NST,MST,sSST,sTST,pMST
			 * and pSST
			 */
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navToUpdateStatusPrompt(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 4 Update all the status types with some statuses on day D1 for
			 * resource RS. Updated status value is displayed on resource pop up
			 * window.
			 */
			
			/*******************************************************************************/	
			
			//Role based
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue1, strSTvalue[0], false, "", "");
				strStatusUpdateDateEST1 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						strStatusValue[1], strSTvalue[1], false, "", "");
				strStatusUpdateDateEST2 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Private

			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = {  "1", "2", "3", "4", "5", "6",
						"7", "8","9"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[2]);
				
				strStatusUpdateDateEST3 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusTST(selenium,
						strStatusUpdateValue4, strSTvalue[3], false, "", "");
				
				strStatusUpdateDateEST4 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			//Private
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViews.fillAndSavUpdateStatusMST(selenium,
						stpStatusValue[1], strSTvalue[4], false, "", "");
				//strStatusUpdateDateEST5 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strScUpdValue1[] = { "0", "1", "2", "3", "4", "5", "6",
						"7", "8"};

				strFuncResult = objViews.fillAndSavUpdateStatusSST(selenium,
						strScUpdValue1, strSTvalue[5]);
				
				strStatusUpdateDateEST6 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
				
				selenium.click("css=input[value='Save']");
				selenium.waitForPageToLoad(gstrTimeOut);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
		/*******************************************************************************/	
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.navResPopupWindow(selenium, strResource);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue1);

				strStatusUpdateDate1 = selenium.getText("//span[text()='"+strStatusUpdateValue1+"" +
						"']/following-sibling::span[1]");
				strStatusUpdateDate1 = strStatusUpdateDate1.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate1.split(" ");
				String strSplitTime[]=strLastUpdArr[2].split(":");
				strStatusUpdateDate1 = strSplitTime[0]+":"+strSplitTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue2);

				strStatusUpdateDate2 = selenium.getText("//span[text()='"+strStatusUpdateValue2+"" +
						"']/following-sibling::span[1]");
				strStatusUpdateDate2 = strStatusUpdateDate2.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate2.split(" ");
				String strSplitTime[]=strLastUpdArr[2].split(":");
				strStatusUpdateDate2 = strSplitTime[0]+":"+strSplitTime[1];
				
	
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objViewMap.verifyUpdValInMap(selenium,
						strStatusUpdateValue3);
				strStatusUpdateDate3 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue3 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate3 = strStatusUpdateDate3.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate3.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate3= strSplitTime[0] + ":" + strSplitTime[1];

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue4);
				
				strStatusUpdateDate4 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue4 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate4 = strStatusUpdateDate4.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate4.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate4= strSplitTime[0] + ":" + strSplitTime[1];

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue5);
				
				strStatusUpdateDate5 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue5 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate5 = strStatusUpdateDate5.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate5.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate5= strSplitTime[0] + ":" + strSplitTime[1];
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			try {
				assertEquals("", strFuncResult);
				
				strFuncResult =objViewMap.verifyUpdValInMap(selenium, strStatusUpdateValue6);
				
				strStatusUpdateDate6 = selenium.getText("//span[text()='"
						+ strStatusUpdateValue6 + ""
						+ "']/following-sibling::span[1]");
				strStatusUpdateDate6 = strStatusUpdateDate6.substring(1, 13);

				String strLastUpdArr[] = strStatusUpdateDate6.split(" ");
				String strSplitTime[] = strLastUpdArr[2].split(":");
				strStatusUpdateDate6= strSplitTime[0] + ":" + strSplitTime[1];
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			/*******************************************************************************/	
			/*
			 * 5 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dro pdown
			 * with status types (all the status types are listed)
			 */
				
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);
				String[] statTypeName = { statrNumTypeName, statrMultiTypeName,
						statsSaturatTypeName, statsTextTypeName,
						statpSaturatTypeName, statpMultiTypeName };
				strFuncResult = objRep.verifyCalndrWidInStatusDetailReportNew(
						selenium, statTypeName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 6 Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select MST and click on 'Next'
			 * 'Status Detail Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type MST 2. Statuses ST1,ST2 3. Resources RS.
			 */
				
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statrMultiTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statrMultiTypeName, strResource, true,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, true, strResVal);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				strStatusGenerateDateEST2 = dts.getTimeOfParticularTimeZone("EST", "HH:mm:ss");
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				
				//strStatusGenerateDate = strCurentDat[2] +":"+ strCurentDat[3];
				strStatusGenerateDate=strStatusGenerateDateEST2.substring(0, 5);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_1 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			
			/*
			 * 7 Select the statuses S1,S2 and resource RS and click on
			 * 'Generate Report'. Status Detail Report is generated in the CSV
			 * (Comma Separated Values) format with sections 'Status Detail' and
			 * 'Status Summary'.
			 * 
			 * 'Status Detail' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource 2. Status 3. Start Date (day D1 date & time)
			 * (DD-MM-YYYY HH:MM) 4. End Date (day D1 date & time) (DD-MM-YYYY
			 * HH:MM) 5. Duration ((total number of minutes on that
			 * status/60)*100) 6. User 7. IP 8. Trace 9. Comments
			 * 
			 * 'Status Summary' section displays following columns with
			 * appropriate data:
			 * 
			 * 1. Resource :(Name of resource) 2. Status :(Updated status name)
			 * 3. Total Hours :(Total number of hours in the reporting period
			 * for that particular status i.e (End date & time - Start date &
			 * time) / 60 4. % of Total Hours :(Status reason report Time (Hrs)/
			 * Total Hours in report period for all the statuses reporting)
			 */
			
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate2;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;

				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST2, strStatusUpdateDateEST2,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);

						
						String[] strTestData = { propEnvDetails.getProperty("Build"),
								gstrTCID, 
								strResource,
								strStatusUpdateValue2,
								strStartDate,
								strEndDate,
								strDurationDiff,
								strUserName_1,
								propEnvDetails.getProperty("ExternalIP"),
								"",
								strCSVDownlPath_1,
								"Status Detail Report need to be checked in PDF file"

						};

						String strWriteFilePath = pathProps
								.getProperty("WriteResultPath");

						objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
						
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 8 Navigate to Reports>>Status Reports, click on 'Status
			 * Detail',select CSV option from 'Report Format' Provide D1 as the
			 * date for 'Start Date' and 'End Date' fields, select NST on
			 * 'Status Detail Report (Step 1 of 2)' screen and click on Next
			 * 'Status Detail Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type NST 2. Resources RS.
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statrNumTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statrNumTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				strStatusGenerateDateEST1 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss");
				
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate=strStatusGenerateDateEST1.substring(0, 5);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_2 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			
			
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate1;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST1, strStatusUpdateDateEST1,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);

				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue1,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_2,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			/*
			 * 10 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * ,Provide D1 as the date for 'Start Date' and 'End Date' fields,
			 * select CSV for report format, select sTST on 'Status Detail
			 * Report (Step 1 of 2)' screen and click on Next 'Status Detail
			 * Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type sTST 2. Resources RS.
			 */
			/*
			 * 11 Select RS and click on 'Generate Report' Status Detail Report
			 * is generated in the CSV (Comma Separated Values) format with
			 * sections 'Status Detail' and 'Status Summary'.
			 * 
			 * Details of resource RS are displayed appropriately with following
			 * columns:
			 * 
			 * 1. Status Value 2. Status Start Date (day D1 date & time)
			 * (DD-MM-YYYY HH:MM) 3. Status End Date (day D1 date & time)
			 * (DD-MM-YYYY HH:MM) 4. Duration (Hrs) 5. User 6. IP 7. Trace 8.
			 * Comments
			 * 
			 * 'Status Summary' section for the resource displays following
			 * details: 1. Resource 2. Status (Updated status name) 3. Total
			 * Hours :(Total number of hours in the reporting period for that
			 * particular status i.e (End date & time - Start date & time) / 60
			 * 4. % of Total Hours :(Status reason report Time (Hrs)/ Total
			 * Hours in report period for all the statuses reporting)
			 */

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statsTextTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statsTextTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				strStatusGenerateDateEST4 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss");
				
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate=strStatusGenerateDateEST4.substring(0, 5);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				

				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_3 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate4;
				String strEndDate = strCurrDate + " " + strStatusGenerateDate;
				
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST4, strStatusUpdateDateEST4,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue4,
						strStartDate,
						strEndDate,
						strDurationDiff,
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_3,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
		
			
			
			
			
			/*12 	Navigate to Reports>>Status Reports, click on 'Status Detail' 		Status Detail Report (Step 1 of 2)' screen is displayed with:

			1. 'Start Date' field (with calender widget)
			2. 'End Date' field (with calender widget)
			3. 'Report Format' (with PDF and CSV options, PDF is selected by default)
			4. Status Types dro pdown with status types (all the status types are listed)
			13 	Provide D1 as the date for 'Start Date' and 'End Date' fields, select CSV for report format, select pSST and click on 'Next' 		'Status Detail Report (Step 2 of 2)' screen is displayed with:

			1. Status type pSST
			2. Resources RS.
			14 	Select RS and click on 'Generate Report' 		ED Saturation Report is generated in the CSV (Comma Separated Values) format with report period.
			ED Saturation Report displays following columns with appropriate data provided while updating status type:

			1. Date Time
			2. Resource
			3. ED Beds Occupied
			4. Pts in Lobby
			5. Amb wait
			6. General Admits
			7. ICU Admits
			8. 1 on 1 Pts
			9. Excess Lobby Wait
			10. RNs short-staffed
			11. Assigned ED Beds
			12. Lobby Capacity
			13. Sat Score
			14. Charge RN/Mgr
			15. Physician
			16. Comments 
			
			*/
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statpSaturatTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);

				String[] strStatusName = { strStatusName1, strStatusName2 };

				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statpSaturatTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);

				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);
				
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
				
				strStatusGenerateDateEST6 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss");

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");

				strStatusGenerateDate=strStatusGenerateDateEST6.substring(0, 5);
				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_4 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);

				
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate6;
				
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST6, strStatusUpdateDateEST6,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue6,
						strStartDate,
						"",
						"",
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_4,
						"Status Detail Report need to be checked in PDF file"
				};
				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
					
			
			/*
			 * 15 Navigate to Reports>>Status Reports, click on 'Status Detail'
			 * Status Detail Report (Step 1 of 2)' screen is displayed with:
			 * 
			 * 1. 'Start Date' field (with calender widget) 2. 'End Date' field
			 * (with calender widget) 3. 'Report Format' (with PDF and CSV
			 * options, PDF is selected by default) 4. Status Types dropdown
			 * with status types (all the status types are listed) 16 Provide D1
			 * as the date for 'Start Date' and 'End Date' fields, select CSV
			 * for report format, select sSST and click on 'Next' 'Status Detail
			 * Report (Step 2 of 2)' screen is displayed with:
			 * 
			 * 1. Status type sSST 2. Resources RS. 17 Select RS and click on
			 * 'Generate Report' ED Saturation Report is generated in the CSV
			 * (Comma Separated Values) format with report period. ED Saturation
			 * Report displays following columns with appropriate data provided
			 * while updating status type:
			 * 
			 * 1. Date Time 2. Resource 3. ED Beds Occupied 4. Pts in Lobby 5.
			 * Amb wait 6. General Admits 7. ICU Admits 8. 1 on 1 Pts 9. Excess
			 * Lobby Wait 10. RNs short-staffed 11. Assigned ED Beds 12. Lobby
			 * Capacity 13. Sat Score 14. Charge RN/Mgr 15. Physician 16.
			 * Comments
			 */
			
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusReports(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.navToStatusDetailReport(selenium);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			
			try {
				assertEquals("", strFuncResult);

				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				System.out.println(strApplTime);

				strFuncResult = objRep.enterReportStatusDetailAndNavigate(
						selenium, strApplTime, strApplTime, false,
						statsSaturatTypeName);

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}

			
			try {
				assertEquals("", strFuncResult);
				String[] strStatusName = { strStatusName1, strStatusName2 };
				strFuncResult = objRep.vrfySTandRSinStatusDetailReport2Of2Pge(
						selenium, statsSaturatTypeName, strResource, false,
						strStatusName);
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			try {
				assertEquals("", strFuncResult);
				strFuncResult = objRep.enterReportStatusDetailGenerateReport(
						selenium, strStatusValue, false, strResVal);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			
			try {
				assertEquals("", strFuncResult);
				selenium.selectWindow("");
				selenium.selectFrame("Data");
			
				strStatusGenerateDateEST3 = dts.getTimeOfParticularTimeZone(
						"EST", "HH:mm:ss");
				// Fetch Application time
				String strCurentDat[] = objGeneral.getSnapTime(selenium);
				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				System.out.println("Current Year: " + year);

				strApplTime = dts.converDateFormat(strCurentDat[0] + "-"
						+ strCurentDat[1] + "-" + year, "dd-MMM-yyyy",
						"M/d/yyyy");
				strStatusGenerateDate = strStatusGenerateDateEST3.substring(0, 5);
			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			

			
			try {
				assertEquals("", strFuncResult);			
				String strProcess = "";
				String strArgs[] = { strAutoFilePath, strCSVDownlPath_5 };

				// AutoIt
				Runtime.getRuntime().exec(strArgs);

				int intCnt = 0;
				do {
					GetProcessList objGPL = new GetProcessList();
					strProcess = objGPL.GetProcessName();
					intCnt++;
					Thread.sleep(500);
				} while (strProcess.contains(strAutoFileName) && intCnt < 180);			
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
						
			try {
				assertEquals("", strFuncResult);
				String strCurrDate = dts.converDateFormat(strApplTime,
						"M/d/yyyy", "dd-MMM-yyyy");
				String strStartDate = strCurrDate + " " + strStatusUpdateDate3;
				
				int TimeDiff = dts.getTimeDiffWithTimeFormat(
						strStatusGenerateDateEST3, strStatusUpdateDateEST3,
						"HH:mm:ss");

				double fltDurationDiff = (double) TimeDiff / 3600;

				double dRounded = dts.roundTwoDecimals(fltDurationDiff);
				String strDurationDiff = Double.toString(dRounded);
				System.out
						.println("CSV generation duration " + strDurationDiff);
				
				String[] strTestData = { propEnvDetails.getProperty("Build"),
						gstrTCID, 
						strResource,
						strStatusUpdateValue3,
						strStartDate,
						"",
						"",
						strUserName_1,
						propEnvDetails.getProperty("ExternalIP"),
						"",
						strCSVDownlPath_5,
						"Status Detail Report need to be checked in PDF file"

				};

				String strWriteFilePath = pathProps
						.getProperty("WriteResultPath");

				objOFC.writeResultData(strTestData, strWriteFilePath, "Status_Detail_Report");
			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}		
			
			try {
				assertEquals("", strFuncResult);
				File CSV1 = new File(strCSVDownlPath_1);
				File CSV2 = new File(strCSVDownlPath_2);
				File CSV3 = new File(strCSVDownlPath_3);
				File CSV4 = new File(strCSVDownlPath_4);
				File CSV5 = new File(strCSVDownlPath_5);

				if (CSV1.isFile() && CSV2.isFile() && CSV3.isFile()
						&& CSV4.isFile() && CSV5.isFile()) {
					gstrResult = "PASS";
				}

			} catch (AssertionError Ae) {
				gstrReason = strFuncResult;
			}
			
			log4j.info("~~~~~TEST CASE " + gstrTCID + " EXECUTION ENDS~~~~~");
			log4j
					.info("----------------------------------------------------------");

		} catch (Exception e) {
			gstrTCID = "BQS-103963";
			gstrTO = "Generate 'Status Detail report' in CSV format and verify that the report displays correct data.";
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


